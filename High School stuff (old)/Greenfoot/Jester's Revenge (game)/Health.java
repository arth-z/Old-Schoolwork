import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Health objects that stores 2 sprites, one filled and one broken to indicate the loss of health.
 * 
 * @author Michael Liu
 * @version 1.0
 */
public class Health extends UI
{
    private GreenfootImage sprite, emptySprite;
    
    /**
     * The constructor for this class sets an empty and filled sprite, and then sets its image to the filled one.
     * 
     * @param sprite        sprite for the health object (full)
     * @param emptySprite   broken sprite for the health object (depleted)
     */
    public Health(GreenfootImage sprite, GreenfootImage emptySprite){
        this.setImage(sprite);
        this.sprite = sprite;
        this.emptySprite = emptySprite;
    }
    
    /**
     * Sets the Health's sprite to its empty variant.
     */
    public void empty(){
        this.setImage(emptySprite);
    }
    
    /**
     * Sets the Health's sprite to its full variant.
     */
    public void fill(){
        this.setImage(sprite);
    }
}
