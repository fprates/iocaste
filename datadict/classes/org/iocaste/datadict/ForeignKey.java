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
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class ForeignKey {

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
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "fkform");
        byte mode = Common.getMode(view);
        DocumentModel modelitem = new Documents(function).getModel("MODELITEM");
        
        dataitem = new DataItem(form, Const.TEXT_FIELD, "model.name");
        dataitem.setValue(modelname);
        dataitem.setModelItem(modelitem.getModelItem("MODEL"));
        dataitem.setEnabled(false);
        
        dataitem = new DataItem(form, Const.TEXT_FIELD, "item.name");
        dataitem.setValue(itemname);
        dataitem.setModelItem(modelitem.getModelItem("NAME"));
        dataitem.setEnabled(false);
        
        dataitem = new DataItem(form, Const.TEXT_FIELD, "reference.model");
        dataitem.setValue(modelref);
        dataitem.setModelItem(modelitem.getModelItem("MODEL"));
        dataitem.setEnabled((mode == Common.SHOW)? false : true);
        
        dataitem = new DataItem(form, Const.TEXT_FIELD, "reference.item");
        dataitem.setValue(itemref);
        dataitem.setModelItem(modelitem.getModelItem("NAME"));
        dataitem.setEnabled((mode == Common.SHOW)? false : true);
        
        if (mode != Common.SHOW) {
            new Button(container, "fkupdate");
            view.setFocus("reference.model");
        }
        
        view.addContainer(container);
        view.setNavbarActionEnabled("back", true);
        view.setTitle("fk-editor");
    }

    /**
     * 
     * @param view
     */
    public static final void update(ViewData view) {
        DataForm form = view.getElement("header");
        String itemname, modelref, itemref, modelname =
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
                selected = item;
                break;
            }
        }
        
        if (selected == null) {
            view.message(Const.ERROR, "choose.one.item");
            return;
        }
        
        itemname = ((InputComponent)selected.get("item.name")).getValue();
        modelref = ((InputComponent)selected.get("model.reference")).getValue();
        itemref = ((InputComponent)selected.get("item.reference")).getValue();
        
        view.setReloadableView(true);
        view.export("mode", mode);
        view.export("model.name", modelname);
        view.export("item.name", itemname);
        view.export("model.reference", modelref);
        view.export("item.reference", itemref);
        view.redirect(null, "fkstructure");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final boolean updateReference(ViewData view,
            Function function) throws Exception {
        InputComponent input;
        DataForm form = view.getElement("fkform");
        String itemname = ((DataItem)form.get("item.name")).getValue();
        String itemref = ((DataItem)form.get("reference.item")).getValue();
        String modelref = ((DataItem)form.get("reference.model")).getValue();
        Shell shell = new Shell(function);
        ViewData structview = shell.getView(view, "tbstructure");
        Table itens = structview.getElement("itens");
        DocumentModel model = new Documents(function).
                getModel(modelref);
        
        if (!model.isKey(model.getModelItem(itemref))) {
            view.message(Const.ERROR, "reference.isnot.key");
            return false;
        }
            
        for (TableItem item : itens.getItens()) {
            input = (InputComponent)item.get("item.name");
            
            if (!input.getValue().equals(itemname))
                continue;
            
            ((InputComponent)item.get("item.reference")).setValue(itemref);
            ((InputComponent)item.get("model.reference")).setValue(modelref);
            
            break;
        }
        
        shell.updateView(structview);
        view.message(Const.STATUS, "reference.updated");
        
        return true;
    }
}
