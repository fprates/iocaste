package org.iocaste.kernel.runtime.shell.tilestool;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.AbstractComponentTool;
import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ProcessOutput;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.elements.StandardContainer;
import org.iocaste.protocol.IocasteException;
import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.page.ViewSpecItem;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;

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
    public final void load() { }

    @Override
    public void refresh() { }

    @Override
    public void run() throws Exception {
        String linkname, pagename, tilename;
        Tile tile;
        ViewContext subpagectx;
        ViewSpecItem itemspec, tilesspec;
        ViewExport viewexport;
        Link link;
        ProcessOutput output;
        Container container;
//        
//        pagename = data.context.view.getPageName();
//        view = data.context.getView(pagename);
//        tilesspec = view.getSpec().get(entry.data.name);
//        builder = new BuilderCustomView();
//        builder.setView(pagename);
//        builder.setViewSpec(data.spec);
//        builder.setViewConfig(data.config);
//        builder.setViewInput(data.input);
//        
//        if (entry.data.style != null)
//            entry.data.context.view.getElement(tilesspec.getName()).
//                    setStyleClass(data.style);
//        
        if (entry.data.objects == null)
            return;
        
//        if (entry.data.action) {
//            itemspec = null;
//            function = (AbstractPageBuilder)data.context.function;
//        } else {
//            itemspec = tilesspec;
//            function = null;
//        }
//        
//        extcontext = view.getExtendedContext();
//        if (extcontext == null)
//            throw new IocasteException(
//                    "tiles %s demand valid ExtendedContext", data.name);
//        
//        extcontext.tilesInstance(data.name);

        container = new StandardContainer(viewctx, entry.data.name);
        setHtmlName(container.getHtmlName());
        output = viewctx.function.get("output_process");
        viewexport = viewctx.subpages.get(entry.data.subpage);
        for (int key : entry.data.objects.keySet()) {
            tile = new Tile(entry.data.name, key);
            tile.set(entry.data.objects.get(key));
            if (entry.data.action)
                itemspec = tile.specItemInstance();
            viewexport.prefix = tilename = tile.getName();
//            extcontext.tilesobjectset(data.name, tile.get());
//            extcontext.parentput(tilename, data.name);
            subpagectx = output.run(viewexport, viewctx.sessionid, entry);
//            builder.execute(data.context, itemspec, tilename, data.action);
            if (!entry.data.action)
                continue;
//            linkname = Tile.getLinkName(tile);
//            link = subpagectx.view.getElement(linkname);
//            link.setText("");
//            link.setStyleClass("nc_tiles_link");
//            viewctx.view.put(linkname,
//                    new TilesAction(extcontext.tilesactionget(data.name)));
//            function.register(pagename, linkname, view);
        }
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
