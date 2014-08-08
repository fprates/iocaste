package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public class SHItemValidator extends AbstractValidator {
    private static final long serialVersionUID = 7361576769721130875L;
    private Documents documents;
    
    public SHItemValidator(AbstractContext context) {
        super("shitem", context);
        documents = new Documents(getContext().function);
    }
    
    private String composeName(String model, String item) {
        return new StringBuilder(model).append(".").append(item).toString();
    }
    
    @Override
    public final void validate() throws Exception {
        TableItem item;
        String modelname, value;
        String itemname = getInput().getst();
        Table table = getElement("itens");
        
        item = getItem(table, "ITEM", itemname);
        if (item == null)
            return;

        modelname = item.getObject().getst("MODEL");
        value = composeName(modelname, itemname);
        if (documents.getObject("MODELITEM", value) == null)
            message("invalid.model.item");
  }

}
