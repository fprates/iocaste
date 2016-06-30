package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
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

    protected final ComplexDocument clone(ComplexDocument document) {
        Map<String, ComplexModelItem> models;
        Map<Object, ExtendedObject> items;
        ExtendedObject source;
        ComplexModel cmodel = document.getModel();
        ComplexDocument clone = documentInstance(cmodel);
        DocumentModel model = cmodel.getHeader();
        ExtendedObject object = new ExtendedObject(model);
        
        /*
         * clone cmodel header object and clear its key field.
         * it's enough to triggers most of reindexing procedures.
         */
        clone.setHeader(object);
        Documents.move(object, document.getHeader());
        for (DocumentModelKey key : model.getKeys()) {
            Documents.clear(object, key.getModelItemName());
            break;
        }
        
        /*
         * clone cmodel items
         */
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            if (models.get(name) == null)
                continue;
            items = document.getItemsMap(name);
            for (Object key : items.keySet()) {
                source = items.get(key);
                Documents.move(clone.instance(name, key), source);
            }
        }
        
        return clone;
    }
    
    protected final void delete(ExtendedObject object) {
        documents.delete(object);
    }
    
    protected final void delete(ComplexDocument document) {
        documents.deleteComplexDocument(document.getModel().getName(),
                document.getNS(), document.getKey());
    }
    
    private DataForm dfget(String name) {
        ComponentEntry entry;
        
        entry = components.entries.get(name);
        if (entry == null)
            throw new RuntimeException(name.concat(
                    " is an invalid dataform."));
        return entry.component.getElement();
    }
    
    protected final DocumentExtractor documentExtractorInstance(String cmodel) {
        return new DocumentExtractor(context, cmodel);
    }

    protected final ComplexDocument documentInstance(String cmodel) {
        return new ComplexDocument(documents.getComplexModel(cmodel));
    }
    
    protected final ComplexDocument documentInstance(ComplexModel cmodel) {
        return new ComplexDocument(cmodel);
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
    
    protected ExtendedObject getdf(String name) {
        return dfget(name).getObject();
    }
    
    protected <T> T getdf(String dataform, String field) {
        return getdfinput(dataform, field).get();
    }
    
    protected final long getdfi(String dataform, String field) {
        return getdfinput(dataform, field).geti();
    }
    
    private InputComponent getdfinput(String dataform, String field) {
        InputComponent input;
        
        input = dfget(dataform).get(field);
        if (input == null)
            throw new RuntimeException(field.concat(" isn't a valid field."));
        
        return input;
    }
    
    private final InputComponent getdfinputkey(String dataform) {
        DataForm df = dfget(dataform);
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
    
    protected final Object getdfns(String dataform) {
        return getdf(dataform).getNS();
    }
    
    protected final String getdfst(String dataform, String field) {
        return getdfinput(dataform, field).getst();
    }
    
    protected final ComplexDocument getDocument(String cmodel, Object id) {
        return getDocument(cmodel, null, id);
    }
    
    protected final ComplexDocument getDocument(
            String cmodel, Object ns, Object id) {
        return documents.getComplexDocument(cmodel, ns, id);
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
    
    protected final Map<Object, ExtendedObject> getRelated(
            ComplexDocument document, String itemsname, String field) {
        return getRelated(document, null, itemsname, field);
    }

    protected final Map<Object, ExtendedObject> getRelated(
            ComplexDocument document, Object ns, String itemsname, String field)
    {
        return Documents.getRelated(documents, document, ns, itemsname, field);
    }
    
    protected final void home() {
        context.function.home(null);
    }
    
    protected final void home(String page) {
        context.function.home(page);
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
        if (extcontext != null)
            extcontext.pageInstance(view);
    }
    
    protected final ExtendedObject instance(String model) {
        return new ExtendedObject(documents.getModel(model));
    }
    
    protected final void message(Const type, String text, Object... args) {
        context.function.message(type, text, args);
    }
    
    protected final int modify(ExtendedObject object) {
        return documents.modify(object);
    }
    
    protected static final ExtendedObject readobjects(
            ExtendedObject[] objects, String op1, Object op2) {
        return Documents.readobjects(objects, op1, op2);
    }

    protected static final ExtendedObject readobjects(
            ExtendedObject[] objects, Map<String, Object> keys) {
        return Documents.readobjects(objects, keys);
    }
    
    protected static final ExtendedObject readobjects(
            Collection<ExtendedObject> objects, String op1, Object op2) {
        return Documents.readobjects(objects, op1, op2);
    }

    protected static final ExtendedObject readobjects(
            Collection<ExtendedObject> objects, Map<String, Object> keys) {
        return Documents.readobjects(objects, keys);
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
        
        if ((rappname == null) && (rpagename != null))
            return;
        
        if (rviewctx == null) {
            context.function.keepView();
            return;
        }
        
        if ((viewspec != null) && rviewctx.isUpdatable())
            viewspec.setInitialized(false);
        else
            context.function.keepView();
    }
    
    protected void refresh() {
        AbstractExtendedContext extcontext;
        Map<String, ComponentEntry> entries;
        ComponentEntry entry;
        
        extcontext = getExtendedContext();
        if (extcontext == null)
            return;
        entries = context.getView().getComponents().entries;
        for (String key : entries.keySet()) {
            entry = entries.get(key);
            switch (entry.data.type) {
            case DATA_FORM:
                extcontext.set(key, getdf(key));
                break;
            case TABLE_TOOL:
                extcontext.set(key, tableitemsget(key));
                break;
            default:
                break;
            }
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
        refresh();
        execute(this.context);
        if (redirectflag)
            redirectContext(this.context, viewctx);
        if (this.context.downloaddata != null)
            context.function.download();
    }
    
    protected final ComplexDocument save(ComplexDocument document) {
        return documents.save(document);
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
    
    protected final void setFocus(String element) {
        context.view.setFocus(context.view.getElement(element));
    }
    
    protected final void setFocus(String container, String field) {
        DataForm dataform = components.getComponent(container).getElement();
        context.view.setFocus(dataform.get(field));
    }
    
    protected final void setFocus(String table, String column, int line) {
        TableItem titem;
        int i = 0;
        TableTool component = components.getComponent(table);
        Set<Element> elements = ((Table)component.getElement()).getElements();
        for (Element element : elements) {
            titem = (TableItem)element;
            if (i == line) {
                context.view.setFocus(titem.get(column));
                return;
            }
            i++;
        }
    }
    
    public static final ExtendedObject[] tableitemsget(TableToolData data) {
        ExtendedObject[] objects;
        int i;
        List<TableToolItem> items = data.getItems();
        
        if (items == null)
            return null;
        
        i = items.size();
        if (i == 0)
            return null;
        
        objects = new ExtendedObject[i];
        i = 0;
        for (TableToolItem item : items)
            objects[i++] = item.object;
        
        return objects;
    }
    
    protected final ExtendedObject[] tableitemsget(String tabletool) {
        TableToolData ttdata = components.getComponentData(tabletool);
        return tableitemsget(ttdata);
    }
    
    protected final List<ExtendedObject> tableselectedget(String tabletool) {
        List<ExtendedObject> objects;
        List<TableToolItem> items = ((TableToolData)components.
                getComponentData(tabletool)).getItems();
        
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
