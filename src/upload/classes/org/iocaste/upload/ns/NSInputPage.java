package org.iocaste.upload.ns;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.upload.main.Upload;

public class NSInputPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new NSInputSpec());
        set(new NSInputConfig());
        submit("continuesel", new ContinueSelect());
        put("upload", new Upload());
    }
    
}