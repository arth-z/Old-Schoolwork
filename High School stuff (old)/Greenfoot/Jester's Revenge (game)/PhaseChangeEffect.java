import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A "scream" VFX for the Joker's phase transition. A semi-transparent white circle - it grows to encompass the whole screen, then eventually shrinks.
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class PhaseChangeEffect extends Effect
{
    public PhaseChangeEffect(int actCounter, GreenfootImage image, boolean black) {
        super(actCounter, image);
        this.getImage().clear();
        this.getImage().scale(1300, 900);
        if (black) {
            this.getImage().setColor(Color.BLACK);

        } else {
            this.getImage().setColor(Color.WHITE);
        }
        this.getImage().fill();
        this.setImage(this.getImage()); // this line is probably redundant
        speed = 2;
    }
    
    /**
     * Act - do whatever the phaseChangeEffect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
}
