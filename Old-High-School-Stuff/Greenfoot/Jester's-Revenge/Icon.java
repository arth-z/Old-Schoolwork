import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Image that is toggled between 2 sprites. Serves as a cooldown icon for Parry and Blank.
 * 
 * @author Michael Liu
 * @version 1.0
 */
public class Icon extends UI
{
    private boolean cooldown;
    private GreenfootImage sprite, cooldownSprite;
    /**
     * Constructor accepts two sprites - one for active, one for on cooldown. Also sets cooldown (is on cooldown) to false.
     * 
     * @param sprite            sprite for when the icon is not on cooldown
     * @param cooldownSprite    sprite for when the icon is on cooldown
     */
    public Icon(GreenfootImage sprite, GreenfootImage cooldownSprite){
        this.sprite = sprite;
        this.cooldownSprite = cooldownSprite;
        cooldown = false;
    }
    
    /**
     * Toggles its image based on the cooldown (is on cooldown) variable - whether or not it is on cooldown.
     */
    public void act(){
        if(cooldown == false){
            this.setImage(sprite);
        }
        else
        {
            this.setImage(cooldownSprite);
        }
    }
    
    /**
     * Toggler for the cooldown variable. Can be changed to true or false, based on parameter.
     * 
     * @param cooldown          the boolean you want cooldown to be on
     */
    public void cooldown(Boolean cooldown){
        this.cooldown = cooldown;
    }
    
}
