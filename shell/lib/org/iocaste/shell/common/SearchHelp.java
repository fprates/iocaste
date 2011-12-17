package org.iocaste.shell.common;

import java.util.LinkedHashSet;
import java.util.Set;

public class SearchHelp extends AbstractControlComponent {
    private static final long serialVersionUID = -1582634834243087782L;
    private String modelname, export;
    private Set<String> itemnames;
    
    public SearchHelp(Container container, String name) {
        super(container, Const.SEARCH_HELP, name);
        
        itemnames = new LinkedHashSet<String>();
        setCancellable(true);
    }

    public final void addModelItemName(String itemname) {
        itemnames.add(itemname);
    }
    
    public final boolean contains(String name) {
        return itemnames.contains(name);
    }
    
    public final String getExport() {
        return export;
    }
    
    public final String getModelName() {
        return modelname;
    }
    
    public final void setExport(String export) {
        this.export = export;
    }
    
    public final void setModelName(String modelname) {
        this.modelname = modelname;
    }
}
