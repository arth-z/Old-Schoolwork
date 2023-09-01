import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An icon that changes based on the day/night status of the world.
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class DayNightIcon extends Actor
{
    protected KDSAbove world; // represents the world this exists in.
    
    /**
     * By default, sets the image to the sun.
     */
    public DayNightIcon() {
        setImage("sunSprite.png");
    }
    
    /**
     * Sets world to KDSAbove when it's to the added to world.
     */
    public void addedToWorld(World w) {
        this.world = (KDSAbove)getWorld();
    }
    
    /**
     * Changes its icon (sun or moon) corresponding to the day/night status of the world it is in.
     */
    public void act()
    {
        if (world.isDay()) {
            setImage("sunSprite.png");
        } else {
            setImage("moonSprite.png");
        }
    }
}
