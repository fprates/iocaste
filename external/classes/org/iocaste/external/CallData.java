package org.iocaste.external;

import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;

public class CallData {
    public String url, function, port;
    public Map<String, Object> parameters;
    public Map<String, Map<String, ExtendedObject[]>> wsdl;
    public Service service;
    public ExtendedObject parameter;
}
