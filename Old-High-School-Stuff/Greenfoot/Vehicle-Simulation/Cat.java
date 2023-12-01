import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A cat. Less health than a Person, can get picked up by other vehicles, gets run over by different ones.
 */
public class Cat extends Pedestrian
{
    
    
    public Cat(int direction) {
        super(direction, 40); // super with health of 40 (can survive with 2/3rds of a second)
        
        // init sound
        scream = new GreenfootSound("yowl.wav");
        scream.setVolume(75);
    }
    
    /**
     * Act - do whatever the Cat wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // act is a clone of Pedestrian
        super.act();
    }
    
    // original overloaded die method due to different DeathEffect neded to spawn
    public void die() {
        scream.play();
        ((VehicleWorld)getWorld()).incrementDeaths();
        getWorld().addObject(new CatDeathEffect(), this.getX(), this.getY());
        getWorld().removeObject(this);
    }

}
