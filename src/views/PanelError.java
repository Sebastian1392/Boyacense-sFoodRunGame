package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import persistense.FileManager;
import presenters.Commands;

public class PanelError extends JPanel{

    private static final long serialVersionUID = 1L;

    public static final String BACKGRAUND_PATH = "src/img/BGError.png";
    public static final String TEXT_ACEPT = "src/img/acept.png"; 
    public static final String LOGO_TEXT_PATH = "src/img/error.png";
    public static final int WIDTH_FRAME = 500;
    public static final int HEIGTH_FRAME = 600;
    public static final Color COLOR_GREEN = Color.decode("#0ed145");
    public static final Color COLOR_RED = Color.decode("#ec1c24");
    public static final Color COLOR_WHITE = Color.decode("#f5ecd2");
    public static final Border BORDER_PANEL = BorderFactory.createEmptyBorder(10, 22, 10, 0);
    public static final Dimension SIZE_BUTTON = new Dimension(175,70);
    public static final String TEXT_ERROR = "<html>No puedes acceder a esta función. Debes empezar<br>una partida.</html>";
    public static final Font FONT = new Font("Cooper Black", Font.PLAIN, 25);
    
    public PanelError(ActionListener listener){
        setLayout(new BorderLayout());
        setBorder(BORDER_PANEL);
        setOpaque(false);
        addLogoText();
        addText();
        addButtons(listener);
    }

    private void addLogoText(){
        JLabel labelImage = new JLabel(Utilities.getImage(LOGO_TEXT_PATH,185,70));
        labelImage.setOpaque(false);
        add(labelImage,BorderLayout.PAGE_START);
    }

    private void addText(){
        JLabel labelText = new JLabel(TEXT_ERROR);
        labelText.setFont(FONT);
        labelText.setForeground(COLOR_WHITE);
        labelText.setOpaque(false);
        add(labelText,BorderLayout.CENTER);
    }

    private void addButtons(ActionListener listener){
        MenuButton continueButton = new MenuButton(COLOR_GREEN, Utilities.getImage(TEXT_ACEPT,120,55), 
        SIZE_BUTTON,listener,Commands.ACEPT.toString());
        createPanel(continueButton);
    }

    private void createPanel(Component component){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.add(component);
        add(panel,BorderLayout.PAGE_END);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(FileManager.readImage(BACKGRAUND_PATH), 0, 0, this.getWidth(), this.getHeight(), this);
        super.paint(g);
    }
    
}