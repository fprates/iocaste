package org.iocaste.dataeditor.entry;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataeditor.Context;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class EntryConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        DataForm dataform;
        InputComponent input;
        DocumentModel model;
        
        getNavControl().setTitle("add.entry");
        
        extcontext = getExtendedContext();
        model = new Documents(context.function).getModel(extcontext.model);
        
        dataform = getElement("detail");
        dataform.importModel(model);
        
        if (extcontext.number != null)
            for (Element element : dataform.getElements()) {
                input = (InputComponent)element;
                if (!model.isKey(input.getModelItem()))
                    continue;
                input.setEnabled(false);
                break;
            }
        
        for (Element element : dataform.getElements()) {
            if (!element.isEnabled())
                continue;
            context.view.setFocus(element);
            return;
        }
    }
}
