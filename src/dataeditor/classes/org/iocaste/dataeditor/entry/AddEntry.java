package org.iocaste.dataeditor.entry;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataeditor.Context;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class AddEntry extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        ExtendedObject object = getdf("detail");

        if (object == null) {
            back();
            return;
        }
        
        extcontext = getExtendedContext();
        if (extcontext.object == null) {
            extcontext.items.add(object);
            back();
            return;
        }
        
        for (ExtendedObject item : extcontext.items) {
            if (!item.equals(object))
                continue;
            Documents.move(item, object);
            break;
        }
        
        back();
    }

}
