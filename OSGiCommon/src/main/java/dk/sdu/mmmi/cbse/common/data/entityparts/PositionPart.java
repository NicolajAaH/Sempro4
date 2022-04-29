/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

import java.awt.*;

/**
 *
 * @author Alexander
 */
public class PositionPart implements EntityPart {

    public static final int right = 0;
    public static final int up = 90;
    public static final int left = 180;
    public static final int down = 270;
    private float x;
    private float y;
    private int radians = 0; // direction of movement
    private String lastChange;
    private float originX, originY;

    public PositionPart(float x, float y, int radians) {
        this.x = x;
        this.y = y;
        this.originX = x;
        this.originY = y;
        this.radians = radians;
    }

    public String getLastChange() {
        return lastChange;
    }

    public void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Integer getRadians() {
        return radians;
    }
    
    public void setX(float newX) {
        this.x = newX;
    }
    
    public void setY(float newY) {
        this.y = newY;
    }

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void setRadians(Integer radians) {
        this.radians = radians;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }

    public float getDistanceFromOrigin(){
        return (float) Math.sqrt((originX - x) * (originX - x) + (originY - y) * (originY -y));
    }
}
