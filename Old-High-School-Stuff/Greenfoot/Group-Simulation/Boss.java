import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A large enemy that walks up to Villagers and does slam attacks. Tries to avoid buildings.
 * 
 * @author Arthur Zeng, Michael Liu, Steven Lu
 * @version 2.0
 */
public class Boss extends Enemy
{
    // variables for slam attacks - sfx
    private GreenfootSound[] slamAttackSFX;
    private int slamAttackIndex;
    
    /**
     * The Boss constructor inherits the Enemy constructor and passes its parameters into it as well, thereby fulfilling the same purpose.
     * Besides that, it also initialises the sound array for the enemy as well as its sprites, using the initialiseSprites, addReflectedSprites, and fullInitialiseAttackSprites methods.
     * 
     * @param maxSpeed      max speed of the spawned entity
     * @param maxHp         max health of the spawned entity
     * @param direction     either 1 (right), -1 (left), 2 (up), or -2 (down), determining the direction of the entity upon initial spawning
     */
    public Boss(int maxSpeed, int maxHp, int direction){
        // call super
        super(maxSpeed, maxHp, direction);
        
        // initialise slam noises
        slamAttackIndex = 0;
        slamAttackSFX = new GreenfootSound[15];
        for(int i = 0; i < slamAttackSFX.length; i++){
            slamAttackSFX[i] = new GreenfootSound("slamSFX.wav");
        }
        
        // initialise sprites
        initialiseSprites("boss", 4);
        addReflectedSprites("boss", 4);
        fullInitialiseAttackSprites("boss", 5, 8);
    }
    
    /**
     * Seeks targets if an existing target does not exist. If the target exists, orient towards it.
     * If there is a target, and it is not touching one of its own slam attack AOEs, it walks towards the target until it reaches a specified range
     * (the range of its slam attack AOE). Then it stops, and executes a slam attack, playing a corresponding sound.
     * The slam attack has an AOE and deals damage to anything caught within it. Until the slam attack AOE has faded away or the boss moves out of it,
     * it cannot do another slam attack (to prevent lag from repeatedly spamming slam attacks and taking up memory)
     * Does death checks and collision checks consistently as well.
     */
    public void act()
    {
        // if there is no target in the world, find a target
        if (target == null || target.getWorld() == null) {
            findTarget();
        } else {
            orientTowards(target);
        }
        
        if (this.isTouching(SlamAttackAOE.class)) { // if touching your own AOE
            super.act(); // just walk
            checkBuilding();
        } else {
            ArrayList<Villager> villagersInRange = (ArrayList) getObjectsInRange(100, Villager.class); // check villagers in range of slam
            if (villagersInRange.size() != 0) { // if yes
                speed = 0; // stop
                
                // cycle through attack sprites based on direction
                if (direction == 1) {
                    attackCycle(invertedSpriteAttackArray, 0,1,2,3);
                } else if (direction == -1) {
                    attackCycle(spriteAttackArray, 0,1,2,3);
                } else {
                    attackCycle(spriteAttackArray, 0,1,2,3);
                }
                
                // increment a timer - when the timer reaches 24, all the attack animations should have played.
                damageTimer++;
                
                if(damageTimer > 24) { // once the animation cycle is complete
                    // play a noise
                    slamAttackSFX[slamAttackIndex].play();
                    slamAttackSFX[slamAttackIndex].setVolume(70);
                    slamAttackIndex++;
                    if(slamAttackIndex > slamAttackSFX.length - 1){
                        slamAttackIndex = 0;
                    }
                    
                    // create a new SlamAttackAOE at your position, visual effect
                    getWorld().addObject(new SlamAttackAOE(), getX(), getY());
                    // whoever's still in range takes 20 damage
                    for(Villager villager: villagersInRange) {
                        villager.takeDamage(20);
                    }
                    
                    // reset timer, go at max speed
                    damageTimer = 0;
                    speed = maxSpeed;
                }
                
            } else {
                speed = maxSpeed;
                super.act(); // walk
            }
        }
        
        // check if you're dead
        deathCheck();
        
    }
}
