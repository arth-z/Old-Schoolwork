import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Effect extends Actor
{
    protected int totalActs, actCounter;
    protected GreenfootImage image;
    
    public Effect (int totalActs){
        this.totalActs = totalActs;
        actCounter = totalActs;
    }
    
    /**
     * Act - do whatever the Effect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (actCounter > 0){
            actCounter--;
            if (actCounter < 60){ // last second
                image.setTransparency (actCounter * 2);
            }
        } else {
            // turn off weather effects on Vehicle?
            
        }
    }
}