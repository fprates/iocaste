package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementa lista dropdown.
 * 
 * @author francisco.prates
 *
 */
public class ListBox extends AbstractInputComponent {
    private static final long serialVersionUID = -8869412092037011348L;
    private Map<String, Object> values;
    
    public ListBox(Container container, String name) {
        super(container, Const.LIST_BOX, Const.LIST_BOX, name);
        
        values = new LinkedHashMap<String, Object>();
        setStyleClass("list_box");
    }
    
    /**
     * Adiciona opção de escolha à lista.
     * @param name nome
     * @param value valor
     */
    public void add(String name, Object value) {
        values.put(name, value);
    }
    
    /**
     * Limpa lista de opções.
     */
    public void clear() {
        values.clear();
    }
    
    /**
     * Retorna valor da lista de opções.
     * @param name nome.
     * @return valor.
     */
    public Object get(String name) {
        return values.get(name);
    }
    
    /**
     * Retorna opções da lista.
     * @return opções
     */
    public String[] getEntriesNames() {
        return values.keySet().toArray(new String[0]);
    }
}
