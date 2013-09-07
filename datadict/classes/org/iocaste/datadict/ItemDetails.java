package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class ItemDetails {
    
    /**
     * 
     * @param view
     * @param function
     * @param context
     */
    public static final void main(View view, Function function,
            Context context) {
        DataItem dataitem;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        Frame techframe, fkframe = new Frame(container, "foreign.key");
        DataForm techform, fkform = new DataForm(fkframe, "fkform");
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("MODELITEM");
        
        pagecontrol.add("back");
        
        /*
         * foreign key
         */
        dataitem = new DataItem(fkform, Const.TEXT_FIELD, "model.name");
        dataitem.setModelItem(model.getModelItem("MODEL"));
        dataitem.set(context.detail.modelname);
        dataitem.setEnabled(false);
        
        dataitem = new DataItem(fkform, Const.TEXT_FIELD, "item.name");
        dataitem.setModelItem(model.getModelItem("NAME"));
        dataitem.set(context.detail.itemname);
        dataitem.setEnabled(false);
        
        dataitem = new DataItem(fkform, Const.TEXT_FIELD, "reference.model");
        dataitem.setModelItem(model.getModelItem("MODEL"));
        dataitem.set(context.detail.modelref);
        dataitem.setEnabled(context.mode != Common.SHOW);
        view.setFocus(dataitem);
        
        dataitem = new DataItem(fkform, Const.TEXT_FIELD, "reference.item");
        dataitem.setModelItem(model.getModelItem("NAME"));
        dataitem.set(context.detail.itemref);
        dataitem.setEnabled(context.mode != Common.SHOW);

        /*
         * technical details
         */
        techframe = new Frame(container, "technical.details");
        techform = new DataForm(techframe, "techform");
        
        dataitem = new DataItem(techform, Const.TEXT_FIELD, "item.classfield");
        dataitem.setModelItem(model.getModelItem("ATTRIB"));
        dataitem.set(context.detail.classfield);
        dataitem.setEnabled(context.mode != Common.SHOW);
        
        dataitem = new DataItem(techform, Const.TEXT_FIELD, "item.sh");
        dataitem.setModelItem(documents.getModel("SH_REFERENCE").
                getModelItem("NAME"));
        dataitem.set(context.detail.sh);
        dataitem.setEnabled(context.mode != Common.SHOW);
        
        dataitem = new DataItem(techform, Const.CHECKBOX, "item.upcase");
        dataitem.setModelItem(documents.getModel("DATAELEMENT").
                getModelItem("UPCASE"));
        dataitem.set(context.detail.upcase);
        dataitem.setEnabled(context.mode != Common.SHOW);
        
        if (context.mode != Common.SHOW)
            new Button(container, "detailsupdate");
        
        view.setTitle("item-detail-editor");
    }
    
    /**
     * 
     * @param view
     * @param context
     */
    public static final void select(View view, Context context) {
        DataForm form = view.getElement("header");
        Table itens = view.getElement("itens");
        TableItem selected = null;
        
        for (TableItem item : itens.getItems()) {
            if (selected != null) {
                view.message(Const.ERROR, "choose.one.item.only");
                return;
            }
                
            if (item.isSelected()) {
                item.setSelected(false);
                selected = item;
                break;
            }
            
            item.setSelected(false);
        }
        
        if (selected == null) {
            view.message(Const.ERROR, "choose.one.item");
            return;
        }
        
        context.detail = new ItemDetail();
        context.detail.modelname = form.get("modelname").get();
        context.detail.itemname = Common.getTableValue(selected, "item.name");
        context.detail.modelref = Common.getTableValue(selected,"model.reference");
        context.detail.itemref = Common.getTableValue(selected, "item.reference");
        context.detail.upcase = Common.getTableValue(selected, "item.upcase");
        context.detail.classfield = Common.getTableValue(selected, "item.classfield");
        context.detail.sh = Common.getTableValue(selected, "item.sh");
        
        view.redirect("detailsview");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final boolean update(View view, Function function) {
        DataItem refitem;
        InputComponent input;
        DocumentModel model;
        DocumentModelItem modelitemref;
        boolean upcase; 
        DataForm form = view.getElement("fkform");
        String itemname = form.get("item.name").get();
        String shname, classfield, modelref = null, itemref = null;
        Shell shell = new Shell(function);
        View structview = shell.getView(view, "tbstructure");
        Table itens = structview.getElement("itens");
        
        input = form.get("reference.model");
        if (input.get() != null) {
            modelref = input.get();
            model = new Documents(function).getModel(modelref);
            refitem = form.get("reference.item");
            itemref = refitem.get();
            modelitemref = model.getModelItem(itemref);
            
            if (modelitemref == null) {
                view.message(Const.ERROR, "reference.doesnt.exists");
                view.setFocus(refitem);
                return false;
            }
                
            if (!model.isKey(modelitemref)) {
                view.message(Const.ERROR, "reference.isnot.key");
                view.setFocus(refitem);
                return false;
            }
        }
        
        form = view.getElement("techform");
        shname = form.get("item.sh").get();
        upcase = form.get("item.upcase").isSelected();
        classfield = form.get("item.classfield").get();
        
        for (TableItem item : itens.getItems()) {
            input = item.get("item.name");
            
            if (!input.get().equals(itemname))
                continue;
            
            Common.setTableValue(item, "item.reference", itemref);
            Common.setTableValue(item, "model.reference", modelref);
            Common.setTableValue(item, "item.sh", shname);
            Common.setTableValue(item, "item.classfield", classfield);
            ((InputComponent)item.get("item.upcase")).setSelected(upcase);
            
            break;
        }
        
        shell.updateView(structview);
        view.message(Const.STATUS, "reference.updated");
        
        return true;
    }
}
