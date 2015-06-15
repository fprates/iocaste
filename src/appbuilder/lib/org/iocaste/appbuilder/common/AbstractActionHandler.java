package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.ViewState;
import org.iocaste.tasksel.common.TaskSelector;
import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;

public abstract class AbstractActionHandler {
    public static final boolean REDIRECT = true;
    private PageBuilderContext context;
    private ViewComponents components;
    private Documents documents;
    
    protected final void back() {
        context.function.back();
    }

    protected final int dbactiongeti(String dashboard, String item) {
        return getDashboardFactory(dashboard).get(item).geti();
    }
    
    protected final long dbactiongetl(String dashboard, String item) {
        return getDashboardFactory(dashboard).get(item).getl();
    }
    
    protected final String dbactiongetst(String dashboard, String item) {
        return getDashboardFactory(dashboard).get(item).getst();
    }
    
    protected final DocumentExtractor documentExtractorInstance(String manager)
    {
        return new DocumentExtractor(context, manager);
    }
    
    protected final void execute(String action) throws Exception {
        String view = context.view.getPageName();
        context.getView(view).getActionHandler(action).run(context, !REDIRECT);
    }
    
    protected abstract void execute(PageBuilderContext context)
            throws Exception;
    
    protected final byte[] filecontentget(String fileentry) {
        return fileentryget(fileentry).getContent();
    }
    
    private final FileEntry fileentryget(String fileentry) {
        FileEntry fentry = context.view.getElement(fileentry);
        if (fentry != null)
            return fentry;
        throw new RuntimeException(fileentry.concat(" is an invalid element."));
    }
    
    protected final int fileerrorget(String fileentry) {
        return fileentryget(fileentry).getError();
    }
    
    protected final String filenameget(String fileentry) {
        return fileentryget(fileentry).get();
    }

    private DashboardFactory getDashboardFactory(String dashboard) {
        DashboardFactory factory = components.dashboards.get(dashboard);
        
        if (factory != null)
            return factory;
        
        factory = components.dashboardgroups.get(dashboard).getFactory();
        if (factory == null)
            throw new RuntimeException(dashboard.concat(
                    " is an invalid dashboard factory."));
        
        return factory;
    }
    
    protected ExtendedObject getdf(String name) {
        DataForm dataform = context.view.getElement(name);
        return dataform.getObject();
    }
    
    protected <T> T getdf(String dataform, String field) {
        return getdfinput(dataform, field).get();
    }
    
    protected final long getdfi(String dataform, String field) {
        return getdfinput(dataform, field).geti();
    }
    
    private InputComponent getdfinput(String dataform, String field) {
        InputComponent input;
        DataForm form = (DataForm)context.view.getElement(dataform);
        
        if (form == null)
            throw new RuntimeException(dataform.concat(
                    " isn't a valid dataform."));
        
        input = form.get(field);        
        if (input == null)
            throw new RuntimeException(field.concat(" isn't a valid field."));
        
        return input;
    }
    
    private final InputComponent getdfinputkey(String dataform) {
        DataForm df = context.view.getElement(dataform);
        for (DocumentModelKey key : df.getModel().getKeys())
            return getdfinput(dataform, key.getModelItemName());

        throw new RuntimeException(dataform.concat(" isn't a valid key."));
    }
    
    protected final Object getdfkey(String dataform) {
        return getdfinputkey(dataform).get();
    }
    
    protected final int getdfkeyi(String dataform) {
        return getdfinputkey(dataform).geti();
    }
    
    protected final long getdfkeyl(String dataform) {
        return getdfinputkey(dataform).getl();
    }
    
    protected final String getdfkeyst(String dataform) {
        return getdfinputkey(dataform).getst();
    }
    
    protected final long getdfl(String dataform, String field) {
        return getdfinput(dataform, field).getl();
    }
    
    protected final String getdfst(String dataform, String field) {
        return getdfinput(dataform, field).getst();
    }
    
    protected final ComplexDocument getDocument(String manager, Object id) {
        return context.getManager(manager).get(id);
    }
    
