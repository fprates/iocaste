package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;

public class CModelViewerContext extends AbstractExtendedContext {
    public Object id, ns;
    public String redirect; 
    public ComplexDocument document;
    public AppBuilderLink link;
    public Map<String, String> models;
    
    public CModelViewerContext(PageBuilderContext context) {
        super(context);
        models = new HashMap<>();
    }
}
