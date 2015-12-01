package org.iocaste.appbuilder.common.dataformtool;

import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;

public class DataFormTool extends AbstractComponentTool {
    
    public DataFormTool(ComponentEntry entry) {
        super(entry);
    }
    
    @Override
    public void refresh() {
        DataFormToolItem item;
        DataFormToolData data = (DataFormToolData)entry.data;
        DataForm form = getElement(getHtmlName());
        
        if (data.object == null) {
            form.clearInputs();
            for (String name : data.items.keySet()) {
                item = data.items.get(name);
                form.get(name).set(item.value);
            }
        } else {
            form.setObject(data.object);
        }
    }

    @Override
    public void run() {
        String htmlname;
        DataFormToolItem item;
        DataItem input;
        DataForm dataform;
        DataFormToolData data = getComponentData();
        Container container = getElement(data.name);
        
        htmlname = new StringBuilder(data.name).
                append("_").append(data.type.toString()).toString();
        dataform = new DataForm(container, htmlname);
        setHtmlName(dataform.getHtmlName());
        if (data.model != null)
            dataform.importModel(data.model);
        if (data.modelname != null) {
            dataform.importModel(data.modelname, data.context.function);
            data.model = dataform.getModel();
        }
        if (data.style != null)
            dataform.setStyleClass(data.style);
        if (data.show != null)
            dataform.show(data.show);
        dataform.setEnabled(!data.disabled);

        if (data.nsitem != null)
            for (Element element : dataform.getElements())
                if (dataform.isNSReference(element.getName())) {
                    setItem(data, (DataItem)element, data.nsitem);
                    break;
                }
        
        for (String name : data.items.keySet()) {
            item = data.items.get(name);
            input = dataform.get(name);
            if ((data.model == null) && (input == null)) {
                input = new DataItem(dataform, (item.componenttype == null)?
                        Const.TEXT_FIELD : item.componenttype, name);
                input.setDataElement(item.element);
            }
            if (item.sh != null)
                data.model.getModelItem(name).setSearchHelp(item.sh);
            setItem(data, input, item);
        }
    }
    
    private void setItem(DataFormToolData data,
            DataItem input, DataFormToolItem item) {
        input.setSecret(item.secret);
        input.setObligatory(item.required);
        input.setEnabled(!data.disabled & !item.disabled);
        input.setVisible(!item.invisible);
        if (item.componenttype != null)
            input.setComponentType(item.componenttype);
        if (item.focus == true)
            data.context.view.setFocus(input);
        if (item.values != null)
            for (String key : item.values.keySet())
                input.add(key, item.values.get(key));
    }
}
