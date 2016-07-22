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
    
    public final XMLElement execute(DataItem dataitem, XMLElement labellist,
            XMLElement valuelist, Config config) {
        View view;
        Text colname;
        DocumentModelItem modelitem;
        XMLElement coltag, labeltag;
        String inputname, text;
        DocumentModel model = null;
        DataForm form = (DataForm)dataitem.getContainer();

        inputname = dataitem.getName();
        text = dataitem.getLabel();
        if (text == null)
            text = inputname;
        
        view = config.getView();
        if (labellist != null) {
            colname = new Text(view, inputname.concat("_form_item_text"));
            colname.setText(text);
            colname.addEvent("style", "padding:10px");
            
            labeltag = new XMLElement("label");
            labeltag.add("for", dataitem.getHtmlName());
            labeltag.addChild(get(Const.TEXT).run(colname, config));
            
            coltag = new XMLElement("li");
            coltag.add("class", "item_form_name");
            coltag.addChild(labeltag);
            
            modelitem = dataitem.getModelItem();
            if (modelitem != null)
                model = modelitem.getDocumentModel();
            
            if (model != null && form.isKeyRequired() &&
                    model.isKey(dataitem.getModelItem()))
                dataitem.setObligatory(true);
            labellist.addChild(coltag);
        }
        
        coltag = new XMLElement("li");
        coltag.add("class", dataitem.getStyleClass());
        coltag.addChild(get(dataitem.getComponentType()).run(dataitem, config));
        valuelist.addChild(coltag);
        return null;
    }
}
