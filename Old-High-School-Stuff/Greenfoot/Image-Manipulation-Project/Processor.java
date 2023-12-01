/**
 * Starter code for Processor - the class that processes images.
 * <p> This class manipulated Java BufferedImages, which are effectively 2d arrays
 * of pixels. Each pixel is a single integer packed with 4 values inside it.</p>
 * 
 * <p>All methods added to this class should be static. In other words, you do not
 *    have to instantiate (create an object of) this class to use it - simply call
 *    the methods with Processor.methodName and pass a GreenfootImage to be manipulated.
 *    Note that you do not have to return the processed image, as you will be passing a
 *    reference to your image, and it will be altered by the method, as seen in the Blueify
 *    example.</p>
 *    
 * <p>Some methods, especially methods that change the size of the image (such as rotation
 *    or scaling) may require a GreenfootImage return type. This is because while it is
 *    possible to alter an image passed as a parameter, it is not possible to re-instantiate it, 
 *    which is required to change the size of a GreenfootImage</p>
 * 
 * <p>
 * I have included two useful methods for dealing with bit-shift operators so
 * you don't have to. These methods are unpackPixel() and packagePixel() and do
 * exactly what they say - extract red, green, blue and alpha values out of an
 * int, and put the same four integers back into a special packed integer. </p>
 * 
 * @author Jordan Cohen 
 * @version November 2013
 */

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import greenfoot.*;

public class Processor  
{
    /**
     * Example colour altering method by Mr. Cohen. This method will
     * increase the blue value while reducing the red and green values.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void blueify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (blue < 253)
                    blue += 2;
                if (red >= 50)
                    red--;
                if (green >= 50)
                    green--;

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }
    
    public static void redify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic RED-er
                if (red < 253)
                    red += 2;
                if (blue >= 50)
                    blue--;
                if (green >= 50)
                    green--;

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }
    
    public static void greenify (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic RED-er
                if (green < 253)
                    green += 2;
                if (blue >= 50)
                    blue--;
                if (red >= 50)
                    red--;

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }
    
    public static void grayscale (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                red = green;
                green = green;
                blue = green;

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }
    
    /**
     * Credits to "https://stackoverflow.com/questions/1061093/how-is-a-sepia-tone-created" for providing the algorithm.
     */
    public static void sepia (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                
                // ternary operators following the algorithm - if any of these are greater than 255, set it to 255.
                double newRed = 0.393*red + 0.769*green + 0.189*blue < 255 ? 0.393*red + 0.769*green + 0.189*blue: 255;
                double newBlue = 0.349*red + 0.686*green + 0.168*blue < 255 ? 0.349*red + 0.686*green + 0.168*blue: 255;
                double newGreen = 0.272*red + 0.534*green + 0.131*blue < 255 ? 0.272*red + 0.534*green + 0.131*blue: 255;

                int newColour = packagePixel ((int)newRed, (int)newBlue, (int)newGreen, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }
    
    public static void negative (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // invert all the colours by taking their "anti-colour", aka the colour that creates black when they're added
                int newColour = packagePixel (255-red, 255-green, 255-blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }
    
    public static void brighten (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newRed = red + 5 < 255 ? red+5: 255;
                int newBlue = blue + 5 < 255 ? blue+5: 255;
                int newGreen = green + 5 < 255 ? green+5: 255;

                
                // invert all the colours by taking their "anti-colour", aka the colour that creates black when they're added
                int newColour = packagePixel (newRed, newGreen, newBlue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }
    
    public static void darken (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newRed = red - 5 > 0 ? red-5: 0;
                int newBlue = blue - 5 > 0 ? blue-5: 0;
                int newGreen = green - 5 > 0 ? green-5: 0;

                int newColour = packagePixel (newRed, newGreen, newBlue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }
    
    /**
     * Credits to "https://math.stackexchange.com/questions/906240/algorithms-to-increase-or-decrease-the-contrast-of-an-image"
     */
    public static void contrast (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        
        // Using array size as limit
        for (int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgba = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgba);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // a pixel is "contrasted" when it is applied to the function f(x) = a(x-128) + 128 + b...apparently.
                // "a" determines the level of contrast, while "b" determines the amount of brightness added on to the pixel.
                int contrastSlope = 2;
                int brightnessConstant = 1;
                                
                int newRed = contrastSlope*(red-128) + 128 + brightnessConstant;
                int newBlue = contrastSlope*(blue-128) + 128 + brightnessConstant;
                int newGreen = contrastSlope*(green-128) + 128 + brightnessConstant;
                
                // sandwich new values between [0, 255]
                if (newRed < 0 || newRed > 255) {
                    if (newRed < 0) {
                        newRed = 0;
                    } else {
                        newRed = 255;
                    }
                }
                
                if (newGreen < 0 || newGreen > 255) {
                    if (newGreen < 0) {
                        newGreen = 0;
                    } else {
                        newGreen = 255;
                    }
                }
                
                if (newBlue < 0 || newBlue > 255) {
                    if (newBlue < 0) {
                        newBlue = 0;
                    } else {
                        newBlue = 255;
                    }
                }
                
                int newColour = packagePixel (newRed, newGreen, newBlue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }

    public static void flipHorizontal (BufferedImage bi)
    {

        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        /**
         * INSERT TWO LOOPS HERE:
         * - FIRST LOOP MOVES DATA INTO A SECOND, TEMPORARY IMAGE WITH PIXELS FLIPPED
         *   HORIZONTALLY
         * - SECOND LOOP MOVES DATA BACK INTO ORIGINAL IMAGE
         * 
         * Note: Due to a limitation in Greenfoot, you can get the backing BufferedImage from
         *       a GreenfootImage, but you cannot set or create a GreenfootImage based on a 
         *       BufferedImage. So, you have to use a temporary BufferedImage (as declared for
         *       you, above) and then copy it, pixel by pixel, back to the original image.
         *       Changes to bi in this method will affect the GreenfootImage automatically.
         */ 
        
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer, as well as a duplicate
                int rgba = bi.getRGB(x, y);
                
                newBi.setRGB(xSize - 1 - x, y, rgba);
            }
        }
        
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer, as well as a duplicate
                int rgba = newBi.getRGB(x, y);
                
                bi.setRGB(x, y, rgba);
            }
        }

    }
    
    public static void flipVertical (BufferedImage bi)
    {

        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        /**
         * INSERT TWO LOOPS HERE:
         * - FIRST LOOP MOVES DATA INTO A SECOND, TEMPORARY IMAGE WITH PIXELS FLIPPED
         *   HORIZONTALLY
         * - SECOND LOOP MOVES DATA BACK INTO ORIGINAL IMAGE
         * 
         * Note: Due to a limitation in Greenfoot, you can get the backing BufferedImage from
         *       a GreenfootImage, but you cannot set or create a GreenfootImage based on a 
         *       BufferedImage. So, you have to use a temporary BufferedImage (as declared for
         *       you, above) and then copy it, pixel by pixel, back to the original image.
         *       Changes to bi in this method will affect the GreenfootImage automatically.
         */ 
        
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer, as well as a duplicate
                int rgba = bi.getRGB(x, y);
                
                newBi.setRGB(x, ySize-1-y, rgba);
            }
        }
        
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer, as well as a duplicate
                int rgba = newBi.getRGB(x, y);
                
