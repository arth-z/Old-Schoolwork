import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A town simulation defense. Villagers perform ranged and melee attacks to find food during day time and fight off monsters during night time.
 * If they do not find food, they starve. Once every villager dies, the simulation ends.
 * 
 * <p>
 * Credits
 * <p>
 * Sprites: <p>
 *  Enemy Sprites: <p>
 *  https://craftpix.net/product/hell-monster-pixel-art-game-sprite-pack/
 * <p>
 *  Resource Sprites: <p>
 *      Cow: 
 *      https://www.pinterest.ca/pin/182184747418334611/ <p>
 *      Pig: 
 *      https://www.pinterest.ca/pin/341921796708587087/ <p>
 *      
 *      <p>
 *      
 *  Misc Sprites:<p>
 *      ResourceBar Sprite:
 *      https://www.redbubble.com/i/sticker/Pixel-Chicken-Leg-by-BenHenry/33223311.EJUG5 <p>
 *      Heart Sprite:
 *      https://www.kindpng.com/downpng/wiRxib_2d-pixel-hearts-hd-png-download/ <p>
 *      Sun Sprite:
 *      https://www.freepik.com/premium-vector/sun-pixel-art-style_21657320.htm <p>
 *      Moon Sprite:
 *      http://pixelartmaker.com/art/b14cf8bbef6f5f5 <p>
 *      Death Sprite:
 *      https://stock.pixlr.com/details/1001519923-pixel-art-gaming-skull-head/ <p>
 *      Giant red simOver() screen from Wikimedia Commons:
 *      https://commons.wikimedia.org/wiki/File:Red.svg <p>
 *  <p>
 * 
 *  Villager Sprites: <p>
 * 
 *      Woman: 
 *      https://www.pinterest.ca/pin/50384089572294624/ <p>
 *      Man:
 *      https://www.pinterest.ca/pin/425519864802466005/ <p>
 *  AttackSprites drawn by Michael <p>
 * 
 * <p>
 *  Village Sprites <p>
 *  https://opengameart.org/content/top-down-rpg-mockup-scene <p>
 * <p>
 * Code: <p>
 *  StatBar Class Ver. 2.1.1 By Mr. Cohen <p>
 *  <p>
 * Sounds: <p>
 * Sword Woosh from: https://mixkit.co/free-sound-effects/sword/, "sword slash woosh", middle option second row <p>
 * Bow Swoosh from: https://soundbible.com/1780-Bow-Fire-Arrow.html <p>
 * Simulation Over noise from: https://mixkit.co/free-sound-effects/game-over/, "game over trombone", sixth row first option from the right <p>
 * Skeleton thunks from: https://www.youtube.com/watch?v=PQVIToqJpJ4 <p>
 * Boss slam noise from: https://www.youtube.com/watch?v=QhJaPElJwLI <p>
 * Village ambience from: https://www.youtube.com/watch?v=xu2pESvXcmM&t=266s <p>
 * Night time battle music from: https://www.youtube.com/watch?v=fwW6DGYEw88&t=3s <p>
 * Entity death sound from: https://www.youtube.com/watch?v=_4vQ6ZQGdnE <p>
 * 
 * Useful Websites & Software: <p>
 *  Image Editor: 
 *  https://pixlr.com/x/ <p>
 *  Pixel Art Maker: 
 *  https://www.pixilart.com/draw <p>
 *  Krita - Sophisticated Image Editor <p>
 * 
 * @author Michael Liu, Arthur Zeng, Steven Lu, Leo Jia
 * @version 4.00
 * 
 * 
 * 
 */

public class KDSAbove extends World
{
    private GreenfootImage background;
    
    private static int dayDuration = 3000;
    private static int nightDuration = 1000;
    private GreenfootSound villageAmbience = new GreenfootSound("villageAmbience.mp3");
    private GreenfootSound battleMusic = new GreenfootSound("battleMusic.wav");
    private GreenfootSound simOverNoise = new GreenfootSound("gameOver.wav");
    
    /**
     * Getter for the duration of the day in the world.
     * 
     * @return int      the duration of day in the world.
     */
    public int getDayDuration(){
        return dayDuration;
    }
    
    /**
     * Getter for the duration of the night in the world.
     * 
     * @return int      the duration of night in the world.
     */
    public int getNightDuration(){
        return nightDuration;
    }
    
    //Configurable constants.

    private static int villagerMaxHp;
    private static int initialVillagers;
    
    private static int enemySpawnRate;
    private static int enemyHpMultiplier;
    
    private static int resourceSpawnRate;
    private static int resourceGainMultiplier;
    private static int maxFood = 6000;
    
    //Village horizontal offset
    private int vh = -31;
    
    //Day/night cycle 
    private int dayNightTimer = 0;
    private boolean isDay;
    private ArrayList<Night> night;
    
    //Used and changing variables.
    private int dayCounter = 0;
    private int foodCounter;
    private StatBar foodBar;
    private GreenfootImage foodBarImage = new GreenfootImage("foodBarSprite.png");
    private boolean onConfigScreen;
    private boolean allVillagersDead;
    private int resourceTimer;
    
    private static int enemiesKilled;
    
