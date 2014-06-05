package org.iocaste.appbuilder;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.TableTool;

public class DisplayConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TableTool tabletool;
        Map<String, DocumentModel> models;
        DocumentModelItem[] items;
        DataForm form;
        ComplexModel cmodel = getManager().getModel();
        DocumentModel model = cmodel.getHeader();

        items = model.getItens();
        for (String name : new String[] {"head", "base"}) {
            form = getElement(name);
            form.importModel(model);
            form.setEnabled(false);
            
            switch(name) {
            case "head":
                for (DocumentModelItem item : items)
                    form.get(item.getName()).setVisible(model.isKey(item));
                break;
            case "base":
                for (DocumentModelItem item : items)
                    form.get(item.getName()).setVisible(!model.isKey(item));
                getTabbedItem("tabs", "basetab").setContainer(form);
            }
        }
        
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            model = models.get(name);
            tabletool = getTableTool(name.concat("_table"));
            tabletool.model(model);
            tabletool.setMode(TableTool.DISPLAY);
            getTabbedItem("tabs", name).setContainer(tabletool.getContainer());
            
        }
    }
}
