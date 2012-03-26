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
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Save {

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        DocumentModelItem modelitem, reference;
        DataElement dataelement;
        String modelref, itemref, itemname;
        CheckBox upcase, key;
        Documents documents = new Documents(function);
        DataForm structure = view.getElement("header");
        Table itens = view.getElement("itens");
        DocumentModel model = new DocumentModel();
        byte modo = Common.getMode(view);
        int i = 0;
        
        if (Common.hasItemDuplicated(view))
            return;
        
        model.setName(structure.get("modelname").getValue());
        model.setClassName(structure.get("modelclass").getValue());
        model.setTableName(structure.get("modeltable").getValue());
        
        for (TableItem item : itens.getItens()) {
            itemname = Common.getTableValue(modo, item, "item.name");
            
            dataelement = new DataElement();
            switch (modo) {
            case Common.CREATE:
                dataelement.setName(new StringBuilder(model.getName()).
                        append(".").
                        append(itemname).toString());
                break;
            case Common.UPDATE:
                dataelement.setName(Common.getTableValue(
                        modo, item, "item.element"));
                break;
            }
            
            dataelement.setLength(Integer.parseInt(Common.getTableValue(
                    modo, item, "item.length")));
            dataelement.setDecimals(Integer.parseInt(Common.getTableValue(
                    modo, item, "item.dec")));
            dataelement.setType(Integer.parseInt(Common.getTableValue(
                    modo, item, "item.type")));
            upcase = item.get("item.upcase");
            dataelement.setUpcase(upcase.isSelected());
            
            modelitem = new DocumentModelItem();
            modelitem.setIndex(i++);
            modelitem.setName(itemname);
            modelitem.setTableFieldName(Common.getTableValue(
                    modo, item, "item.tablefield"));
            modelitem.setAttributeName(Common.getTableValue(
                    modo, item, "item.classfield"));
            modelitem.setDataElement(dataelement);
            modelitem.setDocumentModel(model);
            
            modelref = Common.getTableValue(modo, item, "model.reference");
            if (modelref != null && !modelref.equals("")) {
                itemref = Common.getTableValue(modo, item, "item.reference");
                reference = documents.getModel(modelref).getModelItem(itemref);
                modelitem.setReference(reference);
            }
            
            modelitem.setSearchHelp(Common.
                    getTableValue(modo, item, "item.sh"));
            
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
