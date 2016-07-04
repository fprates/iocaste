package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ProjectViewerSpec extends AbstractViewSpec {
    private String view;
    
    public ProjectViewerSpec() {
        this(null);
    }
    
    public ProjectViewerSpec(String view) {
        this.view = view;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        
        if (view != null) {
            module(view, true);
            return;
        }

        tabbedpane(parent, "objects");
        module("data_elements", true);
        module("models", true);
        module("views", true);
        module("links", true);
        module("source", false);
    }
    
    private void module(String name, boolean detail) {
        String buttonbar, parent;
        
        if (view == null) {
            buttonbar = name.concat("_btbar");
            tabbedpaneitem("objects", name);
            standardcontainer(name, buttonbar);
            button(buttonbar, name.concat("_add"));
            button(buttonbar, name.concat("_remove"));
            parent = name;
        } else {
            parent = this.parent;
        }
        
        tabletool(parent, name.concat("_items"));
        if (detail)
            dataform(parent, name.concat("_detail"));
    }
}

