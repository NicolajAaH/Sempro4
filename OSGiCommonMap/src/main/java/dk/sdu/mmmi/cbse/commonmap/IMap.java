package dk.sdu.mmmi.cbse.commonmap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.awt.*;
import java.util.ArrayList;

public interface IMap {

    TiledMap getTiledMap();

    void setTiledMap(TiledMap tiledMap);

    String getTileType(int x, int y);

    String getTileTypeByCoor(int x, int y);


    Point tileCoorToMapCoor(float x, float y);

    ArrayList<TiledMapTileLayer.Cell> getTilesOfType(String property);

    Point getTileCoordinates(float x, float y);
}
