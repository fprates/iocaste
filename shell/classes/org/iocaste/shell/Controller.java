package org.iocaste.shell;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.ValidatorConfig;
import org.iocaste.shell.common.ViewData;

public class Controller {
    private static final int EINITIAL = 1;
    private static final int EMISMATCH = 2;
    private static final int EINVALID_REFERENCE = 3;
    private static final int WINVALID_ACTION = 4;
    private static final int EVALIDATION = 5;

    private static final String callCustomValidation(Function function,
            ViewData view, ValidatorConfig validatorcfg) throws Exception {
        String url = new StringBuilder("/").append(view.getAppName()).
                append("/view.html").toString();
        GenericService service = new GenericService(function, url);
        Message message = new Message();
        
        message.setId("custom_validation");
        message.add("config", validatorcfg);
        
        return service.invoke(message);
    }
    
    /**
     * 
     * @param input
     */
    private static void convertInputValue(InputComponent input) {
        String value;
        NumberFormat numberformat;
        DateFormat dateformat;
        Locale locale;
        DataElement dataelement = Shell.getDataElement(input);
        
        if (dataelement == null)
            return;
        
        locale = input.getLocale();
        value = input.getValue();
        
        switch(dataelement.getType()) {
        case DataType.NUMC:
            if (Shell.isInitial(value))
                input.setValue("0");
            break;
            
        case DataType.DEC:
            numberformat = NumberFormat.getNumberInstance(locale);
            
            try {
                input.setValue(numberformat.format(numberformat.parse(value)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            
            break;
            
        case DataType.DATE:
            if (Shell.isInitial(input))
                break;
            
            dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            
            try {
                input.setValue(dateformat.format(dateformat.parse(value)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            
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
            if (((String)value).equals(""))
                return true;
            
            break;
        case DataType.NUMC:
            value = Long.parseLong(svalue);
            if ((Long)value == 0)
                return true;
            
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
    private static final boolean isValueCompatible(InputComponent input) {
        Locale locale;
        NumberFormat numberformat;
        DateFormat dateformat;
        DataElement dataelement;
        String value = input.getValue();
        
        if (value == null || value.trim().length() == 0)
            return true;
        
        dataelement = Shell.getDataElement(input);
        
        if (dataelement == null)
            return true;

        locale = input.getLocale();
        switch (dataelement.getType()) {
        case DataType.CHAR:
            return true;
            
        case DataType.NUMC:
            return value.matches("[0-9]+");
            
        case DataType.DEC:
            try {
                numberformat = NumberFormat.getNumberInstance(locale);
                numberformat.parse(value);
                
                return true;
            } catch (ParseException e) {
                return false;
            }
            
        case DataType.DATE:
            try {
                dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM,
                        locale);
                dateformat.parse(value);
                
                return true;
            } catch (ParseException e) {
                return false;
            }
        default:
            return false;
        }
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param values
     * @param status
     * @throws Exception
     */
    private static final void processInputs(ViewData view, Function function,
            Map<String, ?> values, InputStatus status) throws Exception {
        ValidatorConfig validatorcfg;
        Element element;
        String message, value;
        DataElement dataelement;
        InputComponent input;
        
        /*
         * Componentes selecionáveis (como checkboxes), só fornecem
         * valor quando marcados. Processa o estado falso deles antes
         * do processamento principal.
         */
        for (String name : view.getInputs()) {
            value = getString(values, name);
            
            if (value != null)
                continue;
            
            element = view.getElement(name);
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            if (!input.isSelectable())
                continue;
            
            setString(values, name, "off");
        }
        
        for (String name : values.keySet()) {
            element = view.getElement(name);
            
            if (element == null || !element.isDataStorable() ||
                    !element.isEnabled())
                continue;
            
            value = getString(values, name);

            input = (InputComponent)element;
            input.setValue(value);
            
            if (!isValueCompatible(input) && !input.isBooleanComponent()) {
                status.input = input;
                status.error = EMISMATCH;
                continue;
            }
           
            if (input.isObligatory() && Shell.isInitial(input)) {
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
            
            validatorcfg = input.getValidatorConfig();
            if (validatorcfg != null) {
                message = callCustomValidation(function, view, validatorcfg);
                if (message != null) {
                    status.message = message;
                    status.input = input;
                    status.error = EVALIDATION;
                }
            }
        }
    }
    
    /**
     * 
     * @param values
     * @param name
     * @param value
     */
    private static final void setString(Map<String, ?> values, String name,
            String value) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values_ = (Map<String, Object>)values;

        if (values_.containsKey(name))
            values_.remove(name);
        
        try {
            values_.put(name, value);
        } catch (ClassCastException e) {
            values_.put(name, new String[] {value});
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

        controlname = getString(values, "action");
        
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
                processInputs(view, function, values, status);
        } else {
            processInputs(view, function, values, status);
        }
        
        if (status.input != null) {
            view.setFocus(status.input);
            
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
                
            case EVALIDATION:
                view.message(Const.ERROR, status.message);
                break;
            }
        }
        
        return status;
    }
}
