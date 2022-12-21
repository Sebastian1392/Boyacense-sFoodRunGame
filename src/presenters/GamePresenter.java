package presenters;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.Timer;
import models.GameManager;
import persistense.FileManager;
import persistense.SaveGameData;
import persistense.ScreenShot;
import persistense.SoundManager;
import views.DialogError;
import views.DialogImage;
import views.DialogMenuPause;
import views.WindowMain;
import views.WindowMenu;

public class GamePresenter implements KeyListener, ActionListener{

    public static final String PATH_SOUND_GAME = "src/sounds/musicGame.wav";
    public static final String PATH_SOUND_MENU = "src/sounds/musicMenu.wav";
    public static final String PATH_SOUND_GAME_OVER = "src/sounds/musicGameOver.wav";
    public static final String PATH_SOUND_STORY = "src/sounds/musicStory.wav";

    private WindowMenu windowMenu;
    private DialogMenuPause menuPause;
    private DialogImage dialogImage;
    private DialogError dialogError;
    private GameManager game;
    private WindowMain window;
    private ScreenShot screenShot;
    private SoundManager sound;
    private SaveGameData saveGame;
    private Timer gameLoop;
    
    public GamePresenter(){
        menuPause = new DialogMenuPause(this);
        dialogImage = new DialogImage(this);
        dialogError = new DialogError(this);
        windowMenu = new WindowMenu(this);
        addSound(PATH_SOUND_MENU);
    }

    private void addSound(String pathSound){
        sound = new SoundManager(pathSound);
        sound.playSound();
    }

    private void intanceObjects(Point component){
        game = new GameManager();
        window = new WindowMain(this);
        window.setLocation(component);
        window.setVisible(true);
        windowMenu.setVisible(false);
        screenShot = new ScreenShot();
        sound = new SoundManager(PATH_SOUND_GAME);
        saveGame = new SaveGameData(game,sound,screenShot);
    }

