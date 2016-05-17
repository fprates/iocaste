package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;

public class DetailConfig extends AbstractViewConfig {
    private byte mode;
    
    public DetailConfig(byte mode) {
        this.mode = mode;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        DataFormToolItem input;
        String name;
        int type;
        DataFormToolData form;
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
        
        form = getTool("package.config");
        form.model = model;
        for (DocumentModelItem mitem : model.getItens()) {
            input = form.itemInstance(mitem.getName());
            
            switch (mitem.getDataElement().getType()) {
            case DataType.BOOLEAN:
                input.componenttype = Const.CHECKBOX;
                break;
            default:
                input.length = 256;
                input.vlength = 20;
                break;
            }
            
            input.disabled = !(mode == Context.EDIT);
        }
        
        getNavControl().setTitle(Context.TITLES[mode]);
        
    }

}
