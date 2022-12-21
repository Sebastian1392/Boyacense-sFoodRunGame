package persistense;

import java.awt.Image;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import models.Food;
import models.GameManager;

public class FileManager {

    public static final String PATH_FILE_DATA = "resources/gamedata/";
    public static final String PATH_DATA_GAME = "resources/gamedata/Game.json";
    public static final String PATH_DATA_SOUND = "resources/gamedata/Sound.json";
    public static final String PATH_DATA_SCREENS = "resources/gamedata/ScreenS.json";
    public static final String PATH_ACTIONS = "src/img/actions/";
    public static final String PATH_FOOD = "src/img/food/";
    public static final String PATH_STORIES = "src/img/stories/story ";
    public static final String SCREEN = "/screen ";
    public static final String[] TAGS_GAME = { "side", "points", "lifes", "positionX", "positionY", "key",
            "counterImages", "foodList" };
    public static final String[] TAGS_FOOD = { "keyFood", "positionXFood", "positionYFood", "typeFood", "imagePath",
            "heigthCanvas" };
    public static final String[] TAGS_SOUND = { "pathSound", "positionSound" };
    public static final String[] TAGS_SCREENS = { "numberScreen", "fileName" };
    public static final String PNG_EXTENTION = ".png";
    public static final String PATH_DEFAULD = PATH_FOOD + "1" + PNG_EXTENTION;

    public static ArrayList<Image> readActions(int initialValue, int finalValue) {
        ArrayList<Image> actions = new ArrayList<Image>();
        for (int i = initialValue; i <= finalValue; i++) {
            actions.add(readImage(PATH_ACTIONS + i + PNG_EXTENTION));
        }
        return actions;
    }

    public static ArrayList<Image> readFood(int initialValue, int finalValue) {
        ArrayList<Image> actions = new ArrayList<Image>();
        for (int i = initialValue; i <= finalValue; i++) {
            actions.add(readImage(PATH_FOOD + i + PNG_EXTENTION));
        }
        return actions;
    }

    public static ArrayList<Image> readStories(){
        ArrayList<Image> stories = new ArrayList<Image>();
        long i = 1;
        Image image;
        while((image = readImage(PATH_STORIES + i + PNG_EXTENTION,i)) != null){
            stories.add(image);
            i++;
        }
        return stories;
    }

    public static ArrayList<Image> readScreenShots() {
        ArrayList<Image> screenShots = new ArrayList<Image>();
        ScreenShot screenShot = uploadScreensGame();
        if(screenShot != null){
            long i = 1;
            while(i <= screenShot.getNumberScreenShot()){
                Image image = readImage(screenShot.getFileName() + SCREEN + i + PNG_EXTENTION,i);
                if(image != null){
                    screenShots.add(image);
                }
                i++;
            }
        }
        return screenShots;
    }

    public static String getPathImage(int nameImage) {
        return PATH_FOOD + nameImage + PNG_EXTENTION;
    }

