package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;

public class Common {

    public static final void formConfig(ConfigData formdata) {
        boolean key;
        DocumentModel model;
        DocumentModelItem[] items;
        DataFormToolData form;
        DataFormToolItem item;
        Map<String, ComponentEntry> entries;
        
        entries = formdata.context.getView().getComponents().entries;
        model = formdata.cmodel.getHeader();
        items = model.getItens();
        for (String name : new String[] {"head", "base"}) {
            form = (DataFormToolData)entries.get(name).data;
            form.custommodel = model;
            
            switch(name) {
            case "head":
                form.disabled = true;
                for (DocumentModelItem mitem : items) {
                    key = model.isKey(mitem);
                    if (key)
                        formdata.hkey = mitem;
                    item = form.instance(mitem.getName());
                    item.invisible = !key;
                    if (formdata.fieldproperties != null)
                        inputConfig(formdata, item);
                }
                
                break;
            case "base":
                if (model.getNamespace() != null) {
                    item = form.nsItemInstance();
                    item.invisible = item.disabled = true;
                }
                
                for (DocumentModelItem mitem : items) {
                    item = form.instance(mitem.getName());
                    key = model.isKey(mitem);
                    item.disabled = key || (formdata.mode != ConfigData.UPDATE);
                    item.invisible = key;
                    if (!item.focus && !item.disabled)
                        item.focus = true;
                    if (formdata.fieldproperties != null)
                        inputConfig(formdata, item);
                }
                
                break;
            }
        }
    }
    
    public static final void gridConfig(ConfigData griddata) {
        Map<String, ComplexModelItem> models;
        ComplexModelItem cmodelitem;
        TableToolData tabletool;
        DocumentModelItem ns;
        ViewComponents components = griddata.context.getView().getComponents();
        
        models = griddata.cmodel.getItems();
        for (String name : models.keySet()) {
            cmodelitem = models.get(name);
            if (cmodelitem.model == null)
                continue;
            tabletool = components.getComponentData(name.concat("_table"));
            tabletool.model = cmodelitem.model.getName();
            tabletool.mode = griddata.mode;
            tabletool.mark = griddata.mark;
            tabletool.vlines = 0;
            for (String hide : new String[] {
                Documents.getReference(cmodelitem.model, griddata.hkey).
                        getName(),
                Documents.getKey(cmodelitem.model).getName()
            })
                tabletool.instance(hide).invisible = true;
            ns = cmodelitem.model.getNamespace();
            if (ns != null)
                tabletool.instance(ns.getName()).invisible = true;
        }
    }
    
    private static void inputConfig(ConfigData formdata, DataFormToolItem item)
    {
        FieldProperty property;
        
        property = formdata.fieldproperties.get(item.name);
        if ((property != null) && (property.setsecretstate))
            item.secret = property.secret;
    }
}
