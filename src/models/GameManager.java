package models;

import java.awt.Image;
import java.awt.Rectangle;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import persistense.FileManager;
import views.CanvasGame;

public class GameManager extends MyThread {
    
    public static final int SPEED = 110;
    public static final char RIGTH = 'R';
    public static final char LEFT = 'L';
    public static final char BOYACENSE = 'B';
    public static final char[] TYPE_FOOD = {BOYACENSE,'O'};
    public static final int SPEED_HERO = 7;
    public static final int LIMIT_RANDOM = 2;
    public static final int TOTAL_LIFES = 5;
    public static final int SECONDS_PER_FOOD = 1;
    public static final int[] LIMITS_ACTIONS = { 1, 11, 12, 22, 4};
    
    private long key;
    private int positionXHero;
    private int positionYHero;
    private int widthCanvas;
    private int heigthCanvas;
    private int counterImages;
    private char side;
    private boolean isPause;
    private ArrayList<Image> actionsRighHero;
    private ArrayList<Image> actionsLeftHero;
    private ArrayList<Image> food;
    private ConcurrentMap<Long, Food> foodList;
    private int actualSeconds;
    private int newSeconds;
    private Random random;
    private int totalLifes;
    private int points;

    public GameManager() {
        super(SPEED);
        getImages();
        random = new Random();
        side = RIGTH;
        totalLifes = TOTAL_LIFES;
        foodList = new ConcurrentHashMap<Long, Food>();
    }

    public GameManager(char side,int points, int totalLifes, int positionXHero, int positionYHero, int key, 
    int counterImages, ConcurrentHashMap<Long, Food> foodList) {
        super(SPEED);
        getImages();
        random = new Random();
        this.side = side;
        this.points = points;
        this.totalLifes = totalLifes;
        this.positionXHero = positionXHero;
        this.positionYHero = positionYHero;
        this.key = key;
        this.counterImages = counterImages;
        this.foodList = foodList;
    }

    private void getImages(){
        actionsRighHero = FileManager.readActions(LIMITS_ACTIONS[2], LIMITS_ACTIONS[3]);
        actionsLeftHero = FileManager.readActions(LIMITS_ACTIONS[0], LIMITS_ACTIONS[1]);
        food = FileManager.readFood(LIMITS_ACTIONS[0], LIMITS_ACTIONS[4]);
    }

    public void setSizeCanvas(int width, int heigth) {
        this.widthCanvas = width;
        this.heigthCanvas = heigth;
        
    }

    public void setPositionHero(boolean isNewGame){
        if(isNewGame){
            this.positionXHero = ((widthCanvas - CanvasGame.HERO_WIDHT) / 2);
            this.positionYHero = heigthCanvas;
        }
    }

    public int getPositionXHero() {
        return this.positionXHero;
    }

    public int getPositionYHero() {
        return this.positionYHero;
    }

    public int getTotalLifes(){
        return this.totalLifes;
    }

    public int getPoints(){
        return points;
    }

    public long getKey(){
        return key;
    }

    public int getCounterImages(){
        return counterImages;
    }

    public void moveHeroToRigth() {
        side = RIGTH;
        valueMove(actionsRighHero.size());
        if (positionXHero < (widthCanvas - CanvasGame.HERO_WIDHT)) {
            positionXHero += SPEED_HERO;
        }
    }

    public void moveHeroToLeft() {
        side = LEFT;
        valueMove(actionsLeftHero.size());
        if (positionXHero >= 0) {
            positionXHero -= SPEED_HERO;
        }
    }

    private void valueMove(int sizeList) {
        if (counterImages == (sizeList - 1)) {
            counterImages = 1;
        } else {
            counterImages++;
        }
    }

    public void resetCounterImages() {
        counterImages = 0;
    }

    public Image getActionHero() {
        return (side == RIGTH) ? actionsRighHero.get(counterImages) : actionsLeftHero.get(counterImages);
    }

    public char getSide(){
        return side;
    }

    public ConcurrentMap<Long,Food> getfoodList() {
        return foodList;
    }

    private void movefood() {
        for (Iterator<Map.Entry<Long,Food>> iterator = foodList.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<Long, Food> entry = iterator.next();
            Food food = entry.getValue();
            food.move();
        }
    }

    private void sendFood() {
        LocalDateTime locaDate = LocalDateTime.now();
        newSeconds = locaDate.getSecond();
        if((newSeconds - actualSeconds) >= SECONDS_PER_FOOD){
            actualSeconds = newSeconds;
            foodList.put(key++, createFood());
        }else if(newSeconds < actualSeconds){
            actualSeconds = 0;
        }
    }

    private Food createFood(){
        char typeFood = (TYPE_FOOD[random.nextInt(TYPE_FOOD.length)]);
        int positionFoodImage = selectImage(typeFood);
        Image imageFood = food.get(positionFoodImage);
        return new Food(this.widthCanvas, this.heigthCanvas, typeFood, positionFoodImage, imageFood);
    }

    private int selectImage(char typeFood){
        int positionImage = -1;
        switch (typeFood) {
            case BOYACENSE:
                positionImage = random.nextInt(LIMIT_RANDOM);
            break;
            default:
                positionImage = random.nextInt(LIMIT_RANDOM) + LIMIT_RANDOM;
            break;
        }
        return positionImage;
    }

    public void valueCollision() {
        Rectangle bullet = new Rectangle(positionXHero, positionYHero - CanvasGame.FLOOR_PLUS_HEIGTH,
        CanvasGame.HERO_WIDHT, CanvasGame.HERO_HEIGTH);
        for (Iterator<Map.Entry<Long, Food>> iterator = foodList.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<Long, Food> entry = iterator.next();
            Food food = entry.getValue();
            if (bullet.intersects(food.getBounds())) {
                updatePoints(food.getTypeFood());
                iterator.remove();
            }
        }
    }

    private void updatePoints(char typeFood){
       if(typeFood == BOYACENSE){
           points++;
       }else if(typeFood == TYPE_FOOD[1]){
           points-= 2;
       }
    }

    private void updateLifes(Food food){
        if(food.getTypeFood() == BOYACENSE && totalLifes > 0){
            totalLifes--;
        }
    }

    private void verifyCollisionFloor(){
        for (Iterator<Map.Entry<Long,Food>> iterator = foodList.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<Long, Food> entry = iterator.next();
            Food food = entry.getValue();
            if(food.getState()){
                updateLifes(food);
                iterator.remove();
            }
        }
    }

    public void pauseOrResumeGame(){
        if(!isPause){
            isPause = true;
            this.pause();
        }else{
            isPause = false;
            this.resume();
        }
    }

    public boolean getPause(){
        return isPause;
    }

    public boolean isDead(){
        return totalLifes == 0;
    }

    @Override
    protected void executeTask() {
        sendFood();
        movefood();
        valueCollision();
        verifyCollisionFloor();
    }


}