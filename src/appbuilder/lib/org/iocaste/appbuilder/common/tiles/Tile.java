package org.iocaste.appbuilder.common.tiles;

import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;

public class Tile {
    private String name;
    private ExtendedObject object;
    
    public Tile(Map<String, Tile> tiles, String name) {
        int i = tiles.size();
        this.name = new StringBuilder(name).append("_").append(i).toString();
        tiles.put(this.name, this);
    }
    
    public final String getName() {
        return name;
    }
    
    public final ExtendedObject get() {
        return object;
    }
    
    public final void set(ExtendedObject object) {
        this.object = object;
    }
}
