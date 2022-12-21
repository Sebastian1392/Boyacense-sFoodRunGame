package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import persistense.FileManager;
import presenters.Commands;

public class PanelMenuPause extends JPanel{

    private static final long serialVersionUID = 1L;

    public static final String BACKGRAUND_PATH = "src/img/BGPause.png";
    public static final String LOGO_TEXT_PATH = "src/img/pause.png";
    public static final String TEXT_REPLAY = "src/img/replay.png";
    public static final String TEXT_CONTINUE = "src/img/continueGame.png";
    public static final String TEXT_BACK_MENU = "src/img/menu.png";
    public static final int WIDTH_FRAME = 500;
    public static final int HEIGTH_FRAME = 600;
    public static final String LOGO_PATH = "src/img/logoGame.png";
    public static final String TITLE = "Boyacense's Food Run";
    public static final Color COLOR_GREEN = Color.decode("#0ed145");
    public static final Color COLOR_RED = Color.decode("#ec1c24");
    public static final Color COLOR_WHITE = Color.decode("#f5ecd2");
    public static final Border BORDER_PANEL = BorderFactory.createEmptyBorder(0, 0, 10, 0);
    public static final Dimension SIZE_BUTTON = new Dimension(175,70);

    public PanelMenuPause(ActionListener listener){
        setLayout(new GridLayout(4,1,0,3));
        setBorder(BORDER_PANEL);
        setOpaque(false);
        addLogoText();
        addButtons(listener);
    }

    private void addLogoText(){
        JLabel labelImage = new JLabel(Utilities.getImage(LOGO_TEXT_PATH,185,70));
        labelImage.setOpaque(false);
        add(labelImage);
    }

    private void addButtons(ActionListener listener){
        MenuButton continueButton = new MenuButton(COLOR_GREEN, Utilities.getImage(TEXT_CONTINUE,150,45), 
        SIZE_BUTTON,listener,Commands.PAUSE.toString());
        createPanel(continueButton);
        MenuButton replayButton = new MenuButton(COLOR_WHITE, Utilities.getImage(TEXT_REPLAY,150,45), 
        SIZE_BUTTON,listener,Commands.REPLAY.toString());
        createPanel(replayButton);
        MenuButton getOutButton = new MenuButton(COLOR_RED, Utilities.getImage(TEXT_BACK_MENU,120,55), 
        SIZE_BUTTON,listener,Commands.BACK_TO_MENU.toString());
        createPanel(getOutButton);
    }

    private void createPanel(Component component){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.add(component);
        add(panel);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(FileManager.readImage(BACKGRAUND_PATH), 0, 0, this.getWidth(), this.getHeight(), this);
        super.paint(g);
    }
    
}