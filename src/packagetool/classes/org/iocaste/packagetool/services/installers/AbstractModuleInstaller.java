package org.iocaste.packagetool.services.installers;

import java.util.Collection;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.services.Registry;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;

public abstract class AbstractModuleInstaller<S,T> implements ModuleInstaller {
    private String type;
    
    public AbstractModuleInstaller(Services services, String type) {
        services.installers.put(type, this);
        this.type = type;
    }
    
    protected abstract String getObjectName(S key, T object);

    protected final String getObjectName(ExtendedObject object) {
        return object.getst("NAME");
    }
    
    protected abstract void install(State state, S key, T object);
    
    protected void installAll(State state, T[] objects) {
        if (!isValid(objects))
            return;
        for (T object : objects)
            install(state, null, object);
        for (T object : objects)
            registry(state, null, object);
    }
    
    protected void installAll(State state, Collection<T> objects) {
        if (!isValid(objects))
            return;
        for (T object : objects)
            install(state, null, object);
        for (T object : objects)
            registry(state, null, object);
    }

    protected void installAll(State state, Map<S, T> objects) throws Exception {
        if (!isValid(objects))
            return;
        for (S key : objects.keySet())
            install(state, key, objects.get(key));
        for (S key : objects.keySet())
            registry(state, key, objects.get(key));
    }
    
    protected final boolean isValid(T[] objects) {
        return !(objects.length == 0);
    }
    
    protected final boolean isValid(Collection<T> objects) {
        return !(objects.size() == 0);
    }
    
    protected final boolean isValid(Map<S, T> objects) {
        return !(objects.size() == 0);
    }
    
    protected final void registry(State state, S key, T object) {
        if (type != null)
            Registry.add(getObjectName(key, object), type, state);
    }
    
    protected abstract void update(State state, S key, T object)
            throws Exception;
    
    protected void updateAll(State state, Map<S, T> objects) throws Exception {
        if (!isValid(objects))
            return;
        for (S key : objects.keySet())
            update(state, key, objects.get(key));
        for (S key : objects.keySet())
            registry(state, key, objects.get(key));
    }
    
    protected void updateAll(State state, Collection<T> objects)
            throws Exception {
        if (!isValid(objects))
            return;
        for (T object : objects)
            update(state, null, object);
        for (T object : objects)
            registry(state, null, object);
    }
}

