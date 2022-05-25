package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class Entity extends Sprite implements Serializable {

    private Sprite sprite;
    private Texture texture;
    private Types type;

    private final UUID ID = UUID.randomUUID();

    private float radius;
    private Map<Class, EntityPart> parts;

    public Entity(Sprite sprite, Types type) {
        super(sprite);
        this.type = type;
        parts = new ConcurrentHashMap<>();
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public String getID(){
        return ID.toString();
    }

}
