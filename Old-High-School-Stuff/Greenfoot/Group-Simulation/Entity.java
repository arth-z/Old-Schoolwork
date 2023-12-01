    import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.lang.Math;

/**
 * The abstract superclass for the moving entities in the world.
 * As an abstract superclass, it contains many different methods that are useful in its subclasses, but its act()
 * method is comparatively barebones. 
 * 
 * All entities namely have:
 *  - Health (and a corresponding bar)
 *  - Velocity (direction and speed)
 *  - Sprites, or multiple images as opposed to one fixed one.
 *  
 * Additional functionalities and implementations are included in the subclasses.
 * 
 * @author Arthur Zeng and Michael Liu
 * @version Final
 */
public abstract class Entity extends SuperSmoothMover
{
    // movement variables
    protected double speed; 
    protected double maxSpeed;
    protected int direction;
    
    // health/death related variables
    protected double hp; protected double maxHp;
    protected StatBar hpBar;
    protected GreenfootSound scream;
    
    // various helpful timers
    protected int timer;
    protected int damageTimer;
    protected int attackTimer;
    protected int checkTimer;
    protected int chaseTimer;

    protected KDSAbove world;
    // spriting variables
    protected GreenfootImage currentImage;
    
    // useful variables for determining which sprite sheet is used
    protected ArrayList<GreenfootImage> spriteArray;
    protected ArrayList<GreenfootImage> invertedSpriteArray;
    protected ArrayList<GreenfootImage> spriteAttackArray;
    protected ArrayList<GreenfootImage> invertedSpriteAttackArray;
    
    protected int imageWidth = getImage().getWidth()/2;
    protected int imageHeight = getImage().getHeight()/2;
    
    
    
    
    /**
     * The constructor for the Entity class mostly facilitates the intialisation of the classes' many fields.
     * Timers are set to 0 (checkTimer to 5), corresponding parameters are set to the appropriate field, and the statBar is intialised.
     * 
     * @param speed         the current and max speed of the Entity to be initialised.
     * @param maxHP         the maximum health of the Entity.
     * @param direction     Either 1 (right), -1 (left), 2 (up), or -2 (down), determining the direction of the entity upon initial spawning
     */
    public Entity(int speed, int maxHp, int direction) {
        // set speed values
        this.maxSpeed = speed;
        this.speed = speed;
        // set direction
        this.direction = direction;
                
        // set health values
        this.hp = maxHp;
        this.maxHp = maxHp;
        
        // create the helper statBar
        hpBar = new StatBar(maxHp, this);
        
        // intialise our timers
        timer = 0;
        damageTimer = 0;
        attackTimer = 0;
        checkTimer = 5;
    }
    
    /**
     * Called when the object is added to the world. Adds the actor's helper StatBar to the world when the actor is added.
     */
    public void addedToWorld(World w) {
        // also add the actor's helper StatBar to the world when the actor gets added to the world
        world = (KDSAbove) w;
        w.addObject(hpBar, this.getX(), this.getY());
        hpBar.update((int)hp);
    }
    
    /**
     * A useful, "constructor-type" method to initialise sprites.
     * Helps with initialising sprites, as long as naming convention is consistent in the images folder.
     * Iterates through the file system, consecutively and sequentially, based on the sheetSize parameter.
     * Name your sprites as thing#.png - for example, if I want to initialise the skeleton sprites, I would name each sprite "skeleton1.png" to, say, "skeleton8.png"
     * 
     * @param type          the prefix to the sprite name you want (i.e: in a sprite name "man6.jpg", "man" should be passed as type)
     * @param sheetSize     how many sprites you want your Entity to act through - the size of the spriteArray.
     */
    public void initialiseSprites(String type, int sheetSize) {
        // save parameters into field
        spriteArray = new ArrayList<GreenfootImage>(sheetSize);
        
        // iterate through the file system, add GreenfootImages to our spriteArray, named appropriately
        for(int i = 1; i<=sheetSize; i++){
            GreenfootImage sprite = new GreenfootImage(type + i + ".png");
            spriteArray.add(sprite);
        }
    }
    
    /**
     * Checks if the Entity's forward movement causes them to collide into any Buildings, returns true if it does, false if it doesn't.
     * 
     */
    public boolean validPathForward() {
        // checks if the Entity's forward movement causes them to collide into any Buildings, returns true if it does, false if it doesn't
        Building a = (Building)getOneObjectAtOffset(10*direction, 0, Building.class);
        Building d = (Building)getOneObjectAtOffset(0, -(5*direction), Building.class);
        
        if (direction == -1 || direction == 1) {
            return a == null;
        } else {
            return d == null;
        }
    }
    
