import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A basic button superclass that executes code when its image is pressed.
 * Contains a method, doYourJob(), called in act when the actor is clicked.
 * Some subclasses override this, some don't. By default, it sets the world to one passed in the parameter.
 * 
 * @author Michael Liu
 * @version 1.5
 */
public class Button extends UI
{
    private World world;
    /**
     * The constructor for this class sets the button to a specific sprite and sets the directory to a specific world.
     * 
     * @param sprite        sprite for the button
     * @param w             world to move to when the button is pressed
     */
    public Button(GreenfootImage sprite, World w){
        this.setImage(sprite);
        world = w;
    }
    
    /**
     * Transitions the world into the next, called when button is pressed.
     */
    public void doYourJob(){
        Greenfoot.setWorld(world);
    }
    
    /**
     * Act method consists of waiting to be clicked, then executing code based on whether or not it is clicked.
     * If buttonsActive is false, aka it is determined that there shouldn't be any buttons in the world anymore, the button is deleted.
     */
    public void act()
    {
        if (Greenfoot.mousePressed(this)) {
            doYourJob();
        }
    }
}
