package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public class SHExportValidator extends AbstractValidator {
    private static final long serialVersionUID = 7361576769721130875L;
    private Documents documents;
    
    private String composeName(String model, String item) {
        return new StringBuilder(model).append(".").append(item).toString();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractValidator#validate()
     */
    @Override
    public final void validate() throws Exception {
        TableItem item;
        String value, modelname;
        String itemname = getInput().getst();
        Table table = getElement("itens");
        
        item = getItem(table, "EXPORT", itemname);
        if (item == null)
            return;

        if (documents == null)
            documents = new Documents(getContext().function);
        
        modelname = item.getObject().getst("MODEL");
        value = composeName(modelname, itemname);
        if (documents.getObject("MODELITEM", value) == null)
            message("invalid.export.field");
    }

}
