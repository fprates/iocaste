package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
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
    
    public static final void main(Context context) {
        DataItem dataitem;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        Frame techframe, fkframe = new Frame(container, "foreign.key");
        DataForm techform, fkform = new DataForm(fkframe, "fkform");
        Documents documents = new Documents(context.function);
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
        context.view.setFocus(dataitem);
        
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
        
        context.view.setTitle("item-detail-editor");
    }
    
    public static final void select(Context context) {
        DataForm form = context.view.getElement("header");
        Table itens = context.view.getElement("itens");
        TableItem selected = null;
        
        for (TableItem item : itens.getItems()) {
            if (selected != null) {
                context.view.message(Const.ERROR, "choose.one.item.only");
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
            context.view.message(Const.ERROR, "choose.one.item");
            return;
        }
        
        context.detail = new ItemDetail();
        context.detail.modelname = form.get("modelname").get();
        context.detail.itemname = Common.getTableInput(selected,
                "item.name").get();
        context.detail.modelref = Common.getTableInput(selected,
                "model.reference").get();
        context.detail.itemref = Common.getTableInput(selected,
                "item.reference").get();
        context.detail.upcase = Common.getTableInput(selected,
                "item.upcase").get();
        context.detail.classfield = Common.getTableInput(selected,
                "item.classfield").get();
        context.detail.sh = Common.getTableInput(selected, "item.sh").get();
        
        context.view.redirect("detailsview");
    }
    
    public static final boolean update(Context context) {
        DataItem refitem;
        InputComponent input;
        DocumentModel model;
        DocumentModelItem modelitemref;
        boolean upcase; 
        DataForm form = context.view.getElement("fkform");
        String itemname = form.get("item.name").get();
        String shname, classfield, modelref = null, itemref = null;
        Shell shell = new Shell(context.function);
        View structview = shell.getView(context.view, "tbstructure");
        Table itens = structview.getElement("itens");
        
        input = form.get("reference.model");
        if (input.get() != null) {
            modelref = input.get();
            model = new Documents(context.function).getModel(modelref);
            refitem = form.get("reference.item");
            itemref = refitem.get();
            modelitemref = model.getModelItem(itemref);
            
            if (modelitemref == null) {
                context.view.message(Const.ERROR, "reference.doesnt.exists");
                context.view.setFocus(refitem);
                return false;
            }
                
            if (!model.isKey(modelitemref)) {
                context.view.message(Const.ERROR, "reference.isnot.key");
                context.view.setFocus(refitem);
                return false;
            }
        }
        
        form = context.view.getElement("techform");
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
        context.view.message(Const.STATUS, "reference.updated");
        
        return true;
    }
}
