package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Shell extends AbstractServiceInterface {
    public static final String SERVER_NAME = "/iocaste-shell/services.html";
    
    public Shell(Function function) {
        initService(function, SERVER_NAME);
    }
    
    /**
     * 
     * @param inputitem
     * @return
     */
    public static final Element createInputItem(Container container,
            AbstractInputComponent inputitem, String name) {
        TextField tfield;
        CheckBox cbox;
        
        switch (inputitem.getComponentType()) {
        case TEXT_FIELD:
            tfield = new TextField(container, name);
            tfield.setStyleClass(inputitem.getStyleClass());
            tfield.setObligatory(inputitem.isObligatory());
            tfield.setPassword(inputitem.isSecret());
            tfield.setLength(inputitem.getLength());
            tfield.setValue(inputitem.getValue());
            tfield.setModelItem(inputitem.getModelItem());
            tfield.setEnabled(inputitem.isEnabled());
            tfield.setDataElement(inputitem.getDataElement());
            tfield.setSearchHelp(inputitem.getSearchHelp());
            
            return tfield;
        case CHECKBOX:
            cbox = new CheckBox(container, name);
            cbox.setStyleClass(inputitem.getStyleClass());
            cbox.setValue(inputitem.getValue());
            cbox.setModelItem(inputitem.getModelItem());
            cbox.setEnabled(inputitem.isEnabled());
            cbox.setDataElement(inputitem.getDataElement());
            
            return cbox;
            
        default:
            return null;
        }
    }
    
    /**
     * 
     * @param container
     * @param type
     * @param name
     * @param args
     * @return
     */
    public static final Element factory(Container container, Const type,
            String name, Object[] args) {
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
            
        case MENU:
            return new Menu(container, name);
            
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
     * 
     * @param input
     * @return
     */
    public static final DataElement getDataElement(InputComponent input) {
        DocumentModelItem modelitem = input.getModelItem();
        
        return (modelitem == null)? input.getDataElement() : 
            modelitem.getDataElement();
    }
    
    /**
     * 
     * @param input
     * @return
     */
    public static final Object getInputValue(InputComponent input)
            throws Exception {
        DateFormat dateformat;
        NumberFormat numberformat;
        String value = input.getValue();
        Locale locale = input.getLocale();
        
        switch (getDataElement(input).getType()) {
        case DataType.NUMC:
            if (input.isBooleanComponent())
                return input.isSelected()? 1 : 0;
            else
                return (isInitial(input))? 0 : Long.parseLong(value);
            
        case DataType.DATE:
            if (isInitial(input))
                return null;
            
            dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            return dateformat.parse(value);
        
        case DataType.DEC:
            if (isInitial(input))
                return 0;
            
            numberformat = NumberFormat.getNumberInstance(locale);
            return numberformat.parse(value);
            
        default:
            return value;
        }
    }
    
    /**
     * 
     * @param appname
     * @param pagename
     * @return
     * @throws Exception
     */
    public final ViewData getView(ViewData view, String pagename)
            throws Exception {
        Message message = new Message();
        
        message.setId("get_view");
        message.add("app_name", view.getAppName());
        message.add("page_name", pagename);
        message.add("logid", view.getLogid());
        
        return call(message);
    }
    
    /**
     * 
     * @param input
     * @return
     */
    public static final boolean isInitial(InputComponent input) {
        NumberFormat numberformat;
        DataElement dataelement;
        String value, test;
        Locale locale = input.getLocale();
        
        value = input.getValue();
        if (isInitial(value))
            return true;
        
        dataelement = Shell.getDataElement(input);
        if (dataelement == null)
            return false;
        
        test = value.trim();
        switch (dataelement.getType()) {
        case DataType.NUMC:
            return (Long.parseLong(test) == 0)? true : false;
            
        case DataType.DEC:
            numberformat = NumberFormat.getNumberInstance(locale);
            
            try {
                return (numberformat.parse(test).doubleValue() == 0)? true : false;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        default:
            return false;
        }
    }
    
    /**
     * 
     * @param value
     * @return
     */
    public static final boolean isInitial(String value) {
        return (value == null || value.trim().length() == 0)? true : false;
    }
    
    /**
     * 
     * @param input
     * @param object
     */
    public final static void moveExtendedToInput(
    		InputComponent input, ExtendedObject object) {
        Object value;
        
        value = object.getValue(input.getModelItem());
        input.setValue((value == null)? "" : value.toString());
    }
    
    /**
     * 
     * @param input
     * @param object
     */
    public final static void moveItemToInput(
    		InputComponent input, Object object) {
        Method method;
        Object value;
        DocumentModelItem modelitem = input.getModelItem();
        
        try {
            method = object.getClass().getMethod(modelitem.getGetterName());
            value = method.invoke(object, new Object[] {});
            input.setValue((value == null)? "" : value.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * @param view
     * @return
     * @throws Exception
     */
    public final String[] popPage(ViewData view) throws Exception {
        Message message = new Message();
        
        message.setId("pop_page");
        message.add("logid", view.getLogid());
        
        return call(message);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void pushPage(ViewData view) throws Exception {
        Message message = new Message();
        
        message.setId("push_page");
        message.add("app_name", view.getAppName());
        message.add("page_name", view.getPageName());
        message.add("logid", view.getLogid());
        
        call(message);
    }
    
    /**
     * 
     * @param input
     * @param value
     */
    public static final void setInputValue(InputComponent input, Object value) {
        DateFormat dateformat;
        NumberFormat numberformat;
        Locale locale = input.getLocale();
        
        switch (Shell.getDataElement(input).getType()) {
        case DataType.NUMC:
            if (input.isBooleanComponent())
                input.setSelected(((Long)value == 0)? false : true);
            else
                input.setValue(Long.toString((Long)value));
            
            break;
            
        case DataType.DEC:
            numberformat = NumberFormat.getNumberInstance(locale);
            input.setValue((value == null)? null : numberformat.format(value));
            
            break;
            
        case DataType.DATE:
            dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            input.setValue((value == null)? null : dateformat.format(value));
            
            break;
            
        default:
            input.setValue((value == null)? null : value.toString());
            
            break;
        }
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void updateView(ViewData view) throws Exception {
        Message message = new Message();
        
        message.setId("update_view");
        message.add("view", view);
        
        call(message);
    }
}
