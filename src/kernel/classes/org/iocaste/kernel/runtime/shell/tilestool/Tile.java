package org.iocaste.kernel.runtime.shell.tilestool;

import org.iocaste.shell.common.tooldata.ViewSpecItem;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class Tile {
    private String name, tilesname;
    private Object object;
    
    public Tile(String name, int i) {
        this.tilesname = name;
        this.name = new StringBuilder(name).append("_").append(i).toString();
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getName(String field) {
        return new StringBuilder(name).append("_").append(field).toString();
    }
    
    public final Object get() {
        return object;
    }
    
    public static final String getLinkName(String name) {
        return name.concat("_action");
    }
    
    public static final String getLinkName(Tile tile) {
        String linkname = new StringBuilder(tile.getName()).
                append("_").append(tile.getPrefix()).toString();
        return getLinkName(linkname);
    }
    
    public final String getPrefix() {
        return tilesname;
    }
    
    public final void set(Object object) {
        this.object = object;
    }
    
    public final ViewSpecItem specItemInstance() {
        return new ViewSpecItem(tilesname, TYPES.LINK, getLinkName(tilesname));
    }
}
