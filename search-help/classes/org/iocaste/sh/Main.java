package org.iocaste.sh;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void choose(ViewData vdata) throws Exception {
        SearchHelp sh = vdata.getParameter("sh");
        Parameter value = vdata.getElement("value");
        ViewData view = sh.getView();
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
     * @param vdata
     * @throws Exception
     */
    public void main(ViewData vdata) throws Exception {
        TableColumn column;
        TableItem tableitem;
        String name, export;
        Object value;
        Text text;
        Link link;
        DocumentModel model;
        Table table;
        Parameter param;
        Container container = new Form(vdata, "main");
        SearchHelp sh = vdata.getParameter("sh");
        ExtendedObject[] result = getResultsFrom(sh);
        
        if (result == null) {
            text = new Text(container, "no.results.found");
            
            vdata.setNavbarActionEnabled("back", true);
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
                value = object.getValue(modelitem);
                export = sh.getExport();
                
                if (export != null && export.equals(name)) {
                    param.setModelItem(modelitem);
                    link = new Link(table, "choose", "choose");
                    tableitem.add(link);
                    link.add(param, value);
                    link.setText(value.toString());
                } else {
                    text = new Text(table, name);
                    tableitem.add(text);
                    text.setText((value == null)? "" : value.toString());
                }
                
                if (!sh.contains(name)) {
                    column = table.getColumn(name);
                    column.setVisible(false);
                }
            }
            
            tableitem.setObject(object);
        }
        
        vdata.setNavbarActionEnabled("back", true);
        vdata.setTitle(sh.getText());
    }
}
