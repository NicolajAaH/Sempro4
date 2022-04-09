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
    private boolean left, right, up, down, isProjectile;

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

    public void setIsProjectile(boolean value){
        this.isProjectile = value;
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

        if (up && !left && !down && !right){
            dy = speed;
            positionPart.setLastChange("Up");
        }
        if (left && !right && !up && !down){
            dx = -speed;
            positionPart.setLastChange("Left");
        }
        if (right && !left && !up && !down){
            dx = speed;
            positionPart.setLastChange("Right");
        }
        if (down && !up && !left && !right){
            dy = -speed;
            positionPart.setLastChange("Down");
        }

        if(!down && !up && !left && !right){
            dx = 0;
            dy = 0;
        }

        if (isProjectile) {
            //System.out.println("cos(radians) "  + cos(radians));
            //radians = positionPart.getRadians();
            System.out.println("retning af projektil i grader " + (int) Math.toDegrees(radians % (Math.PI/2)));
            dx = (float) cos(radians) * speed;
            dy = (float) sin(radians) * speed;
        }

        // set position
        x += dx;
        y += dy;

        positionPart.setPosition(x,y);
        //positionPart.setX(x);
        //positionPart.setY(y);

        //positionPart.setRadians(radians);
    }

}
