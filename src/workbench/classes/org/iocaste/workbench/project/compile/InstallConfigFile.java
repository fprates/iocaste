package org.iocaste.workbench.project.compile;

import java.util.Locale;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Shell;

public class InstallConfigFile extends AbstractConfigFile {
    private static final String[][] LINK = {
            {"name", "NAME"},
            {"command", "COMMAND"},
            {"group", "GROUP"}
    };
    private static final String[][] DE = {
            {"name", "NAME"},
            {"type", "TYPE"},
            {"length", "SIZE"},
            {"decimals", "DECIMALS"},
            {"upcase", "UPCASE"}
    };
    private static final String[][] MODEL = {
            {"name", "NAME"},
            {"table", "TABLE"}
    };
    private static final String[][] MODEL_ITEM = {
            {"name", "NAME"},
            {"field", "FIELD"},
            {"key", "KEY"},
            {"element", "DATA_ELEMENT"}
    };
    
    public InstallConfigFile(PageBuilderContext context) {
        super(context, "install");
        directory = "META-INF";
        file = "install.xml";
    }

    private void itemInstall(XMLElement parent, ExtendedObject[] objects,
            String name, String[][] fields, Locale locale) {
        XMLElement line, item;
        Object value;
        DocumentModel model;
        DataElement de;
        
        for (ExtendedObject object : objects) {
            model = object.getModel();
            line = new XMLElement(name);
            for (int i = 0; i < fields.length; i++) {
                value = object.get(fields[i][1]);
                de = model.getModelItem(fields[i][1]).getDataElement();
                item = new XMLElement(fields[i][0]);
                item.addInner(Shell.toString(value, de, locale, false));
                line.addChild(item);
            }
            parent.addChild(line);
        }
    }
    
    private void itemInstall(XMLElement parent, Map<Object, ComplexDocument> documents,
            String name, String[][] fields, Locale locale) {
        XMLElement line, item, items;
        Object value;
        DocumentModel model;
        DataElement de;
        ExtendedObject object;
        ComplexDocument document;
        
        for (Object key : documents.keySet()) {
            document = documents.get(key);
            object = document.getHeader();
            model = object.getModel();
            line = new XMLElement(name);
            for (int i = 0; i < fields.length; i++) {
                value = object.get(fields[i][1]);
                de = model.getModelItem(fields[i][1]).getDataElement();
                item = new XMLElement(fields[i][0]);
                item.addInner(Shell.toString(value, de, locale, false));
                line.addChild(item);
            }
            parent.addChild(line);
            items = new XMLElement("items");
            line.addChild(items);
            itemInstall(items,
                    document.getItems("item"), "item", MODEL_ITEM, locale);
        }
    }
    
    @Override
    public void run(CompileData data) {
        Query query;
        ExtendedObject[] objects;
        Map<Object, ComplexDocument> documents;
        XMLElement profile, links, elements, models;
        Locale locale = data.context.view.getLocale();
        
        profile = new XMLElement("profile");
        profile.addInner(data.extcontext.project.getHeader().getst("PROFILE"));
        root.addChild(profile);
        
        objects = data.extcontext.project.getItems("link");
        if (objects != null) {
            links = new XMLElement("links");
            root.addChild(links);
            itemInstall(links, objects, "link", LINK, locale);
        }
        
        query = new Query();
        query.setModel("WB_DATA_ELEMENTS");
        query.andEqual("PROJECT", data.project);
        objects = select(query);
        if (objects != null) {
            elements = new XMLElement("elements");
            root.addChild(elements);
            itemInstall(elements, objects, "element", DE, locale);
        }

        documents = data.extcontext.project.getDocumentsMap("model");
        if (documents.size() > 0) {
            models = new XMLElement("models");
            root.addChild(models);
            itemInstall(models, documents, "model", MODEL, locale);
        }
    }
}
