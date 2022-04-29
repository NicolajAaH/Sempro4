/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.commonmap.IMap;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MovingPart implements EntityPart {

    public MovingPart() {
    }

    private float dx, dy;
    private float speed;

    private boolean moving = false;
    private float rotationSpeed;

   static IMap map;

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

        if (map.isInsideMap(x + dx, y + dy) == true){
            // set position
            x += dx;
            y += dy;
        }

        positionPart.setPosition(x,y);
        positionPart.setRadians(radians);
    }

    public void setIMap(IMap map){
        this.map = map;
    }

    public void removeIMap(IMap map){
        this.map = null;
    }
}
