package org.iocaste.appbuilder.common;

import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewCustomAction;

public class NavControl {
    private PageBuilderContext context;
    private Container container;
    private StandardContainer buttonbar;
    private Text title;
    
    public NavControl(Form container, PageBuilderContext context) {
        this.context = context;
        this.container = new StandardContainer(container, "navcontrol_cntnr");
        build(context);
    }
    
    public final void add(String action) {
        Text text;
        Link link;
        
        if (buttonbar == null) {
            buttonbar = buildButtonBar();
        } else {
            text = new Text(buttonbar, action.concat("_separator"));
            text.setText("|");
            text.setStyleClass("nc_nav_separator");
        }
        
        link = new Link(buttonbar, action, action);
        link.setStyleClass("nc_nav_action");
    }
    
    private final void build(AbstractContext context) {
        Text text;
        Link link;
        String name, titletext;
        Iocaste iocaste;
        PageStackItem[] positions;
        User user;
        AbstractPage page;
        StyleSheet stylesheet = context.view.styleSheetInstance();

        stylesheet.clone(".nc_text", ".text");
        stylesheet.put(".nc_text", "display", "inline");
        
        stylesheet.clone(".nc_usertext", ".text");
        stylesheet.put(".nc_usertext", "display", "inline");
        stylesheet.put(".nc_usertext", "text-align", "right");
        stylesheet.put(".nc_usertext", "right", "0px");
        stylesheet.put(".nc_usertext", "position", "absolute");
        
        cloneLink(stylesheet, ".nc_nav_link:hover", ".link:hover");
        cloneLink(stylesheet, ".nc_nav_link:link", ".link:link");
        cloneLink(stylesheet, ".nc_nav_link:active", ".link:active");
        cloneLink(stylesheet, ".nc_nav_link:visited", ".link:visited");

        page = (AbstractPage)context.function;
        positions = new Shell(context.function).getPagesPositions();        
        for (PageStackItem position : positions) {
            name = new StringBuilder(position.getApp()).
                    append(".").append(position.getPage()).toString();

            if (name.equals("iocaste-login.authentic"))
                continue;
            
            /*
             * esse link pode cancelar o processamento de entradas,
             * portanto não adianta adicionar parâmetro. vamos guardar
             * a posição no próprio handler.
             */
            page.register(name, new NavControlCustomAction(name));
            titletext = position.getTitle();
            
            link = new Link(container, name, name);
            link.setStyleClass("nc_nav_link");
            link.setText((titletext == null)? name : titletext);
            link.setCancellable(true);
            
            text = new Text(container, name.concat(".separator"));
            text.setText("&gt;");
            text.setStyleClass("nc_text");
        }

        iocaste = new Iocaste(context.function);
        name = context.view.getTitle();
        if (name == null)
            name = iocaste.getCurrentApp();
        
        title = new Text(container, "this");
        title.setStyleClass("nc_text");
        title.setText(name);
        
        user = iocaste.getUserData(iocaste.getUsername());
        text = new Text(container, "username");
        text.setText(user.getFirstname());
        text.setStyleClass("nc_usertext");
    }
    
    private final StandardContainer buildButtonBar() {
        StandardContainer buttonbar;
        StyleSheet stylesheet = context.view.styleSheetInstance();
        
        stylesheet.newElement(".nc_nav_buttonbar");
        stylesheet.put(".nc_nav_buttonbar", "background-color", "#aaaaaa");
        stylesheet.put(".nc_nav_buttonbar", "width", "100%");
        stylesheet.put(".nc_nav_buttonbar", "border-style", "none");
        stylesheet.put(".nc_nav_buttonbar", "display", "block");
        stylesheet.put(".nc_nav_buttonbar", "padding", "2px");
        
        cloneLink(stylesheet, ".nc_nav_action:hover", ".link:hover");
        stylesheet.put(".nc_nav_action:hover", "color", "#ffffff");
        stylesheet.put(".nc_nav_action:hover", "font-size", "10pt");
        cloneLink(stylesheet, ".nc_nav_action:link", ".link:link");
        stylesheet.put(".nc_nav_action:link", "color", "#ffffff");
        stylesheet.put(".nc_nav_action:link", "font-size", "10pt");
        cloneLink(stylesheet, ".nc_nav_action:active", ".link:active");
        stylesheet.put(".nc_nav_action:active", "color", "#ffffff");
        stylesheet.put(".nc_nav_action:active", "font-size", "10pt");
        cloneLink(stylesheet, ".nc_nav_action:visited", ".link:visited");
        stylesheet.put(".nc_nav_action:visited", "color", "#ffffff");
        stylesheet.put(".nc_nav_action:visited", "font-size", "10pt");
        
        stylesheet.newElement(".nc_nav_separator");
        stylesheet.put(".nc_nav_separator", "color", "#ffffff");
        stylesheet.put(".nc_nav_separator", "font-size", "10pt");
        stylesheet.put(".nc_nav_separator", "display", "inline");
        
        stylesheet.newElement(".nc_nav_submit");
        stylesheet.put(".nc_nav_submit", "color", "#aaaaaa");
        stylesheet.put(".nc_nav_submit", "font-size", "10pt");
        stylesheet.put(".nc_nav_submit", "border-style", "none");
        stylesheet.put(".nc_nav_submit", "background-color", "#aaaaaa");
        stylesheet.put(".nc_nav_submit", "padding", "0px");
        stylesheet.put(".nc_nav_submit", "font-family", "sans-serif");
        stylesheet.put(".nc_nav_submit", "vertical-align", "middle");
        
        buttonbar = new StandardContainer(container, "navbar.container");
        buttonbar.setStyleClass("nc_nav_buttonbar");
        
        return buttonbar;
    }
    
    private final void cloneLink(
            StyleSheet stylesheet, String to, String from) {
        stylesheet.clone(to, from);
        stylesheet.put(to, "display", "inline");
        stylesheet.put(to, "text-decoration", "none");
        stylesheet.put(to, "padding", "3px");
        stylesheet.put(to, "vertical-align", "middle");
    }
    
    public final void setTitle(String title) {
        this.title.setText(title);
        context.view.setTitle(title);
    }
    
    public final void submit(String action) {
        Text text;
        Button button;
        
        if (buttonbar == null) {
            buttonbar = buildButtonBar();
        } else {
            text = new Text(buttonbar, action.concat("_separator"));
            text.setText("|");
            text.setStyleClass("nc_nav_separator");
        }
        
        button = new Button(buttonbar, action);
        button.setSubmit(true);
        button.setStyleClass("nc_nav_submit");
    }

}

class NavControlCustomAction implements ViewCustomAction {
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
        PageBuilderContext _context = (PageBuilderContext)context;
        AbstractPage page = (AbstractPage)_context.function;
        page.backTo(position);
        _context.view.setReloadableView(true);
        _context.view.setInitialize(false);
        context.function.keepView();
    }
    
}