    /**
     * Getter for the amount of enemies killed in the world.
     * 
     * @return int      the amount of enemies killed in the world in the world.
     */
    public int getEnemiesKilled(){
        return enemiesKilled;
    }
    
    /**
     * Mutator for the amount of enemies killed in the world.
     * Increments the enemiesKilled variable, the amount of enemies killed in the world, by 1.
     */
    public void addEnemiesKilled(){
        enemiesKilled++;
    }
    
    //Wave spawning mechanism
    private int intensity = 0;
    private ArrayList<Fodder> fodder;
    private ArrayList<Boss> boss;
    
    //Resource mechanism
    private ArrayList<Resource> resource;
    //WORLD CONSTRUCTOR 
    /**
     * The world constructor. The first section initialises the world size and background, and then a whole bunch of variables
     * and local fields depending on what is passed in as parameter (things like enemy spawn rate, initial villagers, etc.). 
     * These are passed in as parameter in the ConfigScreen/ConfirmButton code. It then initialises a few more variables to default values,
     * sets the paint order (Nighttime, then widgets, then effects, then entities, then decorations). It then intialises the widgets, spawns villagers and decorations, 
     * then starts the music (and setting its volume).
     */
    public KDSAbove(int villagerMaxHp, int initialVillagers, int enemySpawnRate, int enemyHpMultiplier, int resourceSpawnRate, int resourceGainMultiplier)   
    {   
        
        // Create a new world with 1200x800 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1, false); 
        background = new GreenfootImage ("grass.png");
        setBackground (background);
        
        this.villagerMaxHp = villagerMaxHp;
        this.initialVillagers = initialVillagers;
        this.enemySpawnRate = enemySpawnRate;
        this.enemyHpMultiplier = enemyHpMultiplier;
        this.resourceSpawnRate = resourceSpawnRate;
        this.resourceGainMultiplier = resourceGainMultiplier;
        
        enemiesKilled = 0;
        resourceTimer = 361;
        
        allVillagersDead = false;
        //onConfigScreen = true;
        
        //Paint Order
        setPaintOrder(Night.class, FoodBarSprite.class, DayNightIcon.class, DeathCounterSprite.class, HeartSprite.class, 
        StatBar.class, Tree.class, Tree2.class, 
        Projectile.class, Entity.class, FoodDeathEffect.class, DeathEffect.class, Crate4.class, Blacksmith.class, 
        TownHall.class, House.class, Barrel.class, 
        Crate.class, Barrel2.class, Crate2.class,Crate3.class, SlamAttackAOE.class, Road.class);        
        
        //FoodBar 
        foodCounter = maxFood;
        foodBar = new StatBar(maxFood, foodCounter, null, 230, 15, 0, Color.ORANGE, Color.DARK_GRAY, false);
        addObject(foodBar, 140, 30);
        addObject(new FoodBarSprite(), 30, 30);
        addObject(new DeathCounterSprite(), 25, 75);
        addObject(new DayNightIcon(), 25, 125);
        addObject(new HeartSprite(), 25, 175);
                
        //All the houses, trees, roads.
        drawDecorations();
        addInvisRoad();
        //Villager spawning
        spawnVillagers();
        
        villageAmbience.playLoop();
        villageAmbience.setVolume(30);
    }
    
    /**
     * When Greenfoot is paused, the music should also pause.
     */
    public void stopped() {
        if (isDay) {
            villageAmbience.pause();
        } else {
            battleMusic.pause();
        }
    }
    
    /**
     * When Greenfoot starts, the music should also start.
     */
    public void started(){
        villageAmbience.playLoop();
        villageAmbience.setVolume(30);
    }
    
    /**
     * Mutator for the foodCounter, or the global hunger bar the villagers use to determine hunger checks.
     * Decrements the bar by the parameter passed into it.
     * 
     * @param amount        an integer, and the amount you want to reduce the foodBar by.
     */
    public void decrementFoodCounter(int amount) {
        foodCounter -= amount;
        foodBar.update(foodCounter);
    }
    
    /**
     * Mutator for the foodCounter, or the global hunger bar the villagers use to determine hunger checks.
     * Increments the bar by the parameter passed into it. Does not add food if the current food incremented would exceed the max food capacity.
     * 
     * @param amount        an integer, and the amount you want to increase the foodBar by.
     */
    public void incrementFoodCounter(int amount) {
        if(foodCounter < maxFood) {
            foodCounter += amount;
        }
        foodBar.update(foodCounter);
    }
    
    /**
     * Act method for the world. Regularly executes spawning mechanisms based on time of day, as well as continously decrementing the food counter (less at night).
     * Spawns resources, updates widgets and shows appropriate text, checks if all villagers are dead (if they are, it moves to the simOver() screen).
     */
    public void act() {
        
        intensity = 1 + dayCounter;
        fodder = (ArrayList)getObjects(Fodder.class);
        boss = (ArrayList)getObjects(Boss.class);
        dayNightCycle();
        checkDayOrNight();
        if (isDay()) {
            battleMusic.stop();
            villageAmbience.play();
            decrementFoodCounter(4);
            villageAmbience.setVolume(30);
        } else {
            decrementFoodCounter(1);
            villageAmbience.setVolume(0);
            battleMusic.play();
        }
        resourceSpawner();
        waveMechanism();
        showText((Villager.villagersAddedToWorld() - Villager.villagersKilled()) + "/" + Villager.villagersAddedToWorld() + " villagers remaining", 165, 175);
        showText(enemiesKilled + " enemies slain", 130, 75);
        showText(dayCounter + " days survived ", 135, 125);

        allVillagersDead = ((ArrayList) getObjects(Villager.class)).size() == 0;
            
        if (foodCounter < 0) {
            //incrementFoodCounter(9000);
            foodBar.update(foodCounter);
        }
            
        if (allVillagersDead) {
            simOver();
        }
                
    }
    