    /**
     * A useful, "constructor-type" method to initialise inverted sprites.
     * Operates more or less the exact same as the other initialiseSprites method, but inverts the images and adds it to invertedSpriteArray.
     * Iterates through the file system, consecutively and sequentially, based on the sheetSize parameter.
     * Name your sprites as thing#.png - for example, if I want to initialise the skeleton sprites, I would name each sprite "skeleton1.png" to, say, "skeleton8.png"
     * 
     * @param type          the prefix to the sprite name you want (i.e: in a sprite name "man6.jpg", "man" should be passed as type)
     * @param sheetSize     how many sprites you want your Entity to act through - the size of the spriteArray.
     */
    public void addReflectedSprites(String type, int sheetSize) {
        // initialise field arrayList
        invertedSpriteArray = new ArrayList<GreenfootImage>(sheetSize);
        
        for(int i = 1; i<=sheetSize; i++){ // works just like how it does in initialiseSprites, but 
            GreenfootImage sprite = new GreenfootImage(type + i + ".png");
            sprite.mirrorHorizontally();
            invertedSpriteArray.add(sprite);
        }
    }
    
    /**
     * Another useful, "constructor-type" method to initialise an entirely new array of sprites meant to play an attack animation.
     * It combines both initialiseSprites and addReflectedSprites into one method to initialise both straight and inverted attack sprites.
     * Operates slightly differently than the former two methods, as clarified in parameters.
     * If I wanted to call this to initialise the attack sprites of the Man, and said sprites were sprites "man6.png" to "man10.png", I would call it like: "fullInitialiseAttackSprites("man", 6, 10);"
     * 
     * @param type          the prefix to the sprite name you want (i.e: in a sprite name "man6.jpg", "man" should be passed as type)
     * @param sheetStart    the number in the file system of the sprite that signifies the first sprite of a full attack cycle
     * @param sheetEnd      the number in the file system of the sprite that signifies the final sprite of a full attack cycle.
     */
    public void fullInitialiseAttackSprites(String type, int sheetStart, int sheetEnd) {
        spriteAttackArray = new ArrayList<GreenfootImage>(sheetEnd - sheetStart + 1);
        for(int i = sheetStart; i <= sheetEnd; i++) {
            spriteAttackArray.add(new GreenfootImage(type + i + ".png"));
        }
        
        invertedSpriteAttackArray = new ArrayList<GreenfootImage>(sheetEnd - sheetStart + 1);
        for(int i = sheetStart; i <= sheetEnd; i++) {
            GreenfootImage image = new GreenfootImage(type + i + ".png");
            image.mirrorHorizontally();
            invertedSpriteAttackArray.add(image);
        }
    }
    
    /**
     * A method meant to facilitate the cycling, or drawing, of movement sprites, depending on the entity's direction.
     */
    public void movementAnimation(){
        if(direction == 1){
            cycle(8, 9, 10 ,11);
        }
        
        if(direction == -1){
            cycle(4, 5, 6, 7);
        }
        
        if(direction == 2 && this.getRotation() == 270){
            cycle(12, 13, 14, 15);
        }
        
        if(direction == -2 && this.getRotation() == 90){
            cycle(0, 1, 2, 3);
        }
    }

    /**
     * A method meant to "cycle" through a section of an array of sprites, and draw them upon the Entity every 8 acts.
     * 
     * @param array     the array of images you wish to cycle through
     * @param a,b,c,d,e four sequential indices of images from array you would wish to cycle
     */
    public void cycle(ArrayList<GreenfootImage> array, int a, int b, int c, int d){
        timer ++;
        if(timer == 0){
            this.setImage(array.get(a));
        }
        if(timer == 8){
            this.setImage(array.get(b));
        }
        if(timer == 16){
            this.setImage(array.get(c));
        }
        if(timer == 24){   
            this.setImage(array.get(d));
        }
        if(timer == 32){
           timer = 0;
        }    
        
    }
    
    /**
     * A method meant to "cycle" through a section of an array of sprites, and draw them upon the Entity every 8 acts.
     * Overriden to cycle through spriteArray specifically.
     * 
     * @param a,b,c,d,e four sequential indices of images from spriteArray you would wish to cycle
     */
    public void cycle(int a, int b, int c, int d) {
        cycle(spriteArray, a, b, c, d);
    }
    
    /**
     * Act - essentially, enable the movement of entities by calling walk(). This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        checkCollision();
        walk();
    }
    
    /**
     * A method meant to be called in act(), which checks if the entity's health is lower or equal to zero, and kills them if it is.
     */
    public void deathCheck() {
        // health check - if health reaches zero, die
        hpBar.update((int)hp);
        if (hp <= 0) {
            die();
        }
    }
    
    /**
     * A timer-based implementation of checkBuilding(), executing every 5 acts.
     */
    public void checkCollision(){
        checkTimer--;
        if(checkTimer == 0){
            checkBuilding();
            checkTimer = 5;
        }
        
    }
    
