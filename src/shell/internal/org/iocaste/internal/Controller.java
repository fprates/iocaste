package org.iocaste.internal;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
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
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.EventHandler;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.RangeInputComponent;
import org.iocaste.shell.common.Shell;

public class Controller {
    private static final int EINITIAL = 1;
    private static final int EMISMATCH = 2;
    private static final int EINVALID_REFERENCE = 3;
    private static final int WINVALID_ACTION = 4;
    private static final int EVALIDATION = 5;
    private static final int LOW_RANGE = 3;
    private static final int HIGH_RANGE = 4;
    
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
            
            try {
                numberformat = NumberFormat.getNumberInstance(locale);
                return numberformat.parse(value).doubleValue();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        case DataType.DATE:
            if (Shell.isInitial(value))
                return null;
            
            try {
                dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM,
                        locale);
                return dateformat.parse(value);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        case DataType.TIME:
            if (Shell.isInitial(value))
                return null;
            
            try {
                dateformat = DateFormat.getTimeInstance(DateFormat.MEDIUM,
                        locale);
                return dateformat.parse(value);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        case DataType.BYTE:
        case DataType.INT:
        case DataType.LONG:
        case DataType.SHORT:
        case DataType.NUMC:
            if (Shell.isInitial(value))
                return new BigDecimal(0);
            
            return new BigDecimal(value);
        case DataType.CHAR:
            if (Shell.isInitial(value))
                return null;
            
            if (dataelement.isUpcase())
                return value.toUpperCase();
            
            return value;
        case DataType.BOOLEAN:
            if (input.isBooleanComponent())
                return !value.equals("off");
            
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
            if (boolcomponent)
                return (Boolean)value;
            else
                return (((BigDecimal)value).longValue() == 0l)? true : false;
            
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
        
        case DataType.BYTE:
        case DataType.INT:
        case DataType.LONG:
        case DataType.NUMC:
        case DataType.SHORT:
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
        case DataType.TIME:
            try {
                dateformat = DateFormat.getTimeInstance(DateFormat.MEDIUM,
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
    
    private static final void message(
            ControllerData config, Const type, String text) {
        config.state.messagetype = type;
        config.state.messagetext = text;
    }
    
    /**
     * 
     * @param config
     * @param status
     * @throws Exception
     */
    private static final void processInputs(ControllerData config,
            InputStatus status) throws Exception {
        RangeInputComponent rinput;
        Element element;
        String value;
        DataElement dataelement;
        InputComponent input;
        RangeInputStatus ri = new RangeInputStatus();
        
        /*
         * Componentes selecionáveis (como checkboxes), só fornecem
         * valor quando marcados. Processa o estado falso deles antes
         * do processamento principal.
         */
        for (String name : config.pagectx.inputs) {
            value = getString(config.values, name);
            
            if (value != null)
                continue;
            
            element = config.state.view.getElement(name);
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            if (!input.isSelectable())
                continue;
            
            setString(config.values, name, "off");
        }
        
        for (String name : config.values.keySet()) {
            element = config.state.view.getElement(name);
            
            if (element == null || !element.isDataStorable())
                continue;
            
            value = getString(config.values, name);
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
            
            if ((status.control != null) && (status.control.isPopup()))
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
            
            if (!hasValidReference(input, ri, config.function)) {
                status.input = input;
                status.error = EINVALID_REFERENCE;
                continue;
            }
        }
    }
    
    /**
     * 
     * @param element
     * @param config
     * @param status
     * @throws Exception
     */
    private static final void processInputsStage(Element element,
            ControllerData config, InputStatus status) throws Exception {
        EventHandler evhandler = element.getEventHandler();
        
        status.event = (evhandler != null);
        if (element.isControlComponent()) {
            status.control = (ControlComponent)element;
            
            if (!status.control.isCancellable())
                processInputs(config, status);
            
            if (!status.event)
                return;
            
            evhandler.setView(config.state.view);
            evhandler.setInputError(status.error);
            evhandler.onEvent(EventHandler.ON_CLICK,
                    status.control.getAction());
            config.state.reloadable = false;
        } else {
            if (!status.event)
                return;
            
            evhandler.setView(config.state.view);
            evhandler.onEvent(EventHandler.ON_CLICK, null);
            config.state.reloadable = false;
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
     * @param config
     * @return
     * @throws Exception
     */
    public static final InputStatus validate(ControllerData config)
            throws Exception {
        Element element;
        String controlname;
        InputStatus status = new InputStatus();

        controlname = getString(config.values, "action");
        if (controlname == null) {
            status.fatal = "null control name.";
            return status;
        }
        
        if (config.state.view == null) {
            status.fatal = "null view on action processing.";
            return status;
        }
        
        message(config, Const.NONE, null);
        if (controlname.equals("")) {
            status.error = WINVALID_ACTION;
            return status;
        }
        
        element = config.state.view.getElement(controlname);
        if (element != null)
            processInputsStage(element, config, status);
        else
            processInputs(config, status);
        
        if (status.input != null) {
            config.state.view.setFocus(status.input);
            config.state.reloadable = false;
            
            switch (status.error) {
            case EINITIAL:
                message(config, Const.ERROR, "field.is.obligatory");
                break;
                
            case EMISMATCH:
                message(config, Const.ERROR, "field.type.mismatch");
                break;
                
            case EINVALID_REFERENCE:
                message(config, Const.ERROR, "invalid.value");
                break;
                
            case EVALIDATION:
                message(config, Const.ERROR, status.message);
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
        state = new HashMap<>();
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
