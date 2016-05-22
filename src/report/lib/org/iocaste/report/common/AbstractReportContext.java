package org.iocaste.report.common;

import java.util.ArrayList;
import java.util.Collection;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.report.common.export.AbstractOutputExport;

public abstract class AbstractReportContext extends AbstractExtendedContext {
    public ExtendedObject object;
    public Collection<ExtendedObject> items;
    public AbstractOutputExport export;
    
    public AbstractReportContext(PageBuilderContext context) {
        super(context);
        items = new ArrayList<>();
    }
}
