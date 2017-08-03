package org.iocaste.kernel.runtime.shell.tilestool;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.AbstractComponentTool;
import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ProcessOutput;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.application.Context;

public class TilesTool extends AbstractComponentTool {
    
    /**
     * 
     * @param context
     * @param data
     */
    public TilesTool(ViewContext viewctx, ComponentEntry entry) {
        super(viewctx, entry);
    }
    
    @Override
    public final void load() {
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
//    	ProcessOutput processoutput;
//        String linkname, tilename;
//        Tile tile;
//        ViewSpecItem itemspec, tilesspec;
//        ViewContext viewctx;
//        Link link;
//        AbstractPageBuilder function;
//        Shell shell;
//        TilesData data = (TilesData)entry.data;
//        
//        viewctx = data.context.getView(view.page);
//        tilesspec = viewctx.getSpec().get(entry.data.name);
//        processoutput = shell.get("process_output");
//        
//        if (data.style != null)
//            getElement(tilesspec.getName()).setStyleClass(data.style);
//        
//        if (data.objects == null)
//            return;
//        
//        if (data.action) {
//            itemspec = null;
//            function = (AbstractPageBuilder)data.context.function;
//        } else {
//            itemspec = tilesspec;
//            function = null;
//        }
//        
//        shell = getFunction();
//        tiles = shell.factories.get(TYPES.TILES).instance(data.name);
//        for (int i = 0; i < data.objects.length; i++) {
//            tile = new Tile(data.name, i);
//            tile.set(data.objects[i]);
//            if (data.action)
//                itemspec = tile.specItemInstance();
//            tiles.objectset(data.name, tile.get());
//            tilename = tile.getName();
//            tiles.parentput(tilename, data.name);
//            processoutput.run(itemspec, tilename, data.action);
//            if (!data.action)
//                continue;
//            linkname = Tile.getLinkName(tile);
//            link = getElement(linkname);
//            link.setText("");
//            link.setStyleClass("nc_tiles_link");
//            viewctx.put(linkname, new TilesAction(tiles.actionget(data.name)));
//            function.register(pagename, linkname, view);
//        }
    }
}

//class TilesAction extends AbstractActionHandler<Context> {
//    private String action;
//    
//    public TilesAction(String action) {
//        this.action = action;
//    }
//    
//    @Override
//    protected void execute(Context context) throws Exception {
////        execute(action);
//    }
//    
//}
