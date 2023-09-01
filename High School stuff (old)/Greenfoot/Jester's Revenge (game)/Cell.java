import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class representing a cell upon a 2d grid of movement-based tiles.
 * Functions as an individual "staging ground" upon which the game's action takes place (so player movement, attacks, etc.)
 * While critical to the function of the game, it's still a relatively simple class with a lot of getters and setters.
 * Through a cell, one can reference/access a Player (movement, attack), or a Boss (vulnerability).
 * 
 * @author Arthur Zeng
 * @version 1.00
 */
public class Cell extends Actor
{
    protected Player player;
    protected Cell[][] checkerBoard;
    protected String type;
    protected DamageEffect damageEffect;
    protected boolean attackCell;
    protected VulnCellIndicator vulnIndicator;
    protected Boss boss;
    
    /**
     * The constructor for the cell object initialises a few variables - mainly, the occupying player of the cell as null, its type as the parameter,
     * its state as an "attack cell" against a boss as false, its transparency as 200, and its image based on its type (either white or black)
     * 
     * @param type          pass in string objects that are either named "white" or "black" - this determines whether or not the cell is a dark tile or a light one.
     */
    public Cell(String type) {
        player = null;
        this.type = type;
        attackCell = false;
        setImage(new GreenfootImage(type + ".png"));
        this.getImage().setTransparency(255);
    }
    
    /**
     * Getter for the type of the cell - whether or not it is a white or black tile.
     * 
     * @return String          either "white" or "black", based on the colouring of the tile.
     */
    public String type() {
        return type;
    }
    
    /**
     * Getter for the Y coordinate of the cell, finding itself by searching through the grid world's checkerboard for itself with a nested for loop
     * 
     * @return int          the y-coordinate of the cell - returns -1 if the cell is not on the grid.
     */
    public int getYCoord() {
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if (checkerBoard[i][j] == this) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    /**
     * Getter for the X coordinate of the cell, finding itself by searching through the grid world's checkerboard for itself with a nested for loop
     * 
     * @return int          the x-coordinate of the cell - returns -1 if the cell is not on the grid.
     */
    public int getXCoord() {
        for (int i = 0; i < checkerBoard.length; i++) {
            for (int j = 0; j < checkerBoard[i].length; j++) {
                if (checkerBoard[i][j] == this) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    /**
     * Mutator for the Player variable - redefines the Player variable, typically null, as a player object passed into it by parameter.
     * 
     * @param player          the player object you wish for the Cell to identify as its own player (its instance player variable)
     */
    public void acceptPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Mutator for the Player variable - redefines the Player variable back to null, usually for a Player to "de-anchor" itself from this.
     */
    public void rejectPlayer() {
        this.player = null;
    }

    /**
     * Getter for whether or not this cell "contains" a Player, or, if the player variable is null.
     * 
     * @return boolean          this.player == null, or if this cell contains a player.
     */
    public boolean hasPlayer() {
        return !(this.player == null);
    }
    
    /**
     * Getter for whether or not this cell "contains" a Player, or, if the player variable is null.
     * 
     * @return boolean          this.player == null, or if this cell contains a player.
     */
    public void becomeAttackCell(GreenfootImage image) {
        attackCell = true;
        vulnIndicator = new VulnCellIndicator(image);
        getWorld().addObject(vulnIndicator, getX(), getY());
    }
    
    /**
     * Method to toggle a cell from a cell from which you can attack a Boss from, to one that is vacant or empty of such measures.
     * Toggles the attackCell variable to false, then sets the instance/contained Boss variable to null, then resets its image, 
     * then removes its VulnCellIndicator from the world, and sets the corresponding variable to null.
     */
    public void becomeNotAttackCell() {
        attackCell = false;
        this.boss = null;
        setImage(type + ".png");
        getWorld().removeObject(vulnIndicator);
        vulnIndicator = null;
    }
    
    /**
     * Getter for which Boss this cell can attack if the Player moves to it and presses space, essentially.
     * 
     * @return Boss         either null or the existing boss(es) in-game.
     */
    public Boss getBossOfThisCell() {
        return boss;
    }
    
    /**
     * Mutator for the boss variable - passes in a Boss object into the cell so the cell can access it by instance variable.
     * 
     * @param boss          the boss you want to pass into the cell to allow access (typically to facilitate attack interactions)
     */
    public void becomeBossOfThisCell(Boss boss) {
        this.boss = boss;
    }
    
    /**
     * Getter for whether or not this cell is defined as an attackCell or whether or not it is associated with a boss as a way to attack it.
     * 
     * @return boolean          true if the cell is an attackCell, false if it is not.
     */
    public boolean isAttackCell() {
        return attackCell;
    }
        
    /**
     * Act - refreshes its access to the world's grid/checkerBoard, refreshes transparency, removes any unwanted VulnCellIndicators.
     */
    public void act()
    {
        this.getImage().setTransparency(255);
        checkerBoard = ((JestersRevenge)(getWorld())).getCheckerboard();
        if (!attackCell && vulnIndicator != null) {
            getWorld().removeObject(vulnIndicator);
            vulnIndicator = null;
            removeTouching(VulnCellIndicator.class);
        }
    }
    
    /**
     * A method for the cell to generate a damage effect on it in order to harm or hurt any players anchored to it.
     * The parameters passed into it mirror the parameters you would pass into the constructor of the damageEffect
     * 
     * @param fadeAway          number determining how quickly the damageEffect fades away
     * @param opaqueDuration    number determining how long the damage effect stays opaque (and damaging)
     * @param speed             how fast the damageEffect fades in to max opacity
     * @param sprite            the sprite/image of the damageEffect.
     */
    public void generateDamageEffect(int fadeAway, int opaqueDuration, double speed, GreenfootImage sprite) {
        damageEffect = new DamageEffect(fadeAway, opaqueDuration, speed, sprite);
        getWorld().addObject(damageEffect, getX(), getY());
    }
    
    /**
     * A method for the cell to generate a damage effect on it in order to harm or hurt any players anchored to it.
     * The parameter passed into it is instead directly a separate damageEffect object.
     * 
     * @param damageEffect      the damageEffect you want the cell to generate.
     */
    public void generateDamageEffect(DamageEffect damageEffect) {
        getWorld().addObject(damageEffect, getX(), getY());
    }
}