    /**
     * Method for the World's day/night cycle. Increments a timer until it exceeds the duration of day - then spawns a night class (big box),
     * and increments the days survived counter as well as resetting the timer.
     */
    public void dayNightCycle(){
        dayNightTimer++;
        if(dayNightTimer > dayDuration){
            addObject(new Night(), 600, 400);
            battleMusic.setVolume(30);
            battleMusic.play();
            dayNightTimer = 0;
            dayCounter++;
        }
    }
    
    /**
     * Getter for whether or not it is currently day in the world.
     * @return boolean      whether or not it is day in the world (is there a big black screen over the world right now?)
     */
    public boolean isDay() {
        return isDay;
    }
    
    /**
     * Mutator for the dayNightTimer, used in dayNightCycle(). Sets it to 0. Basically, refreshes the countdown to night.
     */
    public void resetDayNightTimer(){
        dayNightTimer = 0;
    }
    
    /**
     * Technically, an if-dependent mutator for the isDay variable - checks if there are any Night objects in the world, and sets isDay to false if there is, true if there isn't.
     */
    public void checkDayOrNight(){
        night = (ArrayList)getObjects(Night.class);
        if(night.size() == 0){
            isDay = true;
        } else{
            isDay = false;
        }
    }
    
    /**
     * A method to spawn a bunch of villagers around the villager, randomly picking between Man and Woman. Spawns them roughly in the boundaries of the village, with random variance.
     */
    public void spawnVillagers(){
        int randomDirection = Greenfoot.getRandomNumber(2) + 1;
        
        for(int i = 0; i< initialVillagers; i++){
            int random = Greenfoot.getRandomNumber(200) + 100;
            if(Greenfoot.getRandomNumber(2)== 0){
                addObject(new Man(2, villagerMaxHp, randomDirection), 400 + random, 400+ random);
                
            }
            else
            {
                addObject(new Woman(2, villagerMaxHp, randomDirection), 400 + random, 400 + random);
            }
        }
    }
    
    /**
     * A method to spawn Enemies, specifically Fodder. Randomly choosing from left or right, it spawns a Fodder (skeleton) near the outer borders of the World, approaching the village.
     */
    public void spawnFodder(){
        int leftRight = Greenfoot.getRandomNumber(2);
        if(leftRight == 0){
            addObject(new Fodder(1, 20*enemyHpMultiplier, 1), 0, Greenfoot.getRandomNumber(801));
        } else{
            addObject(new Fodder(1, 20*enemyHpMultiplier, -1), 1200, Greenfoot.getRandomNumber(801));
        }              
    }
    
    /**
     * The method that executes when all villagers are killed. It removes every actor from the world except widgets and statbrs.
     * It also plays a sad trombone noise, and stops Greenfoot as a whole, as well as setting the background of the world to a big red screen.
     */
    public void simOver() { // giant red screen taken from Wikimedia commons, https://commons.wikimedia.org/wiki/File:Red.svg
        showText(("All the villagers have been killed." + "\n Days Survived: " + dayCounter), 600, 400);
        simOverNoise.setVolume(75);
        simOverNoise.play();
        for (Entity actor: getObjects(Entity.class)) {
            removeObject(actor);
        }
        for (Decoration actor: getObjects(Decoration.class)) {
            removeObject(actor);
        }
        for (Building actor: getObjects(Building.class)) {
            removeObject(actor);
        }
        for (Effect actor: getObjects(Effect.class)){
            removeObject(actor);
        }
        setBackground("gameOver.png");
        Greenfoot.stop();
    }
    
    /**
     * A resource-spawning method, called in act(). Every 90 acts or so, it spawns one Resource roughly outside of the village.
     * It spawns Resources one by one until it reaches the resourceSpawnRate, which serves as a cap for the amount of resources in the world.
     * If that cap is already met/exceeded, no Resources spawn.
     */
    public void resourceSpawner(){
        resource = (ArrayList)getObjects(Resource.class);
        if(resource.size() < resourceSpawnRate && isDay){
            if (resourceTimer > 90) {
                int resourceX = Greenfoot.getRandomNumber(2) == 0 ? -1: 1;
                addObject(new Resource(1, 15, resourceX), Greenfoot.getRandomNumber(1160) + 20, Greenfoot.getRandomNumber(760) + 20);
                resourceTimer = 0;    
            } else {
                resourceTimer++;
            }
        }
    }
    /**
     * A boss-spawning method, operating similarly to Fodder's spawning method. Spawns a Boss outside the bounds of the village, in the forest, from a random direction, with slight random variance.
     */
    public void spawnBoss(){
        int leftRight = Greenfoot.getRandomNumber(2);
        if(leftRight == 0){
            addObject(new Boss(1, 100*enemyHpMultiplier, 1), 0, Greenfoot.getRandomNumber(801));
        } else{
            addObject(new Boss(1, 100*enemyHpMultiplier, -1), 1200, Greenfoot.getRandomNumber(801));
        } 
    }
    /**
     * A method that enables wave spawning mechanisms at night. If it is not day, and the size of fodder in the world are smaller than the intensity field (incremented by a new day),
     * then fodder is spawned up to the enemySpawnRate multiplied by the intensity. If intensity is divisible by 3, and the amount of Bosses in the world are as well, then a boss is also spawned.
     * That essentially means that roughly every time intensity reaches a number divisible by three (increments each day, starts at 1), a boss is spawned.
     */
    public void waveMechanism(){
        if(!isDay && fodder.size() < intensity){
            for(int i = 0; i<(int)enemySpawnRate*intensity; i++){
                spawnFodder();
            }
        }
        if(!isDay && intensity%3 == 0 && boss.size() < intensity/3){
            spawnBoss();
        }
    }
    
