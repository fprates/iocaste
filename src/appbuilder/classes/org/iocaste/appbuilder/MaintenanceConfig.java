package org.iocaste.appbuilder;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.docmanager.common.AbstractManager;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class MaintenanceConfig extends AbstractViewConfig {
    private String cmodel;
    
    public MaintenanceConfig(String cmodel) {
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        DocumentModelItem hkey;
        InputComponent input;
        Map<String, DocumentModel> models;
        TableToolData tabletool;
        DocumentModelItem[] items;
        Manager manager = getManager(this.cmodel);
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
        
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            model = models.get(name);
            tabletool = getTableTool(name.concat("_table"));
            tabletool.model = model.getName();
            tabletool.mode = TableTool.UPDATE;
            tabletool.hide = new String[] {
                    AbstractManager.getReference(model, hkey).getName(),
                    AbstractManager.getKey(model).getName()
            };
        }
    }
}
