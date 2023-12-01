import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * <p>
 * The King of Clubs/Clovers (or Clubs/Clovers for short), a subclass implementation of the Boss class possessing not many unique variables of its own.
 * Functionally identical to the other bosses - it calls Boss's act() cycle, possesses a unique attackControl() method as well as multiple
 * other attack functions, and does the things that Boss's parent "blueprint" code tells it to.
 * </p>
 * 
 * <p>
 * Clovers is a gimmick fight. The theming of clovers is luck, right? So the entire fight is an RNG fiesta, and Clovers' attacks are randomised
 * both in speed and magnitude. Unlike the other bosses, he doesn't have the individual attack methods - each attack is in attackControl().
 * </p>
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class KingOfClovers extends Boss
{
    private ArrayList<KingOfHearts> kingofhearts;
    private ArrayList<KingOfSpades> kingofspades;
    private GreenfootImage heartHealthSprite = new GreenfootImage("healthHeart.png");
    private GreenfootImage heartEmptyHealthSprite = new GreenfootImage("emptyHealthHeart.png");
    private GreenfootImage spadeHealthSprite = new GreenfootImage("healthSpade.png");
    private GreenfootImage spadeEmptyHealthSprite = new GreenfootImage("emptyHealthSpade.png");
    
    private int timer;
    private GreenfootSound cloverTheme = new GreenfootSound("nargacuga.wav");
    
    /**
     * Constructor for clovers, initialises a few relevant variables (for boss-unique sprites, and attacks) and passes in a relevant GreenfootImage into super() as its image.
     */
    public KingOfClovers() {
        super(new GreenfootImage("koc.png"));
        type = "Clover";
        damageType = "damage" + type + ".png";
    }
    
    /**
     * attackControl() for the King of Clovers is actually slightly different from the way other Kings work.
     * Here, Clovers doesn't actually have any set attacks, and attackControl() executes attacks directly based on complete randomness.
     * There are still, however, 5 possibilities of attacks, each with escalating speed and suddenness in exchange for AOE and area coverage.
     * Quantity of cells covered, speed, telegraph duration remain *roughly* consistent, but which cells are targetted is more or less completely random.
     * These make it so that Clovers *feels* random, but isn't so random so that the fight is incomprehensible.
     */
    protected void attackControl() {
        if(world.isBlankActive() == false){
            attackTimer++;
        }
        
        if (attackTimer > 120) {
            TelegraphIndicator telegraph;
            choose = Greenfoot.getRandomNumber(5);
            attack.get(attackIndex).play();
            attackIndex++;
            if(attackIndex > attack.size() - 1){
                attackIndex = 0;
            }
            switch(choose) {
                case 0:
                    for (int i = 0; i < checkerBoard.length*3 + Greenfoot.getRandomNumber(6); i++) {
                        telegraph = new TelegraphIndicator(this, 
                        checkerBoard[Greenfoot.getRandomNumber(checkerBoard.length)][Greenfoot.getRandomNumber(checkerBoard.length)], 
                        10, 130);
                        telegraph.initNextAttackEffect(7, 7, 30, new GreenfootImage(damageType));
                        getWorld().addObject(telegraph, getX(), getY());
                    }
                    attackTimer = 75;
                    break;
                case 1: 
                    for (int i = 0; i < checkerBoard.length*4 + Greenfoot.getRandomNumber(10); i++) {
                        telegraph = new TelegraphIndicator(this, 
                        checkerBoard[Greenfoot.getRandomNumber(checkerBoard.length)][Greenfoot.getRandomNumber(checkerBoard.length)], 
                        20, 120);
                        telegraph.initNextAttackEffect(7, 7, 30, new GreenfootImage(damageType));
                        getWorld().addObject(telegraph, getX(), getY());
                    }
                    attackTimer = 60;
                    break;
                case 2: 
                    for (int i = 0; i < checkerBoard.length*5 + Greenfoot.getRandomNumber(14); i++) {
                        telegraph = new TelegraphIndicator(this, 
                        checkerBoard[Greenfoot.getRandomNumber(checkerBoard.length)][Greenfoot.getRandomNumber(checkerBoard.length)], 
                        40, 110);
                        telegraph.initNextAttackEffect(7, 7, 30, new GreenfootImage(damageType));
                        getWorld().addObject(telegraph, getX(), getY());
                    }
                    attackTimer = 45;
                    break;
                case 3: 
                    for (int i = 0; i < checkerBoard.length*6 + Greenfoot.getRandomNumber(18); i++) {
                        telegraph = new TelegraphIndicator(this, 
                        checkerBoard[Greenfoot.getRandomNumber(checkerBoard.length)][Greenfoot.getRandomNumber(checkerBoard.length)], 
                        30, 100);
                        telegraph.initNextAttackEffect(7, 7, 30, new GreenfootImage(damageType));
                        getWorld().addObject(telegraph, getX(), getY());
                    }
                    attackTimer = 30;
                    break;
                case 4: 
                    for (int i = 0; i < checkerBoard.length*7 + Greenfoot.getRandomNumber(22); i++) {
                        telegraph = new TelegraphIndicator(this, 
                        checkerBoard[Greenfoot.getRandomNumber(checkerBoard.length)][Greenfoot.getRandomNumber(checkerBoard.length)], 
                        20, 90);
                        telegraph.initNextAttackEffect(7, 7, 30, new GreenfootImage(damageType));
                        getWorld().addObject(telegraph, getX(), getY());
                    }
                    attackTimer = 15;
                    break;
            }
        }
    }
    
    /**
     * Act - super.act(), but once its health is depleted, it gets ready to spawn Hearts and Spades, if they're not already on the field.
     * Sets a flag in the world to true that Clovers has been defeated if its health is depleted as well.
     */
    public void act()
    {
        super.act();
        if(health <= 0){
            cloverTheme.stop();
            timer++;
            if(timer >= 200 && zeroKingOfHearts() && zeroKingOfSpades()){
                for(int i = 0; i<5; i++){
                    world.healPlayer();
                }
                world.resetBossHealth();
                world.initializeBossHealth(world.getHealthDisplay(), heartHealthSprite, heartEmptyHealthSprite, 60, 50, 70);
                world.initializeBossHealth(world.getHealthDisplayB(), spadeHealthSprite, spadeEmptyHealthSprite, 60, 50, 270);
                getWorld().addObject(new KingOfHearts(), 450, 123);
                getWorld().addObject(new KingOfSpades(), 750, 123);
                timer = 0;
                world.defeatClovers();
            } 
        } else {
            cloverTheme.playLoop();
        }
    }
    
    /**
     * Getter for whether or not there is currently a King of Hearts in the world right now.
     * 
     * @return boolean          true if there is a King of Hearts in the world, aflse if there is not.
     */
    public boolean zeroKingOfHearts(){
        kingofhearts = (ArrayList)getWorld().getObjects(KingOfHearts.class);
        if(kingofhearts.size() > 0){
            return false;
        }
        return true;
    }
    
    /**
     * Getter for whether or not there is currently a King of Spades in the world right now.
     * 
     * @return boolean          true if there is a King of Spades in the world, aflse if there is not.
     */
    public boolean zeroKingOfSpades(){
        kingofspades = (ArrayList)getWorld().getObjects(KingOfSpades.class);
        if(kingofspades.size() > 0){
            return false;
        }
        return true;
    }
}

