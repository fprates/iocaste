package org.iocaste.datadict;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.RadioGroup;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.View;

public class Selection {
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void create(View view, Function function,
            Context context) {
        SHLib shlib;
        Documents documents = new Documents(function);
        DataForm form = view.getElement("model");
        String name = form.get("name").get();
        int op = Common.getTpObjectValue(view);
        
        switch (op) {
        case Common.TABLE:
            if (documents.getModel(name) != null) {
                view.message(Const.ERROR, "model.already.exist");
                return;
            }
            
            view.redirect(null, "tbstructure");
            context.modelname = name;
            context.model = null;
            
            break;
        case Common.SH:
            shlib = new SHLib(function);
            if (shlib.get(name) != null) {
                view.message(Const.ERROR, "sh.already.exist");
                return;
            }
            
            view.redirect(null, "shstructure");
            context.shname = name;
            
            break;
        }
        
        context.mode = Common.CREATE;
    }

    /**
     * 
     * @param view
     * @param function
     */
    public static void deletesh(View view, Function function) {
        ExtendedObject[] shdata;
        SHLib shlib = new SHLib(function);
        DataForm form = view.getElement("model");
        String name = form.get("name").get();
        
        shdata = shlib.get(name);
        if (shdata == null) {
            view.message(Const.ERROR, "sh.not.found");
            return;
        }
        
        try {
            shlib.remove((String)shdata[0].getValue("NAME"));
        } catch (Exception e){
            view.message(Const.ERROR, e.getCause().getMessage());
            return;
        }
        
        view.message(Const.STATUS, "sh.removed.sucessfully");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static void deletetb(View view, Function function) {
        Documents documents = new Documents(function);
        DataForm form = view.getElement("model");
        String name = form.get("name").get();
        
        if (documents.getModel(name) == null) {
            view.message(Const.ERROR, "model.not.found");
            return;
        }
        
        documents.removeModel(name);
        documents.commit();
        
        view.message(Const.STATUS, "model.removed.sucessfully");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(View view, Function function) {
        Map<String, String> css;
        RadioGroup group;
        RadioButton tpobj;
        NodeList optlist;
        Form main = new Form(view, "datadict.main");
        PageControl pagecontrol = new PageControl(main);
        DataForm modelform = new DataForm(main, "model");
        DataItem modelname = new DataItem(modelform, Const.TEXT_FIELD, "name");
        Documents documents = new Documents(function);
        
        pagecontrol.add("home");
        modelname.setModelItem(documents.getModel("MODEL").
                getModelItem("NAME"));
        modelname.setObligatory(true);
        
        css = new HashMap<>();
        css.put("list-style-type", "none");
        view.getStyleSheet().put(".optlist", css);
        
        optlist = new NodeList(main, "optlist");
        optlist.setStyleClass("optlist");
        group = new RadioGroup("tpobject");
        tpobj = new RadioButton(optlist, "tpobjtable", group);
        tpobj.setText("table");
        tpobj.setSelected(true);
        
        tpobj = new RadioButton(optlist, "tpobjsh", group);
        tpobj.setText("search.help");
        
        new Button(main, "create");
        new Button(main, "show");
        new Button(main, "update");
        new Button(main, "delete");
        new Button(main, "rename");
        
        view.setFocus(modelname);
        view.setTitle("datadict-selection");
    }

    /**
     * 
     * @param view
     * @param function
     */
    public static final void readtb(View view, Function function,
            Context context) {
        DataForm form = view.getElement("model");
        String name = form.get("name").get();
        Documents documents = new Documents(function);
        
        if (documents.getModel(name) == null) {
            view.message(Const.ERROR, "model.not.found");
            return;
        }
        
        context.modelname = name;
        context.model = documents.getModel(name);
        view.redirect("tbstructure");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void readsh(View view, Function function,
            Context context) {
        SHLib shlib = new SHLib(function);
        DataForm form = view.getElement("model");
        String name = form.get("name").get();
        ExtendedObject[] shdata = shlib.get(name);
        
        if (shdata == null) {
            view.message(Const.ERROR, "sh.not.found");
            return;
        }
        
        context.shitens = new ExtendedObject[shdata.length - 1];
        System.arraycopy(shdata, 1, context.shitens, 0, context.shitens.length);
        context.header = shdata[0];
        context.shname = name;
        
        view.redirect("shstructure");
    }
}
