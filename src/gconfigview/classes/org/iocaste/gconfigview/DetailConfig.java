package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class DetailConfig extends AbstractViewConfig {
    private byte mode;
    
    public DetailConfig(byte mode) {
        this.mode = mode;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        InputComponent input;
        String name;
        int type;
        DataForm form;
        DocumentModel model;
        DocumentModelItem item;
        DataElement de;
        
        model = new DocumentModel("PACKAGE_CONFIG");
        extcontext = getExtendedContext();
        for (ExtendedObject object : extcontext.objects) {
            name = object.get("NAME");
            type = object.geti("TYPE");
            
            de = new DataElement(name);
            de.setType(type);
            de.setLength(256);
            item = new DocumentModelItem(name);
            item.setDataElement(de);
            model.add(item);
        }
        
        form = getElement("package.config");
        form.importModel(model);
        for (Element element : form.getElements()) {
            input = (InputComponent)element;
            
            switch (input.getDataElement().getType()) {
            case DataType.BOOLEAN:
                input = new DataItem(form, Const.CHECKBOX, input.getName());
                break;
            default:
                input = new DataItem(form, Const.TEXT_FIELD, input.getName());
                input.setLength(256);
                input.setVisibleLength(20);
                break;
            }
            
            input.setEnabled(mode == Context.EDIT);
        }
        
        getNavControl().setTitle(Context.TITLES[mode]);
        
    }

}
