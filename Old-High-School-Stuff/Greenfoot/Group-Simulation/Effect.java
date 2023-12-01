import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A basic superclass for Effects - images that eventually fade away when first initialised.
 * 
 * @author Arthur Zeng
 * @version 3.00
 */
public class Effect extends Actor
{
    protected int actCounter;

    /**
     * Sets an image to the one passed into the parameter, and sets its act counter, or duration
     * to the integer passed into the parameters.
     * 
     * @param duration      the duration of the effect - how long it lingers before disappearing
     * @param image         the visual appearence of the effect, a GreenfootImage
     */
    public Effect(int duration, GreenfootImage image) {
        setImage(image);
        actCounter = duration;
    }
    
    /**
     * Acting causes this actor to fade away its own image over the course of its actCounter.
     * After it's fully faded away, it is deleted from the world.
     */
    public void act()
    {
        // act fades away its own image as it is spawned
        this.getImage().setTransparency(getImage().getTransparency() - 1);
        if (this.getImage().getTransparency() < actCounter)
        {
            getWorld().removeObject(this);
        }
    }
}
