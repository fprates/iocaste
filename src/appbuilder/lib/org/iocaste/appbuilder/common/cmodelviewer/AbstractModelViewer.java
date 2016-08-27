package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.panel.StandardPanel;

public abstract class AbstractModelViewer extends AbstractPageBuilder {
    public static final String CREATE = "create";
    public static final String DISPLAY = "display";
    public static final String EDIT = "edit";
    private Context extcontext;
    
    protected final AppBuilderLink getReceivedLink() {
        AppBuilderLink link;
        String entity = getParameter("name");
        
        if (entity == null)
            return null;
        
        link = new AppBuilderLink();
        link.entity = entity;
        link.cmodel = getParameter("cmodel");
        link.number = getParameter("number");
        link.numberseries = getParameter("numberseries");
        link.createview = link.entity.concat(CREATE);
        link.create1view = link.createview.concat("1");
        link.editview = link.entity.concat(EDIT);
        link.edit1view = link.editview.concat("1");
        link.displayview = link.entity.concat(DISPLAY);
        link.display1view = link.displayview.concat("1");
        
        return link;
    }
    
    @Override
    protected abstract void installConfig(
            PageBuilderDefaultInstall defaultinstall);
    
    protected final void loadManagedModule(PageBuilderContext context,
            AppBuilderLink link) {
        StandardPanel panel;
        ViewSpec selspec;
        String entityaction;
        
        if (extcontext == null)
            extcontext = new Context(context);
        
        extcontext.link = link;
        extcontext.redirect = (link.number == null)?
                link.create1view : link.edit1view;
        
        selspec = new SelectSpec();
        if (link.inputvalidate == null)
            link.inputvalidate = new InputValidate();
        panel = new StandardPanel(context);
        
        for (String action : new String[] {CREATE, EDIT, DISPLAY}) {
            if ((link.number != null) && action.equals(CREATE) &&
                    (link.entitypage.config == null))
                continue;
            
            link.entitypage.action = action;
            link.entitypage.spec = selspec;
            link.entitypage.link = link;
            
            entityaction = link.entity.concat(action);
            panel.instance(entityaction, link.entitypage, extcontext);
        }
        
        link.custompage.link = link;
        
        for (String view : new String[] {
                link.createview, link.create1view, link.edit1view}) {
            if (view.equals(link.createview) &&
                    ((link.number == null) || (link.custompage.config != null)))
                continue;
            
            panel.instance(view, link.custompage, extcontext);
        }

        link.displaypage.link = link;
        panel.instance(link.display1view, link.displaypage, extcontext);
    }
    
    /**
     * 
     * @param extcontext
     */
    protected final void setExtendedContext(Context extcontext) {
        this.extcontext = extcontext;
    }
}
