package org.iocaste.appbuilder.common.editor;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.TableTool;

public class MaintainanceConfig extends AbstractViewConfig {
    private Manager manager;
    private String title;
    
    public MaintainanceConfig(Manager manager) {
        this.manager = manager;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        Map<String, DocumentModel> models;
        TableTool tabletool;
        DocumentModelItem[] items;
        ComplexModel cmodel = manager.getModel();
        DocumentModel model = cmodel.getHeader();
        NavControl navcontrol = getNavControl();
        DataForm form = getElement("head");
        
        context.view.setTitle(title);
        navcontrol.add("save");
        
        form.setEnabled(false);
        form.importModel(model);
        items = model.getItens();
        for (DocumentModelItem item : items)
            form.get(item.getName()).setVisible(model.isKey(item));
        
        form = getElement("base");
        form.importModel(model);
        for (DocumentModelItem item : items)
            form.get(item.getName()).setVisible(!model.isKey(item));
        getTabbedItem("tabs", "basetab").setContainer(form);
        
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            model = models.get(name);
            tabletool = getTableTool(name.concat("_table"));
            tabletool.model(model);
            getTabbedItem("tabs", name).setContainer(tabletool.getContainer());
        }
    }

    public final void setTitle(String title) {
        this.title = title;
    }
}
