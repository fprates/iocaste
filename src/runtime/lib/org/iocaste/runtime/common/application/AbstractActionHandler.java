package org.iocaste.runtime.common.application;

import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.IocasteErrorMessage;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.ViewSpec;
import org.iocaste.shell.common.Const;

public abstract class AbstractActionHandler<C extends Context>
	    implements ActionHandler {
    public static final boolean REDIRECT = true;
    private Context context;
//    private Documents documents;
//    private String page;
//    
//    protected final void back() {
//        context.function.back();
//    }
//
//    protected final ComplexDocument clone(ComplexDocument document) {
//        Map<String, ComplexModelItem> models;
//        Map<Object, ExtendedObject> items;
//        ExtendedObject source;
//        ComplexModel cmodel = document.getModel();
//        ComplexDocument clone = documentInstance(cmodel);
//        DocumentModel model = cmodel.getHeader();
//        ExtendedObject object = new ExtendedObject(model);
//        
//        /*
//         * clone cmodel header object and clear its key field.
//         * it's enough to triggers most of reindexing procedures.
//         */
//        clone.setHeader(object);
//        Documents.move(object, document.getHeader());
//        for (DocumentModelKey key : model.getKeys()) {
//            Documents.clear(object, key.getModelItemName());
//            break;
//        }
//        
//        /*
//         * clone cmodel items
//         */
//        models = cmodel.getItems();
//        for (String name : models.keySet()) {
//            if (models.get(name) == null)
//                continue;
//            items = document.getItemsMap(name);
//            for (Object key : items.keySet()) {
//                source = items.get(key);
//                Documents.move(clone.instance(name, key), source);
//            }
//        }
//        
//        return clone;
//    }
//    
//    protected final void delete(ExtendedObject object) {
//        documents.delete(object);
//    }
//    
//    protected final void delete(ComplexDocument document) {
//        documents.deleteComplexDocument(document.getModel().getName(),
//                document.getNS(), document.getKey());
//    }
    
    private ToolData gettool(String name) {
    	ToolData tooldata = context.getPage().getToolData(name);
        if (tooldata == null)
            throw new IocasteException("% is an invalid tooldata.", name);
        return tooldata;
    }
    
//    protected final DocumentExtractor documentExtractorInstance(String cmodel) {
//        return new DocumentExtractor(context, cmodel);
//    }
//
//    protected final ComplexDocument documentInstance(String cmodel) {
//        return new ComplexDocument(documents.getComplexModel(cmodel));
//    }
//    
//    protected final ComplexDocument documentInstance(ComplexModel cmodel) {
//        return new ComplexDocument(cmodel);
//    }
    
    protected final void execute(String action) throws Exception {
        context.getHandler(action).run(context, !REDIRECT);
    }
    
    protected abstract void execute(C context) throws Exception;
    
//    protected final byte[] filecontentget(String fileentry) {
//        return fileentryget(fileentry).getContent();
//    }
//    
//    private final FileEntry fileentryget(String fileentry) {
//        FileEntry fentry = context.view.getElement(fileentry);
//        if (fentry != null)
//            return fentry;
//        throw new RuntimeException(fileentry.concat(" is an invalid element."));
//    }
//    
//    protected final int fileerrorget(String fileentry) {
//        return fileentryget(fileentry).getError();
//    }
//    
//    protected final String filenameget(String fileentry) {
//        return fileentryget(fileentry).get();
//    }
//    
//    private final ViewComponents getComponents() {
//        return context.getView(page).getComponents();
//    }
    
	protected <T> T get(String tooldata, String field) {
    	return gettool(tooldata).object.get(field);
    }
    
    protected final byte getb(String tooldata, String field) {
        return gettool(tooldata).object.getb(field);
    }
    
    protected final boolean getbl(String tooldata) {
    	return (boolean)gettool(tooldata).value;
    }
    
    protected final double getd(String tooldata) {
    	return (double)gettool(tooldata).value;
    }
    
    protected final ExtendedObject getextobj(String model, Object key) {
        return getextobj(model, null, key);
    }
    
    protected final ExtendedObject getextobj(
            String model, Object ns, Object key) {
        return context.runtime().getObject(model, ns, key);
    }

    protected final int geti(String tooldata) {
    	return (int)gettool(tooldata).value;
    }
    
    protected final int geti(String tooldata, String field) {
        return gettool(tooldata).object.geti(field);
    }
    
    protected final Object getkey(String tooldata) {
        return gettoolkey(tooldata).get();
    }
    
    protected final int getkeyi(String tooldata) {
        return (int)getkey(tooldata);
    }
    
    protected final long getkeyl(String tooldata) {
        return (long)getkey(tooldata);
    }
    
    protected final String getkeyst(String tooldata) {
        return (String)getkey(tooldata);
    }

    protected final long getl(String tooldata) {
    	return (long)gettool(tooldata).value;
    }
    
    protected final long getl(String tooldata, String field) {
        return gettool(tooldata).object.getl(field);
    }
    
    protected final Object getns(String tooldata) {
        return gettool(tooldata).object.getNS();
    }
    
    protected ExtendedObject getobject(String tooldata) {
        return gettool(tooldata).object;
    }
    
    protected final String getst(String tooldata) {
        return (String)gettool(tooldata).value;
    }
    
    protected final String getst(String tooldata, String field) {
        return gettool(tooldata).object.get(field);
    }
    
//    protected final ComplexDocument getDocument(String cmodel, Object id) {
//        return getDocument(cmodel, null, id);
//    }
//    
//    protected final ComplexDocument getDocument(
//            String cmodel, Object ns, Object id) {
//        return documents.getComplexDocument(cmodel, ns, id);
//    }
//    
//    protected Const getErrorType() {
//        return context.function.getMessageType();
//    }
//
//    protected final byte[] getFileContent(String name) {
//        return ((MultipartElement)context.view.getElement(name)).getContent();
//    }
    
    private final ToolData gettoolkey(String name) {
        ToolData tooldata = gettool(name);
        for (DocumentModelKey key : tooldata.custommodel.getKeys())
            return tooldata.get(key.getModelItemName());

        throw new IocasteException(name, " isn't a valid key.");
    }
    
//    protected final long getNextNumber(String range) {
//        return documents.getNextNumber(range);
//    }
//    
//    protected final long getNextNumber(String range, Object ns) {
//        return documents.getNSNextNumber(range, ns);
//    }
//    
//    protected final Map<Object, ExtendedObject> getRelated(
//            ComplexDocument document, String itemsname, String field) {
//        return getRelated(document, null, itemsname, field);
//    }
//
//    protected final Map<Object, ExtendedObject> getRelated(
//            ComplexDocument document, Object ns, String itemsname, String field)
//    {
//        return Documents.getRelated(documents, document, ns, itemsname, field);
//    }
//    
//    protected final void home() {
//        context.function.home(null);
//    }
//    
//    protected final void home(String page) {
//        context.function.home(page);
//    }
    
    protected final void init(String view) {
        AbstractPage page = context.getPage(view);
        ViewSpec spec = page.getSpec();
        
        if (spec == null)
            throw new IocasteException("undefined spec for ", view);
        spec.setInitialized(false);
    }
    
    protected final ExtendedObject instance(String model) {
        return new ExtendedObject(context.runtime().getModel(model));
    }
    
    protected final void message(Const type, String text, Object... args) {
        AbstractPage page = context.getPage();
        page.outputview.msgtype = type;
        page.outputview.message = text;
        page.outputview.msgargs = args;
        if (type == Const.ERROR)
        	throw new IocasteErrorMessage();
    }
    
//    protected final int modify(ExtendedObject object) {
//        return documents.modify(object);
//    }
//    
//    protected static final ExtendedObject readobjects(
//            ExtendedObject[] objects, String op1, Object op2) {
//        return Documents.readobjects(objects, op1, op2);
//    }
//
//    protected static final ExtendedObject readobjects(
//            ExtendedObject[] objects, Map<String, Object> keys) {
//        return Documents.readobjects(objects, keys);
//    }
//    
//    protected static final ExtendedObject readobjects(
//            Collection<ExtendedObject> objects, String op1, Object op2) {
//        return Documents.readobjects(objects, op1, op2);
//    }
//
//    protected static final ExtendedObject readobjects(
//            Collection<ExtendedObject> objects, Map<String, Object> keys) {
//        return Documents.readobjects(objects, keys);
//    }
    
    protected final void redirect(String page) {
        context.setPageName(page);
    }
    
//    protected final void redirect(String app, String page) {
//        context.function.exec(app, page);
//    }
//    
//    public static final void redirectContext(
//            PageBuilderContext context, ViewContext viewctx) {
//        String appname, rappname, pagename, rpagename;
//        ViewSpec viewspec;
//        ViewContext rviewctx;
//        
//        context.function.setReloadableView(true);
//        appname = context.view.getAppName();
//        rappname = context.function.getRedirectedApp();
//        if (rappname != null && !appname.equals(rappname)) {
//            context.function.keepView();
//            return;
//        }
//        
//        pagename = context.view.getPageName();
//        rpagename = context.function.getRedirectedPage();
//        if ((rpagename != null) && (rpagename.equals(pagename)))
//            rpagename = null;
//        
//        if (rpagename != null) {
//            rviewctx = context.getView(rpagename);
//            viewspec = (rviewctx == null)? null : rviewctx.getSpec();
//        } else {
//            rviewctx = null;
//            viewspec = null;
//        }
//        
//        if ((rappname == null) && (rpagename != null))
//            return;
//        
//        if (rviewctx == null) {
//            context.function.keepView();
//            return;
//        }
//        
//        if ((viewspec != null) && rviewctx.isUpdatable())
//            viewspec.setInitialized(false);
//        else
//            context.function.keepView();
//    }
//
    @Override
    public final void run(Context context) throws Exception {
        run(context, REDIRECT);
    }
    
    @Override
    public final void run(Context context, boolean redirectflag)
            throws Exception {
        run(context, context.getPageName(), redirectflag);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public final void run(Context context, String page, boolean redirectflag)
    		throws Exception {
//        ViewContext viewctx;
//        
        this.context = context;
//        this.page = page;
//        viewctx = this.context.getView(page);
//        if (viewctx.getActionHandler(context.action) == null)
//            return;
        execute((C)context);
//        if (redirectflag)
//            redirectContext(this.context, viewctx);
//        if (this.context.downloaddata != null)
//            context.function.download();
    }
//    
//    protected final void save(ComplexDocument document) {
//        Map<Object, ComplexDocument> documents;
//        Map<Object, ExtendedObject> objects;
//        ComplexDocument newdoc = this.documents.save(document);
//        
//        document.remove();
//        for (String name : newdoc.getModel().getItems().keySet()) {
//            documents = newdoc.get(name).documents;
//            if (documents != null) {
//                for (Object key : documents.keySet())
//                    document.add(documents.get(key));
//            } else {
//                objects = newdoc.get(name).objects;
//                for (Object key : objects.keySet())
//                    document.add(objects.get(key));
//            }
//        }
//    }
//    
//    protected final void save(ExtendedObject object) {
//        documents.save(object);
//    }
//    
//    protected final ExtendedObject[] select(Query query) {
//        return documents.select(query);
//    }
//    
//    protected final ExtendedObject select(String model, Object key) {
//        return select(model, null, key);
//    }
//    
//    protected final ExtendedObject select(String model, Object ns, Object key) {
//        return documents.getObject(model, ns, key);
//    }
//    
//    protected final void setFocus(String element) {
//        context.view.setFocus(context.view.getElement(element));
//    }
//    
//    protected final void setFocus(String container, String field) {
//        DataForm dataform=getComponents().getComponent(container).getElement();
//        context.view.setFocus(dataform.get(field));
//    }
//    
//    protected final void setFocus(String table, String column, int line) {
//        TableItem titem;
//        int i = 0;
//        TableTool component = getComponents().getComponent(table);
//        Set<Element> elements = ((Table)component.getElement()).getElements();
//        for (Element element : elements) {
//            titem = (TableItem)element;
//            if (i == line) {
//                context.view.setFocus(titem.get(column));
//                return;
//            }
//            i++;
//        }
//    }
//    
//    public static final ExtendedObject[] tableitemsget(TableToolData data) {
//        ExtendedObject[] objects;
//        int i;
//        Map<Integer, TableToolItem> items = data.getItems();
//        
//        if (items == null)
//            return null;
//        
//        i = items.size();
//        if (i == 0)
//            return null;
//        
//        objects = new ExtendedObject[i];
//        i = 0;
//        for (TableToolItem item : items.values())
//            objects[i++] = item.object;
//        
//        return objects;
//    }
//    
//    protected final ExtendedObject[] tableitemsget(String tabletool) {
//        TableToolData ttdata = getComponents().getComponentData(tabletool);
//        return tableitemsget(ttdata);
//    }
//    
//    protected final List<ExtendedObject> tableselectedget(String tabletool) {
//        List<ExtendedObject> objects;
//        TableToolItem item;
//        Map<Integer, TableToolItem> items = ((TableToolData)getComponents().
//                getComponentData(tabletool)).getItems();
//        
//        if (items == null)
//            return null;
//        
//        objects = new ArrayList<>();
//        for (int i : items.keySet()) {
//            item = items.get(i);
//            if (item.selected)
//                objects.add(item.object);
//        }
//        return objects;
//    }
//    
//    protected final void taskredirect(String task) {
//        ViewState state;
//        
//        state = new TaskSelector(context.function).call(task);
//        switch (state.error) {
//        case ViewState.OK:
//            for (String name : state.parameters.keySet())
//                context.function.export(name, state.parameters.get(name));
//            
//            context.function.exec(state.rapp, state.rpage);
//            break;
//        case ViewState.NOT_AUTHORIZED:
//            message(Const.ERROR, "user.not.authorized");
//            break;
//        default:
//            throw new RuntimeException(task.concat(" is an invalid task."));
//        }
//    }
//    
//    protected final void textcreate(String name) {
//        new TextEditorTool(context).create(name);
//    }
//    
//    protected final void texteditorsave(String name, String id, String page) {
//        TextEditorTool editortool = new TextEditorTool(context);
//        TextEditor editor = getComponents().editors.get(name);
//        
//        editortool.commit(editor, page);
//        editortool.update(editor, id);
//    }
//    
//    protected final String textget(String name) {
//        TextArea text = context.view.getElement(name);
//        return text.getst();
//    }
//    
//    protected final Map<String, String> textget(String name, String id) {
//        return new TextEditorTool(context).get(name, id);
//    }
//    
//    protected final void textremove(String id, String page) {
//        TextEditorTool editortool = new TextEditorTool(context);
//        editortool.remove(id, page);
//    }
//    
//    protected final void textsave(String id, String page, String text) {
//        TextEditorTool editortool = new TextEditorTool(context);
//        editortool.update(id, page, text);
//    }
//    
//    protected final int update(Query query) {
//        return documents.update(query);
//    }
}