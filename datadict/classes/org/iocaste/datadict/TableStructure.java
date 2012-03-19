package org.iocaste.datadict;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.ViewData;

public class TableStructure {
    
    /**
     * 
     * @param vdata
     * @return
     */
    private static final String getModelName(ViewData vdata) {
        byte mode = Common.getMode(vdata);
        DocumentModel model = vdata.getParameter("model");
        
        if (mode == Common.CREATE)
            return vdata.getParameter("modelname");
        else
            return model.getName();
    }

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        Table itens;
        String name, title, modelname;
        TableColumn column;
        byte mode = Common.getMode(view);
        Container main = new Form(null, "datadict.structure");
        DataForm structure = new DataForm(main, "header");
        Map<Common.ItensNames, DataElement> references =
                Common.getFieldReferences(function);
        
        modelname = getModelName(view);
        new DataItem(structure, Const.TEXT_FIELD, "modelname");
        new DataItem(structure, Const.TEXT_FIELD, "modeltext");
        new DataItem(structure, Const.TEXT_FIELD, "modelclass");
        new DataItem(structure, Const.TEXT_FIELD, "modeltable");
        
        prepareHeader(structure, modelname, mode, function);
        
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
        prepareItens(itens, mode, modelname, function, view);
        
        switch (mode) {
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
        
        view.setFocus("modeltext");
        view.setNavbarActionEnabled("back", true);
        view.setTitle(title);
        view.addContainer(main);
    }
    
    /**
     * 
     * @param form
     * @param modelname
     * @param model
     * @param mode
     * @param function
     * @throws Exception
     */
    private static final void prepareHeader(DataForm form, String modelname,
            byte mode, Function function) throws Exception {
        String name;
        DataItem dataitem;
        DataElement[] references = new DataElement[3];
        Documents docs = new Documents(function);
        DocumentModel model = docs.getModel("MODEL");
        
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
                dataitem.setValue(modelname);
                dataitem.setEnabled(false);
                dataitem.setDataElement(references[Common.MODELNAME]);
                
                continue;
            }
            
            if (name.equals("modeltable")) {
                dataitem.setEnabled((mode == Common.CREATE)? true : false);
                dataitem.setObligatory((mode == Common.CREATE)? true : false);
                dataitem.setDataElement(references[Common.MODELTABLE]);
                
                if (model == null)
                    continue;
                
                dataitem.setValue(model.getTableName());
                continue;
            }
            
            if (name.equals("modelclass")) {
                dataitem.setEnabled((mode == Common.SHOW)?false:true);
                dataitem.setObligatory(false);
                dataitem.setDataElement(references[Common.MODELCLASS]);
                
                if (model == null)
                    continue;
                
                dataitem.setValue(model.getClassName());
                continue;
            }
        }
    }
    
    /**
     * 
     * @param itens
     * @param mode
     * @param model
     * @param function
     * @throws Exception
     */
    private static final void prepareItens(Table itens, byte mode,
            String modelname, Function function, ViewData view)
                    throws Exception {
        Map<Common.ItensNames, DataElement> references =
                Common.getFieldReferences(function);
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("MODELITEM");
        DocumentModel usermodel = documents.getModel(modelname);
        ItemConfig itemconfig = new ItemConfig();

        itemconfig.setTable(itens);
        itemconfig.setMode(mode);
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
