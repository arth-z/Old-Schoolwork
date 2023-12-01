import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A basic button superclass that executes code when its image is pressed.
 * Contains an abstract "promise" of a method, doYourJob(), called in act when the actor is clicked.
 * Some subclasses fulfill this promise and have an actual method, others have an empty method instead.
 * 
 * @author Michael Liu
 * @version 1.00
 */
public abstract class Button extends Actor
{
    protected abstract void doYourJob();
    protected static boolean buttonsActive;
    
    /**
     * The constructor for this class merely sets a field, a static boolean buttonsActive, representing if other buttons are active, to true.
     */
    public Button() {
        buttonsActive = true;
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
        
        if (!buttonsActive) {
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Mutator for a static boolean buttonsActive, representing if other buttons are active. Sets it to false.
     */
    public static void removeButtons() {
        buttonsActive = false;
    }
}
