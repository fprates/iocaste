package org.iocaste.masterdata;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.SearchHelpData;

public class Currency {
    public static final void init(InstallContext context) {
        DocumentModel model;
        DocumentModelItem item;
        SearchHelpData shd;
        DataElement element;
        
        /*
         * Moedas
         */
        model = context.data.getModel("CURRENCY", "CURRENCY", null);
        
        element = new DataElement("CURRENCY_ID");
        element.setType(DataType.CHAR);
        element.setLength(3);
        element.setUpcase(true);
        context.currency = new DocumentModelItem("CURRENCY_ID");
        context.currency.setTableFieldName("CURID");
        context.currency.setDataElement(element);
        model.add(context.currency);
        model.add(new DocumentModelKey(context.currency));

        element = new DataElement("CURRENCY_TEXT");
        element.setType(DataType.CHAR);
        element.setLength(20);
        element.setUpcase(false);
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("CURTX");
        item.setDataElement(element);
        model.add(item);
        
        context.data.addValues(model, "BRL", "Real");
        context.data.addValues(model, "USD", "Dólar Americano");
        context.data.addValues(model, "EUR", "Euro");
        context.data.addValues(model, "GBP", "Libras Esterlinas");
        context.data.addValues(model, "CAN", "Dólar Canadense");
        context.data.addValues(model, "JPY", "Yene Japonês");

        shd = new SearchHelpData("SH_CURR");
        shd.setModel("CURRENCY");
        shd.add("CURRENCY_ID");
        shd.add("TEXT");
        shd.setExport("CURRENCY_ID");
        context.data.add(shd);
    }

}
