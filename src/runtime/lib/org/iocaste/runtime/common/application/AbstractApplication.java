package org.iocaste.runtime.common.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iocaste.protocol.AbstractIocasteServlet;
import org.iocaste.protocol.Handler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.utils.Tools;
import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.RuntimeEngine;
import org.iocaste.runtime.common.managedview.ManagedViewContext;
import org.iocaste.runtime.common.managedview.ManagedViewFactory;
import org.iocaste.runtime.common.IocasteErrorMessage;
import org.iocaste.runtime.common.navcontrol.StandardNavControlConfig;
import org.iocaste.runtime.common.navcontrol.StandardNavControlSpec;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.StandardPageFactory;
import org.iocaste.runtime.common.page.ViewConfig;
import org.iocaste.runtime.common.page.ViewInput;
import org.iocaste.runtime.common.page.ViewSpec;
import org.iocaste.runtime.common.page.ViewSpecItem;
import org.iocaste.runtime.common.protocol.GenericService;
import org.iocaste.runtime.common.protocol.ServiceInterfaceData;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.StyleSheet;

public abstract class AbstractApplication<T extends Context>
		extends AbstractIocasteServlet implements Application<T> {
    private static final long serialVersionUID = 1890996994514012046L;
    private Map<String, T> ctxentries;
    private StandardPageFactory pagefactory;
    private ManagedViewFactory mviewfactory;
    
    public AbstractApplication() {
    	ctxentries = new HashMap<>();
        pagefactory = new StandardPageFactory();
        mviewfactory = new ManagedViewFactory();
    }
    
    private final void buildPages(T context) throws Exception {
        Map<String, AbstractPage> pages;
        Map<String, ManagedViewContext> entities;
    
        entities = context.getEntities();
        for (String key :  entities.keySet())
            mviewfactory.build(context, entities.get(key));
        pages = context.getPages();
        for (String key : pages.keySet())
            pagefactory.instance(context, key, pages.get(key));
    }
    
    private final void buildView(
            AbstractPage page, T context, ServiceInterfaceData servicedata) {
        Locale locale;
        Collection<ViewSpecItem> items;
        int i;
        ToolData data;
        MessageSource messagesrc;
        ViewSpec spec = page.getSpec();
        ViewConfig config = page.getConfig();
        ViewInput input = page.getInput();
        
        if (!page.isReady()) {
            if (servicedata != null)
                configPageStyleData(page, servicedata);
            spec.run(context);
            i = 0;
            items = spec.getItems();
            page.outputview.items = new Object[items.size()];
            for (ViewSpecItem item : items) {
                page.outputview.items[i++] =
                        data = page.instance(item.getName());
                data.type = item.getType();
                data.parent = item.getParent();
            }
            
            if (config != null) {
                config.run(context);
                page.outputview.links = page.getLinks();
            }
            
            if (servicedata != null)
                configOutputStyleData(page);
            
            if ((locale = context.getLocale()) == null)
                page.outputview.locale = new Locale("pt", "BR");
            else
                page.outputview.locale = locale;
            page.outputview.title = page.getTitle();
            messagesrc = context.getMessageSource();
            if (messagesrc != null)
                prepareMessages(page, messagesrc);
            page.setReady(true);
        }
        
        if (input != null)
            input.run(context, true);
    }
    
    @Override
    protected final void config() {
        register(new Services<T>(this));
    }
    
    private final void configOutputStyleData(AbstractPage page) {
    	String csslink;
    	StyleSheet stylesheet = page.getStyleSheet();
    	page.outputview.stylesheet = StyleSheet.convertStyleSheet(stylesheet);
    	page.outputview.styleconst = Tools.toArray(stylesheet.getConstants());
    	if ((csslink = stylesheet.getLink()) == null)
    	    page.add(new HeaderLink("stylesheet", "text/css", csslink));
    }
    
    private final void configPageStyleData(
    		AbstractPage page, ServiceInterfaceData servicedata) {
        GenericService service;
    	Message message;
        ViewExport viewexport;
        AbstractPage navcontrol;
    	
    	message = new Message("style_data_get");
        servicedata.serviceurl = RuntimeEngine.SERVICE_URL;
        service = new GenericService(servicedata);
        viewexport = service.invoke(message);
        page.importStyle(viewexport.styleconst, viewexport.stylesheet);
        navcontrol = page.getChild("navcontrol");
        ((StandardNavControlSpec)navcontrol.getSpec()).ncspec =
        		viewexport.ncspec;
        ((StandardNavControlConfig)navcontrol.getConfig()).ncconfig =
        		(Object[][])viewexport.ncconfig;
    }
    
    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        try {
        	run(req, resp);
    	} catch (Exception e) {
    		throw new ServletException(e);
    	}
    }
    
    @Override
    protected final void doPost(HttpServletRequest req,
    		HttpServletResponse resp) throws ServletException, IOException {
        String servletpath = req.getServletPath();
        
        if (servletpath.equals("/view.html")) {
            super.doPost(req, resp);
            return;
        }
        
        try {
        	run(req, resp);
    	} catch (Exception e) {
    		throw new ServletException(e);
    	}
    }
    
    @Override
    public <U extends Handler> U get(String handler) {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    @Override
    public final String getAppName() {
    	return getServletName();
    }
    
    //	private final ViewExport getExceptionView(Exception e) {
    //		return new ViewExport();
    //	}
    
    @Override
    public Set<String> getMethods() {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    private final ViewExport getView(
    		ServiceInterfaceData servicedata, T context) throws Exception {
        int i;
        AbstractPage child;
        AbstractPage page = context.getPage();
        
        buildView(page, context, servicedata);
        if ((i = page.getSubPagesSize()) > 0) {
            page.outputview.subpages = new Object[i][2];
            i = 0;
            for (String key : page.getChildren()) {
                child = page.getChild(key);
                if (!page.isSubPage(key))
                    continue;
                if (child.getSpec() == null)
                    pagefactory.instance(context, key, child);
                buildView(child, context, null);
                page.outputview.subpages[i][0] = key;
                page.outputview.subpages[i++][1] = child.outputview;
            }
        }
    	return page.outputview;
    }
    
    @Override
    public boolean isAuthorizedCall() {
    	// TODO Auto-generated method stub
    	return false;
    }
    
    private final void move(Context context, ViewExport inputview) {
    	AbstractPage page = context.getPage();
    	page.clearToolData();
    	for (Object object : inputview.items)
    		page.add((ToolData)object);
    	page.outputview = inputview;
    }
    
    private final void prepareMessages(
    		AbstractPage page, MessageSource messagesrc) {
    	Properties messages;
    	int i;
    	
    	messagesrc.entries();
    	messages = messagesrc.getMessages(page.outputview.locale.toString());
    	if (messages == null)
    		return;
    	page.outputview.messages = new String[messages.size()][2];
    	i = 0;
    	for (Object key : messages.keySet()) {
    		page.outputview.messages[i][0] = (String)key;
    		page.outputview.messages[i++][1] = (String)messages.get(key);
    	}
    }
    
    private final void print(HttpServletResponse resp, byte[] content)
    		throws IOException {
        PrintWriter writer;
        
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        writer = resp.getWriter();
        writer.print(new String(content));
        writer.flush();
        writer.close();
    }
    
    @Override
    public Object run(Message message) throws Exception {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    private void run(HttpServletRequest req, HttpServletResponse resp)
    		throws Exception {
        ViewExport outputview;
        RuntimeEngine runtime;
        ServiceInterfaceData servicedata;
        byte[] content;
        T context = null;
    
        req.setCharacterEncoding("UTF-8");
        servicedata = new ServiceInterfaceData();
        servicedata.servername = getServerName(req);
        servicedata.sessionid = req.getSession().getId();
    	runtime = new RuntimeEngine(servicedata);
        try {
            if (servicedata.sessionid == null)
                throw new IocasteException("invalid session.");
            
            context = ctxentries.get(servicedata.sessionid);
            if (context == null) {
                runtime.newContext();
            	ctxentries.put(servicedata.sessionid, context = execute());
            	if (context == null)
            	  throw new IocasteException("application context undefined.");
            	context.set(runtime);
            	context.set(this);
                if (context.getPages().size() > 0)
                    buildPages(context);
            } else {
                outputview = getView(servicedata, context);
                outputview.action = null;
                outputview.reqparameters = Tools.
                        toArray(req.getParameterMap());
                outputview = runtime.processInput(outputview);
        		move(context, outputview);
            	if (outputview.action != null)
            	    run(context, outputview.action);
            }
            
            outputview = getView(servicedata, context);
            content = runtime.processOutput(outputview);
            print(resp, content);
            runtime.commit();
        } catch (Exception e) {
    //            outputview = getExceptionView(e);
    //            content = iocaste.processOutput(outputview);
            ctxentries.remove(servicedata.sessionid);
            runtime.rollback();
            throw e;
        }
    }
    
    private final void run(T context, String action) throws Exception {
        ActionHandler handler = context.getHandler(action);
        if (handler == null)
            throw new IocasteException("no handler defined for %s.", action);
        try {
            handler.run(context);
        } catch (IocasteErrorMessage e) {
            context.runtime().rollback();
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Override
    public Service serviceInstance(String path) {
        // unused in AbstractApplication. Compatibility only.
        return null;
    }
    
    @Override
    public void setAuthorizedCall(boolean authorized) {
        // unused in AbstractApplication. Compatibility only.
    }
    
    @Override
    public void setServerName(String servername) {
        // unused in AbstractApplication. Compatibility only.
    }
    
    @Override
    public void setServletContext(ServletContext context) {
        // unused in AbstractApplication. Compatibility only.
    }
    
    @Override
    public void setSessionid(String sessionid) {
        // unused in AbstractApplication. Compatibility only.
    }
}
