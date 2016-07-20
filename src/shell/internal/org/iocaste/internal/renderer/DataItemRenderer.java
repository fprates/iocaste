package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class DataItemRenderer extends AbstractElementRenderer<DataItem> {
    
    public DataItemRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.DATA_ITEM);
    }

    @Override
    protected final XMLElement execute(DataItem dataitem, Config config) {
        return null;
    }
    
    public final XMLElement execute(
            DataItem dataitem, XMLElement itemtag, Config config) {
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
            coltag.addChild(get(Const.TEXT).run(colname, config));
            
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
        coltag.addChild(
                get(dataitem.getComponentType()).run(dataitem, config));
        itemtag.addChild(coltag);
        return null;
    }
}
