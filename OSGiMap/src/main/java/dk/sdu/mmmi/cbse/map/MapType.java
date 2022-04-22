package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.maps.MapLayers;
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
        if(layer.getCell(x,y) == null) return null;
        TiledMapTile tile = layer.getCell(x,y).getTile();

        // getting properties of tile
        return tile.getProperties().get("Tag", String.class);
    }

    @Override
    public String getTileTypeByCoor(int x, int y){

        //get first layer of map
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        // Get tile at position (x,y)
        Point point = getTileCoordinates(x,y);
        TiledMapTile tile = layer.getCell(point.x,point.y).getTile();

        // getting properties of tile
        return tile.getProperties().get("Tag", String.class);
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
       return cells.stream().filter(cell -> cell.getTile().getProperties().get("Tag").equals(property)).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Point getTileCoordinates(float x, float y){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        int tileX = (int) Math.floor(x / layer.getTileHeight());
        int tileY = (int) Math.floor(y / layer.getTileWidth());

        return new Point(tileX, tileY);
    }

    @Override
    public MapLayers getLayers() {
        return tiledMap.getLayers();
    }

    @Override
    public void changeTileType(int x, int y, String tileType){

        //Get first layer of map
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        // Get cell at position (x, y)
        TiledMapTileLayer.Cell cell = layer.getCell(x, y);

        // Getting id of property
        int tileSetSize = tiledMap.getTileSets().getTileSet(0).size();
        int tileId = -1;

        for (int id = 1; id <= tileSetSize; id++) {
            String property = tiledMap.getTileSets().getTileSet(0).getTile(id).getProperties().get("Tag", String.class);
            if (property.equals(tileType)) {
                System.out.println(property);
                tileId = id;
                break;
            }
        }



        // setting tile to til with the id tileId in the map tileset
        cell.setTile(tiledMap.getTileSets().getTile(tileId));
    }

    @Override
    // Tiles are squares, so only one side is needed!
    public int getTileSize(){
        TiledMapTileLayer layer = ((TiledMapTileLayer) tiledMap.getLayers().get(0));

        return (int) layer.getTileHeight();
    }

    /**
     * Calculate X and Y of a tile from X and Y on the map
     * @param x coordinate on map
     * @param y coordiante on map
     * @return point for tile corresponding to the given x and y
     */
    @Override
    public Point mapCoorToTileCoor(float x, float y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        int tileX = (int) Math.floor(x / layer.getTileHeight());
        int tileY = (int) Math.floor(y / layer.getTileWidth());

        return new Point(tileX, tileY);
    }

    // TODO: Add to interface if needed

    public int getTileId(int x, int y){
        //Get first layer of map
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        // Get cell at position (x, y)
        TiledMapTileLayer.Cell cell = layer.getCell(x, y);

        // setting tile to til with the id tileId in the map tileset
        return cell.getTile().getId();
    }

    @Override
    public int getMapHeigth(){
        TiledMapTileLayer layer = ((TiledMapTileLayer) tiledMap.getLayers().get(0));

        return layer.getHeight() * getTileSize();
    }

     @Override
     public int getMapWidth(){
        TiledMapTileLayer layer = ((TiledMapTileLayer) tiledMap.getLayers().get(0));

        return layer.getWidth() * getTileSize();
    }

}

