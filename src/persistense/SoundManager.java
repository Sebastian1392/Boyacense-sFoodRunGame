package persistense;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import models.MyThread;

public class SoundManager extends MyThread{

    public static final long SPEED = 1000;

    private boolean isPuause;
    private Clip sound;
    private long positionSound;
    private String pathSound;

    public SoundManager(String path){
        super(SPEED);
        sound = getSound(path);
        pathSound = path;
    }

    public SoundManager(String path, long positionSound){
        super(SPEED);
        sound = getSound(path);
        this.positionSound = positionSound;
        pathSound = path;
    }

    public void pauseSound(){
        if(!isPuause){
            this.pause();
            isPuause = true;
            sound.stop();
        }else{
            this.resume();
            isPuause = false;
            sound.setMicrosecondPosition(positionSound);
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    public void playSound(){
        sound.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void startSound(){
        this.start();
        sound.setMicrosecondPosition(positionSound);
        sound.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void resumeSound(){
        sound.setMicrosecondPosition(positionSound);
        sound.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopSound(){
        sound.stop();
        this.stop();
    }

    public Clip getSound(String path){
        File file = new File(path);
        try {   
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = (Clip) AudioSystem.getClip();
            clip.open(inputStream);
            return clip;
        } catch (Exception e) {
            return null;
        }
    }

    public String getPathSound(){
        return this.pathSound;
    }

    public long getPositionSound() {
        return this.positionSound;
    }

    @Override
    protected void executeTask() {
        positionSound = sound.getMicrosecondPosition();
    }
}