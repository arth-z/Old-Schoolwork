import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A big white circle that fades away upon being spawned. Acts as a visual indicator of a slam attack, by Boss.
 * 
 * @author Arthur Zeng
 * @version 2.0
 */
public class SlamAttackAOE extends Effect
{
    /**
     * Calls its super constructor, and passes into it a new 200x200 vacant GreenfootImage.
     * It then initialises that image into a big white circle, 200x200.
     */
    public SlamAttackAOE() {
        super(20, new GreenfootImage(200, 200));
        
        getImage().setColor(Color.WHITE);
        getImage().drawOval(0, 0, 200, 200);
        getImage().fillOval(0, 0, 200, 200);
        getImage().setTransparency(200);
        setImage(getImage());
    }
    
    /**
     * Calls super.act() - fades away over time based on actCounter, then deletes itself when fully transparent.
     */
    public void act()
    {
        super.act();
    }
}
