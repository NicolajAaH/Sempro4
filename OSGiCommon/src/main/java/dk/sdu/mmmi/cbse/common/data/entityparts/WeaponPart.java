package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeaponPart implements EntityPart {

    private float range;

    private float projectileSpeed;

    public WeaponPart(float range, float projectileSpeed) {
        this.range = range;
        this.projectileSpeed = projectileSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
