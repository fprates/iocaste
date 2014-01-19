package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;

public class ComplexDocumentContainer extends AbstractComponent {
    private static final long serialVersionUID = -4650930529587558455L;
    private Map<String, CDContainerItem> itens;
    private ComplexDocument document;
    
    public ComplexDocumentContainer(ComplexDocument document,
            Container container, String name) {
        super(container, Const.VIRTUAL, name);
        itens = new HashMap<String, CDContainerItem>();
        this.document = document;
    }

    /**
     * 
     * @param modelname
     * @param object
     */
    public final void add(String modelname, ExtendedObject object) {
        itens.get(modelname).add(object);
    }
    
    /**
     * 
     * @return
     */
    public final ComplexDocument getDocument() {
        CDContainerItem cditem;
        ExtendedObject[] itens;
        
        document.remove();
        for (String name : this.itens.keySet()) {
            cditem = this.itens.get(name);
            itens = cditem.getItens();
            if (itens == null)
                continue;
            
            for (ExtendedObject object : itens)
                document.add(object);
        }
        
        return document;
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
     * @param header
     */
    public final void setHeaderForm(DataForm header) {
        ExtendedObject oheader = document.getHeader();
        
        header.importModel(oheader.getModel());
        header.setObject(oheader);
    }
    
    /**
     * 
     * @param modelname
     * @param table
     */
    public final void setItens(String modelname, Table table) {
        CDContainerItem cditem = new CDContainerItem(document);
        
        cditem.setItens(modelname, table);
        itens.put(modelname, cditem);
    }
}

class CDContainerItem implements Serializable {
    private static final long serialVersionUID = 750705218827427635L;
    private Table table;
    private ComplexDocument document;
    
    public CDContainerItem(ComplexDocument document) {
        this.document = document;
    }
    
    public final void add(ExtendedObject object) {
        TableItem item = new TableItem(table);
        
        for (TableColumn column : table.getColumns())
            if (!column.isMark())
                item.add(new TextField(table, column.getName()));
        
        if (object == null)
            object = new ExtendedObject(table.getModel());
        
        document.add(object);
        item.setObject(object);
    }
    
    public final ExtendedObject[] getItens() {
        List<ExtendedObject> itens;
        int t = table.length();
        
        if (t == 0)
            return null;
        
        itens = new ArrayList<ExtendedObject>();
        for (TableItem item : table.getItems())
            itens.add(item.getObject());
        
        return itens.toArray(new ExtendedObject[0]);
    }
    
    public final void remove() {
        for (TableItem item : table.getItems())
            if (item.isSelected())
                table.remove(item);
    }
    
    public final void setItens(String modelname, Table table) {
        this.table = table;
        table.importModel(document.getItemModel(modelname));
        for (ExtendedObject object : document.getItens(modelname))
            add(object);
    }
}
