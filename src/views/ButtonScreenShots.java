package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import presenters.Commands;

public class ButtonScreenShots extends JButton{

    private static final long serialVersionUID = 1L;

    public static final Border BORDER = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK);
    public static final int WIDTH = 138;
    public static final int HEIGTH = 170;
    
    public ButtonScreenShots(Image image, int name ,ActionListener listener){
        setPreferredSize(new Dimension(WIDTH,HEIGTH));
        setIcon(Utilities.getImage(image,WIDTH,HEIGTH));
        setName(String.valueOf(name));
        addActionListener(listener);
        setActionCommand(Commands.SHOW_IMAGE.toString());
        setBorder(BORDER);
        setCursor(Utilities.cursor());
        setOpaque(false);
        setVisible(true);
    }

    public ButtonScreenShots(Image image,int width, int heigth, ActionListener listener, String command){
        setPreferredSize(new Dimension(width,heigth));
        setIcon(Utilities.getImage(image,width,heigth));
        addActionListener(listener);
        setActionCommand(command);
        setCursor(Utilities.cursor());
        setBorderPainted(false);
        setFocusable(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setVisible(true);
    }
}