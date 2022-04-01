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

    private float dx, dy;
    private float deceleration, acceleration;
    private float speed, rotationSpeed;
    private boolean left, right, up, down;

    public MovingPart(float deceleration, float acceleration, float speed, float rotationSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }
    
    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.speed = speed;
    }
    
    public void setSpeed(float speed) {
        this.acceleration = speed;
        this.speed = speed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();

        if (up){
            positionPart.setPosition(positionPart.getX(), positionPart.getY()+speed);
            positionPart.setLastChange("Up");
        }
        if (left){
            positionPart.setPosition(positionPart.getX()-speed, positionPart.getY());
            positionPart.setLastChange("Left");
        }
        if (right){
            positionPart.setPosition(positionPart.getX()+speed, positionPart.getY());
            positionPart.setLastChange("Right");
        }
        if (down){
            positionPart.setPosition(positionPart.getX(), positionPart.getY()-speed);
            positionPart.setLastChange("Down");
        }

        positionPart.setRadians(radians);
    }

}
