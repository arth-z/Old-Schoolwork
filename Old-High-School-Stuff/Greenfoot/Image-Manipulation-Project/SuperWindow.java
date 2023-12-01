import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A draggable (and lockable) window that can hold UI elements (any Actors).
 * 
 * Actors that are held will follow the window if dragged. 
 * 
 * Windows may be closable (in which case there will be a red button to close it) or may be
 * not closable (in which case it can be closed via code, or just left permanently in place).
 * 
 * Notes - The World's paint order is very important here. Anything that should be shown in the Window should
 * be put before the Window in the paint order, and everything else should be after the SuperWindow. For example, see 
 * MyWorld (MrCohenLibrary).
 * 
 * Version 0.2
 * - Adding ability to draw without dragbar
 * 
 * @author Jordan Cohen 
 * @version 0.2 (April 2022)
 */
public class SuperWindow extends Actor
{
    protected GreenfootImage image;

    protected boolean locked;
    protected boolean isBeingDragged;
    protected boolean closable;
    protected boolean minable, minimized; // short for minimizable
    protected boolean dragBar;
    protected int dragBarHeight;
    protected int dragOriginX, dragOriginY;
    protected int width, height, currentHeight;
    protected int localX, localY;

    protected String title;

    protected Color borderColor;

    private Actor temp;

    private MouseInfo mouseInfo;

    private ArrayList<ActorContent> contents;    

    public static final Color WINDOW_BACK_COLOR = new Color (200, 200, 200);
    public static final Color WINDOW_TEXT_COLOR = new Color (0, 0, 15);
    public static final Color WINDOW_BORDER_COLOR_ACTIVE = Color.RED;
    public static final Color WINDOW_BORDER_COLOR_INACTIVE = Color.WHITE;
    public static final Color WINDOW_DRAGBAR_COLOR = new Color (90, 90, 90);

    public static final int DEFAULT_DRAGBAR_HEIGHT = 18;
    public static final int WINDOW_BORDER_THICKNESS = 2;

    // Default Constructor
    public SuperWindow (int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        borderColor = WINDOW_BORDER_COLOR_INACTIVE;
        closable = true;
        locked = false;
        dragBar = true;
        minable = true; // can be minimized - minimizable 
        minimized = false;
        dragBarHeight = DEFAULT_DRAGBAR_HEIGHT;
        currentHeight = height;
        image = drawWindow (width, height, dragBarHeight, borderColor, title, closable, minable, minimized);
        setImage(image);
        isBeingDragged = false;
        contents = new ArrayList<ActorContent>();
    }

    /**
     * <p>Create a SuperWindow. Flags are in an array to avoid very long param list.</p>
     * 
     * <b>flags:</b>
     * <pre>
     * 0 - closable - can close
     * 1 - locked - prevent movement
     * 2 - Draw the dragBar
     * 3 - minable - can be minimized
     * 4 - spawn minimized</pre>
     */
    public SuperWindow (int width, int height, int dragBarHeight, String title, boolean[] flags){
        // Dimensions and Title
        this.width = width;
        this.height = height;
        currentHeight = height;
        this.title = title;

        // Colors
        borderColor = WINDOW_BORDER_COLOR_INACTIVE;

        // Flags
        closable = flags[0];
        locked = flags[1];
        dragBar = flags[2];
        minable = flags[3];
        minimized = flags[4];

        // Booleans
        isBeingDragged = false;

        // Draw
        this.dragBarHeight = dragBarHeight;
        image = drawWindow (width, height, dragBarHeight, borderColor, title, closable, minable, minimized);
        setImage(image);

        contents = new ArrayList<ActorContent>();
    }

