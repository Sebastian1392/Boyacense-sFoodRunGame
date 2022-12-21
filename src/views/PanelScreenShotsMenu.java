package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import persistense.FileManager;
import presenters.Commands;

public class PanelScreenShotsMenu extends JPanel{

    private static final long serialVersionUID = 1L;

    public static final String BACKGRAUND_PATH = "src/img/BG.png";
    public static final String LOGO_TEXT_PATH = "src/img/screenshots2.png";
    public static final String LOGO_BUTTON_PATH = "src/img/buttonback.png";
    public static final String LOGO_MOUSE_PATH = "src/img/mouse.png";
    public static final String LOGO_KEY_PATH = "src/img/keyInstrutions.png";
    public static final Border BORDER = BorderFactory.createEmptyBorder(0, 15, 0, 15);
    public static final Border BORDER_PANEL_TITLE = BorderFactory.createEmptyBorder(0, 0, 0, 40);
    public static final Font LABELS_FONT = new Font("Cooper Black", Font.PLAIN,14);
    public static final String MOUSE_INSTRUCTION = "Seleccionar Imagen";
    public static final String KEY_INSTRUCTION = "Retroceder/Avanzar Imagen";
    
    private PanelScreenShots panelScreens;

    public PanelScreenShotsMenu(ActionListener listener){
        setLayout(new BorderLayout());
        setBorder(BORDER);
        addLogoText(listener);
        addScreens();
        addInstrutions();
        setOpaque(false);
    }

    private void addLogoText(ActionListener listener){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BORDER_PANEL_TITLE);
        ButtonScreenShots back = new ButtonScreenShots(FileManager.readImage(LOGO_BUTTON_PATH), 70, 70,listener,
        Commands.BACK_MENU.toString());
        panel.add(back,BorderLayout.WEST);
        JLabel labelImage = new JLabel(Utilities.getImage(LOGO_TEXT_PATH,195,110));
        labelImage.setOpaque(false);
        panel.add(labelImage, BorderLayout.CENTER);
        add(panel,BorderLayout.NORTH);
    }

    public void addScreens(){
        panelScreens =  new PanelScreenShots();
        JScrollPane bar = new JScrollPane(panelScreens);
        bar.setOpaque(false);
        add(bar, BorderLayout.CENTER);
    }


    public void addImagesToPanel(ActionListener listener,ArrayList<Image> screenList){
        panelScreens.addImagesToPanel(listener,screenList);
    }

    public Image getImage(int numberImage){
        return panelScreens.getImage(numberImage);
    }

    public void changeImage(boolean side){
        panelScreens.changeImage(side);
    }

    public int getPositionImage(){
        return panelScreens.getPositionImage();
    }

    private void addInstrutions(){
        JPanel panelInstructions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInstructions.setOpaque(false);

        JLabel mouseLabel = new JLabel(MOUSE_INSTRUCTION);
        mouseLabel.setFont(LABELS_FONT);
        mouseLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        mouseLabel.setVerticalTextPosition(SwingConstants.CENTER);
        mouseLabel.setIcon(Utilities.getImage(LOGO_MOUSE_PATH, 80, 100));
        mouseLabel.setForeground(Color.BLACK);
        panelInstructions.add(mouseLabel);
        
        JLabel keyLabel = new JLabel(KEY_INSTRUCTION);
        keyLabel.setFont(LABELS_FONT);
        keyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        keyLabel.setVerticalTextPosition(SwingConstants.TOP);
        keyLabel.setIcon(Utilities.getImage(LOGO_KEY_PATH, 135, 90));
        keyLabel.setForeground(Color.BLACK);
        panelInstructions.add(keyLabel);

        add(panelInstructions, BorderLayout.SOUTH);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(FileManager.readImage(BACKGRAUND_PATH),0,0,this.getWidth(), this.getHeight(), this);
        super.paint(g);
    }  
    
}