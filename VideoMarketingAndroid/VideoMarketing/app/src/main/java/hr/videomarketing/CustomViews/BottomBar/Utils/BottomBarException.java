package hr.videomarketing.CustomViews.BottomBar.Utils;

/**
 * Created by bagy on 20.11.2016..
 */

public class BottomBarException extends Exception {
    public final static String resourceNotFoundException = "Bottom bar item resource not found";
    public final static String actionIdIsNull = "Items id not declared";
    public BottomBarException(String mesage){
        super(mesage);
    }
}
