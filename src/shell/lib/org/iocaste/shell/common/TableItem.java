package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModelItem;

/**
 * Item de tabela html.
 * 
 * @author Francisco Prates
 *
 */
public class TableItem extends AbstractContainer {
    private static final long serialVersionUID = -1076760582954115701L;
    private TableColumn[] columns;
    private int index;
    private String tablename;
    private Map<String, String> elements;
    
    public TableItem(Table table) {
        this(table, -1);
    }
    
    public TableItem(Table table, int pos) {
        super(table, Const.TABLE_ITEM, new StringBuilder(table.getHtmlName()).
                append(".").append((pos == -1)? table.size() : pos).toString());
        
        elements = new LinkedHashMap<>();
        tablename = table.getHtmlName();
        columns = table.getColumns();
        index = table.size() - 1;
        
        switch (table.getSelectionType()) {
        case Table.SINGLE:
            table.getGroup().button(this, "mark");
            break;
            
        case Table.MULTIPLE:
            new CheckBox(this, "mark").setStyleClass("table_item_check");
            break;
        }
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#add(
     *    org.iocaste.shell.common.Element)
     */
    @Override
    public final void add(Element element) {
        String htmlname;
        DocumentModelItem modelitem;
        InputComponent input;
        Table table;
        int i, vlength;
        
        table = getTable();
        i = size();
        if (i == table.width())
            throw new RuntimeException("item overflow for table.");

        htmlname = new StringBuilder(tablename).append(".").
                append(index).append(".").append(element.getName()).toString();
        element.setView(getView());
        element.setHtmlName(htmlname);
        element.setTranslatable(columns[i].isTranslatable());
        
        modelitem = columns[i].getModelItem();
        if (element.isDataStorable() && modelitem != null) {
            input = (InputComponent)element;
            input.setModelItem(modelitem);
            vlength = columns[i].getVisibleLength();
            if (vlength == 0)
                input.setVisibleLength(columns[i].getLength());
            else
                input.setVisibleLength(vlength);
        }

        if (columns[i].isMark())
            elements.put("mark", htmlname);
        else
            elements.put(columns[i].getName(), htmlname);
        super.add(element);
    }
    
    /**
     * Retorna elemento da linha.
     * @param name nome do elemento
     * @return elemento
     */
    public final <T extends Element> T get(String name) {
        return getView().getElement(elements.get(name));
    }
    
    /**
     * Retorna tabela associada.
     * @return tabela
     */
    public final Table getTable() {
        return getView().getElement(tablename);
    }
    
    /**
     * Indica se a linha foi selecionada.
     * @return true, se foi selecionada.
     */
    public final boolean isSelected() {
        InputComponent input = getView().getElement(elements.get("mark"));        
        return input.isSelected();
    }
    
    /**
     * Define seleção da linha.
     * @param selected true, para selecionar linha.
     */
    public final void setSelected(boolean selected) {
        CheckBox mark = getView().getElement(elements.get("mark"));
        mark.setSelected(selected);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractElement#toString()
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (TableColumn column : columns) {
            if (sb.length() > 0)
                sb.append(", ");
            
            sb.append(column.getName());
        }
        
        return sb.toString();
    }
}
