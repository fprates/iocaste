package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public abstract class AbstractPage extends AbstractFunction {
    public static final int EINITIAL = 1;
    public static final int EMISMATCH = 2;
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
    public void back(ViewData viewdata)
            throws Exception {
        String[] entry = shell.popPage();
        viewdata.redirect(entry[0], entry[1]);
        viewdata.dontPushPage();
    }
    
    /**
     * 
     * @param cdata
     * @param vdata
     */
    protected void beforeActionCall(ViewData vdata)
            throws Exception {};
    
    /**
     * 
     * @param cdata
     * @param vdata
     */
    protected void beforeValidation(ViewData vdata)
            throws Exception {};
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ViewData execAction(Message message) throws Exception {
        Method method;
        ViewData view;
        Element element;
        ControlComponent control;
        String action, controlname = message.getString("action");
        InputStatus status = new InputStatus();
        
        if (controlname == null)
            return null;
        
        view = (ViewData)message.get("view");
        if (view == null)
            throw new Exception("Null view on action processing.");
        
        beforeValidation(view);
        
        element = view.getElement(controlname);
        if (element.isControlComponent()) {
            control = (ControlComponent)element;
            
            if (!control.isCancellable()) {
                status = processInputs(view, message.getParameters());
                view = status.view;
            }
        } else {
            control = null;
            status = processInputs(view, message.getParameters());
            view = status.view;
        }
        
        if (status.input != null) {
            view.setFocus(((Component)status.input).getName());
            
            switch (status.error) {
            case EINITIAL:
                view.message(Const.ERROR, "field.is.obligatory");
                break;
            case EMISMATCH:
                view.message(Const.ERROR, "field.type.mismatch");
                break;
            }
            
            return view;
        }
        
        appname = view.getAppName();
        if (shell == null)
            shell = new Shell(this);
        
        beforeActionCall(view);
        
        if (element.getType() == Const.SEARCH_HELP) {
            view.addParameter("sh", element);
            view.redirect("iocaste-search-help", "main");
        } else {
            action = (control == null)?controlname : control.getAction();
            method = this.getClass().getMethod(action, ViewData.class);
            method.invoke(this, view);
        }
        
        if (view.hasPageCall() && (control == null ||
                !control.isCancellable() || control.allowStacking()))
            shell.pushPage(view.getAppName(), view.getPageName());
        
        updateView(view);
        
        return view;
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
        
        if (app == null || page == null)
            throw new Exception("Page not especified.");
        
        view = new ViewData(app, page);
        
        method = this.getClass().getMethod(page, ViewData.class);
        method.invoke(this, view);
        
        for (Container container : view.getContainers())
            registerInputs(view, container);
        
        return view;
    }
    
    /**
     * 
     * @param view
     */
    public void help(ViewData view) { }
    
    /**
     * 
     * @param view
     */
    public void home(ViewData view) { }
    
    /**
     * 
     * @param view
     * @param message
     * @return
     * @throws Exception
     */
    private final InputStatus processInputs(ViewData view,
            Map<String, Object> values) throws Exception {
        Object[] result;
        String 
        servername = new StringBuffer(new Iocaste(this).getHost()).append(
                Shell.SERVER_NAME).toString();
        Message message = new Message();
        InputStatus status = new InputStatus();
        
        message.setId("process_inputs");
        message.add("view", view);
        message.add("values", values);
        
        result = (Object[])Service.callServer(servername, message);
        status.input = (InputComponent)result[0];
        status.error = (Integer)result[1];
        status.view = (ViewData)result[2];
        
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
        
        element.setView(vdata);
        
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
    public int error = 0;
    public InputComponent input = null;
    public ViewData view = null;
}