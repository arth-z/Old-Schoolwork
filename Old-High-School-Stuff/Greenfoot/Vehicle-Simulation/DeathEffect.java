import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A skull that fades away upon being spawned. Based on the Bug Simulation's death effect.
 * 
 * @author Arthur Zeng
 * @version 2.0
 */
public class DeathEffect extends Effect
{
    /**
     * Act - do whatever the DeathEffect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public DeathEffect() {
        super(30);
    }
    
    public void act()
    {
        // act fades away the skull as it is spawned
        this.getImage().setTransparency(getImage().getTransparency() - 1);
        if (this.getImage().getTransparency() < actCounter)
        {
            getWorld().removeObject(this);
        }
    }
}
