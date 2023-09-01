import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Bus subclass
 */
public class Bus extends Vehicle
{    
    public Bus(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first
        
        //Set up values for Bus
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        // because the Bus graphic is tall, offset it a up 
        yOffset = 15;
    }

    /**
     * Act - do whatever the Bus wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(checkHitPedestrian()) { // if there's an awake person touching us, basically
            stopThenRemove(Person.class); // spirit away
        } else { // other than that
            drive(); 
        }
        
        super.act();
    }
    
    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class); // someone touching me?
        
        if (p != null){ // do they exist?
            if (p.getClass() == Person.class) { // are they a person?
                if (p.isAwake()) { // awake?
                    return true;
                } else { // if they're not awake...
                    p.knockDown(); // no mercy
                    if (p.getHealth() == 0) { // if we kill them...
                        this.beGuilty();
                    }
                }
            } else { // otherwise it's a cat
                p.knockDown(); // no mercy
                return false;
                }
        }
        return false;
    }
}
