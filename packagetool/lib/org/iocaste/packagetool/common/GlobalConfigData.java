package org.iocaste.packagetool.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class GlobalConfigData implements Serializable {
    private static final long serialVersionUID = -3579471666761111831L;
    private Set<GlobalConfigItemData> itens;
    
    public GlobalConfigData() {
        itens = new HashSet<>();
    }
    
    /**
     * 
     * @param name
     * @param type
     * @param value
     */
    public final void define(String name, Class<?> type, Object value) {
        GlobalConfigItemData item = new GlobalConfigItemData();
        item.setName(name);
        item.setType(type);
        item.setValue(value);
        itens.add(item);
    }
    
    /**
     * 
     * @return
     */
    public final GlobalConfigItemData[] getItens() {
        return itens.toArray(new GlobalConfigItemData[0]);
    }
}
