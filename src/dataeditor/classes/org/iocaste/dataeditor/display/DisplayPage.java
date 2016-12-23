package org.iocaste.dataeditor.display;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.dataeditor.ItemsInput;
import org.iocaste.dataeditor.Load;
import org.iocaste.dataeditor.OutputSpec;
import org.iocaste.dataeditor.StyleSettings;

public class DisplayPage extends AbstractPanelPage {
    
    @Override
    public void execute() throws Exception {
        set(new OutputSpec());
        set(new DisplayConfig());
        set(new ItemsInput());
        set(new StyleSettings());
        put("load", new Load("main"));

        getExtendedContext().getContext().run("main", "load");
        update();
    }
}