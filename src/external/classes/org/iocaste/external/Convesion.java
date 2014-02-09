package org.iocaste.external;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Convesion {

    public static final Map<String, Object> execute(String xml) 
            throws Exception {
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Map<String, Object> map = new LinkedHashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
//        NodeList nodes = document.getDocumentElement().getChildNodes();
        document.normalize();
        extract(map, null, document);
        
        return map;
    }
    
    private static final void extract(Map<String, Object> map, String parent,
            Node node) {
        short type;
        NodeList children;
        Node child;
        String name;
        
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
        
        children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            child = children.item(i);
            extract(map, name, child);
        }
        
    }
}
