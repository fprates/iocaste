package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
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
    public static final void create(ViewData view, Function function)
            throws Exception {
        DocumentModel model;
        Documents documents = new Documents(function);
        String name = ((DataItem)view.getElement("modelname")).getValue();
        int op = Common.getTpObjectValue(view);
        
        switch (op) {
        case Common.TABLE:
            if (documents.hasModel(name)) {
                view.message(Const.ERROR, "model.already.exist");
                return;
            }
            
            view.redirect(null, "tbstructure");
            view.export("modelname", name);
            view.export("model", null);
            
            break;
        case Common.SH:
            model = documents.getModel("SEARCH_HELP");
            
            view.redirect(null, "shstructure");
            view.export("shname", name);
            view.export("shmodel", model);
            
            model = documents.getModel("SH_ITENS");
            view.export("shitens", model);
            
            break;
        }
        
        view.setReloadableView(true);
        view.export("mode", Common.CREATE);
    }

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

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void show(ViewData view, Function function)
            throws Exception {
        DocumentModel model;
        String name = ((DataItem)view.getElement("modelname")).getValue();
        Documents documents = new Documents(function);
        int op = Common.getTpObjectValue(view);
        
        switch (op) {
        case Common.TABLE:
            if (!documents.hasModel(name)) {
                view.message(Const.ERROR, "model.not.found");
                return;
            }
            
            model = documents.getModel(name);
            view.export("model", model);
            view.redirect(null, "tbstructure");
            
            break;
            
        case Common.SH:
            view.redirect(null, "shstructure");
            view.export("shname", name);
            
            break;
        }
        
        view.setReloadableView(true);
        view.export("mode", Common.SHOW);
    }

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void update(ViewData view, Function function)
            throws Exception {
        DocumentModel model;
        String modelname = ((DataItem)view.getElement("modelname")).getValue();
        Documents documents = new Documents(function);
        
        if (!documents.hasModel(modelname)) {
            view.message(Const.ERROR, "model.not.found");
            return;
        }
        
        model = documents.getModel(modelname);
        
        view.setReloadableView(true);
        view.export("mode", Common.UPDATE);
        view.export("model", model);
        view.redirect(null, "tbstructure");
    }
}