    private void startGame(Point component){
        sound.stopSound();
        intanceObjects(component);
        ubicateHero(true);
        gameLoop = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!game.isDead()){
                    window.addFood(game.getfoodList());
                    updateData();
                    takeScreen();
                }else{
                    stopGame();
                    showGameOver();
                }
            }
        });
        initThreads();
    }

    private void takeScreen(){
        boolean isScreen = screenShot.isScreenShot();
        if(isScreen){
            window.takeScreen(isScreen);
            screenShot.notIsScreen();
        }else{
            window.takeScreen(isScreen);
        }
    }

    private void initThreads(){
        window.startCanvas();
        game.start();
        gameLoop.start();
        initScreenShots();
        sound.startSound();
        saveGame.start();
    }

    private void initScreenShots(){
        screenShot.getScreenShot(window);
        screenShot.start();
    }

    private void ubicateHero(boolean isNewGame){
        game.setSizeCanvas(window.getWidthCanvas(), window.getHeigthCanvas());
        game.setPositionHero(isNewGame);
        updateData();
        updatePositionHero();
    }

    private void updateData(){
        window.updateData(game.getTotalLifes(),game.getPoints());
    }
    
    private void updatePositionHero(){
        window.updatePositionHero(game.getPositionXHero(), game.getPositionYHero(), game.getActionHero());
    }

    private void stopGame(){
        game.stop();
        window.stopCanvas();
        screenShot.stop();
        sound.stopSound();
        saveGame.stop();
        gameLoop.stop();
        window.setVisible(false);
    }

    private void showGameOver(){
        windowMenu.setLocation(window.getLocation());
        windowMenu.showGameOverFrame();
        gameLoop = new Timer(450, new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent e) {
                windowMenu.changeSizeHero();
                if(!windowMenu.moveHero()){
                    sound.stopSound();
                    windowMenu.addPanelMenu();
                    addSound(PATH_SOUND_MENU);
                    gameLoop.stop();
                }
			}
        });
        gameLoop.start();
        addSound(PATH_SOUND_GAME_OVER);
    }


    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(dialogImage.isVisible()){
            nextImage(e);
        }else{
            setActions(e);
        }
    }

    private void setActions(KeyEvent e){
        setPauseGame(e);
        moveHero(e);
    }

    private void nextImage(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            windowMenu.changeImage(true);
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            windowMenu.changeImage(false);
        }
        dialogImage.showImageToDialog(windowMenu.getImage(windowMenu.getPositionImage()));
    }

    private void setPauseGame(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_P){
            pauseGame();
        }
    }

    private void moveHero(KeyEvent e){
        if(!game.getPause() || game.isDead()){
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                game.moveHeroToRigth();
            }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                game.moveHeroToLeft();
            }
            updatePositionHero();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!dialogImage.isVisible()){
            game.resetCounterImages();
            updatePositionHero();
        }
    }

    private void pauseGame(){
        game.pauseOrResumeGame();
        screenShot.pauseScreenShots();
        sound.pauseSound();
        menuPause.setLocationRelativeTo(window);
        menuPause.setVisible((menuPause.isVisible())? false: true);
    }

    private void replayGame(){
        stopGame();
        menuPause.setVisible(false);
        window.setVisible(false);
        startGame(window.getLocation());
    }

    private void backToMenu(){
        stopGame();
        menuPause.setVisible(false);
        window.setVisible(false);
        windowMenu.setVisible(true);
        windowMenu.setLocation(window.getLocation());
        addSound(PATH_SOUND_MENU);
    }

    private void validateGame(Point component){
        GameManager gameSave = FileManager.uploadGameData();
        if(gameSave != null){
            uploadGame(component,gameSave);
        }else{
            dialogError.setLocationRelativeTo(windowMenu);
            dialogError.setVisible(true);
        }
    }
    
    private void uploadObjects(Point component,GameManager gameUploated){
        game = gameUploated;
        window = new WindowMain(this);
        window.setLocation(component);
        window.setVisible(true);
        windowMenu.setVisible(false);
        screenShot = FileManager.uploadScreensGame();;
        sound = FileManager.uploadSoundGame();
        saveGame = new SaveGameData(game,sound,screenShot);
    }

    private void uploadGame(Point component, GameManager gameUploated){
        sound.stopSound();
        uploadObjects(component,gameUploated);
        ubicateHero(false);
        gameLoop = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!game.isDead()){
                    window.addFood(game.getfoodList());
                    updateData();
                    takeScreen();
                }else{
                    stopGame();
                    showGameOver();
                }
            }
        });
        initThreads();
    }

    private void showStory(){
        sound.stopSound();
        addSound(PATH_SOUND_STORY);
        windowMenu.showStorie();
        gameLoop = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!windowMenu.getProcess()){
                    windowMenu.addStory();
                }else{
                    gameLoop.stop();
                    windowMenu.showStorie();
                    sound.stopSound();
                    startGame(windowMenu.getLocation());
                }
            }
        });
        gameLoop.start();
    }

    private int getNameImage(ActionEvent e) {
        return Integer.parseInt(((JButton)e.getSource()).getName());
    }

    private void getImage(ActionEvent e){
        dialogImage.setLocationRelativeTo(windowMenu);
        dialogImage.showImageToDialog(windowMenu.getImage(getNameImage(e)));
        dialogImage.setVisible(true);
    }

    private void showScreenShots(){
        windowMenu.addPanelScreenShots(this,FileManager.readScreenShots());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Commands.valueOf(e.getActionCommand())) {
            case PLAY_GAME:
                showStory();
            break;
            case SCREENSHOTS:
                showScreenShots();
            break;
            case UPLOAD_GAME:
                validateGame(windowMenu.getLocation());
            break;
            case GET_OUT:
                System.exit(0);
            break;
            case PAUSE:
                pauseGame();
            break;
            case REPLAY:
                replayGame();
            break;
            case BACK_TO_MENU:
                backToMenu();
            break;
            case BACK_MENU:
                windowMenu.backToMenu();
            break;
            case SHOW_IMAGE:
                getImage(e);
            break;
            case ACEPT:
                dialogError.setVisible(false);
            break;
        }
    }

}