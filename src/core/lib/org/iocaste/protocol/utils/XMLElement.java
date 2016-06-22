package org.iocaste.protocol.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XMLElement implements Serializable {
    private static final long serialVersionUID = 3065700337817067863L;
    private String name, head;
    private Map<String, String> attributes;
    private List<XMLElement> elements;
    private List<String> inner;
    private boolean linebreak;
    
    public XMLElement(String name) {
        this.name = name;
        elements = new ArrayList<>();
        attributes = new LinkedHashMap<>();
        inner = new ArrayList<>();
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void add(String name, Object value) {
        attributes.put(name, (value == null)? "" : value.toString());
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
    
    public final String get(String attribute) {
        return attributes.get(attribute);
    }
    
    public final List<XMLElement> getChildren() {
        return elements;
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getText() {
        StringBuilder sb = new StringBuilder();
        for (String line : inner) {
            sb.append(line);
            if (linebreak)
                sb.append("\n");
        }
        return sb.toString();
    }
    
    public final void head(String head) {
        this.head = head;
    }
    
    public final void setLineBreak(boolean linebreak) {
        this.linebreak = linebreak;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        
        if (head != null)
            sb.append(head).append("\n");
        
        sb.append("<").append(name);
        for (String name : attributes.keySet())
            sb.append(" ").append(name).append("=\"").append(
                    attributes.get(name)).append("\"");
        
        if (inner.size() == 0 && elements.size() == 0)
            return sb.append("/>\n").toString();
        
        sb.append((elements.size() == 0)? ">" : ">\n");
        
        for (XMLElement element : elements)
            sb.append(element.toString());
        
        sb.append(getText());
        sb.append("</").append(name).append(">\n");
        
        return sb.toString();
    }
}
