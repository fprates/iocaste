package org.iocaste.workbench.project.compile;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.utils.XMLElement;

public class ViewConfigFile extends AbstractConfigFile {
    private static final String[][] SPEC_ITEM = {
        {"name", "NAME"},
        {"parent", "PARENT"},
        {"type", "TYPE"}
    };
    
    public ViewConfigFile(PageBuilderContext context) {
        super(context, "views");
        directory = "META-INF";
        file = "views.xml";
    }

    @Override
    public void run(CompileData data) {
        String value;
        XMLElement view, spec, specitem, specitemattrib;
        ComplexDocument[] documents;
        
        documents = data.extcontext.project.getDocuments("screen");
        for (ComplexDocument document : documents) {
            view = new XMLElement("view");
            view.add("name", document.getstKey());
            root.addChild(view);
            spec = new XMLElement("spec");
            view.addChild(spec);
            for (ExtendedObject object : document.getItems("spec")) {
                specitem = new XMLElement("item");
                spec.addChild(specitem);
                for (int i = 0; i < SPEC_ITEM.length; i++) {
                    value = object.getst(SPEC_ITEM[i][1]);
                    specitemattrib = new XMLElement(SPEC_ITEM[i][0]);
                    specitemattrib.addInner((value == null)? "" : value);
                    specitem.addChild(specitemattrib);
                }
            }
        }
    }
}
