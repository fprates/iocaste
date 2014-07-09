package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractValidator;

public class SHItemValidator extends AbstractValidator {
    private static final long serialVersionUID = 7361576769721130875L;
    
    public SHItemValidator(AbstractContext context) {
        super(context, "shitem");
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
        Documents documents = new Documents(getFunction());
        String modelname = getInput("MODEL").get();
        String itemname = getInput("ITEM").get();
        String value = composeName(modelname, itemname);
        
        if (documents.getObject("MODELITEM", value) == null)
            message("invalid.model.item");
  }

}
