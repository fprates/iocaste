package org.iocaste.appbuilder.common.dataformtool;

import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

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
        DataFormToolItem item;
        InputComponent input;
        DataForm dataform;
        DataFormToolData data = getComponentData();
        Container container = getElement(data.name);
        
        formname = new StringBuilder(data.name).
                append("_").append(data.type.toString()).toString();
        dataform = new DataForm(container, formname);
        formname = dataform.getHtmlName();
        if (data.model != null)
            dataform.importModel(data.model);
        if (data.modelname != null)
            dataform.importModel(data.modelname, data.context.function);
        if (data.style != null)
            dataform.setStyleClass(data.style);
        for (String key : data.items.keySet()) {
            input = dataform.get(key);
            item = data.items.get(key);
            input.setSecret(item.secret);
            input.setObligatory(item.required);
            if (item.componenttype != null)
                input.setComponentType(item.componenttype);
            if (item.focus == true)
                data.context.view.setFocus(input);
        }
        
    }

}
