package org.iocaste.upload.main;

import java.util.Locale;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.shell.common.Const;
import org.iocaste.upload.Context;

public class Upload extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Object value;
        DataElement element;
        String name;
        String[] lines, tokens;
        ExtendedObject object, column;
        ExtendedObject[] columns;
        DocumentModelItem[] items;
        Locale locale;
        IocasteException ie;
        Context extcontext;
        Object ns;
        int count, length, skip;
        boolean truncate;

        extcontext = getExtendedContext();
        switch (context.view.getPageName()) {
        case "ns":
            ns = getdf("ns", "NSKEY");
            back();
            break;
        default:
            ns = null;
            extcontext.options = getdf("options");
            extcontext.layout = getDocument(
                    "UPL_LAYOUT", extcontext.options.getst("LAYOUT"));
            if (extcontext.layout == null) {
                setFocus("UPL_LAYOUT", "LAYOUT");
                message(Const.ERROR, "invalid.layout");
                return;
            }
            
            extcontext.linemodel = extcontext.layout.getHeader().getst("MODEL");
            extcontext.model = new Documents(context.function).
                    getModel(extcontext.linemodel);
            extcontext.content = getFileContent("options.FILE");
            
            /*
             * solicita valor se modelo possuir namespace.
             */
            extcontext.nsitem = extcontext.model.getNamespace();
            if ((extcontext.model.getNamespace() != null) &&
                    (extcontext.ns == null)) {
                init("ns", extcontext);
                redirect("ns");
                return;
            }
            break;
        }

        skip = extcontext.options.geti("IGNORE_FIRST_LINES");
        lines = new String(extcontext.content).split("\n");
        columns = extcontext.layout.getItems("columns");
        if ((columns == null) || (columns.length == 0)) {
            items = extcontext.model.getItens();
            columns = new ExtendedObject[items.length];
            for (int i = 0; i < items.length; i++) {
                columns[i] = instance("UPL_LAYOUT_COLUMN");
                columns[i].set("NAME", items[i].getName());
                columns[i].set("MODEL_ITEM", items[i].toString());
            }
        }
        count = 0;
        truncate = extcontext.options.getbl("TRUNCATE_CHAR");
        locale = context.view.getLocale();
        for (String line : lines) {
            count++;
            if ((count - 1) < skip)
                continue;
            object = instance(extcontext.linemodel);
            tokens = line.split(";");
            for (int i = 0; i < columns.length; i++) {
                if (tokens.length == i)
                    break;
                column = columns[i];
                name = column.getst("MODEL_ITEM");
                if (column.getbl("IGNORE") || (name == null))
                    continue;
                name = name.split("\\.")[1];
                element = extcontext.model.getModelItem(name).getDataElement();
                value = Documents.convertValue(tokens[i], element, locale);
                if ((element.getType() == DataType.CHAR) && (value != null) && 
                        truncate) {
                    length = element.getLength();
                    tokens[i] = (String)value;
                    if (tokens[i].length() > length)
                        value = tokens[i].substring(0, length);
                }
                object.set(name, value);
            }
            
            if (Documents.isInitial(object))
                continue;
            
            try {
                object.setNS(ns);
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
        
        message(Const.STATUS, "upload.successful");
    }
}
