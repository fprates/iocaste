package org.iocaste.shell.common;

/**
 * Interface para componente de controle (ex. botões)
 * 
 * A principal implementação é AbstractControlComponent.
 * 
 * @author francisco.prates
 *
 */
public interface ControlComponent extends Element {

    public abstract boolean allowStacking();
    
    /**
     * Retorna nome da ação
     * @return ação.
     */
    public abstract String getAction();
    
    /**
     * 
     * @return
     */
    public abstract String getApplication();
    
    /**
     * Indica se componente é cancelável.
     * Componentes canceláveis não disparam validação da tela.
     * @return true, se for cancelável.
     */
    public abstract boolean isCancellable();
    
    /**
     * Define uma ação, para quando ação for diferente do nome do componente.
     * @param action nome da ação.
     */
    public abstract void setAction(String action);
    
    /**
     * 
     * @param stacking
     */
    public abstract void setAllowStacking(boolean stacking);
    
    /**
     * 
     * @param application
     */
    public abstract void setApplication(String application);
    
    /**
     * Define se componente é cancelável.
     * Componentes canceláveis não disparam validação da tela.
     * @param cancellable true, para cancelável.
     */
    public abstract void setCancellable(boolean cancellable);
}
