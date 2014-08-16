package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
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
import org.iocaste.shell.common.StyleSheet;

public class Selection {
    
    public static final void create(Context context) {
        SHLib shlib;
        Documents documents = new Documents(context.function);
        DataForm form = context.view.getElement("model");
        String name = form.get("name").get();
        int op = Common.getTpObjectValue(context.view);
        
        switch (op) {
        case Common.TABLE:
            if (documents.getModel(name) != null) {
                context.view.message(Const.ERROR, "model.already.exist");
                return;
            }
            
            context.view.redirect("tbstructure");
            context.modelname = name;
            context.model = null;
            
            break;
        case Common.SH:
            shlib = new SHLib(context.function);
            if (shlib.get(name) != null) {
                context.view.message(Const.ERROR, "sh.already.exist");
                return;
            }
            
            context.view.redirect("shstructure");
            context.shname = name;
            
            break;
        }
        
        context.mode = Common.CREATE;
    }

    public static void deletesh(Context context) {
        ExtendedObject[] shdata;
        SHLib shlib = new SHLib(context.function);
        DataForm form = context.view.getElement("model");
        String name = form.get("name").get();
        
        shdata = shlib.get(name);
        if (shdata == null) {
            context.view.message(Const.ERROR, "sh.not.found");
            return;
        }
        
        try {
            shlib.remove((String)shdata[0].get("NAME"));
        } catch (Exception e){
            context.view.message(Const.ERROR, e.getCause().getMessage());
            return;
        }
        
        context.view.message(Const.STATUS, "sh.removed.sucessfully");
    }
    
    public static void deletetb(Context context) {
        Documents documents = new Documents(context.function);
        DataForm form = context.view.getElement("model");
        String name = form.get("name").get();
        
        if (documents.getModel(name) == null) {
            context.view.message(Const.ERROR, "model.not.found");
            return;
        }
        
        documents.removeModel(name);
        documents.commit();
        
        context.view.message(Const.STATUS, "model.removed.sucessfully");
    }
    
    /**
     * 
     * @param context
     */
    public static final void main(Context context) {
        RadioGroup group;
        RadioButton tpobj;
        NodeList optlist;
        StyleSheet stylesheet;
        Form main = new Form(context.view, "datadict.main");
        PageControl pagecontrol = new PageControl(main);
        DataForm modelform = new DataForm(main, "model");
        DataItem modelname = new DataItem(modelform, Const.TEXT_FIELD, "name");
        Documents documents = new Documents(context.function);
        
        pagecontrol.add("home");
        modelname.setModelItem(documents.getModel("MODEL").
                getModelItem("NAME"));
        modelname.setObligatory(true);
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(".optlist");
        stylesheet.put(".optlist", "list-style-type", "none");
        
        optlist = new NodeList(main, "optlist");
        optlist.setStyleClass("optlist");
        group = new RadioGroup(context.view, "tpobject");
        tpobj = group.button(optlist, "tpobjtable");
        tpobj.setText("table");
        tpobj.setSelected(true);
        
        tpobj = group.button(optlist, "tpobjsh");
        tpobj.setText("search.help");
        
        new Button(main, "create");
        new Button(main, "show");
        new Button(main, "update");
        new Button(main, "delete");
        new Button(main, "rename");
        
        context.view.setFocus(modelname);
        context.view.setTitle("datadict-selection");
    }

    public static final void readtb(Context context) {
        DataForm form = context.view.getElement("model");
        String name = form.get("name").get();
        Documents documents = new Documents(context.function);
        
        if (documents.getModel(name) == null) {
            context.view.message(Const.ERROR, "model.not.found");
            return;
        }
        
        context.modelname = name;
        context.model = documents.getModel(name);
        context.view.setReloadableView(true);
        context.view.redirect("tbstructure");
    }
    
    public static final void readsh(Context context) {
        SHLib shlib = new SHLib(context.function);
        DataForm form = context.view.getElement("model");
        String name = form.get("name").get();
        ExtendedObject[] shdata = shlib.get(name);
        
        if (shdata == null) {
            context.view.message(Const.ERROR, "sh.not.found");
            return;
        }
        
        context.shitens = new ExtendedObject[shdata.length - 1];
        System.arraycopy(shdata, 1, context.shitens, 0, context.shitens.length);
        context.header = shdata[0];
        context.shname = name;
        context.view.setReloadableView(true);
        context.view.redirect("shstructure");
    }
}
