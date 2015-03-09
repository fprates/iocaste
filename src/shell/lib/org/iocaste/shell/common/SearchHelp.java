package org.iocaste.shell.common;

import java.util.LinkedHashSet;
import java.util.Set;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

/**
 * Ajuda de pesquisa
 * 
 * Define parâmetros para ajuda de pesquisa.
 * 
 * @author francisco.prates
 *
 */
public class SearchHelp extends PopupControl {
    private static final long serialVersionUID = -1582634834243087782L;
    private String modelname, export, inputname;
    private Set<String> itemnames;
    
    public SearchHelp(Container container, String name) {
        super(container, Const.SEARCH_HELP, name);
        
        itemnames = new LinkedHashSet<>();
        setAllowStacking(true);
        setStyleClass("sh_button");
        setText("?");
        setApplication("iocaste-search-help");
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
     * @param context
     * @param table
     * @param item
     * @param object
     */
    public static final void setTableItem(AbstractContext context, Table table,
            TableItem item, ExtendedObject object) {
        Parameter parameter;
        Link link;
        Element element;
        Object value;
        InputComponent input;
        DocumentModelItem modelitem;
        Component component;
        String name;
        TableColumn[] columns = table.getColumns();
        
        for (TableColumn column : columns) {
            if (column.isMark())
                continue;

            name = column.getName();
            element = item.get(name);
            if (element == null)
                throw new RuntimeException("no component defined for " +
                        "this table item");

            modelitem = column.getModelItem();
            if (!element.isDataStorable()) {
                component = (Component)element;
                value = object.get(modelitem);
                component.setText((value == null)? "" : value.toString());
                if (element.getType() != Const.LINK)
                    continue;
                
                link = (Link)element;
                parameter = column.getParameter();
                if (parameter == null) {
                    parameter = new Parameter(context.view, name);
                    column.setParameter(parameter);
                }
                
                link.add(parameter.getName(), value,
                        modelitem.getDataElement().getType());
                continue;
            }
            
            input = (InputComponent)element;
            if (input.getModelItem() == null)
                input.setModelItem(modelitem);
            
            if (modelitem == null)
                continue;
            
            value = object.get(modelitem.getName());
            input.set(value);
        }
    }
    
    @Override
    public void update(View view) { }
}
