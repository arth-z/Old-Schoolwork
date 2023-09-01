import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A subclass of Button that serves as a click-checker for whether or not the user wants to move on from the Config Screen.
 * Has a degree of content more than its dummy/skeleton peers.
 * 
 * @author Michael Liu
 * @version 1.00
 */
public class ConfirmButton extends Button
{
    private GreenfootImage sprite = new GreenfootImage("confirmButton.png");
    private ConfigScreen w;
    private KDSAbove world; 
    /**
     * Constructor sets the actor's image to a corresponding button of a green checkmark.
     */
    public ConfirmButton(){
        this.setImage(sprite);
    }
    /**
     * Calls super.act().
     */
    public void act()
    {
        // Add your action code here.
        super.act();
    }
    /**
     * When added to any world, the ConfirmButton sets that world as one of its fields, but not before casting it as the ConfigScreen type.
     */
    public void addedToWorld(World w){
        this.w = (ConfigScreen)w;
    }
    /**
     * When the ConfirmButton "does its job", or when it's clicked, it instantiates a new KDSAbove world and then sets the GreenfootWorld to that world.
     * In other words, it moves the simulation forward from the ConfigScreen to the simulation proper.
     */
    public void doYourJob(){
        world = new KDSAbove(w.getVillagerMaxHP(), w.getInitialVillagers(), w.getEnemySpawnRate(), w.getEnemyHpMultiplier(), w.getResourceSpawnRate(), w.getResourceGainMultiplier());
    
        Greenfoot.setWorld(world);
    }
}
