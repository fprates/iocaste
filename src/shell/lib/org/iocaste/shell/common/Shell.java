package org.iocaste.shell.common;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

/**
 * Serviços do shell.
 * 
 * @author francisco.prates
 *
 */
public class Shell extends AbstractServiceInterface {
    public static final String SERVER_NAME = "/iocaste-shell/services.html";
    public static final int BACKGROUND_COLOR = 0;
    public static final int CLICKABLE_COLOR = 1;
    public static final int FONT_COLOR = 2;
    public static final int FONT_FAMILY = 3;
    public static final int FRAME_COLOR = 4;
    public static final int DISABLED_FONT_COLOR = 5;
    public static final int FONT_SIZE = 6;
    public static final int SHADOW = 7;
    public static final int CONTROL_BACKGROUND = 8;
    
    public Shell(Function function) {
        initService(function, SERVER_NAME);
    }
    
    /**
     * Adiciona tíquete de acesso
     * @param ticket
     * @return
     */
    public final String addTicket(AccessTicket ticket) {
        Message message = new Message("add_ticket");
        message.add("ticket", ticket);
        return call(message);
    }
    
    public static final boolean areEquals(InputComponent input, Object value) {
        DataElement de = getDataElement(input);
        Object ivalue = input.get();
        
        if (ivalue == null && value == null)
            return true;
        
        if (ivalue == null)
            return false;
        
        switch(de.getType()) {
        case DataType.BYTE:
        case DataType.INT:
        case DataType.LONG:
        case DataType.SHORT:
        case DataType.NUMC:
            return (new Long(input.getl()).equals(value));
        case DataType.DEC:
            return (new Double(input.getd()).equals(value));
        }
        
        return ivalue.equals(value);
    }
    
    /**
     * Gera um componente especificado.
     * 
     * @param container
     * @param type
     * @param name
     * @param args
     * @return
     */
    public static final Element factory(Container container, Const type,
            String name, Object... args) {
        switch (type) {
        case BUTTON:
            return new Button(container, name);
            
        case CHECKBOX:
            return new CheckBox(container, name);
            
        case DATA_FORM:
            return new DataForm(container, name);
            
        case DATA_ITEM:
            return new DataItem((DataForm)container, (Const)args[0], name);
            
        case FORM:
            return new Form(container, name);
        
        case LINK:
            return new Link(container, name, (String)args[0]);
            
        case LIST_BOX:
            return new ListBox(container, name);
            
        case TABLE:
            return new Table(container, name);
            
        case STANDARD_CONTAINER:
            return new StandardContainer(container, name);
            
        case TEXT:
            return new Text(container, name);
            
        case TEXT_FIELD:
            return new TextField(container, name);
            
        default:
            return null;
        }
    }

    public static final String formSubmit(
            String form, String action, String control) {
        return new StringBuilder("formSubmit('").append(form).
                append("', '").append(action).append("', '").append(control).
                append("');").toString();
    }

    public static final String formSubmitNoLock(
            String form, String action, String control) {
        return new StringBuilder("formSubmitNoLock('").append(form).
                append("', '").append(action).append("', '").append(control).
                append("');").toString();
    }
    
    /**
     * Retorna elemento de dados de um componente de entrada.
     * @param input componente de entrada.
     * @return elemento de dados.
     */
    public static final DataElement getDataElement(InputComponent input) {
        DocumentModelItem modelitem = input.getModelItem();
        
        return (modelitem == null)? input.getDataElement() : 
            modelitem.getDataElement();
    }
    
    /**
     * 
     * @return
     */
    public final String getLoginApp() {
        return call(new Message("login_app_get"));
    }
    
    public static final Map<String, String> getMessages(
            Function function, String locale, String packagename) {
        GenericService service;
        String servername;
        String[][] messages;
        Map<String, String> _messages;
        Message message = new Message("messages_get");
        
        message.add("locale", locale);
        servername = String.format("/%s/view.html", packagename);
        service = new GenericService(function, servername);
        messages = service.invoke(message);
        if (messages == null)
            return null;
        
        _messages = new HashMap<>();
        for (int i = 0; i < messages.length; i++)
            _messages.put(messages[i][0], messages[i][1]);
        return _messages;
    }
    
    /**
     * 
     * @return
     */
    public final PageStackItem[] getPagesPositions() {
        return call(new Message("get_pages_positions"));
    }
    
