import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The title screen for the game. Contains a bunch of buttons leading to other worlds. Goes into JestersRevenge directly, or through Instructions first.
 * 
 * @author Michael Liu
 * @version 2.00
 */
public class TitleScreen extends World
{
    private Button startButton, instructionButton;
    private GreenfootImage titleText = new GreenfootImage("titleText.png");
    private GreenfootImage start = new GreenfootImage("startButton.png");
    private GreenfootImage instructions = new GreenfootImage("instructionButton.png");
    private JestersRevenge mainWorld = new JestersRevenge();
    private Instructions instructionWorld = new Instructions();
    
    private Image title = new Image(titleText);
    
    /**
     * Throws up a bunch of visual elements onscreen, sets the background, and displays buttons and relevant images.
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        setBackground("title.jpg");
        setPaintOrder(Button.class);
        
        
        startButton = new Button(start, mainWorld);
        instructionButton = new Button(instructions, instructionWorld);
        
        addObject(startButton, 600, 400);
        addObject(instructionButton, 600, 550);
        addObject(title, 600, 200);
    }
}
