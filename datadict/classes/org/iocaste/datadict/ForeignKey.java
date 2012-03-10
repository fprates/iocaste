package org.iocaste.datadict;

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
        String itemname = (String)view.getParameter("item.name");
        String modelname = (String)view.getParameter("model.name");
        String itemref = (String)view.getParameter("item.reference");
        String modelref = (String)view.getParameter("model.reference");
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "fkform");
        byte mode = Common.getMode(view);
        
        dataitem = new DataItem(form, Const.TEXT_FIELD, "item.name");
        dataitem.setValue(itemname);
        dataitem.setEnabled(false);
            
        dataitem = new DataItem(form, Const.TEXT_FIELD, "model.name");
        dataitem.setValue(modelname);
        dataitem.setEnabled(false);
        
        dataitem = new DataItem(form, Const.TEXT_FIELD, "reference.item");
        dataitem.setValue(itemref);
        dataitem.setEnabled((mode == Common.SHOW)? false : true);
        
        dataitem = new DataItem(form, Const.TEXT_FIELD, "reference.model");
        dataitem.setValue(modelref);
        dataitem.setEnabled((mode == Common.SHOW)? false : true);
        
        new Button(container, "fkupdate");
        
        view.addContainer(container);
        view.setNavbarActionEnabled("back", true);
        view.setTitle("fk-editor");
    }

    /**
     * 
     * @param view
     */
    public static final void update(ViewData view) {
        DataForm form = (DataForm)view.getElement("header");
        String itemname, modelref, itemref, modelname =
                form.get("modelname").getValue();
        TableItem selected = null;
        Table itens = (Table)view.getElement("itens");
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
    public static final void updateReference(ViewData view, Function function)
            throws Exception {
        InputComponent input;
        DataForm form = (DataForm)view.getElement("fkform");
        String itemname = ((DataItem)form.get("item.name")).getValue();
        String itemref = ((DataItem)form.get("reference.item")).getValue();
        String modelref = ((DataItem)form.get("reference.model")).getValue();
        Shell shell = new Shell(function);
        ViewData structview = shell.getView(null, "tbstructure");
        Table itens = (Table)structview.getElement("itens");
        
        for (TableItem item : itens.getItens()) {
            input = (InputComponent)item.get("item.name");
            
            if (!input.getValue().equals(itemname))
                continue;
            
            ((InputComponent)item.get("item.reference")).setValue(itemref);
            ((InputComponent)item.get("model.reference")).setValue(modelref);
            
            break;
        }
        
        shell.updateView(structview);
    }
}
