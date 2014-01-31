package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.ValidatorConfig;

public class SHExportValidator extends AbstractValidator {
    private static final long serialVersionUID = 7361576769721130875L;
    
    private String composeName(String model, String item) {
        return new StringBuilder(model).append(".").append(item).toString();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Validator#validate(
     *     org.iocaste.shell.common.ValidatorConfig)
     */
    @Override
    public final void validate(ValidatorConfig config) throws Exception {
        Documents documents = new Documents(getFunction());
        String modelname = config.getInput("MODEL").get();
        String itemname = config.getInput("EXPORT").get();
        String value = composeName(modelname, itemname);
        
        if (documents.getObject("MODELITEM", value) == null)
            config.setMessage("invalid.export.field");
  }

}