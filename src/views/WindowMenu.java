package views;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class WindowMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH_FRAME = 500;
    public static final int HEIGTH_FRAME = 600;
    public static final String LOGO_PATH = "src/img/logoGame.png";
    public static final String TITLE = "Boyacense's Food Run";
    
    private PrincipalPanelMenu panelMenu;
    private PanelGameOver panelGameOver;
    private PanelScreenShotsMenu panelScreeShots;
    private PanelStorie panelStorie;

    public WindowMenu(ActionListener actionListener){
        setTitle(TITLE);
        setIconImage(new ImageIcon(LOGO_PATH).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH_FRAME,HEIGTH_FRAME));
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true); 
        setCursor(Utilities.cursor());
        panelMenu = new PrincipalPanelMenu(actionListener);
        add(panelMenu);
        panelGameOver = new PanelGameOver();
        panelScreeShots = new PanelScreenShotsMenu(actionListener);
        panelStorie = new PanelStorie();

        setVisible(true);
    }

    public void showGameOverFrame(){
        this.remove(panelMenu);
        add(panelGameOver);
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    public void changeSizeHero(){
        panelGameOver.changeSize();
    }

    public boolean moveHero(){
        return panelGameOver.moveHero();
    }

    public void addPanelMenu(){
        this.remove(panelGameOver);
        add(panelMenu);
        revalidate();
        repaint();
    }

    public void addPanelScreenShots(ActionListener listener, ArrayList<Image> screenList){
        this.remove(panelMenu);
        panelScreeShots.addImagesToPanel(listener,screenList);
        add(panelScreeShots);
        revalidate();
        repaint();
    }

    public void backToMenu(){
        this.remove(panelScreeShots);
        add(panelMenu);
        revalidate();
        repaint();
    }

    public Image getImage(int numberImage){
        return panelScreeShots.getImage(numberImage);
    }

    public void changeImage(boolean side){
        panelScreeShots.changeImage(side);
    }

    public int getPositionImage(){
        return panelScreeShots.getPositionImage();
    }

    public void showStorie(){
        if(!getProcess()){
            remove(panelMenu);
            add(panelStorie);
        }else{
            remove(panelStorie);
            add(panelMenu);
            panelStorie.resetStory();
        }
        revalidate();
        repaint();
    }

    public boolean getProcess(){
        return panelStorie.getProcess();
    }

    public void addStory(){
        revalidate();
        repaint();
        panelStorie.addStory();
    }
}