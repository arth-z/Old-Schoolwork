import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
/**
 * Starter code for Image Manipulation Array Assignment.
 * 
 * The class Processor contains all of the code to actually perform
 * transformation. The rest of the classes serve to support that
 * capability. This World allows the user to choose transformations
 * and open files.
 * 
 * Notes: 
 * 
 * the interface gets very crowded when an image is loaded in with an extremely large size
 * (i.e. 4096x3048)
 * 
 * Both save file options save the file regardless of if you close the window, or click "ok". The code was provided to us like this.
 * This has taken way too much effort to even try to fix, so I haven't fixed it.
 * 
 * Add to it and make it your own!
 * Sepia Credit: "https://stackoverflow.com/questions/1061093/how-is-a-sepia-tone-created" for providing the algorithm.
 * Contrast Credit: "https://math.stackexchange.com/questions/906240/algorithms-to-increase-or-decrease-the-contrast-of-an-image"
 * SuperWindow Credit: Mr Cohen Library v1.13
 * 
 * @author Jordan Cohen
 * @version November 2013
 */
public class Background extends World
{
    // Constants:
    private final String STARTING_FILE = "sword saint and woman.png";
    public static final int MAX_WIDTH = 800;
    public static final int MAX_HEIGHT = 720;

    // Objects and Variables:
    private ImageHolder image;

    private TextButton blueButton, hRevButton, openButton, saveButton, vRevButton, rotate90ClockwiseButton, rotate90AntiClockwiseButton,
                        rotate180Button, greenButton, redButton, grayButton, sepiaButton, negativeButton, saveJPGButton, brightenButton,
                        darkenButton, contrastButton, undoButton;

    private SuperTextBox openFile;
    private SuperWindow colourControl, attributeControl, systemControl, imageHolderControl;

    private String fileName;
    private MouseInfo m;

