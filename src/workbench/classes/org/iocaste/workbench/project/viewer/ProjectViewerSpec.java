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
        String itemsframe, detailframe;
        
        itemsframe = name.concat("_items_frame");
        frame(parent, itemsframe);
        button(itemsframe, name.concat("_remove"));
        tabletool(itemsframe, name.concat("_items"));
        
        detailframe = name.concat("_detail_frame");
        frame(parent, detailframe);
        button(detailframe, name.concat("_add"));
        dataform(detailframe, name.concat("_detail"));
    }
}

