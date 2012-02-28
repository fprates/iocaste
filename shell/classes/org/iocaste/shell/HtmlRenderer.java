package org.iocaste.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.HtmlTag;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class HtmlRenderer {
    private String currentaction, username, pagetrack, msgtext;
    private Const msgtype;
    private List<String> onload, script;
    private Set<String> actions;
    private MessageSource messages;
    private int logid;
    
    public HtmlRenderer() {
        String line;
        
        script = new ArrayList<String>();
        onload = new ArrayList<String>();
        logid = 0;
        
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
        txtuname.setText(new StringBuilder(username).
                append("@term:").append(logid).toString());
        
        navbar.setMessage((msgtype == null)? Const.STATUS : msgtype,
                (msgtext == null)? "" : msgtext);
    }
    
    /**
     * 
     * @return
     */
    public final Set<String> getActions() {
        return actions;
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
        
        buttontag.add("type", (!button.isSubmit())? "button" : "submit");
        buttontag.add("name", name);
        buttontag.add("id", name);
        buttontag.add("class", button.getStyleClass());
        buttontag.add("value", getText(text_, name));
        buttontag.add("onClick", "defineAction('"+currentaction+"', '" +
        		name + "')");
        
        setAttributes(buttontag, button);
        
        return buttontag;
    }

    /**
     * 
     * @param checkbox
     * @return
     */
    private final XMLElement renderCheckBox(CheckBox checkbox) {
        XMLElement cboxtag = new XMLElement("input");
        String name = checkbox.getHtmlName();
        
        cboxtag.add("type", "checkbox");
        cboxtag.add("name", name);
        cboxtag.add("id", name);
        
        setAttributes(cboxtag, checkbox);
        
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
        switch (container.getType()) {
        case FORM:
            tags.add(renderForm((Form)container));
            break;
            
        case DATA_FORM:
            tags.addAll(renderDataForm((DataForm)container));
            break;
            
        case TABBED_PANE:
            tags.add(renderTabbedPane((TabbedPane)container));
            break;
            
        case TABLE:
            tags.add(renderTable((Table)container));
            break;
            
        case FRAME:
            tags.add(renderFrame((Frame)container));
            break;
            
        case MENU:
        case STANDARD_CONTAINER:
            tags.add(renderStandardContainer((StandardContainer)container));
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
        new TableColumn(table, "name");
        new TableColumn(table, "field");
        
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
        case HTML_TAG:
            tags.add(renderHtmlTag((HtmlTag)element));
            break;
            
        case CHECKBOX:
            tags.add(renderCheckBox((CheckBox)element));
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
        String name = file.getHtmlName();
        
        filetag.add("type", "file");
        filetag.add("name", name);
        filetag.add("id", name);
        
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
        
        currentaction = container.getAction();
        actions.add(currentaction);
        
        formtag.add("method", "post");
        formtag.add("action", "index.html");
        formtag.add("name", container.getHtmlName());

        setAttributes(formtag, container);
        
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
     * @param frame
     * @return
     */
    private final XMLElement renderFrame(Frame frame) {
        XMLElement frametag = new XMLElement("fieldset");
        XMLElement legendtag = new XMLElement("legend");
        
        legendtag.addInner(frame.getText());
        
        frametag.add("id", frame.getName());
        frametag.addChild(legendtag);
        frametag.addChildren(renderElements(frame.getElements()));
        
        return frametag;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderHeader(ViewData vdata) {
        String focus = vdata.getFocus();
        String title = vdata.getTitle();
        XMLElement headtag = new XMLElement("head");
        XMLElement metatag = new XMLElement("meta");
        XMLElement titletag = new XMLElement("title");
        
        metatag.add("http-equiv", "Content-Type");
        metatag.add("content", "text/html; charset=utf-8");
        
        titletag.addInner((title == null)?"Iocaste" : title);

        if (focus != null)
            onload.add(new StringBuffer("document.getElementById('").
                    append(focus).append("').focus();").toString());
        
        headtag.addChild(metatag);
        headtag.addChild(titletag);
        headtag.addChild(renderJavaScript());
        headtag.addChild(renderStyleSheet(vdata));
        
        return headtag;
    }
    
    /**
     * 
     * @param htmltag
     * @return
     */
    private final XMLElement renderHtmlTag(HtmlTag htmltag) {
        XMLElement xmltag = new XMLElement(htmltag.getElement());
        
        xmltag.addInner(htmltag.getLines());
        
        return xmltag;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    private final XMLElement renderJavaScript() {
        XMLElement scripttag = new XMLElement("script");
        
        scripttag.add("type", "text/javascript");
        scripttag.addInner("function initialize() {");
        
        for (String line : onload)
            scripttag.addInner(line);

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
        
        sb.append(link.getHtmlName());
        
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
        String value;
        XMLElement optiontag = null, selecttag= new XMLElement("select");
        String name = list.getHtmlName();
        
        selecttag.add("name", name);
        selecttag.add("id", name);

        setAttributes(selecttag, list);
        
        for (String option : list.getEntriesNames()) {
            optiontag = new XMLElement("option");
            value = list.get(option);
            optiontag.add("value", value);
            
            if (value.equals(list.getValue()))
                optiontag.add("selected", "selected");
            
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
        String name = parameter.getHtmlName();
        
        hiddentag.add("type", "hidden");
        hiddentag.add("name", name);
        hiddentag.add("id", name);

        setAttributes(hiddentag, parameter);
        
        return hiddentag;
    }
    
    /**
     * 
     * @param line
     * @return
     */
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
        Button button = new Button(null, sh.getHtmlName());
        
        button.setText("?");
        
        return renderButton(button);
    }
    
    /**
     * 
     * @param container
     * @return
     */
    private final XMLElement renderStandardContainer(
            StandardContainer container) {
        XMLElement divtag = new XMLElement("div");
        
        divtag.add("id", container.getName());
        setAttributes(divtag, container);
        divtag.addChildren(renderElements(container.getElements()));
        
        return divtag;
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
     * @param tabbedpane
     * @return
     */
    private final XMLElement renderTabbedPane(TabbedPane tabbedpane) {
        Button button;
        StringBuilder sb;
        XMLElement tabitem, tabbedtag = new XMLElement("div");
        String[] names = tabbedpane.getItensNames();
        
        tabbedtag.add("id", tabbedpane.getName());
        
        for (String name : names) {
            sb = new StringBuilder("setElementDisplay('").append(name);
            
            if (tabbedpane.getCurrent().equals(name))
                sb.append(".tabitem', 'block');");
            else
                sb.append(".tabitem', 'none');");
            
            onload.add(sb.toString());
            
            button = new Button(null, name);
            button.setSubmit(false);
            
            sb = new StringBuilder();
            
            for (String name_ : names) {
                sb.append("setElementDisplay('").append(name_);
                
                sb.append((name.equals(name_))?
                        ".tabitem', 'block');" : ".tabitem', 'none');");
            }
            
            button.addAttribute("onClick", sb.toString());
            
            tabbedtag.addChild(renderButton(button));
        }
        
        for (String name : names) {
            tabitem = renderStandardContainer(new StandardContainer(
                    null, name + ".tabitem"));
            tabitem.addChildren(renderTabbedPaneItem(tabbedpane.get(name)));
            
            tabbedtag.addChild(tabitem);
        }
        
        return tabbedtag;
    }
    
    /**
     * 
     * @param tabbedpaneitem
     * @return
     */
    private final List<XMLElement> renderTabbedPaneItem(
            TabbedPaneItem tabbedpaneitem) {
        List<XMLElement> elements = new ArrayList<XMLElement>();
        
        renderContainer(elements, tabbedpaneitem.getContainer());
        
        return elements;
    }
    
    /**
     * 
     * @param table
     * @return
     */
    private final XMLElement renderTable(Table table) {
        String name;
        XMLElement trtag, thtag, tabletag = new XMLElement("table");
        List<XMLElement> tags = new ArrayList<XMLElement>();

        setAttributes(tabletag, table);
        
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
        
        for (TableItem item : table.getItens()) {
            tags.clear();
            tags.add(renderTableItem(table, item));
            tabletag.addChildren(tags);
        }
        
        return tabletag;
    }
    
    /**
     * 
     * @param table
     * @param item
     * @return
     */
    private final XMLElement renderTableItem(Table table, TableItem item) {
        TableColumn column;
        TableColumn[] columns = table.getColumns();
        int i = 0;
        XMLElement tdtag, trtag = new XMLElement("tr");
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        for (Element element : item.getElements()) {
            column = columns[i++];
            
            if (!column.isVisible())
                continue;
            
            if (column.isMark() && !table.hasMark())
                continue;
            
            tdtag = new XMLElement("td");
            
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
        
        ptag.add("id", text.getHtmlName());
        ptag.add("class", text.getStyleClass());

        setAttributes(ptag, text);
        
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
        String name = textfield.getHtmlName(), value = textfield.getValue();
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

        setAttributes(inputtag, textfield);
        
        tags.add(inputtag);
        
        if (textfield.isObligatory()) {
            spantag = new XMLElement("span");
            spantag.addInner("*");
            tags.add(spantag);
        }
        
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
        List<XMLElement> bodycontent = new ArrayList<XMLElement>();
        XMLElement htmltag = new XMLElement("html");
        XMLElement bodytag = new XMLElement("body");
        
        onload.clear();
        actions = new HashSet<String>();
        
        messages = vdata.getMessages();
        pagetrack = new StringBuffer(vdata.getAppName()).append(".").
                append(vdata.getPageName()).append(":").
                append(logid).toString();
        
        if (messages == null)
            messages = new MessageSource(null);
        
        html.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" " +
                "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");

        bodytag.add("onLoad", "initialize()");
        
        configNavigationBar(vdata);
        
        for (Container container : vdata.getContainers())
            renderContainer(bodycontent, container);
        
        bodytag.addChildren(bodycontent);
        tags.add(renderHeader(vdata));
        tags.add(bodytag);
        
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
     * 
     * @param tag
     * @param element
     */
    private void setAttributes(XMLElement tag, Element element) {
        for (String name : element.getAttributeNames())
            tag.add(name, element.getAttribute(name));
    }
    
    /**
     * Ajusta texto da barra de mensagens.
     * @param msgtext texto
     */
    public void setMessageText(String msgtext) {
        this.msgtext = msgtext;
    }
    
    /**
     * 
     * @param logid
     */
    public void setLogid(int logid) {
        this.logid = logid;
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
