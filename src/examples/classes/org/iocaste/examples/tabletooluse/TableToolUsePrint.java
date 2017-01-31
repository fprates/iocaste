package org.iocaste.examples.tabletooluse;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.examples.Context;
import org.iocaste.shell.common.Shell;

public class TableToolUsePrint extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String line;
        ExtendedObject[] items = tableitemsget("items");
        Context extcontext = getExtendedContext();
        
        extcontext.ttuse.items.clear();
        for (ExtendedObject item : items) {
            if (Documents.isInitial(item) || Documents.isInitial(item, "NAME"))
                continue;
            line = new StringBuilder(item.getst("NAME")).
                    append(" casou-se em ").
                    append(Shell.toString(context.view, item, "MARRIED_ON")).
                    append(" e tem ").
                    append(item.geti("AGE")).
                    append(" anos.").toString();
            extcontext.ttuse.items.add(line);
        }
    }

}
