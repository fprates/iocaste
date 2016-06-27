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
import org.iocaste.shell.common.NodeList;
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
        Container logo, options;
        ViewContext viewctx;
        NodeList inner, login;
        NodeListItem loginitem;
        AbstractPageBuilder function;
        User user;
        
        context.view.add(new HeaderLink(
                "shortcut icon", "/iocaste-shell/images/favicon.ico"));
        
        container.setStyleClass("nc_container");
        inner = new NodeList(container, "nc_inner");
        inner.setStyleClass("nc_inner_container");
        
        logo = new StandardContainer(
                new NodeListItem(inner, "nc_inner_logo"), "logo");
        logo.setStyleClass("main_logo");

        title = context.view.getTitle();
        name = (title.text != null)? title.text : context.view.getAppName();
        text = new Text(
                new NodeListItem(inner, "nc_inner_title"), "this");
        text.setStyleClass("nc_title");
        text.setText(name, title.args);
        
        buttonbar = context.view.getElement("actionbar");
        buttonbar.setStyleClass("nc_hide");
        
        iocaste = new Iocaste(context.function);
        if (!iocaste.isConnected())
            return;
        
        user = iocaste.getUserData(iocaste.getUsername());
        login = new NodeList(
                new NodeListItem(inner, "nc_inner_login"), "login");
        login.setStyleClass("nc_login");
        
        loginitem = new NodeListItem(login, "login_user");
        loginitem.setStyleClass("nc_login_item");
        link = new Link(loginitem, "user", "user");
        link.setText("");
        link.setAction(setElementDisplay("login_options", "inline"));
        link.setAbsolute(true);
        text = new Text(link, "username");
        text.setTag("span");
        text.setText(user.getFirstname());
        text.setStyleClass("nc_usertext");
        
        loginitem = new NodeListItem(login, "login_options");
        loginitem.setStyleClass("nc_login_item");
        loginitem.addEvent("style", "display:none");
        options = new StandardContainer(loginitem, "options");
        options.setStyleClass("nc_login_menu");
        link = new Link(options, "logout", "logout");
        link.setCancellable(true);
        
        new VirtualControl(container, "back").setCancellable(true);
        
        page = context.view.getPageName();
        viewctx = context.getView();
        viewctx.put("logout", new Logout());
        viewctx.put("back", new Back());
        function = (AbstractPageBuilder)context.function;
        function.register(page, "logout", viewctx);
        function.register(page, "back", viewctx);
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
    
    private final String setElementDisplay(String id, String display) {
        StringBuilder sb;
        
        sb = new StringBuilder("javascript:setElementDisplay('").
                append(id).append("', '").append(display).append("');");
        return sb.toString();
    }
}
