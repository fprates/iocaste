package org.iocaste.shell;

import java.io.Serializable;

public class StyleElementProperty implements
    Comparable<StyleElementProperty>, Serializable {
    private static final long serialVersionUID = -8102777559037157034L;
    private StyleElement element;
    private int index;
    private String name;
    private String value;
    
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
    public StyleElement getStyleElement() {
        return element;
    }
    
    /**
     * 
     * @return
     */
    public String getValue() {
        return value;
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
     * @param element
     */
    public void setStyleElement(StyleElement element) {
        this.element = element;
    }
    
    /**
     * 
     * @param value
     */
    public void setValue(String value) {
        this. value = value;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(StyleElementProperty property) {
        int compare;
        
        if (equals(property))
            return 0;
        
        compare = element.compareTo(property.getStyleElement());
        if (compare != 0)
            return compare;
        
        return index - property.getIndex();
    }
    
    @Override
    public boolean equals(Object object) {
        StyleElementProperty property;
        
        if (object == this)
            return true;
        
        if (!(object instanceof StyleElementProperty))
            return false;
        
        property = (StyleElementProperty)object;
        if (!(element.equals(property.getStyleElement())))
            return false;
        
        if (index != property.getIndex())
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
