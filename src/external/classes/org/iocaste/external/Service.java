package org.iocaste.external;

import java.util.Set;

public class Service {
    private ServiceData servicedata;
    
    public Service(ServiceData servicedata) {
        this.servicedata = servicedata;
    }
    
    public final String getAction(String operation) {
        return new StringBuilder(servicedata.namespace).
                append("/").append(operation).toString();
    }
    
    public final String getName() {
        return servicedata.name;
    }
    
    public final String getNamespace() {
        return servicedata.namespace;
    }
    
    public final Port getPort(String name) {
        return servicedata.ports.get(name);
    }
    
    public final Set<String> getPortsKeys() {
        return servicedata.ports.keySet();
    }
    
    public final Operation getOperation(String port, String name) {
        return servicedata.ports.get(port).operations.get(name);
    }
    
    public final Type getType(String name) {
        return servicedata.types.get(name);
    }
    
    public final Set<String> getTypesKeys() {
        return servicedata.types.keySet();
    }
}
