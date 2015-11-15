package org.iocaste.appbuilder.common.navcontrol;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewTitle;

public class StandardNavControlDesign implements NavControlDesign {
    protected StandardContainer buttonbar;
    private PageBuilderContext context;
    private Container container;
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
        User user;
        StyleSheet stylesheet;
        Map<String, String> style;
        
        this.container = container;
        this.context = context;
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.clone(".nc_text", ".text").put("display", "inline");
        
        style = stylesheet.clone(".nc_usertext", ".text");
        style.put("display", "inline");
        style.put("text-align", "right");
        style.put("right", "0px");
        style.put("position", "absolute");
        
        cloneLink(stylesheet, ".nc_nav_link:hover", ".link:hover");
        cloneLink(stylesheet, ".nc_nav_link:link", ".link:link");
        cloneLink(stylesheet, ".nc_nav_link:active", ".link:active");
        cloneLink(stylesheet, ".nc_nav_link:visited", ".link:visited");

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
            context.function.register(name, new NavControlCustomAction(name));
            title = position.getTitle();
            
            link = new Link(container, name, name);
            link.setStyleClass("nc_nav_link");
            link.setText((title.text == null)? name : title.text);
            link.setCancellable(true);
            
            text = new Text(container, name.concat(".separator"));
            text.setText("&gt;");
            text.setStyleClass("nc_text");
        }

        iocaste = new Iocaste(context.function);
        name = context.view.getTitle().text;
        if (name == null)
            name = iocaste.getCurrentApp();
        
        text = new Text(container, "this");
        text.setStyleClass("nc_text");
        text.setText(name);
        
        user = iocaste.getUserData(iocaste.getUsername());
        text = new Text(container, "username");
        text.setText(user.getFirstname());
        text.setStyleClass("nc_usertext");
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.navcontrol.NavControlDesign#
     *    buildButton(java.lang.String)
     */
    @Override
    public void buildButton(String action, NavControlButton buttoncfg) {
        Link link;
        Text text;
        Button button;
        
        if (buttonbar == null) {
            buttonbar = buildButtonBar();
        } else {
            text = new Text(buttonbar, action.concat("_separator"));
            text.setText("|");
            text.setStyleClass("nc_nav_separator");
        }
        
        switch (buttoncfg.type) {
        case NavControl.NORMAL:
            link = new Link(buttonbar, action, action);
            link.setStyleClass(buttoncfg.style);
            link.setNoScreenLock(buttoncfg.nolock);
            break;
        case NavControl.SUBMIT:
            button = new Button(buttonbar, action);
            button.setSubmit(true);
            button.setStyleClass(buttoncfg.style);
            button.setNoScreenLock(buttoncfg.nolock);
            break;
        }
    }
    
    /**
     * 
     * @return
     */
    protected final StandardContainer buildButtonBar() {
        StandardContainer buttonbar;
        Map<String, String> style;
        StyleSheet stylesheet = context.view.styleSheetInstance();
        
        style = stylesheet.newElement(".nc_nav_buttonbar");
        style.put("background-color", "#aaaaaa");
        style.put("width", "100%");
        style.put("border-style", "none");
        style.put("display", "block");
        style.put("padding", "2px");
        
        cloneLink(stylesheet, ".nc_nav_action:hover", ".link:hover");
        style.put("color", "#ffffff");
        style.put("font-size", "10pt");
        cloneLink(stylesheet, ".nc_nav_action:link", ".link:link");
        style.put("color", "#ffffff");
        style.put("font-size", "10pt");
        cloneLink(stylesheet, ".nc_nav_action:active", ".link:active");
        style.put("color", "#ffffff");
        style.put("font-size", "10pt");
        cloneLink(stylesheet, ".nc_nav_action:visited", ".link:visited");
        style.put("color", "#ffffff");
        style.put("font-size", "10pt");
        
        style = stylesheet.newElement(".nc_nav_separator");
        style.put("color", "#ffffff");
        style.put("font-size", "10pt");
        style.put("display", "inline");
        
        style = stylesheet.newElement(".nc_nav_submit");
        style.put("color", "#ffffff");
        style.put("font-size", "10pt");
        style.put("border-style", "none");
        style.put("background-color", "#aaaaaa");
        style.put("padding", "0px");
        style.put("font-family", "sans-serif");
        style.put("vertical-align", "middle");
        
        buttonbar = new StandardContainer(container, "navbar.container");
        buttonbar.setStyleClass("nc_nav_buttonbar");
        
        return buttonbar;
    }
    
    /**
     * 
     * @param stylesheet
     * @param to
     * @param from
     */
    private final void cloneLink(
            StyleSheet stylesheet, String to, String from) {
        Map<String, String> style;
        
        style = stylesheet.clone(to, from);
        style.put("display", "inline");
        style.put("text-decoration", "none");
        style.put("padding", "3px");
        style.put("vertical-align", "middle");
    }
    
    public static String getAddress(PageStackItem position) {
        return new StringBuilder(position.getApp()).
                append(".").
                append(position.getPage()).toString();
    }
}
