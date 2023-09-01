import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Ambulance subclass
 */
public class Ambulance extends Vehicle
{
    public Ambulance(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first
        
        maxSpeed = 2.5;
        speed = maxSpeed;
    }

    /**
     * Act - do whatever the Ambulance wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        drive();
        checkHitPedestrian();
        super.act();
    }
    
    public boolean checkHitPedestrian () 
    {
        // entire hitbox
        Pedestrian p = (Pedestrian)getOneIntersectingObject(Pedestrian.class);

        // if i run into a downed person, heal them - otherwise they're a cat, and no mercy
        if (p != null) {
            if (p.getClass() == Person.class) {
                if (!p.isAwake()) { // heal unawake people
                    p.healMe();
                }
            }
            else { // run over cats
                p.knockDown();
            }
            return true;
        }
        return false;
    }
}
