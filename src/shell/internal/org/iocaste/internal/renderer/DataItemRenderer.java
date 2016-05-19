package org.iocaste.internal.renderer;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class DataItemRenderer {

    public static final void render(DataItem dataitem, XMLElement itemtag,
            Config config) {
        Const componenttype;
        View view;
        Text colname;
        DocumentModelItem modelitem;
        DocumentModel model = null;
        DataForm form = (DataForm)dataitem.getContainer();
        XMLElement coltag;
        String inputname, text;

        inputname = dataitem.getName();
        text = dataitem.getLabel();
        if (text == null)
            text = inputname;
        
        view = config.getView();
        if (!dataitem.hasPlaceHolder()) {
            colname = new Text(view, inputname.concat("_form_item_text"));
            colname.setStyleClass("item_form_name");
            colname.setText(text);
            
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
        }
        
        coltag = new XMLElement("td");
        coltag.add("class", dataitem.getStyleClass());
        componenttype = dataitem.getComponentType();
        switch (componenttype) {
        case CHECKBOX:
            coltag.addChild(CheckBoxRenderer.render(dataitem));
            break;
        case LIST_BOX:
            coltag.addChild(ListBoxRenderer.render(dataitem));
            break;
        case RANGE_FIELD:
            coltag.addChild(RangeFieldRenderer.render(dataitem, config));
            break;
        case TEXT_FIELD:
            coltag.addChild(TextFieldRenderer.render(dataitem, null, config));
            break;
        case FILE_ENTRY:
            coltag.addChild(FileEntryRenderer.render(dataitem));
            break;
        default:
            throw new RuntimeException(new StringBuilder("Component type ").
                                append(componenttype).append(" not supported.").
                                toString());
        }
        
        itemtag.addChild(coltag);
    }
}
