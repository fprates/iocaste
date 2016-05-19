package org.iocaste.appbuilder.common.tiles;

import java.util.Map;

public class Tile {
    private String name;
    private Object object;
    
    public Tile(Map<String, Tile> tiles, String name) {
        int i = tiles.size();
        this.name = new StringBuilder(name).append("_").append(i).toString();
        tiles.put(this.name, this);
    }
    
    public final String getName() {
        return name;
    }
    
    public final Object get() {
        return object;
    }
    
    public final void set(Object object) {
        this.object = object;
    }
}
