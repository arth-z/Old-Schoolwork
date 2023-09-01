import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class used by the Bosses to attack specific Cells, doubling also as a telegraph for the player to react to before the attack heads their way proper.
 * For visual fidelity, it typically physically appears from the boss it's assigned to seek out the tile it's targetting before remaining there
 * for its remaining lifespan, then spawning in a damageEffect as it disappears. As such, it inherits from SuperSmoothMover
 * 
 * @author Arthur Zeng
 * @version 2.00
 */
public class TelegraphIndicator extends SuperSmoothMover
{
    protected GifImage gifImage;
    protected Actor boss;
    protected Actor targetCell;
    protected int speed;
    protected int maxSpeed;
    protected int duration;
    protected int timer;
    protected int timeInWorld;
    protected DamageEffect damageEffect;
    protected int transparency;
    
    /**
     * The constructor for the TelegraphIndicator. Initialises multiple other variables, such as its own initial GifImage sprite, and its timers
     * (one for self deletion if it gets stuck, one for deletion after it's finished it's job)
     * 
     * @param boss          the boss this TelegraphIndicator will "emerge", or spawn, from
     * @param targetCell    the cell this TelegraphIndicator will attempt to seek after it's been spawned
     * @param maxSpeed      max speed of the TelegraphIndicator
     * @param duration      how long this TelegraphIndicator will remain on its target cell after seeking it before disappearing and spawning its damageEffect
     */
    public TelegraphIndicator(Actor boss, Actor targetCell, int maxSpeed, int duration) {
        this.boss = boss;
        this.targetCell = targetCell;
        gifImage = new GifImage("reticle.gif");
        timer = 0;
        transparency = 55;
        this.duration = duration;
        this.maxSpeed = maxSpeed;
        this.speed = maxSpeed;
        timeInWorld = 0;
    }
    
    /**
     * An "auxiliary constructor", or a method that serves as a secondary constructor for another object.
     * In this case, this method serves as the init method for the TelegraphIndicator's corresponding damageEffect it spawns at the end of its lifetime.
     * The parameters passed in here mirror the parameters passed into the DamageEffect constructor.
     * 
     * @param fadeAway          number determining how quickly the damageEffect fades away, passed into super()
     * @param opaqueDuration    number determining how long the damage effect stays opaque (and damaging)
     * @param speed             how fast the damageEffect fades in to max opacity
     * @param sprite            the sprite/image of the damageEffect, passed into super()
     */    
    public void initNextAttackEffect(int fadeAway, int opaqueDuration, double speed, GreenfootImage sprite) {
        damageEffect = new DamageEffect(fadeAway, opaqueDuration, speed, sprite);
    }
    
    /**
     * An override of Greenfoot's addedToWorld() method, called whenever the object is added into the world.
     * Here, it sets its initial coordinates to the same as its boss instance variable (passed into constructor)
     * 
     * @param w         the world the object is added into
     */  
    public void addedToWorld(World w) {
        setLocation(boss.getX(), boss.getY());
    }
    
    /**
     * TelegraphIndicator's act method continously sets its current image and transparency from its GifImage (since that's how that class works).
     * It also makes it turn towards its target cell and move at maxSpeed towards it if it's not already touching it.
     * If it IS touching it, then it sets its location to its target's X and Y coordinates in the world (plus some offset to make it look nicer), 
     * resets its rotation to 0, and remains there until a timer exceeds its duration variable - then it disappears, but not without leaving a damageEffect
     * (defined by the initNextAttackEffect() method). There is also a failsafe - if for some reason the TelegraphIndicator can't intersect its target in
     * 180 acts, it self-deletes.
     */
    public void act()
    {
        timeInWorld++;
        setImage(gifImage.getCurrentImage());
        getImage().setTransparency(175);
        
        if (this.intersects(targetCell)) {
            this.setLocation(targetCell.getX(), targetCell.getY() - 20);
            setRotation(0);
            timer++; timer++;
            speed = 0;
            if (timer > duration) {
                getWorld().addObject(damageEffect, targetCell.getX(), targetCell.getY());
                getWorld().removeObject(this);
            }
        } else {
            turnTowards(targetCell);
            move(speed);
            speed = maxSpeed;
        }
        
        if (timeInWorld > 180 && !(getX() == targetCell.getX() && getY() == targetCell.getY())) {
            getWorld().removeObject(this);
        }
    }
}
