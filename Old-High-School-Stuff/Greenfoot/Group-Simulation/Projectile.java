import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A projectile class used by Villager to deal damage to its enemies. Represents an arrow.
 * 
 * @author Arthur Zeng
 * @version 3.00
 */
public class Projectile extends SuperSmoothMover
{
    protected GreenfootImage arrowImage = new GreenfootImage("arrowReflected.png");
    protected Enemy target;
    
    /**
     * Initialises a target to seek from a parameter, and sets its image to an arrow.
     * 
     * @param target       the enemy this projectile should seek.
     */
    public Projectile(Enemy target) {
        this.target = target;
        this.setImage(arrowImage);
    }
    
    /**
     * Seeks the target, and removes itself from the world when the target is no longer in the world.
     */
    public void act()
    {
        if (target == null || target.getWorld() == null) {
            getWorld().removeObject(this);
        } else {
            attemptCollision();
        }
    }
    
    /**
     * Method to seek a target. Moves towards its target, and if it intersects it, the target takes 20 damage.
     */
    public void attemptCollision() {
        turnTowards((Actor)target);
        move(20);
        if (this.intersects(target)) {
            target.takeDamage(5);
            getWorld().removeObject(this);
        }
    }
}
