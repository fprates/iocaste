package org.iocaste.datadict;

import java.util.Map;

import org.iocaste.datadict.Common.ItensNames;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.View;

public class ItemConfig {
    private Table table;
    private DocumentModel model;
    private DocumentModelItem modelitem;
    private byte mode;
    private Map<ItensNames, DataElement> references;
    private View view;
    
    public final byte getMode() {
        return mode;
    }
    
    public final DocumentModel getModel() {
        return model;
    }
    
    public final DocumentModelItem getModelItem() {
        return modelitem;
    }
    
    public final Map<ItensNames, DataElement> getReferences() {
        return references;
    }
    
    public final Table getTable() {
        return table;
    }
    
    public final View getView() {
        return view;
    }
    
    public final void setMode(byte mode) {
        this.mode = mode;
    }
    
    public final void setModel(DocumentModel model) {
        this.model = model;
        
    }
    
    public final void setModelItem(DocumentModelItem modelitem) {
        this.modelitem = modelitem;
    }
    
    public final void setReferences(Map<ItensNames, DataElement> references) {
        this.references = references;
    }
    
    public final void setTable(Table table) {
        this.table = table;
    }
    
    public final void setView(View view) {
        this.view = view;
    }

}
