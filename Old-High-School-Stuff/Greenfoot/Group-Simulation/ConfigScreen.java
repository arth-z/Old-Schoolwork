import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class representing a configuration screen for the world before you load into the world proper.
 * Has six configurable parameters that are set, then passed into the actual World once the Confirm button is clicked.
 * The parameters change based on which buttons you press.
 * 
 * @author Michael Liu
 * @version 2.00
 */
public class ConfigScreen extends World
{

    private GreenfootImage background = new GreenfootImage("configScreen.png");
    private Button confirmButton;
    
    private static int villagerMaxHp  = 100;
    private static int initialVillagers = 6;
    
    private static int enemySpawnRate = 1;
    private static int enemyHpMultiplier = 1;
    
    private static int resourceSpawnRate = 4;
    private static int resourceGainMultiplier = 1;
    
    private Button a1, b1, a2, b2, a3, b3, a4, b4, a5, b5, a6, b6;
    
    /**
     * Constructor for objects of class ConfigScreen.
     * Mainly, adds a bunch of buttons to the screen.
     */
    public ConfigScreen()
    {    
        
        super(977, 600, 1); 
        setPaintOrder(Button.class);
        setBackground(background);
        confirmButton = new ConfirmButton();
        addObject(confirmButton, this.getWidth() - 60, this.getHeight() - 60);
        
        
        a1 = new RemoveButton();
        b1 = new AddButton();
        a2 = new RemoveButton();
        b2 = new AddButton();
        a3 = new RemoveButton();
        b3 = new AddButton();
        a4 = new RemoveButton();
        b4 = new AddButton();
        a5 = new RemoveButton();
        b5 = new AddButton();
        a6 = new RemoveButton();
        b6 = new AddButton();

        addObject(a1, 250, 50);
        addObject(b1, 730, 50);
        addObject(a2, 250, 125);
        addObject(b2, 730, 125);
        addObject(a3, 250, 200);
        addObject(b3, 730, 200);
        addObject(a4, 250, 275);
        addObject(b4, 730, 275);
        addObject(a5, 250, 350);
        addObject(b5, 730, 350);
        addObject(a6, 250, 425);
        addObject(b6, 730, 425);
        
    }
    
    /**
     * Act method for the Config Screen, updates text to show changes to configuration as well as constantly checking for mouse input.
     */
    public void act() {
        checkMouse();
        showText("Villager maximum health: " + villagerMaxHp, 488, 50);
        showText("Villager intial spawn count: " + initialVillagers, 488, 125);
        showText("Enemy spawn rate: " + enemySpawnRate, 488, 200);
        showText("Enemy HP mutliplier: " + enemyHpMultiplier, 488, 275);
        showText("Resource spawn rate: " + resourceSpawnRate, 488, 350);
        showText("Resource gain multiplier: " + resourceGainMultiplier, 488, 425);       
    }
    
    /**
     * A method meant to be called in act, constantly checking if specific Actors (Buttons) have been pressed, and adjusting static fields of the config screen
     * (which are then passed to the main world, again, as static parameters), accordingly.
     */
    public void checkMouse(){
        if (Greenfoot.mouseClicked(null)) {
            if(Greenfoot.mouseClicked(a1)){
                setVillagerMaxHp(villagerMaxHp-5);
            }
            if(Greenfoot.mouseClicked(b1)){
                setVillagerMaxHp(villagerMaxHp+5);
            }
            if(Greenfoot.mouseClicked(a2)){
                setInitialVillagers(initialVillagers-1);
            }
            if(Greenfoot.mouseClicked(b2)){
                setInitialVillagers(initialVillagers+1);
            }
            if(Greenfoot.mouseClicked(a3)){
                if(enemySpawnRate > 1){
                    setEnemySpawnRate(enemySpawnRate-1);
                }
            }
            if(Greenfoot.mouseClicked(b3)){
                setEnemySpawnRate(enemySpawnRate+1);
            }
            if(Greenfoot.mouseClicked(a4)){
                if(enemyHpMultiplier > 1){
                    setEnemyHpMultiplier(enemyHpMultiplier-1);
                }

            }
            if(Greenfoot.mouseClicked(b4)){
                setEnemyHpMultiplier(enemyHpMultiplier+1);
            }
            if(Greenfoot.mouseClicked(a5)){
                if(resourceSpawnRate > 1){
                    setResourceSpawnRate(resourceSpawnRate-1);
                }
            }
            if(Greenfoot.mouseClicked(b5)){
                setResourceSpawnRate(resourceSpawnRate+1);
            }
            if(Greenfoot.mouseClicked(a6)){
                if(resourceGainMultiplier > 1){
                    setResourceGainMultiplier(resourceGainMultiplier-1);
                    
                }
            }
            if(Greenfoot.mouseClicked(b6)){
                setResourceGainMultiplier(resourceGainMultiplier+1);
            }
        }
    }
    
    //Accessors and mutators for configurables
    /**
     * Getter for villagerMaxHP
     * 
     * @return int      the maximum HP of villagers
     */
    public int getVillagerMaxHP(){
        return villagerMaxHp;
    }
    /**
     * Setter for villagerMaxHP, changes the value of the variable to equal the parameter passed in.
     */
    public void setVillagerMaxHp(int x){
        villagerMaxHp = x;
    }
    /**
     * Getter for initial amount of villagers in the world
     * 
     * @return int      the intial number of villagers
     */
    public int getInitialVillagers(){
        return initialVillagers;
    }
    /**
     * Setter for initialVillagers, changes the value of the variable to equal the parameter passed in.
     */
    public void setInitialVillagers(int x){
        initialVillagers = x;
    }
    /**
     * Getter for enemy spawn rate in the world
     * 
     * @return int      the enemy spawn rate
     */
    public int getEnemySpawnRate(){
        return enemySpawnRate;
    }
    /**
     * Setter for enemySpawnRate, changes the value of the variable to equal the parameter passed in.
     */
    public void setEnemySpawnRate(int x){
        enemySpawnRate = x;
    }
    /**
     * Getter for enemy spawn hp multiplier in the world
     * 
     * @return int      the enemy spawn HP multiplier
     */
    public int getEnemyHpMultiplier(){
        return enemyHpMultiplier;
    }
    /**
     * Setter for enemyHpMultiplier, changes the value of the variable to equal the parameter passed in.
     */
    public void setEnemyHpMultiplier(int x){
        enemyHpMultiplier = x;
    }
    /**
     * Getter for resource spawn rate in the world
     * 
     * @return int      the resource spawn rate
     */
    public int getResourceSpawnRate(){
        return resourceSpawnRate;
    }
    /**
     * Setter for resourceSpawnRate, changes the value of the variable to equal the parameter passed in.
     */
    public void setResourceSpawnRate(int x){
        resourceSpawnRate = x;
    }
    /**
     * Getter for resource/food gain multiplier in the world
     * 
     * @return int      the food/resource gain multiplier 
     */
    public int getResourceGainMultiplier(){
        return resourceGainMultiplier;
    }/**
     * Setter for resourceGainMultiplier, changes the value of the variable to equal the parameter passed in.
     */
    public void setResourceGainMultiplier(int x){
        resourceGainMultiplier = x;
    }
}
