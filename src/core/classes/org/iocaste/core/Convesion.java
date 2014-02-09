package org.iocaste.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.protocol.utils.ConversionRules;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Convesion {

    public static final ConversionResult execute(String xml,
            ConversionRules data) throws Exception {
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        ConversionResult map = new ConversionResult();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        
        document.normalize();
        extract(map, null, document, data);
        
        return map;
    }
    
    private static final void extract(ConversionResult map, String parent,
            Node node, ConversionRules rules) {
        short type;
        NodeList children;
        Node child;
        String name, itemname, childname;
        Map<String, String> items;
        ConversionResult itemmap;
        List<ConversionResult> itemslist;
        Object value;
        Class<?> class_;
        
        type = node.getNodeType();
        if (type == Node.TEXT_NODE) {
            value = node.getNodeValue();
            if (rules != null) {
                class_ = rules.getClass(parent);
                if (class_ !=  null)
                    switch (class_.getName()) {
                    case "boolean":
                        value = Boolean.parseBoolean((String)value);
                        break;
                    case "byte":
                        value = Byte.parseByte((String)value);
                        break;
                    case "double":
                        value = Double.parseDouble((String)value);
                        break;
                    case "float":
                        value = Float.parseFloat((String)value);
                        break;
                    case "int":
                        value = Integer.parseInt((String)value);
                        break;
                    case "long":
                        value = Long.parseLong((String)value);
                        break;
                    case "short":
                        value = Short.parseShort((String)value);
                        break;
                    default:
                        value = node.getNodeValue();
                    }
            }
                
            map.set(parent, value);
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
        if ((rules != null) && (name != null)) {
            items = rules.getItems();
            if (items.containsKey(name)) {
                itemslist = new ArrayList<>();
                map.set(name, itemslist);
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
                
                itemmap = new ConversionResult();
                extract(itemmap, name, child, rules);
                itemslist.add(itemmap);
            } else {
                extract(map, name, child, rules);
            }
        }
        
    }
}
