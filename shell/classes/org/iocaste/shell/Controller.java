package org.iocaste.shell;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.ViewData;

public class Controller {
    private static final int EINITIAL = 1;
    private static final int EMISMATCH = 2;
    private static final int EINVALID_REFERENCE = 3;
    private static final int WINVALID_ACTION = 4;
    
    /**
     * 
     * @param input
     */
    private static void convertInputValue(InputComponent input) {
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
     * @param values
     * @param name
     * @return
     */
    private static final String getString(Map<String, ?> values, String name) {
        try {
            return (String)values.get(name);
        } catch (ClassCastException e) {
            return ((String[])values.get(name))[0];
        }
    }
    
    /**
     * 
     * @param input
     * @param function
     * @return
     * @throws Exception
     */
    private static final boolean hasValidReference(InputComponent input,
            Function function) throws Exception {
        Documents documents;
        ExtendedObject object;
        Object value;
        String svalue;
        DocumentModelItem reference, item = input.getModelItem();
        
        if (item == null)
            return true;
        
        reference = item.getReference();
        svalue = input.getValue();
        
        if (reference == null || svalue == null)
            return true;
        
        switch (Shell.getDataElement(input).getType()) {
        case DataType.CHAR:
            value = svalue;
            break;
        case DataType.NUMC:
            value = Long.parseLong(svalue);
            break;
        case DataType.BOOLEAN:
            value = Boolean.parseBoolean(svalue);
            break;
        default:
            return true;
        }
        
        documents = new Documents(function); 
        object = documents.getObject(reference.getDocumentModel().getName(),
                value);
        
        return (object == null)? false : true;
    }
    
    /**
     * 
     * @param input
     * @param value
     * @return
     */
    private static final boolean isInitial(String name, InputComponent input,
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
    private static final boolean isValueCompatible(InputComponent input) {
        DataElement dataelement;
        String value = input.getValue();
        
        if (value == null || value.trim().length() == 0)
            return true;
        
        dataelement = Shell.getDataElement(input);
        
        if (dataelement == null)
            return true;
        
        switch (dataelement.getType()) {
        case DataType.CHAR:
            return true;
            
        case DataType.NUMC:
            return value.matches("[0-9]+");
            
        case DataType.DEC:
            return value.matches("[0-9\\.]+");
            
        default:
            return false;
        }
    }
    
    /**
     * 
     * @param view
     * @param values
     * @param status
     * @param function
     * @throws Exception
     */
    private static final void processInputs(ViewData view,
            Map<String, ?> values, InputStatus status, Function function)
                    throws Exception {
        Element element;
        String value;
        DataElement dataelement;
        InputComponent input;
        
        for (String name : values.keySet()) {
            element = view.getElement(name);
            
            if (element == null || !element.isDataStorable() ||
                    !element.isEnabled())
                continue;
            
            input = (InputComponent)element;
            value = getString(values, name);
            
            input.setValue(value);
            
            if (!isValueCompatible(input) && !input.isBooleanComponent()) {
                status.input = input;
                status.error = EMISMATCH;
                continue;
            }
           
            if (input.isObligatory() && isInitial(name, input, value)) {
                status.input = input;
                status.error = EINITIAL;
                continue;
            }
            
            if (status.error == 0)
                convertInputValue(input);
            
            dataelement = Shell.getDataElement(input);
            
            if (value == null || dataelement == null)
                continue;
            
            if (dataelement.isUpcase())
                input.setValue(value.toUpperCase());
            
            if (!hasValidReference(input, function) &&
                    input.isReferenceValidable()) {
                status.input = input;
                status.error = EINVALID_REFERENCE;
                continue;
            }
        }
    }
    
    /**
     * 
     * @param view
     * @param values
     * @param function
     * @return
     * @throws Exception
     */
    public static final InputStatus validate(ViewData view, Map<String, ?> values,
            Function function) throws Exception {
        Element element;
        String controlname;
        InputStatus status = new InputStatus();

        try {
            controlname = (String)values.get("action");
        } catch (ClassCastException e) {
            controlname = ((String[])values.get("action"))[0];
        }
        
        if (controlname == null) {
            status.fatal = "null control name.";
            return status;
        }
        
        if (view == null) {
            status.fatal = "null view on action processing.";
            return status;
        }
        
        view.clearRedirect();
        
        if (controlname.equals("")) {
            status.error = WINVALID_ACTION;
            return status;
        }
        
        element = view.getElement(controlname);
        if (element.isControlComponent()) {
            if (!((ControlComponent)element).isCancellable())
                processInputs(view, values, status, function);
        } else {
            processInputs(view, values, status, function);
        }
        
        if (status.input != null) {
            view.setFocus(((Component)status.input).getHtmlName());
            
            switch (status.error) {
            case EINITIAL:
                view.message(Const.ERROR, "field.is.obligatory");
                break;
                
            case EMISMATCH:
                view.message(Const.ERROR, "field.type.mismatch");
                break;
                
            case EINVALID_REFERENCE:
                view.message(Const.ERROR, "invalid.value");
                break;
            }
        }
        
        return status;
    }
}
