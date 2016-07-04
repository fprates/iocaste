package org.iocaste.appbuilder.common.dataformtool;

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
        String name;
        InputComponent input;
        ExtendedObject object;
        DataForm df;
        DataFormToolData dfdata = (DataFormToolData)entry.data;
        
        if (dfdata.custommodel == null)
            return null;
        
        object = new ExtendedObject(dfdata.custommodel);
        df = getElement();
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
            ((DataFormToolData)data).object = getObject();
    }
    
    @Override
    public void refresh() {
        DataFormToolItem item;
        DataFormToolData data = (DataFormToolData)entry.data;
        DataForm form = getElement();
        
        if (data.object == null) {
            form.clearInputs();
            for (String name : data.get().keySet()) {
                item = data.get(name);
                form.get(name).set(item.value);
            }
        } else {
            setObject(data.object);
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
        
        htmlname = new StringBuilder(data.name).
                append("_").append(data.type.toString()).toString();
        dataform = new DataForm(container, htmlname);
        setHtmlName(dataform.getHtmlName());
        if (data.custommodel != null)
            dataform.importModel(data.custommodel);
        if (data.model != null) {
            dataform.importModel(data.model, data.context.function);
            data.custommodel = dataform.getModel();
        }
        if (data.style != null)
            dataform.setStyleClass(data.style);
        
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
        
        for (String name : data.get().keySet()) {
            item = data.get(name);
            input = dataform.get(name);
            if ((data.custommodel == null) && (input == null)) {
                input = new DataItem(dataform, (item.componenttype == null)?
                        Const.TEXT_FIELD : item.componenttype, name);
                input.setDataElement(item.element);
            }
            if (item.sh != null)
                data.custommodel.getModelItem(name).setSearchHelp(item.sh);
            setItem(data, input, item);
            input.setVisible(!item.invisible);
            if (item.ns)
                dataform.setNSReference(input.getHtmlName());
        }

        if (data.internallabel)
            for (Element element : dataform.getElements())
                ((DataItem)element).setPlaceHolder(true);
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
            data.context.function.validate(input, item.validator);
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
        
        form.setNS(object.getNS());
    }
}
