package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public abstract class AbstractPage extends AbstractFunction {
    public AbstractPage() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
    }
    
    /**
     * 
     * @param controldata
     * @param viewdata
     */
    public void back(ControlData controldata, ViewData viewdata) { }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ViewData getViewData(Message message) throws Exception {
        Method method;
        ViewData view;
        String page = message.getString("page");
        String app = message.getString("app");
        
        if (app == null || page == null)
            throw new Exception("Page not especified.");
        
        view = new ViewData(app, page);
        
        method = this.getClass().getMethod(page, ViewData.class);
        method.invoke(this, view);
        
        for (Container container : view.getContainers())
            registerInputs(view, container);
        
        return view;
    }
    
    /*
     * 
     * Others
     * 
     */
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ControlData execAction(Message message) throws Exception {
        ControlData controldata;
        Method method;
        ViewData view;
        InputComponent input;
        Map<String, InputComponent> inputs;
        String value;
        boolean einitial;
        InputComponent einput;
        String action = message.getString("action");
        
        if (action == null)
            return null;
        
        view = (ViewData)message.get("view");
        if (view == null)
            throw new Exception("Null view on action processing.");
        
        controldata = new ControlData();
        controldata.setMessages(view.getMessages());
        controldata.setViewData(view);
        
        inputs = view.getInputs();
        einitial = false;
        einput = null;
        
        for (String name : inputs.keySet()) {
            input = inputs.get(name);
            value = message.getString(name);
            
//            if (!isValueCompatible(input, value)) {
//                controldata.message(Const.ERROR, "value.type.mismatch");
//                
//                return controldata;
//            }
            
            inputs.get(name).setValue(value);
            
            if (input.isObligatory() && (isInitial(name, input, value)) &&
                    (!einitial)) {
                einput = input;
                einitial = true;
            }
        }
        
        if (einput != null) {
            view.setFocus(((Component)einput).getName());
            
            if (einitial)
                controldata.message(Const.ERROR, "field.is.obligatory");
            
            return controldata;
        }
        
        method = this.getClass().getMethod(
                action, ControlData.class, ViewData.class);
        method.invoke(this, controldata, view);
        
        return controldata;
    }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public final void help(ControlData controldata, ViewData view) { }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public void home(ControlData controldata, ViewData view) { }
    
    /**
     * 
     * @param input
     * @param value
     * @return
     */
    protected final boolean isInitial(String name, InputComponent input,
            String value) throws Exception {
        DataElement dataelement;
        DocumentModelItem modelitem;
        String test = value.trim();
        
        if (test.length() == 0)
            return true;
        
        modelitem = input.getModelItem();
        
        if (modelitem == null)
            throw new Exception(new StringBuffer("Data element for ").
                    append(name).append(" not defined.").toString());
            
        dataelement = modelitem.getDataElement();
        
        switch (dataelement.getType()) {
        case NUMC:
            return (Long.parseLong(test) == 0)? true : false;
            
        default:
            return false;
        }
    }
    
//    protected final boolean isValueCompatible(InputComponent input, String value) {
//        
//    }
    
    /**
     * 
     * @param inputs
     * @param element
     */
    private final void registerInputs(ViewData vdata, Element element) {
        Container container;
        Component component;
        Map<String, InputComponent> inputs = vdata.getInputs();
        
        if (element == null)
            return;
        
        if (element.isContainable()) {
            container = (Container)element;
            
            for (Element element_ : container.getElements())
                registerInputs(vdata, element_);
            
            return;
        }
        
        if (element.isDataStorable()) {
            component = (Component)element;
            
            inputs.put(component.getName(), (InputComponent)component);
        }
        
        if (element.hasMultipartSupport())
            vdata.addMultipartElement(element);
    }
}