    /**
     * <p>Create a SuperWindow sized to fit around an Actor. This is useful if you 
     * want one Actor to take up all of the space within a SuperWindow.</p>
     * 
     * <b>flags:</b>
     * <pre>
     * 0 - closable - can close
     * 1 - locked - prevent movement
     * 2 - Draw the dragBar
     * 3 - minable - can be minimized
     * 4 - spawn minimized</pre>
     */
    public SuperWindow (Actor fullSizeActor, int dragBarHeight, String text,  boolean flags[]){
        this (fullSizeActor.getImage().getWidth() + WINDOW_BORDER_THICKNESS * 2, fullSizeActor.getImage().getHeight() + WINDOW_BORDER_THICKNESS *2 + dragBarHeight, dragBarHeight, text, flags);
        temp = fullSizeActor;
        
    }

    public void addedToWorld (World w){
        if (temp != null){
            addActor(temp, WINDOW_BORDER_THICKNESS, WINDOW_BORDER_THICKNESS + dragBarHeight);
        }
    }

    public void act()
    {
        mouseInfo = ((Background)getWorld()).getMouseInfo(); // see World for corresponding method and note
        // Offsets so that top left corner of window is 0, 0
        int xOffset = getX() - (width / 2);
        int yOffset = getY() - (currentHeight / 2);
        if (mouseInfo != null){
            // Clicks on the Window
            if (Greenfoot.mouseClicked(this)){
                // Calculate where mouse in within Window
                localX = mouseInfo.getX() - xOffset;
                localY = mouseInfo.getY() - yOffset;
                //System.out.println("localX: " + localX + " localY: " + localY);
                if (dragBar && (closable || minable)){
                    if (localX > width - dragBarHeight - WINDOW_BORDER_THICKNESS - 1 && localY < dragBarHeight + WINDOW_BORDER_THICKNESS){
                        closeButton();
                        return;

                    } else if (localX > width - (2 * dragBarHeight) - WINDOW_BORDER_THICKNESS - (dragBarHeight / 3) && localY < dragBarHeight + WINDOW_BORDER_THICKNESS) {
                        minimizeButton ();
                        //return;
                    }
                }
            }
            
            // Functionality for dragBar enabled Windows
            if (dragBar){
                // Drag Start
                if (!locked && !isBeingDragged && Greenfoot.mouseDragged(this)){
                    // is on dragbar?
                    localX = mouseInfo.getX() - xOffset;
                    localY = mouseInfo.getY() - yOffset;
                    if (localY <= dragBarHeight + WINDOW_BORDER_THICKNESS){
                        // Begin drag
                        isBeingDragged = true;
                    }
                }
                // Drag End
                if (isBeingDragged && Greenfoot.mouseDragEnded(null)){
                    isBeingDragged = false;
                }
                // Drag in Progress
                if (isBeingDragged && Greenfoot.mouseDragged(null)){
                    // Move window
                    setLocation (mouseInfo.getX() - localX + (width / 2), mouseInfo.getY() - localY + (currentHeight / 2));
                    
                    // Move all contained Actors
                    for (ActorContent ac : contents){
                        Actor a = ac.getActor();
                        a.setLocation (getX() - (width / 2) + ac.getX() + a.getImage().getWidth()/2, getY() - (height / 2) + ac.getY() + a.getImage().getHeight()/2);
                    }
                }
            }
        }
    }

    protected void minimizeButton () {
        // toggle minimized
        minimized = !minimized;
        if (minimized){ // minimize
            currentHeight = dragBarHeight + (2 * WINDOW_BORDER_THICKNESS);
            // shift position because of new  smaller size
            setLocation (getX(), getY() - (height / 2) + (WINDOW_BORDER_THICKNESS  + (dragBarHeight/2)));
            for (ActorContent ac : contents){
                Actor a = ac.getActor();
                getWorld().removeObject(a);
            } 

        } else { // maximize ... unminimize?
            currentHeight = height;
            // shift position because of new larger size
            setLocation (getX(), getY() + (height / 2) - (WINDOW_BORDER_THICKNESS  + (dragBarHeight/2)));
            for (ActorContent ac : contents){
                Actor a = ac.getActor();
                getWorld().addObject ( a, getX() - (width / 2) + ac.getX() + a.getImage().getWidth()/2, getY() - (height / 2) + ac.getY() + a.getImage().getHeight()/2);
            }

        }
        // redraw appropriately
        image = drawWindow (width, height, dragBarHeight, borderColor, title, closable, minable, minimized);
        setImage(image);
    }

