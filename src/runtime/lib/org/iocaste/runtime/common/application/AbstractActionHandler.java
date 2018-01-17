package org.iocaste.runtime.common.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;
import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.IocasteErrorMessage;
import org.iocaste.runtime.common.RuntimeEngine;
import org.iocaste.runtime.common.managedview.DocumentExtractor;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.tooldata.MetaObject;
import org.iocaste.shell.common.tooldata.ToolData;

public abstract class AbstractActionHandler<C extends Context>
	    implements ActionHandler {
    public static final boolean REDIRECT = true;
    private Context context;
    
    protected final void back() {
        context.popPage();
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
        context.runtime().delete(object);
    }
    
    protected final void delete(ComplexDocument document) {
        context.runtime().deleteComplexDocument(document.getModel().getName(),
                document.getNS(), document.getKey());
    }
    
    protected final DocumentExtractor documentExtractorInstance(String cmodel) {
        return new DocumentExtractor(context, cmodel);
    }

    protected final ComplexDocument documentInstance(String cmodel) {
        return documentInstance(context.runtime().getComplexModel(cmodel));
    }
    
    protected final ComplexDocument documentInstance(ComplexModel cmodel) {
        return new ComplexDocument(cmodel);
    }
    
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
    
	protected <T> T get(String tooldata, String field) {
    	return gettool(tooldata).object.get(field);
    }
    
    protected final byte getb(String tooldata, String field) {
        return gettool(tooldata).object.getb(field);
    }
    
    protected final boolean getbl(String tooldata) {
    	return (boolean)gettool(tooldata).value;
    }
    
    protected final ComplexDocument getcdocument(String cmodel, Object id) {
        return getcdocument(cmodel, null, id);
    }
    
    protected final ComplexDocument getcdocument(
            String cmodel, Object ns, Object id) {
        return context.runtime().getComplexDocument(cmodel, ns, id);
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
        ToolData hdtool = gettool(tooldata);
        return hdtool.object.get(gettoolkey(tooldata).name);
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

    protected final long getNextNumber(String range) {
        return context.runtime().getNextNumber(range);
    }
    
    protected final long getNextNumber(String range, Object ns) {
        return context.runtime().getNSNextNumber(range, ns);
    }
    
    protected final Object getns(String tooldata) {
        return gettool(tooldata).object.getNS();
    }
    
    protected ExtendedObject getobject(String tooldata) {
        return gettool(tooldata).object;
    }
    
    protected ExtendedObject[] getobjects(String tooldata) {
        List<ExtendedObject> objects;
        Map<Integer, MetaObject> items;
        MetaObject mobject;
        
        if ((items = gettool(tooldata).objects).size() == 0)
            return null;
        objects = new ArrayList<>();
        for (int key : items.keySet())
            if ((mobject = items.get(key)) != null)
                objects.add(mobject.object);
        return objects.toArray(new ExtendedObject[0]);
    }
    
    protected ExtendedObject[] getselected(String tooldata) {
        List<ExtendedObject> objects;
        Map<Integer, MetaObject> items;
        MetaObject mobject;
        
        if ((items = gettool(tooldata).objects).size() == 0)
            return null;
        objects = new ArrayList<>();
        for (int key : items.keySet())
            if (((mobject = items.get(key)) != null) && mobject.selected)
                objects.add(mobject.object);
        return objects.toArray(new ExtendedObject[0]);
    }
    
    protected final String getst(String tooldata) {
        return (String)gettool(tooldata).value;
    }
    
    protected final String getst(String tooldata, String field) {
        return gettool(tooldata).object.get(field);
    }
//
//    protected final byte[] getFileContent(String name) {
//        return ((MultipartElement)context.view.getElement(name)).getContent();
//    }
    
    private ToolData gettool(String name) {
        ToolData tooldata = context.getPage().instance(name);
        if (tooldata == null)
            throw new IocasteException("% is an invalid tooldata.", name);
        return tooldata;
    }
    
    private final ToolData gettoolkey(String name) {
        String keyname;
        ToolData tooldata = gettool(name);
        for (DocumentModelKey key : tooldata.custommodel.getKeys())
            return (!tooldata.contains(keyname = key.getModelItemName()))?
                    null : tooldata.instance(keyname);

        throw new IocasteException("%s isn't a valid key.", name);
    }
    
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
    
    protected final void home() {
        context.popPage(null);
    }
    
    protected final void home(String page) {
        context.popPage(page);
    }
    
    protected final void init(String view) {
        AbstractPage page = context.getPage(view);
        
        if (page == null)
            throw new IocasteException("page %s undefined.", view);
        page.setReady(false);
    }
    
    protected final ExtendedObject instance(String model) {
        return new ExtendedObject(context.runtime().getModel(model));
    }

    @Override
    public boolean isValidator() {
        return false;
    }
    
    protected final void message(Const type, String text, Object... args) {
        AbstractPage page = context.getPage();
        page.outputview.msgtype = type;
        page.outputview.message = text;
        page.outputview.msgargs = args;
        if (type == Const.ERROR)
        	throw new IocasteErrorMessage();
    }
    
    protected final int modify(ExtendedObject object) {
        return context.runtime().modify(object);
    }
    
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
        context.pushPage();
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
    
    protected final void save(ComplexDocument document) {
        Map<Object, ComplexDocument> documents;
        Map<Object, ExtendedObject> objects;
        ComplexDocument newdoc = context.runtime().save(document);
        
        document.remove();
        for (String name : newdoc.getModel().getItems().keySet()) {
            documents = newdoc.get(name).documents;
            if (documents != null) {
                for (Object key : documents.keySet())
                    document.add(documents.get(key));
            } else {
                objects = newdoc.get(name).objects;
                for (Object key : objects.keySet())
                    document.add(objects.get(key));
            }
        }
    }
    
    protected final void save(ExtendedObject object) {
        context.runtime().save(object);
    }
    
    protected final ExtendedObject[] select(Query query) {
        return context.runtime().select(query);
    }
    
    protected final ExtendedObject select(String model, Object key) {
        return select(model, null, key);
    }
    
    protected final ExtendedObject select(String model, Object ns, Object key) {
        return context.runtime().getObject(model, ns, key);
    }
    
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
    
    protected final void textcreate(String name) {
        context.runtime().createText(name);
    }
    
    protected final void texteditorsave(String tooldata, String id, String page) {
        RuntimeEngine runtime = context.runtime();
        ToolData _tooldata = context.getPage().instance(tooldata);
        _tooldata.values.put(id, _tooldata.value);
        runtime.updateText(_tooldata, id);
    }
    
    protected final String textget(String tooldata) {
        return (String)context.getPage().instance(tooldata).value;
    }
    
    protected final Map<String, String> textget(String name, String id) {
        return context.runtime().getText(name, id);
    }
    
    protected final void textremove(String id, String page) {
        context.runtime().removeText(id, page);
    }
    
    protected final void textsave(String id, String page, String text) {
        context.runtime().updateText(id, page, text);
    }
//    
//    protected final int update(Query query) {
//        return documents.update(query);
//    }
}
