/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.commonmap.IMap;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class MovingPart implements EntityPart {

    public MovingPart() {
    }

    private float dx, dy;
    private float speed;

    private boolean moving = false;
    private float rotationSpeed;

    IMap iMap;

    public MovingPart(float speed, float rotationSpeed) {
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
    }

    public MovingPart(float speed, float rotationSpeed, boolean moving) {
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        this.moving = moving;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if(!moving) return;

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        int radians = positionPart.getRadians();

        float radians2 = (float) Math.toRadians(radians);
        dx = (float) cos(radians2) * speed;
        dy = (float) sin(radians2) * speed;

        if ((0 <= x + dx) && (x + dx <= 700) && (0 <= y + dy) && (y + dy <= 700)){
            // set position
            x += dx;
            y += dy;
        }

        positionPart.setPosition(x,y);
        positionPart.setRadians(radians);
    }

    public void setIMap(IMap iMap){
        System.out.println(iMap);
        this.iMap = iMap;
    }

    public void removeIMap(IMap iMap){
        this.iMap = null;
    }
}
