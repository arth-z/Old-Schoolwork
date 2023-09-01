import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A person. Basically the most basic Pedestrian taken from the original implementation.
 */
public class Person extends Pedestrian
{
    public Person(int direction) {
        super(direction, 60);
        
        // death scream - all pedestrians have this
        scream = new GreenfootSound("wilhelm.wav");
        scream.setVolume(75);
    }
    
    public void act()
    {
        // Add your action code here.
        super.act();
    }
    
}
