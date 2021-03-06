package org.iocaste.shell.common;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.WhereClause;
import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;

/**
 * Ajuda de pesquisa
 * 
 * Define parâmetros para ajuda de pesquisa.
 * 
 * @author francisco.prates
 *
 */
public class SearchHelp extends ToolDataElement {
    private static final long serialVersionUID = -1582634834243087782L;
    private String modelname, export, inputname, child;
    private Set<String> itemnames;
    private List<WhereClause> criteria;
    
    public SearchHelp(Container container, String name) {
        this(new ElementViewContext(null, container, null, name), name);
    }

    public SearchHelp(Context context, String name) {
        super(context, Const.SEARCH_HELP, name);
        itemnames = new LinkedHashSet<>();
        setAllowStacking(true);
    }
    
    /**
     * Adiciona coluna.
     * @param itemname item do modelo
     */
    public final void addModelItemName(String itemname) {
        itemnames.add(itemname);
    }
    
    /**
     * Indica se a coluna existe.
     * @param name nome da coluna
     * @return true, se a coluna existe.
     */
    public final boolean contains(String name) {
        return itemnames.contains(name);
    }
    
    /**
     * 
     * @return
     */
    public final String getChild() {
        return child;
    }
    
    /**
     * 
     * @return
     */
    public List<WhereClause> getCriteria() {
        return criteria;
    }
    
    /**
     * Retorna coluna com valor exportado.
     * @return coluna.
     */
    public final String getExport() {
        return export;
    }
    
    /**
     * 
     * @return
     */
    public final String[] getItens() {
        return itemnames.toArray(new String[0]);
    }
    
    /**
     * Retorna componente associado.
     * @return nome do componente.
     */
    public final String getInputName() {
        return inputname;
    }
    
    /**
     * Retorna modelo associado.
     * @return nome do modelo.
     */
    public final String getModelName() {
        return modelname;
    }
    
    @Override
    public boolean isControlComponent() {
        return true;
    }
    
    @Override
    public final boolean isPopup() {
        return true;
    }
    
    @Override
    public final boolean isReady(PopupControl popupcontrol) {
        String shname = getHtmlName();
        SearchHelp search = (SearchHelp)popupcontrol;
        return (shname.equals(search.getHtmlName()) ||
                shname.equals(search.getChild()));
    }
    
    /**
     * 
     * @param child
     */
    public final void setChild(String child) {
        this.child = child;
    }
    
    /**
     * 
     * @param criteria
     */
    public final void setCriteria(List<WhereClause> criteria) {
        this.criteria = criteria;
    }
    
    /**
     * Define item do modelo a ser exportado.
     * @param export item do modelo.
     */
    public final void setExport(String export) {
        this.export = export;
    }
    
    /**
     * Define campo de entrada associado.
     * @param inputname nome do campo.
     */
    public final void setInputName(String inputname) {
        this.inputname = inputname;
    }
    
    /**
     * Define modelo de dados.
     * @param modelname nome do modelo.
     */
    public final void setModelName(String modelname) {
        this.modelname = modelname;
    }
    
    /**
     * 
     * @param table
     * @param item
     * @param object
     */
    public static final void setTableItem(
            Table table, TableItem item, ExtendedObject object) {
        Link link;
        Element element;
        Object value;
        InputComponent input;
        DocumentModelItem modelitem;
        Component component;
        String name;
        Map<String, LinkEntry> parameters;
        TableColumn[] columns = table.getColumns();
        
        for (TableColumn column : columns) {
            if (column.isMark())
                continue;

            name = column.getName();
            element = item.get(name);
            if (element == null)
                throw new RuntimeException(
                        "no component defined for this table item.");

            modelitem = column.getModelItem();
            if (!element.isDataStorable()) {
                component = (Component)element;
                value = (object == null)? null :
                    object.get(modelitem.getName());
                if (!column.isValueLocked())
                    component.setText((value == null)? "" : value.toString());
                if (element.getType() != Const.LINK)
                    continue;
                
                link = (Link)element;
                parameters = link.getParametersMap();
                if (parameters.size() == 0) {
                    link.add(name, value, modelitem.getDataElement().getType());
                } else {
                    for (String key : parameters.keySet())
                        parameters.get(key).value = value;
                }
                
                continue;
            }
            
            input = (InputComponent)element;
            if (input.getModelItem() == null)
                input.setModelItem(modelitem);
            
            if (modelitem == null)
                continue;
            
            if (object == null) {
                input.set(null);
                continue;
            }
            
            if (column.isNamespace())
                value = object.getNS();
            else
                value = object.get(modelitem.getName());
            
            input.set(value);
        }
    }
    
    @Override
    public void update(String action, Object value) {
        ((InputComponent)view.getElement(inputname)).set(value);
    }
}
