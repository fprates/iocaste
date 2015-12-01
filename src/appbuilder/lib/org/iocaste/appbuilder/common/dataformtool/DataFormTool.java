package org.iocaste.appbuilder.common.dataformtool;

import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;

public class DataFormTool extends AbstractComponentTool {
    private String formname;
    
    public DataFormTool(ComponentEntry entry) {
        super(entry);
    }
    
    @Override
    public void refresh() {
        DataFormToolData data = (DataFormToolData)entry.data;
        DataForm form = getElement(formname);
        form.setObject(data.object);
    }

    @Override
    public void run() {
        Container container = getElement(entry.data.name);
        formname = entry.data.name.concat(entry.data.type.toString());
        formname = new DataForm(container, formname).getHtmlName();
    }

}
