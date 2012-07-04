package org.iocaste.internal;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.RangeOption;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.RangeInputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.ValidatorConfig;
import org.iocaste.shell.common.View;

public class Controller {
    private static final int EINITIAL = 1;
    private static final int EMISMATCH = 2;
    private static final int EINVALID_REFERENCE = 3;
    private static final int WINVALID_ACTION = 4;
    private static final int EVALIDATION = 5;
    private static final int LOW_RANGE = 3;
    private static final int HIGH_RANGE = 4;
    
    private static final ValidatorConfig callCustomValidation(Function function,
            View view, ValidatorConfig validatorcfg) throws Exception {
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
    private static void convertInputValue(InputComponent input,
            RangeInputStatus ri) {
        DataElement dataelement;
        String value = (String)getUniversalInputValue(input, ri);
        
        dataelement = Shell.getDataElement(input);
        if (dataelement == null) {
            if (input.isBooleanComponent())
                input.setSelected((value.equals("on")? true : false));
            
            return;
        }
        
        setUniversalInputValue(input, convertValue(value, input), ri);
    }
    
    private static Object convertValue(String value, InputComponent input) {
        NumberFormat numberformat;
        DateFormat dateformat;
        Locale locale = input.getLocale();
        DataElement dataelement = Shell.getDataElement(input);
        
        switch(dataelement.getType()) {
        case DataType.DEC:
            if (Shell.isInitial(value))
                return 0d;
            else
                try {
                    numberformat = NumberFormat.getNumberInstance(locale);
                    
                    return numberformat.parse(value).doubleValue();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            
        case DataType.DATE:
            if (Shell.isInitial(value))
                return null;
            else
                try {
                    dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM,
                            locale);
                    return dateformat.parse(value);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            
        case DataType.NUMC:
            if (Shell.isInitial(value)) {
                if (dataelement.getLength() < DataType.MAX_INT_LEN)
                    return 0;
                else
                    return 0l;
            } else {
                if (dataelement.getLength() < DataType.MAX_INT_LEN)
                    return Integer.parseInt(value);
                else
                    return Long.parseLong(value);
            }
            
        case DataType.CHAR:
            if (Shell.isInitial(value)) {
                return null;
            } else {
                if (dataelement.isUpcase())
                    return value.toUpperCase();
                else
                    return value;
            }
            
        case DataType.BOOLEAN:
            if (input.isBooleanComponent())
                return (value.equals("off"))? false : true;
            else
                return value;
            
        default:
            return null;
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
     * @param type
     * @return
     */
    private static final Object getUniversalInputValue(InputComponent input,
            RangeInputStatus ri) {
        switch (ri.type) {
        case LOW_RANGE:
        case HIGH_RANGE:
            return ri.value;
        default:
            return input.get();
        }
    }
    
    /**
     * 
     * @param input
     * @param type
     * @param function
     * @return
     * @throws Exception
     */
    private static final boolean hasValidReference(InputComponent input,
            RangeInputStatus ri, Function function) throws Exception {
        Documents documents;
        ExtendedObject object;
        DocumentModelItem reference, item;
        
        if (isInitial(input, ri))
            return true;
        
        item = input.getModelItem();
        if (item == null)
            return true;
        
        reference = item.getReference();
        if (reference == null)
            return true;
        
        documents = new Documents(function); 
        object = documents.getObject(reference.getDocumentModel().getName(),
                getUniversalInputValue(input, ri));
        
        return (object != null);
    }
    
    /**
     * 
     * @param input
     * @param ri
     * @return
     */
    private static final boolean isInitial(InputComponent input,
            RangeInputStatus ri) {
        Object value = getUniversalInputValue(input, ri);
        
        return isValueInitial(value, Shell.getDataElement(input),
                input.isBooleanComponent());
    }
    
    private static final boolean isValueInitial(Object value,
            DataElement dataelement, boolean boolcomponent) {
        if (value == null)
            return true;
        
        if (dataelement == null)
            return Shell.isInitial((String)value);
        
        switch (dataelement.getType()) {
        case DataType.NUMC:
            if (boolcomponent) {
                return (Boolean)value;
            } else {
                if (dataelement.getLength() < DataType.MAX_INT_LEN)
                    return ((Integer)value == 0)? true : false;
                else
                    return ((Long)value == 0l)? true : false;
            }
        case DataType.DEC:
            return (((Number)value).doubleValue() == 0)? true : false;

        default:
            return Shell.isInitial(value.toString());
        }
    }
    
    /**
     * 
     * @param input
     * @param ri
     * @return
     */
    private static final boolean isValueCompatible(InputComponent input,
            RangeInputStatus ri) {
        Locale locale;
        NumberFormat numberformat;
        DateFormat dateformat;
        DataElement dataelement;
        String value = (String)getUniversalInputValue(input, ri);
        
        if (Shell.isInitial(value))
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
    private static final void processInputs(View view, Function function,
            Map<String, ?> values, InputStatus status) throws Exception {
        RangeInputComponent rinput;
        ValidatorConfig validatorcfg;
        Element element;
        String value;
        DataElement dataelement;
        InputComponent input;
        List<InputComponent> validations = new ArrayList<InputComponent>();
        RangeInputStatus ri = new RangeInputStatus();
        
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
            
            if (element == null || !element.isDataStorable())
                continue;
            
            value = getString(values, name);
            input = (InputComponent)element;
            ri.type = 0;
            if (input.isSelectable() && input.isStackable())
                ri.type = 2;
            
            if (input.isValueRangeComponent())
                ri.type = 1;
            
            switch (ri.type) {
            case 0:
                input.set(value);
                break;
            case 1:
                rinput = (RangeInputComponent)input;
                if (name.equals(rinput.getLowHtmlName()))
                    ri.type = LOW_RANGE;
                else
                    ri.type = HIGH_RANGE;
                
                ri.addCount(rinput.getHtmlName());
                ri.value = value;
                
                break;
            case 2:
                for (InputComponent input_ : input.getStackComponents())
                    input_.setSelected(value.equals(input_.getName()));
                break;
            }
            
            if (!input.isBooleanComponent() &&
                    !isValueCompatible(input, ri)) {
                status.input = input;
                status.error = EMISMATCH;
                input.set(null);
                continue;
            }
            
            if (input.isStackable())
                continue;
            
            convertInputValue(input, ri);
            
            if (status.control != null &&
                    status.control.getType() == Const.SEARCH_HELP)
                continue;
            
            if (input.isObligatory() && isInitial(input, ri) &&
                    input.isEnabled()) {
                status.input = input;
                status.error = EINITIAL;
                continue;
            }
            
            dataelement = Shell.getDataElement(input);
            if (value == null || dataelement == null)
                continue;
            
            if (!hasValidReference(input, ri, function)) {
                status.input = input;
                status.error = EINVALID_REFERENCE;
                continue;
            }
            
            validatorcfg = input.getValidatorConfig();
            if (validatorcfg != null &&
                    !Shell.isInitial(validatorcfg.getClassName()))
                validations.add(input);
        }
        
        if (status.error != 0)
            return;
        
        for (InputComponent input_ : validations) {
            validatorcfg = input_.getValidatorConfig();
            validatorcfg = callCustomValidation(function, view, validatorcfg);
            
            status.message = validatorcfg.getMessage();
            if (status.message == null) {
                for (String inputname : validatorcfg.getInputNames()) {
                    input = validatorcfg.getInput(inputname);
                    input_ = view.getElement(input.getHtmlName());
                    input_.set(input.get());
                    input_.setText(input.getText());
                }
                
                continue;
            }
            
            status.input = input_;
            status.error = EVALIDATION;
            break;
        }
    }
    
    /**
     * 
     * @param input
     * @param object
     * @param ri
     */
    private static final void setRangeValue(InputComponent input, Object object,
            RangeInputStatus ri) {
        String name, value;
        RangeInputComponent rinput;
        ValueRange range;
        ValueRangeItem rangeitem = null;
        DataElement dataelement = Shell.getDataElement(input);

        name = input.getHtmlName();
        if (isValueInitial(object, dataelement, input.isBooleanComponent())) {
            if (ri.getCount(name) == 2)
                ri.clearCount(name);
            ri.value = object;
            return;
        }

        rinput = (RangeInputComponent)input;
        range = rinput.get();
        if (range == null) {
            range = new ValueRange();
            input.set(range);
        }

        if (range.length() > 0)
            rangeitem = range.get(0);
        
        if (rangeitem == null) {
            rangeitem = new ValueRangeItem();
            range.add(rangeitem);
        }
        
        switch (ri.type) {
        case LOW_RANGE:
            if (dataelement.getType() == DataType.CHAR) {
                value = (String)object;
                if (ri.getCount(name) != 2 || rangeitem.getOption() == null) {
                    if (value != null && value.contains("*"))
                        rangeitem.setOption(RangeOption.CP);
                    else
                        rangeitem.setOption(RangeOption.EQ);
                }
            } else {
                rangeitem.setOption(RangeOption.EQ);
            }

            rangeitem.setLow(object);
            break;
        default:
            rangeitem.setOption(RangeOption.BT);
            rangeitem.setHigh(object);
            break;
        }
        
        ri.value = object;
        if (ri.getCount(name) == 2)
            ri.clearCount(name);
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
     * @param input
     * @param value
     * @param ri
     */
    private static final void setUniversalInputValue(InputComponent input,
            Object value, RangeInputStatus ri) {
        switch (ri.type) {
        case LOW_RANGE:
        case HIGH_RANGE:
            setRangeValue(input, value, ri);
            break;
        default:
            input.set(value);
            break;
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
    public static final InputStatus validate(View view,
            Map<String, ?> values, Function function) throws Exception {
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
        if (element != null && element.isControlComponent()) {
            status.control = (ControlComponent)element;
            if (!status.control.isCancellable())
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

class RangeInputStatus {
    public byte type;
    public Object value;
    private Map<String, Byte> state;
    
    public RangeInputStatus() {
        state = new HashMap<String, Byte>();
    }
    
    public final void addCount(String name) {
        byte count;
        
        if (state.containsKey(name))
            count = state.get(name);
        else
            count = 0;
        state.put(name, ++count);
    }
    
    public final void clearCount(String name) {
        state.put(name, (byte)0);
    }
    
    public final byte getCount(String name) {
        return state.get(name);
    }
}
