package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;

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
