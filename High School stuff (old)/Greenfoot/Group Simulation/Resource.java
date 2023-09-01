import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A wild animal that exists to be killed and provide Food for villagers.
 * 
 * @author Arthur Zeng, Steven Lu, Michael Liu
 * @version 2.00
 */
public class Resource extends Enemy
{
    private int movementTimer;
    private GreenfootSound mooSFX;
    private GreenfootSound oinkSFX;
    /**
     * The Resource constructor inherits the Enemy constructor and passes its parameters into it as well.
     * Besides that, it also initialises the sound array for the enemy as well as its sprites, using the initialiseSprites and addReflectedSprites methods.
     * It also randomly determines whether or not the spawned resource will be a cow or pig, and initialises the sound values.
     * 
     * @param maxSpeed      max speed of the spawned entity
     * @param maxHp         max health of the spawned entity
     * @param direction    either 1 (right), -1 (left), 2 (up), or -2 (down), determining the direction of the entity upon initial spawning
     */
    public Resource(int maxSpeed, int maxHp, int direction) {
        // call entity constructor
        super(maxSpeed, maxHp, direction);
        
        // randomly determine if cow or pig
        String type = Greenfoot.getRandomNumber(2) == 0 ? "cow": "pig";
        
        // init sound variables
        mooSFX = new GreenfootSound("mooSFX.wav");
        oinkSFX = new GreenfootSound("oinkSFX.wav");
        mooSFX.setVolume(30);
        oinkSFX.setVolume(30);
        
        // init sprites for cow/pig
        initialiseSprites(type, 4);
        addReflectedSprites(type, 4);
        movementTimer = 0;
    }
    
    /**
     * Override for the die() method inherited from Entity. This is because it serves different functions:
     *  - Spawns a food effect instead of a death effect
     *  - increments the food counter
     */
    public void die() {
        getWorld().addObject(new FoodDeathEffect(60), this.getX(), this.getY());
        ((KDSAbove)getWorld()).incrementFoodCounter(400 * world.getResourceGainMultiplier() );
        getWorld().removeObject(this);
    }
    
    /**
     * Play noise, and orient towards the invisible ring of roads around the perimeter of the village to prevent wandering off.
     * That ring is the same ring the villagers orient towards at night in their defensive behavioural loops.
     */
    public void act()
    {
        resourceSounds();
        
        // orient towards ring outside village
        if (!this.isTouching(InvisRoad.class)) {
            Actor invisRoad = (Actor) ((ArrayList) getObjectsInRange(1000, InvisRoad.class)).get(0);
            orientTowards(invisRoad);
        }
        
        // stop if you touch a villager
        if (this.isTouching(Villager.class)) {
            speed = 0;
        } else {
            speed = maxSpeed;
        }
        
        // walk around walls and walk in general
        checkCollision();
        super.act();
        
        // check if you're dead
        deathCheck();
    }
    
    /**
     * Method to facilitate the playing of sounds.
     * Rolls a 1/2000 to play either a moo or an oink.
     */
    public void resourceSounds(){
        if(Greenfoot.getRandomNumber(2000) == 0){
            if(Greenfoot.getRandomNumber(2) == 0){
                mooSFX.play();
                mooSFX.setVolume(20);
            } else {
                oinkSFX.play();
                oinkSFX.setVolume(20);
            }
        }
    }
}
