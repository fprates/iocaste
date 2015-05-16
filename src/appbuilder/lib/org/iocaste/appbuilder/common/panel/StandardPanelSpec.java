package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControlCustomAction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Shell;

public class StandardPanelSpec extends AbstractViewSpec {
    private AbstractPanelPage page;
    private Iocaste iocaste;
    private Map<String, PageStackItem> positions;
    
    public StandardPanelSpec(AbstractPanelPage page) {
        this.page = page;
    }

    @Override
    protected final void execute(PageBuilderContext context) {
        AbstractPanelSpec extspec;
        String name, submit;
        PageStackItem[] positions;
        
        form("main");
        navcontrol("main");
        
        standardcontainer("main", "context");
        dashboard("context", "actions");
        for (String action : page.getActions())
            dashboarditem("actions", action);
        submit = page.getSubmit();
        if (submit != null)
            dashboarditem("actions", submit);
        
        dashboard("context", "navigation");
        if (iocaste == null)
            iocaste = new Iocaste(context.function);
        
        if (iocaste.isConnected()) {
            positions = new Shell(context.function).getPagesPositions();
            for (PageStackItem position : positions) {
                name = new StringBuilder(position.getApp()).
                        append(".").append(position.getPage()).toString();

                if (name.equals("iocaste-login.authentic"))
                    continue;
                
                context.function.register(
                        name, new NavControlCustomAction(name));
                
                this.positions.put(name, position);
                dashboarditem("navigation", name);
            }
        }
        
        dashboard("context", "dashcontext");
        
        standardcontainer("main", "content");
        extspec = page.getSpec();
        if (extspec == null)
            return;
        
        spec("content", extspec);
        for (PanelPageItem ctxitem : extspec.getContextItems())
            dashboardgroup("dashcontext", ctxitem.dashctx);
    }
    
    public final void setPositions(Map<String, PageStackItem> positions) {
        this.positions = positions;
    }
}