    /**
     * Getter for villagerMaxHP, the villagers' max HP.
     * 
     * @return int      villager max HP, or the maximum hit points of the villagers.
     */
    public int getVillagerMaxHP(){
        return villagerMaxHp;
    }
    /**
     * Mutator for villagerMaxHP, the villagers' max HP. This does not affect already spawned villagers.
     * 
     * @param x         the amount you want to set the villagers' max HP to.
     */
    public void setVillagerMaxHp(int x){
        villagerMaxHp = x;
    }
    
    /**
     * Getter for initialVillagers, the initial villagers in the world.
     * 
     * @return int      the starting number of villagers in the world.
     */
    public int getInitialVillagers(){
        return initialVillagers;
    }
    /**
     * Mutator for initialVillagers, the initial villagers in the world. Directly sets the variable to the parameter.
     * 
     * @param x         the value you want to set initialVillagers to
     */
    public void setInitialVillagers(int x){
        initialVillagers = x;
    }
    
    /**
     * Getter for enemySpawnRate, a multiplier of Fodder spawns in the world.
     * 
     * @return int      enemySpawnRate, a multiplier of how many Fodder can spawn in the world
     */
    public int getEnemySpawnRate(){
        return enemySpawnRate;
    }
    /**
     * Setter for enemySpawnRate, a multiplier of Fodder spawns in the world, by direct values passed into the parameter.
     * 
     * @param x         the integer value you want to set enemySpawnRate to.
     */
    public void setEnemySpawnRate(int x){
        enemySpawnRate = x;
    }
    /**
     * Getter for enemyHpMultiplier.
     * 
     * @return int      enemyHpMultiplier.
     */
    public int getEnemyHpMultiplier(){
        return enemyHpMultiplier;
    }
    /**
     * Setter for enemyHpMultiplier, by direct value reassignment.
     * 
     * @param x         the desired value of enemyHpMultiplier.
     */
    public void setEnemyHpMultiplier(int x){
        enemyHpMultiplier = x;
    }
    /**
     * Getter for resourceSpawnRate, the max cap of allowed Resources in the world.
     * 
     * @return int      resourceSpawnRate, max cap of allowed Resources in the world.
     */
    public int getResourceSpawnRate(){
        return resourceSpawnRate;
    }
    /**
     * Setter for resourceSpawnRate, the max amount of resources in the world, by direct value reassignment.
     * 
     * @param x         the desired value of setResourceSpawnRate, or the value you want it to be.
     */
    public void setResourceSpawnRate(int x){
        resourceSpawnRate = x;
    }
    /**
     * Getter for resourceGainMultiplier, the multiplier of incremented food gain when a Resource is killed.
     * 
     * @return int      resourceGainMultiplier, the multiplier of incremented food gain when a Resource is killed.
     */
    public int getResourceGainMultiplier(){
        return resourceGainMultiplier;
    }
    /**
     * Setter for resourceGainMultiplier, the multiplier of incremented food gain when a Resource is killed, by direct value assignment.
     * 
     * @param x         the new desired value of resourceGainMultiplier
     */
    public void setResourceGainMultiplier(int x){
        resourceGainMultiplier = x;
    }
    
    /**
     * Getter for foodCounter, the current integer food rating of the Villagers in the world.
     * 
     * @return int      foodCounter, the current integer food rating of the Villagers in the world.
     */
    public int getFoodCounter() {
        return foodCounter;
    }
    
    /**
     * Getter for maxFood, the maximum integer food rating of the Villagers in the world, aka the max capacity.
     * 
     * @return int      maxFood, the ending integer food rating of the Villagers in the world and its max capacity.
     */
    public int getMaxFood(){
        return maxFood;
    }
    /**
     * Setter for maxFood, the maximum integer food rating of the Villagers in the world, aka the max capacity, by direct value assignment.
     * 
     * @param x         the new desired value of maxFood.
     */
    public void setMaxFood(int x){
        maxFood = x;
    }
    
