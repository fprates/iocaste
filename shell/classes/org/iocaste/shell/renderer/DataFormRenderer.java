package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;

public class DataFormRenderer extends Renderer {
    
    /**
     * 
     * @param form
     * @param config
     * @return
     */
    public static final List<XMLElement> render(DataForm form, Config config) {
        Text text;
        String inputname;
        String styleclass;
        DataItem dataitem;
        TableItem tableitem;
        String tablename = new StringBuffer(form.getName()).append(".table").
                toString();
        Table table = new Table(null, tablename);
        DocumentModel model = form.getModel();
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        table.setHeader(false);
        new TableColumn(table, "name");
        new TableColumn(table, "field");
        
        for (Element element : form.getElements()) {
            if (element.isControlComponent())
                continue;
            
            if (element.getType() != Const.DATA_ITEM) {
                renderElement(tags, element, config);
                continue;
            }
            
            dataitem = (DataItem)element;
            inputname = dataitem.getName();
            styleclass = dataitem.getStyleClass();
            
            text = new Text(table, new StringBuffer(inputname).
                    append(".text").toString());
            text.setText(inputname);
            text.setStyleClass(styleclass);

            tableitem = new TableItem(table);
            tableitem.add(text);
            
            if (model != null && form.isKeyRequired() &&
                    model.isKey(dataitem.getModelItem()))
                dataitem.setObligatory(true);
            
            tableitem.add(Shell.createInputItem(table, dataitem, inputname));
        }
        
        renderContainer(tags, table, config);
        
        return tags;
    }
}
