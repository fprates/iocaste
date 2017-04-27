package org.iocaste.runtime.common.navcontrol;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.shell.common.HeaderLink;

public class StandardNavControlConfig extends AbstractViewConfig<Context> {
    public Object[][] ncconfig;
    
    @Override
    protected final void execute(Context context) {
        ToolData text, link, loginitem, back;
        String name, pagename;
//        User user;
        NavControl navcontrol;
        NavControlButton navbutton;
        AbstractPage page;
//        Iocaste iocaste;
        
        page = context.getPage();
        page.add(new HeaderLink(
                "shortcut icon", "/iocaste-kernel/images/favicon.ico"));
        
        navcontrol = page.getNavControl();
        navbutton = null;
        for (String key : page.getActions())
            navbutton = setNavButton(navcontrol, key);
        
        name = page.getSubmit();
        if (name != null)
            navbutton = setNavButton(navcontrol, name);
        
        setNavControlConfig(context);
        if (navbutton != null)
            getTool("actionbar").style = "nc_nav_buttonbar";
        
        link = getTool("nc_user");
        if (link == null)
            return;
        link.text = "";
        link.absolute = true;
//        
//        iocaste = context.getIocaste();
//        if (!iocaste.isConnected())
//            return;
//        
//        user = iocaste.getUserData(iocaste.getUsername());
//        getTool("nc_username").tag = "span";
//        text.setText(user.getFirstname());
//
//        getTool("nc_login_options").attributes.put("style", "display:none");
//        
//        viewctx.put("logout", new Logout());
//
//        getTool("back").cancellable = true;
//        page = context.getPage();
//        if (viewctx.getActionHandler("back") == null) {
//            viewctx.put("back", new Back());
//            function = (AbstractPageBuilder)context.function;
//            function.register(page, "logout", viewctx);
//            function.register(page, "back", viewctx);
//        }
//        
//        if (context.messages == null)
//            context.messages = new MessageSource();
//        context.messages.instance("pt_BR");
//        context.messages.put("pt_BR", "nc_logout", "Log out");
    }
    
//    public static String getAddress(PageStackItem position) {
//        return new StringBuilder(position.getApp()).
//                append(".").
//                append(position.getPage()).toString();
//    }
    
    private final NavControlButton setNavButton(
            NavControl navcontrol, String name) {
    	ToolData tooldata;
        NavControlButton navbutton;
        
        navbutton = navcontrol.get(name);
        tooldata = getTool(name);
        tooldata.submit = (navbutton.type != NavControl.NORMAL);
        tooldata.nolock = navbutton.nolock;
        tooldata.style = "nc_button";
        return navbutton;
    }
    
    private final void setNavControlConfig(Context context) {
        ToolData tooldata;
        String name;
        
        for (int i = 0; i < ncconfig.length; i++) {
            name = (String)ncconfig[i][0];
            tooldata = getTool(name);
            if (tooldata == null)
            	continue;
            tooldata.style = (String)ncconfig[i][1];
            tooldata.actionname = (String)ncconfig[i][2];
            tooldata.cancellable = (boolean)ncconfig[i][3];
        }
    }
}
