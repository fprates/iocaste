package org.iocaste.runtime.common.managedview;

import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.shell.common.Const;

public class ManagedViewSave extends AbstractActionHandler<Context> {
    
    @Override
    protected void execute(Context context) {
        DocumentExtractor extractor;
        DataConversion conversion;
        ComplexModel cmodel;
        String keyname;
        ManagedViewContext mviewctx = context.mviewctx();
        
        cmodel = context.runtime().getComplexModel(mviewctx.cmodel);
        conversion = new DataConversion();
        conversion.dfsource("base");
        for (DocumentModelKey key : cmodel.getHeader().getKeys()) {
            keyname = key.getModelItemName();
            conversion.constant(keyname, getkey("head"));
            if ((mviewctx.number != null) && (mviewctx.id == null))
                conversion.nextnumber(
                        keyname, mviewctx.number, mviewctx.numberseries);
            break;
        }
        
        extractor = documentExtractorInstance(mviewctx.cmodel);
        extractor.setHeader(conversion);
        extractor.ignoreInitialHead();
        extractor.setNS(mviewctx.ns);
        tabs(extractor, context, cmodel);
        mviewctx.document = extractor.save();
        mviewctx.id = mviewctx.document.getKey();
        
        message(Const.STATUS, "record.saved", mviewctx.id);
    }
    
    protected void tabs(DocumentExtractor extractor,
            Context context, ComplexModel cmodel) {
        DataConversion conversion;
        DocumentModel model;
        ToolData tabletool;
        String tbname, itemname;
        AbstractPage page = context.getPage();
        
        for (String name : cmodel.getItems().keySet()) {
            model = cmodel.getItems().get(name).model;
            if (model == null)
                continue;
            tbname = name.concat("_table");
            conversion = new DataConversion(model.getName());
            conversion.tbsource(tbname);
            extractor.add(conversion);

            tabletool = page.instance(tbname);
            for (DocumentModelItem item : model.getItens()) {
                itemname = item.getName();
                if ((tabletool.indexitem != null &&
                        tabletool.indexitem.equals(itemname)) ||
                        model.isKey(item))
                    conversion.ignore(itemname);
            }
        }
    }

}
