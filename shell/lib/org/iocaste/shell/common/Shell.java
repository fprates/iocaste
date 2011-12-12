package org.iocaste.shell.common;

import java.lang.reflect.Method;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Shell extends AbstractServiceInterface {
    private static final String SERVERNAME = "/iocaste-shell/services.html";
    
    public Shell(Function function) {
        initService(function, SERVERNAME);
    }
    
    /**
     * 
     * @param inputitem
     * @return
     */
    public static final Element createInputItem(Container container,
            AbstractInputComponent inputitem, String name) {
        TextField tfield;
        
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
            
            return tfield;
        default:
            return null;
        }
    }
    
    public static final Element factory(Container container, Const type,
            String name, Object[] args) {
        switch (type) {
        case CHECKBOX:
            return new CheckBox(container, name);
            
        case DATA_ITEM:
            return new DataItem(container, (Const)args[0], name);
            
        case FORM:
            return new Form(container, name);
            
        case TABLE:
            return new Table(container, (Integer)args[0], name);
            
        case TABLE_ITEM:
            return new TableItem((Table)container);
            
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
     * @param name
     * @return
     * @throws Exception
     */
    public final ViewData getView(String appname, String pagename)
            throws Exception {
        Message message = new Message();
        
        message.setId("get_view");
        message.add("app_name", appname);
        message.add("page_name", pagename);
        
        return (ViewData)call(message);
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
     * @return
     * @throws Exception
     */
    public final String[] popPage() throws Exception {
        Message message = new Message();
        
        message.setId("pop_page");
        
        return (String[])call(message);
    }
    
    /**
     * 
     * @param appname
     * @param pagename
     * @throws Exception
     */
    public final void pushPage(String appname, String pagename)
            throws Exception {
        Message message = new Message();
        
        message.setId("push_page");
        message.add("app_name", appname);
        message.add("page_name", pagename);
        
        call(message);
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
