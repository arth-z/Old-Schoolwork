import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The Villager abstract superclass meant as the template for the town survivors.
 * Has several behavioural methods meant to be called in the act method for each subclass.
 * 
 * @author Arthur Zeng & Partially Michael
 * @version Final
 */
public abstract class Villager extends Entity
{
    //ArrayLists for enemy detection
    protected ArrayList<Enemy> enemiesInRange; //Basically sight range.
    protected ArrayList<Enemy> enemiesInMeleeRange; //Only used by melee class, self-explanatory.
    
    protected int range; //The actual range of the array "enemiesInRange".
    protected int hungerTimer; //The delay between hunger checks (checks once per second by default).
    
    protected static int villagersAddedToWorld; // for world calls and stats in world
    protected static int villagersKilled;
    protected boolean seesTheEnemy; // for line of sight checks
    
    // sound variables
    protected GreenfootSound[] swordSwooshSFX;
    protected int swordSwooshIndex;
    protected GreenfootSound[] bowSwooshSFX;
    protected int bowSwooshIndex;
    protected KDSAbove world;
    
    /**
     * The Villager constructor inherits the Entity constructor and passes its parameters into it as well.
     * It also initialises a range variable meant for attack calculations at a default value of 300.
     * 
     * @param maxSpeed      max speed of the spawned entity
     * @param maxHp         max health of the spawned entity
     * @param direction     either 1 (right), -1 (left), 2 (up), or -2 (down), determining the direction of the entity upon initial spawning
     */
    public Villager(int maxSpeed, int maxHp, int direction){
        super(maxSpeed, maxHp, direction);
        range = 300;
        villagersKilled = 0;
    }
    
    /**
     * Returns the static variable villagersAddedToWorld.
     * 
     * @return      static villagersAddedToWorld
     */
    public static int villagersAddedToWorld() {
        return villagersAddedToWorld;
    }
    
    /**
     * Returns the static variable villagersKilled.
     * 
     * @return      static villagersKilled
     */
    public static int villagersKilled() {
        return villagersKilled;
    }
    /**
     * addedToWorld() method override - exact same functionality, increments villagersAddedToWorld by 1.
     */
    public void addedToWorld(World w) {
        super.addedToWorld(w);
        world  = (KDSAbove) w;
        villagersAddedToWorld = world.getInitialVillagers();
    }
    /**
     * die() method override - exact same functionality, increments villagersKilled by 1.
     */
    public void die() {
        super.die();
        villagersKilled++;
    }
    
