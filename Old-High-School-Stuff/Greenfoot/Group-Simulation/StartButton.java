import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A subclass of Button that serves as a click-checker for whether or not the user wants to move on from the StartMenu to the ConfigScreen.
 * Has a degree of content more than its dummy/skeleton peers.
 * 
 * @author Michael Liu
 * @version 1.00
 */
public class StartButton extends Button
{
    private GreenfootImage sprite = new GreenfootImage("startButton.png");
    private ConfigScreen world = new ConfigScreen();
    /**
     * Constructor sets the actor's image to a corresponding button of a green checkmark.
     */
    public StartButton(){
        this.setImage(sprite);
    }
    /**
     * Calls super.act() - in other words, when it is clicked, it will do its job.
     */
    public void act()
    {
        // Add your action code here.
        super.act();
    }
    /**
     * When clicked, this button sets Greenfoot's world as the config screen, moving the simulation forwards from the start menu to the config screen.
     */
    public void doYourJob(){
        Greenfoot.setWorld(world);
    }
}
