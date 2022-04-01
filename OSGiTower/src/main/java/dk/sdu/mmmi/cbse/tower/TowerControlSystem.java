package dk.sdu.mmmi.cbse.tower;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commontower.TowerSPI;

public class TowerControlSystem implements IEntityProcessingService, TowerSPI {
    @Override
    public void process(GameData gameData, World world) {
        // TODO: SET DIRECTION OF SHOOTING- AI!
    }

    @Override
    public void draw(SpriteBatch batch, World world) {
        // TODO: MOVE TOWERHEAD ACCORDING TO SHOOTING DIRECTION
    }
    @Override
    public void createTower(World world, int xTile, int yTile) {
        // Replacing af tile on the map at pos (x,y) with tile with tileIf from tileset from the map
        int tileId = 4;
        // TODO: get tileID from properties of tile

        //Get first layer of map
        TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get(0);

        // Get cell at position (x, y)
        TiledMapTileLayer.Cell cell = layer.getCell(xTile, yTile);

        // setting tile to til with the id tileId in the map tileset
        cell.setTile(world.getMap().getTileSets().getTile(tileId));
    }

}
