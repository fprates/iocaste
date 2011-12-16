package org.iocaste.shell.common;

import java.util.ArrayList;
import java.util.List;

public class SearchHelp extends AbstractControlComponent {
    private static final long serialVersionUID = -1582634834243087782L;
    private String modelname;
    private List<String> itemnames;
    
    public SearchHelp(Container container, String name) {
        super(container, Const.SEARCH_HELP, name);
        
        itemnames = new ArrayList<String>();
        setCancellable(true);
    }

    public final void addModelItemName(String itemname) {
        itemnames.add(itemname);
    }
    
    public final String[] getModelItensNames() {
        return itemnames.toArray(new String[0]);
    }
    
    public final String getModelName() {
        return modelname;
    }
    
    public final void setModelName(String modelname) {
        this.modelname = modelname;
    }
}
