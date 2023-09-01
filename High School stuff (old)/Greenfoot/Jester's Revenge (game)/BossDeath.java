import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A "skeleton" actor implementation of a death animation/gif.
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class BossDeath extends Actor
{
    protected GifImage gifImage;
    protected boolean looped;

    /**
     * Initialises a variable called GifImage according to a gif in the system images.
     */
    public BossDeath() {
        gifImage = new GifImage(new GifImage("deathReticle.gif"));
    }
    
    /**
     * Sets its own image to the current image of its GifImage (which cycles/loops endlessly).
     */
    public void act()
    {
        setImage(gifImage.getCurrentImage());
    }
}
