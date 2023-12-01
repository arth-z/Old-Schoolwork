import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A man who defends the village - a melee warrior.
 * Functionally, a skeleton implementation of the behavioural methods in Villager.
 * 
 * @author Arthur and Michael
 * @version 3.00
 */
public class Man extends Villager
{
    /**
     * The Man constructor inherits the Villager constructor and passes its parameters into it as well, thereby fulfilling the same purpose.
     * Besides that, it uses the initialiseSprites, addReflectedSprites, and fullInitialiseAttackSprites to initialise the sprite of the Man.
     * It also initialises the swordSwoosh sound effects that the Man uses in meleeBehaviour.
     * 
     * @param maxSpeed      max speed of the spawned entity
     * @param maxHp         max health of the spawned entity
     * @param direction     either 1 (right), -1 (left), 2 (up), or -2 (down), determining the direction of the entity upon initial spawning
     */
    private GreenfootSound swordSwoosh = new GreenfootSound("swordSwoosh.mp3");
    public Man(int maxSpeed, int maxHp, int direction){
        super(maxSpeed, maxHp, direction);
        
        initialiseSprites("man", 16);
        fullInitialiseAttackSprites("knight", 1, 8); 
        
        swordSwooshIndex = 0;
        swordSwooshSFX = new GreenfootSound[15];
        for(int i = 0; i < swordSwooshSFX.length; i++){
            swordSwooshSFX[i] = swordSwoosh;
        }
    }
    
    /**
     * Act - Perform the MeleeBehaviour. Check if you're dead, or hungry.
     */
    public void act()
    {   
        meleeBehaviour();
        deathCheck();
        hungerCheck();
    }
    
}
