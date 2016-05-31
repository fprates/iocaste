package org.iocaste.shell.common;

import java.util.Map;

/**
 * @author francisco.prates
 *
 */
public class Media {
    private String device, feature;
    
    public Media(Map<String, Media> medias, String name) {
        medias.put(name, this);
    }
    
    public final String getDevice() {
        return device;
    }

    public final String getFeature() {
        return feature;
    }
    
    public final void setDevice(String device) {
        this.device = device;
    }

    public final void setFeature(String feature) {
        this.feature = feature;
    }
    
    
}
