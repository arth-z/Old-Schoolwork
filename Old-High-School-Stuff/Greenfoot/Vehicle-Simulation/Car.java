import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Car subclass. Runs over people, rescues cats.
 */
public class Car extends Vehicle
{    
    public Car(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        
        // init movement variables
        maxSpeed = 1.5 + ((Math.random() * 30)/5);
        speed = maxSpeed;
        yOffset = 0;
        canLaneChange = true;
        
        // init the meow sound effect, usually null, inherited from superclass
        initMeow();
    }

    public void act()
    {
        if(checkHitPedestrian()) {
            stopThenRemove(Cat.class);
            meow.play();
        } else {
            drive();
        }
        
        super.act();
    }
    
    /**
     * When a Car hits a Pedestrian, it should knock it over
     */
    public boolean checkHitPedestrian () {
        
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class); // check if a Pedestrian is in my hitbox

        if (p != null) { // if they exist
            if (p.getClass() == Person.class) { // if they're a person
                p.knockDown(); // no mercy
                if (p.getHealth() == 0) { // if they die, you are guilty
                    this.beGuilty();
                }
            }
            else {
                return true; // means you've run into a cat, and you can stopThenRemove it and play the meow sound (called in act)
            }
        }
        return false; // always return false if first if block true
    }
}
