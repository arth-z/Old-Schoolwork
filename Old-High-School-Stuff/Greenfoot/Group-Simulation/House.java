import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A skeleton class representing a still image of a house.
 * 
 * @author Steven Lu and Arthur Zeng
 * @version (a version number or a date)
 */
public class House extends Building
{
    
    private GreenfootImage house = new GreenfootImage("house.png");
    /**
     * Sets its image to a house.
     */
    public House(){
        this.setImage(house);
    }
}
