import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * <p>
 * The King of Diamonds (or Diamonds for short), a subclass implementation of the Boss class possessing not many unique variables of its own.
 * Functionally identical to the other bosses - it calls Boss's act() cycle, possesses a unique attackControl() method as well as multiple
 * other attack functions, and does the things that Boss's parent "blueprint" code tells it to.
 * </p>
 * 
 * <p>
 * Solo fight and first boss of the game designed to introduce specific mechanics, so has both fast "poke" and slow "strong" attacks. 
 * Design is more similar to the Spades+Hearts duo fight than the more gimmicky Clovers fight. Stays consistent throughout the entire fight.
 * </p>
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class KingOfDiamonds extends Boss
{
    private int timer;
    private ArrayList<KingOfClovers> kingofclovers;
    private Health[] bossHealth = new Health[health];
    private GreenfootImage cloverHealthSprite = new GreenfootImage("healthClover.png");
    private GreenfootImage cloverEmptyHealthSprite = new GreenfootImage("emptyHealthClover.png");
    
    private GreenfootSound diamondTheme = new GreenfootSound("yukari.wav");
    
    /**
     * Constructor for diamonds, initialises a few string variables (for boss-unique sprites) and passes in a relevant GreenfootImage into super() as its image.
     */
    public KingOfDiamonds(){
        super(new GreenfootImage("kod.png"));
        type = "Diamond";
        damageType = "damage" + type + ".png";
    }
    
    /**
     * Controls the behaviour of Hearts in terms of the attacks he performs on the world's game grid. Chooses between his 5 attacks 
     * randomly, and excutes them at ~120-act intervals. Some attacks have different weightings than others - generally, Diamonds'
     * fast attacks are significantly more likely to trigger than his slower ones. Fast attacks are followed up more quickly than slow attacks, so
     * the "120-act interval" is only an average of attack frequency.
     */
    protected void attackControl() {
        if(world.isBlankActive() == false){
            attackTimer++;
        }
        
        if (attackTimer > 120) {
            attack.get(attackIndex).play();
            attackIndex++;
            if(attackIndex > attack.size() - 1){
                attackIndex = 0;
            }
            choose = Greenfoot.getRandomNumber(11);
            //attack1 - target the vulnerability cell (stall your ass out), slow telegraph lets you sneak the attack in, often
            //attack2 - half diamonds on the board, general attack to punish lurking near the edges, semi-often
            //attack3 - attack white-then-black squares, attack requires calm/timing, not that often
            //attack4 - big diamond, punishes playing in the center, semi-often
            //attack5 - fast punish for holding still, filler attack, often
            switch(choose) {
                case 0:
                case 7:
                case 8:
                    attack1();
                    attackTimer = 60;
                    break;
                case 1:
                case 9:
                    attack2();
                    attackTimer = 0;
                    break;
                case 2:
                    attack3();
                    attackTimer = -60;
                    break;
                case 3:
                case 10:
                    attack4();
                    attackTimer = 0;
                    break;
                case 4:
                case 5:
                case 6:
                    attack5();
                    attackTimer = 60;
                    break;
            }
        }
    }
    
    /**
     * A basic attack that flashes a damage indicator on the vulnerability cell to guard it from player interference.
     * Medium-fast speed.
     */
    protected void attack1() {
        TelegraphIndicator telegraph = new TelegraphIndicator(this, vulnCell, 120, 90);
        telegraph.initNextAttackEffect(10, 15, 20, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());
    }
    
    /**
     * A strong attack that flashes half-diamonds onto the grid, either vertically or horizontally.
     * Slow speed.
     */
    protected void attack2() {
        TelegraphIndicator telegraph;
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if (i == j || checkerBoard[i].length - 1 - i == j) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 30, 75);
                    telegraph.initNextAttackEffect(5, 15, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());
                }
            }
        }
        
        if (Greenfoot.getRandomNumber(2) == 0) {
            for (int i = 0; i < 8; i++) {
                if (i != 4) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[0][i], 30, 75);
                    telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
            
            for (int i = 1; i < 7; i++) {
                if (i != 4) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[1][i], 30, 75);
                    telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
            
            for (int i = 2; i < 6; i++) {
                telegraph = new TelegraphIndicator(this, checkerBoard[2][i], 30, 75);
                telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            }
            
            for (int i = 3; i < 5; i++) {
                telegraph = new TelegraphIndicator(this, checkerBoard[3][i], 30, 75);
                telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            }
            
            for (int i = 3; i < 5; i++) {
                telegraph = new TelegraphIndicator(this, checkerBoard[5][i], 30, 75);
                telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            }
            
            for (int i = 2; i < 6; i++) {
                telegraph = new TelegraphIndicator(this, checkerBoard[6][i], 30, 75);
                telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            }
            
            for (int i = 1; i < 7; i++) {
                if (i != 4) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[7][i], 30, 75);
                    telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());     
                }
            }
            
            for (int i = 0; i < 8; i++) {
                if (i != 4) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[8][i], 30, 75);
                    telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());       
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (i != 4) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[i][0], 30, 75);
                    telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());        
                }
            }
            
            for (int i = 1; i < 7; i++) {
                if (i != 4) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[i][1], 30, 75);
                    telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());     
                }
            }
            
            for (int i = 2; i < 6; i++) {
                telegraph = new TelegraphIndicator(this, checkerBoard[i][2], 30, 75);
                telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            }
            
            for (int i = 3; i < 5; i++) {
                telegraph = new TelegraphIndicator(this, checkerBoard[i][3], 30, 75);
                telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            } 
            
            for (int i = 3; i < 5; i++) {
                telegraph = new TelegraphIndicator(this, checkerBoard[i][5], 30, 75);
                telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            }
            
            for (int i = 2; i < 6; i++) {
                telegraph = new TelegraphIndicator(this, checkerBoard[i][6], 30, 75);
                telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            }
            
            for (int i = 1; i < 7; i++) {
                if (i != 4) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[i][7], 30, 75);
                    telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());       
                }
            }
            
            for (int i = 0; i < 8; i++) {
                if (i != 4) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[i][8], 30, 75);
                    telegraph.initNextAttackEffect(7, 15, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());      
                }
            }
        }
    }
    
    /**
     * A strong attack that flashes all the black, then all the white grids respectively with damage effects.
     * Slow speed.
     */
    protected void attack3() {
        for (Cell[] row: checkerBoard) {
            for (Cell cell: row) {
                if (cell.type() == "black") {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, cell, 45, 60);
                    telegraph.initNextAttackEffect(10, 5, 10, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());
                }
            }
        }
        
        for (Cell[] row: checkerBoard) {
            for (Cell cell: row) {
                if (cell.type() == "white") {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, cell, 15, 150);
                    telegraph.initNextAttackEffect(10, 5, 10, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());
                }
            }
        }
    }
    
    /**
     * A strong attack that flashes all a large diamond onto the grid.
     * Slow speed.
     */
    protected void attack4() {
        TelegraphIndicator telegraph;
        
        telegraph = new TelegraphIndicator(this, checkerBoard[0][4], 30, 75);
        telegraph.initNextAttackEffect(7, 30, 30, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());
        
        for (int i = 3; i < 6; i++) {
            telegraph = new TelegraphIndicator(this, checkerBoard[1][i], 30, 75);
            telegraph.initNextAttackEffect(7, 30, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());    
        }

        for (int i = 2; i < 7; i++) {
            telegraph = new TelegraphIndicator(this, checkerBoard[2][i], 30, 75);
            telegraph.initNextAttackEffect(7, 30, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());    
        }
        
        for (int i = 1; i < 8; i++) {
            if (i != 4) {
                telegraph = new TelegraphIndicator(this, checkerBoard[3][i], 30, 75);
                telegraph.initNextAttackEffect(7, 30, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());    
            }
        }
        
        for (int i = 0; i < 9; i++) {
            if (!( 3 < i  && i < 5)){
                telegraph = new TelegraphIndicator(this, checkerBoard[4][i], 30, 75);
                telegraph.initNextAttackEffect(7, 30, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());     
            }
        }
        
        for (int i = 1; i < 8; i++) {
            if ( i !=  4) {
                telegraph = new TelegraphIndicator(this, checkerBoard[5][i], 30, 75);
                telegraph.initNextAttackEffect(7, 30, 30, new GreenfootImage(damageType));
                getWorld().addObject(telegraph, getX(), getY());
            }
        }
        
        for (int i = 2; i < 7; i++) {
            telegraph = new TelegraphIndicator(this, checkerBoard[6][i], 30, 75);
            telegraph.initNextAttackEffect(7, 30, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());    
        }
        
        for (int i = 3; i < 6; i++) {
            telegraph = new TelegraphIndicator(this, checkerBoard[7][i], 30, 75);
            telegraph.initNextAttackEffect(7, 30, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());    
        }
        
        telegraph = new TelegraphIndicator(this, checkerBoard[8][4], 30, 75);
        telegraph.initNextAttackEffect(7, 30, 30, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());
    }
    
    /**
     * A poke attack that directly flashes a damage effect onto the cell the player is occupying at the current moment.
     * Fast speed.
     */
    protected void attack5() {
        TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[world.findPlayerY()][world.findPlayerX()], 120, 45);
        telegraph.initNextAttackEffect(5, 90, 20, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());    
    }
    
    /**
     * Calls Boss's act() method with super.act(), then, if health is less than zero, spawns in a king of clovers after a set period of time,
     * and if there is not already one on field, and sets a flag in the world to true that Diamonds has been defeated.
     */
    public void act()
    {
        super.act();
        if(health <= 0){
            timer++;
            diamondTheme.stop();
            if(timer >= 200 && zeroKingOfClovers()){
                getWorld().addObject(new KingOfClovers(), 600, 123);
                for(int i = 0; i<5; i++){
                    world.healPlayer();
                }
                world.resetBossHealth();
                world.initializeBossHealth(world.getHealthDisplay(), cloverHealthSprite, cloverEmptyHealthSprite, 60, 100, 100);
                timer = 0;
                world.defeatDiamonds();
            }
        } else {
            diamondTheme.play();
        }
    }
    
    /**
     * Returns whether or not there is a King of Clovers on field right now.
     * 
     * @return          true if at least one King of Clovers exists in the game world.
     */
    public boolean zeroKingOfClovers(){
        kingofclovers = (ArrayList)getWorld().getObjects(KingOfClovers.class);
        if(kingofclovers.size() > 0){
            return false;
        }
        return true;
    }
}
