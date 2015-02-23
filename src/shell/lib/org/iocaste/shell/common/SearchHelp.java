package org.iocaste.shell.common;

import java.util.LinkedHashSet;
import java.util.Set;

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

    @Override
    public void update(View view) { }
}
