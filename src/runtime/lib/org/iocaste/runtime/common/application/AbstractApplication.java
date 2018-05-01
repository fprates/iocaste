package org.iocaste.runtime.common.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import org.iocaste.runtime.common.protocol.GenericService;
import org.iocaste.runtime.common.protocol.ServiceInterfaceData;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ViewExport;
import org.iocaste.shell.common.tooldata.ViewSpecItem;

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
        ViewSpec spec = page.getSpec();
        ViewConfig config = page.getConfig();
        ViewInput input = page.getInput();
        
        if (!page.isReady()) {
            if (servicedata != null) {
                configPageStyleData(page, servicedata);
                page.outputview.path = servicedata.path;
            }
            spec.run(context);
            i = 0;
            items = spec.getItems();
            page.outputview.items = new Object[items.size()];
            for (ViewSpecItem item : items) {
                data = page.instance(item.getName());
                data.clear();
                data.type = item.getType();
                data.parent = item.getParent();
                page.outputview.items[i++] = data;
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
            prepareMessages(page, context.getMessageSource());
            page.setReady(true);
        } else {
            for (i = 0; i < page.outputview.items.length; i++) {
                data = (ToolData)page.outputview.items[i];
                page.outputview.items[i] = page.instance(data.name);
            }
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
        return null;
    }
    
    //  private final ViewExport getExceptionView(Exception e) {
    //      return new ViewExport();
    //  }
    
    @Override
    public Set<String> getMethods() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public final AbstractIocasteServlet getServlet() {
        return this;
    }
    
    private final ViewExport getView(
            ServiceInterfaceData servicedata, T context) throws Exception {
        int i;
        AbstractPage child;
        AbstractPage page = context.getPage();
        
        if (page == null)
            throw new IocasteException(
                    "page %s is undefined.", context.getPageName());
        
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
    
    private final void initContext(ContextData<T> ctxdata) throws Exception {
        ctxdata.runtime.newContext();
        ctxentries.put(
                ctxdata.servicedata.sessionid, ctxdata.context = execute());
        if (ctxdata.context == null)
          throw new IocasteException("application context undefined.");
        ctxdata.context.set(ctxdata.runtime);
        ctxdata.context.set(new ContextFunction<T>(ctxentries,
                ctxdata.servicedata.appname, getServerName(),
                ctxdata.servicedata.sessionid));
        if (ctxdata.context.isConnectionByTicket())
            if (!loginByTicket(ctxdata.req, ctxdata.context)) {
                ctxdata.context.setPageName("login-error");
                ctxdata.resetctx = true;
            }
        if (ctxdata.context.getPages().size() > 0)
            buildPages(ctxdata.context);
    }
    
    @Override
    public boolean isAuthorizedCall() {
        // TODO Auto-generated method stub
        return false;
    }
    
    private final boolean loginByTicket(
            HttpServletRequest req, Context context) {
        String id = req.getParameter("id");
        boolean connected = context.runtime().login(id);
        if (!connected)
            return connected;
        context.setConnectionTicket(id);
        return connected;
    }
    
    private final void move(ContextData<T> ctxdata, ViewExport inputview) {
        ToolData tooldata;
        FileItem fileitem;
        String filename;
        AbstractPage page = ctxdata.context.getPage();
        
        page.clearToolData();
        for (Object object : inputview.items) {
            page.add(tooldata = (ToolData)object);
            if (!tooldata.multipart)
                continue;
            fileitem = ctxdata.files.get(tooldata.name);
            if (fileitem == null)
                continue;
            filename = fileitem.getName();
            if (filename.equals("")) {
                tooldata.value = null;
                tooldata.values.remove("content");
                tooldata.values.remove("content-type");
                tooldata.values.remove("error", MultipartElement.EMPTY_FILE_NAME);
                continue;
            }
            if (tooldata.disabled)
                continue;
            tooldata.value = filename;
            tooldata.values.put("content", fileitem.get());
            tooldata.values.put("content-type", fileitem.getContentType());
            tooldata.values.put("error", 0);
        }
        page.outputview = inputview;
    }
    
    private final void prepareMessages(
            AbstractPage page, MessageSource messagesrc) {
        int i;
        Properties messages = null;
        String locale = page.outputview.locale.toString();
        
        if (messagesrc != null) {
            messagesrc.entries();
            messages = messagesrc.getMessages(locale);
        }

        for (String key : page.getChildren()) {
            messagesrc = page.getChild(key).getMessages();
            if (messagesrc == null)
                continue;
            messagesrc.entries();
            if (messages == null)
                messages = messagesrc.getMessages(locale);
            else
                messages.putAll(messagesrc.getMessages(locale));
        }
        
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

    private final Map<String, String[]> processMultipartContent(
            ContextData<T> ctxdata) throws Exception {
        String[] values;
        String value;
        FileItem fileitem;
        Map<String, String[]> parameters;
        
        parameters = new HashMap<>();
        for (String fkey : ctxdata.files.keySet()) {
            fileitem = ctxdata.files.get(fkey);
            if (!fileitem.isFormField()) {
                parameters.put(fkey, new String[] {fileitem.getName()});
                continue;
            }
            
            value = fileitem.getString("UTF-8");
            values = parameters.get(fkey);
            if ((values == null) || (values != null && value.length() > 0))
                parameters.put(fkey, new String[] {value});
        }
        
        return parameters;
    }
    
    @SuppressWarnings("unchecked")
    private final void reloadContext(ContextData<T> ctxdata) throws Exception {
        ViewExport outputview;
        ServletFileUpload fileupload;
        Map<String, String[]> parameters;

        ctxdata.files.clear();
        if (ServletFileUpload.isMultipartContent(ctxdata.req)) {
            fileupload = new ServletFileUpload(new DiskFileItemFactory());
            for (FileItem fileitem : (List<FileItem>)fileupload.parseRequest(ctxdata.req))
                ctxdata.files.put(fileitem.getFieldName(), fileitem);
            parameters = processMultipartContent(ctxdata);
        } else {
            parameters = ctxdata.req.getParameterMap();
        }
        
        outputview = getView(ctxdata.servicedata, ctxdata.context);
        outputview.action = null;
        outputview.reqparameters = Tools.toArray(parameters,
                ctxdata.context.isConnectionByTicket()? "id" : null);
        outputview = ctxdata.runtime.processInput(outputview);
        move(ctxdata, outputview);
        if (outputview.action != null)
            run(ctxdata.context, outputview.action);
    }
    
    @Override
    public Object run(Message message) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
    private void run(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        String ctxticketid, reqticketid;
        ViewExport outputview;
        byte[] content;
        ContextData<T> ctxdata = new ContextData<T>();
    
        req.setCharacterEncoding("UTF-8");
        setServerName(req);
        ctxdata.servicedata = new ServiceInterfaceData();
        ctxdata.servicedata.servername = getServerName();
        ctxdata.servicedata.sessionid = req.getSession().getId();
        ctxdata.servicedata.appname = getServletName();
        ctxdata.servicedata.path = req.getRequestURI();
        ctxdata.runtime = new RuntimeEngine(ctxdata.servicedata);
        ctxdata.req = req;
        try {
            if (ctxdata.servicedata.sessionid == null)
                throw new IocasteException("invalid session.");
            
            ctxdata.context = ctxentries.get(ctxdata.servicedata.sessionid);
            if (ctxdata.context == null)
                initContext(ctxdata);
            else
                if (ctxdata.context.isConnectionByTicket()) {
                    ctxticketid = ctxdata.context.getConnectionTicket();
                    reqticketid = req.getParameter("id");
                    if (!ctxticketid.equals(reqticketid))
                        initContext(ctxdata);
                    else
                        reloadContext(ctxdata);
                } else {
                    reloadContext(ctxdata);
                }
            
            outputview = getView(ctxdata.servicedata, ctxdata.context);
            content = ctxdata.runtime.processOutput(outputview);
            print(resp, content);
            ctxdata.runtime.commit();
            if (ctxdata.resetctx)
                ctxentries.remove(ctxdata.servicedata.sessionid);
        } catch (Exception e) {
    //            outputview = getExceptionView(e);
    //            content = iocaste.processOutput(outputview);
            ctxentries.remove(ctxdata.servicedata.sessionid);
            ctxdata.runtime.rollback();
            throw e;
        }
    }
    
    private final void run(T context, String action) throws Exception {
        ActionHandler handler = context.getHandler(action);
        if (handler == null)
            throw new IocasteException("no handler defined for %s.", action);
        try {
            validate(context);
            handler.run(context);
        } catch (IocasteErrorMessage e) {
            context.runtime().rollback();
        } catch (Exception e) {
            throw e;
        }
    }
    
    private final void validate(T context) throws Exception {
        ToolData tooldata;
        AbstractPage page = context.getPage();
        
        for (Object object : page.outputview.items) {
            tooldata = (ToolData)object;
            validate(context, page, tooldata);
        }
    }
    
    private final void validate(Context context,
            AbstractPage page, ToolData tooldata) throws Exception {
        ValidatorHandler validator;
        Map<String, ValidatorHandler> validators = page.getValidators();
        
        for (String key : tooldata.validators) {
            validator = validators.get(key);
            if (validator == null)
                throw new IocasteException("validator %s undefined.", key);
            validator.run(context, tooldata);
        }
        
        for (String key : tooldata.items.keySet())
            validate(context, page, tooldata.items.get(key));
    }
    
    @Override
    public Service serviceInstance(String path) {
        // unused in AbstractApplication. Compatibility only.
        return null;
    }
    
    @Override
    public void set(AbstractIocasteServlet servlet) {
        // unused in AbstractApplication. Compatibility only.
    }
    
    @Override
    public void setAuthorizedCall(boolean authorized) {
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

class ContextData<T extends Context> {
    public HttpServletRequest req;
    public ServiceInterfaceData servicedata;
    public RuntimeEngine runtime;
    public T context;
    public boolean resetctx;
    public Map<String, FileItem> files;
    
    public ContextData() {
        files = new HashMap<>();
    }
}
