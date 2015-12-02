package org.iocaste.appbuilder.common.dataformtool;

import java.util.ArrayList;
import java.util.List;

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
    public final String getNSField() {
        DataFormToolData dfdata = (DataFormToolData)entry.data;
        DataForm df = getElement();
        
        if (dfdata.nsitem != null) {
            for (Element element : df.getElements())
                if (df.isNSReference(element.getName()))
                    return element.getHtmlName();
            return null;
        }
        for (String key : dfdata.items.keySet())
            if (dfdata.items.get(key).ns)
                return df.get(key).getHtmlName();
        return null;
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
        ComponentEntry nsentry;
        DataFormToolData data = getComponentData();
        Container container = getElement(data.name);
        List<String> columns = null;
        
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
        if (data.show != null) {
            if (data.columns > 0) {
                dataform.setColumns(data.columns);
                columns = new ArrayList<>();
                for (String column : data.show)
                    if (column != null)
                        columns.add(column);
                dataform.show(columns.toArray(new String[0]));
                columns.clear();
            } else {
                dataform.show(data.show);
            }
        }
        
        dataform.setEnabled(!data.disabled);
        if (data.nsitem != null)
            for (Element element : dataform.getElements())
                if (dataform.isNSReference(element.getName())) {
                    setItem(data, (DataItem)element, data.nsitem);
                    break;
                }
        if (data.nsdata != null) {
            nsentry = data.context.getView().getComponents().entries.
                    get(data.nsdata.name);
            dataform.setNSReference(nsentry.component.getNSField());
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
            if (item.ns)
                dataform.setNSReference(input.getHighHtmlName());
        }

        if (columns == null)
            return;
        
        for (String name : data.show) {
            columns.add(name);
            if (columns.size() != data.columns)
                continue;
            dataform.addLine(columns.toArray(new String[0]));
            columns.clear();
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
        if (item.validate != null)
            data.context.function.validate(input, item.validate);
        if (item.length > 0)
            input.setLength(item.length);
        if (item.vlength > 0)
            input.setVisibleLength(item.vlength);
    }
}
