import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A skull that fades away upon being spawned. Based on the Bug Simulation's death effect.
 * 
 * @author Arthur Zeng
 * @version 2.0
 */
public class DeathEffect extends Effect
{
    /**
     * This constructor inherits the superclass constructor, but passes in a GreenfootImage of
     * a skull, automatically, so the only parameter to be passed in is duration.
     * 
     * @param duration      how long the effect stays on screen before it disappears.
     */
    public DeathEffect(int duration) {
        super(duration, new GreenfootImage("skull.png"));
    }
    
    
    /**
     * Acting causes this actor to fade away its own image over the course of its actCounter.
     * After it's fully faded away, it is deleted from the world.
     */
    public void act()
    {
        // act fades away its own image as it is spawned
        super.act();
    }
}
