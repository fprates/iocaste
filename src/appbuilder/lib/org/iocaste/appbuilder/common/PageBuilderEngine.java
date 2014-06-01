package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.NavControl;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.texteditor.common.TextEditorTool;

public class PageBuilderEngine {
    
    public PageBuilderEngine(PageBuilderContext context) {
        BuilderCustomView customview;
        Map<String, ViewSpec> viewspecs;
        ViewSpec viewspec;
        ViewConfig viewconfig;

        viewspecs = context.getViewSpecs();
        for (String name : viewspecs.keySet()) {
            viewspec = viewspecs.get(name);
            viewconfig = context.getViewConfig(name);
            
            customview = new BuilderCustomView();
            customview.setViewSpec(viewspec);
            customview.setViewConfig(viewconfig);
            ((AbstractPage)context.function).register(name, customview);
        }
    }
}

class BuilderCustomView extends AbstractCustomView {
    
    private void buildItem(PageBuilderContext context, ViewSpecItem item) {
        Container container = context.view.getElement(item.getParent());
        ViewSpecItem.TYPES[] types = ViewSpecItem.TYPES.values();
        
        switch (types[item.getType()]) {
        case FORM:
            new Form(context.view, item.getName());
            break;
        case PAGE_CONTROL:
            new NavControl((Form)container, context);
            break;
        case STANDARD_CONTAINER:
            new StandardContainer(container, item.getName());
            break;
        case TABBED_PANE:
            new TabbedPane(container, item.getName());
            break;
        case TABBED_PANE_ITEM:
            new TabbedPaneItem((TabbedPane)container, item.getName());
            break;
        case TEXT_EDITOR:
            
        }
        
        for (ViewSpecItem child : item.getItems())
            buildItem(context, child);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.CustomView#execute(
     *    org.iocaste.shell.common.AbstractContext)
     */
    @Override
    public void execute(AbstractContext context) {
        ViewSpec viewspec = getViewSpec();
        
        viewspec.execute();
        for (ViewSpecItem item : viewspec.getItems())
            buildItem((PageBuilderContext)context, item);
    }
}