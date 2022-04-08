package dk.sdu.mmmi.cbse.tower;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Types;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commontower.Tower;
import dk.sdu.mmmi.cbse.commontower.TowerSPI;

import java.awt.*;
import java.util.Random;

public class TowerControlSystem implements IEntityProcessingService, TowerSPI {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity tower : world.getEntities(Tower.class)) {
            PositionPart positionPart = tower.getPart(PositionPart.class);
            // TODO: SET DIRECTION OF SHOOTING- AI!
            tower.setRadius(100);
            // Rotate sprite
        }
    }

    @Override
    public void draw(SpriteBatch batch, World world) {
       for (Entity tower : world.getEntities(Tower.class)) {
           tower.draw(batch);
       }
    }
    @Override
    public void createTower(World world, int xTile, int yTile) {

        // Checking tileProperties if tower can be created
        if (!world.getMap().getTileType(xTile, yTile).equals("Grass")) {
            System.out.println("can not place tower here");
            return;
        }

        // Replacing af tile on the map at pos (x,y) with tile with tileIf from tileset from the map
        int tileId = 4; // TODO: get tileID from properties of tile

        //Get first layer of map
        TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getTiledMap().getLayers().get(0);

        // Get cell at position (x, y)
        TiledMapTileLayer.Cell cell = layer.getCell(xTile, yTile);

        // setting tile to til with the id tileId in the map tileset
        cell.setTile(world.getMap().getTiledMap().getTileSets().getTile(tileId));

        float deacceleration = 0;
        float acceleration = 0;
        float speed = 0;
        float rotationSpeed = 5;

        Point coordinate = tileCoorToMapCoor(xTile, yTile, world);
        float x = (float) coordinate.x - 29;
        float y = (float) coordinate.y - 29;
        float radians = 3.1415f / 2;

        Sprite sprite = new Sprite(world.getTextureHashMap().get(Types.TOWER));
        Entity tower = new Tower(sprite, Types.TOWER); //throws exception nulpointer
        tower.add(new MovingPart(deacceleration, acceleration, speed, rotationSpeed));
        tower.add(new PositionPart(x, y, radians));
        tower.add(new LifePart(1));
        world.addEntity(tower);
    }
}
