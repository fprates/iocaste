package org.iocaste.workbench.project.compile;

import java.util.Locale;

import org.iocaste.appbuilder.common.PageBuilderContext;
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
        file = "install.txt";
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
    
    @Override
    public void run(CompileData data) {
        Query query;
        String name;
        ExtendedObject[] objects;
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
        
        query = new Query();
        query.setModel("WB_MODEL_HEADER");
        query.andEqual("PROJECT", data.project);
        objects = select(query);
        if (objects != null) {
            models = new XMLElement("models");
            root.addChild(models);
            itemInstall(models, objects, "model", MODEL, locale);
            
            name = null;
            for (XMLElement model : models.getChildren()) {
                for (XMLElement property : model.getChildren()) {
                    if (!property.getName().equals("name"))
                        continue;
                    name = property.getText();
                    break;
                }
                query = new Query();
                query.setModel("WB_MODEL_ITEMS");
                query.andEqual("MODEL", name);
                query.andEqual("PROJECT", data.project);
                objects = select(query);
                itemInstall(model, objects, "item", MODEL_ITEM, locale);
            }   
        }
    }

}
