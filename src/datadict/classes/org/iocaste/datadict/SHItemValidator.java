package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.CustomContainer;

public class SHItemValidator extends AbstractValidator {
    private static final long serialVersionUID = 7361576769721130875L;
    
    public SHItemValidator(AbstractContext context) {
        super(context, "shitem");
    }
    
    private String composeName(String model, String item) {
        return new StringBuilder(model).append(".").append(item).toString();
    }
    
    @Override
    public final void validate() throws Exception {
        ExtendedObject object;
        String modelname, value;
        String itemname = getInput().getst();
        CustomContainer custom = getElement("itens");
        ExtendedObject[] objects = custom.get("objects");
        
        object = getObject(objects, "ITEM", itemname);
        if (object == null)
            return;

        modelname = object.getst("MODEL");
        value = composeName(modelname, itemname);
        if (new Documents(getContext().function).
                getObject("MODELITEM", value) == null)
            message("invalid.model.item");
  }

}
