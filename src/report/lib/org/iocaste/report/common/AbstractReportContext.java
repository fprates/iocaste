package org.iocaste.report.common;

import java.util.ArrayList;
import java.util.Collection;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.report.common.export.AbstractOutputExport;

public abstract class AbstractReportContext implements ExtendedContext {
    public ExtendedObject object;
    public Collection<ExtendedObject> items;
    public AbstractOutputExport export;
    
    public AbstractReportContext() {
        items = new ArrayList<>();
    }
}
