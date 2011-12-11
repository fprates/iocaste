package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public abstract class AbstractPage extends AbstractFunction {
    private String appname;
    private Shell shell;
    
    public AbstractPage() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
    }
    
    /**
     * 
     * @param controldata
     * @param viewdata
     * @throws Exception 
     */
    public void back(ControlData controldata, ViewData viewdata)
            throws Exception {
        String[] entry = shell.popPage();
        controldata.redirect(entry[0], entry[1]);
    }
    
    /**
     * 
     * @param cdata
     * @param vdata
     */
    protected void beforeActionCall(ControlData cdata, ViewData vdata)
            throws Exception {};
    
    /**
     * 
     * @param cdata
     * @param vdata
     */
    protected void beforeValidation(ControlData cdata, ViewData vdata)
            throws Exception {};
    
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
        Element element;
        ControlComponent control;
        String action = message.getString("action");
        InputStatus status = new InputStatus();
        
        if (action == null)
            return null;
        
        view = (ViewData)message.get("view");
        if (view == null)
            throw new Exception("Null view on action processing.");
        
        controldata = new ControlData();
        controldata.setMessages(view.getMessages());
        controldata.setViewData(view);
        
        beforeValidation(controldata, view);
        
        element = view.getElement(action);
        if (element.isControlComponent()) {
            control = (ControlComponent)element;
            
            if (!control.isCancellable())
                status = processInputs(view, message);
        } else {
            control = null;
            status = processInputs(view, message);
        }
        
        if (status.input != null) {
            view.setFocus(((Component)status.input).getName());
            
            if (status.initial)
                controldata.message(Const.ERROR, "field.is.obligatory");
            
            return controldata;
        }
        
        appname = view.getAppName();
        if (shell == null)
            shell = new Shell(this);
        
        beforeActionCall(controldata, view);
        
        method = this.getClass().getMethod(
                action, ControlData.class, ViewData.class);
        method.invoke(this, controldata, view);
        
        if (controldata.hasPageCall() && (control == null ||
                !control.isCancellable() || control.isHelpControl()))
            shell.pushPage(view.getAppName(), view.getPageName());
        
        updateView(view);
        
        return controldata;
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception 
     */
    protected final ViewData getView(String name) throws Exception {
        return shell.getView(appname, name);
    }
    
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
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters =
            (Map<String, Object>)message.get("parameters");
        
        if (app == null || page == null)
            throw new Exception("Page not especified.");
        
        view = new ViewData(app, page);
        view.setParameters(parameters);
        
        method = this.getClass().getMethod(page, ViewData.class);
        method.invoke(this, view);
        
        for (Container container : view.getContainers())
            registerInputs(view, container);
        
        return view;
    }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public void help(ControlData controldata, ViewData view) { }
    
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
            return false;
            
        dataelement = modelitem.getDataElement();
        
        switch (dataelement.getType()) {
        case DataType.NUMC:
            return (Long.parseLong(test) == 0)? true : false;
            
        default:
            return false;
        }
    }
    
//    protected final boolean isValueCompatible(
//            InputComponent input, String value) {
//        
//    }
    
    /**
     * 
     * @param view
     * @param message
     * @return
     * @throws Exception
     */
    private final InputStatus processInputs(ViewData view, Message message) 
            throws Exception {
        InputComponent input;
        Element element;
        String value;
        DocumentModelItem modelitem;
        String[] inputs = view.getInputs();
        InputStatus status = new InputStatus();
        
        for (String name : inputs) {
            element = view.getElement(name);
            if (!element.isEnabled())
                continue;
            
            input = (InputComponent)element;
            value = message.getString(name);
            modelitem = input.getModelItem();
            
//            if (!isValueCompatible(input, value)) {
//                controldata.message(Const.ERROR, "value.type.mismatch");
//                
//                return controldata;
//            }
            
            input.setValue(value);
            if (input.isObligatory() && (isInitial(name, input, value)) &&
                    (!status.initial)) {
                status.input = input;
                status.initial = true;
                continue;
            }
            
            if (value == null || modelitem == null ||
                    modelitem.getDataElement() == null)
                continue;
            
            if (modelitem.getDataElement().isUpcase())
                input.setValue(value.toUpperCase());
        }
        
        return status;
    }
    
    /**
     * 
     * @param inputs
     * @param element
     */
    private final void registerInputs(ViewData vdata, Element element) {
        Container container;
        
        if (element == null)
            return;
        
        if (element.isContainable()) {
            container = (Container)element;
            
            for (Element element_ : container.getElements())
                registerInputs(vdata, element_);
            
            return;
        }
        
        if (element.isDataStorable())
            vdata.addInput(element.getName());
        
        if (element.hasMultipartSupport())
            vdata.addMultipartElement(element);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    protected final void updateView(ViewData view) throws Exception {
        view.clearInputs();
        for (Container container : view.getContainers())
            registerInputs(view, container);
        
        shell.updateView(view);
    }
}

class InputStatus {
    public boolean initial = false;
    public InputComponent input = null;
}