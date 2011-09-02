package org.iocaste.shell;

import java.util.Set;
import java.util.TreeSet;

public class Style implements Comparable<Style> {
    private String name;
    private int index;
    private Set<StyleElement> elements;
    
    public Style() {
        elements = new TreeSet<StyleElement>();
    }
    
    public Set<StyleElement> getElements() {
        return elements;
    }
    
    /**
     * 
     * @return
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param elements
     */
    public void setElements(Set<StyleElement> elements) {
        this.elements = elements;
    }
    
    /**
     * 
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }
    
    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Style style) {
        if (style == this)
            return 0;
        
        return name.compareTo(style.getName()); 
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        Style style;
        
        if ((object == null) || !(object == this))
            return false;
        
        style = (Style)object;
        
        if (name.equals(style.getName()))
            return true;
        
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
