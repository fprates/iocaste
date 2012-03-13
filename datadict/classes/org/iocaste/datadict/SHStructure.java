package org.iocaste.datadict;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.sh.common.SHLib;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class SHStructure {

    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(ViewData view, Function function) {
        DataItem ditem;
        Container container = new Form(null, "main");
        DocumentModel model = view.getParameter("shmodel");
        DataForm header = new DataForm(container, "header");
        Table itens = new Table(container, "itens");
        
        header.importModel(model);
        
        for (Element element : header.getElements()) {
            if (element.getType() != Const.DATA_ITEM)
                continue;
            
            ditem = (DataItem)element;
            
            if (ditem.getName().equals("NAME")) {
                ditem.setValue((String)view.getParameter("shname"));
                ditem.setEnabled(false);
                
                continue;
            }
            
            ditem.setObligatory(true);
        }
        
        model = view.getParameter("shitens");
        itens.importModel(model);
        itens.getColumn("SEARCH_HELP").setVisible(false);
        itens.setMark(true);
        
        Add.insertSHItem(itens);
        
        new Button(container, "saveshitem");
        new Button(container, "addshitem");
        new Button(container, "deleteshitem");
        
        view.setNavbarActionEnabled("back", true);
        view.addContainer(container);
    }
    
    public static final void save(ViewData view, Function function)
            throws Exception {
        List<ExtendedObject> oitens;
        SHLib shlib = new SHLib(function);
        DataForm header = view.getElement("header");
        ExtendedObject oitem, object = header.getObject();
        Table itens = view.getElement("itens");
          
        oitens = new ArrayList<ExtendedObject>();
        for (TableItem item : itens.getItens()) {
            oitem = item.getObject();
            oitem.setValue("NAME", object.getValue("NAME"));
            oitens.add(oitem);
        }
          
        shlib.save(object, oitens.toArray(new ExtendedObject[0]));
          
        view.message(Const.STATUS, "search.help.saved.successfully");
    }
}
