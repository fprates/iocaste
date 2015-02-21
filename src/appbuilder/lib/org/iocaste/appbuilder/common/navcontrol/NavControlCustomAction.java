package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ViewCustomAction;

public class NavControlCustomAction implements ViewCustomAction {
    private static final long serialVersionUID = -2444551337966528191L;
    private String position;
    
    public NavControlCustomAction(String position) {
        this.position = position;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.ViewCustomAction#execute(
     *    org.iocaste.shell.common.PageContext)
     */
    @Override
    public void execute(AbstractContext context) {
        ViewContext viewctx;
        PageBuilderContext _context = (PageBuilderContext)context;
        AbstractPage page = (AbstractPage)_context.function;
        
        page.backTo(position);
        viewctx = _context.getView(_context.view.getPageName());
        AbstractActionHandler.redirectContext(_context, viewctx);
    }
    
}