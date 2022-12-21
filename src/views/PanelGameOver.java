package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

import persistense.FileManager;

public class PanelGameOver extends JPanel {

    private static final long serialVersionUID = 1L;

    public static final String PATH_GAME_OVER = "src/img/gameover.png";
    public static final String PATH_HERO_NEUTRAL_POSITION = "src/img/actions/12.png";
    public static final int HERO_HEIGTH = 140;
    public static final int HERO_WIDHT = 110;
    public static final int TEXT_HEIGTH = 160;
    public static final int TEXT_WIDHT = 310;


    private ArrayList<Image> actionsList;
    private int positionList;
    private int height;
    private int width;

    public PanelGameOver() {
        setOpaque(false);
        actionsList = FileManager.readActions(23, 32);
        actionsList.add(0, FileManager.readImage(PATH_HERO_NEUTRAL_POSITION));
        height = HERO_HEIGTH;
        width = HERO_WIDHT;
        setVisible(true);
    }

    public void changeSize(){
        if(positionList == 0){
            height += 20;
            width += 20; 
        }
    }

    public boolean moveHero(){
        boolean isValid = false;
        if(positionList < actionsList.size() - 1){
            positionList ++;
            isValid = true;
        }
        resetAnimation(isValid);
        repaint();
        return isValid;
    }

    private void resetAnimation(boolean isValid){
        if(!isValid){
            positionList = 0;
            height = HERO_HEIGTH;
            width = HERO_WIDHT;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.drawImage(FileManager.readImage(PATH_GAME_OVER), ((this.getWidth() - TEXT_WIDHT)/ 2), 50, TEXT_WIDHT ,TEXT_HEIGTH, this);
        g2.drawImage(actionsList.get(positionList), ((this.getWidth() - HERO_WIDHT)/ 2 ), TEXT_HEIGTH + 160, width, height, this);
    }
    
}