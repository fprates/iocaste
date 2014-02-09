package org.iocaste.external;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.iocaste.external.service.ConversionData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Convesion {

    public static final Map<String, Object> execute(String xml,
            ConversionData data) throws Exception {
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Map<String, Object> map = new LinkedHashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
//        NodeList nodes = document.getDocumentElement().getChildNodes();
        document.normalize();
        extract(map, null, document, data);
        
        return map;
    }
    
    private static final void extract(Map<String, Object> map, String parent,
            Node node, ConversionData data) {
        short type;
        NodeList children;
        Node child;
        String name, itemname, childname;
        Map<String, String> items;
        Map<String, Object> itemmap;
        List<Map<String, Object>> itemslist;
        
        type = node.getNodeType();
        if (type == Node.TEXT_NODE) {
            map.put(parent, node.getNodeValue());
            return;
        }
        
        if (type == Node.DOCUMENT_NODE)
            name = parent;
        else
            name = (parent == null)? node.getNodeName() :
                new StringBuilder(parent).
                    append(".").
                    append(node.getNodeName()).toString();
        
        itemname = null;
        items = null;
        itemslist = null;
        if ((data != null) && (name != null)) {
            items = data.getItems();
            if (items.containsKey(name)) {
                itemslist = new ArrayList<>();
                map.put(name, itemslist);
                itemname = items.get(name);
            }
        }
        
        children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            child = children.item(i);
            childname = child.getNodeName();
            if (itemslist != null) {
                if (!childname.equals(itemname))
                    continue;
                
                itemmap = new LinkedHashMap<>();
                extract(itemmap, name, child, data);
                itemslist.add(itemmap);
            } else {
                extract(map, name, child, data);
            }
        }
        
    }
}
