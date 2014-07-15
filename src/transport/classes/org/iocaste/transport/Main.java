package org.iocaste.transport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private static final String IDTAG = "IOCASTE000002";
    private static final int FILE_IS_EMPTY = 1;
    private static final int INVALID_FILE = 2;
    private static final int INVALID_HEADER = 3;
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void add() {
        Table objects = context.view.getElement("objects");
        insertItem(objects);
    }
    
    /**
     * 
     * @param filename
     * @param function
     * @return
     * @throws Exception
     */
    private final int build(String filename, Function function)
            throws Exception {
        int currentline, nitens, pass;
        String[] parsed;
        DocumentModel model;
        DocumentModelItem modelitem;
        DataElement dataelement;
        Documents documents;
        File file = new File(filename);
        FileReader freader = new FileReader(file);
        BufferedReader breader = new BufferedReader(freader);
        List<String> list = new ArrayList<>();
        
        while (breader.ready())
            list.add(breader.readLine());
        
        breader.close();
        if (list.size() == 0)
            return FILE_IS_EMPTY;
        
        pass = 0;
        currentline = 0;
        nitens = 0;
        model = null;
        documents = new Documents(this);
        for (String line : list) {
            switch (pass) {
            case 0:
                if (!line.equals(IDTAG))
                    return INVALID_FILE;
                
                pass = 1;
                continue;
            case 1:
                parsed = line.split(";");
                
                if (parsed.length != 4)
                    return INVALID_HEADER;
                
                model = new DocumentModel(parsed[0]);
                model.setTableName(parsed[1]);
                model.setClassName(parsed[2]);
                
                nitens = Integer.parseInt(parsed[3]);
                pass = 2;
                currentline = 0;
                continue;
            case 2:
                parsed = line.split(";");
                modelitem = new DocumentModelItem(parsed[0]);
                modelitem.setDocumentModel(model);
                modelitem.setTableFieldName(parsed[1]);
                modelitem.setAttributeName(parsed[2]);
                modelitem.setIndex(currentline);
                
                dataelement = new DataElement(parsed[3]);
                dataelement.setType(Integer.parseInt(parsed[4]));
                dataelement.setLength(Integer.parseInt(parsed[5]));
                dataelement.setDecimals(Integer.parseInt(parsed[6]));
                dataelement.setUpcase(Boolean.parseBoolean(parsed[7]));
                
                modelitem.setDataElement(dataelement);
                if (Boolean.parseBoolean(parsed[8]))
                    model.add(new DocumentModelKey(modelitem));
                
                if ((parsed.length == 11) && (!parsed[9].equals("")))
                    modelitem.setReference(documents.getModel(parsed[9]).
                            getModelItem(parsed[10]));
                
                model.add(modelitem);
                
                currentline++;
                if (currentline != nitens)
                    continue;
                
                if (documents.getModel(model.getName()) == null)
                    documents.createModel(model);
                else
                    documents.updateModel(model);
                
                pass = 1;
                
                continue;
            }
        }
        
        return 0;
    }
    
    public final void download() {
        for (String line : context.instructions)
            context.view.print(line+"\n");
        
        context.view.setContentType("text/plain");
        context.view.setHeader("Content-Disposition",
                new StringBuilder("attachment; filename=\"").
                append(context.instructionname).append("\"").toString());
    }
    
    public final void generate() {
        String name;
        Table objects = context.view.getElement("objects");
        List<String> lines;
        Documents documents = new Documents(this);
        
        context.instructions.clear();
        context.instructions.add(IDTAG);
        
        for (TableItem item : objects.getItems()) {
            name = ((InputComponent)item.get("object")).get();
            if (name.length() == 0)
                continue;
            
            lines = getInstructions(name, documents);
            if (lines == null) {
                context.view.message(Const.ERROR, "model.not.found");
                return;
            }
            
            context.instructions.addAll(lines);
        }
        
        if (context.instructions.size() == 1)
            return;
        
        context.instructionname = new StringBuilder(
                "IOCASTE_BUILD_INSTRUCTIONS_").
                append(Calendar.getInstance().getTime().getTime()).
                append(".txt").toString();
        
        context.view.redirect("download");
    }
    
    /**
     * 
     * @param name
     * @param documents
     * @return
     */
    public final List<String> getInstructions(String name, Documents documents)
    {
        DataElement dataelement;
        StringBuilder sb;
        List<String> lines;
        DocumentModel model;
        DocumentModelItem reference;
        DocumentModelItem[] itens;
        
        model = documents.getModel(name);
        if (model == null)
            return null;
        
        lines = new ArrayList<>();
        itens = model.getItens();
        
        sb = new StringBuilder(model.getName()).append(";").
                append(model.getTableName()).append(";").
                append(model.getClassName()).append(";").
                append(itens.length);
        
        lines.add(sb.toString());
        for (DocumentModelItem item : itens) {
            dataelement = item.getDataElement();
            reference = item.getReference();
            
            sb = new StringBuilder(item.getName()).append(";").
                    append(item.getTableFieldName()).append(";").
                    append(item.getAttributeName()).append(";").
                    append(dataelement.getName()).append(";").
                    append(dataelement.getType()).append(";").
                    append(dataelement.getLength()).append(";").
                    append(dataelement.getDecimals()).append(";").
                    append(dataelement.isUpcase()).append(";").
                    append(model.isKey(item)).append(";");
            
            if (reference != null)
                sb.append(reference.getDocumentModel().getName()).append(";").
                        append(reference.getName());
            else
                sb.append(";");
            
            lines.add(sb.toString());
        }
        
        return lines;
    }
    
    public final void importitens() throws Exception {
        String filename = null;
        Table table = context.view.getElement("pool");
        
        for (TableItem item : table.getItems()) {
            if (!item.isSelected())
                continue;
            
            filename = getRealPath("WEB-INF", "pool",
                    ((Text)item.get("filename")).getName());
            switch (build(filename, this)) {
            case FILE_IS_EMPTY:
                context.view.message(Const.ERROR, "file.is.empty");
                return;
                
            case INVALID_FILE:
                context.view.message(Const.ERROR, "invalid.file");
                return;
                
            case INVALID_HEADER:
                context.view.message(Const.ERROR, "invalid.header");
                return;
            }
        }
        
        if (filename == null)
            return;
        
        context.view.message(Const.STATUS, "importing-successful");
    }
    
    @Override
    public final AbstractContext init(View view) {
        context = new Context();
        
        return context;
    }
    
    /**
     * 
     * @param table
     */
    private final void insertItem(Table table) {
        Documents documents = new Documents(this);
        TextField tfield = new TextField(new TableItem(table), "name");
        SearchHelp sh = new SearchHelp(table, "shname");
        
        sh.setModelName("MODEL");
        sh.addModelItemName("NAME");
        sh.setExport("NAME");
        
        tfield.setDataElement(documents.getDataElement("MODEL.NAME"));
        tfield.setSearchHelp(sh);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public void list() {
        String[] files = context.view.getParameter("files");
        Form container = new Form(context.view, "list");
        PageControl pagecontrol = new PageControl(container);
        Table table = new Table(container, "pool");
        
        pagecontrol.add("back");
        
        new TableColumn(table, "filename");
        table.setHeader(false);
        table.setMark(true);
        
        for (String file : files)
            new Text(new TableItem(table), file);
        
        new Button(container, "importitens");
        context.view.setTitle("importable-build-instructions");
    }
    
    /**
     * 
     * @param view
     */
    public void main() {
        TableItem importtbitem;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        Form uploadcontainer = new Form(context.view, "upldcntr");
        Frame exportframe = new Frame(container, "export");
        Frame importframe = new Frame(uploadcontainer, "import");
        Table importtable, objects = new Table(exportframe, "objects");
        
        pagecontrol.add("home");
        
        new TableColumn(objects, "object");
        
        insertItem(objects);
        
        new Button(exportframe, "add");
        new Button(exportframe, "generate");
        
        uploadcontainer.setEnctype("multipart/form-data");
        
        importtable = new Table(importframe, "importtable");
        importtable.setHeader(false);
        
        new TableColumn(importtable, "a");
        
        new FileEntry(new TableItem(importtable), "buildfile");
        new Button(new TableItem(importtable), "upload");
        new Link(new TableItem(importtable), "frompool", "pool");
        
        context.view.setTitle("transport-utility");
    }
    
    /**
     * 
     * @param view
     */
    public void pool() {
        File file = new File(getRealPath("WEB-INF", "pool"));
        String[] files = file.list();
        
        if (files == null) {
            context.view.message(Const.ERROR, "no.files");
            return;
        }
        
        context.view.redirect("list");
    }
    
    public void upload() throws Exception {
        FileEntry fileentry = null;
        String filename = null;
        Table table = context.view.getElement("importtable");
        
        fileentry = (FileEntry)table.get(0).get("a");
        filename = fileentry.get();
        
        switch (fileentry.getError()) {
        case FileEntry.FILE_NOT_FOUND:
            context.view.message(Const.ERROR, "file.not.found");
            return;
        case FileEntry.EMPTY_FILE_NAME:
            context.view.message(Const.ERROR, "filename.is.obligatory");
            return;
        }
        
        switch (build(filename, this)) {
        case FILE_IS_EMPTY:
            context.view.message(Const.ERROR, "file.is.empty");
            return;
            
        case INVALID_FILE:
            context.view.message(Const.ERROR, "invalid.file");
            return;
            
        case INVALID_HEADER:
            context.view.message(Const.ERROR, "invalid.header");
            return;
        }
        
        context.view.message(Const.STATUS, "model.imported.successfully");
    }
}

class Context extends AbstractContext {
    public String instructionname;
    public List<String> instructions;
    
    public Context() {
        instructions = new ArrayList<>();
    }
}