package dk.sdu.mmmi.cbse.commonmap;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public interface IMap {

    TiledMap getTiledMap();

    void setTiledMap(TiledMap tiledMap);

    String getTileType(int x, int y);

    String getTileTypeByCoor(int x, int y);

    Point tileCoorToMapCoor(float x, float y);

    Point getStartTileCoor();

    Point getEndTileCoor();
    ArrayList<TiledMapTileLayer.Cell> getTilesOfType(String property);

    MapLayers getLayers();

    ArrayList<Point> getPath();

    void changeTileType(int x, int y, String tileType);

    int getTileSize();

    Point mapCoorToTileCoor(float x, float y);

    int getMapHeigth();

    int getMapWidth();

    Point getTileCenter(Point point);

    Point getTileCoordinates(float x, float y);

    int getTileId(int x, int y);

    boolean isInsideMap(float x, float y);
}
