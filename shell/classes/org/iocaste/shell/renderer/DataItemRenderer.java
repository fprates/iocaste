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

    public static final void render(DataItem dataitem, XMLElement itemtag,
            Config config) {
        Text colname;
        List<XMLElement> coltags;
        DocumentModelItem modelitem;
        InputComponent input;
        DocumentModel model = null;
        DataForm form = (DataForm)dataitem.getContainer();
        XMLElement coltag;
        String inputname = dataitem.getName();
        
        colname = new Text(null, "");
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

        input = Shell.copyInputItem(null, dataitem, inputname, dataitem.
                getValues());
        input.setText(dataitem.getText());
        
        coltags = new ArrayList<XMLElement>();
        Renderer.renderElement(coltags, input, config);
        
        coltag.addChildren(coltags);
        itemtag.addChild(coltag);
    }
}
