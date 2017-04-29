package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.kernel.runtime.shell.elements.Text;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;

public class DataItemRenderer extends AbstractElementRenderer<DataItem> {
    
    public DataItemRenderer(HtmlRenderer renderer) {
        super(renderer, Const.DATA_ITEM);
    }

    @Override
    protected final XMLElement execute(DataItem dataitem, Config config) {
        return null;
    }
    
    public final void execute(
            DataItem dataitem, XMLElement formtag, Config config) {
    	ToolData tooldata;
        Text colname;
        DocumentModelItem modelitem;
        XMLElement labeltag, itemtag;
        String inputname, text;
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
        	tooldata = new ToolData(TYPES.TEXT);
        	tooldata.name = inputname.concat("_form_item_text");
            colname = new Text(config.viewctx, tooldata);
            colname.setText(text);
            colname.setTag("span");
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
        itemtag.add("class", dataitem.getStyleClass());
        itemtag.addChild(labeltag);
        formtag.addChild(itemtag);
    }
}
