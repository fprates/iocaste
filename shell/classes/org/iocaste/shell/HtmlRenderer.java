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
import org.iocaste.shell.common.SearchHelp;
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
    private List<String> script;
    private String username;
    private MessageSource messages;
    private String pagetrack;
    
    public HtmlRenderer() {
        String line;
        script = new ArrayList<String>();
        InputStream is = this.getClass().getResourceAsStream(
                "/META-INF/shell.js");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        
        try {
            while ((line = reader.readLine()) != null)
                script.add(line);
            
            reader.close();
            is.close();
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }

    /**
     * 
     * @param vdata
     */
    private final void configNavigationBar(ViewData vdata) {
        NavigationBar navbar;
        Text txtuname, txttitle;
        Map<String, Boolean> navbarstatus;
        Container container = vdata.getNavBarContainer();
        String title = vdata.getTitle();
        
        container.clear();
        navbar = new NavigationBar(container);
        
        navbarstatus = vdata.getNavbarStatus();
        for (String name : navbarstatus.keySet())
            navbar.setEnabled(name, navbarstatus.get(name));
        
        txttitle = new Text(navbar.getStatusArea(), "navbar.title");
        txttitle.setText((title == null)?vdata.getAppName() : title);
        txttitle.setTag("h1");
        
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
     * @param fail
     * @return
     */
    private final String getText(String tag, String fail) {
        if (tag == null)
            return fail;
        
        return messages.get(tag, tag);
    }
    
    /**
     * 
     * @param button
     * @return
     */
    private final XMLElement renderButton(Button button) {
        String text_ = button.getText();
        String name = button.getName();
        XMLElement buttontag= new XMLElement("input");
        
        if (text_ == null)
            text_ = name;
        
        if (!button.isSubmit())
            buttontag.add("type", "button");
        else
            buttontag.add("type", "submit");
        
        buttontag.add("name", name);
        buttontag.add("class", button.getStyleClass());
        buttontag.add("value", getText(text_, name));
        buttontag.add("onClick", "defineAction('"+name+"')");
        
        return buttontag;
    }

    /**
     * 
     * @param checkbox
     * @return
     */
    private final XMLElement renderCheckBox(CheckBox checkbox) {
        XMLElement cboxtag = new XMLElement("input");
        
        cboxtag.add("type", "checkbox");
        cboxtag.add("name", checkbox.getName());
        
        if (checkbox.isSelected())
            cboxtag.add("checked", "checked");
        
        return cboxtag;
    }
    
    /**
     * 
     * @param tags
     * @param container
     */
    private final void renderContainer(List<XMLElement> tags,
            Container container) {
        XMLElement divtag;
        
        switch (container.getType()) {
        case FORM:
            tags.add(renderForm((Form)container));
            break;
            
        case DATA_FORM:
            tags.addAll(renderDataForm((DataForm)container));
            break;
            
        case TABLE:
            tags.add(renderTable((Table)container));
            break;
            
        case MENU:
        case STANDARD_CONTAINER:
            divtag = new XMLElement("div");
            divtag.addChildren(renderElements(container.getElements()));
            
            tags.add(divtag);
            
            break;
        }
        
    }
    
    /**
     * 
     * @param form
     * @return
     */
    private final List<XMLElement> renderDataForm(DataForm form) {
        Text text;
        String inputname;
        String styleclass;
        DataItem dataitem;
        TableItem tableitem;
        String tablename = new StringBuffer(form.getName()).append(".table").
                toString();
        Table table = new Table(null, tablename);
        DocumentModel model = form.getModel();
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        table.setHeader(false);
        table.addColumn("name");
        table.addColumn("field");
        
        for (Element element : form.getElements()) {
            if (element.isControlComponent())
                continue;
            
            if (element.getType() != Const.DATA_ITEM) {
                renderElement(tags, element);
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
        
        renderContainer(tags, table);
        
        for (String action : form.getActions())
            tags.add(renderButton((Button)form.getElement(action)));
        
        return tags;
    }
    
    /**
     * 
     * @param tags
     * @param element
     */
    private final void renderElement(List<XMLElement> tags, Element element) {
        if (!element.isVisible())
            return;
        
        switch (element.getType()) {
        case CHECKBOX:
            tags.add(renderCheckBox((CheckBox)element));
            break;
            
        case TABLE_ITEM:
            tags.add(renderTableItem((TableItem)element));
            break;
        
        case MENU_ITEM:
            tags.add(renderMenuItem((MenuItem)element));
            break;
            
        case FILE_ENTRY:
            tags.add(renderFileEntry((FileEntry)element));
            break;
            
        case LIST_BOX:
            tags.add(renderList((ListBox)element));
            break;
            
        case TEXT:
            tags.add(renderText((Text)element));
            break;
            
        case TEXT_FIELD:
            tags.addAll(renderTextField((TextField)element));
            break;
            
        case BUTTON:
            tags.add(renderButton((Button)element));
            break;
            
        case LINK:
            tags.add(renderLink((Link)element));
            break;
        
        case PARAMETER:
            tags.add(renderParameter((Parameter)element));
            break;
            
        default:
            if (element.isContainable())
                renderContainer(tags, (Container)element);
        }
    }
    
    /**
     * 
     * @param elements
     * @return
     */
    private final List<XMLElement> renderElements(Element[] elements) {
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        for (Element element : elements) 
            renderElement(tags, element);
        
        return tags;
    }

    /**
     * 
     * @param file
     * @return
     */
    private final XMLElement renderFileEntry(FileEntry file) {
        XMLElement filetag = new XMLElement("input");
        
        filetag.add("type", "file");
        filetag.add("name", file.getName());
        
        return filetag;
    }

    /**
     * 
     * @param container
     * @return
     */
    private final XMLElement renderForm(Form container) {
        XMLElement hiddentag, formtag = new XMLElement("form");
        String enctype = container.getEnctype();
        
        formtag.add("method", "post");
        formtag.add("action", "index.html");
        formtag.add("name", container.getName());
        
        if (enctype != null)
            formtag.add("enctype", enctype);
        
        hiddentag = new XMLElement("input");
        hiddentag.add("type", "hidden");
        hiddentag.add("name", "pagetrack");
        hiddentag.add("value", pagetrack);
        
        formtag.addInner(hiddentag.toString());
        formtag.addChildren(renderElements(container.getElements()));
        
        return formtag;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderHeader(ViewData vdata) {
        String title = vdata.getTitle();
        XMLElement headtag = new XMLElement("head");
        XMLElement metatag = new XMLElement("meta");
        XMLElement titletag = new XMLElement("title");
        
        metatag.add("http-equiv", "Content-Type");
        metatag.add("content", "text/html; charset=utf-8");
        
        titletag.addInner((title == null)?"Iocaste" : title);

        headtag.addChild(metatag);
        headtag.addChild(titletag);
        headtag.addChild(renderJavaScript(vdata));
        headtag.addChild(renderStyleSheet(vdata));
        
        return headtag;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderJavaScript(ViewData vdata) {
        String focus = vdata.getFocus();
        XMLElement scripttag = new XMLElement("script");
        
        scripttag.add("type", "text/javascript");
        scripttag.addInner("function initialize() {");
        
        if (focus != null)
            scripttag.addInner(new StringBuffer("document.getElementById('").
                    append(focus).append("').focus();").toString());

        scripttag.addInner("}");
        scripttag.addInner(script);
        
        return scripttag;
    }
    
    /**
     * 
     * @param link
     * @return
     */
    private final XMLElement renderLink(Link link) {
        Map<Parameter, String> parameters;
        StringBuffer sb;
        XMLElement atag;
        
        if (!link.isEnabled()) {
            atag = new XMLElement("span");
            atag.addInner(link.getText());
            
            return atag;
        }
        
        atag = new XMLElement("a");
        
        if (link.isAbsolute())
            sb = new StringBuffer();
        else
            sb = new StringBuffer("index.html?pagetrack=").
                    append(pagetrack).append("&action=");
        
        sb.append(link.getName());
        
        parameters = link.getParametersMap();
        
        if (parameters.size() > 0)
            for (Parameter parameter: parameters.keySet())
                sb.append("&").append(parameter.getName()).append("=").
                        append(parameters.get(parameter));

        atag.add("href", sb.toString());
        atag.addInner(link.getText());
        
        return atag;
    }
    
    /**
     * 
     * @param list
     * @return
     */
    private final XMLElement renderList(ListBox list) {
        XMLElement optiontag = null, selecttag= new XMLElement("select");
        
        selecttag.add("name", list.getName());
        
        for (String option : list.getEntriesNames()) {
            optiontag = new XMLElement("option");
            optiontag.add("value", list.get(option));
            optiontag.addInner(option);
            
            selecttag.addChild(optiontag);
        }
        
        return selecttag;
    }
    
    /**
     * 
     * @param menuitem
     * @return
     */
    private final XMLElement renderMenuItem(MenuItem menuitem) {
        Menu menu = (Menu)menuitem.getContainer();
        String name = new StringBuffer(menuitem.getName()).append(".link").
                toString();
        Link link = new Link(null, name, menu.getAction());
        
        link.setText(menuitem.getText());
        link.add(menu.getParameter(), menuitem.getFunction());
        
        for (Element element: menu.getElements()) {
            if ((element.getType() != Const.PARAMETER) ||
                    (element == menu.getParameter()))
                continue;
            
            link.add((Parameter)element, menuitem.getParameter(
                    element.getName()));
        }
                
        return renderLink(link);
    }
    
    /**
     * 
     * @param parameter
     * @return
     */
    private final XMLElement renderParameter(Parameter parameter) {
        XMLElement hiddentag = new XMLElement("input");
        String name = parameter.getName();
        
        hiddentag.add("type", "hidden");
        hiddentag.add("name", name);
        hiddentag.add("id", name);
        
        return hiddentag;
    }
    
    private final XMLElement renderPrintElement(String line) {
        XMLElement ptag = new XMLElement("p");
        
        ptag.addInner(line);
        
        return ptag;
    }
    
    /**
     * 
     * @param sh
     * @return
     */
    private final XMLElement renderSearchHelp(SearchHelp sh) {
        Button button = new Button(null, sh.getName());
        
        button.setText("?");
        
        return renderButton(button);
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderStyleSheet(ViewData vdata) {
        Map<String, String> properties;
        Map<String, Map<String, String>> elements;
        String sheet = vdata.getStyleSheet();
        XMLElement styletag = new XMLElement("style");
        
        styletag.add("type", "text/css");
        
        if (sheet == null)
            sheet = "DEFAULT";
        
        elements = getStyleSheetElements(sheet);
        if (elements.size() == 0)
            styletag.addInner("");
        
        for (String element : elements.keySet()) {
            properties = elements.get(element);
            styletag.addInner(element+" {");
            
            for (String property: properties.keySet())
                styletag.addInner(new StringBuffer(property).append(": ").
                        append(properties.get(property)).
                        append(";").toString());
            
            styletag.addInner("}");
        }
        
        return styletag;
    }
    
    /**
     * 
     * @param table
     * @return
     */
    private final XMLElement renderTable(Table table) {
        String name;
        TableItem tableitem;
        XMLElement trtag, thtag, tabletag = new XMLElement("table");
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        if (table.hasHeader()) {
            trtag = new XMLElement("tr");
            
            for (TableColumn column: table.getColumns()) {
                if (!column.isVisible())
                    continue;
                
                if (column.isMark() && !table.hasMark())
                    continue;
                
                thtag = new XMLElement("th");
                name = column.getName();
                if (name != null)
                    thtag.addInner(name);
                
                trtag.addChild(thtag);
            }
            
            tabletag.addChild(trtag);
        }
        
        for (int i = 0; i < table.getLength(); i++) {
            tableitem = table.getTableItem(i);
            tags.clear();
            renderElement(tags, tableitem);
            tabletag.addChildren(tags);
        }
        
        return tabletag;
    }
    
    /**
     * 
     * @param item
     * @return
     */
    private final XMLElement renderTableItem(TableItem item) {
        TableColumn column;
        Element element;
        Table table = (Table)item.getContainer();
        TableColumn[] columns = table.getColumns();
        int i = 0;
        XMLElement tdtag, trtag = new XMLElement("tr");
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        for (String name : item.getElementNames()) {
            column = columns[i++];
            
            if (!column.isVisible())
                continue;
            
            if (column.isMark() && !table.hasMark())
                continue;
            
            tdtag = new XMLElement("td");
            
            element = table.getElement(name);
            if (element != null) {
                tags.clear();
                renderElement(tags, element);
                tdtag.addChildren(tags);
            }
            
            trtag.addChild(tdtag);
        }
        
        return trtag;
    }
    
    /**
     * 
     * @param text
     * @return
     */
    private final XMLElement renderText(Text text) {
        XMLElement ptag = new XMLElement(text.getTag());
        
        ptag.add("class", text.getStyleClass());
        ptag.addInner(getText(text.getText(), text.getName()));
        
        return ptag;
    }
    
    /**
     * 
     * @param textfield
     * @return
     */
    private final List<XMLElement> renderTextField(TextField textfield) {
        SearchHelp search;
        DataElement dataelement = Shell.getDataElement(textfield);
        int length = (dataelement == null)?textfield.getLength() :
            dataelement.getLength();
        String name = textfield.getName(), value = textfield.getValue();
        XMLElement spantag, inputtag = new XMLElement("input");
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        if (value == null)
            value = "";
        
        if (!textfield.isPassword())
            inputtag.add("type", "text");
        else
            inputtag.add("type", "password");
        
        inputtag.add("name", name);
        inputtag.add("class", textfield.getStyleClass());
        inputtag.add("id", name);
        inputtag.add("size", Integer.toString(length));
        inputtag.add("maxlength", Integer.toString(length));
        inputtag.add("value", value);
        
        if (!textfield.isEnabled())
            inputtag.add("readonly", "readonly");
        
        if (textfield.isObligatory()) {
            spantag = new XMLElement("span");
            spantag.addInner("*");
            tags.add(spantag);
        }
        
        tags.add(inputtag);
        
        search = textfield.getSearchHelp();
        if (search != null)
            tags.add(renderSearchHelp(search));
        
        return tags;
    }
    
    /**
     * Retorna visão renderizada.
     * @param vdata dados da visão
     * @return código html da visão
     */
    public final String[] run(ViewData vdata) {
        List<String> html = new ArrayList<String>();
        List<XMLElement> tags = new ArrayList<XMLElement>();
        XMLElement htmltag = new XMLElement("html");
        XMLElement bodytag = new XMLElement("body");
        
        messages = vdata.getMessages();
        pagetrack = new StringBuffer(vdata.getAppName()).append(".").
                append(vdata.getPageName()).toString();
        
        if (messages == null)
            messages = new MessageSource(null);
        
        html.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" " +
                "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");

        bodytag.add("onLoad", "initialize()");
        tags.add(renderHeader(vdata));
        tags.add(bodytag);
        
        configNavigationBar(vdata);
        
        for (Container container : vdata.getContainers())
            renderContainer(tags, container);
        
        msgtext = null;
        msgtype = Const.NONE;
        
        htmltag.addChildren(tags);
        for (String line : vdata.getPrintLines())
            htmltag.addChild(renderPrintElement(line));
        
        vdata.clearPrintLines();
        
        html.add(htmltag.toString());
        
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
