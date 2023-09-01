import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A small class for an arrow shot by an Archer, except it's invisible and is used for line of sight checks for Archer.
 * 
 * @author Arthur Zeng
 * @version 3.00
 */
public class InvisProjectile extends Projectile
{    
    
    private Villager owner;
    
    /**
     * The constructor for the InvisProjectile inherits from its superclass, so it picks a target to seek.
     * It also sets the transparency of itself to 0.
     * 
     * @param target       the enemy this projectile should seek.
     * @param owner        the original villager who shot the arrow.
     */
    public InvisProjectile(Enemy target, Villager owner) {
        super(target);
        getImage().setTransparency(0);
        this.owner = owner;
    }
    
    /**
     * Inherits from super.act(), so it seeks its target.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * The attemptCollision() method is overriden (called in act method) by a method that tries to seek its target.
     * If it collides into a building on the way, the owner of the arrow is removed from the target's "in LOS" arraylist (meaning they don't see each other).
     * Otherwise, if the target is tagged by this invisible arrow, the owner is added to the target's "in LOS" arraylist (meaning they DO see each other)
     */
    public void attemptCollision() {
        turnTowards((Actor)target);
        move(30);
        if (this.isTouching(Building.class)) {
            target.removeArcherInLOS(owner);
            getWorld().removeObject(this);
        } else if (this.intersects(target)) {
            target.addArcherInLOS(owner);
            getWorld().removeObject(this);
        }
        
        if (target == null || owner == null) {
            getWorld().removeObject(this);
        }
    }
}
