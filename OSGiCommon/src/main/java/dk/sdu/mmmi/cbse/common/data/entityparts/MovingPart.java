/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class MovingPart implements EntityPart {

    public static final Integer right = 0;
    public static final Integer up = 90;
    public static final Integer left = 180;
    public static final Integer down = 270;

    private float dx, dy;
    private float speed;
    private float rotationSpeed;

    public MovingPart(float speed, float rotationSpeed) {
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
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

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        Integer radians = positionPart.getRadians();

        if(radians == null) return;

        float radians2 = (float) Math.toRadians(radians);
        dx = (float) cos(radians2) * speed;
        dy = (float) sin(radians2) * speed;


        if ((0 <= x + dx) && (x + dx <= 600) && (0 <= y + dy) && (y + dy <= 600)){
            // set position
            x += dx;
            y += dy;
        }

        positionPart.setPosition(x,y);
        positionPart.setRadians(radians);
    }
}
