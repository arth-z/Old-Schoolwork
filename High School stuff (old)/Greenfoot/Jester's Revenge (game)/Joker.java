import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * <p>
 * Joker is the final boss - or I suppose, his name would be closer to something like "Fool" or "Moth".
 * </p>
 * 
 * <p>
 * Final boss. Two phases. Uses every attack and every suit...probably? I can figure something out.
 * Randomness, fast, focused, strong, etc., all the stops?
 * Think...Moth-y? But not exactly, maybe.
 * </p>
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class Joker extends Boss
{
    private int attack1Timer;
    private int timer;
    private GreenfootSound jokerP1Theme;
    private GreenfootSound jokerP2Theme;
    private GreenfootSound jokerTransitionScream;
    private GreenfootSound jokerTransitionStun;
    private boolean jokerTransitionScreamPlayed;
    private boolean jokerTransitionStunPlayed;
    /**
     * Constructor for spades, initialises a few string variables (for boss-unique sprites) and passes in a relevant GreenfootImage into super() as its image.
     */
    public Joker() {
        super(new GreenfootImage("fool.jpg"));
        overrideHealth();
        type = "Fool";
        damageType = "damage" + type + ".png";
        attack1Timer = 0;
        timer = 0;
        jokerTransitionScreamPlayed = false;
        jokerTransitionStunPlayed = false;
        jokerTransitionScream = new GreenfootSound("grimm_scream.wav");
        jokerTransitionStun = new GreenfootSound("radiance_hit.wav");
        jokerP1Theme = new GreenfootSound("hecatia.wav");
        jokerP2Theme = new GreenfootSound("junko.wav");
    }
    
    private void overrideHealth() {
        health = 26;
    }
    
    /**
     * Joker's attacks
     */
    protected void attackControl() {
        if(world.isBlankActive() == false){
            attackTimer++;
        }
        
        // this is always active, cuz fuck you
        attack1Timer++;
        
        // wanna try getting a phase 2 thing set up
        int interval = health > 13 ? 85 : 75;

        if (attackTimer > interval) {
            attack.get(attackIndex).play();
            attackIndex++;
            if(attackIndex > attack.size() - 1){
               attackIndex = 0;
            }
            choose = health > 13 ? Greenfoot.getRandomNumber(6) : Greenfoot.getRandomNumber(11);
            switch(choose) {
                case 0:
                    attack2();
                    attackTimer = 0;
                    break;
                case 1:
                    attack3();
                    attackTimer = -10;
                    break;
                case 2:
                    attack4();
                    attackTimer = 0;
                    break;
                case 3:
                    attack5();
                    attackTimer = 0;
                    break;
                case 4:
                    attack6();
                    attackTimer = 10;
                    break;
                case 5:
                    attack7();
                    attackTimer = 10;
                    break;
                case 6:
                    attack8();
                    attackTimer = -80;
                    break;
                case 7:
                    attack9();
                    attackTimer = -20;
                    break;
                case 8:
                    attack10();
                    attackTimer = -15;
                    break;
                case 9: 
                    attack11();
                    attackTimer = -30;
                    break;
                case 10:
                    attack1();
                    attackTimer = -20;
                    break;
            }
        }
    }
    
    /*
     * We call upon the Moth, who seeks amongst the trees of the wood...
     * Nonstop pressure, is basically always active, seeks you.
     */
    public void attack1() {
        

        damageType = "damageSpade.png";
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
                    telegraph.initNextAttackEffect(5, 5, 17, new GreenfootImage(damageType));
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
                    telegraph.initNextAttackEffect(5, 5, 17, new GreenfootImage(damageType));
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
                    telegraph.initNextAttackEffect(5, 5, 17, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
        damageType = "damageFool.png";
    }
    
    /*
     * We call upon the Moth, who is dappled...
     * Ripped straight from clovers - slow-acting RNG spread.
     */
    public void attack2() {        
        if (health < 13) {
            damageType = "damageClover.png";

        }
        TelegraphIndicator telegraph;
        for (int i = 0; i < checkerBoard.length*3 + Greenfoot.getRandomNumber(6); i++) {
            telegraph = new TelegraphIndicator(this, 
            checkerBoard[Greenfoot.getRandomNumber(checkerBoard.length)][Greenfoot.getRandomNumber(checkerBoard.length)], 30, 90);
            telegraph.initNextAttackEffect(7, 7, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
        }
        damageType = "damageFool.png";
    }
    
    /*
     * Covers the entire arena - there are safe rows, and two safe columns.
     */
    public void attack3() {
        TelegraphIndicator telegraph;
        int safe1 = Greenfoot.getRandomNumber(checkerBoard.length);
        int safe2 = Greenfoot.getRandomNumber(checkerBoard.length);
        int safe3 = Greenfoot.getRandomNumber(checkerBoard.length);
        int safe4 = Greenfoot.getRandomNumber(checkerBoard.length);
        
        while (safe1 == safe2 || safe1 == safe3 || safe1 == safe4 || safe2 == safe3 || safe2 == safe4 || safe3 == safe4) {
            safe1 = Greenfoot.getRandomNumber(checkerBoard.length);
            safe2 = Greenfoot.getRandomNumber(checkerBoard.length);
            safe3 = Greenfoot.getRandomNumber(checkerBoard.length);
            safe4 = Greenfoot.getRandomNumber(checkerBoard.length);
        }
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard.length; j++) {
                if (i != safe1 && j != safe2 && i != safe3 && j != safe4) {
                    telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 20, 80);
                    telegraph.initNextAttackEffect(5, 10, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());
                }
            }
        }
    }
    
    public void attack4() {
        int vulnX = 0;
        int vulnY = 0;
        
        for (Cell[] array: checkerBoard) {
            for (Cell cell: array) {
                if (cell.isAttackCell()) {
                    vulnX = cell.getXCoord();
                    vulnY = cell.getYCoord();
                }
            }
        }
        
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == vulnY - 1 && j == vulnX + 1) || 
                (i == vulnY + 1 && j == vulnX + 1) || 
                (i == vulnY - 1 && j == vulnX - 1) || 
                (i == vulnY + 1 && j == vulnX - 1) || 
                (i == vulnY && j == vulnX - 1) || 
                (i == vulnY && j == vulnX + 1) || 
                (i == vulnY + 1 && j == vulnX) ||
                (i == vulnY - 1 && j == vulnX)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 90, 90);
                    telegraph.initNextAttackEffect(5, 10, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());  
                }
            }
        }
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == vulnY && j == vulnX - 2) || 
                (i == vulnY && j == vulnX + 2) || 
                (i == vulnY + 2 && j == vulnX) ||
                (i == vulnY - 2 && j == vulnX) || 
                (i == vulnY - 2 && j == vulnX - 2) || 
                (i == vulnY + 2 && j == vulnX + 2) || 
                (i == vulnY + 2 && j == vulnX - 2) || 
                (i == vulnY - 2 && j == vulnX + 2)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 60, 120);
                    telegraph.initNextAttackEffect(5, 10, 5, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
        
        if (health < 13) {
            damageType = "damageDiamond.png";
            
            TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[world.findPlayerY()][world.findPlayerX()], 120, 45);
            telegraph.initNextAttackEffect(5, 90, 20, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());   
            
            damageType = "damageFool.png";
        }
    }
    
    public void attack5() {
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == world.findPlayerY() && j == world.findPlayerX() - 2) || 
                (i == world.findPlayerY() && j == world.findPlayerX() + 2) || 
                (i == world.findPlayerY() + 2 && j == world.findPlayerX()) ||
                (i == world.findPlayerY() - 2 && j == world.findPlayerX()) || 
                (i == world.findPlayerY() + 2 && j == world.findPlayerX() - 2) || 
                (i == world.findPlayerY() - 2 && j == world.findPlayerX() - 2) ||
                (i == world.findPlayerY() + 2 && j == world.findPlayerX() + 2) ||
                (i == world.findPlayerY() - 2 && j == world.findPlayerX() + 2)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 120, 60);
                    telegraph.initNextAttackEffect(5, 10, 60, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == world.findPlayerY() - 1 && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() + 1 && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() - 1 && j == world.findPlayerX() - 1) || 
                (i == world.findPlayerY() + 1 && j == world.findPlayerX() - 1) || 
                (i == world.findPlayerY() && j == world.findPlayerX() - 1) || 
                (i == world.findPlayerY() && j == world.findPlayerX() + 1) || 
                (i == world.findPlayerY() + 1 && j == world.findPlayerX()) ||
                (i == world.findPlayerY() - 1 && j == world.findPlayerX())) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 90, 90);
                    telegraph.initNextAttackEffect(5, 10, 30, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
        
        TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[world.findPlayerY()][world.findPlayerX()], 60, 120);
        telegraph.initNextAttackEffect(5, 10, 5, new GreenfootImage(damageType));
        getWorld().addObject(telegraph, getX(), getY());  
        
        if (health < 13) {
            damageType = "damageDiamond.png";
            telegraph = new TelegraphIndicator(this, vulnCell, 120, 90);
            telegraph.initNextAttackEffect(10, 15, 20, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
            
            damageType = "damageFool.png";
        }
    }
    
    public void attack6() {
        int vulnX = 0;
        int vulnY = 0;
        
        for (Cell[] array: checkerBoard) {
            for (Cell cell: array) {
                if (cell.isAttackCell()) {
                    vulnX = cell.getXCoord();
                    vulnY = cell.getYCoord();
                }
            }
        }
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard.length; j++) {
                if ((i == vulnY ^ j == vulnX)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 45, 80);
                    telegraph.initNextAttackEffect(5, 10, 10, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());  
                    
                    telegraph = new TelegraphIndicator(this, checkerBoard[checkerBoard.length - 1 - i][checkerBoard.length - 1 -j], 45, 80);
                    telegraph.initNextAttackEffect(5, 10, 10, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());  
                }
            }
        }
    }
    
    public void attack7() {
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard.length; j++) {
                if (i == world.findPlayerY() ^ j == world.findPlayerX()) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 45, 80);
                    telegraph.initNextAttackEffect(5, 10, 10, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());  
                    
                    telegraph = new TelegraphIndicator(this, checkerBoard[checkerBoard.length - 1 - i][checkerBoard.length - 1 - j], 45, 80);
                    telegraph.initNextAttackEffect(5, 10, 10, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY()); 
                }
            }
        }
    }
    
    protected void attack8() {
        
        damageType = "damageHeart.png";
        
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
        
        damageType = "damageFool.png";
    }
    
    public void attack9() {
        int vulnX = 0;
        int vulnY = 0;
        
        for (Cell[] array: checkerBoard) {
            for (Cell cell: array) {
                if (cell.isAttackCell()) {
                    vulnX = cell.getXCoord();
                    vulnY = cell.getYCoord();
                }
            }
        }
        
        damageType = "damageSpade.png";
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == vulnY - 2 && j == vulnX) || 
                (i == vulnY - 1 && j == vulnX + 1) || 
                (i == vulnY - 1 && j == vulnX - 1) || 
                (i == vulnY && j == vulnX - 2) ||
                (i == vulnY && j == vulnX) || 
                (i == vulnY && j == vulnX + 2) || 
                (i == vulnY + 1 && j == vulnX + 1) || 
                (i == vulnY + 1 && j == vulnX -1)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 120, 60);
                    telegraph.initNextAttackEffect(5, 5, 17, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == vulnY - 1 && j == vulnX) || 
                (i == vulnY && j == vulnX + 1) || 
                (i == vulnY && j == vulnX - 1)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 90, 105);
                    telegraph.initNextAttackEffect(5, 5, 17, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
        
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if ((i == vulnY - 2 && j == vulnX) || 
                (i == vulnY - 1 && j == vulnX + 1) || 
                (i == vulnY - 1 && j == vulnX - 1) || 
                (i == vulnY && j == vulnX - 2) ||
                (i == vulnY && j == vulnX) || 
                (i == vulnY && j == vulnX + 2) || 
                (i == vulnY + 1 && j == vulnX + 1) || 
                (i == vulnY + 1 && j == vulnX -1)) {
                    TelegraphIndicator telegraph = new TelegraphIndicator(this, checkerBoard[i][j], 60, 150);
                    telegraph.initNextAttackEffect(5, 5, 17, new GreenfootImage(damageType));
                    getWorld().addObject(telegraph, getX(), getY());    
                }
            }
        }
        
        damageType = "damageFool.png";

    }
    
    public void attack10() {
        
        damageType = "damageDiamond.png";
        
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
        
        damageType = "damageFool.png";
    }
    
    public void attack11() {
        damageType = "damageClover.png";
        
        TelegraphIndicator telegraph;
        for (int i = 0; i < checkerBoard.length*7 + Greenfoot.getRandomNumber(22); i++) {
            telegraph = new TelegraphIndicator(this, 
            checkerBoard[Greenfoot.getRandomNumber(checkerBoard.length)][Greenfoot.getRandomNumber(checkerBoard.length)], 
            20, 90);
            telegraph.initNextAttackEffect(7, 7, 30, new GreenfootImage(damageType));
            getWorld().addObject(telegraph, getX(), getY());
        }
        
        damageType = "damageFool.png";
    }
    
    /**
     * Act - calls Boss's act() method with super.act(). If health less or equal to zero, sets a flag in the world to true that Hearts has been defeated 
     */
    public void act()
    {
        if (health > 13) { // phase 1
            super.act();
            jokerP1Theme.play();
        } 
        else if (health < 13) { // phase 2
            super.act();        
            jokerP2Theme.play();
        }
        else { // phase transition
            checkerBoard = ((JestersRevenge)(getWorld())).getCheckerboard();
            maintainVulnCell();
            attackTimer = -20;
            attack1Timer = -10;
            
            if(jokerP1Theme.getVolume() > 0) {
                jokerP1Theme.setVolume(jokerP1Theme.getVolume() - 1);
            } else {
                jokerP1Theme.stop();
            }
            
        }
        
        // VFX to enter phase 1
        if (health == 12 && !jokerTransitionScreamPlayed) {
            jokerTransitionScreamPlayed = true;
            getWorld().addObject(new PhaseChangeEffect(1, new GreenfootImage("bigWhiteScreen.jpg"), true), 600, 400);
            jokerTransitionScream.play();
        }
        
        if (health == 13 && !jokerTransitionStunPlayed) {
            jokerTransitionStunPlayed = true;
            clearBoard();
            getWorld().addObject(new PhaseChangeEffect(1, new GreenfootImage("bigWhiteScreen.jpg"), false), 600, 400);
            jokerTransitionStun.play();
        }
        
        if (health <= 0) {
            timer++;
            if (timer > 200) {
                world.defeatJoker();                
            }
        }
    }
}
