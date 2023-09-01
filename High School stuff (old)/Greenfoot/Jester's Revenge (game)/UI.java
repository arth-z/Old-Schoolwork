import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class serves as an empty abstract class meant to contain all user interface related classes, this class is created purely for the sake of organization.
 * 
 * @author Michael Liu 
 * @version 1.0
 */
public abstract class UI extends Actor
{
    /**
     * Sets its own image. If no image is passed in, does nothing.
     */
    public UI(GreenfootImage sprite){
        this.setImage(sprite);
    }
    public UI(){
        
    }
}
