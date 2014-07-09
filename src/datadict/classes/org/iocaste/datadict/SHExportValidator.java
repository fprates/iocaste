package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractValidator;

public class SHExportValidator extends AbstractValidator {
    private static final long serialVersionUID = 7361576769721130875L;
    
    public SHExportValidator(AbstractContext context) {
        super(context, "shexport");
    }
    
    private String composeName(String model, String item) {
        return new StringBuilder(model).append(".").append(item).toString();
    }
    
    @Override
    public final void validate() throws Exception {
        Documents documents = new Documents(getFunction());
        String modelname = getInput("MODEL").get();
        String itemname = getInput("EXPORT").get();
        String value = composeName(modelname, itemname);
        
        if (documents.getObject("MODELITEM", value) == null)
            message("invalid.export.field");
  }

}
