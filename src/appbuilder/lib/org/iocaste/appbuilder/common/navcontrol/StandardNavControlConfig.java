package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.NodeListItem;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewTitle;
import org.iocaste.shell.common.VirtualControl;

public class StandardNavControlConfig extends AbstractViewConfig {
    
    @Override
    protected final void execute(PageBuilderContext context) {
        Text text;
        Link link;
        String name, page;
        ViewTitle title;
        Iocaste iocaste;
        ViewContext viewctx;
        NodeListItem loginitem;
        AbstractPageBuilder function;
        User user;
        VirtualControl back;
        NavControl navcontrol;
        NavControlButton navbutton;
        AbstractPanelPage panel;
        
        context.view.add(new HeaderLink(
                "shortcut icon", "/iocaste-shell/images/favicon.ico"));

        title = context.view.getTitle();
        name = (title.text != null)? title.text : context.view.getAppName();
        text = getElement("this");
        text.setText(name, title.args);
        
        navcontrol = getNavControl();
        viewctx = context.getView();
        navbutton = null;
        panel = viewctx.getPanelPage();
        for (String key : panel.getActions())
            navbutton = setNavButton(navcontrol, key);
        
        name = panel.getSubmit();
        if (name != null)
            navbutton = setNavButton(navcontrol, name);
        
        setNavControlConfig(context);
        if (navbutton != null)
            getElement("actionbar").setStyleClass("nc_nav_buttonbar");
        
        link = getElement("nc_user");
        if (link == null)
            return;
        link.setText("");
        link.setAbsolute(true);
        
        iocaste = new Iocaste(context.function);
        if (!iocaste.isConnected())
            return;
        
        user = iocaste.getUserData(iocaste.getUsername());
        text = getElement("nc_username");
        text.setTag("span");
        text.setText(user.getFirstname());

        loginitem = getElement("nc_login_options");
        loginitem.addAttribute("style", "display:none");
        
        page = context.view.getPageName();
        viewctx.put("logout", new Logout());

        back = getElement("back");
        back.setCancellable(true);
        if (viewctx.getActionHandler("back") == null) {
            viewctx.put("back", new Back());
            function = (AbstractPageBuilder)context.function;
            function.register(page, "logout", viewctx);
            function.register(page, "back", viewctx);
        }
        
        if (context.messages == null)
            context.messages = new MessageSource();
        context.messages.instance("pt_BR");
        context.messages.put("pt_BR", "nc_logout", "Log out");
    }
    
    public static String getAddress(PageStackItem position) {
        return new StringBuilder(position.getApp()).
                append(".").
                append(position.getPage()).toString();
    }
    
    private final NavControlButton setNavButton(
            NavControl navcontrol, String name) {
        Button button;
        NavControlButton navbutton;
        
        navbutton = navcontrol.get(name);
        button = getElement(name);
        button.setSubmit(navbutton.type != NavControl.NORMAL);
        button.setNoScreenLock(navbutton.nolock);
        return navbutton;
    }
    
    private final void setNavControlConfig(PageBuilderContext context) {
        String name, style, action;
        Element element;
        ControlComponent control;
        boolean cancellable;
        
        for (int i = 0; i < context.ncconfig.length; i++) {
            name = (String)context.ncconfig[i][0];
            style = (String)context.ncconfig[i][1];
            action = (String)context.ncconfig[i][2];
            cancellable = (boolean)context.ncconfig[i][3];
            element = getElement(name);
            if (element == null)
                continue;
            if (style != null)
                element.setStyleClass(style);
            if (action == null)
                continue;
            control = (ControlComponent)element;
            control.setAction(action);
            control.setCancellable(cancellable);
        }
    }
}
