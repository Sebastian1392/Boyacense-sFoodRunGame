package persistense;

import models.GameManager;
import models.MyThread;

public class SaveGameData extends MyThread{

    public static final int SPEED = 100;

    private GameManager game;
    private SoundManager sound;
    private ScreenShot screenShot;

    public SaveGameData(GameManager game, SoundManager sound, ScreenShot screenShot){
        super(SPEED);
        this.game = game;
        this.sound = sound;
        this.screenShot = screenShot;
    }

    @Override
    protected void executeTask() {
        FileManager.saveGameData(game);
        FileManager.saveSoundGame(sound);
        FileManager.saveScreensGame(screenShot);
    }
    
}