package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ProjectViewerSpec extends AbstractViewSpec {
    private String view, extension;
    
    public ProjectViewerSpec() { }
    
    public ProjectViewerSpec(String view, String extension) {
        this.view = view;
        this.extension = extension;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        
        if (view != null) {
            module(view);
            if (extension != null)
                module(parent, extension);
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
        if (view == null) {
            tabbedpaneitem("objects", name);
            module(name, name);
        } else {
            module(this.parent, name);
        }
    }
    
    private void module(String parent, String name) {
        String buttonbar;
        
        buttonbar = name.concat("_btbar");
        standardcontainer(parent, buttonbar);
        button(buttonbar, name.concat("_add"));
        button(buttonbar, name.concat("_remove"));
        tabletool(parent, name.concat("_items"));
        dataform(parent, name.concat("_detail"));
    }
}

