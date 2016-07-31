package org.iocaste.appbuilder.common.tiles;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.BuilderCustomView;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.Link;

public class TilesTool extends AbstractComponentTool {
    
    /**
     * 
     * @param context
     * @param data
     */
    public TilesTool(ComponentEntry entry) {
        super(entry);
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
        BuilderCustomView builder;
        ViewSpecItem itemspec, tilesspec;
        ViewContext view;
        Link link;
        AbstractPageBuilder function;
        ExtendedContext extcontext;
        TilesData data = (TilesData)entry.data;
        
        pagename = data.context.view.getPageName();
        view = data.context.getView();
        tilesspec = view.getSpec().get(entry.data.name);
        builder = new BuilderCustomView();
        builder.setView(pagename);
        builder.setViewSpec(data.spec);
        builder.setViewConfig(data.config);
        builder.setViewInput(data.input);
        
        if (data.style != null)
            data.context.view.getElement(tilesspec.getName()).
                    setStyleClass(data.style);
        
        if (data.objects == null)
            return;
        
        if (data.action) {
            itemspec = null;
            function = (AbstractPageBuilder)data.context.function;
        } else {
            itemspec = tilesspec;
            function = null;
        }
        
        extcontext = view.getExtendedContext();
        for (int i = 0; i < data.objects.length; i++) {
            if (data.input != null)
                data.input.set(data.objects[i]);
            tile = new Tile(data.name, i);
            tile.set(data.objects[i]);
            if (data.action)
                itemspec = tile.specItemInstance();
            if (extcontext != null)
                extcontext.set(tile);
            builder.execute(
                    data.context, itemspec, tile.getName(), data.action);
            if (!data.action)
                continue;
            linkname = Tile.getLinkName(tile);
            link = data.context.view.getElement(linkname);
            link.setText("");
            link.setStyleClass("nc_tiles_link");
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
