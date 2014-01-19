package org.iocaste.shell;

import java.util.Set;
import java.util.TreeSet;

public class StyleElement implements Comparable<StyleElement> {
    private Style style;
    private int index;
    private String name;
    private Set<StyleElementProperty> properties;
    
    public StyleElement() {
        properties = new TreeSet<StyleElementProperty>();
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
     * @return
     */
    public Set<StyleElementProperty> getProperties() {
        return properties;
    }
    
    /**
     * 
     * @return
     */
    public Style getStyle() {
        return style;
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
    
    /**
     * 
     * @param properties
     */
    public void setProperties(Set<StyleElementProperty> properties) {
        this.properties = properties;
    }
    
    /**
     * 
     * @param style
     */
    public void setStyle(Style style) {
        this.style = style;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(StyleElement element) {
        int compare;
        
        if (element.equals(this))
            return 0;
        
        compare = style.compareTo(element.getStyle());
        if (compare != 0)
            return compare;
        
        return index - element.getIndex();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        StyleElement element;
        
        if (this == object)
            return true;
        
        if (!(object instanceof StyleElement))
            return false;
        
        element = (StyleElement)object;
        if (!style.equals(element.getStyle()))
            return false;
        
        if (index != element.getIndex())
            return false;
        
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Integer.toString(index).hashCode();
    }

}
