package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.SHLib;
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
        SHLib shlib;
        Documents documents = new Documents(function);
        String name = ((DataItem)view.getElement("modelname")).getValue();
        int op = Common.getTpObjectValue(view);
        
        switch (op) {
        case Common.TABLE:
            if (documents.getModel(name) != null) {
                view.message(Const.ERROR, "model.already.exist");
                return;
            }
            
            view.redirect(null, "tbstructure");
            view.export("modelname", name);
            view.export("model", null);
            
            break;
        case Common.SH:
            shlib = new SHLib(function);
            if (shlib.get(name) != null) {
                view.message(Const.ERROR, "sh.already.exist");
                return;
            }
            
            view.redirect(null, "shstructure");
            view.export("shname", name);
            
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
    public static void deletesh(ViewData view, Function function)
            throws Exception {
        ExtendedObject[] shdata;
        SHLib shlib = new SHLib(function);
        String shname = ((DataItem)view.getElement("modelname")).getValue();
        
        shdata = shlib.get(shname);
        if (shdata == null) {
            view.message(Const.ERROR, "sh.not.found");
            return;
        }
        
        shlib.remove((String)shdata[0].getValue("NAME"));
        
        view.message(Const.STATUS, "sh.removed.sucessfully");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static void deletetb(ViewData view, Function function)
            throws Exception {
        Documents documents = new Documents(function);
        String modelname = ((DataItem)view.getElement("modelname")).getValue();
        
        if (documents.getModel(modelname) == null) {
            view.message(Const.ERROR, "model.not.found");
            return;
        }
        
        documents.removeModel(modelname);
        documents.commit();
        
        view.message(Const.STATUS, "model.removed.sucessfully");
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
        Container main = new Form(view, "datadict.main");
        DataForm modelform = new DataForm(main, "modelform");
        DataItem modelname = new DataItem(modelform, Const.TEXT_FIELD,
                "modelname");
        Documents documents = new Documents(function);
        
        modelname.setModelItem(documents.getModel("MODEL").
                getModelItem("NAME"));
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
    }

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void readtb(ViewData view, Function function)
            throws Exception {
        DocumentModel model;
        String name = ((DataItem)view.getElement("modelname")).getValue();
        Documents documents = new Documents(function);
        
        if (documents.getModel(name) == null) {
            view.message(Const.ERROR, "model.not.found");
            return;
        }
        
        model = documents.getModel(name);
        
        view.setReloadableView(true);
        view.export("model", model);
        view.redirect(null, "tbstructure");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void readsh(ViewData view, Function function)
            throws Exception {
        ExtendedObject[] shitens;
        SHLib shlib = new SHLib(function);
        String name = ((DataItem)view.getElement("modelname")).getValue();
        ExtendedObject[] shdata = shlib.get(name);
        
        if (shdata == null) {
            view.message(Const.ERROR, "sh.not.found");
            return;
        }
        
        shitens = new ExtendedObject[shdata.length - 1];
        System.arraycopy(shdata, 1, shitens, 0, shitens.length);
        
        view.setReloadableView(true);
        view.redirect(null, "shstructure");
        view.export("header", shdata[0]);
        view.export("itens", shitens);
        view.export("shname", name);
    }
}
