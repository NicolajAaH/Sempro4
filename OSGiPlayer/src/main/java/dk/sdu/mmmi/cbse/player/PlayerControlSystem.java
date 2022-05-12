package dk.sdu.mmmi.cbse.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonmap.IMap;
import dk.sdu.mmmi.cbse.commonplayer.Player;
import dk.sdu.mmmi.cbse.commontower.Tower;
import dk.sdu.mmmi.cbse.commontower.TowerSPI;

import java.awt.*;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;

public class PlayerControlSystem implements IEntityProcessingService {

    TowerSPI towerSPI;
    IMap map;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);


            GameKeys keys = gameData.getKeys();


            if(keys.isDown(SHIFT)) {
                System.out.println(gameData);
            }

            handleInput(positionPart, movingPart, keys);

            if (gameData.getKeys().isDown(SPACE)) { //TODO: Should only be called once per SPACE press, even when space is held down
                Tower tower;

                Point coordinates = map.mapCoorToTileCoor(positionPart.getX(),positionPart.getY());
                tower = (Tower) towerSPI.createTower(gameData, world, coordinates.x, coordinates.y);

                if (tower !=null && tower.getBuildCost() > gameData.getMoney()) {
                    int buildCost = tower.getBuildCost();
                    tower = null;
                    map.changeTileType(coordinates.x, coordinates.y, "Grass");
                    gameData.setScreenMessage("You don't have enough \nmoney to buy a Tower \n\nTower cost: " + buildCost);
                }

                if (tower != null) {
                    world.addEntity(tower);
                    gameData.setMoney(gameData.getMoney() - tower.getBuildCost());
                    gameData.setScore(gameData.getScore() + 5);
                }
                gameData.getKeys().setKey(SPACE, false);
            }

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
        }
    }

    private void handleInput(PositionPart positionPart, MovingPart movingPart, GameKeys keys) {

        if (keys.isDown(LEFT)) {
            positionPart.setRadians(PositionPart.left);
            movingPart.setMoving(true);
        } else if (keys.isDown(RIGHT)) {
            positionPart.setRadians(PositionPart.right);
            movingPart.setMoving(true);
        } else if (keys.isDown(UP)) {
            positionPart.setRadians(PositionPart.up);
            movingPart.setMoving(true);
        } else if (keys.isDown(DOWN)) {
            positionPart.setRadians(PositionPart.down);
            movingPart.setMoving(true);
        } else {
            movingPart.setMoving(false);
        }

    }

    @Override
    public void draw(SpriteBatch batch, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            player.draw(batch);
        }
    }

    public void setTowerSPI(TowerSPI towerSPI) {
        this.towerSPI = towerSPI;
    }

    public void removeTowerSPI(TowerSPI towerSPI) {
        this.towerSPI = null;
    }

    public void setIMap(IMap map) {
        this.map = map;
    }

    public void removeIMap(IMap map) {
        this.map = null;
    }
}