    /**
     * Retorna visão especificada.
     * @param view visão atual
     * @param pagename nome da visão
     * @return visão especificada.
     */
    public final View getView(View view, String pagename) {
        Message message = new Message("get_view");
        message.add("app_name", view.getAppName());
        message.add("page_name", pagename);
        return call(message);
    }
    
    /**
     * Retorna à página inicial.
     * @param view
     * @return
     */
    public final PageStackItem home(String page) {
        Message message = new Message("home");
        message.add("page", page);
        return call(message);
    }
    
    /**
     * 
     */
    public final void invalidateStyle(String name) {
        Message message = new Message("style_invalidate");
        
        message.add("style", name);
        call(message);
    }
    
    /**
     * Verifica se uma string é inicial.
     * @param value string
     * @return true, se string for inicial.
     */
    public static final boolean isInitial(String value) {
        return Documents.isInitial(value);
    }
    
    /**
     * 
     * @param input
     * @return
     */
    public static final boolean isInitial(InputComponent input) {
        DataElement element;
        Object value = input.get();
        if (value == null)
            return true;
        
        element = getDataElement(input);
        return Documents.isInitial(element, value);
    }
    
    /**
     * Restaura página anterior.
     * @return dados da página anterior
     */
    public final PageStackItem popPage() {
        return call(new Message("pop_page"));
    }
    
    /**
     * Salva página na pilha de páginas.
     * @param view visão.
     */
    public final void pushPage(View view) {
        Message message = new Message("push_page");
        message.add("view", view);
        call(message);
    }
    
    /**
     * Remove ticket da lista de acessos.
     * @param ticket código do ticket de acesso.
     */
    public final void removeTicket(String ticket) {
        Message message = new Message("remove_ticket");
        message.add("ticket", ticket);
        call(message);
    }
    
    public static final String setElementDisplay(String name, String value) {
        return new StringBuilder("setElementDisplay('").append(name).
                append("','").append(value).append("');").toString();
    }

    public static final String setElementDisplayOfClass(
            String style, String display) {
        return new StringBuilder("setElementDisplayOfClass('").
                append(style).append("','").append(display).append("');").
                toString();
    }
    
    /**
     * 
     * @param position
     */
    public final void setPagesPosition(String position) {
        Message message = new Message("set_pages_position");        
        message.add("position", position);
        call(message);
    }
    
    /**
     * 
     * @param name
     * @param value
     * @return
     */
    public static final String setValue(String name, Object value) {
        return new StringBuilder("setValue('").append(name).
            append("', '").append(value).append("');").toString();
    }
    
    /**
     * 
     * @param value
     * @param element
     * @param locale
     * @param boolconvert
     * @return
     */
    public static final String toString(Object value, DataElement element,
            Locale locale, boolean boolconvert) {
        if (element == null)
            return (value == null)? "" : value.toString();
        
        return toString(value, element.getType(), element.getDecimals(), locale,
                boolconvert);
    }
    
    public static final String toString(
            View view, ExtendedObject object, String field) {
        Object value = object.get(field);
        DataElement element = object.getModel().getModelItem(field).
                getDataElement();
        Locale locale = view.getLocale();
        
        return toString(value, element, locale, false);
    }
    
    public static final String toString(Object value, int type, int dec,
            Locale locale, boolean boolconvert) {
        DateFormat dateformat;
        NumberFormat numberformat;
        
        switch (type) {
        case DataType.DEC:
            numberformat = NumberFormat.getNumberInstance(locale);
            numberformat.setMaximumFractionDigits(dec);
            numberformat.setMinimumFractionDigits(dec);
            numberformat.setGroupingUsed(true);
            return numberformat.format((value == null)? 0 : value);
            
        case DataType.NUMC:
            return (value == null)? "0" : value.toString();
            
        case DataType.DATE:
            if (value == null)
                return "";
            
            dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            return dateformat.format(value);
            
        case DataType.BOOLEAN:
            if (boolconvert)
                return ((Boolean)value)? "on" : "off";
            else
                return Boolean.toString((Boolean)value);
            
        case DataType.TIME:
            if (value == null)
                return "";
            
            dateformat = DateFormat.getTimeInstance(DateFormat.MEDIUM, locale);
            return dateformat.format(value);
            
        default:
            return (value == null)? "" : value.toString();
        }
    }
    
    /**
     * Atualiza visão na pilha de páginas.
     * @param view visão.
     */
    public final void updateView(View view) {
        Message message = new Message("update_view");
        message.add("view", view);
        call(message);
    }
}
