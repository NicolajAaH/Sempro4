package dk.sdu.mmmi.cbse.commonmap;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.awt.*;
import java.util.ArrayList;

public interface IMap {

    TiledMap getTiledMap();

    void setTiledMap(TiledMap tiledMap);

    String getTileType(int x, int y);

    Point tileCoorToMapCoor(float x, float y);

    ArrayList<TiledMapTileLayer.Cell> getTilesOfType(String property);

    MapLayers getLayers();
}
