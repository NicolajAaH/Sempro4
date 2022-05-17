package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.commonmap.IMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MockMap implements IMap {

    HashMap<Point, String> tileTypes;
    Point startPoint;
    Point endPoint;

    MockMap(HashMap<Point, String> tileTypes, Point startPoint, Point endPoint){
        this.tileTypes = tileTypes;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }


    @Override
    public TiledMap getTiledMap() {
        return null;
    }

    @Override
    public void setTiledMap(TiledMap tiledMap) {

    }

    @Override
    public String getTileType(int x, int y) {
        return tileTypes.get(new Point(x,y));
    }

    @Override
    public String getTileTypeByCoor(int i, int i1) {
        return null;
    }

    @Override
    public Point tileCoorToMapCoor(float v, float v1) {
        return null;
    }

    @Override
    public Point getStartTileCoor() {
        return startPoint;
    }

    @Override
    public Point getEndTileCoor() {
        return endPoint;
    }

    @Override
    public ArrayList<TiledMapTileLayer.Cell> getTilesOfType(String s) {
        return null;
    }

    @Override
    public MapLayers getLayers() {
        return null;
    }

    @Override
    public ArrayList<Point> getPath() {
        PathFinder pathFinder = new PathFinder(this);
        return pathFinder.calculatePath();
    }

    @Override
    public void changeTileType(int i, int i1, String s) {

    }

    @Override
    public int getTileSize() {
        return 0;
    }

    @Override
    public Point mapCoorToTileCoor(float v, float v1) {
        return null;
    }

    @Override
    public int getMapHeigth() {
        return 0;
    }

    @Override
    public int getMapWidth() {
        return 0;
    }

    @Override
    public Point getTileCenter(Point point) {
        return null;
    }

    @Override
    public Point getTileCoordinates(float v, float v1) {
        return null;
    }

    @Override
    public int getTileId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean isInsideMap(float v, float v1) {
        return false;
    }
}
