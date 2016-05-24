package org.quantic.iocasteconnector;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;

import com.sap.conn.jco.JCoParameterList;

public class Context {
    public Map<String, Object> result;
    public Map<String, JCoParameterList> lists;
    public Map<String, ExtendedObject> items;
    public Map<String, ComplexDocument> structures;
    
    public Context() {
        lists = new HashMap<>();
        result = new HashMap<>();
    }
}
