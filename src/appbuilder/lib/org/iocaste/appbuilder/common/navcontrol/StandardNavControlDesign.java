package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewTitle;

public class StandardNavControlDesign implements NavControlDesign {
    protected StandardContainer buttonbar;
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
        Container trackbar;
//        User user;
        
        this.container = container;
        container.setStyleClass("nc_container");
        iocaste = new Iocaste(context.function);
        if (iocaste.isConnected()) {
            if (shell == null);
                shell = new Shell(context.function);
                
            positions = shell.getPagesPositions();
            if (loginapp == null)
                loginapp = shell.getLoginApp();
            
            trackbar = new StandardContainer(container, "nc_trackbar");
            trackbar.setStyleClass("nc_trackbar");
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
        }
        
        name = context.view.getTitle().text;
        if (name == null)
            name = iocaste.getCurrentApp();
        
        text = new Text(container, "this");
        text.setStyleClass("nc_title");
        text.setText(name);
        
//        user = iocaste.getUserData(iocaste.getUsername());
//        text = new Text(container, "username");
//        text.setText(user.getFirstname());
//        text.setStyleClass("nc_usertext");
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.navcontrol.NavControlDesign#
     *    buildButton(java.lang.String)
     */
    @Override
    public void buildButton(String action, NavControlButton buttoncfg) {
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
        button.setSubmit(buttoncfg.type != NavControl.NORMAL);
        button.setStyleClass(buttoncfg.style);
        button.setNoScreenLock(buttoncfg.nolock);
    }
    
    /**
     * 
     * @return
     */
    protected final StandardContainer buildButtonBar() {
        StandardContainer buttonbar;
        
        buttonbar = new StandardContainer(container, "navbar.container");
        buttonbar.setStyleClass("nc_nav_buttonbar");
        
        return buttonbar;
    }
    
    public static String getAddress(PageStackItem position) {
        return new StringBuilder(position.getApp()).
                append(".").
                append(position.getPage()).toString();
    }
}
