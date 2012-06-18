package org.iocaste.shell.common;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Implementação de controle de página.
 * 
 * O componente é renderizado apenas uma vez por página.
 * Abriga barra de links, título da página e barra de mensagens.
 * 
 * @author francisco.prates
 *
 */
public class PageControl extends AbstractContainer {
    private static final long serialVersionUID = 462408931862107155L;
    public static final boolean EXTERNAL = true;
    public static final boolean NATIVE = false;
    private Set<String> extern, actions;
    
    public PageControl(Form form) {
        super(form, Const.PAGE_CONTROL, "navbar");
        actions = new LinkedHashSet<String>();
        extern = new HashSet<String>();
        setStyleClass("header");
    }
    
    /**
     * Adiciona link de ação.
     * @param action nome da ação.
     */
    public final void add(String action) {
        add(action, false);
    }
    
    /**
     * Adiciona link de ação, e ajusta contexto da ação.
     * @param action nome da ação.
     * @param extern true, se a ação chama outra aplicação.
     */
    public final void add(String action, boolean extern) {
        actions.add(action);
        if (extern)
            this.extern.add(action);
    }
    
    /**
     * Retorna ações do controle.
     * @return nomes das ações.
     */
    public final String[] getActions() {
        return actions.toArray(new String[0]);
    }

    /**
     * Indica se a ação especificada é externa.
     * @param name nome da ação.
     * @return true, se a ação é externa.
     */
    public final boolean isExternal(String name) {
        return extern.contains(name);
    }
}
