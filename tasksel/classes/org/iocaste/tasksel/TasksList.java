package org.iocaste.tasksel;

import java.util.HashSet;
import java.util.Set;

public class TasksList {
    private String name;
    private Set<TaskEntry> entries;
    
    public TasksList() {
        entries = new HashSet<TaskEntry>();
    }
    
    /**
     * 
     * @param entry
     */
    public final void add(TaskEntry entry) {
        entries.add(entry);
    }
    
    /**
     * 
     * @return
     */
    public final Set<TaskEntry> getEntries() {
        return entries;
    }
    
    /**
     * 
     * @return
     */
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @param name
     */
    public final void setName(String name) {
        this.name = name;
    }
}
