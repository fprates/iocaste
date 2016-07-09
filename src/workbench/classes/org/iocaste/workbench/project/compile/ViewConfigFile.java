package org.iocaste.workbench.project.compile;

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
    
    private static final String[][] TOOL_ITEM = {
            {"name", "NAME"},
            {"disabled", "DISABLED"},
            {"invisible", "INVISIBLE"},
            {"vlength", "VLENGTH"},
            {"length", "LENGTH"},
            {"required", "REQUIRED"},
            {"focus", "FOCUS"}
    };
    
    private static final String[][] ACTION_ITEM = {
            {"name", "NAME"},
            {"class", "CLASS"},
            {"type", "TYPE"},
    };
    
    public ViewConfigFile(PageBuilderContext context) {
        super(context, "views");
        directory = "META-INF";
        file = "views.xml";
    }

    private final XMLElement add(
            XMLElement parent, String[][] items, ExtendedObject object) {
        XMLElement item, attrib;
        String value;
        
        item = new XMLElement("item");
        parent.addChild(item);
        for (int i = 0; i < items.length; i++) {
            value = object.getst(items[i][1]);
            attrib = new XMLElement(items[i][0]);
            attrib.addInner((value == null)? "" : value);
            item.addChild(attrib);
        }
        
        return item;
    }
    
    @Override
    public final void run(CompileData data) {
        ExtendedObject viewhead, spechd;
        XMLElement view, viewattrib, spec, specitem, config, action, subitems;
        ComplexDocument[] documents;
        
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
            for (ComplexDocument specdoc : document.getDocuments("spec")) {
                spechd = specdoc.getHeader();
                specitem = add(spec, SPEC_ITEM, spechd);
                
                config = new XMLElement("config");
                specitem.addChild(config);
                for (ExtendedObject configobj : specdoc.getItems("config"))
                    add(config, CONFIG_ITEM, configobj);
                
                subitems = new XMLElement("subitems");
                specitem.addChild(subitems);
                for (ExtendedObject subitem : specdoc.getItems("tool_item"))
                    add(subitems, TOOL_ITEM, subitem);
            }
            
            action = new XMLElement("action");
            view.addChild(action);
            for (ExtendedObject actionobj : document.getItems("action"))
                add(action, ACTION_ITEM, actionobj);
        }
    }
}
