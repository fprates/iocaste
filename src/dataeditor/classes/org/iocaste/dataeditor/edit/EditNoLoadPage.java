package org.iocaste.dataeditor.edit;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.dataeditor.ItemsInput;
import org.iocaste.dataeditor.OutputSpec;
import org.iocaste.dataeditor.StyleSettings;

public class EditNoLoadPage extends AbstractPanelPage {
    
    @Override
    public void execute() {
        set(new OutputSpec());
        set(new EditConfig());
        set(new ItemsInput());
        set(new StyleSettings());
        action("new", new NewEntry());
        action("edit", new EditEntry());
        action("save", new Save());
        
        update();
    }
}