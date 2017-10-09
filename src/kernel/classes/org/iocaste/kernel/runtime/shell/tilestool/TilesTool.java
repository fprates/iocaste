package org.iocaste.kernel.runtime.shell.tilestool;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.runtime.shell.AbstractComponentTool;
import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ProcessOutput;
import org.iocaste.kernel.runtime.shell.ProcessOutputData;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.tooldata.StandardContainer;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ViewExport;

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
        String response;
        ExtendedObject object = (ExtendedObject)tile.get();
        String prefix = tile.getPrefix();
        
        for (Object exportobj : data.viewctx.viewexport.items) {
            exportitem = (ToolData)exportobj;
            item = data.viewctx.entries.
                    get(tile.getName(exportitem.name)).data;
            if (item.indexitem == null) {
                if (data.viewctx.messagesrc.get(exportitem.name) == null) {
                    value = object.get(exportitem.name);
                    item.text = (value == null)? "" : value.toString();
                } else {
                    item.text = exportitem.name;
                    item.textargs = new Object[] {object.get(exportitem.name)};
                }
                continue;
            }
            
            response = new StringBuilder(prefix).append("_").
                    append(item.indexitem).toString();
            link = data.viewctx.view.getElement(item.name);
            link.add(response, item.indexitem, object);
            link.setText("");
            if (item.action)
                item.actionname = object.get(item.indexitem).toString();
        }
    }
    
    @Override
    public final void load() {
        ToolData exportitem;
        String name;
        InputComponent input;
        ViewExport viewexport = viewctx.subpages.get(entry.data.subpage);
        
        for (int i = 0; i < viewexport.items.length; i++) {
            exportitem = (ToolData)viewexport.items[i];
            if (exportitem.indexitem == null)
                continue;
            name = new StringBuilder(entry.data.name).append("_").
                    append(exportitem.indexitem).toString();
            input = viewctx.view.getElement(name);
            if (input == null)
                continue;
            entry.data.value = input.get();
            return;
        }
    }

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
        data.viewctx.viewexport = viewctx.subpages.get(entry.data.subpage);
        data.parententry = entry;
        data.noeventhandlers = true;
        data.noinitmessages = true;
        for (int key : entry.data.objects.keySet()) {
            tile = new Tile(entry.data.name, key);
            tile.set(entry.data.objects.get(key));
            data.viewctx.viewexport.prefix = tile.getName();
            output.run(data);
            buildLink(tile, data);
        }
    }
}
