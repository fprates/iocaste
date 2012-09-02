package org.iocaste.packagetool.common;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class TaskGroup implements Serializable {
    private static final long serialVersionUID = -5449992441143136737L;
    private String name;
    private Set<String> links;
    
    public TaskGroup(String name) {
        this.name = name;
        links = new TreeSet<String>();
    }
    
    /**
     * 
     * @param link
     */
    public final void add(String link) {
        links.add(link);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object object) {
        TaskGroup taskgroup;
        
        if (object == this)
            return true;
        
        if (!(object instanceof TaskGroup))
            return false;
        
        taskgroup = (TaskGroup)object;
        return name.equals(taskgroup.getName());
    }
    
    /**
     * 
     * @return
     */
    public final Set<String> getLinks() {
        return links;
    }
    
    /**
     * 
     * @return
     */
    public final String getName() {
        return name;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return name.hashCode();
    }
}
