package org.iocaste.kernel.runtime.shell.dataformtool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.runtime.shell.AbstractComponentTool;
import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.protocol.IocasteException;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.tooldata.ToolData;

public class DataFormTool extends AbstractComponentTool {
    
    public DataFormTool(ViewContext viewctx, ComponentEntry entry) {
        super(viewctx, entry);
    }
    
    @Override
    public final String getNSField() {
        ToolData dfdata = (ToolData)entry.data;
        DataForm df = getElement();
        
        if (dfdata.nsitem != null) {
            for (Element element : df.getElements())
                if (df.isNSReference(element.getName()))
                    return element.getHtmlName();
            return null;
        }
        for (String key : dfdata.get().keySet())
            if (dfdata.get(key).ns)
                return df.get(key).getHtmlName();
        return null;
    }
    
    /**
     * 
     * @param tooldata
     * @return
     */
    public final ExtendedObject getObject() {
        String name, nsreference;
        InputComponent input;
        ExtendedObject object;
        DataForm df;
        
        if (entry.data.custommodel == null)
            return null;

        df = getElement();
        if (df == null)
            return entry.data.object;
        
        object = new ExtendedObject(entry.data.custommodel);
        nsreference = df.getNSReference();
        if (nsreference != null) {
            input = getElement(nsreference);
            object.setNS(input.get());
        }
        
        for (Element element: df.getElements()) {
            if (!element.isDataStorable())
                continue;

            input = (InputComponent)element;
            name = input.getName();
            if (entry.data.custommodel.getModelItem(name) == null)
                continue;
            
            object.set(name, input.get());
        }
        
        return object;
    }
    
    @Override
    public final void load() {
        ExtendedObject object;
        ToolData item;
        
        if (getElement() == null)
        	return;
        object = getObject();
        if (entry.data.object == null)
            entry.data.object = object;
        for (String key : entry.data.items.keySet()) {
            item = entry.data.items.get(key);
            if (!item.disabled)
                entry.data.object.set(key, object.get(key));
            item.value = entry.data.object.get(key);
        }
    }
    
    @Override
    public void refresh() {
        ToolData item;
        DataForm form = getElement();
        
        if (entry.data.object == null) {
            form.clearInputs();
            for (String name : entry.data.get().keySet()) {
                item = entry.data.get(name);
                form.get(name).set(item.value);
            }
        } else {
            setObject(viewctx, entry.data.object);
        }
    }

    @Override
    public void run() {
        ToolData item;
        DataItem input;
        DataForm df;
        ComponentEntry nsentry;
        Map<String, List<String>> groups;
        String htmlname;
        ToolData data = getComponentData();
        Container container = getElement(data.parent);
        
        df = new DataForm(container, data.name);
        setHtmlName(df.getHtmlName());
        if (data.custommodel != null)
            DataForm.importModel(df, data.custommodel);
        if (data.model != null)
            data.custommodel = DataForm.importModel(
                    df, data.model, viewctx.function);
        else
            data.model = (data.custommodel != null)?
                    data.custommodel.getName() : null;

        if (data.custommodel == null)
            throw new IocasteException("undefined model for %s.", data.name);
        
        if (data.style != null)
            df.setStyleClass(data.style);
        
        df.setEnabled(!data.disabled);
        if (data.nsitem != null)
            for (Element element : df.getElements())
                if (df.isNSReference(element.getName())) {
                    setItem(data, (DataItem)element, data.nsitem);
                    break;
                }
        if (data.nsdata != null) {
            nsentry = viewctx.entries.get(data.nsdata.name);
            df.setNSReference(nsentry.component.getNSField());
        }
        
        if (data.groups != null) {
            groups = new LinkedHashMap<>();
            for (String name : data.groups)
                groups.put(name, new ArrayList<String>());
        } else {
            groups = null;
        }
        
        for (DocumentModelItem modelitem : data.custommodel.getItens()) {
            item = data.instance(modelitem.getName());
            input = df.get(item.name);
            if ((data.custommodel == null) && (input == null)) {
                input = new DataItem(df, (item.componenttype == null)?
                        Const.TEXT_FIELD : item.componenttype, item.name);
                input.setDataElement(item.element);
            }
            if (item.sh != null)
                data.custommodel.getModelItem(item.name).setSearchHelp(item.sh);
            setItem(data, input, item);
            input.setVisible(!item.invisible);
            if (item.style != null)
                input.setStyleClass(item.style);
            htmlname = input.getHtmlName();
            if (item.ns)
                df.setNSReference(htmlname);
            if ((data.groups != null) && (item.group != null))
                groups.get(item.group).add(item.name);
        }

        if (data.internallabel)
            for (Element element : df.getElements())
                ((DataItem)element).setPlaceHolder(true);
        
        if (data.groups != null)
            for (String name : groups.keySet())
                df.addGroup(name, groups.get(name).toArray(new String[0]));
    }
    
    private void setItem(ToolData data, DataItem input, ToolData item) {
        input.setSecret(item.secret);
        input.setObligatory(item.required);
        input.setEnabled(!item.disabled);
        input.setVisible(!item.invisible);
        if (item.componenttype != null)
            input.setComponentType(item.componenttype);
//        if (item.focus == true)
//            view.setFocus(input);
        if (item.values != null)
            for (String key : item.values.keySet())
                input.add(key, item.values.get(key));
//        for (String validator : item.validators)
//            data.context.getView().validate(input, validator);
        if (item.length > 0)
            input.setLength(item.length);
        if (item.vlength > 0)
            input.setVisibleLength(item.vlength);
        if (item.label != null)
            input.setLabel(item.label);
    }
    
    /**
     * Lê a partir de objeto extendido.
     * @param object object extendido.
     */
    public final void setObject(ViewContext viewctx, ExtendedObject object) {
        DataItem item;
        String name;
        DataForm form = getElement();
        
        for (Element element : form.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (DataItem)element;
            name = item.getName();
            item.set(object.getNS(), object.get(name));
        }
        
        if (form.getNSReference() != null)
            form.setNS(object.getNS());
    }
}
