import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>
 * The King of Hearts (or Hearts for short), a subclass implementation of the Boss class possessing no unique variables of its own.
 * Functionally identical to the other bosses - it calls Boss's act() cycle, possesses a unique attackControl() method as well as multiple
 * other attack functions, and does the things that Boss's parent "blueprint" code tells it to.
 * </p>
 * 
 * <p>
 * Meant to be part of a duo fight with the King of Spades. Is the "slow and dramatic" to Spades' "fast and forceful".
 * When the King of Spades is on field, Hearts attacks slower and doesn't do one of his stronger attacks. 
 * When Hearts is alone, he attacks faster and uses one of his stronger attacks.
 * In order to defeat Hearts, it's best to be patient and stay calm, trying not to be overwhelmed.
 * </p>
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class KingOfHearts extends Boss
{
    private int timer;
    /**
     * Constructor for hearts, initialises a few string variables (for boss-unique sprites) and passes in a relevant GreenfootImage into super() as its image.
     */
    public KingOfHearts() {
        super(new GreenfootImage("koh.png"));
        type = "Heart";
        damageType = "damage" + type + ".png";
        timer = 0;
    }
    
    /**
     * Controls the behaviour of Hearts in terms of the attacks he performs on the world's game grid. Chooses between his 4 attacks 
     * (5 if he is alone, with the 1 extra having higher weighting) randomly, and excutes them at 120/240 act intervals, depending on if he is alone.
     */
    protected void attackControl() {
        if(world.isBlankActive() == false){
            attackTimer++;
        }
        //ternary operator to determine speed of attacks - 60 if Spades is alive, 30 if Hearts is alone.

        int interval = getWorld().getObjects(KingOfSpades.class).size() == 0 ? 100 : 240;
        
        if (attackTimer > interval) {
            attack.get(attackIndex).play();
            attackIndex++;
            if(attackIndex > attack.size() - 1){
                attackIndex = 0;
            }
            //ternary operator to determine possibility of Hearts' strong attack.
            //essentially, if the other member of the duo is dead, the choose variable is able to access three more random values, 
            //all of which trigger the strong attack.
            choose = getWorld().getObjects(KingOfSpades.class).size() == 0 ? Greenfoot.getRandomNumber(9): Greenfoot.getRandomNumber(5);
            switch(choose) {
                case 0:
                    attack4();
                    attackTimer = 0;
                    break;
                case 1:
                    attack2();
                    attackTimer = 0;
                    break;
                case 2:
                    attack3();
                    attackTimer = 0;
                    break;
                case 3:
                    attack4();
                    attackTimer = 0;
                    break;
                case 4:
                    attack5();
                    attackTimer = 0;
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    attack1();
                    attackTimer = -60; // longer wait time here to prevent unfair hits
                    break;
            }
            
        }
    }
    
    /**
     * Hearts' strong attack, which is only performed if he is alone and Spades is dead.
     * Highlights the entire grid, and then flashes damaging effects on white, then black, then white cells respectively.
     * Slow speed.
     */
    protected void attack1() {
        for (Cell[] row: checkerBoard) {
            for (Cell cell: row) {
                if (cell.type() == "white") {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, cell, 75, 60);
                    telegraph.initNextAttackEffect(10, 5, 10, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());
                }
            }
        }
        
        for (Cell[] row: checkerBoard) {
            for (Cell cell: row) {
                if (cell.type() == "black") {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, cell, 45, 150);
                    telegraph.initNextAttackEffect(10, 5, 10, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());
                }
            }
        }
        
        for (Cell[] row: checkerBoard) {
            for (Cell cell: row) {
                if (cell.type() == "white") {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, cell, 15, 240);
                    telegraph.initNextAttackEffect(10, 5, 10, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());
                }
            }
        }
    }
    
    /**
     * An attack that highlights the entire grid sans the middle row, and then spawns damaging effects that converge on the safe row.
     * Medium-slow speed.
     */
    protected void attack2() {
        TelegraphIndicator telegraph;
        
        for (int i = 0; i < checkerBoard.length; i++) {
            telegraph = new TelegraphIndicator(this, checkerBoard[0][i], 100, 120);
            telegraph.initNextAttackEffect(5, 5, 60, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[8][i], 100, 120);
            telegraph.initNextAttackEffect(5, 5, 60, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[1][i], 80, 120);
            telegraph.initNextAttackEffect(5, 5, 45, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[7][i], 80, 120);
            telegraph.initNextAttackEffect(5, 5, 45, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[2][i], 60, 120);
            telegraph.initNextAttackEffect(5, 5, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[6][i], 60, 120);
            telegraph.initNextAttackEffect(5, 5, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[3][i], 40, 120);
            telegraph.initNextAttackEffect(5, 5, 15, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[5][i], 40, 120);
            telegraph.initNextAttackEffect(5, 5, 15, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
        }
    }
    
    /**
     * An attack that highlights the entire grid sans the middle column, and then spawns damaging effects that converge on the safe column.
     * Medium-slow speed.
     */
    protected void attack3() {
        TelegraphIndicator telegraph;
        
        for (int i = 0; i < checkerBoard.length; i++) {
            telegraph = new TelegraphIndicator(this, checkerBoard[i][0], 100, 120);
            telegraph.initNextAttackEffect(5, 5, 60, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[i][8], 100, 120);
            telegraph.initNextAttackEffect(5, 5, 60, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[i][1], 80, 120);
            telegraph.initNextAttackEffect(5, 5, 45, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[i][7], 80, 120);
            telegraph.initNextAttackEffect(5, 5, 45, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[i][2], 60, 120);
            telegraph.initNextAttackEffect(5, 5, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[i][6], 60, 120);
            telegraph.initNextAttackEffect(5, 5, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[i][3], 40, 120);
            telegraph.initNextAttackEffect(5, 5, 15, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            telegraph = new TelegraphIndicator(this, checkerBoard[i][5], 40, 120);
            telegraph.initNextAttackEffect(5, 5, 15, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
        }
    }
    
    /**
     * An attack that draws a very large heart on the entire grid, in a set location.
     * Medium-slow speed.
     */
    protected void attack4() {
        TelegraphIndicator telegraph;
        
        telegraph = new TelegraphIndicator(this, checkerBoard[0][1], 100, 120);
        telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());
        telegraph = new TelegraphIndicator(this, checkerBoard[0][2], 100, 120);
        telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());
        telegraph = new TelegraphIndicator(this, checkerBoard[0][6], 100, 120);
        telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());
        telegraph = new TelegraphIndicator(this, checkerBoard[0][7], 100, 120);
        telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());
        
        for (int i = 0; i < checkerBoard.length; i++) {
            if (i != 4) {
                telegraph = new TelegraphIndicator(this, checkerBoard[1][i], 100, 120);
                telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            }
        }
        
        for (int j = 2; j < 5; j++) {
            for (int i = 0; i < checkerBoard.length; i++) {
                if (!(i == 4 & j == 4)) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[j][i], 100, 120);
                    telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());     
                }
            }    
        }
        
        for (int i = 1; i < checkerBoard.length - 1; i++) {
            telegraph = new TelegraphIndicator(this, checkerBoard[5][i], 100, 120);
            telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());    
        }
        
        for (int i = 2; i < checkerBoard.length - 2; i++) {
            telegraph = new TelegraphIndicator(this, checkerBoard[6][i], 100, 120);
            telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());    
        }
        
        for (int i = 3; i < checkerBoard.length - 3; i++) {
            telegraph = new TelegraphIndicator(this, checkerBoard[7][i], 100, 120);
            telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());    
        }
        
        telegraph = new TelegraphIndicator(this, checkerBoard[8][4], 100, 120);
        telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());    
    }
    
    /**
     * An attack that highlights the entire grid except for a safe "X" in the center of the grid.
     * Medium-slow speed.
     */
    protected void attack5() {
        TelegraphIndicator telegraph;
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard.length; j++) {
                if (i != j && i != checkerBoard[i].length - 1 - j) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 100, 120);
                    telegraph.initNextAttackEffect(10, 10, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());     
                }
            }
        }
        
    }
    
    /**
     * Act - calls Boss's act() method with super.act(). If health less or equal to zero, sets a flag in the world to true that Hearts has been defeated.
     */
    public void act()
    {
        // Add your action code here.
        super.act();
        if (health <= 0) {
            timer++;
            if (timer > 200) {
                world.defeatHearts();    
            }
        }
    }
}
