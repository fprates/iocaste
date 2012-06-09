package org.iocaste.datadict;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Save {

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(View view, Function function)
            throws Exception {
        DocumentModelItem modelitem, reference;
        DataElement dataelement;
        String value, modelref, itemref, itemname;
        CheckBox upcase, key;
        Documents documents = new Documents(function);
        DataForm structure = view.getElement("header");
        Table itens = view.getElement("itens");
        DocumentModel model = new DocumentModel();
        byte modo = Common.getMode(view);
        
        if (Common.hasItemDuplicated(view))
            return;
        
        model.setName((String)structure.get("modelname").get());
        model.setClassName((String)structure.get("modelclass").get());
        model.setTableName((String)structure.get("modeltable").get());
        
        for (TableItem item : itens.getItens()) {
            itemname = Common.getTableValue(item, "item.name");
            
            value = Common.getTableValue(item, "item.element");
            if (value == null) {
                value = new StringBuilder(model.getName()).
                        append(".").append(itemname).toString();
                Common.setTableValue(item, "item.element", value);
            }
            
            dataelement = new DataElement();
            dataelement.setName(value);
            dataelement.setLength((Integer)Common.getTableValue(
                    item, "item.length"));
            
            dataelement.setDecimals((Integer)Common.getTableValue(
                    item, "item.dec"));
            
            dataelement.setType((Integer)Common.getTableValue(
                    item, "item.type"));
            
            upcase = item.get("item.upcase");
            dataelement.setUpcase(upcase.isSelected());
            
            modelitem = new DocumentModelItem();
            modelitem.setName(itemname);
            modelitem.setTableFieldName((String)Common.getTableValue(
                    item, "item.tablefield"));
            
            modelitem.setAttributeName((String)Common.getTableValue(
                    item, "item.classfield"));
            
            modelitem.setDataElement(dataelement);
            
            modelref = Common.getTableValue(item, "model.reference");
            if (!Shell.isInitial(modelref)) {
                itemref = Common.getTableValue(item, "item.reference");
                reference = documents.getModel(modelref).getModelItem(itemref);
                modelitem.setReference(reference);
            }
            
            modelitem.setSearchHelp((String)Common.
                    getTableValue(item, "item.sh"));
            
            model.add(modelitem);
            
            key = item.get("item.key");
            if (!key.isSelected())
                continue;
            
            model.add(new DocumentModelKey(modelitem));
        }
        
        switch (modo) {
        case Common.UPDATE:
            documents.updateModel(model);
            
            break;
        case Common.CREATE:
            switch (documents.validate(model)) {
            case Documents.TABLE_ALREADY_ASSIGNED:
                view.message(Const.ERROR, "table.already.assigned");
                return;
            }
            
            documents.createModel(model);
            view.export("model", model);
            view.export("mode", Common.UPDATE);
            view.setReloadableView(true);
            
            break;
        }
        
        documents.commit();
        
        view.message(Const.STATUS, "table.saved.successfully");
    }

}
