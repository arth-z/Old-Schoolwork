import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A grid-based, 2d "bullet-hell" type boss rush game where you play as a star, manuevering around a checkerboard against corrupted kings
 * wielding suits of cards. Deliberately meant to be challenging, requiring quick reflexes, pattern recognition, and some memorisation.
 * Themed around, you guessed it, card suits, and playing cards in general - in this case, the player is the Ace of the imagined "fifth" suit of cards,
 * Stars. In a world where each card is personified into sentient beings, the Ace of Stars isgoing after the Kings of Hearts, Spades, 
 * Clovers, and Diamonds after they've been corrupted by the Jester, who is sadly not in the game due to time constraints. You can read more
 * about the lore in the instructions menu.
 * 
 * By the way, slow down Greenfoot's project speed if you want to trivialise the game. 
 * Your movement and attacks are determined by how fast you can press the buttons, and affected not as much by the speed of the game. 
 * Bosses are still dependent on game speed for their attacks to be quick. Death animations are still going to be slow with a slow game speed,
 * but you can just speed it up then slow it back down to get by those quickly.
 * 
 * <p>
 * Features: </p>
 * <p> - Two solo boss fights, one duo boss fight </p>
 * <p> - Player abilities such as a "parry" and a "blank" (or a "bomb") </p>
 * <p> - High scores emphasizing skill, speed, and success. </p>
 * <p> - Grid-based movement and attacks emphasizing precision and speed </p>
 * <p> - Unique, difficult bosses with interesting attacks and engaging gameplay </p>
 *  
 *  <p>
 *  Issues: </p>
 * <p> - Ease of gameplay is dependent on game lag (laggier = easier) </p>
 * <p> - Boss death animation is inconsistent </p>
 * <p> - Sometimes, two vulnerability cells appear and only one works </p>
 * <p> - Might be a bit inacessible for those with slow reflexes and low focus </p>
 * 
 * <p>
 * Credits:
 * </p>
 * 
 * <p>
 *All king cards draw by Mak_art.
 *Links: 
 *</p>
 *<p>
 *https://www.istockphoto.com/vector/the-king-a-skeleton-gm150019482-13742698
 *</p>
 *<p>
 *https://www.istockphoto.com/vector/the-king-a-skeleton-gm150017649-13744755
 *</p>
 *<p>
 *https://www.istockphoto.com/vector/the-king-a-skeleton-gm152035083-13744519
 *</p>
 *<p>
 *https://www.istockphoto.com/vector/the-king-a-skeleton-gm150021476-13741369
 *</p>
 *
 *<p>
 *Attack suits:
 *https://www.crushpixel.com/stock-vector/heart-diamond-clover-ace-poker-3762829.html
 *</p>
 *
 *<p>
 *Health suits:
 *https://www.shutterstock.com/image-vector/design-card-suits-clubs-diamonds-hearts-388005556
 *</p>
 *
 *<p>
 *Damaged Health suits: Drawn by Michael
 *</p>
 *
 *<p>
 *Reticle:
 *https://calamitymod.fandom.com/wiki/Daawnlight_Spirit_Origin
 *</p>
 *
 *<p>
 *Black boxes inspired by Undertale
 *</p>
 *
 *<p>
 *Title screen background:
 *https://www.crushpixel.com/stock-vector/decorative-seamless-pattern-vintage-gothic-2596753.html
 *</p>
 *
 *<p>
 *Button design
 *https://www.shutterstock.com/image-vector/vector-buttons-halloweengothic-style-1424771183
 *</p>
 *
 *<p>
 *Player icon drawn by Michael
 *</p>
 * <p>
 *Parry icon:
 *https://warframe.fandom.com/wiki/Pillage
 * </p>
 * 
 * <p>
 *Blank icon:
 *https://warframe.fandom.com/wiki/Hysteria
 *</p>
 *
 *<p>
 *Stage Curtains
 *https://www.moaw.art/post/dress-up-disco-buttons-and-sliders-for-icm
 *</p>
 *
 *<p>
 *Mainworld background
 *https://t4.ftcdn.net/jpg/01/21/00/45/360_F_121004593_GE6o1XUd5wcQigS5WKxv1AP35s1oIwYO.jpg
 *</p>
 *
 *<p>
 *Boss death animation: Enter the gungeon death reticle
 *https://www.youtube.com/watch?v=al4ezE4Rc0s
 *</p>
 *
 *<p>
 *Photo editing softwares:
 *pixlrX: https://pixlr.com/x/#editor
 *https://www.pixilart.com/draw
 *</p>
 *<p>
 *GifImage class provided by Greenfoot (project -> edit -> import class)
 *</p>
 *<p>
 *SuperSmoothMover class provided by Poul Henriksen, Michael Kolling, Neil Brown, modified by Jordan Cohen
 *</p>
 *<p>
 *ScoreBoard class provided by Jordan Cohen, at https://www.greenfoot.org/scenarios/27256
 * </p>
 * 
 * <p>
 * Victory screen provided by kindpng at https://www.kindpng.com/imgv/iRTooRi_victory-png-images-word-victory-transparent-png/
 * </p>
 * <p>
 * Defeat screen provided by https://www.seekpng.com/ima/u2w7w7a9y3a9w7o0/
 * </p>
 * 
 * <p> Music: </p>
 * <p> Diamonds' theme - "Lace" by Christopher Larkin - https://christopherlarkin.bandcamp.com/track/lace </p>
 * <p> Clovers' theme - "String Quartet No. 8 - Allegro" by Dmitri Shostakovich - https://www.youtube.com/watch?v=-0nKJoZY64A </p>
 * <p> Spades' & Hearts', duo - "The Shroud" by Stuart Chatwood - https://www.youtube.com/watch?v=UBLqu05bX3k </p>
 * <p> Spades' & Hearts', solo - "Dies irae" by Wolfgang Amadeus Mozart - https://www.youtube.com/watch?v=RKJur8wpfYM </p>
 * 
 * <p> Sound effects: </p>
 * <p> Boss attack - "woosh" by Youtuber StephenPog - https://www.youtube.com/watch?v=AnH6ODYlDOI </p>
 * <p> Player hit - from Undertale by Toby Fox - https://www.youtube.com/watch?v=Dzwdr_RdFa0 </p>
 * <p> Player movement - from chess.com - https://www.youtube.com/watch?v=7skwR49UhqA </p>
 * <p> Player attack, blank, parry - from https://soundeffect-lab.info/sound/battle/ </p>
 * @author Michael Liu, Steven Lu, Arthur Zeng
 * @version 5.00
 */
