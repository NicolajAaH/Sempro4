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
import dk.sdu.mmmi.cbse.common.data.Attack;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.filehandler.OSGiFileHandle;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class Game implements ApplicationListener {

    private OrthographicCamera cam;
    private final GameData gameData = new GameData(); // GameData object containing all the global variables of the game
    private static final World world = new World();

    //Lists of services
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static final List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();


    private OrthogonalTiledMapRenderer renderer;
    private SpriteBatch batch;
    private static HashMap<Types, Texture> textures = new HashMap<>();
    private IMap map;

    // Screen size
    private final int MAP_SIZE = 58 * 12; //tile size in pixels * number of tiles
    private final int SCREEN_WIDTH = MAP_SIZE + 200;
    private final int SCREEN_HEIGHT = MAP_SIZE;


    //FONTS
    private BitmapFont scoreFont;
    private BitmapFont moneyFont;
    private BitmapFont lifeFont;
    private BitmapFont messagesFont;


    public Game() {
        init();
    }

    public void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "TowerDefense";
        cfg.width = SCREEN_WIDTH;
        cfg.height = SCREEN_HEIGHT;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }


    @Override
    public void create() {
        // setting initial values of games attributes
        gameData.setLife(100);
        gameData.setMoney(500);
        gameData.setScore(0); //TODO: This is not changed anywhere (either fix it or remove it from the screen)
        // Set the screen width and height (global variables) //TODO: Maybe these should be set in GameData.java and be final. these could also be used in init()
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        // Set the start time of the game to the current time
        gameData.setGameStartTime(System.currentTimeMillis());

        // Create the map
        map.setTiledMap(new TmxMapLoader().load("Map.tmx"));
        renderer = new OrthogonalTiledMapRenderer(map.getTiledMap());

        gameData.setGameStartTime(System.currentTimeMillis());

        for(int x = 0 ; x < 100 ; x++){
            gameData.addAttack(new Attack(x*7000,x));
        }

        renderer = new OrthogonalTiledMapRenderer(map.getTiledMap());
        batch = new SpriteBatch();

        //Fonts
        createFonts();

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2f, gameData.getDisplayHeight() / 2f);
        cam.update();

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
        messagesFont = new BitmapFont();
        messagesFont.setColor(Color.RED);
        messagesFont.scale(fontSize);

    }


    private void drawFonts() {
        //Font positions
        int fontSpacing = 25;
        int fontX = SCREEN_WIDTH - 190;
        int scoreY = SCREEN_HEIGHT - fontSpacing;
        int lifeY = SCREEN_HEIGHT - 2 * fontSpacing;
        int moneyY = SCREEN_HEIGHT - 3 * fontSpacing;

        scoreFont.draw(batch, ("Score: " + gameData.getScore()), fontX, scoreY);
        lifeFont.draw(batch, ("Life: " + gameData.getLife()), fontX, lifeY);
        moneyFont.draw(batch, ("Money: " + gameData.getMoney()), fontX, moneyY);

        // Message when player is dead from collision
        if (gameData.isPlayerDead()) {
            String message = "The Player is dead! " +
                    "\n\nYou can no longer place " +
                    "\nnew Towers";
            messagesFont.drawMultiLine(batch, message, fontX, SCREEN_HEIGHT - 5 * fontSpacing);

        }

        if (!gameData.getScreenMessage().isEmpty()) {
            messagesFont.drawMultiLine(batch, gameData.getScreenMessage(), fontX, SCREEN_HEIGHT - 9 * fontSpacing);
        }

/*
        while (gameData.getScreenMessage().isEmpty()) {

            lifeFont.draw(batch, "Testing", fontX, SCREEN_HEIGHT - 7 * fontSpacing);

            long now = System.currentTimeMillis();
            long later = now + TimeUnit.SECONDS.toMillis(5);

            if (now == later) {
                gameData.setScreenMessage("hej");
            }

        }

 */
    }

    public void createAttacks() {
        for(int x = 0 ; x < 100 ; x++){
            gameData.addAttack(new Attack(x*10000,x*2));
        }
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();

        renderer.setView(cam);
        renderer.render();

        // Starting batch and drawing fonts
        batch.begin();
        drawFonts();
        batch.end();

        update();
    }

    private void update() {
        if(gameData.getLife() <=0){
            //TODO stop the game
            //access bundlecontext and unload all modules
            Bundle bundle = FrameworkUtil.getBundle(IBulletCreation.class);
            BundleContext bundleContext = bundle.getBundleContext();
            bundleContext.
            ServiceReference serviceReference = bundleContext.getServiceReference(IBulletCreation.class);
            iBulletService = (IBulletCreation) bundleContext.getService(serviceReference);
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
        cam.viewportHeight = height;
        cam.viewportWidth = width;
        cam.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        lifeFont.dispose();
    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
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

    public void removeIMap(IMap map){
        this.map = null;
    }
}
