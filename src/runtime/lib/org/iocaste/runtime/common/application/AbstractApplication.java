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
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.utils.Tools;
import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.RuntimeEngine;
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
    private StandardPageFactory factory;
    
	public AbstractApplication() {
		ctxentries = new HashMap<>();
        factory = new StandardPageFactory();
	}
	
    private final void buildPages(T context) throws Exception {
        Map<String, AbstractPage> pages;
        
        pages = context.getPages();
        for (String key : pages.keySet())
            factory.instance(context, key, pages.get(key));
    }
    
    private final void buildView(
            AbstractPage page, T context, ServiceInterfaceData servicedata) {
        Collection<ViewSpecItem> items;
        int i;
        ToolData data;
        MessageSource messagesrc;
        ViewSpec spec = page.getSpec();
        ViewConfig config = page.getConfig();
        ViewInput input = page.getInput();
        
        if (!spec.isInitialized()) {
            if (servicedata != null)
                configPageStyleData(page, servicedata);
            spec.run(context);
            i = 0;
            items = spec.getItems();
            page.outputview.items = new Object[items.size()];
            for (ViewSpecItem item : items) {
                page.outputview.items[i++] =
                        data = new ToolData(item.getType());
                data.name = item.getName();
                data.parent = item.getParent();
                page.add(data);
            }
            
            if (config != null) {
                config.run(context);
                page.outputview.links = page.getLinks();
            }
            
            if (servicedata != null)
                configOutputStyleData(page);
            
            page.outputview.locale = new Locale("pt", "BR");
            page.outputview.title = page.getTitle();
            messagesrc = context.getMessageSource();
            if (messagesrc != null)
                prepareMessages(page, messagesrc);
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
	
	private final ViewExport getExceptionView() {
		return new ViewExport();
	}

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
                    factory.instance(context, key, child);
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
		Transaction transaction;
        T context;
        ViewExport outputview;
        ActionHandler handler;
        RuntimeEngine iocaste;
        ServiceInterfaceData servicedata;
        byte[] content;

        req.setCharacterEncoding("UTF-8");
        servicedata = new ServiceInterfaceData();
        servicedata.servername = getServerName(req);
        servicedata.sessionid = req.getSession().getId();
		iocaste = new RuntimeEngine(servicedata);
        transaction = transactionInstance(iocaste);
        servicedata.sessionid = transaction.begin(req);
        if (servicedata.sessionid == null) {
        	outputview = getExceptionView();
        } else {
            context = ctxentries.get(servicedata.sessionid);
            if (context == null) {
            	ctxentries.put(servicedata.sessionid, context = execute());
            	context.set(iocaste);
            	context.set(this);
                if (context.getPages().size() > 0)
                    buildPages(context);
            } else {
                outputview = getView(servicedata, context);
                outputview.reqparameters = Tools.toArray(req.getParameterMap());
                outputview = iocaste.processInput(outputview);
        		move(context, outputview);
            	if (outputview.action != null) {
                	handler = (ActionHandler)
                			context.getHandler(outputview.action);
                	try {
                		handler.run(context);
                	} catch (IocasteErrorMessage e) {
                		
                	} catch (Exception e) {
                		throw e;
                	}
            	}
            }
            outputview = getView(servicedata, context);
        }
        
        content = iocaste.processOutput(outputview);
        transaction.finish(resp);
        print(resp, content);
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
	
    private final Transaction transactionInstance(RuntimeEngine runtime) {
    	return new Transaction(runtime);
    }
}