public class JestersRevenge extends World
{
    
    private Cell[][] checkerBoard;
    private DeathScreen death;
    private WinScreen win;
    
    
    
    //Player related variables
    private Player player;
    private int health = 5;
    private Health[] playerHealth = new Health[health];
    private int score;
    private boolean blank = false;
    private boolean isBlankOnCooldown = false;
    private boolean isParryOnCooldown = false;
    private int blankTimer = 120;

    //UI images
    private GreenfootImage parrySprite = new GreenfootImage("parryIcon.png");
    private GreenfootImage cooldownParrySprite = new GreenfootImage("cooldownParryIcon.png");

    private GreenfootImage blankSprite = new GreenfootImage("blankIcon.png");
    private GreenfootImage cooldownBlankSprite = new GreenfootImage("cooldownBlankIcon.png");
    
    private GreenfootImage statInterfaceSprite = new GreenfootImage("statInterface.png");
    private GreenfootImage healthInterfaceSprite = new GreenfootImage("healthInterface.png");
    private GreenfootImage bossHealthInterfaceSprite = new GreenfootImage("bossHealthInterface.png");
    
    private GreenfootImage StageSprite = new GreenfootImage("stage.png");
    
    
    private GreenfootImage playerHealthSprite = new GreenfootImage("Health.png");
    private GreenfootImage emptyPlayerHealthSprite = new GreenfootImage("emptyHealth.png");
    
