package org.iocaste.workbench.project.compile;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.utils.XMLElement;

public class ViewConfigFile extends AbstractConfigFile {
    private static final String[][] VIEW = {
            {"name", "NAME"}
    };
    private static final String[][] SPEC_ITEM = {
        {"name", "NAME"},
        {"parent", "PARENT"},
        {"type", "TYPE"}
    };
    
    private static final String[][] CONFIG_ITEM = {
            {"name", "NAME"},
            {"value", "VALUE"},
            {"type", "TYPE"},
    };
    
    public ViewConfigFile(PageBuilderContext context) {
        super(context, "views");
        directory = "META-INF";
        file = "views.xml";
    }

    @Override
    public void run(CompileData data) {
        ExtendedObject viewhead;
        String value;
        XMLElement view, viewattrib, spec, item, itemattrib, config;
        ComplexDocument[] documents;
        Map<String, String> specitems;
        
        specitems = new HashMap<>();
        documents = data.extcontext.project.getDocuments("screen");
        for (ComplexDocument document : documents) {
            view = new XMLElement("view");
            root.addChild(view);
            viewhead = document.getHeader();
            for (int i = 0; i < VIEW.length; i++) {
                viewattrib = new XMLElement(VIEW[i][0]);
                viewattrib.addInner(viewhead.getst(VIEW[i][1]));
                view.addChild(viewattrib);
            }
            
            spec = new XMLElement("spec");
            view.addChild(spec);
            for (ExtendedObject object : document.getItems("spec")) {
                specitems.put(object.getst("ITEM_ID"), object.getst("NAME"));
                item = new XMLElement("item");
                spec.addChild(item);
                for (int i = 0; i < SPEC_ITEM.length; i++) {
                    value = object.getst(SPEC_ITEM[i][1]);
                    itemattrib = new XMLElement(SPEC_ITEM[i][0]);
                    itemattrib.addInner((value == null)? "" : value);
                    item.addChild(itemattrib);
                }
            }
            
            config = new XMLElement("config");
            view.addChild(config);
            for (ExtendedObject object : document.getItems("config")) {
                item = new XMLElement("item");
                config.addChild(item);
                itemattrib = new XMLElement("element");
                itemattrib.addInner(specitems.get(object.getst("SPEC")));
                item.addChild(itemattrib);
                
                for (int i = 0; i < CONFIG_ITEM.length; i++) {
                    itemattrib = new XMLElement(CONFIG_ITEM[i][0]);
                    itemattrib.addInner(
                            object.get(CONFIG_ITEM[i][1]).toString());
                    item.addChild(itemattrib);
                }
            }
            specitems.clear();
        }
    }
}
