import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Brief description of the controls, mechanics, and lore. TitleScreen transitions into this, which transitions into JestersRevenge.
 * 
 * @author Arthur Zeng
 * @version 1.0
 */
public class Instructions extends World
{

    private Button beginButton;
    private GreenfootImage beginButtonSprite = new GreenfootImage("beginButton.png");
    private JestersRevenge world = new JestersRevenge();
    
    /**
     * Just shows a bunnch of text on screen, sets world size, sets background, adds a button.
     */
    public Instructions()
    {    
        
        super(1200, 800, 1); 
        
        setBackground("deathScreen.jpg");
        
        beginButton = new Button(beginButtonSprite, world);
        addObject(beginButton, 1080, 720);
        showText("The Jester Card has robbed all the cards of their suits and sown chaos everywhere.", 600, 100);
        showText("Diamonds. Clovers. Hearts. Spades. The Kings hoard their suits greedily to themselves, corrupted by the foul Jester.", 600, 125);
        showText("You are the Ace of the 5th missing suit - Stars - and the earthly champion of your long-ascended people.", 600, 200);
        showText("It is your duty to challenge the corrupted Kings one-by-one and put them in their place.", 600, 225);
        showText("The Kings have agreed to meet you on an isolated checkerboard where the consecutive duels will not be disturbed.", 600, 300);
        showText("Use WASD to navigate this checkerboard and battleground tile by tile.", 600, 325);
        showText("Avoid attacks that target specific tiles. The Kings will indicate which cells they attack,", 600, 400);
        showText("before using the unholy power of their stolen suit to desecrate that tile and cause damage to your holy being.", 600, 425);
        showText("You can also use holy power by pressing J to become invulnerable for a short period of time,", 600, 500); 
        showText("and K to purge the board of all corrupted influence. ", 600, 525);
        showText("Your power is transient, however, and you must wait before you can use them again.", 600, 550);
        showText("Reach the special tile on the board with the strange suit and its seeing eye to press SPACE to retaliate.", 600, 650);
        showText("The struggle may be hard, but speed, focus, and persistence will be the key to victory.", 600, 725);
        showText("Best of luck, Ace of Stars!", 600, 750);
    }
}
