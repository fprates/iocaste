package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class ProjectView extends AbstractViewSpec {
    public int count;
    public Map<String, ProjectTreeItem> treeitems;
    public String name;
    
    public ProjectView(String name) {
        treeitems = new HashMap<>();
        this.name = name;
    }
    
    public final void add(String type, String container, String name) {
        count++;
        switch (type) {
        case "form":
            form(name);
            break;
        case "navcontrol":
            navcontrol(container, name);
            break;
        case "dataform":
            dataform(container, name);
            break;
        }
    }
    
    @Override
    protected void execute() {
        // TODO Stub de método gerado automaticamente
        
    }
}