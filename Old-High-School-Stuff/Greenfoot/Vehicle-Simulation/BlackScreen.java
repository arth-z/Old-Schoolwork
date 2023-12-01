import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A black screen that ideally covers the entire world, and fades in when it's spawned, then stays there until removed.
 * Helper class for the DivineRetribution.
 * 
 * @author (Arthur Zeng) 
 * @version (a version number or a date)
 */
public class BlackScreen extends Actor
{
    private double transparency;
    
    public BlackScreen() {
        // init transparency
        transparency = 0;
        
        // make a giant black screen
        this.getImage().clear();
        this.getImage().scale(1600, 1200);
        this.getImage().setColor(Color.BLACK);
        this.getImage().fill();
        this.setImage(this.getImage()); // this line is probably redundant
        
        //warning! some memory issues might occur from using this, greenfoot really can't handle big black rectangles...?
    }
    
    public void addedToWorld(World w) {
        this.getImage().setTransparency(0); // start of the fade in
    }
    
    /**
     * Act - Literally just fades in a giant black screen.
     */
    public void act()
    {
        // Transparency acts both as an act counter and the actual transparency value of the rectangle
        
        this.getImage().setTransparency((int)transparency);
        
        if (transparency < 254) { // transparency increments by 0.5 until 254, then stops incrementing
            transparency+=0.5;
        } 
    }
}
