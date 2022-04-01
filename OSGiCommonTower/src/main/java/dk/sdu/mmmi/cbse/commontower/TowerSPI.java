package dk.sdu.mmmi.cbse.commontower;

/*
Provided interface
The tower will expose a function for placing a tower in the map.
Required interface
The tower will require the map interface to find out where it can be placed.
It will require the projectile interface to generate projectiles.
 */


public interface TowerSPI {
    void createTower(int xTile, int yTile);
}
