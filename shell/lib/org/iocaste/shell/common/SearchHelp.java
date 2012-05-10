package org.iocaste.shell.common;

import java.util.LinkedHashSet;
import java.util.Set;

public class SearchHelp extends AbstractControlComponent {
    private static final long serialVersionUID = -1582634834243087782L;
    private String modelname, export, inputname;
    private Set<String> itemnames;
    
    public SearchHelp(Container container, String name) {
        super(container, Const.SEARCH_HELP, name);
        
        itemnames = new LinkedHashSet<String>();
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
    public final String getInputName() {
        return inputname;
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
     * @param inputname
     */
    public final void setInputName(String inputname) {
        this.inputname = inputname;
    }
    
    /**
     * 
     * @param modelname
     */
    public final void setModelName(String modelname) {
        this.modelname = modelname;
    }
}
