package org.iocaste.sh;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public final void cancel(ControlData cdata, ViewData vdata) {
        
    }
    
    public final void choose(ControlData cdata, ViewData vdata) {
        // implemente ações como nesse exemplo
    }
    
    private ExtendedObject[] getResultsFrom(SearchHelp sh) throws Exception {
        Documents documents = new Documents(this);
        return documents.select("from "+sh.getModelName(), null);
    }
    
    public void main(ViewData vdata) throws Exception {
        TableItem tableitem;
        String name;
        Text text;
        SearchHelp sh = (SearchHelp)vdata.getParameter("sh");
        ExtendedObject[] result = getResultsFrom(sh);
        DocumentModel model = result[0].getModel();
        Container container = new Form(null, "main");
        Table table = new Table(container, 0, "search.table");
        int i;
        
        table.importModel(model);
        
        for (ExtendedObject object : result) {
            tableitem = new TableItem(table);
            i = 0;
            
            for (DocumentModelItem modelitem : model.getItens()) {
                i++;
                name = modelitem.getName();
                
                tableitem.add(Const.TEXT, name, null);
                text = (Text)table.getElement(tableitem.getComplexName(name));
                text.setText((String)object.getValue(modelitem));
                
                if (!sh.contains(name))
                    table.setVisibleColumn(i, false);
            }
            
            tableitem.setObject(object);
        }
        
        vdata.disableHead();
        vdata.addContainer(container);
    }
}
