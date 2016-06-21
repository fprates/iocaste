package org.iocaste.workbench.common.engine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.protocol.utils.ConversionRules;
import org.iocaste.protocol.utils.XMLConversion;
import org.iocaste.workbench.common.install.InstallModule;
import org.iocaste.workbench.common.install.LinkModule;
import org.iocaste.workbench.common.install.ModuleContext;
import org.iocaste.workbench.common.install.ProfileModule;

public class AutomatedInstall extends AbstractInstallObject {
    private static final String KEY = "install.models.model.items.item.key";
    private Function function;
    private String xml;
    private PageBuilderDefaultInstall defaultinstall;
    
    public AutomatedInstall(Function function,
            PageBuilderDefaultInstall defaultinstall) {
        ModuleContext modulectx;
        String pkgname = defaultinstall.getPackage();
        Iocaste iocaste = new Iocaste(function);
        byte[] buffer = iocaste.getMetaContext(pkgname, "install.txt");
        
        xml = new String(buffer);
        this.function = function;
        this.defaultinstall = defaultinstall;

        
        modulectx = new ModuleContext(function);
        modulectx.context = null;
        modulectx.defaultinstall = defaultinstall;
        new LinkModule(modulectx);
        new ProfileModule(modulectx);

        install(modulectx);
    }
    
    private final void installAuthorizations(
            List<ConversionResult> authorizations, InstallData data) {
        String name, object, action, appname;
        Authorization authorization;
        
        for (ConversionResult map : authorizations) {
            name = map.getst("install.authorizations.authorization.name");
            object = map.getst("install.authorizations.authorization.object");
            action = map.getst("install.authorizations.authorization.action");
            appname = map.getst("install.authorizations.authorization.appname");
            authorization = new Authorization(name);
            authorization.setObject(object);
            authorization.setAction(action);
            authorization.add("APPNAME", appname);
            data.add(authorization);
        }
    }
    
    private final Map<String, DataElement> installElements(
            List<ConversionResult> elements, InstallData data) {
        String name, type;
        DataElement element;
        int length;
        Object upcase;
        Map<String, DataElement> elements_ = new HashMap<>();
        
        for (ConversionResult map : elements) {
            name = map.getst("install.elements.element.name");
            type = map.getst("install.elements.element.type");
            length = map.geti("install.elements.element.length");
            upcase = map.get("install.elements.element.upcase");
            
            element = new DataElement(name);
            switch (type) {
            case "char":
                element.setType(DataType.CHAR);
                break;
            case "numc":
                element.setType(DataType.NUMC);
                break;
            case "dec":
                element.setType(DataType.DEC);
                break;
            case "date":
                element.setType(DataType.DATE);
                break;
            }
            element.setLength(length);
            if (upcase != null)
                element.setUpcase((boolean)upcase);
            elements_.put(name, element);
        }
        
        return elements_;
    }
    
    private final void installGroups(List<ConversionResult> groups,
            ConversionResult converted, InstallData data) {
        TaskGroup taskgroup;
        String name;
        List<ConversionResult> links;
        
        for (ConversionResult map : groups) {
            name = map.getst("install.tasksgroups.tasksgroup.name");
            taskgroup = new TaskGroup(name);
            data.add(taskgroup);
            
            links = map.getList("install.tasksgroups.tasksgroup.links");
            for (ConversionResult link : links) {
                name = link.getst("install.tasksgroups.tasksgroup.links.link");
                taskgroup.add(name);
            }
        }
    }
    
    private final void installModels(List<ConversionResult> models,
            Map<String, DataElement> elements, InstallData data) {
        DocumentModel model;
        DocumentModelItem item;
        boolean key;
        String name, table, field, element;
        List<ConversionResult> items;
        
        for (ConversionResult map : models) {
            name = map.getst("install.models.model.name");
            table = map.getst("install.models.model.table");
            model = data.getModel(name, table, null);
            
            items = map.getList("install.models.model.items");
            for (ConversionResult item_ : items) {
                name = item_.getst("install.models.model.items.item.name");
                field = item_.getst("install.models.model.items.item.field");
                element = item_.getst("install.models.model.items.item.element");
                
                item = new DocumentModelItem(name);
                item.setTableFieldName(field);
                item.setDataElement(elements.get(element));
                if (item_.contains(KEY)) {
                    key = item_.getbl(KEY);
                    if (key)
                        model.add(new DocumentModelKey(item));
                }
                model.add(item);
            }
        }
    }

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        Map<String, DataElement> elements;
        ModuleContext modulectx = new ModuleContext(function);
        
        modulectx.context = context;
        modulectx.defaultinstall = defaultinstall;
        
//        mapping.setType("install.elements.element.length", int.class);
//        mapping.setType("install.elements.element.upcase", boolean.class);
//        mapping.setType("install.models.model.items.item.key", boolean.class);
//        mapping.addItems("install.authorizations", "authorization");
//        mapping.addItems("install.links", "link");
//        mapping.addItems("install.tasksgroups", "tasksgroup");
//        mapping.addItems("install.tasksgroups.tasksgroup.links", "link");
//        mapping.addItems("install.elements", "element");
//        mapping.addItems("install.models", "model");
//        mapping.addItems("install.models.model.items", "item");
//        
//        elements = null;
//        for (String key : converted.keySet()) {
//            switch (key) {
//            case "install.authorizations":
//                values =  converted.getList(key);
//                installAuthorizations(values, data);
//                break;
//            case "install.links":
//                values =  converted.getList(key);
//                installLinks(values, data);
//                break;
//            case "install.tasksgroups":
//                values =  converted.getList(key);
//                installGroups(values, converted, data);
//                break;
//            case "install.elements":
//                values =  converted.getList(key);
//                elements = installElements(values, data);
//                break;
//            case "install.models":
//                values =  converted.getList(key);
//                installModels(values, elements, data);
//                break;
//            }
//        }
    }
    
    private void install(ModuleContext modulectx) {
        ConversionResult converted;
        InstallModule module;
        
        converted = modulectx.conversion.conversion(xml, modulectx.mapping);
        for (String key : converted.keySet()) {
            module = modulectx.modules.get(key);
            if (module == null)
                continue;
            module.setConverted(converted);
            module.run();
        }
    }
}
