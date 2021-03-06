package org.iocaste.kernel.runtime.shell;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.RangeOption;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;
import org.iocaste.kernel.documents.Documents;
import org.iocaste.kernel.documents.GetObject;
import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.factories.SpecFactory;
import org.iocaste.kernel.runtime.shell.renderer.StandardHtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.AbstractProcessHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.ControllerData;
import org.iocaste.kernel.runtime.shell.renderer.internal.InputStatus;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.Tools;
import org.iocaste.protocol.utils.Tools.TYPE;
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
import org.iocaste.shell.common.tooldata.ViewExport;

public class ProcessInput extends AbstractProcessHandler {
    private ControllerData config;
    private StandardHtmlRenderer renderer;

    public ProcessInput() {
        renderer = new StandardHtmlRenderer();
    }
    
    private final InputStatus callController(ViewExport viewexport)
    		throws Exception {
        InputStatus status;
        ComponentEntry entry;
        SpecFactory factory;
        
        status = validate();
        if (status.fatal != null)
            throw new IocasteException(status.fatal);
        
        viewexport.msgtype = status.msgtype;
        viewexport.message = status.message;
        viewexport.msgargs = status.msgargs;
        viewexport.action = null;
        if (status.msgtype == Const.ERROR)
            return status;
        
        if (!status.event)
            viewexport.action = getString(config.values, "action");
        
        for (String key : config.state.viewctx.entries.keySet()) {
            entry = config.state.viewctx.entries.get(key);
            if (entry.component != null) {
            	entry.component.load();
            	continue;
            }
            
            factory = config.state.viewctx.factories.get(entry.data.type);
            if (factory != null)
                factory.update(config.state.viewctx, key);
        }
        
        return status;
    }
    
    /**
     * 
     * @param app
     * @return
     */
    protected final String composeUrl(String app) {
        String servername = getFunction().getServlet().getServerName();
        return new StringBuffer(servername).append("/").
                append(app).append("/view.html").toString();
    }
    
    /**
     * 
     * @param input
     */
    private void convertInputValue(InputComponent input,
            RangeInputStatus ri) {
        DataElement dataelement;
        String value = (String)getUniversalInputValue(input, ri);
        
        dataelement = Shell.getDataElement(input);
        if (dataelement == null) {
            if (input.isBooleanComponent())
                input.setSelected((value != null) && value.equals("on"));
            
            return;
        }
        
        setUniversalInputValue(input, convertValue(value, input), ri);
    }
    
    private Object convertValue(String value, InputComponent input) {
        Locale locale = input.getLocale();
        DataElement dataelement = Shell.getDataElement(input);
        
        switch (dataelement.getType()) {
        case DataType.BOOLEAN:
            if (input.isBooleanComponent())
                return !((value == null) || value.equals("off"));
            
            return value;
        default:
            return org.iocaste.documents.common.Documents.convertValue(value, dataelement, locale);
        }
    }
    
    private final Element getControl(String name) {
        Map<String, Map<String, ActionEventHandler>> events;
        Element element = config.state.viewctx.view.getElement(name);
        
        if ((element != null) ||
                (events = config.state.viewctx.actions.get(name)) == null)
            return element;
        
        for (String key : events.keySet()) {
            for (ActionEventHandler handler : events.get(key).values()) {
                name = handler.name;
                return config.state.viewctx.view.getElement(name);
            }
            return null;
        }
        return null;
    }
    
    /**
     * 
     * @param values
     * @param name
     * @return
     */
    private final String getString(Map<String, ?> values, String name) {
        try {
            return (String)values.get(name);
        } catch (ClassCastException e) {
            return ((String[])values.get(name))[0];
        }
    }
    
    private final String getTranslation(InputComponent input) {
        String text = input.getLabel();
        return (text == null)? input.getHtmlName() : text;
    }
    
    /**
     * 
     * @param input
     * @param type
     * @return
     */
    private final Object getUniversalInputValue(InputComponent input,
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
    private final boolean hasValidReference(InputComponent input,
    		RangeInputStatus ri, ControllerData config) throws Exception {
    	Documents documents;
        Connection connection;
    	GetObject objectget;
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
            nsinput = config.state.viewctx.view.getElement(nsreference);
            ns = nsinput.get();
        } else {
            ns = input.getNS();
        }

        documents = ((RuntimeEngine)getFunction()).documents;
        connection = documents.database.
                getDBConnection(config.state.viewctx.sessionid);
        objectget = documents.get("get_object");
        object = objectget.run(connection, documents,
                reference.getDocumentModel().getName(),
                ns,
                getUniversalInputValue(input, ri));
        
        return (object != null);
    }
    
    private final boolean isAction(String element, String name) {
        Map<String, ActionEventHandler> handlers;
        
        if (!config.state.viewctx.actions.containsKey(element))
            return false;
        handlers = config.state.viewctx.actions.get(element).get(name);
        if (handlers == null)
            return false;
        for (String key : handlers.keySet())
            return handlers.get(key).submit;
        return false;
    }
    
    /**
     * 
     * @param input
     * @param ri
     * @return
     */
    private final boolean isInitial(InputComponent input,
            RangeInputStatus ri) {
        Object value = getUniversalInputValue(input, ri);
        
        return isValueInitial(value, Shell.getDataElement(input),
                input.isBooleanComponent());
    }
    
