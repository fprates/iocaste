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
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
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
        
        instructions.add("IOCASTE000001");
        
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
    
    public final void importing(ViewData view) throws Exception {
        Parameter filepath = (Parameter)view.getElement("file");
        File file = new File(filepath.getValue());
        FileReader freader = new FileReader(file);
        BufferedReader breader = new BufferedReader(freader);
        List<String> list = new ArrayList<String>();
        
        while (breader.ready())
            list.add(breader.readLine());
        
        breader.close();
        
        view.export("list", list.toArray(new String[0]));
        view.redirect(null, "download");
        view.message(Const.STATUS, "objects.deployed.successfully");
        view.setReloadableView(true);
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
        Table objects = new Table(container, "objects");
        
        new TableColumn(objects, "object");
        
        insertItem(objects);
        
        new Button(container, "add");
        new Button(container, "generate");
        
        view.addContainer(container);
        view.setNavbarActionEnabled("back", true);
        view.setTitle("transport-utility");
    }
    
//    public final void report(ViewData view) {
//        Container container = new Form(null, "report");
//        String[] list = (String[])view.getParameter("list");
//        
//        for (String line : list)
//            view.print(line);
//        
//        view.setTitle("order-viewer");
//        view.setNavbarActionEnabled("back", true);
//        view.addContainer(container);
//    }
}
