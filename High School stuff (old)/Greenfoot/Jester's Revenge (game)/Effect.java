import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A basic superclass for Effects - images that eventually fade away when first initialised.
 * 
 * @author Arthur Zeng
 * @version 3.00
 */
public abstract class Effect extends Actor
{
    protected int actCounter;
    protected double speed;

    /**
     * Sets an image to the one passed into the parameter, and sets its act counter, or duration
     * to the integer passed into the parameters.
     * 
     * @param duration      the duration of the effect - how long it lingers before disappearing
     * @param image         the visual appearence of the effect, a GreenfootImage
     */
    public Effect(int fadeAway, GreenfootImage image) {
        setImage(image);
        actCounter = fadeAway;
        speed = 1;
    }
    
    /**
     * Acting causes this actor to fade away its own image over the course of its actCounter.
     * After it's fully faded away, it is deleted from the world.
     */
    public void act()
    {
        // act fades away its own image as it is spawned
        if (this.getImage().getTransparency() - speed > 0) {
            this.getImage().setTransparency((int)(getImage().getTransparency() - speed));            
        } else {
            this.getImage().setTransparency(actCounter - 1);
        }
        if (this.getImage().getTransparency() < actCounter)
        {
            getWorld().removeObject(this);
        }
    }
}