    protected Const getErrorType() {
        return context.function.getMessageType();
    }
    
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        String page = context.view.getPageName();
        return (T)context.getView(page).getExtendedContext();
    }

    protected final double getinputd(String name) {
        return ((InputComponent)context.view.getElement(name)).getd();
    }

    protected final int getinputi(String name) {
        return ((InputComponent)context.view.getElement(name)).geti();
    }

    protected final long getinputl(String name) {
        return ((InputComponent)context.view.getElement(name)).getl();
    }
    
    protected final String getinputst(String name) {
        return ((InputComponent)context.view.getElement(name)).getst();
    }
    
    protected final Manager getManager(String name) {
        return context.getManager(name);
    }
    
    protected final long getNextNumber(String range) {
        return documents.getNextNumber(range);
    }
    
    protected final ExtendedObject getObject(String model, Object key) {
        return getObject(model, null, key);
    }
    
    protected final ExtendedObject getObject(
            String model, Object ns, Object key) {
        return documents.getObject(model, ns, key);
        
    }
    
    protected final void init(String view, ExtendedContext extcontext) {
        AbstractViewSpec spec;
        
        ViewContext viewctx = context.getView(view);
        viewctx.set(extcontext);
        spec = viewctx.getSpec();
        if (spec == null)
            throw new RuntimeException(new StringBuilder("undefined spec for ").
                    append(view).toString());
        spec.setInitialized(false);
    }
    
    protected final void inputrefresh() {
        context.setInputUpdate(true);
    }
    
    protected final ExtendedObject instance(String model) {
        return new ExtendedObject(documents.getModel(model));
    }
    
    protected final boolean keyExists(String manager, Object id) {
        return context.getManager(manager).exists(id);
    }
    
    protected final void managerMessage(String manager, Const status, int msgid,
            Object... args) {
        context.function.message(
                status, context.getManager(manager).getMessage(msgid), args);
    }
    
    protected final void message(Const type, String text, Object... args) {
        context.function.message(type, text, args);
    }
    
    protected final int modify(ExtendedObject object) {
        return documents.modify(object);
    }
    
    protected static final ExtendedObject readobject(ExtendedObject object,
            String op1, Object op2) {
        int type;
        Object value1 = object.get(op1);
        Object value2 = op2;
        
        type = object.getModel().getModelItem(op1).getDataElement().getType();
        switch (type) {
        case DataType.INT:
            value1 = ExtendedObject.converti(value1);
            value2 = ExtendedObject.converti(value2);
            break;
        case DataType.NUMC:
        case DataType.LONG:
            value1 = ExtendedObject.convertl(value1);
            value2 = ExtendedObject.convertl(value2);
            break;
        }
        
        return (value1.equals(value2))? object : null;
    }
    
    protected static final ExtendedObject readobjects(ExtendedObject[] objects,
            String op1, Object op2) {
        for (ExtendedObject object : objects) {
            object = readobject(object, op1, op2);
            if (object == null)
                continue;
            return object;
        }
        
        return null;
    }
    
    protected static final ExtendedObject readobjects(
            Collection<ExtendedObject> objects, String op1, String op2) {
        for (ExtendedObject object : objects)
            if (object.get(op1).equals(op2))
                return object;
        
        return null;
    }
    
    protected final void redirect(String page) {
        context.function.redirect(page);
    }
    
    protected final void redirect(String app, String page) {
        context.function.exec(app, page);
    }
    
    public static final void redirectContext(
            PageBuilderContext context, ViewContext viewctx) {
        String appname, rappname, pagename, rpagename;
        AbstractViewSpec viewspec;
        ViewContext rviewctx;
        
        context.function.setReloadableView(true);
        appname = context.view.getAppName();
        rappname = context.function.getRedirectedApp();
        if (rappname != null && !appname.equals(rappname)) {
            context.function.keepView();
            return;
        }
        
        pagename = context.view.getPageName();
        rpagename = context.function.getRedirectedPage();
        if ((rpagename != null) && (rpagename.equals(pagename)))
            rpagename = null;
        
        if (rpagename != null) {
            rviewctx = context.getView(rpagename);
            viewspec = (rviewctx == null)? null : rviewctx.getSpec();
        } else {
            rviewctx = null;
            viewspec = null;
        }
        
        if ((rappname == null) && (rpagename != null)) {
            if ((viewspec != null) && viewspec.isInitialized())
                context.setInputUpdate(true);
        } else {
            if (rviewctx == null) {
                context.function.keepView();
                return;
            }
            
            if ((viewspec != null) && rviewctx.isUpdatable())
                viewspec.setInitialized(false);
            else
                context.function.keepView();
        }
    }
    
    public final void run(AbstractContext context) throws Exception {
        run(context, REDIRECT);
    }
    
    public final void run(AbstractContext context, boolean redirectflag)
            throws Exception {
        ViewContext viewctx;
        String view = context.view.getPageName();
        
        this.context = (PageBuilderContext)context;
        viewctx = this.context.getView(view);
        if (viewctx.getActionHandler(context.action) == null)
            return;
        
        components = viewctx.getComponents();
        documents = new Documents(context.function);
        execute(this.context);
        if (redirectflag)
            redirectContext(this.context, viewctx);
        if (this.context.downloaddata != null)
            context.function.download();
    }
    
    protected final ComplexDocument save(
            String managername, ComplexDocument document) {
        return context.getManager(managername).save(document);
    }
    
    protected final void save(ExtendedObject object) {
        documents.save(object);
    }
    
    protected final ExtendedObject[] select(Query query) {
        return documents.select(query);
    }
    
    protected final ExtendedObject select(String model, Object key) {
        return select(model, null, key);
    }
    
    protected final ExtendedObject select(String model, Object ns, Object key) {
        return documents.getObject(model, ns, key);
    }
    
    protected final ExtendedObject[] tableitemsget(String tabletool) {
        ExtendedObject[] objects;
        int i = 0;
        TableToolEntry entry = components.tabletools.get(tabletool);
        List<TableToolItem> items = entry.data.getItems();
        
        if (items == null)
            return null;
        
        objects = new ExtendedObject[items.size()];
        for (TableToolItem item : items)
            objects[i++] = item.object;
        
        return objects;
    }
    
    protected final List<ExtendedObject> tableselectedget(String tabletool) {
        List<ExtendedObject> objects;
        TableToolEntry entry = components.tabletools.get(tabletool);
        List<TableToolItem> items = entry.data.getItems();
        
        if (items == null)
            return null;
        
        objects = new ArrayList<>();
        for (TableToolItem item : items)
            if (item.selected)
                objects.add(item.object);
        
        return objects;
    }
    
    protected final void taskredirect(String task) {
        ViewState state;
        
        state = new TaskSelector(context.function).call(task);
        switch (state.error) {
        case ViewState.OK:
            for (String name : state.parameters.keySet())
                context.function.export(name, state.parameters.get(name));
            
            context.function.exec(state.rapp, state.rpage);
            break;
        case ViewState.NOT_AUTHORIZED:
            message(Const.ERROR, "user.not.authorized");
            break;
        default:
            throw new RuntimeException(task.concat(" is an invalid task."));
        }
    }
    
    protected final void textcreate(String name) {
        new TextEditorTool(context).create(name);
    }
    
    protected final void texteditorsave(String name, String id, String page) {
        TextEditorTool editortool = new TextEditorTool(context);
        TextEditor editor = components.editors.get(name);
        
        editortool.commit(editor, page);
        editortool.update(editor, id);
    }
    
    protected final String textget(String name) {
        TextArea text = context.view.getElement(name);
        return text.getst();
    }
    
    protected final Map<String, String> textget(String name, String id) {
        return new TextEditorTool(context).get(name, id);
    }
    
    protected final void textsave(String id, String page, String text) {
        TextEditorTool editortool = new TextEditorTool(context);
        
        editortool.update(id, page, text);
    }
    protected final int update(Query query) {
        return documents.update(query);
    }
}
