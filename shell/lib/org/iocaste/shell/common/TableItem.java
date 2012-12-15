package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

/**
 * Item de tabela html.
 * 
 * @author Francisco Prates
 *
 */
public class TableItem implements Serializable {
    private static final long serialVersionUID = -1076760582954115701L;
    private Map<String, Element> elements;
    private TableColumn[] columns;
    private Table table;
    private Locale locale;
    private boolean visible;
    
    public TableItem(Table table) {
        String markname;
        RadioButton mark;
        table.add(this);
        
        this.table = table;
        columns = table.getColumns();
        elements = new LinkedHashMap<String, Element>();
        visible = true;
        
        switch (table.getSelectionType()) {
        case Table.SINGLE:
            markname = new StringBuilder(table.getName()).append(".").
                    append(table.length() - 1).append(".mark").toString();
            mark = new RadioButton(table, markname, table.getGroup());
            
            elements.put("mark", mark);
            break;
            
        case Table.MULTIPLE:
            elements.put("mark", new CheckBox(table, "mark"));
            break;
        }
    }
    
    /**
     * Adiciona elemento para linha.
     * @param element elemento
     */
    public final void add(Element element) {
        DocumentModelItem modelitem;
        InputComponent input;
        int i = elements.size();
        
        if (i == table.width())
            throw new RuntimeException("Item overflow for table.");
        
        elements.put(columns[i].getName(), element);
        element.setLocale(locale);
        
        modelitem = columns[i].getModelItem();
        if (element.isDataStorable() && modelitem != null) {
            input = (InputComponent)element;
            input.setModelItem(modelitem);
            input.setVisibleLength(columns[i].getLength());
        }
    }
    
    /**
     * Retorna elemento da linha.
     * @param name nome do elemento
     * @return elemento
     */
    @SuppressWarnings("unchecked")
    public final <T extends Element> T get(String name) {
        return (T)elements.get(name);
    }
    
    /**
     * Retorna elementos da linha.
     * @return elementos
     */
    public final Element[] getElements() {
        return elements.values().toArray(new Element[0]);
    }
    
    /**
     * Retorna localização da linha.
     * @return localização.
     */
    public final Locale getLocale() {
        return locale;
    }
    
    /**
     * Retorna objeto extendido associado.
     * @return object extendido.
     */
    public final ExtendedObject getObject() {
        Element element;
        InputComponent input;
        DocumentModelItem modelitem;
        ExtendedObject object = new ExtendedObject(table.getModel());
        
        for (String name : elements.keySet()) {
            element = elements.get(name);
            
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            modelitem = input.getModelItem();
            
            if (modelitem == null)
                continue;
            
            object.setValue(modelitem, input.get());
        }
        
        return object;
    }
    
    /**
     * Retorna tabela associada.
     * @return tabela
     */
    public final Table getTable() {
        return table;
    }
    
    /**
     * Indica se a linha foi selecionada.
     * @return true, se foi selecionada.
     */
    public final boolean isSelected() {
        InputComponent input = (InputComponent)elements.get("mark");
        
        return input.isSelected();
    }
    
    /**
     * Indica se a linha é visível.
     * @return true, se a linha é visível.
     */
    public final boolean isVisible() {
        return visible;
    }
    
    /**
     * Define localização para linha.
     * @param locale localização.
     */
    public final void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    /**
     * Importa objeto extendido.
     * 
     * Preenche componentes de entrada com valores do objeto extendido.
     * 
     * @param object objeto extendido.
     */
    public final void setObject(ExtendedObject object) {
        Element element;
        Object value;
        InputComponent input;
        DocumentModelItem modelitem;
        
        for (TableColumn column : columns) {
            if (column.isMark())
                continue;
            
            element = elements.get(column.getName());
            
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            modelitem = column.getModelItem();
            if (input.getModelItem() == null)
                input.setModelItem(modelitem);
            
            if (modelitem == null)
                continue;
            
            value = object.getValue(modelitem);
            input.set(value);
        }
    }
    
    /**
     * Define seleção da linha.
     * @param selected true, para selecionar linha.
     */
    public final void setSelected(boolean selected) {
        CheckBox mark = (CheckBox)elements.get("mark");
        
        mark.setSelected(selected);
    }
    
    /**
     * Define se linha é visível.
     * @param visible true, para linha visível.
     */
    public final void setVisible(boolean visible) {
        this.visible = visible;
    }
}
