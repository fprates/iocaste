package org.iocaste.workbench.common.engine;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.protocol.utils.ConversionRules;
import org.iocaste.protocol.utils.XMLConversion;

public class ApplicationEngine extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) {
        StandardPanel panel;
        Iocaste iocaste;
        byte[] buffer;
        ConversionResult result;
        ConversionRules mapping;
        Context extcontext;
        String viewname;
        XMLConversion conversor = new XMLConversion(context.function);
        
        iocaste = new Iocaste(context.function);
        buffer = iocaste.getMetaContext(context.view.getAppName(), "views.xml");

        mapping = new ConversionRules();
        mapping.add("views", "view");
        mapping.add("views.view.spec", "item");
        mapping.add("views.view.config", "item");
        mapping.add("views.view.input", "item");
        mapping.add("views.view.config", "item");
        mapping.setType("views.view.config.item.type", int.class);
        
        extcontext = new Context(context);
        result = conversor.conversion(new String(buffer), mapping);
        
        panel = new StandardPanel(context);
        for (ConversionResult view : result.getList("views")) {
            viewname = view.getst("views.view.name");
            extcontext.views.put(viewname, view);
            panel.instance(viewname, new AutomatedPage(), extcontext);
        }
    }
    
    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        installObject("auto", new AutomatedInstall(this, defaultinstall));
    }

}

class AutomatedPage extends AbstractPanelPage {
    
    @Override
    public void execute() {
        set(new AutomatedSpec());
        set(new AutomatedConfig());
    }
    
}
