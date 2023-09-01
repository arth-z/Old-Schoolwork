import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A collision helper for the lane change method, meant as the same size as a car.
 * Acts essentially as a transparent actor dummy.
 */
public class CollisionHelper extends Actor
{
    public CollisionHelper() {
    }
    
    public void act()
    {
        // Add your action code here.
    }
    
    // basically just public access to isTouching
    public boolean helperIsTouching(Class<?> type) {
        return isTouching(type);
    }
}
