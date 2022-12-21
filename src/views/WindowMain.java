package views;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.util.concurrent.ConcurrentMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import models.Food;

public class WindowMain extends JFrame{

    private static final long serialVersionUID = 1L;
    public static final int WIDTH_FRAME = 500;
    public static final int HEIGTH_FRAME = 600;
    public static final String LOGO_PATH = "src/img/logoGame.png";
    public static final String TITLE = "Boyacense's Food Run";

    private CanvasGame canvas;

    public WindowMain(KeyListener listener){
        setTitle(TITLE);
        setIconImage(new ImageIcon(LOGO_PATH).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH_FRAME,HEIGTH_FRAME));
        setResizable(false);
        setAlwaysOnTop(true); 
        setCursor(Utilities.cursor());
        
        canvas = new CanvasGame(listener);
        add(canvas);

    }

    public void startCanvas(){
        canvas.startCanvas();
    }

    public void updateData( int totalLifes, int points){
        canvas.updateData(totalLifes, points);
    }

    public void updatePositionHero(int x, int y, Image action){
        canvas.updatePositionHero(x, y, action);
    }

    public int getWidthCanvas(){
        return canvas.getWidth();
    }

    public int getHeigthCanvas(){
        return canvas.getHeight();
    }

    public void addFood(ConcurrentMap<Long,Food> enemies){
        canvas.addFood(enemies);
    }

    public CanvasGame getCanvasGame(){
        return this.canvas;
    }

    public void stopCanvas(){
        canvas.stopCanvas();
    }

    public void takeScreen(boolean isScreen){
        canvas.takeScreen(isScreen);
    }
}