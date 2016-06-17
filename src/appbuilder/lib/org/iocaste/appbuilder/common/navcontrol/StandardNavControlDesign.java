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
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewTitle;

public class StandardNavControlDesign implements NavControlDesign {
    protected StandardContainer buttonbar;
    private Shell shell;
    private String loginapp;
    
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
        String name;
        ViewTitle title;
        Iocaste iocaste;
        PageStackItem[] positions;
        Container trackbar, inner, logo, options;
        ViewContext viewctx;
        NodeList login;
        NodeListItem loginitem;
        User user = null;
        
        container.setStyleClass("nc_container");
        inner = new StandardContainer(container, "nc_inner");
        inner.setStyleClass("nc_inner_container");
        trackbar = new StandardContainer(inner, "nc_trackbar");
        trackbar.setStyleClass("nc_trackbar");
        iocaste = new Iocaste(context.function);
        if (iocaste.isConnected()) {
            if (shell == null);
                shell = new Shell(context.function);
                
            positions = shell.getPagesPositions();
            if (loginapp == null)
                loginapp = shell.getLoginApp();
            
            for (PageStackItem position : positions) {
                name = getAddress(position);
                if (name.equals(loginapp))
                    continue;
                
                /*
                 * esse link pode cancelar o processamento de entradas,
                 * portanto não adianta adicionar parâmetro. vamos guardar
                 * a posição no próprio handler.
                 */
                context.function.register(
                        name, new NavControlCustomAction(name));
                title = position.getTitle();
                
                link = new Link(trackbar, name, name);
                link.setStyleClass("nc_nav_link");
                link.setText((title.text == null)? name : title.text);
                link.setCancellable(true);
                
                text = new Text(trackbar, name.concat(".separator"));
                text.setText("&gt;");
                text.setStyleClass("nc_text");
            }
            user = iocaste.getUserData(iocaste.getUsername());
        }
        
        context.view.add(new HeaderLink(
                "shortcut icon", "/iocaste-shell/images/favicon.ico"));
        logo = new StandardContainer(inner, "logo");
        logo.setStyleClass("main_logo");

        title = context.view.getTitle();
        name = (title.text != null)? title.text : context.view.getAppName();
        text = new Text(inner, "this");
        text.setStyleClass("nc_title");
        text.setText(name, title.args);
        
        buttonbar = new StandardContainer(inner, "navbar.container");
        buttonbar.setStyleClass("nc_nav_buttonbar");
        if (user != null) {
            login = new NodeList(buttonbar, "login");
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
            
            viewctx = context.getView();
            viewctx.put("logout", new Logout());
            ((AbstractPageBuilder)context.function).register(
                    context.view.getPageName(), "logout", viewctx);
        }
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.navcontrol.NavControlDesign#
     *    buildButton(java.lang.String)
     */
    @Override
    public void buildButton(String action, NavControlButton buttoncfg) {
        Button button;

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