    public void refresh () {
        for (ActorContent ac : contents){
            Actor a = ac.getActor();
            getWorld().addObject ( a, getX() - (width / 2) + ac.getX() + a.getImage().getWidth()/2, getY() - (height / 2) + ac.getY() + a.getImage().getHeight()/2);
        }
    }

    // Close button has been clicked
    public void closeButton () {
        // Delete all contained Actors from the World
        for (ActorContent ac : contents){
            Actor a = ac.getActor();
            getWorld().removeObject(a);
        }
        // remove self
        getWorld().removeObject(this);
    }

    public void addActor (Actor a, int xx, int yy){
        contents.add (new ActorContent (a, xx, yy));
        if (a.getWorld() == null){
            getWorld().addObject(a, getX() - (width / 2) + xx + a.getImage().getWidth()/2, getY() - (height / 2) + yy + a.getImage().getHeight()/2);
        } else {
            a.setLocation (getX() - (width / 2) + xx + a.getImage().getWidth()/2, getY() - (height / 2) + yy + a.getImage().getHeight()/2);
        }
    }

    public void removeActor (Actor a){
        if (contents.contains(a)){
            contents.remove(a);
        }
    }

    protected static GreenfootImage drawWindow(int width, int height, int dragBarHeight, Color borderColor, String title, boolean closable, boolean minable, boolean minimized ){
        if (minimized){
            height = dragBarHeight + WINDOW_BORDER_THICKNESS * 2;
        }

        GreenfootImage temp = new GreenfootImage (width, height);
        temp.setColor (WINDOW_BACK_COLOR);
        temp.fill();
        temp.setColor (borderColor);

        for (int i = 0; i < WINDOW_BORDER_THICKNESS; i++){
            temp.drawRect (i, i, width - (i*2), height - (i*2));
        }

        if (dragBarHeight > 0){
            temp.setColor (WINDOW_DRAGBAR_COLOR);
            temp.fillRect (WINDOW_BORDER_THICKNESS, WINDOW_BORDER_THICKNESS, width - (WINDOW_BORDER_THICKNESS*2), dragBarHeight);

            // Draw title
            Font windowFont = new Font ("Arial", false, false, dragBarHeight - 4);
            temp.setFont (windowFont);
            temp.setColor (WINDOW_TEXT_COLOR);
            temp.drawString (title, WINDOW_BORDER_THICKNESS + 4, dragBarHeight - 2);
        }
        // If Closable, draw a red circle as the X to close
        if (closable){
            temp.setColor (new Color (255, 70, 70));
            temp.fillOval (width - dragBarHeight - WINDOW_BORDER_THICKNESS - 1, WINDOW_BORDER_THICKNESS + 1, dragBarHeight-3 , dragBarHeight-3);
        }

        if (minable){
            temp.setColor (new Color (255, 255, 150));
            temp.fillOval (width - dragBarHeight - WINDOW_BORDER_THICKNESS - 1 - dragBarHeight-3, WINDOW_BORDER_THICKNESS + 1, dragBarHeight-3 , dragBarHeight-3);
        }

        return temp;
    }
}

/**
 * Container to hold and Actor and an LOCAL position (position x, y on the WINDOW's Canvas, 0,0 in top left)
 */
class ActorContent {
    private Actor actor;
    private int xx, yy;
    public ActorContent(Actor actor, int xx, int yy){
        this.actor = actor;
        this.xx = xx;
        this.yy = yy;
    }

    public void setLocation (int x, int y){
        xx = x;
        yy = y;
    }

    public int getX() {
        return xx;
    }

    public int getY() {
        return yy;
    }

    public Actor getActor(){
        return actor;
    }
}

