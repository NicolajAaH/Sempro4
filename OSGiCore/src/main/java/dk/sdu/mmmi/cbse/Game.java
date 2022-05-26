package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.filehandler.OSGiFileHandle;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private OrthographicCamera camera;
    private final GameData gameData = new GameData(); // GameData object containing all the global variables of the game
    private static final World world = new World();


    //Lists of services
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static final List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private OrthogonalTiledMapRenderer mapRenderer;
    private SpriteBatch batch;
    private static HashMap<Types, Texture> textures = new HashMap<>();
    private IMap map;

    // Screen size
    private final int MAP_SIZE = 58 * 12; //tile size in pixels * number of tiles
    private final int SCREEN_BAR_WIDTH = 300;
    private final int SCREEN_WIDTH = MAP_SIZE + SCREEN_BAR_WIDTH;
    private final int MAP_HEIGHT = MAP_SIZE;


    //FONTS
    private BitmapFont scoreFont;
    private BitmapFont moneyFont;
    private BitmapFont lifeFont;
    private BitmapFont feedbackToPlayerFont;
    private BitmapFont howToPlayFont;
    private BitmapFont gameOverFont;


    public Game() {
        init();
    }

    public void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "TowerDefense";
        cfg.width = SCREEN_WIDTH;
        cfg.height = MAP_HEIGHT;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }


    @Override
    public void create() {
        // setting initial values of games atitributes
        gameData.setLife(5);
        gameData.setMoney(500);
        gameData.setWave(0);
        gameData.setScore(0);
        // Set the screen width and height (global variables)
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        // Set the start time of the game to the current time
        gameData.setGameStartTime(System.currentTimeMillis());

        // Create the map
        map.setTiledMap(new TmxMapLoader().load("Map.tmx"));
        mapRenderer = new OrthogonalTiledMapRenderer(map.getTiledMap());

        // Creating 100 attacks
        for (int x = 0; x < 100; x++) {
            gameData.addAttack(new Attack(x * 7000, x));
            gameData.setWave(gameData.getWave() + 1);
        }

        batch = new SpriteBatch();

        //Fonts
        createFonts();

        camera = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        camera.translate(gameData.getDisplayWidth() / 2f, gameData.getDisplayHeight() / 2f);
        camera.update();

        // Input processor
        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        // adding sprites to textures
        textures.put(Types.PLAYER, new Texture(new OSGiFileHandle("/images/Sprites/player_nogun.png")));
        textures.put(Types.TOWER, new Texture(new OSGiFileHandle("/images/Sprites/cannon3.png")));
        textures.put(Types.ENEMY, new Texture(new OSGiFileHandle("/images/Sprites/monster.png")));
        textures.put(Types.PROJECTILE, new Texture(new OSGiFileHandle("/images/Sprites/projectile.png")));
        world.setTextureHashMap(textures);
    }

    private void createFonts() {
        float fontSize = 0.1f;

        //HighScore
        scoreFont = new BitmapFont(); //create
        scoreFont.setColor(Color.CYAN); //color
        scoreFont.scale(fontSize); //resize

        //Money
        moneyFont = new BitmapFont();
        moneyFont.setColor(Color.MAGENTA);
        moneyFont.scale(fontSize);

        //Life
        lifeFont = new BitmapFont();
        lifeFont.setColor(Color.GREEN);
        lifeFont.scale(fontSize);

        //Messages
        feedbackToPlayerFont = new BitmapFont();
        feedbackToPlayerFont.setColor(Color.ORANGE);
        feedbackToPlayerFont.scale(fontSize);

        //How to play
        howToPlayFont = new BitmapFont();
        howToPlayFont.setColor(Color.WHITE);
        howToPlayFont.scale(fontSize);

        //Game Over
        gameOverFont = new BitmapFont();
        gameOverFont.setColor(Color.RED);
        gameOverFont.scale(fontSize*2);
    }

    private void drawFonts() {
        //Font positions
        int fontSpacing = 25;
        int x = SCREEN_WIDTH - SCREEN_BAR_WIDTH + 10;
        int highestScoreY = MAP_HEIGHT - fontSpacing;
        int scoreY = MAP_HEIGHT - 2 * fontSpacing;
        int lifeY = MAP_HEIGHT - 3 * fontSpacing;
        int moneyY = MAP_HEIGHT - 4 * fontSpacing;
        int howToPlayY = MAP_HEIGHT - 24 * fontSpacing;
        int messageY = MAP_HEIGHT - 15 * fontSpacing;
        int deadPlayerY = MAP_HEIGHT - 10 * fontSpacing;

        // Info about score, life and money
        scoreFont.draw(batch, ("Score: " + gameData.getScore()), x, scoreY);
        lifeFont.draw(batch, ("Life: " + gameData.getLife()), x, lifeY);
        moneyFont.draw(batch, ("Money: " + gameData.getMoney()), x, moneyY);

        // Highest score
        if (gameData.getScore() > gameData.getHighestScore()) {
            gameData.setHighestScore(gameData.getScore());
        }

        scoreFont. draw(batch, ("Highest Score: " + gameData.getHighestScore()), x, highestScoreY);

        //How to play message
        String howToPlay = "HOW TO PLAY: " +
                "\n- Use the ARROWS to move the Player" +
                "\n- Press SPACE to place a Tower" +
                "\n- Don't run into the Monsters";
        howToPlayFont.drawMultiLine(batch, howToPlay, x, howToPlayY);

        // Message when player is dead from collision
        if (gameData.isPlayerDead()) {
            String message = "The Player is dead! " +
                    "\n\nYou can no longer place " +
                    "\nnew Towers";

            gameData.setScreenMessage(message);
        }

        // Messages for feedback to player during game
        if (!gameData.getScreenMessage().isEmpty()) {
            feedbackToPlayerFont.drawMultiLine(batch, gameData.getScreenMessage(), x, messageY);
        }

        // GAME OVER message when no more life
        if (gameData.getLife() <= 0) {
            feedbackToPlayerFont.dispose(); // Remove messages to player
            for (Attack attack : gameData.getCurrentAttacks())
                gameData.removeAttack(attack);
            for(Entity entity : world.getEntities())
                world.removeEntity(entity);

            String gameOver = "GAME OVER" +
                    "\n\nYou have no more life" + "\n\nPress ENTER to restart";
            gameOverFont.drawMultiLine(batch, gameOver, x, messageY);
        }
    }

    @Override
    public void render() {
        // Set screen color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        // Starting batch and drawing fonts
        batch.begin();
        drawFonts();
        batch.end();

        update();
    }

    private void update() {
        boolean restart = false;

        if (gameData.getLife() <= 0) {
            // Stop all entities
            for (IGamePluginService iGamePluginService : gamePluginList) {
                iGamePluginService.stop(gameData, world);
            }
            // Set restart if enter is pressed
            if(gameData.getKeys().isDown(GameKeys.ENTER)){
                restart = true;
            }
            mapRenderer.render();
        }

        if (restart) {
            gameData.setPlayerDead(false);
            gameData.setScreenMessage("");
            create();
            for (IGamePluginService iGamePluginService : gamePluginList) {
                iGamePluginService.start(gameData, world, textures);
            }
            return;
        }

        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
            entityProcessorService.draw(batch, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    public void addEntityProcessingService(IEntityProcessingService service) {
        entityProcessorList.add(service);
    }

    public void removeEntityProcessingService(IEntityProcessingService service) {
        entityProcessorList.remove(service);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService service) {
        postEntityProcessorList.add(service);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService service) {
        postEntityProcessorList.remove(service);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gamePluginList.add(plugin);
                plugin.start(gameData, world, Game.textures);
            }
        });
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }

    public void setIMap(IMap map) {
        this.map = map;
    }

    public void removeIMap(IMap map) {
        this.map = null;
    }
}
