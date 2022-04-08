package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.cbse.common.data.Attack;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.core.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.filehandler.OSGiFileHandle;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private OrthogonalTiledMapRenderer renderer;
    private SpriteBatch batch;
    public HashMap<Types, Texture> textures = new HashMap<>();


    public Game() {
        init();
    }

    public void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "TowerDefense";
        cfg.width = 1200;
        cfg.height = 800;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        world.setTiledMap(new TmxMapLoader().load("Map.tmx"));

        gameData.setGameStartTime(System.currentTimeMillis());
        gameData.addAttack(new Attack(0,10));

        renderer = new OrthogonalTiledMapRenderer(world.getMap());
        batch = new SpriteBatch();
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        textures.put(Types.PLAYER, new Texture(new OSGiFileHandle("/images/Sprites/player_nogun.png")));
        textures.put(Types.TOWER, new Texture(new OSGiFileHandle("/images/Sprites/cannon2.png")));
        textures.put(Types.ENEMY, new Texture(new OSGiFileHandle("/images/Sprites/monster.png")));
        // TODO: change image of projectile
        textures.put(Types.PROJECTILE, new Texture(new OSGiFileHandle("/images/Sprites/player_nogun.png")));


        world.setTextureHashMap(textures);

        for (IGamePluginService iGamePluginService : gamePluginList) {
            switch (iGamePluginService.getType()){
                case PLAYER:
                    Texture texturePlayer = textures.get(Types.PLAYER);
                    iGamePluginService.create(gameData, world);
                    break;
                case ENEMY:
                    Texture textureEnemy = textures.get(Types.ENEMY);
                    break;

                case TOWER:
                    Texture textureTower = textures.get(Types.TOWER);
                    break;

                case PROJECTILE:
                    Texture textureProjectile = textures.get(Types.PROJECTILE);
                    break;
            }
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
        update();
        draw(batch);

        replaceTile(1,2,4);
        //System.out.println("tile id " + getTileId(1,1));

//        Collection<Entity> entities = world.getEntities();
//        for (Entity entity : entities){
//            System.out.println(getTileType(entity));
//
//        }
    }

    private int getTileId(int x, int y){
        //Get first layer of map
        TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get(0);

        // Get cell at position (x, y)
        TiledMapTileLayer.Cell cell = layer.getCell(x, y);

        // setting tile to til with the id tileId in the map tileset
        return cell.getTile().getId();
    }

    /**
     * Calculate X and Y of a tile from X and Y on the map
     * @param x coordinate on map
     * @param y coordiante on map
     * @return point for tile corresponding to the given x and y
     */
    private Point mapCoorToTileCoor(float x, float y){
        TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get(0);

        int tileX = (int) Math.floor(x / layer.getTileHeight());
        int tileY = (int) Math.floor(y / layer.getTileWidth());

        return new Point(tileX, tileY);
    }

    /**
     * Calculate X and Y on the map from X and Y from a tile
     * @param x coordinate of tile
     * @param y coordiante of tile
     * @return point on map corresponding to the given x and y
     */
    private Point tileCoorToMapCoor(float x, float y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get(0);

        int mapX = (int) (x * layer.getTileHeight() + (layer.getTileHeight()/2));
        int mapY = (int) (y * layer.getTileWidth() + (layer.getTileWidth()/2));

        return new Point(mapX, mapY);
    }

    private String getTileType(Entity entity){

        PositionPart positionPart = entity.getPart(PositionPart.class);
        // Get entity coordinates
        float x = positionPart.getX();
        float y = positionPart.getY();

        // Get first layer of map
       TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get(0);

        // Get coordinate of the entity's tile
        Point tilePoint = mapCoorToTileCoor(x, y);

        // Get cell at position (x,y)
        TiledMapTileLayer.Cell cell = layer.getCell((int) tilePoint.getX(), (int) tilePoint.getY());

        TiledMapTile tile = cell.getTile();

        // print
        return tile.getProperties().get("Tag", String.class);
    }

    private void replaceTile(int x, int y, int tileId){
        // Replacing af tile on the map at pos (x,y) with tile with tileIf from tileset from the map

        //Get first layer of map
        TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get(0);

        // Get cell at position (x, y)
        TiledMapTileLayer.Cell cell = layer.getCell(x, y);

        // setting tile to til with the id tileId in the map tileset
        cell.setTile(world.getMap().getTileSets().getTile(tileId));
    }


    private void update() {
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

    private void draw(SpriteBatch spriteBatch) {
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
          entityProcessorService.draw(spriteBatch, world);
        }
        //IPost

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

    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.add(plugin);
        plugin.start(gameData, world);
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }



}
