package org.iocaste.datadict;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;

public class TableStructure {

    public static final void main(Context context) {
        Table itens;
        String name, title;
        TableColumn column;
        Form main = new Form(context.view, "datadict.structure");
        PageControl pagecontrol = new PageControl(main);
        DataForm structure = new DataForm(main, "header");
        Map<Common.ItensNames, DataElement> references =
                Common.getFieldReferences(context.function);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        new DataItem(structure, Const.TEXT_FIELD, "modelname");
        context.view.setFocus(new DataItem(structure, Const.TEXT_FIELD,
                "modeltext"));
        new DataItem(structure, Const.TEXT_FIELD, "modelclass");
        new DataItem(structure, Const.TEXT_FIELD, "modeltable");
        
        prepareHeader(structure, context);
        
        new Button(main, "itemdetails");
        
        itens = new Table(main, "itens");
        
        for (Common.ItensNames itemname : Common.ItensNames.values()) {
            column = new TableColumn(itens, itemname.getName());
            column.setDataElement(references.get(itemname));
            
            name = itemname.getName();
            if (!name.equals("item.classfield") &&
                    !name.equals("item.reference") &&
                    !name.equals("model.reference") &&
                    !name.equals("item.upcase") &&
                    !name.equals("item.sh"))
                continue;
            
            column.setVisible(false);
        }
        
        itens.setMark(true);
        prepareItens(itens, context);
        
        switch (context.mode) {
        case Common.UPDATE:
            itens.setMark(true);
            title = "datadict-update";
            new Button(main, "save");
            new Button(main, "add");
            new Button(main, "deleteitem");
            
            break;
        
        case Common.SHOW:
            itens.setMark(true);
            title = "datadict-view";
            new Button(main, "generateclass");
            
            break;
        
        case Common.CREATE:
            itens.setMark(true);
            title = "datadict-create";
            new Button(main, "save");
            new Button(main, "add");
            new Button(main, "deleteitem");
            
            break;
            
        default:
            title = null;
        }
        
        context.view.setTitle(title);
    }
    
    private static final void prepareHeader(DataForm form, Context context) {
        String name;
        DataItem dataitem;
        DataElement[] references = new DataElement[3];
        Documents docs = new Documents(context.function);
        DocumentModel model = docs.getModel(context.modelname);
        
        references[Common.MODELNAME] = docs.getDataElement("MODEL.NAME");
        references[Common.MODELTABLE] = docs.getDataElement("MODEL.TABLE");
        references[Common.MODELCLASS] = docs.getDataElement("MODEL.CLASS");
        
        for (Element element : form.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            dataitem = (DataItem)element;
            name = dataitem.getName();
            
            if (name.equals("modelname")) {
                dataitem.setObligatory(false);
                dataitem.set(context.modelname);
                dataitem.setEnabled(false);
                dataitem.setDataElement(references[Common.MODELNAME]);
                
                continue;
            }
            
            if (name.equals("modeltable")) {
                dataitem.setEnabled(context.mode == Common.CREATE);
                dataitem.setObligatory(context.mode == Common.CREATE);
                dataitem.setDataElement(references[Common.MODELTABLE]);
                
                if (model == null)
                    continue;
                
                dataitem.set(model.getTableName());
                continue;
            }
            
            if (name.equals("modelclass")) {
                dataitem.setEnabled(!(context.mode == Common.SHOW));
                dataitem.setObligatory(false);
                dataitem.setDataElement(references[Common.MODELCLASS]);
                
                if (model == null)
                    continue;
                
                dataitem.set(model.getClassName());
                continue;
            }
        }
    }
    
    private static final void prepareItens(Table itens, Context context) {
        Map<Common.ItensNames, DataElement> references =
                Common.getFieldReferences(context.function);
        Documents documents = new Documents(context.function);
        DocumentModel model = documents.getModel("MODELITEM");
        DocumentModel usermodel = documents.getModel(context.modelname);
        ItemConfig itemconfig = new ItemConfig();

        itemconfig.setTable(itens);
        itemconfig.setMode(context.mode);
        itemconfig.setModel(model);
        itemconfig.setReferences(references);
        
        if (usermodel == null)
            Common.insertItem(itemconfig);
        else
            for (DocumentModelItem modelitem : usermodel.getItens()) {
                itemconfig.setModelItem(modelitem);
                Common.insertItem(itemconfig);
            }
    }
}