                bi.setRGB(x, y, rgba);
            }
        }

    }
    
    public static GreenfootImage rotate90Clockwise(BufferedImage bi) {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (ySize, xSize, 3);
        
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer, as well as a duplicate
                int rgba = bi.getRGB(x, y);
                
                newBi.setRGB(y, x, rgba);
            }
        }
        
        flipHorizontal(newBi);
    
        return createGreenfootImageFromBI(newBi);
    }
    
    public static GreenfootImage rotate90AntiClockwise(BufferedImage bi) {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        
        BufferedImage newBi = new BufferedImage (ySize, xSize, 3);
        
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer, as well as a duplicate
                int rgba = bi.getRGB(x, y);
                
                newBi.setRGB(y, x, rgba);
            }
        }
        
        flipVertical(newBi);
    
        return createGreenfootImageFromBI(newBi);
    }
    
    public static GreenfootImage rotate180(BufferedImage bi) {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);
        newBi = bi;
        flipHorizontal(newBi);
        flipVertical(newBi);
        return createGreenfootImageFromBI(newBi);
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
    
    public static BufferedImage deepCopy(BufferedImage bi) {
        return bi;
    }

    
    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }
    
    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single, 24-bit, integer.
     * The alpha value of the pixel is not included.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b)
    {
        // the bitwise operation of the previous method but the bit is 24-bit so you move everything over by 8 or something lol
        int newRGB = (r << 16) | (g << 8) | b;
        return newRGB;
    }
}
