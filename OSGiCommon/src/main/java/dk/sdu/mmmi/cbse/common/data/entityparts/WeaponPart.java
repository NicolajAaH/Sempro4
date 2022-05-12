package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeaponPart implements EntityPart {

    private float range;
    private float shootingFrequency;
    private float damage;

    public WeaponPart(float range, float shootingFrequency, float damage) {
        this.range = range;
        this.shootingFrequency = shootingFrequency;
        this.damage = damage;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
