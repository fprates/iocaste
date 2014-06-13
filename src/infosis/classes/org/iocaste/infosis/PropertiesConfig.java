package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class PropertiesConfig extends AbstractViewConfig {
    private String title;
    
    public PropertiesConfig(String title) {
        this.title = title;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        NavControl navcontrol = getNavControl();
        
        navcontrol.setTitle(title);
    }

}
