package org.iocaste.dataeditor.display;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.dataeditor.ItemsInput;
import org.iocaste.dataeditor.OutputSpec;
import org.iocaste.dataeditor.StyleSettings;
import org.iocaste.dataeditor.edit.Save;

public class DisplayNoLoadPage extends AbstractPanelPage {
    
    @Override
    public void execute() {
        set(new OutputSpec());
        set(new DisplayConfig());
        set(new ItemsInput());
        set(new StyleSettings());
        action("save", new Save());
        
        update();
    }
}