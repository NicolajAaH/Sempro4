import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test of F10.1: A Game Engine must initialize the game
 * Test of F10.2: A Game Engine must update the game flow
 */
@ExtendWith(MockitoExtension.class)
public class GameTest {

    @Test
    void gameInitializedUpdated(){
        Game game = new Game();
        assertEquals(game, Gdx.app.getApplicationListener());
    }
}