    /**
     * A self-contained method that adds InvisRoads in a ring around the village. These serve as a navigation point for both Villagers and Resources.
     * The InvisRoads are invisible, but allow Resources and Villagers to loiter around the edges of the village.
     */
    public void addInvisRoad(){
        int startingX = 350;
        int startingY = 200;
        
        for(int i = 0; i < 22; i++){
            addObject(new InvisRoad(), startingX, startingY);
            startingX += 25;
        }
        startingX = 350;
        startingY += 400;
        for(int i = 0; i < 22; i++){
            addObject(new InvisRoad(), startingX, startingY);
            startingX += 25;
        }
        startingY = 200;
        for (int i = 0; i < 16; i++){
            addObject(new InvisRoad(), 350, startingY);
            startingY += 25;
            
        }
        startingY = 200;
        for (int i = 0; i < 16; i++){
            addObject(new InvisRoad(), 875, startingY);
            startingY += 25;
            
        }
    }
    
   
    /**
     * A self-contained method that draws/adds all the relevant decorations, roads, trees, etc. to the world in one massive block/action.
     * Any decoration on the world that are not Entities or UI elements, such as the trees, houses, crates, roads, etc. are added by this method.
     */
    public void drawDecorations(){
        //Village constructor
        addObject(new Blacksmith(), 639 + vh, 540);     
        addObject(new TownHall(), 641 + vh, 389);      
        addObject(new House(), 544 + vh, 300);
        addObject(new House(), 719 + vh, 283);
        addObject(new House(), 747 + vh, 471);
        addObject(new House(), 475 + vh, 400);
        addObject(new House(), 524 + vh, 481);
        addObject(new House(), 643 + vh, 262);
        addObject(new House(), 796 + vh, 375);       
        addObject(new Road(), 506 + vh, 422);
        addObject(new Road(), 506 + vh, 447);
        addObject(new Road(), 506 + vh, 472);
        addObject(new Road(), 506 + vh, 497);
        addObject(new Road(), 506 + vh, 522);
        addObject(new Road(), 531 + vh, 522);
        addObject(new Road(), 481 + vh, 447);
        addObject(new Road(), 481 + vh, 472);
        addObject(new Road(), 481 + vh, 497);
        addObject(new Road(), 481 + vh, 522);
        addObject(new Road(), 481 + vh, 397);
        addObject(new Road(), 481 + vh, 422);
        addObject(new Road(), 531 + vh, 422);
        addObject(new Road(), 531 + vh, 447);
        addObject(new Road(), 531 + vh, 472);
        addObject(new Road(), 531 + vh, 497);
        addObject(new Road(), 531 + vh, 397);
        addObject(new Road(), 506 + vh, 397);
        addObject(new Road(), 556 + vh, 447);
        addObject(new Road(), 556 + vh, 472);
        addObject(new Road(), 556 + vh, 497);
        addObject(new Road(), 556 + vh, 522);
        addObject(new Road(), 581 + vh, 447);
        addObject(new Road(), 606 + vh, 447);
        addObject(new Road(), 581 + vh, 422);
        addObject(new Road(), 606 + vh, 422);
        addObject(new Road(), 631 + vh, 422);
        addObject(new Road(), 631 + vh, 447);
        addObject(new Road(), 656 + vh, 447);
        addObject(new Road(), 681 + vh, 447);
        addObject(new Road(), 706 + vh, 447);
        addObject(new Road(), 731 + vh, 447);
        addObject(new Road(), 581 + vh, 472);
        addObject(new Road(), 606 + vh, 472);
        addObject(new Road(), 631 + vh, 472);
        addObject(new Road(), 656 + vh, 472);
        addObject(new Road(), 681 + vh, 472);
        addObject(new Road(), 706 + vh, 472);        
        addObject(new Road(), 581 + vh, 497);
        addObject(new Road(), 606 + vh, 497);
        addObject(new Road(), 631 + vh, 497);
        addObject(new Road(), 656 + vh, 497);
        addObject(new Road(), 681 + vh, 497);
        addObject(new Road(), 706 + vh, 497);      
        addObject(new Road(), 581 + vh, 522);
        addObject(new Road(), 606 + vh, 522);
        addObject(new Road(), 631 + vh, 522);
        addObject(new Road(), 656 + vh, 522);
        addObject(new Road(), 681 + vh, 522);
        addObject(new Road(), 706 + vh, 522);
        addObject(new Road(), 731 + vh, 522);
        addObject(new Road(), 731 + vh, 497);
        addObject(new Road(), 731 + vh, 472);
        addObject(new Road(), 756 + vh, 522);
        addObject(new Road(), 756 + vh, 497);
        addObject(new Road(), 756 + vh, 472);
        addObject(new Road(), 756 + vh, 522);
        addObject(new Road(), 756 + vh, 497);
        addObject(new Road(), 756 + vh, 472);
        addObject(new Road(), 781 + vh, 522);
        addObject(new Road(), 781 + vh, 497);
        addObject(new Road(), 781 + vh, 472);
        addObject(new Road(), 806 + vh, 422);
        addObject(new Road(), 806 + vh, 397);
        addObject(new Road(), 806 + vh, 372);
        addObject(new Road(), 806 + vh, 347);
        addObject(new Road(), 831 + vh, 422);
        addObject(new Road(), 831 + vh, 397);
        addObject(new Road(), 831 + vh, 372);
        addObject(new Road(), 831 + vh, 347);
        addObject(new Road(), 781 + vh, 347);
        addObject(new Road(), 781 + vh, 372);
        addObject(new Road(), 781 + vh, 397);
        addObject(new Road(), 781 + vh, 422);
        addObject(new Road(), 781 + vh, 447);
        addObject(new Road(), 756 + vh, 322);
        addObject(new Road(), 756 + vh, 347);
        addObject(new Road(), 756 + vh, 372);
        addObject(new Road(), 756 + vh, 397);
        addObject(new Road(), 756 + vh, 422);
        addObject(new Road(), 756 + vh, 447);
        addObject(new Road(), 731 + vh, 322);
        addObject(new Road(), 731 + vh, 347);
        addObject(new Road(), 731 + vh, 372);
        addObject(new Road(), 731 + vh, 397);
        addObject(new Road(), 731 + vh, 422);
        addObject(new Road(), 706 + vh, 322);
        addObject(new Road(), 706 + vh, 347);
        addObject(new Road(), 706 + vh, 372);
        addObject(new Road(), 706 + vh, 397);
        addObject(new Road(), 706 + vh, 422);
        addObject(new Road(), 681 + vh, 322);
        addObject(new Road(), 681 + vh, 347);
        addObject(new Road(), 681 + vh, 372);
        addObject(new Road(), 681 + vh, 397);
        addObject(new Road(), 681 + vh, 422);
        addObject(new Road(), 656 + vh, 322);
        addObject(new Road(), 656 + vh, 347);
        addObject(new Road(), 656 + vh, 372);
        addObject(new Road(), 656 + vh, 397);
        addObject(new Road(), 656 + vh, 422);
        addObject(new Road(), 631 + vh, 322);
        addObject(new Road(), 631 + vh, 347);
        addObject(new Road(), 631 + vh, 372);
        addObject(new Road(), 631 + vh, 397);
        addObject(new Road(), 606 + vh, 322);
        addObject(new Road(), 606 + vh, 347);
        addObject(new Road(), 606 + vh, 372);
        addObject(new Road(), 606 + vh, 397);
        addObject(new Road(), 581 + vh, 322);
        addObject(new Road(), 581 + vh, 347);
        addObject(new Road(), 581 + vh, 372);
        addObject(new Road(), 581 + vh, 397);
        addObject(new Road(), 556 + vh, 322);
        addObject(new Road(), 556 + vh, 347);
        addObject(new Road(), 556 + vh, 372);
        addObject(new Road(), 556 + vh, 397);
        addObject(new Road(), 556 + vh, 422);
        addObject(new Road(), 556 + vh, 447);
        addObject(new Road(), 531 + vh, 322);
        addObject(new Road(), 531 + vh, 347);
        addObject(new Road(), 531 + vh, 372);
        addObject(new Road(), 506 + vh, 372);
        addObject(new Road(), 481 + vh, 372);
        addObject(new Road(), 456 + vh, 372);
        addObject(new Road(), 456 + vh, 397);
        addObject(new Road(), 456 + vh, 422);
        addObject(new Road(), 456 + vh, 447);
        addObject(new Road(), 531 + vh, 297);
        addObject(new Road(), 556 + vh, 297);
        addObject(new Road(), 581 + vh, 297);
        addObject(new Road(), 606 + vh, 297);
        addObject(new Road(), 631 + vh, 297);
        addObject(new Road(), 656 + vh, 297);
        addObject(new Road(), 681 + vh, 297);
        addObject(new Road(), 706 + vh, 297);
        addObject(new Road(), 731 + vh, 297);
        addObject(new Road(), 756 + vh, 297);
        addObject(new Road(), 606 + vh, 272);
        addObject(new Road(), 631 + vh, 272);
        addObject(new Road(), 656 + vh, 272);
        addObject(new Road(), 681 + vh, 272);
        addObject(new Road(), 606 + vh, 247);
        addObject(new Road(), 631 + vh, 247);
        addObject(new Road(), 656 + vh, 247);
        addObject(new Road(), 681 + vh, 247);
        addObject(new Road(), 606 + vh, 570);
        addObject(new Road(), 631 + vh, 570);
        addObject(new Road(), 656 + vh, 570);
        addObject(new Road(), 681 + vh, 570);
        addObject(new Road(), 706 + vh, 272);
        addObject(new Road(), 731 + vh, 272);
        addObject(new Road(), 756 + vh, 272);
        
        //Deleted Decoration
        //addObject(new Crate(), 634 + vh, 420);
        //addObject(new Crate(), 634 + vh, 410);
        //addObject(new Crate2(), 634 + vh, 400);
        //addObject(new Barrel(), 634 + vh, 430);
        addObject(new Crate(), 748 + vh, 287);
        addObject(new Crate2(), 748 + vh, 280);
        addObject(new Barrel(), 761 + vh, 297);
        addObject(new Barrel2(), 761 + vh, 317);
        addObject(new Barrel2(), 761 + vh, 307);
        addObject(new Crate(), 515 + vh, 310);
        addObject(new Crate2(), 546 + vh, 345);
        addObject(new Barrel(), 516 + vh, 336);
        addObject(new Barrel2(), 516 + vh, 325);
        addObject(new Crate3(), 748 + vh, 300);
        addObject(new Crate(), 532 + vh, 345);
        addObject(new Crate(), 720 + vh, 498);
        addObject(new Crate(), 551 + vh, 502);
        addObject(new Barrel(), 501 + vh, 423);
        addObject(new Crate4(), 655 + vh , 438);
        addObject(new Crate4(), 668 + vh , 438);
        addObject(new Crate4(), 501 + vh , 408);
        addObject(new Crate4(), 729 + vh , 507);
        addObject(new Crate4(), 750 + vh , 507);
        addObject(new Crate4(), 634 + vh , 297);
        addObject(new Crate4(), 660 + vh , 297);
        addObject(new Crate(), 501 + vh , 398);
        addObject(new Crate(), 496 + vh , 497);
        addObject(new Crate(), 496 + vh , 507);
        addObject(new Crate2(), 496 + vh , 487);
        addObject(new Crate(), 496 + vh , 493);
        addObject(new Barrel(), 482 + vh , 493);
        addObject(new Crate(), 675 + vh, 417);
        addObject(new Crate2(), 675 + vh, 407);
        addObject(new Crate(), 560 + vh, 345);
        addObject(new Crate(), 619 + vh, 268);
        addObject(new Crate(), 695 + vh, 308);
        addObject(new Road(), 506 + vh, 547);
        addObject(new Road(), 531 + vh, 547);
        addObject(new Road(), 556 + vh, 547);
        addObject(new Road(), 581 + vh, 547);
        addObject(new Road(), 606 + vh, 547);
        addObject(new Road(), 631 + vh, 547);
        addObject(new Road(), 656 + vh, 547);
        addObject(new Road(), 681 + vh, 547);
        addObject(new Road(), 706 + vh, 547);
        addObject(new Road(), 731 + vh, 547);
        addObject(new Road(), 756 + vh, 547);
        addObject(new Road(), 806 + vh, 447);
        addObject(new Road(), 781 + vh, 322);
        addObject(new Road(), 581 + vh, 272);
        addObject(new Road(), 506 + vh, 347);
        addObject(new Road(), 431 + vh, 372);
        addObject(new Road(), 431 + vh, 397);
        addObject(new Road(), 431 + vh, 422);
        addObject(new Road(), 431 + vh, 447);
        addObject(new Road(), 431 + vh, 372);
        addObject(new Road(), 456 + vh, 472);
        addObject(new Road(), 481 + vh, 347);
        addObject(new Road(), 506 + vh, 322);
        addObject(new Crate(), 823 + vh, 394);
        
        //Upper forest outer layer
        addObject(new Tree(), 50, 40);
        addObject(new Tree(), 100, 15);
        addObject(new Tree(), 154, 30);
        addObject(new Tree(), 214, 20);
        addObject(new Tree(), 280, 15);
        addObject(new Tree(), 335, 5);
        addObject(new Tree(), 400, 10);
        addObject(new Tree(), 471, 15);
        addObject(new Tree(), 542, 40);
        addObject(new Tree(), 605, 30);
        addObject(new Tree(), 680, 15);
        addObject(new Tree(), 757, 20);
        addObject(new Tree(), 813, 5);
        addObject(new Tree(), 873, 30);
        addObject(new Tree(), 937, 15);
        addObject(new Tree(), 1005, 40);
        addObject(new Tree(), 1085, 20);
        addObject(new Tree(), 1139, 30);
        addObject(new Tree(), 1197, 5);
        //Upper forest inner layer
        addObject(new Tree2(), 10, 10);
        addObject(new Tree2(), 130, 0);
        addObject(new Tree2(), 73, 0);
        addObject(new Tree2(), 181, 1);
        addObject(new Tree2(), 241, 0);
        addObject(new Tree2(), 300, 0);
        addObject(new Tree2(), 370, 0);
        addObject(new Tree2(), 430, 0);
        addObject(new Tree2(), 515, 10);
        addObject(new Tree2(), 560, 0);
        addObject(new Tree2(), 620, 0);
        addObject(new Tree2(), 720, 0);
        addObject(new Tree2(), 780, 0);
        addObject(new Tree2(), 850, 5);
        addObject(new Tree2(), 900, 0);
        addObject(new Tree2(), 970, 0);
        addObject(new Tree2(), 1030, 0);
        addObject(new Tree2(), 1100, 0);
        addObject(new Tree2(), 1150, 0);
        
        //Right side forest
        addObject(new Tree(), 1180, 50);
        addObject(new Tree(), 1170, 80);
        addObject(new Tree(), 1167, 120);
        addObject(new Tree(), 1170, 150);
        addObject(new Tree(), 1160, 200);
        addObject(new Tree(), 1180, 250);
        addObject(new Tree(), 1166, 290);
        addObject(new Tree(), 1199, 297);
        addObject(new Tree(), 1170, 330);
        addObject(new Tree(), 1180, 360);
        addObject(new Tree(), 1190, 400);
        addObject(new Tree(), 1180, 440);
        addObject(new Tree(), 1170, 480);
        addObject(new Tree(), 1170, 530);
        addObject(new Tree(), 1170, 570);
        addObject(new Tree(), 1190, 600);
        addObject(new Tree(), 1180, 640);
        addObject(new Tree(), 1160, 670);
        addObject(new Tree(), 1180, 710);
        addObject(new Tree(), 1190, 740);
        addObject(new Tree(), 1170, 770);
        addObject(new Tree(), 1170, 800);
        
        //Bottom left corner
        addObject(new Tree2(), 177, 689); 
        addObject(new Tree2(), 73, 599);    
        addObject(new Tree2(), 73, 650);  
        addObject(new Tree2(), 130, 650);  
        addObject(new Tree2(), 130, 700);   
        addObject(new Tree2(), 80, 700);
        addObject(new Tree2(), 95, 723);
        addObject(new Tree2(), 151, 730);
        addObject(new Tree2(), 220, 726); 
        
        
        //Bottom outer layer forest
        addObject(new Tree2(), 50, 760);
        addObject(new Tree2(), 100, 785);
        addObject(new Tree2(), 154, 770);
        addObject(new Tree2(), 214, 780);
        addObject(new Tree2(), 280, 785);
        addObject(new Tree2(), 335, 795);
        addObject(new Tree2(), 400, 790);
        addObject(new Tree2(), 471, 785);
        addObject(new Tree2(), 542, 760);
        addObject(new Tree2(), 605, 770);
        addObject(new Tree2(), 680, 785);
        addObject(new Tree2(), 757, 780);
        addObject(new Tree2(), 813, 795);
        addObject(new Tree2(), 873, 770);
        addObject(new Tree2(), 937, 785);
        addObject(new Tree2(), 1005, 760);
        addObject(new Tree2(), 1085, 780);
        addObject(new Tree2(), 1139, 770);
        addObject(new Tree2(), 1197, 795);
        
        //Bottom innner layer forest
        addObject(new Tree(), 10, 790);
        addObject(new Tree(), 130, 800);
        addObject(new Tree(), 73, 800);
        addObject(new Tree(), 181, 799);
        addObject(new Tree(), 241, 800);
        addObject(new Tree(), 300, 800);
        addObject(new Tree(), 370, 800);
        addObject(new Tree(), 430, 800);
        addObject(new Tree(), 515, 790);
        addObject(new Tree(), 560, 800);
        addObject(new Tree(), 620, 800);
        addObject(new Tree(), 720, 800);
        addObject(new Tree(), 780, 800);
        addObject(new Tree(), 850, 795);
        addObject(new Tree(), 900, 800);
        addObject(new Tree(), 970, 800);
        addObject(new Tree(), 1030, 800);
        addObject(new Tree(), 1100, 800);
        addObject(new Tree(), 1150, 800);
        
        //Left side forest
        addObject(new Tree(), 20, 50);
        addObject(new Tree(), 30, 80);
        addObject(new Tree(), 33, 120);
        addObject(new Tree(), 30, 150);
        addObject(new Tree(), 30, 200);
        addObject(new Tree(), 20, 250);
        addObject(new Tree(), 34, 290);
        addObject(new Tree(), 1, 297);
        addObject(new Tree(), 30, 330);
        addObject(new Tree(), 20, 360);
        addObject(new Tree(), 10, 400);
        addObject(new Tree(), 20, 440);
        addObject(new Tree(), 30, 480);
        addObject(new Tree(), 30, 530);
        addObject(new Tree(), 30, 570);
        addObject(new Tree(), 10, 600);
        addObject(new Tree(), 20, 660);
        addObject(new Tree(), 20, 710);
        addObject(new Tree(), 10, 740);
        addObject(new Tree(), 30, 770);
        addObject(new Tree(), 30, 800);
        
        //Top right corner
        addObject(new Tree(), 1106, 60);
        addObject(new Tree(), 940, 60);
        addObject(new Tree(), 1050, 65);
        addObject(new Tree(), 1000, 60);
        addObject(new Tree(), 1120, 77);
        addObject(new Tree(), 1090, 100);
        addObject(new Tree(), 1036, 95);
        addObject(new Tree(), 985, 90);
        addObject(new Tree(), 1120, 120);
        addObject(new Tree(), 1060, 115);
        addObject(new Tree(), 1115, 164);
        addObject(new Tree(), 1065, 149);
        addObject(new Tree(), 1126, 211);
        
        //Top leftcorner
        addObject(new Tree(), 94, 60);
        addObject(new Tree(), 260, 60);
        addObject(new Tree(), 150, 65);
        addObject(new Tree(), 200, 60);
        addObject(new Tree(), 80, 77);
        addObject(new Tree(), 110, 100);
        addObject(new Tree(), 164, 95);
        addObject(new Tree(), 215, 90);
        addObject(new Tree(), 80, 120);
        addObject(new Tree(), 140, 115);
        addObject(new Tree(), 85, 164);
        addObject(new Tree(), 135, 149);
        addObject(new Tree(), 74, 211);
        
        //Bottom right corner
        addObject(new Tree2(), 1023, 689); 
        addObject(new Tree2(), 1127, 599);      
        addObject(new Tree2(), 1127, 650);   
        addObject(new Tree2(), 1070, 650);  
        addObject(new Tree2(), 1070, 700);   
        addObject(new Tree2(), 1120, 700);
        addObject(new Tree2(), 1105, 723);
        addObject(new Tree2(), 1049, 730);
        addObject(new Tree2(), 961, 726); 
        
        addObject(new Tree(), 313, 37);




    }
}

