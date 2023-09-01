import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A basic skeleton enemy that walks up to villagers, moving around buildings, and tries to punch them.
 * 
 * @author Arthur Zeng, Steven Lu, and Michael Liu
 * @version 3.00
 */
public class Fodder extends Enemy
{   
    private GreenfootSound[] fodderSFX;
    private int fodderSFXIndex;
    private int fodderTimer;
    
    /**
     * The Fodder constructor inherits the Enemy constructor and passes its parameters into it as well.
     * Besides that, it also initialises the sound array for the enemy as well as its sprites, using the initialiseSprites, addReflectedSprites, and fullInitialiseAttackSprites method.
     * 
     * @param maxSpeed      max speed of the spawned entity
     * @param maxHp         max health of the spawned entity
     * @param direction     either 1 (right), -1 (left), 2 (up), or -2 (down), determining the direction of the entity upon initial spawning
     */
    public Fodder(int maxSpeed, int maxHp, int direction) {
        // call super
        super(maxSpeed, maxHp, direction);
        
        // initialise your sound
        fodderSFXIndex = 0;
        fodderSFX = new GreenfootSound[15];
        for(int i = 0; i < fodderSFX.length; i++){
            fodderSFX[i] = new GreenfootSound("skeletonSFX.wav");
        }
        
        // initialise your sprite
        initialiseSprites("skeleton", 4);
        addReflectedSprites("skeleton", 4);
        fullInitialiseAttackSprites("skeleton", 5, 8);
    }
    
    /**
     * Seeks targets if an existing target does not exist. If the target exists, orient towards it.
     * If there is a target, it walks towards the target until it reaches a specified range, or when they touch
     * Then it stops, and attacks it, cycling through an animation as it does so.
     * Plays a skeleton sound effect when it attacks.
     * Does death checks and collision checks consistently as well.
     */
    public void act()
    {
        // if there is no target in the world, find a target
        if (target == null || target.getWorld() == null) {
            findTarget(); 
        } else {
            orientTowards(target); // otherwise turn towards them
            if (this.intersects(target)) { // and if you touch them
                fodderTimer++;
                
                // cycle through attack sprites (should take 24 acts, roughly)
                if (direction == 1) {
                    attackCycle(invertedSpriteAttackArray, 0,1,2,3);
                } else if (direction == -1) {
                    attackCycle(spriteAttackArray, 0,1,2,3);
                } else {
                    attackCycle(spriteAttackArray, 0,1,2,3);
                }
                
                // once the 24 acts are up, as indicated by fodderTimer, play the sound
                if(fodderTimer > 24){
                    fodderSFX[fodderSFXIndex].play();
                    fodderSFX[fodderSFXIndex].setVolume(80);
                    fodderSFXIndex++;
                    if(fodderSFXIndex > fodderSFX.length - 1){
                        fodderSFXIndex = 0;
                    }
                    fodderTimer = 0;
                }
                
                // also once the 24 acts are up, damage the enemy - for the duration of the acts as well, stop and deal damage.
                stopThenDamage(Villager.class, 24, 7);
            } else {
                speed = maxSpeed;
                super.act(); // if you're not touching your target, walk towards them until you do
            }
        }
        
        // check if you're dead
        deathCheck();
    }
}
