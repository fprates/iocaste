package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;

public class ComplexDocumentContainer extends AbstractComponent {
    private static final long serialVersionUID = -4650930529587558455L;
    public DataForm header;
    public Map<String, CDContainerItem> itens;
    
    public ComplexDocumentContainer(Container container, String name) {
        super(container, Const.VIRTUAL, name);
        itens = new HashMap<String, CDContainerItem>();
    }

    /**
     * 
     * @param modelname
     * @param object
     */
    public final void add(String modelname, ExtendedObject object) {
        itens.get(modelname).add(object);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isControlComponent()
     */
    @Override
    public final boolean isControlComponent() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return false;
    }

    /**
     * 
     * @param modelname
     */
    public final void remove(String modelname) {
        itens.get(modelname).remove();
    }
    
    /**
     * 
     * @param document
     */
    public final void setDocument(ComplexDocument document) {
        ExtendedObject oheader = document.getHeader();
        
        header.importModel(oheader.getModel());
        header.setObject(oheader);
        
        for (String name : itens.keySet())
            itens.get(name).set(document.getItemModel(name));
    }
    
    /**
     * 
     * @param header
     */
    public final void setHeaderForm(DataForm header) {
        this.header = header;
    }
    
    /**
     * 
     * @param modelname
     * @param table
     */
    public final void setItens(String modelname, Table table) {
        CDContainerItem cditem = new CDContainerItem();
        
        cditem.setItens(table);
        itens.put(modelname, cditem);
    }
}

class CDContainerItem implements Serializable {
    private static final long serialVersionUID = 750705218827427635L;
    private Table table;
    
    public final void add(ExtendedObject object) {
        TableItem item = new TableItem(table);
        
        for (TableColumn column : table.getColumns())
            if (!column.isMark())
                item.add(new TextField(table, column.getName()));
        
        if (object == null)
            return;
        
        item.setObject(object);
    }
    
    public final void remove() {
        for (TableItem item : table.getItens())
            if (item.isSelected())
                table.remove(item);
    }
    
    public final void set(DocumentModel model) {
        table.importModel(model);
    }
    
    public final void setItens(Table table) {
        this.table = table;
    }
}