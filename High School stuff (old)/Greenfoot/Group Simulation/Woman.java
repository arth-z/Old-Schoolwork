import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A female ranged attacker.
 * Functionally, a skeleton implementation of methods in Villager.
 * 
 * @author Arthur Zeng, Michael Liu
 * @version 3.00
 */
public class Woman extends Villager
{   
    /**
     * The Woman constructor inherits the Villager constructor and passes its parameters into it as well, thereby fulfilling the same purpose.
     * Besides that, it uses the initialiseSprites, addReflectedSprites, and fullInitialiseAttackSprites to initialise the sprite of the Woman.
     * It also initialises the bowSwoosh sound effects that the Woman uses in meleeBehaviour.
     * 
     * @param maxSpeed      max speed of the spawned entity
     * @param maxHp         max health of the spawned entity
     * @param direction     either 1 (right), -1 (left), 2 (up), or -2 (down), determining the direction of the entity upon initial spawning
     */    
    public Woman(int maxSpeed, int maxHp, int direction){
        super(maxSpeed, maxHp, direction);
        
        initialiseSprites("woman", 16);
        fullInitialiseAttackSprites("archer", 1, 8);
        
        bowSwooshIndex = 0;
        bowSwooshSFX = new GreenfootSound[15];
        for(int i = 0; i < bowSwooshSFX.length; i++){
            bowSwooshSFX[i] = new GreenfootSound("bowSwoosh.wav");
        }
    }
    
    /**
     * Act - Perform the RangedBehaviour. Check if you're dead, or hungry.
     */
    public void act()
    {
        rangedBehaviour();
        deathCheck();
        hungerCheck();
    }
    
}
