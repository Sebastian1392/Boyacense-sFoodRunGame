package views;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class DialogImage extends JDialog{

    private static final long serialVersionUID = 1L;
    public static final int WIDTH_FRAME = 370;
    public static final int HEIGTH_FRAME = 480;
    public static final int CUT_WIDTH = 16;
    public static final int CUT_HEIGTH = 27;
    public static final String TITLE_DIALOG = "ScreenShot";
    public static final String CAMERA_PATH = "src/img/camera.png";

    private JLabel labelImage;

    public DialogImage(KeyListener keyListener){
        setTitle(TITLE_DIALOG);
        setIconImage(new ImageIcon(CAMERA_PATH).getImage());
        setSize(new Dimension(WIDTH_FRAME,HEIGTH_FRAME));
        addKeyListener(keyListener);
        setResizable(false);
        setModal(true);
        setAlwaysOnTop(true); 
        setCursor(Utilities.cursor());
        
        labelImage = new JLabel();
        add(labelImage);
    }

    public void showImageToDialog(Image image){
        labelImage.setIcon(Utilities.getImage(image,WIDTH_FRAME - CUT_WIDTH,HEIGTH_FRAME - CUT_HEIGTH));
        labelImage.repaint();
    }
    
}