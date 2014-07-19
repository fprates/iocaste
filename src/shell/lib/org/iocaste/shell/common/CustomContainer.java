package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomContainer extends AbstractContainer {
    private static final long serialVersionUID = 7689711993523839095L;
    private String renderurl;
    private Map<String, Object> properties;
    private boolean initialized;
    private long damage;
    private Set<String> nodamage;
    
    public CustomContainer(Container container, String name) {
        super(container, Const.CUSTOM_CONTAINER, name);
        properties = new HashMap<>();
        nodamage = new HashSet<>();
    }

    @Override
    public void add(Element element) {
        super.add(element);
        initialized = true;
    }
    
    private long calculateDamage() {
        Object value;
        long damage = 0;
        
        for (String key : properties.keySet()) {
            value = properties.get(key);
            if (nodamage.contains(key) || value == null)
                continue;
            damage += (key.hashCode() + value.hashCode());
        }
        
        return damage;
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
     * Verifica dano nas propriedades do conteiner.
     * 
     * @return true, se conteiner foi atualizado
     */
    public final boolean isDamaged() {
        long damage;
        
        if (!initialized)
            return true;
        
        damage = calculateDamage();
        return (this.damage != damage);
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
    
    public final void noDamageFor(String property) {
        nodamage.add(property);
    }
    
    public final Map<String, Object> properties() {
        return properties;
    }

    /**
     * Ajusta dano nas propriedades do conteiner.
     * Este método não deve ser chamado por aplicações do usuário,
     * sob risco de não atualizar o conteúdo atual.
     */
    public final void restore() {
        damage = calculateDamage();
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
