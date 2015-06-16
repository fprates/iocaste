package org.iocaste.packagetool.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchHelpData implements Serializable {
    private static final long serialVersionUID = -4719222126755931062L;
    private String export, model, name;
    private List<String> itens;
    
    public SearchHelpData(String name) {
        this.name = name;
        itens = new ArrayList<String>();
    }
    
    public final void add(String item) {
        itens.add(item);
    }
    
    public final String getExport() {
        return export;
    }
    
    public final String[] getItens() {
        return itens.toArray(new String[0]);
    }
    
    public final String getModel() {
        return model;
    }
    
    public final String getName() {
        return name;
    }
    
    public final void setExport(String export) {
        this.export = export;
    }
    
    public final void setModel(String model) {
        this.model = model;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
