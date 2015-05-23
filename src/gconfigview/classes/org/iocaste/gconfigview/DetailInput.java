package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;

public class DetailInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void init(PageBuilderContext context) {
        String name, value;
        int type;
        Context extcontext;
        
        extcontext = getExtendedContext();
        for (ExtendedObject object : extcontext.objects) {
            name = object.getst("NAME");
            type = object.geti("TYPE");
            value = object.get("VALUE");
            
            switch (type) {
            case DataType.BOOLEAN:
                dfset("package.config", name, Boolean.parseBoolean(value));
                break;
            default:
                dfset("package.config", name, value);
                break;
            }
        }
        
    }

}
