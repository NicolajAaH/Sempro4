package dk.sdu.mmmi.cbse.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import dk.sdu.mmmi.cbse.commontower.TowerSPI;

import java.awt.*;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;

public class PlayerControlSystem implements IEntityProcessingService {

    TowerSPI towerSPI;
    IMap map;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            movingPart.setDown(gameData.getKeys().isDown(DOWN));

            if (gameData.getKeys().isDown(SPACE)) {
                Point coordinates = getTileCoordinates(positionPart.getX(), positionPart.getY(), world);
                towerSPI.createTower(world, (int) coordinates.x, (int) coordinates.y);
            }

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);
        }
    }

    @Override
    public void draw(SpriteBatch batch, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            player.draw(batch);
        }
    }

    public void setTowerSPI(TowerSPI towerSPI) {
        this.towerSPI = towerSPI;
    }

    public void removeTowerSPI(TowerSPI towerSPI) {
        this.towerSPI = null;
    }

    // TODO: refactor to use method from Imap instead
    private Point getTileCoordinates(float x, float y, World world){

        return map.mapCoorToTileCoor(x,y);
        /*
        TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getTiledMap().getLayers().get(0);

        int tileX = (int) Math.floor(x / layer.getTileHeight());
        int tileY = (int) Math.floor(y / layer.getTileWidth());

        return new Point(tileX, tileY);

         */
    }

    public void setIMap(IMap map) {
        this.map = map;
    }

    public void removeIMap(IMap map){
        this.map = null;
    }
}
