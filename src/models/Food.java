package models;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import views.CanvasGame;

public class Food{

    public static final int MIN_SPEED = 5;

    private int xPosition;
    private int yPosition;
    private int heightCanvas;
    private int canvasWidth;
    private char typeFood;
    private Image imageFood;
    private int pathImage;
    private boolean isCollision;
    private Random random;

    public Food(int canvasWidth, int heightCanvas, char typeFood, int pathImage ,Image imageFood){
        random = new Random();
        this.canvasWidth = canvasWidth;
        this.heightCanvas = heightCanvas;
        this.typeFood = typeFood;
        this.imageFood = imageFood;
        this.pathImage = (pathImage + 1);
        this.xPosition = random.nextInt(canvasWidth-CanvasGame.SIZE_FOOD);
        this.yPosition = CanvasGame.HEIGTH_HEART;
        isCollision = false;
    }

    public Food(int xPosition, int yPosition, char typeFood,Image imageFood, int heightCanvas){
        random = new Random();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.typeFood = typeFood;
        this.imageFood = imageFood;
        this.heightCanvas = heightCanvas;
    }

    public void move(){
        if((yPosition + CanvasGame.SIZE_FOOD) < (heightCanvas - CanvasGame.SIZE_FLOOR)){
            yPosition += random.nextInt(MIN_SPEED) + MIN_SPEED;
        }else{
            this.isCollision = true;
        }
    }

    public int getHeigthCanvas(){
        return this.heightCanvas;
    }

    public int getYposition(){
        return yPosition;
    }

    public int getXposition(){
        return xPosition;
    }

    public void setXPosition(){
        xPosition = random.nextInt(canvasWidth-CanvasGame.SIZE_FOOD);
    }
    
    public Image getImageFood(){
        return imageFood;
    }

    public char getTypeFood(){
        return typeFood;
    }

    public int getPathImage(){
        return pathImage;
    }

    public Rectangle getBounds(){
        return new Rectangle(xPosition,yPosition,CanvasGame.SIZE_FOOD,CanvasGame.SIZE_FOOD);
    }

    public boolean getState(){
        return this.isCollision;
    }
    
}