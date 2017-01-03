package org.iocaste.copy.main;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.external.common.External;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;

public class CopyFromDBSource extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Documents documents;
        Query query;
        ExtendedObject[] objects;
        Function function;
        External external;
        String model = getdfst("model", "NAME");
        String ns = getdfst("model", "NAMESPACE");
        String db = getdfst("db", "DATABASE");
        
        external = new External(context.function);
        function = external.dbInstance(db);
        
        documents = new Documents(function);
        query = new Query();
        query.setModel(model);
        query.setNS(ns);
        objects = documents.select(query);
        if (objects == null) {
            message(Const.WARNING, "no.records");
            return;
        }
    }
}
