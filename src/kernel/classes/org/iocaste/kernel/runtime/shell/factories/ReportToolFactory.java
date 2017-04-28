package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.elements.StandardContainer;

public class ReportToolFactory extends AbstractSpecFactory {
    
    @Override
    public final void generate(
    		ViewContext viewctx, ComponentEntry entry, String prefix) {
//        boolean hasfocus;
//        DataFormToolItem dfitem;
//        ReportToolStageItem item;
//        DocumentModel model;
//        ComponentEntry ttentry, dfentry;
//        TableToolData ttdata;
//        TableToolColumn ttcol;
//        DataFormToolData dfdata;
//        ReportToolData rtdata = (ReportToolData)entry.data;
//        
//        entry.component = new ReportTool(entry);
//        if (rtdata.isInput()) {
//            context.getView().getExtendedContext().dataformInstance(
//                    entry.data.name);
//            
//            dfdata = (DataFormToolData)rtdata.input.tooldata;
//            dfdata.context = entry.data.context;
//            dfdata.name = entry.data.name;
//            dfdata.style = rtdata.input.outerstyle = rtdata.input.outerstyle;
//            dfdata.parent = rtdata.parent;
//            rtdata.input.tooldata = dfdata;
//            if (rtdata.input.items.size() > 0) {
//                model = ReportTool.buildModel(rtdata);
//                dfdata.custommodel = model;
//            } else {
//                if (rtdata.input.model == null)
//                    throw new RuntimeException("model not defined.");
//                dfdata.model = rtdata.input.model;
//            }
//            
//            hasfocus = false;
//            for (String itemname : rtdata.input.items.keySet()) {
//                item = rtdata.input.items.get(itemname);
//                dfitem = dfdata.instance(itemname);
//                dfitem.componenttype = item.type;
//                dfitem.values = item.values;
//                dfitem.sh = item.sh;
//                dfitem.required = item.required;
//                if ((rtdata.input.nsreference != null) &&
//                        rtdata.input.nsreference.equals(itemname))
//                    dfitem.ns = true;
//                if (!hasfocus)
//                    hasfocus = dfitem.focus = true;
//                if (item.label != null)
//                    dfitem.label = item.label;
//            }
//            
//            dfentry = new ComponentEntry();
//            dfentry.data = dfdata;
//            rtdata.input.toolcomponent = new DataFormTool(dfentry);
//        } else {
//            ttdata = (TableToolData)rtdata.output.tooldata;
//            ttdata.context = entry.data.context;
//            ttdata.name = entry.data.name;
//            ttdata.mode = TableTool.DISPLAY;
//            ttdata.vlines = 0;
//            ttdata.style = rtdata.output.outerstyle;
//            ttdata.model = rtdata.output.model;
//            ttdata.parent = rtdata.parent;
//            rtdata.output.tooldata = ttdata;
//            if (rtdata.output.items.size() > 0) {
//                ttdata.custommodel = ReportTool.buildModel(rtdata);
//                for (String key : rtdata.output.items.keySet()) {
//                    item = rtdata.output.items.get(key);
//                    ttcol = ttdata.instance(key);
//                    ttcol.label = item.label;
//                    ttcol.length = item.element.getLength();
//                    ttcol.vlength = item.vlength;
//                }
//            }
//            ttentry = new ComponentEntry();
//            ttentry.data = ttdata;
//            rtdata.output.toolcomponent = new TableTool(ttentry);
//        }
    }
}
