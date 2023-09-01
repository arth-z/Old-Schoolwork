import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>
 * The King of Spades (or Spades for short), a subclass implementation of the Boss class possessing no unique variables of its own.
 * Functionally identical to the other bosses - it calls Boss's act() cycle, possesses a unique attackControl() method as well as multiple
 * other attack functions, and does the things that Boss's parent "blueprint" code tells it to.
 * </p>
 * 
 * <p>
 * Meant to be part of a duo fight with the King of Hearts. Is the "fast and forceful" to Hearts' "slow and dramatic".
 * When the King of Hearts is on field, Spades attacks slower and doesn't do one of his stronger attacks. 
 * When Spades is alone, he attacks faster and uses one of his stronger attacks.
 * In order to defeat Spades, it's best to move as quickly as possible, and stay constantly mobile.
 * </p>
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class KingOfSpades extends Boss
{
    private int timer;
    /**
     * Constructor for spades, initialises a few string variables (for boss-unique sprites) and passes in a relevant GreenfootImage into super() as its image.
     */
    public KingOfSpades() {
        super(new GreenfootImage("kos.png"));
        type = "Spade";
        damageType = "damage" + type + ".png";
        timer = 0;
    }
    
    /**
     * Controls the behaviour of Spades in terms of the attacks he performs on the world's game grid. Chooses between his 4 attacks 
     * (5 if he is alone, with the 1 extra having significantly higher weighting) randomly, and excutes them at 45/75 act intervals, depending on if he is alone.
     */
    protected void attackControl() {
        if(world.isBlankActive() == false){
            attackTimer++;
        }
        //ternary operator to determine speed of attacks - 75 if Hearts is alive, 45 if Spades is alone.
        int interval = getWorld().getObjects(KingOfHearts.class).size() == 0 ? 40 : 75;

        if (attackTimer > interval) {
            attack.get(attackIndex).play();
            attackIndex++;
            if(attackIndex > attack.size() - 1){
               attackIndex = 0;
            }
            //ternary operator to check if Hearts is alive, to determine possibility of Spade strong attack
            //essentially, if the other member of the duo is dead, the choose variable is able to access three more random values, 
            //all of which trigger the strong attack.
            choose = getWorld().getObjects(KingOfHearts.class).size() == 0 ? Greenfoot.getRandomNumber(9): Greenfoot.getRandomNumber(5);
            switch(choose) {
                case 0:
                    attack1();
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
                    attack4();
                    attackTimer = 0;
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    attack5();
                    attackTimer = -45; // longer wait time here to prevent unfair hits
                    break;
            }
            
        }
    }
    
    /**
     * Draws a very simple spade pattern of damaging grids around any vulnerability cells in the board.
     * The spade is cut off by the grid world's edges.
     * Fast speed.
     */
    public void attack1() {
        TelegraphIndicator telegraph;
        
        for (Cell[] array: checkerBoard) {
            for (Cell cell: array) {
                if (cell.isAttackCell()) {
                    int vulnX = cell.getXCoord();
                    int vulnY = cell.getYCoord();
                    for (int i = 0; i < checkerBoard.length; i++) {
                        for (int j = 0; j < checkerBoard[i].length; j++) {
                            if ((i == vulnY - 1 && j == vulnX) || (i == vulnY && j == vulnX + 1) || (i == vulnY && j == vulnX - 1) || (i == vulnY + 2 && j == vulnX)) {
                                telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 100, 60);
                                telegraph.initNextAttackEffect(5, 5, 20, new GreenfootImage(damageType));
                                getWorld().addObject(telegraph, getX(), getY());    
                            }
                        }
                    }
                }
            }
        }
        
    }
    /**
     * Draws a very small upside-down V of damaging grids, onto the vulnerability cell, with the top of the V being the vulnCell.
     * The V is cut off by the grid world's edges.
     * Fast speed.
     */
    public void attack2() {
        TelegraphIndicator telegraph;
        
        for (Cell[] array: checkerBoard) {
            for (Cell cell: array) {
                if (cell.isAttackCell()) {
                    int vulnX = cell.getXCoord();
                    int vulnY = cell.getYCoord();
                    for (int i = 0; i < checkerBoard.length; i++) {
                        for (int j = 0; j < checkerBoard[i].length; j++) {
                            if ((i == vulnY && j == vulnX) || (i == vulnY + 1 && j == vulnX + 1) || (i == vulnY + 1 && j == vulnX - 1)) {
                                telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 100, 60);
                                telegraph.initNextAttackEffect(5, 5, 20, new GreenfootImage(damageType));
                                getWorld().addObject(telegraph, getX(), getY());    
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Draws a very simple spade pattern of damaging grids around the cell the player is currently occupying in the board.
     * The spade is cut off by the grid world's edges.
     * Fast speed.
     */
    public void attack3() {
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == world.findPlayerY() - 1 && j == world.findPlayerX()) || 
                (i == world.findPlayerY() && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() && j == world.findPlayerX() - 1) || 
                (i == world.findPlayerY() + 2 && j == world.findPlayerX())) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 120, 60);
                    telegraph.initNextAttackEffect(5, 5, 20, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
            
    }
    
    /**
     * Draws a very simple upside-down V of damaging grids on the cell the player is currently occupying in the board. The top of the V is the player cell.
     * The V is cut off by the grid world's edges.
     * Fast speed.
     */
    public void attack4() {
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == world.findPlayerY() && j == world.findPlayerX()) || 
                (i == world.findPlayerY() + 1 && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() + 1 && j == world.findPlayerX() - 1)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 120, 60);
                    telegraph.initNextAttackEffect(5, 5, 20, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
    }
    
    /**
     * Spade's super attack. Centers a medium-size spade on the player, then flashes that spade with damaging cells on its black, then white, then black squares respectively.
     * Fast speed. Only used when Spades is alone, and Hearts is dead.
     */
    public void attack5() {
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == world.findPlayerY() - 2 && j == world.findPlayerX()) || 
                (i == world.findPlayerY() - 1 && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() - 1 && j == world.findPlayerX() - 1) || 
                (i == world.findPlayerY() && j == world.findPlayerX() - 2) ||
                (i == world.findPlayerY() && j == world.findPlayerX()) || 
                (i == world.findPlayerY() && j == world.findPlayerX() + 2) || 
                (i == world.findPlayerY() + 1 && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() + 1 && j == world.findPlayerX() -1)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 120, 60);
                    telegraph.initNextAttackEffect(5, 5, 20, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == world.findPlayerY() - 1 && j == world.findPlayerX()) || 
                (i == world.findPlayerY() && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() && j == world.findPlayerX() - 1)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 90, 105);
                    telegraph.initNextAttackEffect(5, 5, 20, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == world.findPlayerY() - 2 && j == world.findPlayerX()) || 
                (i == world.findPlayerY() - 1 && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() - 1 && j == world.findPlayerX() - 1) || 
                (i == world.findPlayerY() && j == world.findPlayerX() - 2) ||
                (i == world.findPlayerY() && j == world.findPlayerX()) || 
                (i == world.findPlayerY() && j == world.findPlayerX() + 2) || 
                (i == world.findPlayerY() + 1 && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() + 1 && j == world.findPlayerX() -1)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 60, 150);
                    telegraph.initNextAttackEffect(5, 5, 20, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
    }
    
    /**
     * Act - calls Boss's act() method with super.act(). If health less or equal to zero, sets a flag in the world to true that Hearts has been defeated 
     */
    public void act()
    {
        // Add your action code here.
        super.act();
        if (health <= 0) {
            timer++;
            if (timer > 200) {
                world.defeatSpades();              
            }
        }
    }
}
