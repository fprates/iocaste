package org.iocaste.appbuilder;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.DataForm;

public class DisplayConfig extends AbstractViewConfig {
    private String cmodel;
    
    public DisplayConfig(String cmodel) {
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        TableToolData tabletool;
        Map<String, DocumentModel> models;
        DocumentModelItem[] items;
        DataForm form;
        ComplexModel cmodel = getManager(this.cmodel).getModel();
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
                break;
            }
        }
        
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            model = models.get(name);
            tabletool = getTableTool(name.concat("_table"));
            tabletool.model = model.getName();
            tabletool.mode = TableTool.DISPLAY;
        }
    }
}
