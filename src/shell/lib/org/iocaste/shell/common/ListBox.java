package org.iocaste.shell.common;

import java.util.Map;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Implementa lista dropdown.
 * 
 * @author francisco.prates
 *
 */
public class ListBox extends ToolDataElement {
    private static final long serialVersionUID = -8869412092037011348L;
    
    public ListBox(Context viewctx, String name) {
        super(viewctx, Const.LIST_BOX, name);
    }

    public ListBox(View view, String name) {
        super(new ElementViewContext(view, null, TYPES.LISTBOX, name),
                Const.LIST_BOX, name);
    }
    
    public ListBox(Container container, String name) {
        super(new ElementViewContext(null, container, TYPES.LISTBOX, name),
                Const.LIST_BOX, name);
    }
    
    /**
     * Adiciona opção de escolha à lista.
     * @param name nome
     * @param value valor
     */
    public void add(String name, Object value) {
        tooldata.values.put(name, value);
    }
    
    /**
     * Retorna valor da lista de opções.
     * @param name nome.
     * @return valor.
     */
    public Object get(String name) {
        return tooldata.values.get(name);
    }
    
    /**
     * Retorna opções da lista.
     * @return opções
     */
    public String[] getEntriesNames() {
        return tooldata.values.keySet().toArray(new String[0]);
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Object> properties() {
        return tooldata.values;
    }
}
