package org.iocaste.appbuilder.common.tiles;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.BuilderCustomView;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.CustomView;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StyleSheet;

public class TilesTool extends AbstractComponentTool {
    
    /**
     * 
     * @param context
     * @param data
     */
    public TilesTool(ComponentEntry entry) {
        super(entry);
    }
    
    private final void configStyleSheet(PageBuilderContext context) {
        Map<String, String> style;
        StyleSheet stylesheet = context.view.styleSheetInstance();
        
        style = stylesheet.newElement("._tiles_link");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("text-decoration", "none");
    }
    
    @Override
    public final void load(AbstractComponentData componentdata) {
//        TilesData data = (TilesData)componentdata;
//        
//        for (Tile tile : tiles) {
//            
//        }
    }

    @Override
    public void refresh() {
        
    }

    @Override
    public void run() {
        String linkname, pagename;
        Tile tile;
        CustomView builder;
        ViewSpecItem itemspec, tilesspec;
        Object[] objects;
        ViewContext view;
        Link link;
        AbstractPageBuilder function;
        TilesData data = (TilesData)entry.data;
        
        pagename = data.context.view.getPageName();
        view = data.context.getView();
        tilesspec = view.getSpec().get(entry.data.name);
        builder = new BuilderCustomView();
        builder.setView(pagename);
        builder.setViewSpec(data.spec);
        builder.setViewConfig(data.config);
        builder.setViewInput(data.input);
        
        objects = data.get();
        if (objects == null)
            return;
        
        if (data.action) {
            itemspec = null;
            function = (AbstractPageBuilder)data.context.function;
            configStyleSheet(data.context);
        } else {
            itemspec = tilesspec;
            function = null;
        }
        
        for (int i = 0; i < objects.length; i++) {
            if (data.input != null)
                data.input.set(objects[i]);
            tile = new Tile(data.name, i);
            tile.set(objects[i]);
            if (data.action)
                itemspec = tile.specItemInstance();
            builder.execute(data.context, itemspec, tile.getName());
            if (!data.action)
                continue;
            linkname = Tile.getLinkName(tile);
            link = data.context.view.getElement(linkname);
            link.setText("");
            link.setStyleClass("_tiles_link");
            view.put(linkname, new TilesAction(data.input.getAction()));
            function.register(pagename, linkname, view);
        }
    }
}

class TilesAction extends AbstractActionHandler {
    private String action;
    
    public TilesAction(String action) {
        this.action = action;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        execute(action);
    }
    
}