package org.iocaste.datadict;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public class Save {

    public static final void main(Context context) {
        DocumentModelItem modelitem, reference;
        DataElement dataelement;
        String value, modelref, itemref, itemname;
        CheckBox upcase, key;
        Documents documents = new Documents(context.function);
        DataForm structure = context.view.getElement("header");
        Table itens = context.view.getElement("itens");
        
        if (Common.hasItemDuplicated(context.view))
            return;
        
        context.model = new DocumentModel(
                (String)structure.get("modelname").get());
        context.model.setClassName((String)structure.get("modelclass").get());
        context.model.setTableName((String)structure.get("modeltable").get());
        
        for (TableItem item : itens.getItems()) {
            itemname = Common.getTableValue(item, "item.name");
            
            value = Common.getTableValue(item, "item.element");
            if (value == null) {
                value = new StringBuilder(context.model.getName()).
                        append(".").append(itemname).toString();
                Common.setTableValue(item, "item.element", value);
            }
            
            dataelement = new DataElement(value);
            dataelement.setLength((Integer)Common.getTableValue(
                    item, "item.length"));
            
            dataelement.setDecimals((Integer)Common.getTableValue(
                    item, "item.dec"));
            
            dataelement.setType((Integer)Common.getTableValue(
                    item, "item.type"));
            
            upcase = item.get("item.upcase");
            dataelement.setUpcase(upcase.isSelected());
            
            modelitem = new DocumentModelItem(itemname);
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
            
            context.model.add(modelitem);
            
            key = item.get("item.key");
            if (!key.isSelected())
                continue;
            
            context.model.add(new DocumentModelKey(modelitem));
        }
        
        switch (context.mode) {
        case Common.UPDATE:
            documents.updateModel(context.model);
            
            break;
        case Common.CREATE:
            switch (documents.validate(context.model)) {
            case Documents.TABLE_ALREADY_ASSIGNED:
                context.view.message(Const.ERROR, "table.already.assigned");
                return;
            }
            
            documents.createModel(context.model);
            context.mode = Common.UPDATE;
            
            break;
        }
        
        documents.commit();
        
        context.view.message(Const.STATUS, "table.saved.successfully");
    }

}
