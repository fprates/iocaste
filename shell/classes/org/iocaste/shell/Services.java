package org.iocaste.shell;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.ViewData;

public class Services extends AbstractFunction {

    public Services() {
        export("get_view", "getView");
        export("update_view", "updateView");
        export("pop_page", "popPage");
        export("push_page", "pushPage");
        export("process_inputs", "processInputs");
    }
    
    /**
     * 
     * @param input
     */
    private void convertInputValue(InputComponent input) {
        String value;
        DataElement dataelement = Shell.getDataElement(input);
        
        if (dataelement == null)
            return;
        
        value = input.getValue();
        switch(dataelement.getType()) {
        case DataType.NUMC:
            if (value == null || value.trim().length() == 0)
                input.setValue("0");
            break;
        }
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final ViewData getView(Message message) {
        return PageRenderer.getView(message.getSessionid(),
                message.getString("app_name"), message.getString("page_name"));
    }
    
    /**
     * 
     * @param values
     * @param name
     * @return
     */
    private final String getString(Map<String, Object> values, String name) {
        try {
            return (String)values.get(name);
        } catch (ClassCastException e) {
            return ((String[])values.get(name))[0];
        }
    }
    
    /**
     * 
     * @param input
     * @param value
     * @return
     */
    private final boolean isInitial(String name, InputComponent input,
            String value) throws Exception {
        DataElement dataelement;
        String test;
        
        if (value == null)
            return true;
        
        test = value.trim();
        if (test.length() == 0)
            return true;
        
        dataelement = Shell.getDataElement(input);
        
        if (dataelement == null)
            return false;
        
        switch (dataelement.getType()) {
        case DataType.NUMC:
            return (Long.parseLong(test) == 0)? true : false;
            
        case DataType.DEC:
            return (Double.parseDouble(test) == 0)? true : false;
            
        default:
            return false;
        }
    }
    
    /**
     * 
     * @param input
     * @param value
     * @return
     */
    private final boolean isValueCompatible(InputComponent input,
            String value) {
        DataElement dataelement;
        
        if (value == null || value.trim().length() == 0)
            return true;
        
        dataelement = Shell.getDataElement(input);
        
        if (dataelement == null)
            return true;
        
        switch (dataelement.getType()) {
        case DataType.CHAR:
            return true;
            
        case DataType.NUMC:
            return input.getValue().matches("[0-9]+");
            
        case DataType.DEC:
            return input.getValue().matches("[0-9\\.]+");
            
        default:
            return false;
        }
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String[] popPage(Message message) {
        return PageRenderer.popPage(message.getSessionid());
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Object[] processInputs(Message message) throws Exception {
        Element element;
        String value;
        DataElement dataelement;
        InputComponent input, einput = null;
        Object[] result = new Object[3];
        ViewData view = (ViewData)message.get("view");
        @SuppressWarnings("unchecked")
        Map<String, Object> values = (Map<String, Object>)message.get("values");
        String[] inputs = view.getInputs();
        int ecode = 0;
        
        for (String name : inputs) {
            element = view.getElement(name);
            if (!element.isEnabled())
                continue;
            
            input = (InputComponent)element;
            value = getString(values, name);
            
            input.setValue(value);
            if (!isValueCompatible(input, value)) {
                einput = input;
                ecode = AbstractPage.EMISMATCH;
                continue;
            }
           
            if (input.isObligatory() && isInitial(name, input, value)) {
                einput = input;
                ecode = AbstractPage.EINITIAL;
                continue;
            }
            
            if (ecode == 0)
                convertInputValue(input);
            
            dataelement = Shell.getDataElement(input);
            
            if (value == null || dataelement == null)
                continue;
            
            if (dataelement.isUpcase())
                input.setValue(value.toUpperCase());
        }
        
        result[0] = einput;
        result[1] = ecode;
        result[2] = view;
        
        return result;
    }
    /**
     * 
     * @param message
     */
    public final void pushPage(Message message) {
        String sessionid = message.getSessionid();
        String appname = message.getString("app_name");
        String pagename = message.getString("page_name");
        
        PageRenderer.pushPage(sessionid, appname, pagename);
    }
    
    /**
     * 
     * @param message
     */
    public final void updateView(Message message) {
        PageRenderer.updateView(message.getSessionid(),
                (ViewData)message.get("view"));
    }
}
