import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Pedestrian that tries to walk across the street. 
 * Dies when it runs out of health. Gets knocked down when touching an eligible vehicle.
 */
public abstract class Pedestrian extends SuperSmoothMover
{
    // movement variables
    protected double speed; 
    protected double maxSpeed;
    protected int direction;
    
    // health/death related variables
    protected boolean awake;
    protected int health; protected int maxHealth; 
    protected StatBar hpBar;
    protected GreenfootSound scream;
    
    public Pedestrian(int direction, int maxHealth) {
        // choose a random speed
        maxSpeed = Math.random() * 2 + 1;
        speed = maxSpeed;
        
        // start as awake 
        awake = true;
        
        this.direction = direction;
        
        // set health values
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        
        // create our helper statbar
        hpBar = new StatBar(maxHealth, this);
    }
    
    public void addedToWorld(World w) {
        // also add the actor's helper StatBar to the world when the actor gets added to the world
        w.addObject(hpBar, this.getX(), this.getY());
        hpBar.update(health);
    }
    
    /**
     * Act - do whatever the Pedestrian wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // If there is a vehicle in front of me, stop
        if (awake){
            if (getOneObjectAtOffset(0, (int)(direction * getImage().getHeight()/2 + (int)(direction * speed)), Vehicle.class) == null){
                setLocation (getX(), getY() + (int)(speed*direction));
            }
            if (direction == -1 && getY() < 100){
                getWorld().removeObject(this);
            } else if (direction == 1 && getY() > 550){
                getWorld().removeObject(this);
            }
        }
        
        // if health drops below 0...well die
        if (health < 0) {
            die();
        }
        
        // also just constantly update the health bar for consistency
        hpBar.update(health);
    }
    
    // calls SuperSmoothMover.die(), but two additions - increments death counter, plays the pedestrian death scream
    public void die() {
        ((VehicleWorld)getWorld()).incrementDeaths();
        scream.play();
        super.die();
    }
    
    /**
     * Method to cause this Pedestrian to become knocked down - stop moving, turn onto side
     * Also the method that damages them every frame a vehicle is over them. 
     * As such, HP values are set to some duration of frames (i.e. 60, or one second) the person can survive a vehicle 
     * (painting a somewhat gruesome picture)
     */
    public void knockDown() {
        // decrement health and stop me in my tracks
        health--;
        speed = 0;
        hpBar.update(health);
        
        if (isAwake()) { // if awake and knocked down, knock me down
            setRotation (90);
            awake = false;
        }
    }

    /**
     * Method to allow a downed Pedestrian to be healed
     */
    public void healMe () {
        // get me moving and upright again
        speed = maxSpeed;
        setRotation (0);
        awake = true;
        
        // restore my hp to full
        health = maxHealth;
        hpBar.update(health);
    }
    
    public boolean isAwake () {
        return awake;
    }
    
    public int getHealth() {
        return health;
    }
}
