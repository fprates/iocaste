package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

public class CustomContainer extends AbstractContainer {
    private static final long serialVersionUID = 7689711993523839095L;
    private String renderurl;
    private Map<String, Object> properties;
    private boolean initialized;
    private int damage;
    
    public CustomContainer(Container container, String name) {
        super(container, Const.CUSTOM_CONTAINER, name);
        properties = new HashMap<>();
    }

    @Override
    public void add(Element element) {
        super.add(element);
        initialized = true;
    }
    
    @SuppressWarnings("unchecked")
    public final <T> T get(String key) {
        return (T)properties.get(key);
    }
    
    public final byte getb(String key) {
        return get(key);
    }
    
    public final boolean getbl(String key) {
        return get(key);
    }
    
    public final int geti(String key) {
        return get(key);
    }
    
    public final String getRenderURL() {
        return renderurl;
    }
    
    public final String getst(String key) {
        return get(key);
    }

    /**
     * Verifica e ajusta dano nas propriedades do conteiner.
     * Este método não deve ser chamado por aplicações do usuário,
     * sob risco de não atualizar o conteúdo atual.
     * 
     * @return true, se conteiner foi atualizado
     */
    public final boolean isDamaged() {
        int damage = properties.toString().hashCode();
        if (this.damage == damage)
            return false;
        this.damage = damage;
        return true;
    }
    
    public final boolean isInitialized() {
        return initialized;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractElement#isRemote()
     */
    @Override
    public final boolean isRemote() {
        return true;
    }
    
    public final Map<String, Object> properties() {
        return properties;
    }
    
    public final void set(String key, Object value) {
        properties.put(key, value);
    }
    
    public final void set(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    public final void setRenderURL(String url) {
        renderurl = url;
    }
}
