package org.iocaste.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class HtmlRenderer {
    private String msgtext;
    private Const msgtype;
    private String[] script;
    private String username;
    private MessageSource messages;
    private String pagetrack;
    
    public HtmlRenderer() {
        String line;
        List<String> lines = new ArrayList<String>();
        InputStream is = this.getClass().getResourceAsStream(
                "/META-INF/shell.js");
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

    /**
     * 
     * @param html
     */
    private final void configNavigationBar(List<String> html, ViewData vdata) {
        NavigationBar navbar;
        Text txtuname;
        Map<String, Boolean> navbarstatus;
        Container container = vdata.getNavBarContainer();
        
        container.clear();
        navbar = new NavigationBar(container);
        
        navbarstatus = vdata.getNavbarStatus();
        for (String name : navbarstatus.keySet())
            navbar.setEnabled(name, navbarstatus.get(name));
        
        txtuname = new Text(navbar.getStatusArea(), "navbar.username");
        txtuname.setText(username);
        
        navbar.setMessage((msgtype == null)? Const.STATUS : msgtype,
                (msgtext == null)? "" : msgtext);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    private final Map<String, Map<String, String>> getStyleSheetElements(
            String name) {
//        Style style;
        Map<String, Map<String, String>> elements =
                new HashMap<String, Map<String, String>>();
//        Map<String, String> properties;
//        
//        style = (Style)session.load(Style.class, name);
//        for (StyleElement element : style.getElements()) {
//            properties = new HashMap<String, String>();
//            elements.put(element.getName(), properties);
//            
//            for (StyleElementProperty property : element.getProperties())
//                properties.put(property.getName(), property.getValue());
//        }
        
        return elements;
    }
    
    /**
     * 
     * @param tag
     * @return
     */
    private final String getText(String tag, String fail) {
        if (tag == null)
            return fail;
        
        return messages.get(tag, tag);
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
        
        if (text_ == null)
            text_ = name;
        
        if (!button.isSubmit())
            inputtext = "<input type=\"button\" name=\"";
        else
            inputtext = "<input type=\"submit\" name=\"";
        
        html.add(new StringBuffer(inputtext).append(name).
                append("\" class=\"").append(button.getStyleClass()).
                append("\" value=\"").append(getText(text_, name)).
                append("\" onClick=\"defineAction('").append(name).
                append("')\"/>").toString());
    }

    /**
     * 
     * @param html
     * @param checkbox
     */
    private final void renderCheckBox(List<String> html, CheckBox checkbox) {
        StringBuilder sb = new StringBuilder("<input type=\"checkbox\" " +
        		"name=\"").append(checkbox.getName());
        
        if (checkbox.isSelected())
            sb.append("\" checked=\"checked\"/>");
        else
            sb.append("\"/>");
        
        html.add(sb.toString());
    }
    
    /**
     * 
     * @param text
     * @param container
     */
    private final void renderContainer(
            List<String> html, Container container) {
        switch (container.getType()) {
        case FORM:
            renderForm(html, (Form)container);
            
            break;
            
        case DATA_FORM:
            renderDataForm(html, (DataForm)container);
            
            break;
            
        case TABLE:
            renderTable(html, (Table)container);
            
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
     * @param html
     * @param form
     */
    private final void renderDataForm(List<String> html, DataForm form) {
        Text text;
        String inputname;
        String styleclass;
        DataItem dataitem;
        TableItem tableitem;
        Table table = new Table(null, 2, new StringBuffer(form.getName()).
                append(".table").toString());
        DocumentModel model = form.getModel();
        
        table.setHeader(false);
        
        for (Element element : form.getElements()) {
            if (element.isControlComponent())
                continue;
            
            if (element.getType() != Const.DATA_ITEM) {
                renderElement(html, element);
                continue;
            }
            
            dataitem = (DataItem)element;
            inputname = dataitem.getName();
            styleclass = dataitem.getStyleClass();
            
            text = new Text(table, new StringBuffer(inputname).
                    append(".text").toString());
            text.setText(inputname);
            text.setStyleClass(styleclass);

            tableitem = new TableItem(table);
            tableitem.add(text);
            
            if (model != null && form.isKeyRequired() &&
                    model.isKey(dataitem.getModelItem()))
                dataitem.setObligatory(true);
            
            tableitem.add(Shell.createInputItem(table, dataitem, inputname));
        }
        
        renderContainer(html, table);
        
        for (String action : form.getActions())
            renderButton(html, (Button)form.getElement(action));
    }
    
    /**
     * 
     * @param text
     * @param element
     */
    private final void renderElement(List<String> html, Element element) {
        if (!element.isVisible())
            return;
        
        switch (element.getType()) {
        case CHECKBOX:
            renderCheckBox(html, (CheckBox)element);
            
            break;
            
        case TABLE_ITEM:
            renderTableItem(html, (TableItem)element);
            
            break;
        
        case MENU_ITEM:
            renderMenuItem(html, (MenuItem)element);
            
            break;
            
        case FILE_ENTRY:
            renderFileEntry(html, (FileEntry)element);
            
            break;
            
        case LIST_BOX:
            renderList(html, (ListBox)element);
            
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
     * @param file
     */
    private final void renderFileEntry(List<String> html, FileEntry file) {
        html.add(new StringBuffer("<input type=\"file\" name=\"").
                append(file.getName()).append("\"/>").toString());
    }

    /**
     * 
     * @param html
     * @param container
     */
    private final void renderForm(List<String> html, Form container) {
        StringBuffer sb = new StringBuffer("<form method=\"post\" " +
        		"action=\"index.html\" name=\"").append(container.getName());
        String enctype = container.getEnctype();
        
        if (enctype != null)
            sb.append("\" enctype=\"").append(enctype).append("\">");
        else
            sb.append("\">");
        
        html.add(sb.toString());
        html.add(new StringBuffer(
                "<input type=\"hidden\" name=\"pagetrack\" value=\"").
                append(pagetrack).append("\"/>").toString());
        renderElements(html, container.getElements());
        html.add("</form>");
    }
    
    /**
     * 
     * @param text
     * @param title
     */
    private final void renderHeader(List<String> html, ViewData vdata) {
        String title = vdata.getTitle();
        
        html.add("<head>");
        html.add("<meta http-equiv=\"Content-Type\" content=\"text/html;" +
                " charset=utf-8\"/>");
        
        if (title == null)
            title = "Iocaste";
        
        html.add(new StringBuffer("<title>").append(title).
                append("</title>").toString());
        renderJavaScript(html, vdata, script);
        renderStyleSheet(html, vdata);
        html.add("</head>");
    }
    
    /**
     * 
     * @param html
     * @param vdata
     * @param script
     */
    private final void renderJavaScript(
            List<String> html, ViewData vdata, String[] script) {
        String focus = vdata.getFocus();
        
        html.add("<script type=\"text/javascript\">");
        html.add("function initialize() {");
        
        if (focus != null)
            html.add(new StringBuffer("document.getElementById('").
                    append(focus).append("').focus();").toString());

        html.add("}");
        
        for (String line : script)
            html.add(line);
        html.add("</script>");
    }
    
    /**
     * 
     * @param text
     * @param link
     */
    private final void renderLink(List<String> html, Link link) {
        Map<Parameter, String> parameters;
        StringBuffer sb;
        
        if (!link.isEnabled()) {
            html.add(link.getText());
            return;
        }
        
        if (link.isAbsolute())
            sb = new StringBuffer("<a href=\"");
        else
            sb = new StringBuffer("<a href=\"index.html?pagetrack=").
                    append(pagetrack).append("&action=");
        
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
     * @param list
     */
    private void renderList(List<String> html, ListBox list) {
        StringBuilder sb = new StringBuilder("<select name=\"").
                append(list.getName()).append("\">");
        
        html.add(sb.toString());
        for (String option : list.getEntriesNames())
            html.add(new StringBuilder("<option value=\"").append(
                    list.get(option)).append("\">").append(option).append(
                            "</option>").toString());
        html.add("</select>");
    }
    
    /**
     * 
     * @param html
     * @param menuitem
     */
    private void renderMenuItem(List<String> html, MenuItem menuitem) {
        Menu menu = (Menu)menuitem.getContainer();
        String name = new StringBuffer(menuitem.getName()).append(".link").
                toString();
        Link link = new Link(null, name, menu.getAction());
        
        link.setText(menuitem.getText());
        link.add(menu.getParameter(), menuitem.getFunction());
        
        for (Element element: menu.getElements()) {
            if (!(element instanceof Parameter) ||
                    (element == menu.getParameter()))
                continue;
            
            link.add((Parameter)element, menuitem.getParameter(
                    element.getName()));
        }
                
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
     * @param html
     * @param vdata
     */
    private final void renderStyleSheet(List<String> html, ViewData vdata) {
        Map<String, String> properties;
        Map<String, Map<String, String>> elements;
        String sheet = vdata.getStyleSheet();
        
        if (sheet == null)
            sheet = "DEFAULT";
        
        elements = getStyleSheetElements(sheet);
        
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
     * @param html
     * @param table
     */
    private void renderTable(List<String> html, Table table) {
        String name;
        TableItem tableitem;
        int iniline = table.getFirstItem();
        int maxline = table.getMaxPageLines();
        int length = table.getLength();
        int lastline = iniline + ((length < maxline)? length : maxline);
        
        html.add("<table>");
        
        if (table.hasHeader()) {
            html.add("<tr>");
            
            for (TableColumn column: table.getColumns()) {
                if (!column.isVisible())
                    continue;
                
                name = column.getName();
                html.add("<th>");
                if (name != null)
                    html.add(name);
                html.add("</th>");
            }
            
            html.add("</tr>");
        }
        
        for (int i = iniline; i < lastline; i++) {
            tableitem = table.getTableItem(i);
            renderElement(html, tableitem);
        }
        
        html.add("</table>");
    }
    
    /**
     * 
     * @param text
     * @param item
     */
    private final void renderTableItem(List<String> html, TableItem item) {
        TableColumn column;
        Element element;
        Table table = (Table)item.getContainer();
        TableColumn[] columns = table.getColumns();
        int i = 0;
        
        html.add("<tr>");
        
        for (String name : item.getElementNames()) {
            column = columns[i++];
            
            if (!column.isVisible())
                continue;
            
            if (column.isMark() && !table.hasMark())
                continue;
            
            html.add("<td>");
            
            element = table.getElement(name);
            if (element != null)
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
    private final void renderText(List<String> html, Text text) {
        html.add(new StringBuffer("<p class=\"").append(text.getStyleClass()).
                append("\">").append(getText(text.getText(), text.getName())).
                append("</p>").toString());
    }
    
    /**
     * 
     * @param text
     * @param textfield
     */
    private final void renderTextField(
            List<String> html, TextField textfield) {
        StringBuffer inputtext;
        DataElement dataelement = Shell.getDataElement(textfield);
        int length = (dataelement == null)?textfield.getLength() :
            dataelement.getLength();
        String name = textfield.getName();
        String value = textfield.getValue();
        
        if (value == null)
            value = "";
        
        if (!textfield.isPassword())
            inputtext = new StringBuffer("<input type=\"text\" name=\"");
        else
            inputtext = new StringBuffer("<input type=\"password\" name=\"");
        
        inputtext.append(name).append("\" class=\"").
                append(textfield.getStyleClass()).append("\" id=\"").
                append(name).append("\" size=\"").append(length).
                append("\" maxlength=\"").append(length).append("\" value=\"").
                append(value);
        
        if (textfield.isEnabled())
            inputtext.append("\"/>");
        else
            inputtext.append("\" disabled=\"disabled\"/>");
        
        if (textfield.isObligatory())
            inputtext.append("<span>*</span>");
        
        html.add(inputtext.toString());
    }
    
    /**
     * Retorna visão renderizada.
     * @param vdata dados da visão
     * @return código html da visão
     */
    public final String[] run(ViewData vdata) {
        List<String> html = new ArrayList<String>();
        
        messages = vdata.getMessages();
        pagetrack = new StringBuffer(vdata.getAppName()).append(".").
                append(vdata.getPageName()).toString();
        
        if (messages == null)
            messages = new MessageSource(null);
        
        html.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" " +
        		"\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        html.add("<html>");
        
        renderHeader(html, vdata);
        
        html.add("<body onLoad=\"initialize()\">");


        configNavigationBar(html, vdata);
        for (Container container : vdata.getContainers())
            renderContainer(html, container);

        html.add("</body>");
        html.add("</html>");

        msgtext = null;
        msgtype = Const.NONE;
        
        return html.toArray(new String[0]);
    }
    
    /**
     * Ajusta texto da barra de mensagens.
     * @param msgtext texto
     */
    public void setMessageText(String msgtext) {
        this.msgtext = msgtext;
    }
    
    /**
     * Ajusta tipo de mensagem da barra de mensagens.
     * @param msgtype
     */
    public final void setMessageType(Const msgtype) {
        this.msgtype = msgtype;
    }
    
    /**
     * Ajusta nome do usuário
     * @param username nome
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
