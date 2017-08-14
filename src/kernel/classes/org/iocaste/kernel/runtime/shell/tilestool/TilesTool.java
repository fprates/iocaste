package org.iocaste.kernel.runtime.shell.tilestool;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.runtime.shell.AbstractComponentTool;
import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ProcessOutput;
import org.iocaste.kernel.runtime.shell.ProcessOutputData;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.elements.Link;
import org.iocaste.kernel.runtime.shell.elements.StandardContainer;
import org.iocaste.runtime.common.application.ToolData;
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
    
    private final void buildLink(Tile tile, ProcessOutputData data) {
        ToolData item, exportitem;
        Object value;
        Link link;
        ExtendedObject object = (ExtendedObject)tile.get();
        String prefix = tile.getPrefix();
        
        for (Object exportobj : data.viewexport.items) {
            exportitem = (ToolData)exportobj;
            item = data.viewctx.entries.
                    get(tile.getName(exportitem.name)).data;
            if (item.modelitem == null) {
                value = object.get(exportitem.name);
                item.text = (value == null)? "" : value.toString();
                continue;
            }
            link = data.viewctx.view.getElement(item.name);
            link.add(prefix, item.modelitem, object);
            link.setText("");
            if (item.action)
                item.actionname = object.get(item.modelitem).toString();
        }
    }
    
    @Override
    public final void load() { }

    @Override
    public void refresh() { }

    @Override
    public void run() throws Exception {
        Tile tile;
        ProcessOutput output;
        ProcessOutputData data;
        Container container;
        
        if (entry.data.objects == null)
            return;

        container = new StandardContainer(viewctx, entry.data.name);
        setHtmlName(container.getHtmlName());
        output = viewctx.function.get("output_process");
        
        data = new ProcessOutputData();
        data.viewctx = new ViewContext(viewctx.view);
        data.viewctx.sessionid = viewctx.sessionid;
        data.viewctx.messagesrc = viewctx.messagesrc;
        data.viewexport = viewctx.subpages.get(entry.data.subpage);
        data.parententry = entry;
        data.noeventhandlers = true;
        data.noinitmessages = true;
        for (int key : entry.data.objects.keySet()) {
            tile = new Tile(entry.data.name, key);
            tile.set(entry.data.objects.get(key));
            data.viewexport.prefix = tile.getName();
            output.run(data);
            buildLink(tile, data);
        }
    }
}
