package org.iocaste.appbuilder.common.dataformtool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class DataFormTool extends AbstractComponentTool {
    private String page;
    
    public DataFormTool(ComponentEntry entry) {
        super(entry);
        page = entry.data.context.view.getPageName();
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
        for (String key : dfdata.get().keySet())
            if (dfdata.get(key).ns)
                return df.get(key).getHtmlName();
        return null;
    }
    
    /**
     * Retorna objeto extendido equivalente.
     * @return objeto extendido
     */
    public final ExtendedObject getObject() {
        String name, nsreference;
        InputComponent input;
        ExtendedObject object;
        DataForm df;
        DataFormToolData dfdata = (DataFormToolData)entry.data;
        
        if (dfdata.custommodel == null)
            return null;

        df = getElement();
        if (df == null)
            return entry.data.context.getView(page).getExtendedContext().
                    dfobjectget(page, entry.data.name);
        
        object = new ExtendedObject(dfdata.custommodel);
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
            if (dfdata.custommodel.getModelItem(name) == null)
                continue;
            
            object.set(name, input.get());
        }
        
        return object;
    }
    
    @Override
    public final void load(AbstractComponentData data) {
        if (getElement() != null)
            getExtendedContext().set(entry.data.name, getObject());
    }
    
    @Override
    public void refresh() {
        DataFormToolItem item;
        DataForm form = getElement();
        ExtendedObject object = getExtendedContext().
                dfobjectget(entry.data.name);
        
        if (object == null) {
            form.clearInputs();
            for (String name : entry.data.get().keySet()) {
                item = entry.data.get(name);
                form.get(name).set(item.value);
            }
        } else {
            setObject(object);
        }
    }

    @Override
    public void run() {
        String htmlname;
        DataFormToolItem item;
        DataItem input;
        DataForm df;
        ComponentEntry nsentry;
        Map<String, List<String>> groups;
        DataFormToolData data = getComponentData();
        Container container = getElement(data.name);
        
        htmlname = new StringBuilder(data.name).
                append("_").append(data.type.toString()).toString();
        df = new DataForm(container, htmlname);
        setHtmlName(df.getHtmlName());
        if (data.custommodel != null)
            DataForm.importModel(df, data.custommodel);
        if (data.model != null)
            data.custommodel = DataForm.importModel(
                    df, data.model, data.context.function);
        else
            data.model = (data.custommodel != null)?
                    data.custommodel.getName() : null;
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
            nsentry = data.context.getView().getComponents().entries.
                    get(data.nsdata.name);
            df.setNSReference(nsentry.component.getNSField());
        }
        
        if (data.groups != null) {
            groups = new LinkedHashMap<>();
            for (String name : data.groups)
                groups.put(name, new ArrayList<>());
        } else {
            groups = null;
        }
        
        for (String name : data.get().keySet()) {
            item = data.get(name);
            input = df.get(name);
            if ((data.custommodel == null) && (input == null)) {
                input = new DataItem(df, (item.componenttype == null)?
                        Const.TEXT_FIELD : item.componenttype, name);
                input.setDataElement(item.element);
            }
            if (item.sh != null)
                data.custommodel.getModelItem(name).setSearchHelp(item.sh);
            setItem(data, input, item);
            input.setVisible(!item.invisible);
            if (item.style != null)
                input.setStyleClass(item.style);
            if (item.ns)
                df.setNSReference(input.getHtmlName());
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
    
    private void setItem(DataFormToolData data,
            DataItem input, DataFormToolItem item) {
        input.setSecret(item.secret);
        input.setObligatory(item.required);
        input.setEnabled(!item.disabled);
        input.setVisible(!item.invisible);
        if (item.componenttype != null)
            input.setComponentType(item.componenttype);
        if (item.focus == true)
            data.context.view.setFocus(input);
        if (item.values != null)
            for (String key : item.values.keySet())
                input.add(key, item.values.get(key));
        if (item.validator != null)
            data.context.getView().validate(input, item.validator);
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
    public final void setObject(ExtendedObject object) {
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
