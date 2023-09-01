import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Interface that serve as an anchor for icons. Basically, a black box.
 * 
 * @author Michael Liu 
 * @version 1.0
 */
public class StatInterface extends UI
{
   
    private int spacing = 60;
    private int iconWidths;
    
    /**
     * Sets its image to a passed in sprite, defines spacing based on a parameter.
     * 
     * @param sprite            the sprite of the interface.
     * @param spacing           the spacing between elements on that interface.
     */
    public StatInterface(int spacing, GreenfootImage sprite){
        this.setImage(sprite);
        this.spacing = spacing;
    }
    
    /**
     * Adds an icon passed by parameter, to the StatInterface, based on spacing.
     * 
     * @param x             the Icon you want to add to the interface.
     */
    public void addIcon(Icon x){
        getWorld().addObject(x, this.getX()/2 + 20 + iconWidths, this.getY());
        iconWidths += x.getImage().getWidth() + spacing;
    }
}
