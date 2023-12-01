import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Never runs people over - pursues the guilty. Cats are fair game, though.
 * Would rather destroy itself in shame than let a guilty party escape
 */
public class Police extends Vehicle
{
    // variables for catching and detecting guilty
    private ArrayList<Vehicle> inRange;
    private ArrayList<Vehicle> guiltyInRange;
    private boolean jobDone;
    private boolean seeking;
    
    public Police(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        
        // init movement values
        maxSpeed = 1.25 + ((Math.random() * 30)/5);
        speed = maxSpeed;
        yOffset = 0;
        canLaneChange = true;
        
        // init sound values
        generalUseSound = new GreenfootSound("sirens.wav");
        generalUseSound.setVolume(75);
        
        // init arraylist for scan
        ArrayList<Vehicle> inRange = new ArrayList<Vehicle>();
    }
    
    /**
     * Act - do whatever the Police wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // scan in a 300 pixel radius for guilty vehicles
        scan(300);
        
        if (!checkHitPedestrian()) { // While there isn't anything in front of me
            // just drive
            drive();
        } 
        
        super.act();
        // if successfully caught a guilty vehicle, self-obliterate
        if (jobDone) {
            myJobHereIsDone();
        }
    }
    
    public boolean checkHitPedestrian() {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset(direction*50, 0, Pedestrian.class); // look in front of me
        
        if (p == null) { // if there's nothing, return false
            return false;
        } else { // if there is something
            if (!p.isAwake()) { // if it's not awake
                if (p.getClass() == Person.class) { // if it's a person
                    stopThenRemove(Person.class); // rescue them
                } else { // if it's a downed cat
                    p.knockDown(); // no mercy
                }
            } else { // if the person in front is awake
                speed = 0; // stop
            }
            return true; // return true
        }
    }
    
    public void scan(int range) {
        // get a list of all the vehicles in range
        inRange = (ArrayList) getObjectsInRange(range, Vehicle.class);
        guiltyInRange = new ArrayList<Vehicle>();
        
        for (Vehicle v: inRange) { // go through them
            if (v.isGuilty()) { // if they're guilty
                guiltyInRange.add(v); // add to guilty list
                generalUseSound.play(); // sirens on
                seeking = true;
                if (seek(v)) { // seek them
                    jobDone = true; // if seek was successful, you can disappear
                }
            }
        }
        
        if (seeking == true && guiltyInRange.isEmpty()) { // if you are seeking but the list of guilty are empty
            jobDone = true; // you have lost your target and must self-terminate
        }
    }
    
    // method to pursue the guilty
    public boolean seek(Actor a) {
        if (a != null) { // if the actor you attempt to seek exists
            turnTowards(a.getX(), a.getY()); // turn towards it and move in its direction
            move(6); 
            if (getOneIntersectingObject(Vehicle.class) == a) { // if we touch
                ((SuperSmoothMover)a).die(); // it dies
                return true;
            }
        } else {
            jobDone = true; // if police lose their target, they explode.
        }
        return false;
    }
    
    public void myJobHereIsDone() { // when the police has obliterated the guilty
                                    // but im too lazy to get it back into a lane
        getWorld().addObject(new BangEffect(), this.getX(), this.getY());
        getWorld().removeObject(this);
    }
}
