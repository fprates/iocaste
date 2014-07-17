package org.iocaste.appbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.docmanager.common.AbstractManager;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class MaintenanceConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        DocumentModelItem hkey;
        List<String> names;
        InputComponent input;
        Map<String, DocumentModel> models;
        TableTool tabletool;
        DocumentModelItem[] items;
        Manager manager = getManager();
        ComplexModel cmodel = manager.getModel();
        DocumentModel model = cmodel.getHeader();
        NavControl navcontrol = getNavControl();
        DataForm form = getElement("head");
        
        navcontrol.add("save");
        form.setEnabled(false);
        form.importModel(model);
        items = model.getItens();
        hkey = null;
        for (DocumentModelItem item : items) {
            input = form.get(item.getName());
            if (model.isKey(item)) {
                input.setEnabled(false);
                hkey = item;
            } else {
                input.setVisible(false);
            }
        }
        
        form = getElement("base");
        form.importModel(model);
        for (DocumentModelItem item : items)
            form.get(item.getName()).setVisible(!model.isKey(item));
        getTabbedItem("tabs", "basetab").set(form);
        
        models = cmodel.getItems();
        names = new ArrayList<>();
        for (String name : models.keySet()) {
            model = models.get(name);
            tabletool = getTableTool(name.concat("_table"));
            tabletool.model(model);
            tabletool.setMode(TableTool.UPDATE);
            names.clear();
            names.add(AbstractManager.getReference(model, hkey).getName());
            names.add(AbstractManager.getKey(model).getName());
            tabletool.setVisibility(false, names.toArray(new String[0]));
            
            getTabbedItem("tabs", name).set(tabletool.getContainer());
        }
    }
}
