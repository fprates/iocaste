package org.iocaste.external;

import org.iocaste.appbuilder.common.cmodelviewer.AbstractEntityPage;
import org.iocaste.appbuilder.common.cmodelviewer.SelectConfig;

public class ExternalEntityPage extends AbstractEntityPage {

    public ExternalEntityPage() {
        super(new SelectConfig("XTRNL_STRUCTURE"), null, null);
    }
    
}