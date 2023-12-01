import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A skeleton Button subclass that serves as a dummy click-checker in ConfigScreen.
 * The doYourJob() promise is empty.
 * 
 * @author Michael Liu
 * @version 1.00
 */
public class AddButton extends Button
{
    private GreenfootImage sprite = new GreenfootImage("addButton.png");
    /**
     * The constructor of this method sets the image of the actor to a corresponding image of a button.
     */
    public AddButton(){
        this.setImage(sprite);
    }
    public void doYourJob(){
        
    }
}
