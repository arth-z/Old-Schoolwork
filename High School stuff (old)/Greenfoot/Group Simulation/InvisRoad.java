import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An invisible dummy class with no acting code.
 * As for its purpose in code, it serves as a point to which Villagers can orient themselves towards.
 * 
 * @author Arthur Zeng and Michael Liu
 * @version 1.00
 */
public class InvisRoad extends Decoration
{
    /**
     * All this constructor does is set the image of the object to be transparent.
     */
    public InvisRoad(){
        this.getImage().setTransparency(0);
    }
}
