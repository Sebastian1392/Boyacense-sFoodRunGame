package views;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import models.Food;
import persistense.FileManager;

public class CanvasGame extends Canvas {

    private static final long serialVersionUID = 1L;

    public static final int HERO_HEIGTH = 110;
    public static final int HERO_WIDHT = 80;
    public static final int SIZE_FLOOR = 40;
    public static final int SIZE_FOOD = 50;
    public static final int HEIGTH_HEART = 25;
    public static final int WIDHT_HEART = 30;
    public static final int Y_POSITION_LIFES = 7;
    public static final int Y_POSITION_POINTS = 25;
    public static final int WIDHT_CAMERA = 40;
    public static final int HEIGTH_CAMERA = 30;
    public static final int SPACE_X = 5;
    public static final int FLOOR_PLUS_HEIGTH = (HERO_HEIGTH + SIZE_FLOOR);
    public static final Font FONT = new Font("Cooper Black", Font.PLAIN, 25);
    public static final int FPS = 60;
    public static final long FPMS = TimeUnit.SECONDS.toMillis(1) / FPS;
    public static final String POINTS_TEXT = "Puntos: ";
    public static final String BACKGRAUND_PATH = "src/img/BG.png";
    public static final String FLOOR_PATH = "src/img/floor.png";
    public static final String LIFE_PATH = "src/img/life.png";
    public static final String CAMERA_PATH = "src/img/camera.png";

    private Timer gameLoop;
    private BufferStrategy bufferStrategy;
    private Image backGraund;
    private Image floor;
    private Image actionHero;
    private Image life;
    private Image camera;
    private boolean isScreen;
    private int positionXHero;
    private int positionYHero;
    private int counterLifes;
    private int points;
    private ConcurrentMap<Long, Food> foodList;

    public CanvasGame(KeyListener listener) {
        addKeyListener(listener);
        setFocusable(true);
        backGraund = FileManager.readImage(BACKGRAUND_PATH);
        floor = FileManager.readImage(FLOOR_PATH);
        life = FileManager.readImage(LIFE_PATH);
        camera = FileManager.readImage(CAMERA_PATH);
        foodList = new ConcurrentHashMap<Long, Food>();
        gameLoop = new Timer((int) FPMS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateImage();
            }
        });
    }

    public void startCanvas() {
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();
        setIgnoreRepaint(true);
        gameLoop.start();
    }

    public void stopCanvas(){
        gameLoop.stop();
    }

    public void updatePositionHero(int x, int y, Image action) {
        this.positionXHero = x;
        this.positionYHero = y;
        this.actionHero = action;
    }
    
    public void updateData( int totalLifes, int points){
        this.counterLifes = totalLifes;
        this.points = points;

    }

    public void drawFloor(Graphics2D backgroundGraphics) {
        for (int i = 0; i < this.getWidth(); i += SIZE_FLOOR) {
            backgroundGraphics.drawImage(floor, i, (getHeight() - SIZE_FLOOR), SIZE_FLOOR, SIZE_FLOOR, this);
        }
    }

    public void drawLifes(Graphics2D backgroundGraphics) {
        for (int i = (WIDHT_HEART*counterLifes); i > 0; i -= WIDHT_HEART) {
            backgroundGraphics.drawImage(life, (this.getWidth()-SPACE_X)-i, Y_POSITION_LIFES, WIDHT_HEART, HEIGTH_HEART, this);
        }
    }

    public void addFood(ConcurrentMap<Long, Food> foodList) {
        this.foodList = foodList;
    }

    public void drawFood(Graphics2D backgroundGraphics) {
        backgroundGraphics.setColor(Color.BLACK);
        for (Long key : foodList.keySet()) {
            Food food = foodList.get(key);
            if(food != null){
                backgroundGraphics.drawImage(food.getImageFood(), food.getXposition(), food.getYposition(), SIZE_FOOD, SIZE_FOOD, this);
            }
        }
    }

    private void drawPoints(Graphics2D backgroundGraphics){
        backgroundGraphics.setColor(Color.BLACK);
        backgroundGraphics.setFont(FONT);
        backgroundGraphics.drawString(POINTS_TEXT + points,SPACE_X,Y_POSITION_POINTS);
    }

    public void takeScreen(boolean isScreen){
        this.isScreen = isScreen;
    }

    private void drawCamera(Graphics2D backgroundGraphics){
        if(this.isScreen){
            backgroundGraphics.drawImage(camera, (this.getWidth() - WIDHT_CAMERA) / 2, 1, WIDHT_CAMERA,HEIGTH_CAMERA,this);
        }
    }

    private void updateImage() {
        Graphics2D backgroundGraphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        backgroundGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        backgroundGraphics.setRenderingHint( RenderingHints.  KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
        backgroundGraphics.drawImage(backGraund, 0, 0,this);
        drawFloor(backgroundGraphics);
        try {
            drawPoints(backgroundGraphics);
            drawLifes(backgroundGraphics);
            backgroundGraphics.drawImage(actionHero, positionXHero, (positionYHero - FLOOR_PLUS_HEIGTH), 
            HERO_WIDHT, HERO_HEIGTH, this);
            drawFood(backgroundGraphics);
            drawCamera(backgroundGraphics);
        }finally {
            backgroundGraphics.dispose();
        }
        bufferStrategy.show();
    }

}