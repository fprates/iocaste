package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.ViewData;

public class Selection {

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        RadioButton tpobj;
        Container main = new Form(null, "datadict.main");
        DataForm modelform = new DataForm(main, "modelform");
        DataItem modelname = new DataItem(modelform, Const.TEXT_FIELD,
                "modelname");
        SearchHelp search = new SearchHelp(main, "tablename");
        Documents documents = new Documents(function);
        
        search.setText("table.name.search");
        search.setModelName("MODEL");
        search.addModelItemName("NAME");
        search.setExport("NAME");
        
        modelname.setSearchHelp(search);
        modelname.setDataElement(documents.getDataElement("MODEL.NAME"));
        modelname.setObligatory(true);
        
        tpobj = new RadioButton(main, "tpobject");
        tpobj.add("0", "table");
        tpobj.add("1", "search.help");
        tpobj.setValue("0");
        
        new Button(main, "create");
        new Button(main, "show");
        new Button(main, "update");
        new Button(main, "delete");
        new Button(main, "rename");
        
        view.setFocus("modelname");
        view.setNavbarActionEnabled("back", true);
        view.setTitle("datadict-selection");
        view.addContainer(main);
    }
}
