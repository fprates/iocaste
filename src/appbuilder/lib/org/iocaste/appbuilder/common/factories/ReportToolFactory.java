package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.dataformtool.DataFormTool;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.reporttool.ReportToolStageItem;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.DocumentModel;

public class ReportToolFactory extends AbstractSpecFactory {
    
    @Override
    protected AbstractComponentData dataInstance() {
        ReportToolData data = new ReportToolData(context);
        data.input.tooldata = new DataFormToolData();
        data.output.tooldata = new TableToolData();
        return data;
    }
    
    @Override
    public final void generate(ComponentEntry entry) {
        boolean hasfocus;
        DataFormToolItem dfitem;
        ReportToolStageItem item;
        DocumentModel model;
        ComponentEntry ttentry, dfentry;
        TableToolData ttdata;
        TableToolColumn ttcol;
        DataFormToolData dfdata;
        ReportToolData rtdata = (ReportToolData)entry.data;
            
        entry.component = new ReportTool(entry);
        if (rtdata.isInput()) {
            dfdata = (DataFormToolData)rtdata.input.tooldata;
            dfdata.context = entry.data.context;
            dfdata.name = entry.data.name;
            dfdata.style = rtdata.input.outerstyle = rtdata.input.outerstyle;
            rtdata.input.tooldata = dfdata;
            if (rtdata.input.items.size() > 0) {
                model = ReportTool.buildModel(rtdata);
                dfdata.custommodel = model;
            } else {
                if (rtdata.input.model == null)
                    throw new RuntimeException("model not defined.");
                dfdata.model = rtdata.input.model;
            }
            
            hasfocus = false;
            for (String itemname : rtdata.input.items.keySet()) {
                item = rtdata.input.items.get(itemname);
                dfitem = dfdata.itemInstance(itemname);
                dfitem.componenttype = item.type;
                dfitem.values = item.values;
                dfitem.sh = item.sh;
                dfitem.required = item.required;
                if ((rtdata.input.nsreference != null) &&
                        rtdata.input.nsreference.equals(itemname))
                    dfitem.ns = true;
                if (!hasfocus)
                    hasfocus = dfitem.focus = true;
                if (item.label != null)
                    dfitem.label = item.label;
            }
            
            dfentry = new ComponentEntry();
            dfentry.data = dfdata;
            rtdata.input.toolcomponent = new DataFormTool(dfentry);
        } else {
            ttdata = (TableToolData)rtdata.output.tooldata;
            ttdata.context = entry.data.context;
            ttdata.name = entry.data.name;
            ttdata.mode = TableTool.DISPLAY;
            ttdata.vlines = 0;
            ttdata.style = rtdata.output.outerstyle;
            ttdata.model = rtdata.output.model;
            rtdata.output.tooldata = ttdata;
            if (rtdata.output.items.size() > 0) {
                ttdata.refmodel = ReportTool.buildModel(rtdata);
                for (String key : rtdata.output.items.keySet()) {
                    item = rtdata.output.items.get(key);
                    ttcol = new TableToolColumn(ttdata, key);
                    ttcol.label = item.label;
                    ttcol.length = item.element.getLength();
                    ttcol.vlength = item.vlength;
                }
            }
            ttentry = new ComponentEntry();
            ttentry.data = ttdata;
            rtdata.output.toolcomponent = new TableTool(ttentry);
        }
    }
}
