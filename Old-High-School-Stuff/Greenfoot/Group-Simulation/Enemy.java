import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The Enemy abstract superclass controls all the entities in the game world that are not Villagers.
 * In brief, they provide an adversarial function against the Villagers, and are usually hostile or targettable by and towards Villagers.
 * Comes with its own sprite implementation, multiple helpful methods, and Villager targetting mechanisms.
 * 
 * @author Arthur Zeng and Michael Liu
 * @version 3.00
 */
public abstract class Enemy extends Entity
{
    // for villager targetting       
    protected int targetIndex;
    protected Villager target;
    
    // for targetted by villagers
    protected boolean targetOfArcher;
    protected ArrayList<Villager> archersInLOS;
    
    /**
     * The Enemy constructor inherits the Entity constructor and passes its parameters into it as well. 
     * It also sets the false default value for the variable that determines if the Enemy is being targetted by an archer (Woman).
     * 
     * @param maxSpeed      max speed of the spawned entity
     * @param maxHp         max health of the spawned entity
     * @param direction     either 1 (right), -1 (left), 2 (up), or -2 (down), determining the direction of the entity upon initial spawning
     */
    public Enemy(int maxSpeed, int maxHp, int direction){
        super(maxSpeed, maxHp, direction);
        targetOfArcher = false;
        archersInLOS = new ArrayList<Villager>();
    }
    
    /**
     * Override of the inherited die() method that also incremeents the static variable enemiesKilled for usage in stat tracking.
     */
    public void die() {
        super.die();
        world.addEnemiesKilled();
    }
    
    /**
     * A clone of the cycle method from Entity adapted for the Enemy's 4-sprite based animation cycle. As such, it only cycles through four sprites at a time, and only has four parameters
     * 
     * @param array     the array of GreenfootImages you want to cycle through
     * @param a         index of the first sprite you want to cycle
     * @param b         index of the second sprite you want to cycle
     * @param c         index of the third sprite you want to cycle
     * @param d         index of the fourth sprite you want to cycle
     */
    public void attackCycle(ArrayList<GreenfootImage> array, int a, int b, int c, int d) {
        attackTimer++;
        if(attackTimer == 0){
            this.setImage(array.get(a));
        }
        if(attackTimer == 8){
            this.setImage(array.get(b));
        }
        if(attackTimer == 16){
            this.setImage(array.get(c));
        }
        if(attackTimer == 24){   
            this.setImage(array.get(d));
        }
        if(attackTimer == 32){
            attackTimer = 0;
        }
    }
    
    /**
     * A modification of the movementAnimation() method from Entity, overridden here to match with the Enemy's cycle method, as well as their lack of sprites for moving up and down.
     */
    public void movementAnimation(){
        if (direction == -1) {
            cycle(spriteArray, 0,1,2,3);
        } 
        
        if (direction == 1) {
            cycle(invertedSpriteArray, 0,1,2,3);
        } 
        
        if (direction == 2) {
            cycle(invertedSpriteArray, 0,1,2,3);
        } 
        
        if (direction == -2) {
            cycle(spriteArray, 0,1,2,3);
        }
    }
    
    /**
     * A modification of the walk() method from Entity, overridden here to match with the Enemy's cycle method, as well as their lack of sprites for moving up and down.
     */
    public void walk(){
        if(direction == 1 || direction == -1){
            this.setRotation(0);
            move(speed*direction);
            movementAnimation();
        }
        else
        {
            if(direction == 2){
                setLocation(getX(), getY() - speed);
                movementAnimation();
            }
            else
            {
                if(direction == -2){
                    setLocation(getX(), getY() + speed);
                    movementAnimation();
                }
            }
        }
    }
    
    /**
     * A small recursive method that looks for targets, broadening its range until it finds a Villager in 200 unit increments of range.
     */
    public void findTarget(int range) {
        if (((ArrayList)((KDSAbove)getWorld()).getObjects(Villager.class)).size() > 0) {
            ArrayList<Villager> targets = (ArrayList) getObjectsInRange(range,Villager.class);
            if (targets.size() != 0) {
                target = targets.get(0);
            } else {
                findTarget(range+50);
            }
        }
        
    }
    
    /**
     * A small method that quickly finds any villager within a range of 2000 and designates that one as the 'target', for use in subclass behaviours.
     * The 'target' variable is used in a variety of different behaviours for the Enemy, but is more or less the one the Enemy tries to attack and seek out at most times.
     */
    public void findTarget() {
        int range = 100;
        ArrayList<Villager> targets = (ArrayList) getObjectsInRange(range,Villager.class);
        if (targets.size() != 0) {
            target = targets.get(0);
        } else {
            findTarget(range+100);
        }
    }
    
    /**
     * Enemy's act() method only calls super.act(), which in itself is in Entity, and calls the walk() method.
     */
    public void act()
    {   
        super.act();
    }
    
    /**
     * Mutator for archersInLOS, arraylist. Adds a parameter archer to that arraylist.
     * 
     * @param archer        a villager archer you want to add to the arraylist to count as "in line of sight"
     */
    public void addArcherInLOS(Villager archer) {
        archersInLOS.add(archer);
    }
    
    /**
     * Mutator for archersInLOS, arraylist. Removes a parameter archer from that arraylist, if it is contained within.
     * 
     * @param archer        a villager archer you want to remove from the arraylist to count as "in line of sight"
     */
    public void removeArcherInLOS(Villager archer) {
        if (archer != null && archersInLOS.contains(archer)) {
            archersInLOS.remove(archer);
        }
    }
    
    /**
     * Accessor for whether or not the parameter archer is contained in the "archersInLOS" arraylist, or if the parameter archer is in line of sight.
     * 
     * @return boolean      whether or not archersInLOS contains the parameter Villager archer
     */
    public boolean seenByArcher(Villager archer) {
        return (archersInLOS.contains(archer));
    }
    
}
