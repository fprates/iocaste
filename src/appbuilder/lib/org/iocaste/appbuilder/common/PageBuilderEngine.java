package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.NavControl;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.TableTool;
import org.iocaste.shell.common.TableToolData;
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
            
            context.addViewComponents(name);
            customview = new BuilderCustomView();
            customview.setViewSpec(viewspec);
            customview.setViewConfig(viewconfig);
            customview.setName(name);
            ((AbstractPage)context.function).register(name, customview);
        }
    }
}

class BuilderCustomView extends AbstractCustomView {
    private NavControl navcontrol;
    private String name;
    
    private void buildItem(PageBuilderContext context, ViewSpecItem item) {
        TableToolData ttdata;
        ViewComponents viewcomponents;
        Container container = context.view.getElement(item.getParent());
        ViewSpecItem.TYPES[] types = ViewSpecItem.TYPES.values();
        String name = item.getName();
        
        switch (types[item.getType()]) {
        case FORM:
            new Form(context.view, name);
            break;
        case PAGE_CONTROL:
            navcontrol = new NavControl((Form)container, context);
            break;
        case STANDARD_CONTAINER:
            new StandardContainer(container, name);
            break;
        case TABBED_PANE:
            new TabbedPane(container, name);
            break;
        case TABBED_PANE_ITEM:
            new TabbedPaneItem((TabbedPane)container, name);
            break;
        case TABLE_TOOL:
            ttdata = new TableToolData();
            ttdata.container = container;
            ttdata.context = context;
            ttdata.name = name;
            
            viewcomponents = context.getViewComponents(this.name);
            viewcomponents.tabletools.put(ttdata.name, new TableTool(ttdata));
            break;
        case DATA_FORM:
            new DataForm(container, name);
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
        ViewConfig viewconfig = getViewConfig();
        
        viewspec.execute();
        for (ViewSpecItem item : viewspec.getItems())
            buildItem((PageBuilderContext)context, item);
        
        viewconfig.setNavControl(navcontrol);
        viewconfig.run((PageBuilderContext)context);
    }
    
    public final void setName(String name) {
        this.name = name;
    }
}