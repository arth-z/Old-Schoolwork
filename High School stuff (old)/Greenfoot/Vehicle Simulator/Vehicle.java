import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootSound;

/**
 * This is the superclass for Vehicles.
 * Additions: guiltyness, a stopThenRemove method (with assosciated pedTimer)
 * 
 */
public abstract class Vehicle extends SuperSmoothMover
{
    // movement/spawning variables
    protected double maxSpeed;
    protected double speed;
    protected int direction; // 1 = right, -1 = left
    protected int yOffset;
    protected VehicleSpawner origin;
    protected boolean canLaneChange;
    
    // for method stopThenRemove
    protected int pedTimer;
    
    // for police
    protected boolean guilty;
    
    // for DivineRetribution
    protected boolean markedForDeath;
    
    // meow is for the car and cyclists, generalUseSound is...well, a general use sound that any vehicle can use
    protected GreenfootSound generalUseSound;
    protected GreenfootSound meow;
    
    protected abstract boolean checkHitPedestrian ();

    public Vehicle (VehicleSpawner origin) {    
        // init variables
        this.origin = origin; // pass as parameter
        guilty = false; // always start as unguilty
        pedTimer = 0; // prepare pedTimer to work
        
        // determine direction from origin
        if (origin.facesRightward()){
            direction = 1;
        } else {
            direction = -1;
            getImage().mirrorHorizontally();
        }
        
    }
    
    // initialise the meow sound effect (used by cyclists and cars), usually called in constructors
    public void initMeow() {
        meow = new GreenfootSound("meow.wav");
        meow.setVolume(60);
    }
    
    public void act() {
        // mostly used for super.act() in vehicle subclasses so i dont have to copypaste this like 20 times
        if (checkEdge()){
            getWorld().removeObject(this);
        }
    }
    
    public void addedToWorld (World w){ // when added in the world, spawn from origin vehicle spawner
        setLocation (origin.getX() - (direction * 100), origin.getY() - yOffset);
    }

    /**
     * A method used by all Vehicles to check if they are at the edge
     */
    protected boolean checkEdge() {
        if (direction == 1){
            if (getX() > getWorld().getWidth() + 200){
                return true;
            }
        } else {
            if (getX() < -200){
                return true;
            }
        }
        return false;
    }

    /**
     * Method that deals with movement. Speed can be set by individual subclasses in their constructors
     */
    public void drive() 
    {
        // Ahead is a generic vehicle - we don't know what type BUT
        // since every Vehicle "promises" to have a getSpeed() method,
        // we can call that on any vehicle to find out it's speed        
        if (markedForDeath) { // if divine retribution is active
            if (speed > 0.1) { // decrement speed until lower than 0.1
                speed -= 0.025;
            }
        } else {
            speed = maxSpeed;
            if (canLaneChange) { // lane change called separately since vehicle speed is handled in here
                laneChange(54);
            } else {
                Vehicle front = (Vehicle)getOneObjectAtOffset((int)speed + (this.getImage().getWidth()/2 + 4)*direction, 0, Vehicle.class);
                if (front != null) {
                    speed = front.getSpeed();
                }
            }
        }
            
        move (speed * direction);
    }   

    public void stopThenRemove(Class<?> victimType) { // parameter is any class we want the vehicle to remove
        pedTimer++; // increment pedTimer (called during act cycles)
        speed = 0; // stop
        if (pedTimer > 60) { // after about a second
            pedTimer = 0; // reset the timer
            this.removeTouching(victimType); // remove
        }
    }
    
    public void laneChange(int laneDistance){
        int imageWidth = getImage().getWidth();
        int imageHeight = getImage().getHeight();
        
        // can the vehicle change lanes by going up, without going into a lane that opposes motion/off the road? or, is this on any of the lanes that cant move up?
        boolean blockedAbove = this.getY() == 252 || this.getY() == 414;
        
        // can the vehicle change lanes by going down, without going into a lane that has opposite motion/off the road? or, is this on any of the lanes that cant move down?
        boolean blockedBelow = this.getY() == 360 || this.getY() == 522;
        
        // information above was gathered from lane data in vehicleworld - these are the top and bottom edges of lanes
        
        // grab three points above, below, and in front of me
        Vehicle front = (Vehicle)getOneObjectAtOffset((int)speed + (getImage().getWidth()/2 + 4)*direction, 0, Vehicle.class);
        
        if (front != null) { // if a vehicle is in front
            
            // create car dummies based on vehicle position
            CollisionHelper top = new CollisionHelper();
            getWorld().addObject(top, getX() + ((int)speed - (getImage().getWidth()))*direction, getY() - imageHeight - 60);
            CollisionHelper bottom = new CollisionHelper();
            getWorld().addObject(bottom, getX() + ((int)speed - (getImage().getWidth()))*direction, getY() + imageHeight + 60);
            
            if(!top.helperIsTouching(Vehicle.class) && !blockedAbove) { // if no vehicles are above and a lane exists above
                this.setRotation(270); // look up
                move(laneDistance); // move to next lane
                this.setRotation(0); // rotate back to neutral position
            } else if (!bottom.helperIsTouching(Vehicle.class) && !blockedBelow){ // if no vehicles are below, and a lane exists below
                this.setRotation(90); // look down
                move(laneDistance); // move
                this.setRotation(0); // look straight
            } else { // otherwise, if you're sandwiched
                speed = front.getSpeed(); // just slow down
            }
            // clean up
            getWorld().removeObject(top);
            getWorld().removeObject(bottom);
        } else {
            speed = maxSpeed;
        }
    }

    
    public void beGuilty() { // public setters let's go
        guilty = true;
    }
    
    public boolean isGuilty() { // public getters let's go
        return guilty;
    }
    
    /**
     * An accessor that can be used to get this Vehicle's speed. Used, for example, when a vehicle wants to see
     * if a faster vehicle is ahead in the lane.
     */
    public double getSpeed(){
        return speed;
    }
    
    public void markForDeath() { // a mutator used to set speed for DivineRetribution
        markedForDeath = true;
    }
    
}
