package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Text;

public class DataItemRenderer {

    public static final XMLElement render(DataItem dataitem, Config config) {
        Text text;
        List<XMLElement> coltags;
        DocumentModelItem modelitem;
        DocumentModel model = null;
        DataForm form = (DataForm)dataitem.getContainer();
        XMLElement coltag, itemtag = new XMLElement("tr");
        String inputname = dataitem.getName();
        String styleclass = dataitem.getStyleClass();
        
        text = new Text(form, new StringBuffer(inputname).
                append(".text").toString());
        text.setText(inputname);
        text.setStyleClass(styleclass);
        
        coltag = new XMLElement("td");
        coltag.addChild(TextRenderer.render(text, config));
        
        modelitem = dataitem.getModelItem();
        if (modelitem != null)
            model = modelitem.getDocumentModel();
        
        if (model != null && form.isKeyRequired() &&
                model.isKey(dataitem.getModelItem()))
            dataitem.setObligatory(true);
        
        itemtag.addChild(coltag);
        
        coltag = new XMLElement("td");
        coltags = new ArrayList<XMLElement>();
        
        Renderer.renderElement(coltags,
                Shell.createInputItem(null, dataitem, inputname), config);
        
        coltag.addChildren(coltags);
        itemtag.addChild(coltag);
        
        return itemtag;
    }
}
