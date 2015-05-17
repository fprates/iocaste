package org.iocaste.shell;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.StyleSheet;

public class StyleServices {

    public static final void remove(Function function, String name) {
        Query[] queries = new Query[3];
        Documents documents = new Documents(function);
        
        queries[0] = new Query("delete");
        queries[0].setModel("STYLE_ELEMENT_DETAIL");
        queries[0].andEqual("STYLE", name);
        
        queries[1] = new Query("delete");
        queries[1].setModel("STYLE_ELEMENT");
        queries[1].andEqual("STYLE", name);
        
        queries[2] = new Query("delete");
        queries[2].setModel("STYLE");
        queries[2].andEqual("NAME", name);
        documents.update(queries);
    }
    
    public static final void save(
            Function function, String name, StyleSheet stylesheet) {
        Query[] queries;
        ExtendedObject object;
        Map<String, String> properties;
        Map<String, Map<String, String>> elements;
        DocumentModel elementmodel, propertymodel;
        int i, j;
        String eindex, pindex;
        Documents documents = new Documents(function);
        
        object = new ExtendedObject(documents.getModel("STYLE"));
        object.set("NAME", name);
        documents.save(object);
        
        queries = new Query[2];
        
        queries[0] = new Query("delete");
        queries[0].setModel("STYLE_ELEMENT_DETAIL");
        queries[0].andEqual("STYLE", name);
        queries[1] = new Query("delete");
        queries[1].setModel("STYLE_ELEMENT");
        queries[1].andEqual("STYLE", name);
        documents.update(queries);
        
        elementmodel = documents.getModel("STYLE_ELEMENT");
        propertymodel = documents.getModel("STYLE_ELEMENT_DETAIL");
        elements = stylesheet.getElements();
        i = 0;
        for (String element : elements.keySet()) {
            eindex = String.format("%s%03d", name, i++);
            object = new ExtendedObject(elementmodel);
            object.set("INDEX", eindex);
            object.set("NAME", element);
            object.set("STYLE", name);
            documents.save(object);
            
            j = 0;
            properties = elements.get(element);
            for (String key : properties.keySet()) {
                pindex = String.format("%s%03d", eindex, j++);
                object = new ExtendedObject(propertymodel);
                object.set("INDEX", pindex);
                object.set("ELEMENT", eindex);
                object.set("STYLE", name);
                object.set("PROPERTY", key);
                object.set("VALUE", properties.get(key));
                documents.save(object);
            }
        }
    }
}
