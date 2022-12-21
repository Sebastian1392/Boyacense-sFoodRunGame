package persistense;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

import models.MyThread;
import views.WindowMain;

public class ScreenShot extends MyThread {

    public static final String PATH_SCREEN_SHOTS = "resources/screenshots/";
    public static final String HYPHEN = "-";
    public static final String POINT = ".";
    public static final String SCREEN_TEXT = "/screen ";
    public static final String PNG_EXTENTION = ".png";
    public static final int SPEED = 7000;
    public static final int CUT_X = 7;
    public static final int CUT_Y = 30;

    private long numberScreenShot;
    private boolean isPause;
    private boolean takeScreen;
    private boolean isStart;
    private WindowMain frame;
    private String routeFile;

    public ScreenShot() {
        super(SPEED);
        File file = new File(PATH_SCREEN_SHOTS + createNameDirectory());
        if(!file.exists()){
            new File(PATH_SCREEN_SHOTS).mkdir();
            file.mkdir();
        }
        routeFile = file.getPath();
        isStart = true;
    }
    
    public ScreenShot(long numberScreenShot, String filePath) {
        super(SPEED);
        this.numberScreenShot = (numberScreenShot - 1);
        File file = new File(filePath);
        if(!file.exists()){
            new File(PATH_SCREEN_SHOTS).mkdir();
            file.mkdir();
            this.numberScreenShot = 0;
        }
        routeFile = file.getPath();
        isStart = true;
    }

    private String createNameDirectory(){
        return (LocalDateTime.now().getDayOfMonth() + HYPHEN + LocalDateTime.now().getMonthValue() + HYPHEN + 
        LocalDateTime.now().getYear() + HYPHEN + LocalDateTime.now().getHour() + POINT + LocalDateTime.now().getMinute() 
        + POINT + LocalDateTime.now().getSecond() + POINT);
    }

    public String getFileName(){
        return routeFile;
    }

    public long getNumberScreenShot(){
        return this.numberScreenShot;
    } 

    public void getScreenShot(WindowMain frame) {
        this.frame = frame;
    }

    public void pauseScreenShots(){
        if(!isPause){
            isPause = true;
            this.pause();
        }else{
            isPause = false;
            this.resume();
        }
    }

    public boolean isScreenShot(){
        return takeScreen;
    }

    public void notIsScreen(){
        takeScreen = false;
    }

    public static void captureScreen(String fileName, WindowMain frame){ 
        try {
            Point point = new Point(frame.getX() + CUT_X,frame.getY() + CUT_Y);
            Dimension screenSize = new Dimension(frame.getWidthCanvas(),frame.getHeigthCanvas());
            Rectangle screenRectangle = new Rectangle(point,screenSize); 
            Robot robot = new Robot(); 
            BufferedImage image = robot.createScreenCapture(screenRectangle); 
            ImageIO.write(image, PNG_EXTENTION.substring(1), new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeTask() {
        if(!isStart){
            captureScreen(routeFile + SCREEN_TEXT +numberScreenShot + PNG_EXTENTION,frame);
            takeScreen = true;
        }else{
            isStart = false;
        }
        numberScreenShot++;
    }
}