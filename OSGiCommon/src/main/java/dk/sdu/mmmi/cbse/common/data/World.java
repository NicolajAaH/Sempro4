package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.cbse.commonmap.IMap;

import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class World {
    public World() {
    }

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private HashMap<Types, Texture> textureHashMap;

    static IMap IMap;

    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    public void removeEntityType(String stringId){
        //TODO: implement remove by string type
        entityMap.replaceAll((String key, Entity entity) -> {
            return entity;
        });
    }
    
    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    public IMap getMap(){
        return IMap;
    }

    public void setTextureHashMap(HashMap<Types, Texture> textureHashMap) {
        this.textureHashMap = textureHashMap;
    }

    public HashMap<Types, Texture> getTextureHashMap() {
        return textureHashMap;
    }

    public void setIMap(IMap iMap){
        this.IMap = iMap;
    }

    public void removeIMap(IMap iMap){
        this.IMap = null;
    }

}
