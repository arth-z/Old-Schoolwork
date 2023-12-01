import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A big black screen representing night time in the game's world.
 * 
 * @author Steven Lu
 * @version 2.00
 */
public class Night extends Actor
{
    // transparency variables
    private double transparency;
    private double transparency2;
    private int nightTimer;
    
    // the world this actor exists in
    private KDSAbove world; 
    
    // the image this actor has
    private GreenfootImage nightImage = new GreenfootImage("black.png");
    
    /**
     * Initialises two transparency variables, a timer, and initialises the object's image as a big black screen.
     */
    public Night() {
        // init transparency
        transparency = 1;
        transparency2 = 140;
        nightTimer = 0;
        this.setImage(nightImage);
        this.getImage().setTransparency(1);
    }
    
    /**
     * Act - Literally just fades in a giant black screen.
     */
    public void act()
    {
        // Transparency acts both as an act counter and the actual transparency value of the rectangle
        sunsetAndSunrise(); 
        if(transparency2 == 0){
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Sets the world field to the world it is added to.
     */
    public void addedToWorld(World w){
        world = (KDSAbove)w;
    }
    
    /**
     * Method responsible for facilitating the sunrise and sunset of the world and changes the black screen in order to do so.
     */
    public void sunsetAndSunrise(){
        //Sunset
        if (transparency < transparency2) { // transparency increments by 1 until 140, then stops incrementing
            transparency+=0.5;
            this.getImage().setTransparency((int)transparency);
        } 
        if(transparency == transparency2){
            //Wait a bit before sunrise
            nightTimer++;
            //Init sunrise
            if(nightTimer > world.getNightDuration()){
                if (transparency2 > 0){
                    transparency2-=0.5;
                    transparency-=0.5;
                    this.getImage().setTransparency((int)transparency2);
                }
            }
        }
    }
    

}
