package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class WeaponPart implements EntityPart {

    private float range;
    private float shootingFrequency;
    private float damage;

    public WeaponPart(float range, float shootingFrequency, float damage) {
        this.range = range;
        this.shootingFrequency = shootingFrequency;
        this.damage = damage;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getShootingFrequency() {
        return shootingFrequency;
    }

    public void setShootingFrequency(float shootingFrequency) {
        this.shootingFrequency = shootingFrequency;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
