package org.iocaste.sh;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param controldata
     * @param view
     * @throws Exception 
     */
    @Override
    public void back(View view) throws Exception {
        String[] entry = new Shell(this).popPage(view);
        view.redirect(entry[0], entry[1]);
        view.dontPushPage();
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void choose(View vdata) throws Exception {
        SearchHelp sh = vdata.getParameter("sh");
        Parameter value = vdata.getElement("value");
        View view = sh.getView();
        InputComponent input = view.getElement(sh.getInputName());
        
        if (!input.isEnabled()) {
            back(vdata);
            return;
        }
        
        input.set(value.get());
        updateView(view);
        back(vdata);
    }
    
    /**
     * 
     * @param sh
     * @return
     * @throws Exception
     */
    private ExtendedObject[] getResultsFrom(SearchHelp sh) throws Exception {
        Documents documents = new Documents(this);
        return documents.select("from "+sh.getModelName());
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public void main(View vdata) throws Exception {
        TableColumn column;
        TableItem tableitem;
        String name, export;
        Object value;
        Text text;
        Link link;
        DocumentModel model;
        Table table;
        Parameter param;
        Form container = new Form(vdata, "main");
        PageControl pagecontrol = new PageControl(container);
        SearchHelp sh = vdata.getParameter("sh");
        ExtendedObject[] result = getResultsFrom(sh);
        
        pagecontrol.add("back");
        
        if (result == null) {
            text = new Text(container, "no.results.found");
            
            vdata.setTitle(sh.getText());
            
            return;
        }
        
        model = result[0].getModel();
        table = new Table(container, "search.table");
        param = new Parameter(container, "value");
        
        table.importModel(model);
        
        for (ExtendedObject object : result) {
            tableitem = new TableItem(table);
            
            for (DocumentModelItem modelitem : model.getItens()) {
                name = modelitem.getName();
                column = table.getColumn(name);
                value = object.getValue(modelitem);
                export = sh.getExport();
                
                if (export != null && export.equals(name)) {
                    param.setModelItem(modelitem);
                    link = new Link(table, "choose", "choose");
                    tableitem.add(link);
                    link.add(param, value);
                    link.setText(value.toString());
                } else {
                    column.setRenderTextOnly(true);
                    text = new Text(table, name);
                    tableitem.add(text);
                    text.setText((value == null)? "" : value.toString());
                }
                
                if (!sh.contains(name))
                    column.setVisible(false);
            }
            
            tableitem.setObject(object);
        }
        
        vdata.setTitle(sh.getText());
    }
}
