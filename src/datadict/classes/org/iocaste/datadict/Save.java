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
//        DocumentModelItem modelitem, reference;
//        DataElement dataelement;
//        String value, modelref, itemref, itemname;
//        CheckBox upcase, key;
//        Documents documents = new Documents(context.function);
//        DataForm structure = context.view.getElement("header");
//        Table itens = context.view.getElement("itens");
//        
//        if (Common.hasItemDuplicated(context.view))
//            return;
//        
//        context.model = new DocumentModel(
//                (String)structure.get("modelname").get());
//        context.model.setClassName((String)structure.get("modelclass").get());
//        context.model.setTableName((String)structure.get("modeltable").get());
//        
//        for (TableItem item : itens.getItems()) {
//            itemname = Common.getTableInput(item, "item.name").get();
//            
//            value = Common.getTableInput(item, "item.element").get();
//            if (value == null) {
//                value = new StringBuilder(context.model.getName()).
//                        append(".").append(itemname).toString();
//                Common.setTableValue(item, "item.element", value);
//            }
//            
//            dataelement = new DataElement(value);
//            dataelement.setLength(Common.
//                    getTableInput(item, "item.length").geti());
//            
//            dataelement.setDecimals(Common.getTableInput(
//                    item, "item.dec").geti());
//            
//            dataelement.setType(Common.getTableInput(
//                    item, "item.type").geti());
//            
//            upcase = item.get("item.upcase");
//            dataelement.setUpcase(upcase.isSelected());
//            
//            modelitem = new DocumentModelItem(itemname);
//            modelitem.setTableFieldName((String)Common.getTableInput(
//                    item, "item.tablefield").get());
//            
//            modelitem.setAttributeName((String)Common.getTableInput(
//                    item, "item.classfield").get());
//            
//            modelitem.setDataElement(dataelement);
//            
//            modelref = Common.getTableInput(item, "model.reference").get();
//            if (!Shell.isInitial(modelref)) {
//                itemref = Common.getTableInput(item, "item.reference").get();
//                reference = documents.getModel(modelref).getModelItem(itemref);
//                modelitem.setReference(reference);
//            }
//            
//            modelitem.setSearchHelp((String)Common.
//                    getTableInput(item, "item.sh").get());
//            
//            context.model.add(modelitem);
//            
//            key = item.get("item.key");
//            if (!key.isSelected())
//                continue;
//            
//            context.model.add(new DocumentModelKey(modelitem));
//        }
//        
//        switch (context.mode) {
//        case Common.UPDATE:
//            documents.updateModel(context.model);
//            
//            break;
//        case Common.CREATE:
//            switch (documents.validate(context.model)) {
//            case Documents.TABLE_ALREADY_ASSIGNED:
//                context.view.message(Const.ERROR, "table.already.assigned");
//                return;
//            }
//            
//            documents.createModel(context.model);
//            context.mode = Common.UPDATE;
//            
//            break;
//        }
//        
//        documents.commit();
//        
//        context.view.message(Const.STATUS, "table.saved.successfully");
    }

}
