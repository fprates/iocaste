package org.iocaste.report.common;

import java.util.Collection;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ExtendedObject;

public abstract class AbstractReportContext implements ExtendedContext {
    public ExtendedObject object;
    public Collection<ExtendedObject> items;
}
