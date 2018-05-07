package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.tooldata.Text;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class DataItemRenderer extends AbstractElementRenderer<InputComponent> {
    
    public DataItemRenderer(HtmlRenderer renderers) {
        super(renderers, Const.DATA_ITEM);
    }

    @Override
    protected final XMLElement execute(InputComponent dataitem, Config config) {
        return null;
    }
    
    public final void execute(DataItem dataitem,
            XMLElement formtag, Config config) throws Exception {
        Text colname;
        DocumentModelItem modelitem;
        XMLElement labeltag, itemtag;
        String inputname, text, labelname;
        DocumentModel model = null;
        DataForm form = (DataForm)dataitem.getContainer();

        inputname = dataitem.getName();
        text = dataitem.getLabel();
        if (text == null)
            text = inputname;

        labeltag = new XMLElement("div");
        /*
         * that's a shame. for some reason, <label> messes with onclick
         * events of elements inside it. need to put the context button
         * out of <label>. while not reimplemented, replace <label> for
         * <div> temporarily.
         */
//        labeltag = new XMLElement("label");
//        labeltag.add("for", dataitem.getHtmlName());
        if (!dataitem.hasPlaceHolder()) {
            config.viewctx.instance(TYPES.TEXT,
                    labelname = inputname.concat("_form_item_text"));
            colname = new Text(config.viewctx, labelname);
            colname.setTag("span");
            colname.setText(text);
            colname.addAttribute("style", "font-weight:bold");
            labeltag.addChild(get(Const.TEXT).run(colname, config));
        }
        
        modelitem = dataitem.getModelItem();
        if (modelitem != null)
            model = modelitem.getDocumentModel();
        
        if (model != null && form.isKeyRequired() &&
                model.isKey(dataitem.getModelItem()))
            dataitem.setObligatory(true);
        
        labeltag.addChild(
                get(dataitem.getComponentType()).run(dataitem, config));
        itemtag = new XMLElement("li");
        itemtag.add("class", dataitem.getStyleClass("cell"));
        itemtag.addChild(labeltag);
        formtag.addChild(itemtag);
    }
}
