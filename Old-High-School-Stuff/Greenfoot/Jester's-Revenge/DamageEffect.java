import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An extension of the Effect class, but this time serving as a facilitator of a damage/telegraph system in the grid.
 * Has a few extensions - unlike the Effect class, also has parameters controlling how quickly it fades in, and how long it stays opaque.
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class DamageEffect extends Effect
{
    protected boolean opaque;
    protected int opaqueDuration;
    protected boolean fadedIn;
    protected int opacityTimer;
    protected GreenfootImage sprite;
    protected JestersRevenge world;
    
    /**
     * Constructor for the DamageEffect. Initialises some timers and some other boolean variables to false as well, and initial transparency to 0.
     * 
     * @param fadeAway          number determining how quickly the damageEffect fades away, passed into super()
     * @param opaqueDuration    number determining how long the damage effect stays opaque (and damaging)
     * @param speed             how fast the damageEffect fades in to max opacity
     * @param sprite            the sprite/image of the damageEffect, passed into super()
     */    
    public DamageEffect(int fadeAway, int opaqueDuration, double speed, GreenfootImage sprite) {
        super(fadeAway, sprite);
        this.opaqueDuration = opaqueDuration;
        this.speed = speed;
        this.opaque = false;
        this.fadedIn = false;
        opacityTimer = 0;
        getImage().setTransparency(0);
    }
    
    /**
     * DamageEffect, when added to a world, will cast that as a JestersRevenge type and save it to an instance variable to get easy access to its methods
     * 
     * @param w         the world this DamageEffect is added to.
     */ 
    public void addedToWorld(World w){
        world = (JestersRevenge) w;
    }
    
    /**
     * DamageEffect's act method proceeds in three phases.
     * First, the image fades in based on its speed parameter.
     * Then, the image remains opaque until a timer exceeds its opaqueDuration.
     * Finally, then it calls super.act() in order to fade away.
     */ 
    public void act()
    {
        if (this.getImage().getTransparency() + speed < 255 && !fadedIn) {
            this.getImage().setTransparency((int)(this.getImage().getTransparency() + speed));
            opaque = false;
            fadedIn = false;
        } else {
            opaque = true;
            fadedIn = true;
            opacityTimer++;
            if (opacityTimer > opaqueDuration) {
                super.act();
            } else {
                this.getImage().setTransparency(255);
            }
        }
        if(world.isBlankActive()){
            world.removeObject(this);
        }
    }
    
}
