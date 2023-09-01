import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The player of the game. A relatively extensive class, contains the mechanisms for player movement, health, damage, parries, blanks, death, and more.
 * Is the entity controlled by the keyboard in gameplay. Has an "anchor" cell - the cell the Player currently occupies, and where much of the action takes place.
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class Player extends Actor
{
    protected Cell anchor;
    protected int health;
    protected int damage;
    protected Cell[][] checkerBoard;
    protected int iFrames;
    protected JestersRevenge world;
    protected int parryCooldown;
    protected boolean parryOnCooldown;
    
    private ArrayList<GreenfootSound> move;
    private int moveIndex;
    private ArrayList<GreenfootSound> playerattack;
    private int playerattackIndex;
    private ArrayList<GreenfootSound> takedamage;
    private int takedamageIndex;
    
    protected int blankCooldown;
    protected boolean blankOnCooldown;
    
    private GreenfootImage sprite = new GreenfootImage("player.png");
    private GreenfootImage damagedSprite = new GreenfootImage("playerDamaged.png");
    
    /**
     * Player's constructor initialises health to 5, damage to 1, iFrames to 60, parryCooldown to 0, blankCooldown to 0.
     * It also initialises several sound variables as well, mostly arrays.
     * sets its image, and defines an anchor cell that the player initially anchors to. The "anchor" cell for the Player is typically the cell it is attached to.
     * 
     * @param anchor        the initial anchor cell of the player.
     */
    public Player(Cell anchor) {
        this.anchor = anchor;
        this.setImage(sprite);
        health = 5;
        damage = 1;
        iFrames = 60;
        parryCooldown = 0;
        
        blankCooldown = 0;
        
        parryOnCooldown = false;
        blankOnCooldown = false;
        
        moveIndex = 0;
        move = new ArrayList<GreenfootSound>();
        for(int i = 0; i < 25; i++){
            move.add(new GreenfootSound("move.wav"));
        }
        
        playerattackIndex = 0;
        playerattack = new ArrayList<GreenfootSound>(5);
        for(int i = 0; i < 5; i++){
            playerattack.add(new GreenfootSound("player attack.wav"));
        }
        
        takedamageIndex = 0;
        takedamage = new ArrayList<GreenfootSound>(5);
        for(int i = 0; i < 5; i++){
            takedamage.add(new GreenfootSound("take damage2.wav"));
        }
    }
    
    /**
     * Override of Greenfoot's addedToWorld() method. When added to the world, it casts it as JestersRevenge to store it in an instance variable for easy access.
     * It also stores the world's checkerBoard (grid) onto a local variable for easy access as well.
     */
    public void addedToWorld(World w) {
        checkerBoard = ((JestersRevenge)(w)).getCheckerboard();
        world = (JestersRevenge)(w);
    }
    
    /**
     * Method to quickly update the position of the player to its anchor's coordinates.
     */
    public void updatePosition() {
        setLocation(anchor.getX(), anchor.getY());
    }
    
    /**
     * Method to completely detach from current anchor and gain a new one based on parameter passed into it.
     * 
     * @param anchor        the cell to become the new anchor.
     */
    public void getNewAnchor(Cell anchor) {
        Cell lastAnchor = this.anchor;
        anchor.acceptPlayer(this);
        this.anchor = anchor;
        lastAnchor.rejectPlayer();
    }
    
    /**
     * Returns current anchor, or the cell.
     * 
     * @param anchor        the cell to become the new anchor.
     */
    public Cell getAnchor() {
        return anchor;
    }
    
    /**
     * Checks for movement. If a movement key is pressed, the Player disattaches from current anchor to the anchor specified by the movement key.
     * Since it checks on release, not press, movement is on release as well. If the Player tries to access an anchor out of bounds,
     * then the code will not execute - so if the player tries to move out of the grid, they can't.
     */
    public void playerMovementCheck() { // player movement check
        try { // player anchors themselves onto their own x and y coords add or minus an offset in a specific direction (x or y)
            String key = Greenfoot.getKey(); // find a button that has been pressed then released            
            
            if (key == null) { // if it is null, set it to a placeholder value
                key = "";
            }
            
            // if it is the WASD keys, move the player around
            // after this executes the key variable gets reset, so you have to press multiple times to move multiple times
            if(key.equals("w")) {
                getNewAnchor(checkerBoard[world.findPlayerY() - 1][world.findPlayerX()]);
                move.get(moveIndex).play();
                moveIndex++;
                if(moveIndex > move.size() - 1){
                    moveIndex = 0;
                }
            }
            if (key.equals("a")) {
                getNewAnchor(checkerBoard[world.findPlayerY()][world.findPlayerX() - 1]);
                move.get(moveIndex).play();
                moveIndex++;
                if(moveIndex > move.size() - 1){
                    moveIndex = 0;
                }
            }
            if (key.equals("d")) {
                getNewAnchor(checkerBoard[world.findPlayerY()][world.findPlayerX() + 1]);
                move.get(moveIndex).play();
                moveIndex++;
                if(moveIndex > move.size() - 1){
                    moveIndex = 0;
                }
            }
            if (key.equals("s")) {
                getNewAnchor(checkerBoard[world.findPlayerY() + 1][world.findPlayerX()]);
                move.get(moveIndex).play();
                moveIndex++;
                if(moveIndex > move.size() - 1){
                    moveIndex = 0;
                }
            }
            
            key = "";
            
        } catch (ArrayIndexOutOfBoundsException e) { // if out of bounds
            // just, don't execute the above block of code...?
        }
    }
    
    /**
     * Checks for attacking. If the Player anchor is a vuln cell, and space is being pressed, then the boss the vuln cell belongs to is damaged by 1.
     * There is some different code that is run if the Player is fighting Spades and Hearts, but on the whole, it remains the same.
     * The vuln cell then becomes not a vuln cell.
     */
    public void attackCheck() {
        if (Greenfoot.isKeyDown("space")) {
            if (this.anchor.isAttackCell()) {
                Boss boss = anchor.getBossOfThisCell();
                if(boss.getType() == "Spade"){
                    world.damageBossB();
                }
                else
                {
                    if (boss.getType() == "Fool") {
                        if (boss.getHealth() > 13) {
                            world.damageBossB();
                        } else {
                            world.damageBoss();
                        }
                    }
                    else {
                        world.damageBoss();
                    }
                }
                
                boss.takeDamage(1);
                this.anchor.becomeNotAttackCell();
                
                playerattack.get(playerattackIndex).play();
                playerattackIndex++;
                if(playerattackIndex > playerattack.size() - 1){
                    playerattackIndex = 0;
                }
            }
        }
    }
    
    /**
     * Checks for damage. If the player touches a damageEffect, and  iFrames (incremented each act, reset when hit) are greater than 60, they'll take 1 damage.
     * Translates roughly to one second of immunity each time you get hit, to allow for a bit of recovery.
     * Also determines which sprite the player adopts based on their iFrames - there is a special sprite for iFrames below 60 (in immunity period)
     */
    public void damageCheck() {
        iFrames++;
        
        DamageEffect touchedDamageEffect = (DamageEffect) getOneIntersectingObject(DamageEffect.class);
        
        if (touchedDamageEffect != null) {
            if (touchedDamageEffect.getImage().getTransparency() == 255) {
                if (iFrames > 60) {
                    world.damagePlayer();
                    iFrames = 0;
                    setImage(damagedSprite);
                    world.hitPenalty();
                    
                    takedamage.get(takedamageIndex).play();
                    takedamageIndex++;
                    if(takedamageIndex > takedamage.size() - 1){
                        takedamageIndex = 0;
                    }
                }    
            } 
        }
        
        if (iFrames < 60) {
            setImage(damagedSprite);
        } else {
            setImage(sprite);
        }    
    }
    
    /**
     * Checks for parrying, and its cooldown. If the button is pressed, but the cooldown is still active, nothing happens.
     * If the button is pressed but the cooldown is not active, a parry occurs.
     */
    public void parryCheck() {
        if(parryCooldown > 0){
            parryOnCooldown = true;
            parryCooldown--;
        }
        if(parryCooldown == 0){
            parryOnCooldown = false;
            world.parryCooldown(false);
        }
        if(parryOnCooldown == false){
            if (Greenfoot.isKeyDown("j")) {
                parry();
                playerattack.get(playerattackIndex).play();
                playerattackIndex++;
                if(playerattackIndex > playerattack.size() - 1){
                    playerattackIndex = 0;
                }
            }
        }
        
    }
    
    /**
     * A parry. Essentially just a way of setting iFrames in a specific way to give you 90 acts of guaranteed immunity, with some added
     * setting of values such as cooldowns or calling a World method to make sure things are going smoothly and things work together.
     */
    public void parry() {
        iFrames = -30;
        parryCooldown = 150; //Puts parry on cooldown for 2.5seconds
        world.parryCooldown(true);//Updates parry icon.
        world.parryPenalty();
    }
    
    /**
     * Checks for blanks, and its cooldown. If the button is pressed, but the cooldown is still active, nothing happens.
     * If the button is pressed but the cooldown is not active, a blank appears.
     */
    public void blankCheck() {
        if(blankCooldown > 0){
            blankOnCooldown = true;
            blankCooldown--;
        }
        if(blankCooldown == 0){
            parryOnCooldown = false;
            world.blankCooldown(false);
        }
        if(blankOnCooldown == false){
            if (Greenfoot.isKeyDown("k")) {
                blank();
                playerattack.get(playerattackIndex).play();
                playerattackIndex++;
                if(playerattackIndex > playerattack.size() - 1){
                    playerattackIndex = 0;
                }
            }
        }
        
    }
    
    /**
     * A blank. Deletes any TelegraphIndicators and damageEffects on the board, and prevents them from progressing to their damaging states.
     */
    public void blank() {
        world.blankActive();
        world.blankCooldown(true); //Updates the blank icon.
        blankCooldown = 20*60; //Puts blank on cooldown for 20 seconds
        world.bombPenalty();
    }
    
    /**
     * Act - update the checkerBoard by access to an instance variable, check for movement, position, blanks, parrise, damage, and attacks.
     */
    public void act()
    {
        checkerBoard = ((JestersRevenge)(getWorld())).getCheckerboard();
        playerMovementCheck();
        updatePosition();
        blankCheck();
        parryCheck();
        damageCheck();
        attackCheck();        
    }
}
