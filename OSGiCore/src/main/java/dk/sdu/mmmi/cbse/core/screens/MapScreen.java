package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dk.sdu.mmmi.cbse.Game;


public class MapScreen implements Screen {

    Stage stage;
    Table table;
    Skin skin;


    private OrthographicCamera cam;
    private OrthogonalTiledMapRenderer renderer;

    // Reference to game to access its variables
    private Game game;



    public MapScreen(Game game) {
        this.game = game;

    }

    /**
     * Called when Screen when is first rendered
     */
    @Override
    public void show() {


    }

    // Called for every frame rendering
    @Override
    public void render(float delta) {
        game.getMap().setTiledMap(new TmxMapLoader().load("Map.tmx"));

        renderer = new OrthogonalTiledMapRenderer(game.getMap().getTiledMap());

        cam = new OrthographicCamera(game.getGameData().getDisplayWidth(), game.getGameData().getDisplayHeight());
        cam.translate(game.getGameData().getDisplayWidth() / 2, game.getGameData().getDisplayHeight() / 2);
        cam.update();

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.getGameData().setDelta(Gdx.graphics.getDeltaTime());
        game.getGameData().getKeys().update();

        renderer.setView(cam);
        renderer.render();

    }




    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    // To get rid of a screen
    @Override
    public void dispose() {

    }
}
