import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The DivineRetribution - a sarcastic implementation of divine punishment upon the VehicleWorld
 * for the wrathfulness of its inhabitants - aka, it spawns in after too many pedestrians are killed, and wipes out everything
 * on screen using the same graphic as most deaths in the VehicleWorld
 * 
 * @author (ArthurZeng) 
 * @version (a version number or a date)
 */
public class DivineRetribution extends Effect
{
    private ArrayList<SuperSmoothMover> allInWorld; // list of everything on world
    private ArrayList<Vehicle> vehiclesInWorld; // list of every vehicle in world

    private BlackScreen blackScreen; // black screen helper class to add to the visual impact of class being spawned in
                                     // literally just a big black screen that fades in
    private GreenfootSound ominousAudio; // a piano stinger that plays when this thing is spawned
    
    public DivineRetribution() {
        super(600); // call Effect constructor, last for 10 seconds
        
        // init our helper variables
        blackScreen = new BlackScreen();
        ominousAudio = new GreenfootSound("bluefeathershort.mp3");
    }
    
    public void addedToWorld(World w) { // when added to the world
        getWorld().addObject(blackScreen, 800, 600); // fade in the black screen
        ominousAudio.play(); // play the audio stinger
    }
    
    public void paused() {
        ominousAudio.pause(); // pause audio when world is paused
    }
    
    /**
     * Act - do whatever the DivineRetribution wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        allInWorld = (ArrayList) getWorld().getObjects(SuperSmoothMover.class); // get all the moving parts in the simulation
        vehiclesInWorld = (ArrayList) getWorld().getObjects(Vehicle.class);
        
        if (actCounter > 0) { // first if block: "idling" time for the actor (this lets the black screen fade in)
            actCounter--; // decrements 600 times using actCounter
            for (Vehicle v: vehiclesInWorld) {
                v.markForDeath();
            }
        } else { // once 10 seconds have passed (value of actCounter set by constructor)

            allInWorld = (ArrayList) getWorld().getObjects(SuperSmoothMover.class); // get all the moving parts in the simulation
            
            // call die method for all of them (yes, SuperSmoothMover was given a die() method, so vehicles can 'die')
            for (SuperSmoothMover actor: allInWorld) {
                actor.die();
            }
            
            // remove the black screen we faded in, as well as the self 
            getWorld().removeObject(blackScreen);
            getWorld().removeObject(this);
        }
    }
}
