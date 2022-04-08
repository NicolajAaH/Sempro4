package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.commonmap.IMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MapType implements IMap {

    private TiledMap tiledMap;
    private ArrayList<TiledMapTileLayer.Cell> cells = new ArrayList<>();

    public MapType() {
    }

    @Override
    public TiledMap getTiledMap(){
        return tiledMap;
    }

    @Override
    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        collectCells();
    }

    private void collectCells(){
        cells.clear();
        TiledMapTileLayer layer = ((TiledMapTileLayer) tiledMap.getLayers().get(0));
        for(int y = 0 ; y < layer.getHeight() ; y++){
            for(int x = 0 ; x < layer.getHeight() ; x++){
                cells.add(layer.getCell(x,y));
            }
        }
    }

    @Override
    public String getTileType(int x, int y){

        //get first layer of map
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        // Get tile at position (x,y)
        TiledMapTile tile = layer.getCell(x,y).getTile();

        // getting properties of tile
        String tileProperties = tile.getProperties().get("Tag", String.class);

        return tileProperties;
    }

    /**
     * Calculate X and Y on the map from X and Y from a tile
     * @param x coordinate of tile
     * @param y coordiante of tile
     * @return point on map corresponding to the given x and y
     */
    @Override
    public Point tileCoorToMapCoor(float x, float y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        int mapX = (int) (x * layer.getTileHeight() + (layer.getTileHeight()/2));
        int mapY = (int) (y * layer.getTileWidth() + (layer.getTileWidth()/2));

        return new Point(mapX, mapY);
    }

    @Override
    public ArrayList<TiledMapTileLayer.Cell> getTilesOfType(String property){
       return cells.stream().filter(cell -> cell.getTile().getProperties().containsKey(property)).collect(Collectors.toCollection(ArrayList::new));
    }
}
