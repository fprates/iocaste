package org.iocaste.shell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLElement {
    private String name;
    private Map<String, String> attributes;
    private List<XMLElement> elements;
    private List<String> inner;
    
    public XMLElement(String name) {
        this.name = name;
        elements = new ArrayList<XMLElement>();
        attributes = new HashMap<String, String>();
        inner = new ArrayList<String>();
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void add(String name, String value) {
        attributes.put(name, value);
    }
    
    /**
     * 
     * @param element
     */
    public final void addChild(XMLElement element) {
        elements.add(element);
    }
    
    /**
     * 
     * @param elements
     */
    public final void addChildren(Collection<XMLElement> elements) {
        this.elements.addAll(elements);
    }
    
    /**
     * 
     * @param line
     */
    public final void addInner(String line) {
        inner.add(line);
    }
    
    /**
     * 
     * @param lines
     */
    public final void addInner(Collection<String> lines) {
        inner.addAll(lines);
    }
    
    /**
     * 
     * @param lines
     */
    public final void addInner(String[] lines) {
        for (String line : lines)
            inner.add(line);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        StringBuilder sb = new StringBuilder("<").append(name);
        
        for (String name : attributes.keySet())
            sb.append(" ").append(name).append("=\"").append(
                    attributes.get(name)).append("\"");
        
        if (inner.size() == 0 && elements.size() == 0)
            return sb.append("/>\n").toString();
        else
            sb.append(">\n");
        
        for (XMLElement element : elements)
            sb.append(element.toString());
        
        for (String line : inner)
            sb.append(line).append("\n");
        
        sb.append("</").append(name).append(">\n");
        
        return sb.toString();
    }
}