    /**
     * A method that facilitates the avoidance of Buildings. Changes the entity's direction when it bumps into a Building, 
     * keeping in mind other Buildings nearby, or corners.
     */
    public void checkBuilding(){
        Building a = (Building)getOneObjectAtOffset(10*direction, 0, Building.class);
        Building b = (Building)getOneObjectAtOffset( (imageWidth/2)*direction, -(imageHeight/2*direction), Building.class);
        Building c = (Building)getOneObjectAtOffset( (imageWidth/2)*direction,  (imageHeight/2*direction), Building.class);
        
        Building d = (Building)getOneObjectAtOffset(0, -(5*direction), Building.class);
        Building e = (Building)getOneObjectAtOffset( -(imageWidth/2)*direction, imageHeight/2*direction, Building.class);
        Building f = (Building)getOneObjectAtOffset(  (imageWidth/2)*direction, imageHeight/2*direction, Building.class);
        
        
        if(this.direction == 1 || this.direction == -1){
            if(a != null){
                if(b == null){
                    this.direction = 2;
                }
                else
                {
                    if(c == null){
                        this.direction = -2;
                    }
                }
            }
        }
        if(this.direction == 2 || this.direction == -2){
            if(d != null){
                if(e == null){
                    this.direction = -1;
                }
                else
                {
                    if(f == null){
                        this.direction = 1;
                    }
                }
            }
        }
        
    }
    
    /**
     * A method that enables the 4-directional movement of Entities. Determines which direction the Entity is set to, 
     * and moves it along the corresponding direction. 
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
                this.setRotation(270);
                move(speed);
                movementAnimation();
            }
            else
            {
                if(direction == -2){
                    this.setRotation(90);
                    move(speed);
                    movementAnimation();
                }
            }
        }
        
    }
    
    /**
     * No return type no parameter method that deletes the Entity from the world and spawns a skull graphic that fades away.
     */
    public void die() {
        
        getWorld().addObject(new DeathEffect(60), this.getX(), this.getY());
        getWorld().removeObject(this);
    }
    
    /**
     * Mutator for the hp variable that decrements it by the parameter of damage, and updates their statbar.
     */
    public void takeDamage(double damage) {
        hp -= damage;
        hpBar.update((int)hp);
    }
        
    /**
     * A general-use function that makes the actor stop, then deal damage to another actor.
     * 
     * @param other     the target actor that you want to deal damage to 
     * @param duration  the time you want the damaging actor to stop for 
     * @param damage    the damage you want to be dealt 
     */
    public void stopThenDamage(Entity other, int duration, int damage) {
        if (other != null && this.intersects(other)) {
            damageTimer++;
            speed = 0;
            if (damageTimer > duration) {
                other.takeDamage(damage);
                damageTimer = 0;
                speed = maxSpeed;
            }
        }
    }
    
    /**
     * A general-use function that makes the actor stop, then deal damage to another actor of a specific actor type.
     * Does not discriminate based on target, all actors of the other type are fair game.
     * 
     * @param other     the target class type that you want to deal damage to 
     * @param duration  the time you want the damaging actor to stop for 
     * @param damage    the damage you want to be dealt 
     */
    public void stopThenDamage(Class<?> otherType, int duration, int damage) {
        if (this.isTouching(otherType)) {
            Entity other = (Entity) getOneIntersectingObject(otherType);
            damageTimer++;
            speed = 0;
            if (damageTimer > duration) {
                other.takeDamage(damage);
                damageTimer = 0;
                speed = maxSpeed;
            }
        }
    }
    
    /**
     * A function that allows the Entity, which uses 4-directional movement, to pursue any target. Every second, it will also check for any 
     * Buildings in its path and ideally move out of the way before cycling back to focusing only on pursuing the target.
     * 
     * @param other     the actor you want to be pursued.
     */
    public void orientTowards(Actor other) {
        chaseTimer++; // increment our timer
        if (this.isTouching(Building.class)) { // if there is no forward path, run code that changes direction based on Building
            checkBuilding();
        } else if (chaseTimer > 60) { // otherwise, and only after one second
            
            // compute the horizontal/vertical component of the diagonal path to the target, and determine which one is shorter
            boolean fartherX = (Math.abs(other.getX() - this.getX()) > Math.abs(other.getY() - this.getY())) ? true: false;
            
            if (!fartherX) { // if the target is closer to us vertically
                // change directions to chase *only* in the y-axis
                if (other.getY() > this.getY()) {
                    direction = -2;
                } 
                
                if (other.getY() < this.getY()) {
                    direction = 2;
                }
            } else { // otherwise, pursue in the x-axis
                if (other.getX() < this.getX()) {
                    direction = -1;
                } 
            
                if (other.getX() > this.getX()) {
                    direction = 1;
                }  
            }    
            // reset our timer
            chaseTimer = 0;
        }
                
    }
    
}
