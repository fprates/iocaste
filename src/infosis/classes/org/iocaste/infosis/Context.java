package org.iocaste.infosis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;

public class Context extends AbstractExtendedContext {
    public String title;
    public DocumentModel model;
    public List<Map<String, Object>> users;
    public List<ExtendedObject> report;
    
    public Context(PageBuilderContext context) {
        super(context);
        report = new ArrayList<>();
    }
}
