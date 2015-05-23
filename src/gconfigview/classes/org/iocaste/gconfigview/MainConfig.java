package org.iocaste.gconfigview;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.Colors;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class MainConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public MainConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        InputComponent input;
        DataForm form;
        Context extcontext;
        Map<String, String> style;
        
        style = context.view.styleSheetInstance().get(".std_panel_content");
        style.put("background-color", page.getColors().get(Colors.CONTENT_BG));
        style.put("width", "100%");
        style.put("height", "100%");
        
        extcontext = getExtendedContext();
        
        form = getElement("package");
        form.importModel(extcontext.globalcfgmodel);
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("NAME");
        input.setObligatory(true);
        input.setVisible(true);
        
        context.view.setFocus(input);
        getNavControl().setTitle(Context.TITLES[Context.SELECT]);
    }

}
