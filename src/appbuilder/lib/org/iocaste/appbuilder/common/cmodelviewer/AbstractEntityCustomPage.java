package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewInput;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public abstract class AbstractEntityCustomPage extends AbstractPanelPage {
    public AppBuilderLink link;
    public ViewSpec spec;
    public ViewConfig config;
    public ViewInput input;
    
    public AbstractEntityCustomPage(
            ViewSpec spec, ViewConfig config, ViewInput input) {
        this.spec = spec;
        this.config = config;
        this.input = input;
    }
    
    @Override
    public void execute() {
        set(spec);
        set(config);
        set(input);
        
        submit("validate", link.inputvalidate);
        action("save", link.save);
    }

}
