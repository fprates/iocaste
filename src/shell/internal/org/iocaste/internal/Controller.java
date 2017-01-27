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
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.EventHandler;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.RangeInputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;

public class Controller {
    private static final int EINITIAL = 1;
    private static final int EMISMATCH = 2;
    private static final int EINVALID_REFERENCE = 3;
    private static final int LOW_RANGE = 3;
    private static final int HIGH_RANGE = 4;
    public static Map<String, String> messages;
    private static Map<Integer, String> msgconv;
    
    static {
        messages = new HashMap<>();
        messages.put("calendar", "Calendário");
        messages.put("field.is.obligatory", "Campo é obrigatório (%s).");
        messages.put("field.type.mismatch",
                "Tipo de valor incompatível com campo.");
        messages.put("grid.options", "Opções da grid");
        messages.put("input.options", "Opções da entrada");
        messages.put("invalid.value", "Valor inválido (%s).");
        messages.put("not.connected", "Não conectado");
        messages.put("required", "Obrigatório");
        messages.put("user.not.authorized", "Usuário não autorizado.");
        messages.put("values", "Valores possíveis");
        
        msgconv= new HashMap<>();
        msgconv.put(Controller.EINITIAL, "field.is.obligatory");
        msgconv.put(Controller.EMISMATCH, "field.type.mismatch");
        msgconv.put(Controller.EINVALID_REFERENCE, "invalid.value");
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
        Locale locale = input.getLocale();
        DataElement dataelement = Shell.getDataElement(input);
        
        switch (dataelement.getType()) {
        case DataType.BOOLEAN:
            if (input.isBooleanComponent())
                return !value.equals("off");
            
            return value;
        default:
            return Documents.convertValue(value, dataelement, locale);
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
    
    private static final String getTranslation(InputComponent input) {
        String text = input.getLabel();
        return (text == null)? input.getHtmlName() : text;
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
    private static final boolean hasValidReference(Documents documents,
            InputComponent input, RangeInputStatus ri, ControllerData config)
                    throws Exception {
        InputComponent nsinput;
        Object ns;
        String nsreference;
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
        
        nsreference = input.getNSReference();
        if (nsreference != null) {
            nsinput = config.state.view.getElement(nsreference);
            ns = nsinput.get();
        } else {
            ns = input.getNS();
        }
        object = documents.getObject(
                reference.getDocumentModel().getName(),
                ns,
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
    
    private static final boolean isElementVisible(Element element) {
        Container container;
        String htmlname;
        
        if (element == null)
            return true;
        
        if (!element.isVisible())
            return false;
        
        if (element.isContainable())
            container = ((Container)element).getContainer();
        else
            container = ((Component)element).getContainer();
        
        if (container != null) {
            htmlname = element.getHtmlName();
            if (htmlname.equals(container.getHtmlName()))
                throw new RuntimeException(new StringBuilder(
                    "element-container lock detected for '").
                    append(htmlname).
                    append("'. element has the same name of its container.").
                    toString());
        }
        
        return isElementVisible(container);
    }
    
    private static final void message(
            ControllerData config, Const type, String text, Object... args) {
        config.state.messagetype = type;
        config.state.messagetext = messages.get(text);
        config.state.messageargs = args;
    }
    
    /**
     * 
     * @param config
     * @param status
     * @throws Exception
     */
    private static final void processInputs(ControllerData config,
            InputStatus status) throws Exception {
        Element element;
        String value;
        DataElement dataelement;
        InputComponent input;
        RangeInputStatus ri;
        Documents documents;
        
        /*
         * Componentes selecionáveis (como checkboxes), só fornecem
         * valor quando marcados. Processa o estado falso deles antes
         * do processamento principal.
         */
        for (String name : config.pagectx.inputs) {
            value = getString(config.values, name);
            
            if ((value != null) && (value.length() > 0))
                continue;
            
            element = config.state.view.getElement(name);
            if (!element.isDataStorable())
                continue;
            
            input = (InputComponent)element;
            if (!input.isSelectable())
                continue;
            
            if (!isElementVisible(input))
                continue;
            
            setString(config.values, name, "off");
        }

        ri = new RangeInputStatus();
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
                ri.set(config.state.view, input.getMaster());
                if (name.equals(ri.state.master.getLowHtmlName()))
                    ri.type = LOW_RANGE;
                else
                    ri.type = HIGH_RANGE;
                
                ri.value = value;
                break;
            }
            
            if (!input.isBooleanComponent() &&
                    !isValueCompatible(input, ri)) {
                status.input = input;
                status.error = EMISMATCH;
                status.msgtype = Const.ERROR;
                input.set(null);
                continue;
            }
            
            if (input.isStackable())
                continue;
            
            convertInputValue(input, ri);
            
            if ((status.control != null) && (status.control.isPopup()))
                continue;
            
            if (input.isObligatory() && isInitial(input, ri) &&
                    input.isEnabled() && input.isVisible()) {
                status.input = input;
                status.error = EINITIAL;
                status.msgtype = Const.ERROR;
                status.msgargs = new String[] {getTranslation(input)};
            }
        }
        
        if (status.error != 0)
            return;

        documents = new Documents(config.function);
        for (String name : config.values.keySet()) {
            element = config.state.view.getElement(name);
            
            if (element == null || !element.isDataStorable() ||
                    !element.isEnabled())
                continue;
            
            input = (InputComponent)element;
            dataelement = Shell.getDataElement(input);
            if (input.get() == null || dataelement == null)
                continue;
            
            if (hasValidReference(documents, input, ri, config))
                continue;

            status.input = input;
            status.error = EINVALID_REFERENCE;
            status.msgtype = (input.isEnabled())? Const.ERROR : Const.WARNING;
            status.msgargs = new String[] {getTranslation(input)};
        }
        documents.commit();
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
            evhandler.setErrorType(status.msgtype);
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
        ValueRange range;
        ValueRangeItem rangeitem = null;
        DataElement dataelement = Shell.getDataElement(input);

        name = input.getMaster();
        if (name == null)
            name = input.getHtmlName();
        
        if (isValueInitial(object, dataelement, input.isBooleanComponent())) {
            ri.value = null;
            if (!ri.state.low) {
                ri.state.master.set(null);
                ri.state.low = true;
                return;
            }
            
            if (!ri.state.high)
                ri.state.high = true;
            
            return;
        }

        range = ri.state.master.get();
        if (range == null) {
            range = new ValueRange();
            ri.state.master.set(range);
        }

        if (range.length() > 0)
            rangeitem = range.get(0);
        
        if (rangeitem == null) {
            rangeitem = new ValueRangeItem();
            range.add(rangeitem);
        }
        
        switch (ri.type) {
        case LOW_RANGE:
            if (!ri.state.high) {
                if (dataelement.getType() == DataType.CHAR) {
                    value = (String)object;
                    if (rangeitem.getOption() == null) {
                        if (value != null && value.contains("*"))
                            rangeitem.setOption(RangeOption.CP);
                        else
                            rangeitem.setOption(RangeOption.EQ);
                    }
                } else {
                    rangeitem.setOption(RangeOption.EQ);
                }
            }

            rangeitem.setLow(object);
            ri.state.low = true;
            break;
        default:
            rangeitem.setOption(RangeOption.BT);
            rangeitem.setHigh(object);
            ri.state.high = true;
            break;
        }
        
        ri.value = object;
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
        element = config.state.view.getElement(controlname);
        if ((element == null) && config.pagectx.isAction(controlname)) {
            controlname = config.pagectx.getControlName(controlname);
            element = config.state.view.getElement(controlname);
        }
        
        if (element != null)
            processInputsStage(element, config, status);
        else
            processInputs(config, status);
        
        if (status.input == null)
            return status;
        
        config.state.view.setFocus(status.input);
        config.state.reloadable = false;
        
        if (status.error == 0)
            return status;

        status.message = msgconv.get(status.error);
        message(config, status.msgtype, status.message, status.msgargs);
        
        return status;
    }
}

class RangeInputStatus {
    private Map<String, RangeInputState> states;
    public RangeInputState state;
    public Object value;
    public int type;
    
    public RangeInputStatus() {
        states = new HashMap<>();
    }
    
    public final void set(View view, String name) {
        RangeInputComponent component = view.getElement(name);
        
        state = states.get(name);
        if (state != null)
            return;
        state = new RangeInputState();
        state.master = component;
        states.put(name, state);
    }
}

class RangeInputState {
    public boolean low, high;
    public RangeInputComponent master;
}