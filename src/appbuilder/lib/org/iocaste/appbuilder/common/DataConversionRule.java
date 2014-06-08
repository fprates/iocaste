package org.iocaste.appbuilder.common;

import org.iocaste.documents.common.ExtendedObject;

public interface DataConversionRule {

    public abstract void afterConversion(ExtendedObject object);
    
    public abstract void beforeConversion(ExtendedObject object);
}
