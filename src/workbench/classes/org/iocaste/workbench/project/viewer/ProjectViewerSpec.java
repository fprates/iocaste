package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ProjectViewerSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        tabbedpane(parent, "objects");
        
        module("data_elements", true);
        module("models", true);
        module("views", true);
        module("links", true);
        module("source", false);
    }
    
    private void module(String name, boolean detail) {
        String buttonbar = name.concat("_btbar");
        
        tabbedpaneitem("objects", name);
        standardcontainer(name, buttonbar);
        button(buttonbar, name.concat("_add"));
        button(buttonbar, name.concat("_remove"));
        tabletool(name, name.concat("_items"));
        if (detail)
            dataform(name, name.concat("_detail"));
    }
}

