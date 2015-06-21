package org.iocaste.dataeditor.entry.select;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataeditor.Context;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class SelectEntryConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        DataForm dataform;
        InputComponent input;
        DocumentModel model;
        
        getNavControl().setTitle("select.entry");
        
        extcontext = getExtendedContext();
        model = new Documents(context.function).getModel(extcontext.model);
        
        dataform = getElement("selection");
        dataform.importModel(model);
        
        for (Element element : dataform.getElements()) {
            input = (InputComponent)element;
            if (model.isKey(input.getModelItem())) {
                input.setObligatory(true);
                continue;
            }
            input.setVisible(false);
        }
    }

}
