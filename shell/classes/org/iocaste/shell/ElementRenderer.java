package org.iocaste.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.FormItem;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class ElementRenderer {
    private String msgtext;
    private Const msgtype;
    private String[] script;
    private String username;
    private MessageSource messages;
    
    public ElementRenderer() {
        String line;
        List<String> lines = new ArrayList<String>();
        InputStream is = this.getClass().getResourceAsStream("/META-INF/shell.js");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        
        try {
            while ((line = reader.readLine()) != null)
                lines.add(line);
            
            reader.close();
            is.close();
        } catch (IOException e) {
            new RuntimeException(e);
        }
        
        script = lines.toArray(new String[0]);
    }
    
    public final void setMessageType(Const msgtype) {
        this.msgtype = msgtype;
    }
    
    /**
     * 
     * @param text
     * @param button
     */
    private final void renderButton(List<String> html, Button button) {
        String inputtext;
        String text_ = button.getText();
        String name = button.getName();
        
        if (!button.isSubmit())
            inputtext = "<input type=\"button\" name=\"";
        else
            inputtext = "<input type=\"submit\" name=\"";
        
        html.add(new StringBuffer(inputtext).append(name).
                append("\" value=\"").append(messages.get(text_, text_)).
                append("\" onClick=\"defineAction('").append(name).
                append("')\"/>").toString());
    }

    /**
     * 
     * @param text
     * @param container
     */
    private final void renderContainer(List<String> html, Container container) {
        switch (container.getType()) {
        case FORM:
            renderForm(html, (Form)container);
            
            break;
            
        case TABLE:
            html.add("<table>");
            renderElements(html, container.getElements());
            html.add("</table>");
            
            break;
        
        case MENU:
        case STANDARD_CONTAINER:
            html.add("<div>");
            renderElements(html, container.getElements());
            html.add("</div>");
            
            break;
        }
        
    }
    
    /**
     * 
     * @param text
     * @param element
     */
    private final void renderElement(List<String> html, Element element) {
        switch (element.getType()) {
        case TABLE_ITEM:
            renderTableItem(html, (TableItem)element);
            
            break;
        
        case MENU_ITEM:
            renderMenuItem(html, (MenuItem)element);
            
            break;
            
        case TEXT:
            renderText(html, (Text)element);
            
            break;
            
        case TEXT_FIELD:
            renderTextField(html, (TextField)element);
            
            break;
            
        case BUTTON:
            renderButton(html, (Button)element);
            
            break;
            
        case LINK:
            renderLink(html, (Link)element);
            
            break;
        
        case PARAMETER:
            renderParameter(html, (Parameter)element);
            
            break;
            
        default:
            if (element.isContainable())
                renderContainer(html, (Container)element);
        }
    }
    
    /**
     * 
     * @param text
     * @param elements
     */
    private final void renderElements(List<String> html, Element[] elements) {
        for (Element element : elements) 
            renderElement(html, element);
    }

    /**
     * 
     * @param html
     * @param form
     */
    private final void renderForm(List<String> html, Form form) {
        Const type;
        Text text;
        Component component;
        String inputname;
        Button button;
        FormItem formitem;
        TableItem tableitem;
        Table table = new Table(null, 2, null);
        
        for (Element element : form.getElements()) {
            if (element.getType() != Const.FORM_ITEM) {
                renderElement(html, element);
                continue;
            }
            
            tableitem = new TableItem(table, null);
            formitem = (FormItem)element;
            inputname = formitem.getName();
            
            text = new Text(null, inputname);
            text.setText(messages.get(inputname, inputname));
            
            type = formitem.getComponentType();
            
            switch (type) {
            case TEXT_FIELD:
            case PASSWORD:
                component = new TextField(null, inputname);
                component.setStyleClass(formitem.getStyleClass());
                
                if (type == Const.TEXT_FIELD)
                    ((TextField)component).setPassword(false);
                else
                    ((TextField)component).setPassword(true);
                
                break;
            default:
                component = null;
            }
            
            tableitem.add(text);
            tableitem.add(component);
        }
                
        renderContainer(html, table);
        
        for (String action : form.getActions()) {
            button = new Button(form, action);
            button.setSubmit(true);
            button.setText(messages.get(action, action));
            
            renderButton(html, button);
        }
    }
    
    /**
     * 
     * @param text
     * @param link
     */
    private final void renderLink(List<String> html, Link link) {
        Map<Parameter, String> parameters;
        StringBuffer sb;
        
        if (link.isAbsolute())
            sb = new StringBuffer("<a href=\"");
        else
            sb = new StringBuffer("<a href=\"index.html?action=");
        
        sb.append(link.getAction());
        
        parameters = link.getParametersMap();
        
        if (parameters.size() > 0)
            for (Parameter parameter: parameters.keySet())
                sb.append("&").append(parameter.getName()).append("=").
                        append(parameters.get(parameter));

        sb.append("\">").append(link.getText()).append("</a>");
        
        html.add(sb.toString());
    }
    
    /**
     * 
     * @param html
     * @param menuitem
     */
    private void renderMenuItem(List<String> html, MenuItem menuitem) {
        Menu menu = (Menu)menuitem.getContainer();
        Link link = new Link(null, null, menu.getAction());
        
        link.setText(menuitem.getText());
        link.add(menu.getParameter(), menuitem.getFunction());
        
        renderLink(html, link);
    }
    
    /**
     * 
     * @param html
     * @param parameter
     */
    private void renderParameter(List<String> html, Parameter parameter) {
        String name = parameter.getName();
        StringBuffer sb = new StringBuffer("<input type=\"hidden\" name=\"").
                append(name).append("\" id=\"").append(name).append("\"/>");
        html.add(sb.toString());
    }
    
    /**
     * 
     * @param text
     * @param item
     */
    private final void renderTableItem(List<String> html, TableItem item) {
        html.add("<tr>");
        
        for (Element element : item.getElements()) {
            html.add("<td>");
            renderElement(html, element);
            html.add("</td>");
        }
        
        html.add("</tr>");
    }
    
    /**
     * 
     * @param text
     * @param text_
     */
    private final void renderText(List<String> html, Text text_) {
        String value = messages.get(text_.getText(), text_.getText());
        
        html.add(new StringBuffer("<p>").append(value).append("</p>").
                toString());
    }
    
    /**
     * 
     * @param text
     * @param textfield
     */
    private final void renderTextField(List<String> html, TextField textfield) {
        String inputtext;
        String name = textfield.getName();
        
        if (!textfield.isPassword())
            inputtext = "<input type=\"text\" name=\"";
        else
            inputtext = "<input type=\"password\" name=\"";
        
        html.add(new StringBuffer(inputtext).append(name).
                append("\" class=\"").append(textfield.getStyleClass()).
                append("\" id=\"").append(name).append("\"/>").toString());
    }
    
    /**
     * 
     * @param html
     * @param vdata
     * @param script
     */
    private final void renderJavaScript(
            List<String> html, ViewData vdata, String[] script) {
        Component focus = vdata.getFocus();
        
        html.add("<script type=\"text/javascript\">");
        html.add("function initialize() {");
        
        if (focus != null)
            html.add(new StringBuffer("document.getElementById('").
                    append(focus.getName()).append("').focus();").toString());

        html.add("}");
        
        for (String line : script)
            html.add(line);
        html.add("</script>");
    }
    
    /**
     * 
     * @param text
     * @param title
     */
    private final void renderHeader(List<String> html, ViewData vdata) {
        String title = vdata.getTitle();
        
        html.add("<head>");
        html.add("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
        
        if (title == null)
            title = "Iocaste";
        
        html.add(new StringBuffer("<title>").append(title).
                append("</title>").toString());
        renderJavaScript(html, vdata, script);
        html.add("</head>");
    }
    
    /**
     * 
     * @param text
     */
    private final void renderMessage(List<String> html) {
        StringBuffer message;
        
        message = new StringBuffer("<div>");
        
        html.add("<div><p>");
        switch (msgtype) {
        case WARNING:
            message.append("Aviso: ");
        case ERROR:
            message.append("Erro: ");
        }
        
        html.add(message.append(msgtext).append("</p></div>").toString());
    }

    /**
     * 
     * @param html
     */
    private final void renderStatus(List<String> html) {
        html.add(new StringBuffer("<p>").append(username).append("</p>").
                toString());
    }
    
    /**
     * 
     * @param html
     * @param vdata
     */
    private final void renderStyleSheet(List<String> html, ViewData vdata) {
        Map<String, String> properties;
        Map<String, Map<String, String>> elements = vdata.getStyleSheet().getElements();
        
        html.add("<style type=\"text/css\">");
        
        for (String element : elements.keySet()) {
            properties = elements.get(element);
            html.add(element+" {");
            
            for (String property: properties.keySet())
                html.add(new StringBuffer(property).append(": ").
                        append(properties.get(property)).
                        append(";").toString());
            
            html.add("}");
        }
        
        html.add("</style>");
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    public final String[] run(ViewData vdata) {
        Container container = vdata.getContainer();
        List<String> html = new ArrayList<String>();
        
        messages = vdata.getMessages();
        if (messages == null)
            messages = new MessageSource(null);
        
        html.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" " +
        		"\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        html.add("<html>");
        
        if (vdata.getStyleSheet() != null)
            renderStyleSheet(html, vdata);
        
        renderHeader(html, vdata);
        html.add("<body onLoad=\"initialize()\">");
        html.add("<form id=\"main\" method=\"post\" action=\"index.html\">");
        
        renderStatus(html);
        
        if (msgtext != null)
            renderMessage(html);

        if (container != null)
            renderContainer(html, container);

        html.add("</form>");
        html.add("</body>");
        html.add("</html>");

        msgtext = null;
        msgtype = Const.NONE;
        
        return html.toArray(new String[0]);
    }
    
    /**
     * 
     * @param msgtext
     */
    public void setMessageText(String msgtext) {
        this.msgtext = msgtext;
    }
    
    /**
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
