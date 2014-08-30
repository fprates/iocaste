package org.iocaste.appbuilder.common;

import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.View;
import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;

public abstract class AbstractActionHandler {
    private PageBuilderContext context;
    private ViewComponents components;
    private Documents documents;
    
    protected final void back() {
        ((AbstractPage)context.function).back();
    }
    
    protected final String dbactionget(String dashboard, String item) {
        DashboardFactory factory = components.dashboards.get(dashboard);
        
        if (factory == null)
            throw new RuntimeException(dashboard.concat(
                    " is an invalid dashboard factory."));
        
        return factory.get(item).getValue();
    }
    
    protected final DocumentExtractor documentExtractorInstance(String manager)
    {
        return new DocumentExtractor(context, manager);
    }
    
    protected final void execute(String action) throws Exception {
        String view = context.view.getPageName();
        context.getActionHandler(view, action).run(context);
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
    
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getExtendedContext(context.view.getPageName());
    }

    protected final String getinputst(String name) {
        return ((InputComponent)context.view.getElement(name)).getst();
    }
    
    protected final Manager getManager(String name) {
        return context.getManager(name);
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
    
    protected final void managerMessage(String manager, Const status, int msgid) {
        context.view.message(
                status, context.getManager(manager).getMessage(msgid));
    }
    
    protected final void message(Const type, String text) {
        context.view.message(type, text);
    }
    
    protected final void redirect(String page) {
        context.view.redirect(page);
    }
    
    protected final void redirect(String app, String page) {
        context.view.redirect(app, page);
    }
    
    public final void run(AbstractContext context) throws Exception {
        String appname, rappname, pagename, rpagename;
        AbstractViewSpec viewspec;
        String view = context.view.getPageName();
        String action = context.view.getActionControl();
        
        this.context = (PageBuilderContext)context;
        if (!this.context.hasActionHandler(view, action))
            return;
        
        components = this.context.getViewComponents(view);
        documents = new Documents(context.function);
        context.view.setInitialize(false);
        execute(this.context);
        context.view.setReloadableView(true);
        appname = this.context.view.getAppName();
        rappname = this.context.view.getRedirectedApp();
        if (rappname != null && !appname.equals(rappname))
            rappname = null;
        
        pagename = this.context.view.getPageName();
        rpagename = this.context.view.getRedirectedPage();
        if ((rpagename != null) && (rpagename.equals(pagename)))
            rpagename = null;
        
        if (rpagename != null)
            viewspec = this.context.getViewSpec(rpagename);
        else
            viewspec = null;
        
        if ((rappname == null) && (rpagename != null)) {
            if ((viewspec != null) && viewspec.isInitialized())
                inputrefresh();
        } else {
            if (!this.context.isViewUpdatable(view))
                context.function.keepView();
        }
    }
    
    protected final ExtendedObject[] select(Query query) {
        return documents.select(query);
    }
    
    protected final ExtendedObject[] tableitemsget(String tabletool) {
        return components.tabletools.get(tabletool).component.getObjects();
    }
    
    protected final List<ExtendedObject> tableselectedget(String tabletool) {
        return components.tabletools.get(tabletool).component.getSelected();
    }
    
    protected final void taskredirect(String task) {
        View view;
        GenericService service = new GenericService(
                context.function, "/iocaste-tasksel/view.html");
        Message message = new Message("task_redirect");
        
        message.add("task", task);
        view = service.invoke(message);
        if (view == null)
            throw new RuntimeException(task.concat(" is an invalid task."));
        
        for (String name : view.getExportable())
            context.view.setParameter(name, view.getParameter(name));
        
        context.view.redirect(view.getRedirectedApp(),
                view.getRedirectedPage(), View.INITIALIZE);
    }
    
    protected final void texteditorsave(String name, String id) {
        TextEditorTool editortool = new TextEditorTool(context);
        TextEditor editor = components.editors.get(name);
        
        editortool.commit(editor, id);
        editortool.update(editor, "B2B_OBSERV");
    }
    
    protected final String textget(String name) {
        TextArea text = context.view.getElement(name);
        return text.getst();
    }
    
    protected final Map<String, String> textget(String name, String id) {
        return new TextEditorTool(context).get(name, id);
    }
}
