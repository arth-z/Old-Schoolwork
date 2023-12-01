import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A start menu for the simulation. Has a "start" button that moves forwards to the config screen.
 * 
 * @author Michael Liu
 * @version 1.00
 */
public class StartMenu extends World
{

    private Button start = new StartButton();
    private GreenfootImage startImage = new GreenfootImage("startWorld.jpg");
    /**
     * Creates a new world, sets its background to an appropriate image, and spawns a button that, when pressed, moves the world forwards to the config screen.
     * Also, sets an appropriate paint order so that the button is over the StartMenu, which is over the ConfigScreen, over the Simulation proper.
     */
    public StartMenu()
    {    
        super(799, 600, 1); 
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        setBackground(startImage);
        setPaintOrder(Button.class, StartMenu.class, ConfigScreen.class, KDSAbove.class);
        addObject(start, this.getWidth()/2, 400);
    }
}
