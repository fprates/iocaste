package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.CustomContainer;

public class SHExportValidator extends AbstractValidator {
    private static final long serialVersionUID = 7361576769721130875L;
    
    public SHExportValidator(AbstractContext context) {
        super(context, "shexport");
    }
    
    private String composeName(String model, String item) {
        return new StringBuilder(model).append(".").append(item).toString();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractValidator#validate()
     */
    @Override
    public final void validate() throws Exception {
        String value, modelname;
        ExtendedObject object;
        String itemname = getInput().getst();
        CustomContainer table = getElement("itens");
        ExtendedObject[] objects = table.get("objects");
        
        object = getObject(objects, "EXPORT", itemname);
        if (object == null)
            return;

        modelname = object.getst("MODEL");
        value = composeName(modelname, itemname);
        if (new Documents(getContext().function).
                getObject("MODELITEM", value) == null)
            message("invalid.export.field");
    }

}
