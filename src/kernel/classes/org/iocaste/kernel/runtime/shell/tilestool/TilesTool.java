package org.iocaste.kernel.runtime.shell.tilestool;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.runtime.shell.AbstractComponentTool;
import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ProcessOutput;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.elements.Link;
import org.iocaste.kernel.runtime.shell.elements.StandardContainer;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.shell.common.Container;

public class TilesTool extends AbstractComponentTool {
    
    /**
     * 
     * @param context
     * @param data
     */
    public TilesTool(ViewContext viewctx, ComponentEntry entry) {
        super(viewctx, entry);
    }
    
    private final void buildLink(
            Tile tile, ViewContext subpagectx, ViewExport viewexport) {
        ToolData item, exportitem;
        Object value;
        Link link;
        ExtendedObject object = (ExtendedObject)tile.get();
        
        for (Object exportobj : viewexport.items) {
            exportitem = (ToolData)exportobj;
            item = subpagectx.entries.get(tile.getName(exportitem.name)).data;
            if (item.modelitem == null) {
                value = object.get(exportitem.name);
                item.text = (value == null)? "" : value.toString();
                continue;
            }
            link = subpagectx.view.getElement(item.name);
            link.add(item.modelitem, object);
            link.setText("");
        }
    }
    
    @Override
    public final void load() { }

    @Override
    public void refresh() { }

    @Override
    public void run() throws Exception {
        Tile tile;
        ViewContext subpagectx;
        ViewExport viewexport;
        ProcessOutput output;
        Container container;
        
        if (entry.data.objects == null)
            return;

        container = new StandardContainer(viewctx, entry.data.name);
        setHtmlName(container.getHtmlName());
        output = viewctx.function.get("output_process");
        viewexport = viewctx.subpages.get(entry.data.subpage);
        for (int key : entry.data.objects.keySet()) {
            tile = new Tile(entry.data.name, key);
            tile.set(entry.data.objects.get(key));
            viewexport.prefix = tile.getName();
            subpagectx = output.run(viewexport, viewctx.sessionid, entry);
            buildLink(tile, subpagectx, viewexport);
        }
    }
}
