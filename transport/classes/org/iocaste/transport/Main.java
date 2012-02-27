package org.iocaste.transport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final String IDTAG = "IOCASTE000001";
    
    public final void add(ViewData view) throws Exception {
        Table objects = (Table)view.getElement("objects");
        
        insertItem(objects);
    }
    
    public final void download(ViewData view) throws Exception {
        String filename = (String)view.getParameter("instructionname");
        String[] lines = (String[])view.getParameter("list");
        
        for (String line : lines)
            view.print(line+"\n");
        
        view.setContentType("text/plain");
        view.setHeader("Content-Disposition", "attachment; filename=\"" +
                filename + "\"");
    }
    
    public final void generate(ViewData view) throws Exception {
        String name;
        Table objects = (Table)view.getElement("objects");
        List<String> lines, instructions = new ArrayList<String>();
        Documents documents = new Documents(this);
        
        instructions.add(IDTAG);
        
        for (TableItem item : objects.getItens()) {
            name = ((InputComponent)item.get("object")).getValue();
            
            if (name.equals(""))
                continue;
            
            lines = getInstructions(name, documents);
            
            if (lines == null) {
                view.message(Const.ERROR, "model.not.found");
                return;
            }
            
            instructions.addAll(lines);
        }
        
        if (instructions.size() == 1)
            return;
        
        name = new StringBuilder("IOCASTE_BUILD_INSTRUCTIONS_").
                append(Calendar.getInstance().getTime().getTime()).
                append(".txt").toString();
        
        view.setReloadableView(true);
        view.export("list", instructions.toArray(new String[0]));
        view.export("instructionname", name);
        view.redirect(null, "download");
    }
    
    public final List<String> getInstructions(String name, Documents documents) 
            throws Exception {
        DataElement dataelement;
        StringBuilder sb;
        List<String> lines;
        DocumentModel model;
        Set<DocumentModelItem> itens;
        
        if (!documents.hasModel(name))
            return null;
        
        lines = new ArrayList<String>();  

        model = documents.getModel(name);
        itens = model.getItens();
        
        sb = new StringBuilder(model.getName()).append(";").
                append(model.getTableName()).append(";").
                append(model.getClassName()).append(";").
                append(itens.size());
        
        lines.add(sb.toString());
        for (DocumentModelItem item : itens) {
            dataelement = item.getDataElement();
            
            sb = new StringBuilder(item.getName()).append(";").
                    append(item.getTableFieldName()).append(";").
                    append(item.getAttributeName()).append(";").
                    append(dataelement.getName()).append(";").
                    append(dataelement.getType()).append(";").
                    append(dataelement.getLength()).append(";").
                    append(dataelement.getDecimals()).append(";").
                    append(dataelement.isUpcase()).append(";").
                    append(model.isKey(item));
            
            lines.add(sb.toString());
        }
        
        return lines;
    }

    private final void insertItem(Table table) throws Exception {
        Documents documents = new Documents(this);
        TableItem item = new TableItem(table);
        TextField tfield = new TextField(table, "name");
        SearchHelp sh = new SearchHelp(table, "shname");
        
        sh.setModelName("MODEL");
        sh.addModelItemName("NAME");
        sh.setExport("NAME");
        
        tfield.setDataElement(documents.getDataElement("MODEL.NAME"));
        tfield.setSearchHelp(sh);
        
        item.add(tfield);
    }
    
    public void main(ViewData view) throws Exception {
        Container container = new Form(null, "main");
        Form uploadcontainer = new Form(null, "upldcntr");
        Frame exportframe = new Frame(container, "export");
        Frame importframe = new Frame(uploadcontainer, "import");
        Table objects = new Table(exportframe, "objects");
        
        new TableColumn(objects, "object");
        
        insertItem(objects);
        
        new Button(exportframe, "add");
        new Button(exportframe, "generate");
        
        uploadcontainer.setEnctype("multipart/form-data");
        
        new FileEntry(importframe, "buildfile");
        new Button(importframe, "upload");
        
        view.addContainer(container);
        view.addContainer(uploadcontainer);
        view.setNavbarActionEnabled("back", true);
        view.setTitle("transport-utility");
    }
    
    public void upload(ViewData view) throws Exception {
        int currentline, nitens, pass;
        File file;
        FileReader freader;
        BufferedReader breader;
        List<String> list;
        String[] parsed;
        DocumentModel model;
        DocumentModelItem modelitem;
        DocumentModelKey key;
        DataElement dataelement;
        Documents documents;
        FileEntry fileentry = (FileEntry)view.getElement("buildfile");
        String filename = fileentry.getValue();
        
        if (filename.equals("")) {
            view.message(Const.ERROR, "filename.is.obligatory");
            return;
        }
        
        file = new File(filename);
        freader = new FileReader(file);
        breader = new BufferedReader(freader);
        list = new ArrayList<String>();
        
        while (breader.ready())
            list.add(breader.readLine());
        
        breader.close();
        
        if (list.size() == 0) {
            view.message(Const.ERROR, "file.is.empty");
            return;
        }
        
        pass = 0;
        currentline = 0;
        nitens = 0;
        model = null;
        documents = new Documents(this);
        
        for (String line : list) {
            switch (pass) {
            case 0:
                if (!line.equals(IDTAG)) {
                    view.message(Const.ERROR, "invalid.file");
                    return;
                }
                
                pass = 1;
                continue;
            case 1:
                parsed = line.split(";");
                
                if (parsed.length != 4) {
                    view.message(Const.ERROR, "invalid.header");
                    return;
                }
                
                model = new DocumentModel();
                model.setName(parsed[0]);
                model.setTableName(parsed[1]);
                model.setClassName(parsed[2]);
                
                nitens = Integer.parseInt(parsed[3]);
                
                pass = 2;
                
                currentline = 0;
                continue;
            case 2:
                parsed = line.split(";");
                
                modelitem = new DocumentModelItem();
                modelitem.setDocumentModel(model);

                modelitem.setName(parsed[0]);
                modelitem.setTableFieldName(parsed[1]);
                modelitem.setAttributeName(parsed[2]);
                modelitem.setIndex(currentline);
                
                dataelement = new DataElement();
                dataelement.setName(parsed[3]);
                dataelement.setType(Integer.parseInt(parsed[4]));
                dataelement.setLength(Integer.parseInt(parsed[5]));
                dataelement.setDecimals(Integer.parseInt(parsed[6]));
                dataelement.setUpcase(Boolean.parseBoolean(parsed[7]));
                
                modelitem.setDataElement(dataelement);
                
                if (Boolean.parseBoolean(parsed[8])) {
                    key = new DocumentModelKey();
                    key.setModel(model);
                    key.setModelItem(modelitem.getName());
                    
                    model.addKey(key);
                }
                
                model.add(modelitem);
                
                currentline++;
                
                if (currentline != nitens)
                    continue;
                
                if (!documents.hasModel(model.getName()))
                    documents.createModel(model);
                else
                    documents.updateModel(model);
                
                pass = 1;
                
                continue;
            }
        }
        
        view.message(Const.STATUS, "model.imported.successfully");
    }
}