    /**
     * Constructor for objects of class Background.
     * 
     */
    public Background()
    {    
        super(1200, 800, 1); 

        // Initialize buttons and the image
        image = new ImageHolder(STARTING_FILE); // The image holder constructor does the actual image loading
        
        // Set up UI elements
        blueButton = new TextButton("Blueify", 8, 160, true, Color.BLACK, Color.BLUE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        redButton = new TextButton("Redify", 8, 160, true, Color.BLACK, Color.RED, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        greenButton = new TextButton("Greenify", 8, 160, true, Color.BLACK, Color.GREEN, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        grayButton = new TextButton("Grayscale", 8, 160, true, Color.BLACK, Color.GRAY, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        sepiaButton = new TextButton("Sepia", 8, 160, true, Color.BLACK, Color.ORANGE, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        negativeButton = new TextButton("Negative", 8, 160, true, Color.BLACK, Color.LIGHT_GRAY, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        darkenButton = new TextButton("Darken", 8, 160, true, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        brightenButton = new TextButton("Brighten", 8, 160, true, Color.BLACK, Color.LIGHT_GRAY, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        contrastButton = new TextButton("Contrast", 8, 160, true, Color.BLACK, Color.LIGHT_GRAY, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));

        
       // blueButton.setFixedWidth(160); // setting a fixed width so buttons will be the same width
        hRevButton = new TextButton("Flip Horizontal", 8, 160, true, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        saveButton = new TextButton("Save File", 8, 160, true, Color.BLACK, Color.GREEN, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        saveJPGButton = new TextButton("Save File as JPG", 8, 160, true, Color.BLACK, Color.GREEN, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        vRevButton = new TextButton("Flip Vertical", 8, 160, true, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        rotate90ClockwiseButton = new TextButton("Rotate CW", 8, 160, true, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        rotate90AntiClockwiseButton = new TextButton("Rotate AntiCW", 8, 160, true, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        rotate180Button = new TextButton("Rotate 180", 8, 160, true, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        undoButton = new TextButton("Undo", 8, 160, true, Color.BLACK, Color.RED, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        
        openButton = new TextButton ("Open", 8, 80, true, Color.BLACK, Color.GREEN, Color.WHITE, Color.WHITE, Color.BLACK, new Font ("Verdana",false ,false ,16));
        //openButton.setFixedWidth(80);
        
        openFile = new SuperTextBox(new String[]{"File: " + STARTING_FILE,"Scale: " + image.getScale() + " W: " + image.getRealWidth() + " H: " + image.getRealHeight()}, new Font ("Comic Sans MS", false, false, 16), 600, true);//new TextButton(" [ Open File: " + STARTING_FILE + " ] ");

        colourControl = new SuperWindow(200, 438, 18, "Colour Control", new boolean[]{false, false, true, true, false});
        systemControl = new SuperWindow(200, 210, 18, "System Control", new boolean[]{false, false, true, true, false});
        attributeControl = new SuperWindow(200, 294, 18, "Attribute Control", new boolean[]{false, false, true, true, false});
        imageHolderControl = new SuperWindow(750, 750, 30, "Image", new boolean[]{false, false, true, true, false});
        
        // Add objects to the screen
        
        addObject (colourControl, 875, 300);
        colourControl.addActor(blueButton, 20, 42);
        colourControl.addActor(redButton, 20, 84);
        colourControl.addActor(greenButton, 20, 126);
        colourControl.addActor(sepiaButton, 20, 168);
        colourControl.addActor(grayButton, 20, 210);
        colourControl.addActor(negativeButton, 20, 252);
        colourControl.addActor(contrastButton, 20, 294);
        colourControl.addActor(brightenButton, 20, 336);
        colourControl.addActor(darkenButton, 20, 378);
        colourControl.refresh();
        
        addObject (attributeControl, 1090, 228);
        attributeControl.addActor (rotate90ClockwiseButton, 20, 42);
        attributeControl.addActor (rotate90AntiClockwiseButton, 20, 84);
        attributeControl.addActor (rotate180Button, 20, 126);
        attributeControl.addActor (hRevButton, 20, 168);
        attributeControl.addActor (vRevButton, 20, 210);
        attributeControl.refresh();
        
        addObject (systemControl, 990, 650);
        systemControl.addActor (undoButton, 20, 42);
        systemControl.addActor (saveButton, 20, 84);
        systemControl.addActor (saveJPGButton, 20, 126);
        systemControl.refresh();
    
        addObject (imageHolderControl, 375, 430);
        imageHolderControl.addActor(image, 178 - (int)(image.getRealWidth()*image.getScale()/4), 215 - (int)(image.getRealHeight()*image.getScale()/4));
        // place the open file text box in the top left corner
        addObject (openFile, openFile.getImage().getWidth() / 2, openFile.getImage().getHeight() / 2);
        // place the open file button directly beside the open file text box
        addObject (openButton, openFile.getImage().getWidth()  + openButton.getImage().getWidth()/2 + 2, openFile.getImage().getHeight() / 2);
        
    }

    /**
     * Act() method just checks for mouse input
     */
    public void act ()
    {
        m = Greenfoot.getMouseInfo();
        checkMouse();
    }

    /**
     * Check for user clicking on a button
     */
    private void checkMouse ()
    {
        // Avoid excess mouse checks - only check mouse if somethething is clicked.
        if (Greenfoot.mouseClicked(null))
        {
            if (Greenfoot.mouseClicked(blueButton)){
                image.updateLastImages();
                Processor.blueify(image.getBufferedImage());
                image.redraw();
                openFile.update (image.getDetails ());
            }
            else if (Greenfoot.mouseClicked(redButton)){
                image.updateLastImages();
                Processor.redify(image.getBufferedImage());
                image.redraw();
                openFile.update (image.getDetails ());
            }
            else if (Greenfoot.mouseClicked(greenButton)){
                image.updateLastImages();
                Processor.greenify(image.getBufferedImage());
                image.redraw();
                openFile.update (image.getDetails ());
            }
            else if (Greenfoot.mouseClicked(grayButton)){
                image.updateLastImages();
                Processor.grayscale(image.getBufferedImage());
                image.redraw();
                openFile.update (image.getDetails ());
            }
            else if (Greenfoot.mouseClicked(sepiaButton)){
                image.updateLastImages();
                Processor.sepia(image.getBufferedImage());
                image.redraw();
                openFile.update (image.getDetails ());
            }
            else if (Greenfoot.mouseClicked(negativeButton)){
                image.updateLastImages();
                Processor.negative(image.getBufferedImage());
                image.redraw();
                openFile.update (image.getDetails ());
            }
            else if (Greenfoot.mouseClicked(contrastButton)){
                image.updateLastImages();
                Processor.contrast(image.getBufferedImage());
                image.redraw();
                openFile.update (image.getDetails ());
            }
            else if (Greenfoot.mouseClicked(brightenButton)){
                image.updateLastImages();
                Processor.brighten(image.getBufferedImage());
                image.redraw();
                openFile.update (image.getDetails ());
            }
            else if (Greenfoot.mouseClicked(darkenButton)){
                image.updateLastImages();
                Processor.darken(image.getBufferedImage());
                image.redraw();
                openFile.update (image.getDetails ());
            }
            else if (Greenfoot.mouseClicked(hRevButton)){
                image.updateLastImages();
                Processor.flipHorizontal(image.getBufferedImage());
                image.redraw();
                openFile.update(image.getDetails());
                //BufferedImage temp = Processor.rotate90Clockwise (image.getBufferedImage());
                //image.setImage(createGreenfootImageFromBI (temp));
            }
            else if (Greenfoot.mouseClicked(vRevButton)) {
                image.updateLastImages();
                Processor.flipVertical(image.getBufferedImage());
                image.redraw();
                openFile.update(image.getDetails());
            }
            else if (Greenfoot.mouseClicked(rotate90ClockwiseButton)) {
                image.updateLastImages();
                image.setNewImage(Processor.rotate90Clockwise(image.getBufferedImage()));
                image.redraw();
                openFile.update(image.getDetails());
            }
            else if (Greenfoot.mouseClicked(rotate90AntiClockwiseButton)) {
                image.updateLastImages();
                image.setNewImage(Processor.rotate90AntiClockwise(image.getBufferedImage()));
                image.redraw();
                openFile.update(image.getDetails());
            }
            else if (Greenfoot.mouseClicked(rotate180Button)) {
                image.updateLastImages();
                image.setNewImage(Processor.rotate180(image.getBufferedImage()));
                image.redraw();
                openFile.update(image.getDetails());
            }
            else if (Greenfoot.mouseClicked(undoButton)) {
                image.restoreLastImage();
                image.redraw();
                openFile.update(image.getDetails());
            }
            else if (Greenfoot.mouseClicked(saveButton)) {
                saveFile();
            }
            else if (Greenfoot.mouseClicked(saveJPGButton)) {
                saveFileJPG();
            }
            else if (Greenfoot.mouseClicked(openButton))
            {
                openFile ();
            }
        }
    }

    // Code provided, but not yet implemented - This will save image as a png.
    private void saveFile () {
        try{
            // This will pop up a text input box - You should improve this with a JFileChooser like for the open function
            String fileName = JOptionPane.showInputDialog("Enter a file name (no extension)");
            
            fileName += ".png";
            File f = new File (fileName);  
            ImageIO.write(image.getBufferedImage(), "png", f); 
            
        }
        catch (IOException e){
            // this code instead
            System.out.println("Unable to save file: " + e);
        }
    }

    private void saveFileJPG () {
        try{
            // This will pop up a text input box - You should improve this with a JFileChooser like for the open function
            String fileName = JOptionPane.showInputDialog("Input file name (no extension)");

            fileName += ".jpg";
            File f = new File (fileName);
            BufferedImage noAlphas= new BufferedImage(image.getRealWidth(), image.getRealHeight(), BufferedImage.TYPE_INT_RGB);
            
            for (int y = 0; y < image.getRealHeight(); y++)
            {
                for (int x = 0; x < image.getRealWidth(); x++)
                {
                    // Calls method in BufferedImage that returns R G B and alpha values
                    // encoded together in an integer
                    int rgba = image.getBufferedImage().getRGB(x, y);
    
                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    int[] rgbValues = Processor.unpackPixel (rgba);
    
                    int alpha = rgbValues[0];
                    int red = rgbValues[1];
                    int green = rgbValues[2];
                    int blue = rgbValues[3];
    
                    int newColour = Processor.packagePixel (red, green, blue);
                    noAlphas.setRGB (x, y, newColour);
                }
            }
            ImageIO.write(noAlphas, "jpg", f); 

        }
        catch (IOException e){
            // this code instead
            System.out.println("Unable to save file: " + e);
        }
    }
    
    /**
     * Allows the user to open a new image file.
     */
    private void openFile ()
    {
        // Create a UI frame (required to show a UI element from Java.Swing)
        JFrame frame = new JFrame();
        // Create a JFileChooser, a file chooser box included with Java 
        JFileChooser fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            if (image.openFile (selectedFile.getAbsolutePath(), selectedFile.getName()))
            {
                //String display = " [ Open File: " + selectedFile.getName() + " ] ";
                openFile.update (image.getDetails ());
            }
        }
        // If the file opening operation is successful, update the text in the open file button
        /**if (image.openFile (fileName))
        {
        String display = " [ Open File: " + fileName + " ] ";
        openFile.update (display);
        }*/
        
    }

    /**
     * Takes in a BufferedImage and returns a GreenfootImage.
     * 
     * @param newBi The BufferedImage to convert.
     * 
     * @return GreenfootImage   A GreenfootImage built from the BufferedImage provided.
     */
    public static GreenfootImage createGreenfootImageFromBI (BufferedImage newBi)
    {
        GreenfootImage returnImage = new GreenfootImage (newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D)backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);

        return returnImage;
    }
    
    /**
     * Sharing mouseInfo is important.
     * 
     * Greenfoot can only poll Greenfoot.getMouseInfo() once per act. I suggest
     * putting this in your World so that your Actors can share this. Otherwise,
     * literally only one object can access the mouse data per act, which is not ideal.
     * Note that this World's act method contains m = Greenfoot.getMouseInfo, and that
     * the act order sets the World to act first.
     * 
     * @return MouseInfo the current state of the mouse as captured by World at the start of this act
     */
    public MouseInfo getMouseInfo() {
          if (m == null){
              m = Greenfoot.getMouseInfo();
          }
        return m;
    }
}

