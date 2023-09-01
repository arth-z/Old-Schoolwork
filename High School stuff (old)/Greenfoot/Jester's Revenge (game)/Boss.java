import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The main enemy of the game. Basic abstract framework for boss behaviour, except for attack behaviour.
 * Includes:
 * <p>
 *  - Boss vulnerability cell mechanic (the way the player attacks the boss, by moving to a cell and pressing space while anchored to it)
 * </p>
 * <p>
 *  - Boss spawn-in animation
 * </p>
 * <p>
 *  - Boss health and death
 * </p>
 * 
 * <p>
 * Does not include the mechanisms for attacking that each boss has - those are in the individual subclasses itself.
 * </p>
 * 
 * <p>
 * Issues:
 * </p>
 * <p> - Boss death animation is inconsistent. </p>
 * @author Arthur Zeng
 * @version 3.00
 */
public abstract class Boss extends Actor
{
    protected Cell[][] checkerBoard;
    protected Cell vulnCell;
    protected JestersRevenge world;
    
    protected ArrayList<GreenfootSound> attack;
    protected int attackIndex;
    
    protected int attackTimer;
    protected int deathTimer;
    protected int choose;
    protected String type;
    protected BossDeath bossDeath;
    protected String damageType;
    protected int health = 13;
    
    protected int temp;
    
    
    /**
     * A "promise" that each boss subclass will have a method known as attackControl, meant to be called in act(), controlling the boss's
     * attacking behaviour.
     */
    protected abstract void attackControl();
    
    /**
     * The constructor for the Boss class initialises a few values: multiple timers/choose variables to 0 (death and attack), and health to 13).
     * It also sets the boss's image as a parameter, and initialises some sound variables.
     * 
     * @param image         the GreenfootImage you would want the boss to take on
     */
    public Boss(GreenfootImage image) {
        setImage(image);
        attackTimer = 0;
        choose = 0;
        deathTimer = 0;
        attackIndex = 0;
        attack = new ArrayList<GreenfootSound>(50);
        for(int i = 0; i < 50; i++){
            attack.add(new GreenfootSound("attack.wav"));
        }
    }
    
    /**
     * A method that maintains the Boss's singular vulnerability cell on the game world's grid, meant to be called in act().
     * It returns a type int in order to terminate the function early if there is already a vulnCell on the world. 
     * Return value currently is not used for anything.
     * 
     * @return int          -1 if there already is a vuln cell on the grid, 1 if there is no vuln cell on the grid.
     */
    public int maintainVulnCell() {
        for (Cell[] array: checkerBoard) {
            for (Cell cell: array) {
                if (cell.getBossOfThisCell() == this) {
                    return -1;
                }
            }
        }
        
        generateVulnCell();
        return 1;
    }
    
    /**
     * A method that randomly creates a single vulnerability cell on the game world's grid.
     */
    public void generateVulnCell() {
        vulnCell = checkerBoard[Greenfoot.getRandomNumber(9)][Greenfoot.getRandomNumber(9)];
        vulnCell.becomeBossOfThisCell(this);
        vulnCell.becomeAttackCell(new GreenfootImage("health" + type + ".png"));
    }
    
    /**
     * Simple mutator that subtracts the parameter passed into it from the boss's health value.
     * Called by other objects (Player).
     * 
     * @param x         the damage you would like to inflict onto the boss health
     */
    public void takeDamage(int x) {
        health -= x;
    }
    
    public int getHealth() {
        return health;
    }
    
    /**
     * Override for Greenfoot's addedToWorld() method, called when the boss is added to the world. 
     * Assuming the world is a JestersRevenge object and contains a checkerboard (game grid), it stores that into a instance variable, casts 
     * the world it is added into as JestersRevenge and saves that as another instance variable, and then sets its transparency to 0 (for spawning animation).
     * 
     * @param w         the world the Boss is added to.
     */
    public void addedToWorld(World w) {
        checkerBoard = ((JestersRevenge)(w)).getCheckerboard();
        world = (JestersRevenge)(w);
        getImage().setTransparency(0);
    }
    
    /**
     * Death process for a boss. Removes their attacks, attack telegraphs, and vulnerability cell from the world, 
     * then adds a BossDeath centered on them (a gif), and deletes both itself and the BossDeath from the world after a set amount of acts
     * (attempting to be as accurate to the end of the gif as possible, although it is inconsistent and varies from death to death).
     * Called in act().
     */
    public void die() {
        ArrayList<Actor> attackTelegraphs = (ArrayList) getWorld().getObjects(TelegraphIndicator.class);
        ArrayList<Actor> attacks = (ArrayList) getWorld().getObjects(DamageEffect.class);
        
        for (Actor element: attackTelegraphs) {
            getWorld().removeObject(element);
        }
        
        for (Actor element: attacks) {
            getWorld().removeObject(element);
        }
        
        for (Cell[] array: checkerBoard) {
            for (Cell cell: array) {
                if (cell.isAttackCell()) {
                    cell.becomeNotAttackCell();
                }
            }
        }
        
        if (bossDeath == null) {
            bossDeath = new BossDeath();
        }

        if (getWorld().getObjects(BossDeath.class).size() < 1) {
            getWorld().addObject(bossDeath, getX(), getY());
        }
        
        vulnCell.becomeNotAttackCell();
        deathTimer++;
        if (deathTimer > 200) {
            getWorld().removeObject(bossDeath);
            getImage().setTransparency(getImage().getTransparency() - 45 > 0 ? getImage().getTransparency() - 45: 0);
            if (getImage().getTransparency() == 0) {
                getWorld().removeObject(this);
            }
        }
    }
    
    public void clearBoard() {
        ArrayList<Actor> attackTelegraphs = (ArrayList) getWorld().getObjects(TelegraphIndicator.class);
        ArrayList<Actor> attacks = (ArrayList) getWorld().getObjects(DamageEffect.class);
        
        for (Actor element: attackTelegraphs) {
            getWorld().removeObject(element);
        }
        
        for (Actor element: attacks) {
            getWorld().removeObject(element);
        }
    }
    
    public String getType(){
        return type;
    }
    
    /**
     * Act method for the boss. Ensures that the boss is at full transparency - fades in by a value of 1 each act if it is not (for spawning animation).
     * If the boss is at full transparency, it continuously updates the instance variable representing the game world's checkerBoard, so that it can access/manipulate it.
     * If the boss's health is above zero, it performs its attack behaviour and maintains the vulnerability cell on the grid.
     * Otherwise, it dies.
     */
    public void act()
    {
        if (getImage().getTransparency() < 255 && health > 0) {
            getImage().setTransparency(getImage().getTransparency() + 1);
        } else {
            checkerBoard = ((JestersRevenge)(getWorld())).getCheckerboard();
            if (health > 0) {
                attackControl();
                maintainVulnCell();
            } else {
                die();
            }
        }
        
        
    }
}
