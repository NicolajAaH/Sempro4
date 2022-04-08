package dk.sdu.mmmi.cbse.common.services;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);

    void draw(SpriteBatch batch, World world);
}
