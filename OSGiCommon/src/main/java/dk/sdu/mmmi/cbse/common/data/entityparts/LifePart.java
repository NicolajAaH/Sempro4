package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LifePart implements EntityPart {
    private int life;

    public LifePart(int life) {
        this.life = life;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
