package org.iocaste.runtime.common.style;

import java.util.Map;

/**
 * @author francisco.prates
 *
 */
public class Media {
    private String rule;
    
    public Media(Map<String, Media> medias, String name) {
        medias.put(name, this);
    }
    
    public final String getRule() {
        return rule;
    }
    
    public final void setRule(String rule) {
        this.rule = rule;
    }
    
}
