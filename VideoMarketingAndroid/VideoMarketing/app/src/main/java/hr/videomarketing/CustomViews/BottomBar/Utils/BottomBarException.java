package hr.videomarketing.CustomViews.BottomBar.Utils;

/**
 * Created by bagy on 20.11.2016..
 */

public class BottomBarException extends Exception {
    public final static String resourceNotFoundException = "Bottom bar item resource not found";
    public final static String actionIdIsNull = "Items id not declared";
    public final static String listenerNull = "You must implement bottombar item interface";
    public final static String childException = "Child must be instanceof BottomBarItem";
    public BottomBarException(String mesage){
        super(mesage);
    }
}
