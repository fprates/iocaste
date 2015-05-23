package org.iocaste.gconfigview;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.shell.common.Const;

public class Save extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        Map<String, Object> values;
        ExtendedObject object = getdf("package.config");
        
        values = new HashMap<>();
        for (DocumentModelItem item : object.getModel().getItens())
            values.put(item.getName(), object.get(item));
        
        extcontext = getExtendedContext();
        new GlobalConfig(context.function).set(extcontext.appname, values);
        message(Const.STATUS, "save.successful");
    }

}
