package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class ItemDetails {

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        DataItem dataitem;
        String itemname = view.getParameter("item.name");
        String modelname = view.getParameter("model.name");
        String itemref = view.getParameter("item.reference");
        String modelref = view.getParameter("model.reference");
        String upcase = view.getParameter("item.upcase");
        String sh = view.getParameter("item.sh");
        String classfield = view.getParameter("item.classfield");
        Container container = new Form(null, "main");
        Frame techframe, fkframe = new Frame(container, "foreign.key");
        DataForm techform, fkform = new DataForm(fkframe, "fkform");
        byte mode = Common.getMode(view);
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("MODELITEM");
        
        /*
         * foreign key
         */
        dataitem = new DataItem(fkform, Const.TEXT_FIELD, "model.name");
        dataitem.setModelItem(model.getModelItem("MODEL"));
        dataitem.setValue(modelname);
        dataitem.setEnabled(false);
        
        dataitem = new DataItem(fkform, Const.TEXT_FIELD, "item.name");
        dataitem.setModelItem(model.getModelItem("NAME"));
        dataitem.setValue(itemname);
        dataitem.setEnabled(false);
        
        dataitem = new DataItem(fkform, Const.TEXT_FIELD, "reference.model");
        dataitem.setModelItem(model.getModelItem("MODEL"));
        dataitem.setValue(modelref);
        dataitem.setEnabled((mode == Common.SHOW)? false : true);
        
        dataitem = new DataItem(fkform, Const.TEXT_FIELD, "reference.item");
        dataitem.setModelItem(model.getModelItem("NAME"));
        dataitem.setValue(itemref);
        dataitem.setEnabled((mode == Common.SHOW)? false : true);

        /*
         * technical details
         */
        techframe = new Frame(container, "technical.details");
        techform = new DataForm(techframe, "techform");
        
        dataitem = new DataItem(techform, Const.TEXT_FIELD, "item.classfield");
        dataitem.setModelItem(model.getModelItem("ATTRIB"));
        dataitem.setValue(classfield);
        dataitem.setEnabled((mode == Common.SHOW)? false : true);
        
        dataitem = new DataItem(techform, Const.TEXT_FIELD, "item.sh");
        dataitem.setModelItem(documents.getModel("SH_REFERENCE").
                getModelItem("NAME"));
        dataitem.setValue(sh);
        dataitem.setEnabled((mode == Common.SHOW)? false : true);
        
        dataitem = new DataItem(techform, Const.CHECKBOX, "item.upcase");
        dataitem.setModelItem(documents.getModel("DATAELEMENT").
                getModelItem("UPCASE"));
        dataitem.setValue(upcase);
        dataitem.setEnabled((mode == Common.SHOW)? false : true);
        
        if (mode != Common.SHOW) {
            new Button(container, "detailsupdate");
            view.setFocus("reference.model");
        }
        
        view.addContainer(container);
        view.setNavbarActionEnabled("back", true);
        view.setTitle("item-detail-editor");
    }

    /**
     * 
     * @param view
     */
    public static final void select(ViewData view) {
        DataForm form = view.getElement("header");
        String itemname, modelref, itemref, upcase, classfield, sh, modelname =
                form.get("modelname").getValue();
        TableItem selected = null;
        Table itens = view.getElement("itens");
        byte mode = Common.getMode(view);
        
        for (TableItem item : itens.getItens()) {
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
        
        itemname = Common.getTableValue(mode, selected, "item.name");
        modelref = Common.getTableValue(mode, selected,"model.reference");
        itemref = Common.getTableValue(mode, selected, "item.reference");
        upcase = Common.getTableValue(mode, selected, "item.upcase");
        classfield = Common.getTableValue(mode, selected, "item.classfield");
        sh = Common.getTableValue(mode, selected, "item.sh");
        
        view.setReloadableView(true);
        view.export("mode", mode);
        view.export("model.name", modelname);
        view.export("item.name", itemname);
        view.export("model.reference", modelref);
        view.export("item.reference", itemref);
        view.export("item.upcase", upcase);
        view.export("item.classfield", classfield);
        view.export("item.sh", sh);
        view.redirect(null, "detailsview");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final boolean update(ViewData view, Function function)
            throws Exception {
        InputComponent input;
        DocumentModel model;
        DocumentModelItem modelitemref;
        boolean upcase; 
        DataForm form = view.getElement("fkform");
        String itemname = ((DataItem)form.get("item.name")).getValue();
        String shname, classfield, modelref = null, itemref = null;
        Shell shell = new Shell(function);
        ViewData structview = shell.getView(view, "tbstructure");
        Table itens = structview.getElement("itens");
        
        input = (DataItem)form.get("reference.model");
        if (!Shell.isInitial(input)) {
            modelref = input.getValue();
            model = new Documents(function).getModel(modelref);
            itemref = ((DataItem)form.get("reference.item")).getValue();
            modelitemref = model.getModelItem(itemref);
            
            if (modelitemref == null) {
                view.message(Const.ERROR, "reference.doesnt.exists");
                view.setFocus("reference.item");
                return false;
            }
                
            if (!model.isKey(modelitemref)) {
                view.message(Const.ERROR, "reference.isnot.key");
                view.setFocus("reference.item");
                return false;
            }
        }
        
        form = view.getElement("techform");
        shname = ((DataItem)form.get("item.sh")).getValue();
        upcase = ((DataItem)form.get("item.upcase")).isSelected();
        classfield = ((DataItem)form.get("item.classfield")).getValue();
        
        for (TableItem item : itens.getItens()) {
            input = (InputComponent)item.get("item.name");
            
            if (!input.getValue().equals(itemname))
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
