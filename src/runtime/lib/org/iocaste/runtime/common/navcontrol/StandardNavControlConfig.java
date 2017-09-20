package org.iocaste.runtime.common.navcontrol;

import org.iocaste.protocol.user.User;
import org.iocaste.runtime.common.RuntimeEngine;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.tooldata.ToolData;

public class StandardNavControlConfig extends AbstractViewConfig<Context> {
    public Object[][] ncconfig;
    
    private final boolean configBar(NavControl navcontrol, AbstractPage page) {
        String name;
        NavControlButton navbutton = null;
        for (String key : page.getActions())
            navbutton = setNavButton(navcontrol, key);
        name = page.getSubmit();
        if (name != null)
            navbutton = setNavButton(navcontrol, name);
        return (navbutton != null);
    }
    
    @Override
    protected final void execute(Context context) {
        ToolData text, link;
        User user;
        NavControl navcontrol;
        AbstractPage page;
        RuntimeEngine runtime;
        MessageSource messages;
        boolean show = false;
        
        page = context.getPage();
        page.add(new HeaderLink(
                "shortcut icon", "/iocaste-kernel/images/favicon.ico"));
        
        navcontrol = page.getNavControl();
        show |= configBar(navcontrol, page);
        for (String key : page.getChildren())
            show |= configBar(navcontrol, page.getChild(key));
        
        setNavControlConfig(context);
        if (show)
            getTool("actionbar").style = "nc_nav_buttonbar";
        
        link = getTool("nc_user");
        if (link == null)
            return;
        link.text = "";
        link.absolute = true;

        text = getTool("nc_username");
        text.tag = "span";
        runtime = context.runtime();
//        if (runtime.isConnected()) {
//            user = runtime.getUserData(runtime.getUsername());
//            text.text = user.getFirstname();
//        }

        getTool("nc_login_options").attributes.put("style", "display:none");
        getTool("back").cancellable = true;
        page = context.getPage();
        
        if ((messages = context.getMessageSource()) == null)
            return;
        messages.instance("pt_BR");
        messages.put("pt_BR", "nc_logout", "Log out");
    }
    
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
