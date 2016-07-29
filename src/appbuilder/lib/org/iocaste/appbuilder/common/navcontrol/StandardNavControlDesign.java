package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeListItem;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewTitle;
import org.iocaste.shell.common.VirtualControl;

public class StandardNavControlDesign implements NavControlDesign {
    protected StandardContainer buttonbar;
    protected boolean actionbarinit;
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.navcontrol.NavControlDesign#
     *    build(
     *       org.iocaste.shell.common.Container,
     *       org.iocaste.appbuilder.common.PageBuilderContext)
     */
    @Override
    public final void build(Container container, PageBuilderContext context) {
        Text text;
        Link link;
        String name, page;
        ViewTitle title;
        Iocaste iocaste;
        ViewContext viewctx;
        NodeListItem loginitem;
        AbstractPageBuilder function;
        User user;
        
        context.view.add(new HeaderLink(
                "shortcut icon", "/iocaste-shell/images/favicon.ico"));

        title = context.view.getTitle();
        name = (title.text != null)? title.text : context.view.getAppName();
        text = context.view.getElement("this");
        text.setText(name, title.args);
        
        buttonbar = context.view.getElement("actionbar");
        
        link = context.view.getElement("nc_user");
        if (link == null)
            return;
        link.setText("");
        link.setAbsolute(true);
        
        iocaste = new Iocaste(context.function);
        if (!iocaste.isConnected())
            return;
        
        user = iocaste.getUserData(iocaste.getUsername());
        text = context.view.getElement("nc_username");
        text.setTag("span");
        text.setText(user.getFirstname());

        loginitem = context.view.getElement("nc_login_options");
        loginitem.addAttribute("style", "display:none");
        
        page = context.view.getPageName();
        viewctx = context.getView();
        viewctx.put("logout", new Logout());

        new VirtualControl(container, "back").setCancellable(true);
        if (viewctx.getActionHandler("back") == null) {
            viewctx.put("back", new Back());
            function = (AbstractPageBuilder)context.function;
            function.register(page, "logout", viewctx);
            function.register(page, "back", viewctx);
        }
        
        context.messages.instance("pt_BR");
        context.messages.put("pt_BR", "nc_logout", "Log out");
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.navcontrol.NavControlDesign#
     *    buildButton(java.lang.String)
     */
    @Override
    public void buildButton(String action, NavControlButton buttoncfg) {
        Button button;

        if (!actionbarinit) {
            buttonbar.setStyleClass("nc_nav_buttonbar");
            actionbarinit = true;
        }
        
        button = new Button(buttonbar, action);
        button.setSubmit(buttoncfg.type != NavControl.NORMAL);
        button.setNoScreenLock(buttoncfg.nolock);
    }
    
    public static String getAddress(PageStackItem position) {
        return new StringBuilder(position.getApp()).
                append(".").
                append(position.getPage()).toString();
    }
}
