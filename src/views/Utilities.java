package views;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Utilities {

    public static Icon getImage(String routeImage, int width, int heigth){
        ImageIcon icon = new ImageIcon(routeImage);
        Icon scaleIcon = new ImageIcon(icon.getImage().getScaledInstance(width, heigth, Image.SCALE_SMOOTH));
        return scaleIcon;
    }

    public static Icon getImage(Image image,int width, int heigth){
        ImageIcon icon = new ImageIcon(image);
        Icon scaleIcon = new ImageIcon(icon.getImage().getScaledInstance(width,heigth, Image.SCALE_SMOOTH));
        return scaleIcon;
    }

    public static Cursor cursor(){
        ImageIcon cursorImage = new ImageIcon("src/img/hat.png");
        Toolkit tk = Toolkit.getDefaultToolkit();
        Cursor cursor = tk.createCustomCursor(cursorImage.getImage(), new Point(1,1), "String");
        return cursor;
    }
    
}