package org.iocaste.workbench;

import java.util.ArrayList;
import java.util.List;

public class ProjectTreeItem {
    public String name, link, text, container;
    public int type;
    public List<ProjectTreeItem> items;
    
    public ProjectTreeItem() {
        items = new ArrayList<>();
    }
}
