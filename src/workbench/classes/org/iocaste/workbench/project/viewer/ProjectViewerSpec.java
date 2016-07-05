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
            module(view);
            return;
        }

        tabbedpane(parent, "objects");
        module("data_elements");
        module("models");
        module("views");
        module("links");
        module("packages");
    }
    
    private void module(String name) {
        String buttonbar, parent;
        
        if (view == null) {
            tabbedpaneitem("objects", name);
            parent = name;
        } else {
            parent = this.parent;
        }

        buttonbar = name.concat("_btbar");
        standardcontainer(parent, buttonbar);
        button(buttonbar, name.concat("_add"));
        button(buttonbar, name.concat("_remove"));
        tabletool(parent, name.concat("_items"));
        dataform(parent, name.concat("_detail"));
    }
}

