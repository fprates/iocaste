package org.iocaste.workbench.common.engine;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.protocol.utils.ConversionRules;
import org.iocaste.protocol.utils.XMLConversion;

public class ApplicationEngine extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
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
        mapping.add("views.view.action", "item");
        mapping.setType("views.view.config.item.type", int.class);
        mapping.setType("views.view.action.item.type", int.class);
        
        extcontext = new Context(context);
        result = conversor.conversion(new String(buffer), mapping);
        
        panel = new StandardPanel(context);
        for (ConversionResult view : result.getList("views")) {
            viewname = view.getst("views.view.name");
            extcontext.views.put(viewname, view);
            panel.instance(viewname, new AutomatedPage(view), extcontext);
        }
    }
    
    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        installObject("auto", new AutomatedInstall(this, defaultinstall));
    }

}

class AutomatedPage extends AbstractPanelPage {
    private ConversionResult view;
    
    public AutomatedPage(ConversionResult view) {
        this.view = view;
    }
    
    @Override
    public void execute() throws Exception {
        String name, classname;
        int type;
        AbstractActionHandler handler;
        
        set(new AutomatedSpec());
        set(new AutomatedConfig());
        set(new StandardViewInput());
        
        for (ConversionResult action : view.getList("views.view.action")) {
            name = action.getst("views.view.action.item.name");
            classname = action.getst("views.view.action.item.class");
            type = action.geti("views.view.action.item.type");
            
            handler = (AbstractActionHandler)Class.forName(classname).
                    newInstance();
            switch (type) {
            case 0:
                action(name, handler);
                break;
            case 1:
                submit(name, handler);
                break;
            case 2:
                put(name, handler);
                break;
            }
        }
    }
    
}
