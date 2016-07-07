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
            if (extension != null) {
                tabbedpane(parent, "objects");
                module(view, true);
                module(extension, true);
            } else {
                module(view, false);
            }
            return;
        }

        tabbedpane(parent, "objects");
        module("data_elements", true);
        module("models", true);
        module("views", true);
        module("links", true);
        module("packages", true);
    }
    
    private void module(String name, boolean tab) {
        if (tab) {
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