    private Icon parryIcon, blankIcon;
    private StatInterface statInterface = new StatInterface(60, statInterfaceSprite);
    private StatInterface healthInterface = new StatInterface(10, healthInterfaceSprite);
    private StatInterface bossHealthInterface = new StatInterface(10, bossHealthInterfaceSprite);
    private Image stage = new Image(StageSprite);
    
    
    //Arrays for boss health visuals
    private Health[] bossHealthDisplay = new Health[13];
    private Health[] bossHealthDisplayB = new Health[13];
    
    /**
     * Getter for bossHealthDisplay.
     * 
     * @return Health[]         bossHealthDisplay
     */
    public Health[] getHealthDisplay(){
        return bossHealthDisplay;
    }
    
    /**
     * Getter for bossHealthDisplayB.
     * 
     * @return Health[]         bossHealthDisplayB
     */
    public Health[] getHealthDisplayB(){
        return bossHealthDisplayB;
    }
    
    //Health visuals for first boss
    private GreenfootImage diamondHealthSprite = new GreenfootImage("healthDiamond.png");
    private GreenfootImage diamondEmptyHealthSprite = new GreenfootImage("emptyHealthDiamond.png");
    
    private boolean diamondsDefeated, cloversDefeated, spadesDefeated, heartsDefeated, jokerDefeated;
    
    /**
     * Toggles the diamondsDefeated variable to true.
     */
    public void defeatDiamonds() {
        diamondsDefeated = true;
    }
    
    /**
     * Toggles the cloversDefeated variable to true.
     */
    public void defeatClovers() {
        cloversDefeated = true;
    }

    /**
     * Toggles the heartsDefeated variable to true.
     */
    public void defeatHearts() {
        heartsDefeated = true;
    }
    
    /**
     * Toggles the spadesDefeated variable to true.
     */
    public void defeatSpades() {
        spadesDefeated = true;
    }
    
    /**
     * Toggles the jokerDefeated variable to true.
     */
    public void defeatJoker() {
        jokerDefeated = true;
    }
    
    private int actsTaken;
    
    /**
     * Evaluates the final score of the game. Adds 10000, 20000, 25000 for each bossfight (25000 for both participants in duo fight).
     * Then sets score to itself/secondsTheGameLasted * health (just 1 if health is zero and you died).
     */
    public void evaluateScore() {
        if (diamondsDefeated) {
            score += 10000;
        }
        
        if (cloversDefeated) {
            score += 20000;
        }
        
        if (spadesDefeated) {
            score += 15000;
        }
        
        if (heartsDefeated) {
            score += 15000;
        }
        
        if (jokerDefeated) {
            score += 40000;
        }
        
        score -= hitsTaken*2500;
        score -= bombsUsed*2000;
        score -= parriesUsed*1500;
        score = score/(actsTaken/60);
    }
    
    //Boss Variables
    private int bossHealth = 13;
    private int bossBHealth = 13;
    private int bossCHealth = 26;
    private int hitsTaken = 0;
    private int bombsUsed = 0;
    private int parriesUsed = 0;
    
    public void hitPenalty() {
        hitsTaken++;
    }
    
    public void bombPenalty() {
        bombsUsed++;
    }
    
    public void parryPenalty() {
        parriesUsed++;
    }
    
    /**
     * Setss boss health (visual element) to 13.
     */
    public void resetBossHealth(){
        bossHealth = 13;
    }
    
    private ArrayList<Player> playerr;
    private ArrayList<KingOfHearts> kingofhearts;
    private ArrayList<KingOfSpades> kingofspades;
    
    private ArrayList<KingOfDiamonds> kingofdiamonds;
    private int KODCount;
    
