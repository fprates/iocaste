package org.iocaste.upload;

import java.util.Locale;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.shell.common.MultipartElement;

public class Upload extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Object value;
        DataElement element;
        DocumentModel model;
        ComplexDocument layout;
        ExtendedObject options;
        String linemodel, name;
        byte[] content;
        String[] lines, tokens;
        ExtendedObject object, column;
        ExtendedObject[] columns;
        int count, skip;
        Locale locale;
        IocasteException ie;
        
        options = getdf("options");
        layout = getDocument("layout", options.getst("LAYOUT"));
        linemodel = layout.getHeader().getst("MODEL");
        skip = options.geti("IGNORE_FIRST_LINES");
        content = ((MultipartElement)context.view.getElement("options.FILE")).
                getContent();
        
        lines = new String(content).split("\n");
        columns = layout.getItems("columns");
        count = 0;
        model = null;
        locale = context.view.getLocale();
        for (String line : lines) {
            count++;
            if ((count - 1) < skip)
                continue;
            object = instance(linemodel);
            if (model == null)
                model = object.getModel();
            tokens = line.split(";");
            for (int i = 0; i < columns.length; i++) {
                column = columns[i];
                if (column.getbl("IGNORE"))
                    continue;
                name = column.getst("MODEL_ITEM").split("\\.")[1];
                element = model.getModelItem(name).getDataElement();
                value = Documents.convertValue(tokens[i], element, locale);
                object.set(name, value);
            }
            
            try {
                modify(object);
            } catch (Exception e) {
                name = new StringBuilder(e.getMessage()).append("\n").
                        append("at line ").append(count).
                        append(": ").append(line).toString();
                
                ie = new IocasteException(name);
                ie.setStackTrace(e.getStackTrace());
                throw ie;
            }
        }
    }

}
