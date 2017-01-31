package org.iocaste.appbuilder.common.portal.tiles;

import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.documents.common.ExtendedObject;

public class PortalTileItemInput extends StandardViewInput {
    private PortalTileItemData data;
    
    public PortalTileItemInput() {
        this(null);
    }
    
    public PortalTileItemInput(PortalTileItemData data) {
        this.data = data;
    }
    
    @Override
    public void execute(PageBuilderContext context) {
        Object value;
        Set<String> show;
        boolean key = false;
        ExtendedObject object = tilesobjectget();
        
        super.execute(context);
        show = PortalTileItemData.showset(getExtendedContext(), data);
        for (String name : show) {
            if (((data != null) && (data.key != null) &&
                    (name.equals(data.key))) || ((data == null) && !key)) {
                linkadd("item", object, name);
                key = true;
            }   
            value = object.get(name);
            textset(name, (value == null)? "" : value.toString());
        }
        
        textset("item", "");
    }
}