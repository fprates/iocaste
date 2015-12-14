package org.iocaste.appbuilder.common.reporttool;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.documents.common.ExtendedObject;

public class ReportToolStage {
    public String outerstyle, model, nsreference;
    public Map<String, ReportToolStageItem> items;
    public ExtendedObject object;
    public ExtendedObject[] objects;
    public AbstractComponentTool toolcomponent;
    public AbstractComponentData tooldata;
    
    public ReportToolStage() {
        items = new LinkedHashMap<>();
    }


    public ReportToolStageItem item(String name) {
        ReportToolStageItem item;
        
        item = new ReportToolStageItem();
        items.put(name, item);
        return item;
    }
}
