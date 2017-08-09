package org.iocaste.runtime.common.install;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.InstallData;

public class InstallContext {
    private Map<String, ModelInstall> models;
    private Map<String, ComplexModelInstall> cmodels;
    private Map<String, DocumentModelItem> items;
    private Map<String, DataElement> elements;
    private Map<String, AbstractInstallObject> objects;
    private InstallData data;
    private String profile, authorization;
    private boolean ready;
    
    public InstallContext() {
        objects = new LinkedHashMap<>();
        elements = new HashMap<>();
        data = new InstallData();
        items = new HashMap<>();
        models = new HashMap<>();
        cmodels = new HashMap<>();
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends AbstractInstallObject> T get(String name) {
        return (T)objects.get(name);
    }
    
    public final String getAuthorization() {
        return authorization;
    }
    
    public final ComplexModelInstall getCModel(String name) {
        return cmodels.get(name);
    }
    
    public final Map<String, DataElement> getElements() {
        return elements;
    }
    
    public final InstallData getInstallData() {
        return data;
    }
    
    public final DocumentModelItem getItem(String name) {
        return items.get(name);
    }
    
    public final ModelInstall getModel(String name) {
        return models.get(name);
    }
    
    public final Map<String, AbstractInstallObject> getObjects() {
        return objects;
    }
    
    public final String getProfile() {
        return profile;
    }
    
    public final boolean isReady() {
        return ready;
    }
    
    public final void put(String name, AbstractInstallObject object) {
        objects.put(name, object);
    }
    
    public final void put(String name, DataElement element) {
        if (elements.containsKey(name))
            throw new RuntimeException(new StringBuilder("dataelement ").
                    append(name).
                    append(" has already defined.").toString());
        elements.put(name, element);
    }
    
    public final void ready() {
        ready = true;
    }
    
    public final void set(String name, ComplexModelInstall cmodel) {
        cmodels.put(name, cmodel);
    }
    
    public final void setAuthorization(String name) {
        authorization = name;
    }
    
    public final void setItem(String name, DocumentModelItem item) {
        items.put(name, item);
    }
    
    public final void setModel(String name, ModelInstall model) {
        models.put(name, model);
    }
    
    public final void setProfile(String name) {
        profile = name;
    }
}