    private GreenfootSound heartSpadeTheme = new GreenfootSound("astalos.wav");
    private GreenfootSound deathTheme = new GreenfootSound("astalos_climax.wav");
    /**
     * Constructor for the world. Initialises a whole bunch of variables, paint order, checkerBoard, background, size, then adds
     * the Player, checkerBoard, and other helpful UI elements into the world.
     */
    public JestersRevenge()
    {    
        
        super(1200, 800, 1); 
        setBackground("background.jpg");
        // declare 2d array of cells, "staging ground" for the game
        checkerBoard = new Cell[9][9];
        
        heartSpadeTheme.setVolume(90);
        
        setPaintOrder(BossDeath.class, TelegraphIndicator.class, Player.class, Effect.class, Boss.class, VulnCellIndicator.class, 
        Health.class, Cell.class, Icon.class);
                
        //init score variables
        spadesDefeated = false;
        heartsDefeated = false;
        cloversDefeated = false;
        diamondsDefeated = false;
        jokerDefeated = false;
        
        //Initializing the checkerboard
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                checkerBoard[i][j] = new Cell(j % 2 == 0 ? "black" : "white");
            }
            i++;
            for (int j = 0; j < 9; j++) {
                checkerBoard[i][j] = new Cell(j % 2 == 0 ? "white" : "black");
            }
        }
        
        for(int i = 0; i< 9; i++){
            checkerBoard[8][i] = new Cell(i % 2 == 0 ? "black" : "white");
        }
        
        //Adding the checkerboard into the world
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                addObject(checkerBoard[i][j], (j+1)*60 + this.getWidth()/2 - 300, (i+1)*60 + this.getHeight()/2 - 180);
            }
        }
        
        //Initializing Objects
        player = new Player(checkerBoard[4][4]);
        int temp = 65;
        
        //Initializing player health visual onto the interface
        for(int i = 0; i<health; i++){
            playerHealth[i] = new Health(playerHealthSprite, emptyPlayerHealthSprite);
        }
        for(int i = 0; i<health; i++){
            
            addObject(playerHealth[i], temp, 100);
            temp += 50;
        }
        
        //Initializing the boss health visuals for king of diamonds.
        initializeBossHealth(bossHealthDisplay, diamondHealthSprite, diamondEmptyHealthSprite, 60, 100, 100);
        
        parryIcon = new Icon(parrySprite,cooldownParrySprite);
        blankIcon = new Icon(blankSprite,cooldownBlankSprite);
       
        //Adding Objects
        checkerBoard[4][4].acceptPlayer(player);
        
        addObject(statInterface, 170, 240);
        statInterface.addIcon(parryIcon);
        statInterface.addIcon(blankIcon);
        
        addObject(healthInterface, 170, 100);
        addObject(bossHealthInterface, 1040, 240);
        
        addObject(stage, this.getWidth()/2, 120);
        addObject(player, 600, 400);
        KODCount = 0;
        
    }
    
    /**
     * Getter for the world's checkerboard (2d grid).
     * 
     * @return Cell[][]         the world's 2d grid, checkerboard.
     */
    public Cell[][] getCheckerboard() {
        return checkerBoard;
    }
    
    
    /**
     * Getter for the player's y position, found using iterating for loop (return first var).
     * 
     * @return int              player's Y position on the grid (-1 if not on grid)
     */
    public int findPlayerY() { // method to get the Y coord of the player, simple search that returns iterating var
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if (checkerBoard[i][j].hasPlayer()) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    /**
     * Getter for the player's x position, found using iterating for loop (return second var).
     * 
     * @return int              player's X position on the grid (-1 if not on grid)
     */
    public int findPlayerX() { // method to get the X coord of the player, simple search then return iterating var
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if (checkerBoard[i][j].hasPlayer()) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    /**
     * Visually breaks a player hitpoint on the player health interface.
     */
    public void damagePlayer(){ //Visually break a player hitpoint on the player health interface
        if(health > 1){
            playerHealth[health-1].empty();
            health--;
        }
        else
        {
            die();
        }
    }
   
    /**
     * Visually repairs a player hitpoint on the player health interface.
     */
    public void healPlayer(){ //Visually repair a player hitpoint on the player health interface
        if(health < 5){
            playerHealth[health].fill();
            health++;
        }
    }
    
    /**
     * Visually break a boss's hitpoint on the player health interface.
     */
    public void damageBoss(){//Visually break a boss hitpoint on the player health interface
        if(bossHealth > 0){
            bossHealthDisplay[bossHealth-1].empty();
            bossHealth--;
        }
        
    }
    
    /**
     * Visually break one of King of Spades' hitpoints on the player health interface.
     */
    public void damageBossB(){
        if(bossBHealth > 0){
            bossHealthDisplayB[bossBHealth-1].empty();
            bossBHealth--;
        }
        
    }
    
    
    /**
     * Mutators for the boolean parryCooldown.
     * 
     * @param cooldown          what you want parryCooldown to become (boolean variable, true or false)
     */
    public void parryCooldown(boolean cooldown){
        isParryOnCooldown = cooldown;
    }
    
    /**
     * Mutators for the boolean blankCooldown.
     * 
     * @param cooldown          what you want blankCooldown to become (boolean variable, true or false)
     */
    public void blankCooldown(boolean cooldown){
        isBlankOnCooldown = cooldown;
    }
    
    /**
     * Method to continuously update UI elements/icons, called in act().
     */
    public void updateIcons(){
        if(isParryOnCooldown){
            parryIcon.cooldown(true);
        }
        else
        {
            parryIcon.cooldown(false);
        }
        if(isBlankOnCooldown){
            blankIcon.cooldown(true);
        }
        else
        {
            blankIcon.cooldown(false);
        }
    }
    
    /**
     * Getter for whether or not the player's just activated a blank.
     * 
     * @return boolean          if a blank is active right now
     */
    public boolean isBlankActive(){
        return blank; 
    }
    
    /**
     * Mutator for the player's blank - activates it, sets it to true.
     */
    public void blankActive(){
        blank = true;
    }
    
    /**
     * Called when player health reaches 0, moves the world to the deathScreen after evaluating score.
     */
    public void die(){
        evaluateScore();
        death = new DeathScreen(score); //Transfers score to endscreen 
        Greenfoot.setWorld(death);
    }
    
    /**
     * Called when all bosses have been defeated, moves the world to the victoryScreen after evaluating score (15% bonus).
     */
    public void win(){
        evaluateScore();
        score *= 1.15;
        win = new WinScreen(score); //Transfers score to endscreen 
        Greenfoot.setWorld(win);
    }
    
    /**
     * Checks if the King of Hearts is present in the world.
     * 
     * @return boolean          true if Hearts is in the world, false if not.
     */
    public boolean kingOfHeartsPresent(){
        kingofhearts = (ArrayList)getObjects(KingOfHearts.class);
        if(kingofhearts.size() > 0){
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the King of Spades is present in the world.
     * 
     * @return boolean          true if Spades is in the world, false if not.
     */
    public boolean kingOfSpadesPresent(){
        kingofspades = (ArrayList)getObjects(KingOfSpades.class);
        if(kingofspades.size() > 0){
            return true;
        }
        return false;
    }
    
    private GreenfootImage foolHealthSprite = new GreenfootImage("healthFool.png");
    private GreenfootImage emptyFoolHealthSprite = new GreenfootImage("emptyHealthFool.png");
    
    /**
     * Act. Spawns a king of diamonds if one hasn't been spawned already, increments the actsTaken variable, 
     * decrements a blank timer if a blank is active so it doesn't last forever, updates icons, and checks if all bosses have been defeated
     * (if yes, move to victory)
     */
    public void act() {
        actsTaken++;
        
        if(kingOfHeartsPresent() && kingOfSpadesPresent()){
            heartSpadeTheme.playLoop();
        }
        if(kingOfHeartsPresent() && !kingOfSpadesPresent() || !kingOfHeartsPresent() && kingOfSpadesPresent()){
            heartSpadeTheme.stop();
            deathTheme.playLoop();
        }
        if(!kingOfHeartsPresent() && !kingOfSpadesPresent()){
            deathTheme.stop();
        }

        
        if(checkPlayerPresent() && zeroKingOfDiamonds() && KODCount == 0){
            addObject(new KingOfDiamonds(), 600, 123);
            KODCount++;
        }
        
        
        if(blank == true){
            blankTimer--;
        }
        if(blankTimer == 0){
            blank = false;
            blankTimer = 120;
        }
        updateIcons();
        
        
        if (diamondsDefeated && heartsDefeated && cloversDefeated && spadesDefeated) {
            if (jokerDefeated) {
                win();
            }
            else {
                if (getObjects(Joker.class).size() == 0) {
                    addObject(new Joker(), 600, 123);
                    for(int i = 0; i<5; i++){
                        healPlayer();
                    }
                    resetBossHealth();
                    initializeBossHealth(getHealthDisplay(), foolHealthSprite, emptyFoolHealthSprite, 65, 55, 60);
                    initializeBossHealth(getHealthDisplayB(), foolHealthSprite, emptyFoolHealthSprite, 65, 55, 270);
                }
            }
        }
    }
    
    /**
     * Checks if the player is pressent in the world.
     * 
     * @return boolean          true if player is in the world, false if not.
     */
    public boolean checkPlayerPresent(){
        playerr = (ArrayList)getObjects(Player.class);
        if(playerr.size() == 1){
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the King of Diamonds is not present in the world.
     * 
     * @return boolean          false if Diamonds is in the world, true if not.
     */
    public boolean zeroKingOfDiamonds(){
        kingofdiamonds = (ArrayList)getObjects(KingOfDiamonds.class);
        if(kingofdiamonds.size() > 0){
            return false;
        }
        return true;
        
    }
    
    //Initializes an array with images and displays it in the boss health interface.
    /**
     * Initializes an array with images and displays it in the boss health interface.
     * 
     * @param healthArray           an array of Health[] objects (boss healthbar)
     * @param healthSprite          visual appearence of the health icon of each boss
     * @param emptyHealthSprite     visual appearence of a shattered health icon
     * @param hSpacing              horizontal spacing
     * @param vSpacing              vertical spacing
     * @param startingHeight        starting height of the health bar
     */
    public void initializeBossHealth(Health[] healthArray, GreenfootImage healthSprite, GreenfootImage emptyHealthSprite, int hSpacing, int vSpacing, int startingHeight){
        //Clearing the array if it was filled.
        for(int i= 0; i<13; i++){
            if(healthArray[i] != null){
                removeObject(healthArray[i]);
            }
        }
        int temp = 0;
        //Adding images to the array.
        for(int i = 0; i<13; i++){
            healthArray[i] = new Health(healthSprite, emptyHealthSprite);
        }
        
        //Displays the images in 3 rows of 4 and 1 row of 1.
        for(int i = 0; i<4; i++){
            addObject(healthArray[i], 940 + temp, startingHeight);
            temp += hSpacing;
        }
        temp = 0;
        for(int i = 4; i<8; i++){
            addObject(healthArray[i], 940 + temp, startingHeight + vSpacing);
            temp += hSpacing;
        }
        temp = 0;
        for(int i = 8; i<12; i++){
            addObject(healthArray[i], 940 + temp, startingHeight + vSpacing*2);
            temp += hSpacing;
        }
        temp = 0;
        addObject(healthArray[12], 940 + temp, startingHeight + vSpacing*3);
    }
    
}