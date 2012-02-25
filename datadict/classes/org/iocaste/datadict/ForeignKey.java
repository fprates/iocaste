package org.iocaste.datadict;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class ForeignKey {

    public static final void main(ViewData view) {
        TableItem selected = null;
        Table itens = (Table)view.getElement("itens");
        
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
            
        view.setReloadableView(true);
        view.export("item", selected);
        view.redirect(null, "fkstructure");
    }
}
