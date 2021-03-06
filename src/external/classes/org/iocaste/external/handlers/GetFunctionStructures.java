package org.iocaste.external.handlers;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetFunctionStructures extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Map<String, ComplexDocument> structures;
        ComplexDocument structure, function;
        String structurename, functionname;
        Documents documents = new Documents(getFunction());

        functionname = message.getst("function");
        function = documents.getComplexDocument(
                "XTRNL_FUNCTION", null, functionname);
        
        if (function == null)
            return null;
        
        structures = new HashMap<>();
        for (ExtendedObject object : function.getItems("parameters")) {
            switch (object.geti("TYPE")) {
            case DataType.EXTENDED:
            case DataType.TABLE:
                break;
            default:
                continue;
            }
            
            structurename = object.getst("STRUCTURE");
            structure = documents.getComplexDocument(
                    "XTRNL_STRUCTURE", null, structurename);

            if (structure == null)
                return null;
            
            structures.put(structurename, structure);
        }
        
        return structures;
    }

}
