package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public class PropertiesInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        reportset("properties", extcontext.report.
                toArray(new ExtendedObject[0]));
    }

}
