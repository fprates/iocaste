package org.iocaste.tasksel;

public class TaskEntry {
    private String name, text;
    
    /**
     * 
     * @return
     */
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @return
     */
    public final String getText() {
        return text;
    }
    
    /**
     * 
     * @param name
     */
    public final void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @param text
     */
    public final void setText(String text) {
        this.text = text;
    }
}
