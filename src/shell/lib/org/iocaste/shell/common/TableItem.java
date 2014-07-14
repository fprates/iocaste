package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
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
public class TableItem implements Serializable {
    private static final long serialVersionUID = -1076760582954115701L;
    private Map<String, Element> elements;
    private TableColumn[] columns;
    private Table table;
    private Locale locale;
    private boolean visible;
    private int index;
    
    public TableItem(Table table) {
        this(table, -1);
    }
    
    public TableItem(Table table, int pos) {
        InputComponent input;
        String markname;
        
        if (pos < 0)
            table.add(this);
        else
            table.add(this, pos);
        
        this.table = table;
        columns = table.getColumns();
        elements = new LinkedHashMap<>();
        visible = true;
        index = table.length() - 1;
        
        markname = new StringBuilder(table.getName()).append(".").
                append(index).append(".mark").toString();
        switch (table.getSelectionType()) {
        case Table.SINGLE:
            input = new RadioButton(table.getView(), markname, table.getGroup());
            break;
            
        case Table.MULTIPLE:
            input = new CheckBox(table.getView(), markname);
            break;
        default:
            input = null;
        }
        
        if (input == null)
            return;

        elements.put("mark", input);
    }
    
    /**
     * Adiciona elemento para linha.
     * @param element elemento
     */
    public final void add(Element element) {
        String htmlname;
        DocumentModelItem modelitem;
        InputComponent input;
        int i = elements.size();
        
        if (i == table.width())
            throw new RuntimeException("item overflow for table.");

        htmlname = new StringBuilder(table.getName()).
                append(index).append(element.getName()).toString();
        element.setView(table.getView());
        element.setHtmlName(htmlname);
        element.setLocale(locale);
        elements.put(columns[i].getName(), element);
        
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
        DocumentModel model = table.getModel();
        ExtendedObject object = new ExtendedObject(model);
        
        for (String name : elements.keySet()) {
            element = elements.get(name);
            
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
    
    public final void setEnabled(boolean enabled) {
        for (Element element : elements.values())
            element.setEnabled(enabled);
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
        Parameter parameter;
        Link link;
        Element element;
        Object value;
        InputComponent input;
        DocumentModelItem modelitem;
        Component component;
        String name;
        
        for (TableColumn column : columns) {
            if (column.isMark())
                continue;
            
            name = column.getName();
            element = elements.get(name);
            if (element == null)
                throw new RuntimeException("no component defined for " +
                		"this table item");
            
            if (!element.isDataStorable()) {
                component = (Component)element;
                value = object.get(column.getModelItem());
                component.setText((value == null)? "" : value.toString());
                if (element.getType() == Const.LINK) {
                    link = (Link)element;
                    parameter = column.getParameter();
                    if (parameter == null) {
                        parameter = new Parameter(table.getContainer(), name);
                        column.setParameter(parameter);
                    }
                    
                    link.add(parameter.getName(), value);
                }
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
