package org.iocaste.shell.common;

import java.util.LinkedHashSet;
import java.util.Set;

public class SearchHelp extends AbstractControlComponent {
    private static final long serialVersionUID = -1582634834243087782L;
    private String modelname, export;
    private Set<String> itemnames;
    private InputComponent input;
    
    public SearchHelp(Container container, String name) {
        super(container, Const.SEARCH_HELP, name);
        
        itemnames = new LinkedHashSet<String>();
        setCancellable(true);
        setAllowStacking(true);
    }

    /**
     * 
     * @param itemname
     */
    public final void addModelItemName(String itemname) {
        itemnames.add(itemname);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final boolean contains(String name) {
        return itemnames.contains(name);
    }
    
    /**
     * 
     * @return
     */
    public final String getExport() {
        return export;
    }
    
    /**
     * 
     * @return
     */
    public final InputComponent getInput() {
        return input;
    }
    
    /**
     * 
     * @return
     */
    public final String getModelName() {
        return modelname;
    }
    
    /**
     * 
     * @param export
     */
    public final void setExport(String export) {
        this.export = export;
    }
    
    /**
     * 
     * @param input
     */
    public final void setInput(InputComponent input) {
        this.input = input;
    }
    
    /**
     * 
     * @param modelname
     */
    public final void setModelName(String modelname) {
        this.modelname = modelname;
    }
}