    public static Image readImage(String path, long nameScreen) {
        Image image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
        }
        return image;
    }

    public static Image readImage(String path) {
        Image image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            readImage(PATH_DEFAULD);
        }
        return image;
    }

    public static void saveGameData(GameManager game) {
        JsonObject jsonFile = new JsonObject();
        jsonFile.put(TAGS_GAME[0], Character.toString(game.getSide()));
        jsonFile.put(TAGS_GAME[1], game.getPoints());
        jsonFile.put(TAGS_GAME[2], game.getTotalLifes());
        jsonFile.put(TAGS_GAME[3], game.getPositionXHero());
        jsonFile.put(TAGS_GAME[4], game.getPositionYHero());
        jsonFile.put(TAGS_GAME[5], game.getKey());
        jsonFile.put(TAGS_GAME[6], game.getCounterImages());
        JsonArray foodList = new JsonArray();
        for (Long key : game.getfoodList().keySet()) {
            Food food = game.getfoodList().get(key);
            if (food != null) {
                JsonObject foodData = new JsonObject();
                foodData.put(TAGS_FOOD[0], key);
                foodData.put(TAGS_FOOD[1], food.getXposition());
                foodData.put(TAGS_FOOD[2], food.getYposition());
                foodData.put(TAGS_FOOD[3], Character.toString(food.getTypeFood()));
                foodData.put(TAGS_FOOD[4], getPathImage(food.getPathImage()));
                foodData.put(TAGS_FOOD[5], food.getHeigthCanvas());
                foodList.add(foodData);
            }
        }
        jsonFile.put(TAGS_GAME[7], foodList);
        try (FileWriter file = new FileWriter(PATH_DATA_GAME, false)) {
            file.write(jsonFile.toJson());
        } catch (IOException e) {
            new File(PATH_FILE_DATA).mkdir();
            saveGameData(game);
        }
    }

    public static GameManager uploadGameData() {
        GameManager game = null;
        try {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(new FileReader((PATH_DATA_GAME)));
            char side = jsonObject.getString(TAGS_GAME[0]).charAt(0);
            int points = jsonObject.getInteger(TAGS_GAME[1]);
            int lifes = jsonObject.getInteger(TAGS_GAME[2]);
            int positionXHero = jsonObject.getInteger(TAGS_GAME[3]);
            int positionYHero = jsonObject.getInteger(TAGS_GAME[4]);
            int key = jsonObject.getInteger(TAGS_GAME[5]);
            int counterImages = jsonObject.getInteger(TAGS_GAME[6]);
            ConcurrentHashMap<Long, Food> foodList = new ConcurrentHashMap<Long, Food>();
            JsonArray foodListJson = (JsonArray)jsonObject.get(TAGS_GAME[7]);
            for (Object object : foodListJson) {
                JsonObject food = (JsonObject) object;
                long keyFood = food.getLong(TAGS_FOOD[0]);
                int xPositionFood = food.getInteger(TAGS_FOOD[1]);
                int YPositionFood = food.getInteger(TAGS_FOOD[2]);
                char typeFood = food.getString(TAGS_FOOD[3]).charAt(0);
                Image imageFood = readImage(food.getString(TAGS_FOOD[4]));
                int heigthCanvas = food.getInteger(TAGS_FOOD[5]);
                foodList.put(keyFood, new Food(xPositionFood,  YPositionFood,  typeFood, imageFood,heigthCanvas));
            }
            game = new GameManager(side, points, lifes, positionXHero, positionYHero, key, counterImages, foodList);
        } catch (DeserializationException | IOException e) {
            return null;
        }
        return game;
    }

    public static void saveSoundGame(SoundManager sound){
        JsonObject soundFile = new JsonObject();
        soundFile.put(TAGS_SOUND[0], sound.getPathSound());
        soundFile.put(TAGS_SOUND[1], sound.getPositionSound());
        try (FileWriter file = new FileWriter(PATH_DATA_SOUND, false)) {
            file.write(soundFile.toJson());
        } catch (IOException e) {
            new File(PATH_FILE_DATA).mkdir();
            saveSoundGame(sound);
        }
    }

    public static SoundManager uploadSoundGame(){
        SoundManager sound = null;
        try {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(new FileReader((PATH_DATA_SOUND)));
            sound = new SoundManager(jsonObject.getString(TAGS_SOUND[0]), jsonObject.getLong(TAGS_SOUND[1]));
        } catch (DeserializationException | IOException e) {
            return null;
        }
        return sound;
    }

    public static void saveScreensGame(ScreenShot screen){
        JsonObject screenFile = new JsonObject();
        screenFile.put(TAGS_SCREENS[0], screen.getNumberScreenShot());
        screenFile.put(TAGS_SCREENS[1], screen.getFileName());
        try (FileWriter file = new FileWriter(PATH_DATA_SCREENS, false)) {
            file.write(screenFile.toJson());
        } catch (IOException e) {
            new File(PATH_FILE_DATA).mkdir();
            saveScreensGame(screen);
        }
    }

    public static ScreenShot uploadScreensGame(){
        ScreenShot screenShot = null;
        try {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(new FileReader((PATH_DATA_SCREENS)));
            screenShot = new ScreenShot(jsonObject.getLong(TAGS_SCREENS[0]), jsonObject.getString(TAGS_SCREENS[1]));
        } catch (DeserializationException | IOException e) {
           return null;
        }
        return screenShot;
    }
}