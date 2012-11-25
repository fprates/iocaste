package org.iocaste.external;

import java.util.Map;

public class CallData {
    public String url, function;
    public Map<String, Object> parameters;
    public Map<String, Map<String, String[]>> wsdl;
}
