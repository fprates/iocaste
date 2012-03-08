package org.iocaste.datadict;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
//import org.iocaste.sh.common.SHLib;
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
        DocumentModelItem modelitem;
        DocumentModelKey modelkey;
        DataElement dataelement;
        String itemname;
        CheckBox upcase, key;
        Documents documents = new Documents(function);
        DataForm structure = (DataForm)view.getElement("structure.form");
        Table itens = (Table)view.getElement("itens");
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
            dataelement.setName(new StringBuilder(model.getName()).append(".").
                    append(itemname).toString());
            dataelement.setLength(Integer.parseInt(Common.getTableValue(
                    modo, item, "item.length")));
            dataelement.setType(Integer.parseInt(Common.getTableValue(
                    modo, item, "item.type")));
            upcase = (CheckBox)item.get("item.upcase");
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
            
            model.add(modelitem);
            
            key = (CheckBox)item.get("item.key");
            if (!key.isSelected())
                continue;
        
            modelkey = new DocumentModelKey();
            modelkey.setModel(model);
            modelkey.setModelItem(itemname);
            
            model.addKey(modelkey);
        }
        
        switch (modo) {
        case Common.UPDATE:
            documents.updateModel(model);
            
            break;
        case Common.CREATE:
            documents.createModel(model);
            view.export("model", model);
            view.export("mode", Common.UPDATE);
            view.setReloadableView(true);
            
            break;
        }
        
        documents.commit();
        
        view.message(Const.STATUS, "table.saved.successfully");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void shitem(ViewData view, Function function)
            throws Exception {
        List<ExtendedObject> oitens;
//        SHLib shlib = new SHLib(function);
        DataForm header = (DataForm)view.getElement("header");
        ExtendedObject oitem, object = header.getObject();
        Table itens = (Table)view.getElement("itens");
        
        oitens = new ArrayList<ExtendedObject>();
        for (TableItem item : itens.getItens()) {
            oitem = item.getObject();
            oitem.setValue("SH_NAME", object.getValue("NAME"));
            oitens.add(oitem);
        }
        
//        shlib.save(object, oitens.toArray(new ExtendedObject[0]));
        
        view.message(Const.STATUS, "search.help.saved.successfully");
    }

}
