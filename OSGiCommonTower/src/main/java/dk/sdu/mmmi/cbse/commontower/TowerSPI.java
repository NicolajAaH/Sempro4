package dk.sdu.mmmi.cbse.commontower;

/*
Provided interface
The tower will expose a function for placing a tower in the map.
Required interface
The tower will require the map interface to find out where it can be placed.
It will require the projectile interface to generate projectiles.
 */


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface TowerSPI {
    Entity createTower(World world, int xTile, int yTile);
}
