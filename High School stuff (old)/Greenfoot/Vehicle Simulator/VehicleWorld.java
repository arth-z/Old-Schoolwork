import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Arthur Zeng's Vehicle simulation. Features:
 *  - Pedestrians can now die, and have health values. Death sound effects included. 
 *      - Once knocked down, health values decrement for every frame that a vehicle remains on top of them.
 *      - As such, most vehicle collisions are rectangle-based, and not point-based.
 *      - Downed pedestrians can be killed by cars, buses, and cyclists.
 *      - Technically, all SuperSmoothMovers are able to 'die' as well, but vehicles are only 'killed' in a specific event.
 *  - New Cat pedestrian - different health, death effects, and audio, can be "picked up" by Cars and Cyclists, downed or not
 *      - Downed cats can be killed by police, buses, and ambulances
 *      - Meows when picked up (meow sound effect is part of Car and Cyclist, not Cat)
 *  - Every DEATH_TRIGGER amount of pedestrians that die (this can be changed), the VehicleWorld incurs divine punishment, 
 *    and everything on-screen dies. Ominous audio and fade-in black screen included.
 *  - There should be a counter at the top of the screen that shows how many pedestrians have died.
 *  - New Cyclist: when colliding with a Person, the rider is also downed. 
 *    When revived, the rider becomes a Person. Picks up cats. Cyclists drop a cat if they picked one up, when knocked down.
 *  - New Police: detects vehicles in range around it that have killed Persons, and chases them until it reaches them. 
 *    If reached, mutual destruction occurs. Never runs Persons over - cats are fair game. Picks up Persons.
 *    If their target is lost, they explode in shame.
 *  - Assorted helper classes for implementation of features: death effects, black screens, etc.
 *  - Bird chirping ambience.
 * 
 * Credits:
 * StatBar for healthbar implementation of Pedestrian taken from Jordan Cohen Bug Simulation
 * Police "seek()" method created based on Jordan Cohen Bug Simulation
 * Implementation of DeathEffect and subclasses based on Jordan Cohen Big Simulation
 * 
 * many were resized with an online resizer tool: onlinepngtools.com/resize-png
 *  - BangEffect by Joshua Bali from kindpng.com (https://www.kindpng.com/imgv/iTRwxww_transparent-comic-effect-png-bang-cartoon-png-png/)
 *  - Police by Null Norris from toppng.com (https://toppng.com/free-image/police-car-png-top-view-s-PNG-free-PNG-Images_38753)
 *  - Cyclist - no author credited from https://cdn.stardekk.com/b928cde7-3d0e-4415-a306-f2b19d64095f/icon-fietsverhuur.png, from https://www.fermesaintchristophe.com/en/
 *  - DeathEffect - no author credited from http://www.stickpng.com/img/icons-logos-emojis/emojis/skull-emoji
 *  - CatDeathEffect by "S400I Rte" from kindpng.com (https://www.kindpng.com/imgv/owxmRT_cat-skull-png-transparent-png/)
 *  - DivineRetribution by Riley Girard, from kindpng.com (https://www.kindpng.com/imgv/mRhhbm_death-png-angel-of-death-vector-transparent-png/)
 *  - Cat by R Wibisono, from kindpng.com (https://www.kindpng.com/imgv/iixToR_cute-cat-head-drawing-clipart-png-download-cat/)
 *  
 * Piano stinger for DivineRetribution is Kevin MacLeod's royalty-free "Blue Feather"
 *  - clipped it myself from a youtube-mp3 conversion, https://www.youtube.com/watch?v=jWaVlgAY7ek
 * The Person death scream is the well-known Wilhelm Scream from https://bigsoundbank.com/detail-0477-wilhelm-scream.html
 * Cat death sound uncredited from https://www.wavsource.com/snds_2020-10-01_3728627494378403/animals/cat_screech.wav
 * Cat meow when vehicles pick them up is uncredited from https://www.wavsource.com/snds_2020-10-01_3728627494378403/animals/cat_meow_x.wav
 * Police siren, uncredited, from http://wolo-usa.com/siren6.wav
 * Ambient bird chirping, uncredited, from https://www.freesoundslibrary.com/birds-chirping-sound-effect/
 * 
 * Issues:
 *  - Lane change is instant
 *  - Police self destructing when losing target is a lazy implementation 
 *      - i.e. if multiple police are on field and one of the police catch a guilty party, the other police explodes
 *      - why there can't be more than one police in the world
 *  - Police unexplainably and rarely orient themselves backwards when seeking guilty..?
 */
public class VehicleWorld extends World
{
    private GreenfootImage background;

    // Color Constants
    public static Color GREY_BORDER = new Color (108, 108, 108);
    public static Color GREY_STREET = new Color (88, 88, 88);
    public static Color YELLOW_LINE = new Color (255, 216, 0);

    // death trigger constant for divine retribution
    private static int DEATH_TRIGGER = 30;
    
    // Instance variables / Objects
    private boolean twoWayTraffic, splitAtCenter;
    private int laneHeight, laneCount, spaceBetweenLanes;
    private int[] lanePositionsY;
    private VehicleSpawner[] laneSpawners;
    private int pedestrianDeaths;
    private int ambientTimer;
    private GreenfootSound ambience;
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public VehicleWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1, false); 

        setPaintOrder (DivineRetribution.class, DeathEffect.class, StatBar.class, Pedestrian.class, Police.class, Bus.class, Car.class, Ambulance.class, BlackScreen.class);

        // set up background
        background = new GreenfootImage ("background01.png");
        setBackground (background);

        // Set critical variables
        laneCount = 6;
        laneHeight = 48;
        spaceBetweenLanes = 6;
        splitAtCenter = true;
        twoWayTraffic = true;

        // Init lane spawner objects 
        laneSpawners = new VehicleSpawner[laneCount];

        // Prepare lanes method - draws the lanes
        lanePositionsY = prepareLanes (this, background, laneSpawners, 222, laneHeight, laneCount, spaceBetweenLanes, twoWayTraffic, splitAtCenter);

        pedestrianDeaths = 0;
        
        // prepare sound
        ambience = new GreenfootSound("birds.mp3");
        ambience.setVolume(66);
    }
    
    
    // ambience matches state of world
    public void started() {
        ambience.play();
    }
    public void stopped() {
        ambience.pause();
    }
    
    public void act () {
        // method for playing ambient noise - basic timer plays noise every two minutes or so
        ambientTimer++;
        if (ambientTimer % 7200 == 0) {
            ambience.play();
        }
        
        spawn(); // spawn things
        checkPedestrianDeaths(DEATH_TRIGGER); // check if divine retribution can be spawned, and if it can, spawn it
        
        // display pedestrian death count and a divine retribution disclaimer
        showText((("Current Pedestrian Deaths: " + pedestrianDeaths) + "\n Every " + DEATH_TRIGGER + " deaths, divine wrath is incurred"), 400, 50);
    }
    
    public void incrementDeaths() { // called in divine retribution's class
        pedestrianDeaths++;
    }
    
    // method to handle the spawning of divine retribution
    public void checkPedestrianDeaths(int trigger) {
        boolean retributionFlag = (pedestrianDeaths % trigger == 0 && pedestrianDeaths > 1); // basic bool variable to check if deaths has been met
        if (retributionFlag) { // if so
            if (((ArrayList)getObjects(DivineRetribution.class)).size() == 0) { // if-block safe guard to only spawn one divine retribution
                addObject(new DivineRetribution(), 100, 150);
            }
        }
    }

    private void spawn () {
        // Chance to spawn a vehicle - only one police at a time can be active
        if (Greenfoot.getRandomNumber (60) == 0){
            int lane = Greenfoot.getRandomNumber(laneCount);
            if (!laneSpawners[lane].isTouchingVehicle()){
                int vehicleType = Greenfoot.getRandomNumber(5);
                if (vehicleType == 0){
                    addObject(new Car(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 1){
                    addObject(new Bus(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 2){
                    addObject(new Ambulance(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 3){
                    addObject(new Cyclist(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 4){
                    if (((ArrayList)getObjects(Police.class)).isEmpty()) {
                        addObject(new Police(laneSpawners[lane]), 0, 0);
                    }
                }
            }
        }

        // Chance to spawn a Pedestrian
        if (Greenfoot.getRandomNumber (60) == 0) {
            int xSpawnLocation = Greenfoot.getRandomNumber (600) + 100; // random between 99 and 699, so not near edges
            boolean spawnAtTop = Greenfoot.getRandomNumber(2) == 0 ? true : false;
            boolean spawnPerson = Greenfoot.getRandomNumber(3) > 0 ? true : false;
            if (spawnAtTop){    
                if (spawnPerson) {
                    addObject (new Person (1), xSpawnLocation, 50);
                } else {
                    addObject (new Cat (1), xSpawnLocation, 50);
                }
            } else {
                if (spawnPerson) {
                    addObject (new Person (-1), xSpawnLocation, 50);
                } else {
                    addObject (new Cat (-1), xSpawnLocation, 50);
                }
            }
        }
    }

       /**
     * <p>The prepareLanes method is a static (standalone) method that takes a list of parameters about the desired roadway and then builds it.</p>
     * 
     * <p><b>Note:</b> So far, Centre-split is the only option, regardless of what values you send for that parameters.</p>
     *
     * <p>This method does three things:</p>
     * <ul>
     *  <li> Determines the Y coordinate for each lane (each lane is centered vertically around the position)</li>
     *  <li> Draws lanes onto the GreenfootImage target that is passed in at the specified / calculated positions. 
     *       (Nothing is returned, it just manipulates the object which affects the original).</li>
     *  <li> Places the VehicleSpawners (passed in via the array parameter spawners) into the World (also passed in via parameters).</li>
     * </ul>
     * 
     * <p> After this method is run, there is a visual road as well as the objects needed to spawn Vehicles. Examine the table below for an
     * in-depth description of what the roadway will look like and what each parameter/component represents.</p>
     * 
     * <pre>
     *                  <=== Start Y
     *  ||||||||||||||  <=== Top Border
     *  /------------\
     *  |            |  
     *  |      Y[0]  |  <=== Lane Position (Y) is the middle of the lane
     *  |            |
     *  \------------/
     *  [##] [##] [##| <== spacing ( where the lane lines or borders are )
     *  /------------\
     *  |            |  
     *  |      Y[1]  |
     *  |            |
     *  \------------/
     *  ||||||||||||||  <== Bottom Border
     * </pre>
     * 
     * @param world     The World that the VehicleSpawners will be added to
     * @param target    The GreenfootImage that the lanes will be drawn on, usually but not necessarily the background of the World.
     * @param spawners  An array of VehicleSpawner to be added to the World
     * @param startY    The top Y position where lanes (drawing) should start
     * @param heightPerLane The height of the desired lanes
     * @param lanes     The total number of lanes desired
     * @param spacing   The distance, in pixels, between each lane
     * @param twoWay    Should traffic flow both ways? Leave false for a one-way street (Not Yet Implemented)
     * @param centreSplit   Should the whole road be split in the middle? Or lots of parallel two-way streets? Must also be two-way street (twoWay == true) or else NO EFFECT
     * 
     */
    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit){
        // Declare an array to store the y values as I calculate them
        int[] lanePositions = new int[lanes];
        // Pre-calculate half of the lane height, as this will frequently be used for drawing.
        // To help make it clear, the heightOffset is the distance from the centre of the lane (it's y position)
        // to the outer edge of the lane.
        int heightOffset = heightPerLane / 2;

        // draw top border
        target.setColor (GREY_BORDER);
        target.fillRect (0, startY, target.getWidth(), spacing);

        // Main Loop to Calculate Positions and draw lanes
        for (int i = 0; i < lanes; i++){
            // calculate the position for the lane
            lanePositions[i] = startY + spacing + (i * (heightPerLane+spacing)) + heightOffset ;

            // draw lane
            target.setColor(GREY_STREET); 
            // the lane body
            target.fillRect (0, lanePositions[i] - heightOffset, target.getWidth(), heightPerLane);
            // the lane spacing - where the white or yellow lines will get drawn
            target.fillRect(0, lanePositions[i] + heightOffset, target.getWidth(), spacing);

            // Place spawners and draw lines depending on whether its 2 way and centre split
            if (twoWay && centreSplit){
                // first half of the lanes go rightward (no option for left-hand drive, sorry UK students .. ?)
                if ( i < lanes / 2){
                    spawners[i] = new VehicleSpawner(false, heightPerLane);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else { // second half of the lanes go leftward
                    spawners[i] = new VehicleSpawner(true, heightPerLane);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw yellow lines if middle 
                if (i == lanes / 2){
                    target.setColor(YELLOW_LINE);
                    target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                } else if (i > 0){ // draw white lines if not first lane
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                } 

            } else if (twoWay){ // not center split
                if ( i % 2 == 0){
                    spawners[i] = new VehicleSpawner(false, heightPerLane);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else {
                    spawners[i] = new VehicleSpawner(true, heightPerLane);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw Grey Border if between two "Streets"
                if (i > 0){ // but not in first position
                    if (i % 2 == 0){
                        target.setColor(GREY_BORDER);
                        target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                    } else { // draw dotted lines
                        for (int j = 0; j < target.getWidth(); j += 120){
                            target.setColor (YELLOW_LINE);
                            target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                        }
                    } 
                }
            } else { // One way traffic
                spawners[i] = new VehicleSpawner(true, heightPerLane);
                world.addObject(spawners[i], 0, lanePositions[i]);
                if (i > 0){
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                }
            }
        }
        // draws bottom border
        target.setColor (GREY_BORDER);
        target.fillRect (0, lanePositions[lanes-1] + heightOffset, target.getWidth(), spacing);

        return lanePositions;
    }

}
