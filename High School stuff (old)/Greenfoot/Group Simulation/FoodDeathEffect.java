import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A chicken drumstick, inherited from Effect, that fades away over time.
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class FoodDeathEffect extends Effect
{
    /**
     * This constructor inherits the superclass constructor, but passes in a GreenfootImage of
     * a chicken drumstick, automatically, so the only parameter to be passed in is duration.
     * 
     * @param duration      how long the effect stays on screen before it disappears.
     */
    public FoodDeathEffect(int duration) {
        super(duration, new GreenfootImage("resourceDeath.png"));
    }
    
    /**
     * Fades away over time based on duration, then deletes itself when fully transparent.
     */
    public void act()
    {
        super.act();
    }
}
