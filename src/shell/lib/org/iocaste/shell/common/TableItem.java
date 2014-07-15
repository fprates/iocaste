package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

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
    private boolean recursive;
    private Map<String, String> elements;
    
    public TableItem(Table table) {
        this(table, -1);
    }
    
    public TableItem(Table table, int pos) {
        super(table, Const.TABLE_ITEM, new StringBuilder(table.getHtmlName()).
                append(".").append(table.length()).toString());
        
        if (pos < 0)
            table.add(this);
        else
            table.add(this, pos);
        
        elements = new LinkedHashMap<>();
        tablename = table.getHtmlName();
        columns = table.getColumns();
        index = table.length() - 1;
        
        switch (table.getSelectionType()) {
        case Table.SINGLE:
            new RadioButton(this, "mark", table.getGroup());
            break;
            
        case Table.MULTIPLE:
            new CheckBox(this, "mark");
            break;
        }
    }
    
    /**
     * Adiciona elemento para linha.
     * @param element elemento
     */
    public final void add(Element element) {
        String htmlname;
        DocumentModelItem modelitem;
        InputComponent input;
        Table table;
        int i;
        
        if (recursive) {
            recursive = false;
            return;
        }
        recursive = true;
        table = getTable();
        i = size();
        if (i == table.width())
            throw new RuntimeException("item overflow for table.");

        htmlname = new StringBuilder(tablename).append(".").
                append(index).append(".").append(element.getName()).toString();
        element.setView(getView());
        element.setHtmlName(htmlname);
        
        modelitem = columns[i].getModelItem();
        if (element.isDataStorable() && modelitem != null) {
            input = (InputComponent)element;
            input.setModelItem(modelitem);
            input.setVisibleLength(columns[i].getLength());
        }

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
     * Retorna objeto extendido associado.
     * @return object extendido.
     */
    public final ExtendedObject getObject() {
        Element element;
        InputComponent input;
        DocumentModelItem modelitem;
        View view = getView();
        DocumentModel model = getTable().getModel();
        ExtendedObject object = new ExtendedObject(model);
        
        for (String name : elements.keySet()) {
            element = view.getElement(elements.get(name));
            
            if (element.isDataStorable()) {
                input = (InputComponent)element;
                modelitem = input.getModelItem();
                
                if (modelitem == null)
                    continue;
                
                object.set(modelitem, input.get());
                continue;
            }
            
            if ((element.getType() == Const.LINK) && (model.contains(name)))
                object.set(name, ((Link)element).getText());
        }
        
        return object;
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
     * Importa objeto extendido.
     * 
     * Preenche componentes de entrada com valores do objeto extendido.
     * 
     * @param object objeto extendido.
     */
    public final void setObject(ExtendedObject object) {
        View view;
        Parameter parameter;
        Link link;
        Element element;
        Object value;
        InputComponent input;
        DocumentModelItem modelitem;
        Component component;
        String name;
        Container container = getTable().getContainer();
        
        view = getView();
        for (TableColumn column : columns) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            element = view.getElement(elements.get(name));
            if (element == null)
                throw new RuntimeException("no component defined for " +
                		"this table item");
            
            if (!element.isDataStorable()) {
                component = (Component)element;
                value = object.get(column.getModelItem());
                component.setText((value == null)? "" : value.toString());
                if (element.getType() != Const.LINK)
                    continue;
                
                link = (Link)element;
                parameter = column.getParameter();
                if (parameter == null) {
                    parameter = new Parameter(container, name);
                    column.setParameter(parameter);
                }
                
                link.add(parameter.getName(), value);
                continue;
            }
            
            input = (InputComponent)element;
            modelitem = column.getModelItem();
            if (input.getModelItem() == null)
                input.setModelItem(modelitem);
            
            if (modelitem == null)
                continue;
            
            value = object.get(modelitem.getName());
            input.set(value);
        }
    }
    
    /**
     * Define seleção da linha.
     * @param selected true, para selecionar linha.
     */
    public final void setSelected(boolean selected) {
        CheckBox mark = getView().getElement(elements.get("mark"));
        mark.setSelected(selected);
    }
    
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
