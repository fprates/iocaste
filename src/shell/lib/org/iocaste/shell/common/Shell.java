package org.iocaste.shell.common;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

/**
 * Serviços do shell.
 * 
 * @author francisco.prates
 *
 */
public class Shell extends AbstractServiceInterface {
    public static final String SERVER_NAME = "/iocaste-shell/services.html";
    
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
    
    /**
     * Copia componentes de entrada.
     * 
     * @param container conteiner
     * @param inputitem componente original
     * @param name nome do componente destino
     * @param values valores (para listbox)
     * @return componente de destino
     */
    public static final InputComponent copyInputItem(Container container,
            InputComponent inputitem, String name, Map<String, Object> values) {
        RangeField rfield;
        TextField tfield;
        CheckBox cbox;
        ListBox lbox;
        Const componenttype;
        
        componenttype = inputitem.getComponentType();
        switch (componenttype) {
        case CHECKBOX:
            cbox = new CheckBox(container, name);
            cbox.set(inputitem.get());
            cbox.setModelItem(inputitem.getModelItem());
            cbox.setEnabled(inputitem.isEnabled());
            cbox.setDataElement(inputitem.getDataElement());
            cbox.setLocale(inputitem.getLocale());
            cbox.setHtmlName(inputitem.getHtmlName());
            
            return cbox;
            
        case LIST_BOX:
            lbox = new ListBox(container, name);
            lbox.set(inputitem.get());
            lbox.setModelItem(inputitem.getModelItem());
            lbox.setEnabled(inputitem.isEnabled());
            lbox.setDataElement(inputitem.getDataElement());
            lbox.setLocale(inputitem.getLocale());
            lbox.setHtmlName(inputitem.getHtmlName());
            
            for (String key : values.keySet())
                lbox.add(key, values.get(key));
            
            return lbox;
            
        case RANGE_FIELD:
            rfield = new RangeField(container, name);
            rfield.setObligatory(inputitem.isObligatory());
            rfield.setLength(inputitem.getLength());
            rfield.set(inputitem.get());
            rfield.setModelItem(inputitem.getModelItem());
            rfield.setEnabled(inputitem.isEnabled());
            rfield.setDataElement(inputitem.getDataElement());
            rfield.setSearchHelp(inputitem.getSearchHelp());
            rfield.setLocale(inputitem.getLocale());
            rfield.setHtmlName(inputitem.getHtmlName());
            
            return rfield;
            
        case TEXT_FIELD:
            tfield = new TextField(container, name);
            tfield.setObligatory(inputitem.isObligatory());
            tfield.setSecret(inputitem.isSecret());
            tfield.setLength(inputitem.getLength());
            tfield.setVisibleLength(inputitem.getVisibleLength());
            tfield.set(inputitem.get());
            tfield.setModelItem(inputitem.getModelItem());
            tfield.setEnabled(inputitem.isEnabled());
            tfield.setDataElement(inputitem.getDataElement());
            tfield.setSearchHelp(inputitem.getSearchHelp());
            tfield.setLocale(inputitem.getLocale());
            tfield.setHtmlName(inputitem.getHtmlName());
            
            return tfield;
            
        default:
            throw new RuntimeException(new StringBuilder("Component type ").
                    append(componenttype).append(" not supported.").toString());
        }
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
    public final PageStackItem home(View view) {
        return call(new Message("home"));
    }
    
    /**
     * Verifica se uma string é inicial.
     * @param value string
     * @return true, se string for inicial.
     */
    public static final boolean isInitial(String value) {
        return (value == null || value.trim().length() == 0)? true : false;
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
        if (element == null)
            return Shell.isInitial((String)value);
        
        switch (element.getType()) {
        case DataType.BOOLEAN:
            return (boolean)value;
            
        case DataType.NUMC:
            return (((BigDecimal)value).longValue() == 0l);
            
        case DataType.DEC:
            return (((Number)value).doubleValue() == 0);

        default:
            return Shell.isInitial(value.toString());
        }
    }
    
    /**
     * Restaura página anterior.
     * @param view visão atual
     * @return dados da página anterior
     */
    public final PageStackItem popPage(View view) {
        return call(new Message("pop_page"));
    }
    
    /**
     * Salva página na pilha de páginas.
     * @param view visão.
     */
    public final void pushPage(View view) {
        Message message = new Message("push_page");
        message.add("app_name", view.getAppName());
        message.add("page_name", view.getPageName());
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
     * @param value
     * @param element
     * @param locale
     * @param boolconvert
     * @return
     */
    public static final String toString(Object value, DataElement element,
            Locale locale, boolean boolconvert) {
        DateFormat dateformat;
        NumberFormat numberformat;
        
        if (element == null)
            return (value == null)? "" : value.toString();
        
        switch (element.getType()) {
        case DataType.DEC:
            numberformat = NumberFormat.getNumberInstance(locale);
            numberformat.setMaximumFractionDigits(element.getDecimals());
            numberformat.setMinimumFractionDigits(element.getDecimals());
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
