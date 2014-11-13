package org.iocaste.appbuilder;

import java.util.Map;

import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.docmanager.common.AbstractManager;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class Common {

    public static final void formConfig(ConfigData formdata) {
        InputComponent input;
        boolean key;
        DocumentModel model;
        DocumentModelItem[] items;
        DataForm form;
        
        model = formdata.cmodel.getHeader();
        items = model.getItens();
        for (String name : new String[] {"head", "base"}) {
            form = formdata.context.view.getElement(name);
            form.importModel(model);
            form.setEnabled(false);
            
            switch(name) {
            case "head":
                for (DocumentModelItem item : items) {
                    key = model.isKey(item);
                    if (key)
                        formdata.hkey = item;
                    form.get(item.getName()).setVisible(key);
                }
                
                break;
            case "base":
                for (DocumentModelItem item : items) {
                    key = model.isKey(item);
                    input = form.get(item.getName());
                    input.setVisible(!key);
                    input.setEnabled(
                            (!key && (formdata.mode == ConfigData.UPDATE)));
                }
                
                break;
            }
        }
    }
    
    public static final void gridConfig(ConfigData griddata) {
        DocumentModel model;
        Map<String, DocumentModel> models;
        TableToolData tabletool;
        ViewComponents components = griddata.context.getView().getComponents();
        
        models = griddata.cmodel.getItems();
        for (String name : models.keySet()) {
            model = models.get(name);
            tabletool = components.getTableToolData(name.concat("_table"));
            tabletool.model = model.getName();
            tabletool.mode = griddata.mode;
            tabletool.mark = griddata.mark;
            tabletool.hide = new String[] {
                AbstractManager.getReference(model, griddata.hkey).getName(),
                AbstractManager.getKey(model).getName()
            };
        }
    }
}
