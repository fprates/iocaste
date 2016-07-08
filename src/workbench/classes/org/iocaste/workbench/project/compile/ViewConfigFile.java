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

    @Override
    public void run(CompileData data) {
        ExtendedObject viewhead, spechd;
        String value;
        XMLElement view, viewattrib, spec, specitem, itemattrib, config, action;
        XMLElement item, subitems;
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
                specitem = new XMLElement("item");
                spec.addChild(specitem);
                for (int i = 0; i < SPEC_ITEM.length; i++) {
                    value = spechd.getst(SPEC_ITEM[i][1]);
                    itemattrib = new XMLElement(SPEC_ITEM[i][0]);
                    itemattrib.addInner((value == null)? "" : value);
                    specitem.addChild(itemattrib);
                }
                
                config = new XMLElement("config");
                specitem.addChild(config);
                for (ExtendedObject configobj : specdoc.getItems("config")) {
                    item = new XMLElement("item");
                    config.addChild(item);
                    for (int i = 0; i < CONFIG_ITEM.length; i++) {
                        itemattrib = new XMLElement(CONFIG_ITEM[i][0]);
                        itemattrib.addInner(
                                configobj.get(CONFIG_ITEM[i][1]).toString());
                        item.addChild(itemattrib);
                    }
                }
                
                subitems = new XMLElement("subitems");
                specitem.addChild(subitems);
                for (ExtendedObject subitem : specdoc.getItems("tool_item")) {
                    item = new XMLElement("item");
                    subitems.addChild(item);
                    for (int i = 0; i < TOOL_ITEM.length; i++) {
                        itemattrib = new XMLElement(TOOL_ITEM[i][0]);
                        itemattrib.addInner(
                                subitem.get(TOOL_ITEM[i][1]).toString());
                        item.addChild(itemattrib);
                    }
                }
            }
            
            action = new XMLElement("action");
            view.addChild(action);
            for (ExtendedObject actionobj : document.getItems("action")) {
                item = new XMLElement("item");
                action.addChild(item);
                for (int i = 0; i < ACTION_ITEM.length; i++) {
                    itemattrib = new XMLElement(ACTION_ITEM[i][0]);
                    itemattrib.addInner(
                            actionobj.get(ACTION_ITEM[i][1]).toString());
                    item.addChild(itemattrib);
                }
            }
        }
    }
}