    private final boolean isValueInitial(Object value,
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
    private final boolean isValueCompatible(InputComponent input,
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
    
    private final boolean isElementVisible(Element element) {
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
    
    private final void message(Const type, String text, Object... args) {
        config.state.viewctx.messagetype = type;
        config.state.viewctx.messagetext =
        		getMessage(config.state.viewctx.view, text);
        config.state.viewctx.messageargs = args;
    }
    
    public static final String getMessage(View view, String id) {
        Map<String, String> messages;
        messages = msgsource.get(view.getLocale().toString());
        return (messages == null)? id : messages.get(id);
    }
    
    /**
     * 
     * @param status
     * @throws Exception
     */
    private final void processInputs(InputStatus status) throws Exception {
        Element element;
        String value;
        DataElement dataelement;
        InputComponent input;
        RangeInputStatus ri;
        Map<String, Element> elements;
        
        /*
         * Componentes selecionáveis (como checkboxes), só fornecem
         * valor quando marcados. Processa o estado falso deles antes
         * do processamento principal.
         */
        ri = new RangeInputStatus();
        elements = config.state.viewctx.view.getElements();
        for (String name : elements.keySet()) {
            element = elements.get(name);
            if (!element.isDataStorable())
                continue;
            input = (InputComponent)element;
            value = getString(config.values, name);
            if (input.isSelectable() && isElementVisible(input))
                if ((value == null) || (value.length() == 0))
                    setString(config.values, name, value = "off");
            
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
                ri.set(config.state.viewctx.view, input.getMaster());
                if (ri.state.master == null)
                    continue;
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

        for (String name : config.values.keySet()) {
            element = config.state.viewctx.view.getElement(name);
            
            if (element == null || !element.isDataStorable() ||
                    !element.isEnabled())
                continue;
            
            input = (InputComponent)element;
            dataelement = Shell.getDataElement(input);
            if (input.get() == null || dataelement == null)
                continue;
            
            if (hasValidReference(input, ri, config))
                continue;

            status.input = input;
            status.error = EINVALID_REFERENCE;
            status.msgtype = (input.isEnabled())? Const.ERROR : Const.WARNING;
            status.msgargs = new String[] {getTranslation(input)};
        }
    }
    
    /**
     * 
     * @param element
     * @param config
     * @param status
     * @throws Exception
     */
    private final void processInputsStage(
    		Element element, InputStatus status) throws Exception {
        EventHandler evhandler;
        String event;
        
        if (element.isControlComponent()) {
            status.control = (ControlComponent)element;
            if (!status.control.isCancellable())
                processInputs(status);
        }
        
        event = getString(config.values, "event");
        evhandler = element.getEventHandler(event);
        if (evhandler == null)
            return;
        evhandler.setView(config.state.viewctx.view);
        evhandler.setInputError(status.error);
        evhandler.setErrorType(status.msgtype);
        evhandler.onEvent(status.control);
        config.state.reloadable = false;
        status.event = true;
    }

    public final InputStatus run(ControllerData config) throws Exception {
        this.config = config;
        return callController(config.state.viewctx.viewexport);
    }
    
	@Override
	public Object run(Message message) throws Exception {
        String actionname = null;
        Map<String, String[]> parameters;
        ProcessOutputData data = new ProcessOutputData();
        ProcessOutput outputprocess = getFunction().get("output_process");
        
        data.viewctx = new ViewContext();
        data.viewctx.sessionid = message.getSessionid();
        data.viewctx.viewexport = message.get("view");
        parameters = Tools.
                toMap(TYPE.HASH, data.viewctx.viewexport.reqparameters);
        
        config = new ControllerData();
        config.state.viewctx = data.viewctx;
        config.state.viewctx.offline = true;
        outputprocess.run(data);
        
        renderer.run(config.state.viewctx);
        for (String key : parameters.keySet()) {
            if (isAction(key, key)) {
                if (actionname != null)
                    continue;
                actionname = parameters.get(key)[0];
                config.values.put("action", parameters.get(key));
            } else {
                config.values.put(key, parameters.get(key));
            }
        }
        
        if (parameters.size() == 0)
            return data.viewctx.viewexport;
        
        config.function = getFunction();
        callController(data.viewctx.viewexport);
        return data.viewctx.viewexport;
    }
    
    /**
     * 
     * @param input
     * @param object
     * @param ri
     */
    private final void setRangeValue(InputComponent input, Object object,
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
    private final void setString(
    		Map<String, ?> values, String name, String value) {
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
    private final void setUniversalInputValue(InputComponent input,
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
    public final InputStatus validate() throws Exception {
        Element element;
        InputStatus status = new InputStatus();

        config.state.viewctx.controlname = getString(config.values, "action");
        if (config.state.viewctx.controlname == null) {
            status.fatal = "null control name.";
            return status;
        }
        
        if (config.state.viewctx.view == null) {
            status.fatal = "null view on action processing.";
            return status;
        }
        
        message(Const.NONE, null);
        element = getControl(config.state.viewctx.controlname);
        if (element != null)
            processInputsStage(element, status);
        else
            processInputs(status);
        
        if (status.input == null)
            return status;
        
        config.state.viewctx.view.setFocus(status.input);
        config.state.reloadable = false;
        
        if (status.error == 0)
            return status;

        status.message = msgconv.get(status.error);
        message(status.msgtype, status.message, status.msgargs);
        
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