    /**
     * Simple method that checks the status of the hunger bar (located top left). 
     * Villagers take continuous damage at set intervals if hunger bar is zeroed out.
     * If the huger bar is above half, regen hp at set intervals.
     */
    public void hungerCheck() {
        hungerTimer++;
        if (hungerTimer > 60 && this.getWorld() != null) {
            if (((KDSAbove)getWorld()).isDay()) {
                if (((KDSAbove)getWorld()).getFoodCounter() <= 0) {
                takeDamage(5);
                }
                hungerTimer = 0;
                
                if (((KDSAbove)getWorld()).getFoodCounter() >= ((KDSAbove)getWorld()).getMaxFood()*0.5) {
                    if (hp < maxHp) {
                        takeDamage(-5);
                    }
                }
            }
            
        }
        
    }
    /**
     * Sprite cycling method used by woman/archer.
     * Cycles through 8 images in approximately 1 second.
     */
    public void attackCycle(ArrayList<GreenfootImage> array, int a, int b, int c, int d, int e, int f, int g, int h) {
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
            this.setImage(array.get(e));
        }
        if(attackTimer == 40){
            this.setImage(array.get(f));
        }
        if(attackTimer == 48){
            this.setImage(array.get(g));
        }
        if(attackTimer == 56){   
            this.setImage(array.get(h));
        }
        if(attackTimer == 64){
            attackTimer = 0;
        }
    }
    /**
     * Sprite cycling method used by man/knight.
     * Cycles through 4 images in 2/3 of a second.
     */
    public void attackCycle(ArrayList<GreenfootImage> array, int a, int b, int c, int d) {
        attackTimer++;
        if(attackTimer == 0){
            this.setImage(array.get(a));
        }
        if(attackTimer == 20){
            this.setImage(array.get(b));
        }
        if(attackTimer == 30){
            this.setImage(array.get(c));
        }
        if(attackTimer == 40){   
            this.setImage(array.get(d));
            attackTimer = 0;            
        }
        
    }
    /**
     * Act - do whatever the Villager wants to do.
     * This method isn't used - it also just inherits from Entity's super.act() (which is walk)
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * The idle behaviour of villagers if there are no enemies or animals to target.
     * Behaviour changes corresponding to day-night cycle:
     *      During the daytime, villagers will attempt to stay within proximity of the village by orienting toward road objects.
     *      During night time, villagers will orient towards an invisible ring of roads surrounding the perimeter of the village (This is so enemies do not get caught in small gaps between buildings).
     */
    public void passiveBehaviour() { // basically: stay in the bounds of the village, and chill out.
        speed = maxSpeed;
        if (((KDSAbove)getWorld()).isDay()) { // if it's day time, essentially wander around in the bounds of the villager, orienting towards roads
            if (!this.isTouching(Road.class)) {
                Road road = (Road)((ArrayList)getObjectsInRange(range*4, Road.class)).get(0);
                orientTowards(road);
            } 
        } else { // but if it's night, wander around/orient towards a perimeter of invisible road objects around the village
            if (!this.isTouching(InvisRoad.class)) {
                InvisRoad road = (InvisRoad)((ArrayList)getObjectsInRange(range*100, InvisRoad.class)).get(0);
                orientTowards(road);
            } 
        }
        
        // dont bump into buildings
        checkBuilding();
        
        // walk
        super.act();
    }
    
    /**
     * The fighting AI code for woman/archer:
     *      Essentially, all enemies in a certain range are put into an arraylist (basically sight range), and the woman/archer targets the closest enemy in line of sight.
     *      
     *      An invisible projectile object is fired from woman to the enemy, the invisible projectile will delete itself if it touches a building.
     *      Enemies have a boolean called targetable which is set to false by default and becomes true only when an invisible projectile makes contact with them.
     *      Woman and archer will only shoot actual arrows at enemys with targetable set to true (in line of sight).
     *      
     *      @param TargetingLogic: 
     *          Are there enemies in sight range?
     *              >> Yes:
     *                  Are they in LOS?
     *                      >> Yes: Stop and shoot.
     *                      >> No: Move to a spot where they are in LOS.
     *              >> No: Idle around.
     *          
     */
    public void rangedBehaviour() { // shoot things in line of sight.
        
        // enemiesInRange is initialised, but also initialise an arraylist of enemies in a certain radius
        ArrayList<Enemy> tempInRange = (ArrayList) getObjectsInRange((int)(range*1.5), Enemy.class);
        enemiesInRange = new ArrayList<Enemy>(tempInRange);
        
        for (Enemy enemy: tempInRange) {
            getWorld().addObject(new InvisProjectile(enemy, (Villager) this), getX(), getY()); // shoot an invisible "tracker" at each enemy in temp
            
            // the invisible projectiles "tag" enemies and can flip their targettable() status, but are stopped on buildings
            // therefore, this essentially functions as a line of sight check to enemies in range
            if (enemy.seenByArcher(this)) { //if the enemy is in LOS, they are now a target
                enemiesInRange.add(enemy);
            } else { // otherwise, they can't, or won't be a target.
                if (enemiesInRange.contains(enemy)) {
                    enemiesInRange.remove(enemy);
                }
            }
        }

        if (enemiesInRange.size() == 0) { // if no enemies are in range, default to passive behaviour at max speed
            passiveBehaviour();
        } else { //otherwise, start shooting
            orientTowards(enemiesInRange.get(0)); // turn towards the enemy at first
            speed = 0; // stand still
            
            // is the enemy to the left or right of you?
            boolean right = this.getX() < enemiesInRange.get(0).getX();
            
            setRotation(0); // set a default rotation first
            
            if (right) { // then turn to face this enemy
                direction = 1;
            } else {
                direction = -1;
            }
            
            // cycle/play the movement animations for attack cycles
            if (direction == 1) {
                attackCycle(invertedSpriteAttackArray, 0,1,2,3,4,5,6,7);
            } else if (direction == -1) {
                attackCycle(spriteAttackArray, 0,1,2,3,4,5,6,7);
            }
                
            damageTimer++;
            
            if (damageTimer > 64) { // shoot an arrow every so often
                getWorld().addObject(new Projectile(enemiesInRange.get(0)), this.getX(), this.getY());
                speed = maxSpeed;
                damageTimer = 0;
                bowSwooshSFX[bowSwooshIndex].play();
                bowSwooshSFX[bowSwooshIndex].setVolume(70);
                bowSwooshIndex++;
                
                if(bowSwooshIndex > bowSwooshSFX.length - 1){
                    bowSwooshIndex = 0;
                }
            }
        }
    }
    /**
     * The fighting AI code for man/knight: 
     *      Similar to the fighting AI code for woman/archer, man/knight stores all enemies in a certain range into an arraylist that basically acts as its sight range.
     *      However, man/knight has a second arraylist called enemiesInMeleeRange which are the targets it will actually stop and attack.
     *      
     *     
     *      @param TargetingLogic:
     *          Are there enemies in melee range?
     *              >> No: Are there enemies in sight range? 
     *                      >> Yes: Move towards the closest enemy.
     *                      >> No: Idle Around.
     *              >> Yes: Stop and attack.
     */
    public void meleeBehaviour() { // a defensive behaviour that retaliates, in melee, against enemies in a certain range
        enemiesInRange = (ArrayList) getObjectsInRange(range, Enemy.class);
        enemiesInMeleeRange = (ArrayList) getObjectsInRange(25, Enemy.class);
        if(enemiesInMeleeRange.size() == 0){
            if (enemiesInRange.size() == 0) {
                passiveBehaviour();
                speed = maxSpeed;
            } else {
                speed = maxSpeed;
                Enemy target = enemiesInRange.get(0);
                orientTowards(target);
                super.act();
            }
        }
        else
        {
            
            Enemy meleeTarget = enemiesInMeleeRange.get(0);
            // is the enemy to the left or right of you?
            boolean right = this.getX() < meleeTarget.getX();
                
            setRotation(0); // set a default rotation first
                
            if (right) { // then turn to face this enemy
                direction = 1;
            } else {
                direction = -1;
            }
                
            //Cycles different image arrays depending on direction.
            if (direction == 1) { 
                attackCycle(invertedSpriteAttackArray, 4,5,6,7);
            } else if (direction == -1) {
                attackCycle(spriteAttackArray, 4,5,6,7);
            } else {
                attackCycle(spriteAttackArray, 4,5,6,7);
            }
            
            damageTimer++;
            speed = 0; //Stops when attacking.
                
            if (damageTimer > 40) { //Deals damage after the attack animation finishes.
                swordSwooshSFX[swordSwooshIndex].play();
                swordSwooshSFX[swordSwooshIndex].setVolume(20);
                swordSwooshIndex++;
                if(swordSwooshIndex > swordSwooshSFX.length - 1){
                    swordSwooshIndex = 0;
                }

                meleeTarget.takeDamage(10);
                damageTimer = 0;
                speed = maxSpeed;
            }
                
        }
       
    }
    

    
}
