import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Person on a bicycle. When colliding with another Person, the cyclist is also knocked down.
 * Picks up cats. Usually slow.
 */
public class Cyclist extends Vehicle
{
    
    private boolean downed;
    private boolean catOnBoard;
    
    public Cyclist(VehicleSpawner origin) {
        super(origin); // superclass const
        
        // init movement variables 
        maxSpeed = 1.5;
        speed = maxSpeed;
        yOffset = 0;    
        canLaneChange = true;
        
        // init the meow sound effect, usually null in other subclasses, inherited from Vehicle
        initMeow();
        
        // init wilhelm scream
        generalUseSound = new GreenfootSound("wilhelm.wav");
        generalUseSound.setVolume(75);
    }
    
    /**
     * Act - do whatever the Cyclist wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
                
        if(checkHitPedestrian()) {
            stopThenRemove(Cat.class);
            meow.play();
            catOnBoard = true;
        } else {
            drive();
        }
        
        super.act();
        
        // death check - basically, if you crashed, get removed
        // called last in act method due to greenfoot removedfromactor errors that pop up if i dont
        if (downed) {
            getWorld().removeObject(this);
        }
    }
    
    public void crash() { // method called when the cyclist crashes into a Person
        
        int direction = (Greenfoot.getRandomNumber(2) == 0) ? 1 : -1; // ternary operator - pick randomly from 1 and -1
        
        // scream!
        generalUseSound.play();
        
        // make a new Person, who's downed
        Person downedCyclist = new Person(direction);
        getWorld().addObject(downedCyclist, this.getX(), this.getY());
        
        // knock that guy over
        downedCyclist.knockDown();
        
        if (catOnBoard) { // if we picked up a cat before, we'll spawn a cat
            Cat newCat = new Cat(direction);
            getWorld().addObject(newCat, this.getX(), this.getY());
            meow.play();
        }
        
        // set imagetransparency to 0, illusion of death
        this.getImage().setTransparency(0);
        
        // set downed flag to true (flag for deletion)
        this.downed = true;
    }
    
    public boolean checkHitPedestrian() {
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class); // get someone in my hitbox
        
        if (p != null) { // if exists
            if (p.getClass() == Person.class) { // if it's a person
                if (p.isAwake()) { // if they're awake
                    this.crash(); // we crash
                    p.knockDown();
                } else { // if they're not awake
                    p.knockDown(); // knock it down
                    if (p.getHealth() == 0) { // if we kill them
                        this.beGuilty();
                    }
                }
            } else { // if it's a cat, though, check act
                return true;
            }
        }
        return false; // always return false if first if block fails
        }
        
    public boolean isDowned() {
        return downed;
    }
    }
