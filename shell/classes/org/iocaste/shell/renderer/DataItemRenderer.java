package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Text;

public class DataItemRenderer {

    public static final XMLElement render(DataItem dataitem, Config config) {
        Text colname;
        List<XMLElement> coltags;
        DocumentModelItem modelitem;
        InputComponent input;
        DocumentModel model = null;
        DataForm form = (DataForm)dataitem.getContainer();
        XMLElement coltag, itemtag = new XMLElement("tr");
        String inputname = dataitem.getName();
        
        colname = new Text(null, inputname);
        colname.setStyleClass("item_form_name");
        colname.setText(inputname);
        
        coltag = new XMLElement("td");
        coltag.add("class", dataitem.getStyleClass());
        coltag.addChild(TextRenderer.render(colname, config));
        
        modelitem = dataitem.getModelItem();
        if (modelitem != null)
            model = modelitem.getDocumentModel();
        
        if (model != null && form.isKeyRequired() &&
                model.isKey(dataitem.getModelItem()))
            dataitem.setObligatory(true);
        
        itemtag.addChild(coltag);
        
        coltag = new XMLElement("td");
        coltag.add("class", dataitem.getStyleClass());
        
        coltags = new ArrayList<XMLElement>();

        input = Shell.copyInputItem(null, dataitem, inputname, dataitem.
                getValues());
        Renderer.renderElement(coltags, input, config);
        
        coltag.addChildren(coltags);
        itemtag.addChild(coltag);
        
        return itemtag;
    }
}
