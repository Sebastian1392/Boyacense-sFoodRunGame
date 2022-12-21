package views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

import persistense.FileManager;

public class PanelStorie extends JPanel implements MouseListener {

    private static final long serialVersionUID = 1L;

    public static final String BACKGRAUND_PATH = "src/img/BG.png";
    public static final int LEFT_CLICKED = 1;
    public static final int CENTER_CLICKED = 2;
    public static final int RIGTH_CLICKED = 3;
    public static final int WIDTH = 484;
    public static final int HEIGTH = 561;

    private ArrayList<Image> storyList;
    private JLabel labelStory;
    private int position;
    private boolean endStory;

    public PanelStorie() {
        storyList = FileManager.readStories();
        setOpaque(false);
        addMouseListener(this);
        labelStory = new JLabel();
        add(labelStory);
    }

    public void addStory(){
        labelStory.setIcon(Utilities.getImage(storyList.get(position), WIDTH, HEIGTH));
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(FileManager.readImage(BACKGRAUND_PATH), 0, 0, this.getWidth(), this.getHeight(), this);
        super.paint(g);
    }

    private void backToImage(){
        position += (position > 0)? -1 : 0;
    }

    private void nextImage(){
        position += (position < (storyList.size()))? 1 : 0;
        if(position == (storyList.size())){
            endStory();
        }
    }

    public void endStory(){
        position = 0;
        endStory = true;
    }

    public boolean getProcess(){
        return endStory;
    }

    public void resetStory(){
        endStory = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case LEFT_CLICKED:
                backToImage();
            break;
            case CENTER_CLICKED:
                endStory();
            break;
            case RIGTH_CLICKED:
                nextImage();
            break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}