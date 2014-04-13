package org.iocaste.masterdata;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.protocol.user.Authorization;

public class Country {
    public static final void init(InstallContext context) {
        DocumentModel model;
        DocumentModelItem item;
        SearchHelpData shd;
        DataElement element;
        Authorization authorization;
        
        /*
         * Países
         */
        model = context.data.getModel("COUNTRIES", "COUNTRIES", null);
        element = new DataElement("COUNTRY_ID");
        element.setType(DataType.CHAR);
        element.setLength(2);
        element.setUpcase(true);
        context.countryid = new DocumentModelItem("COUNTRY_ID");
        context.countryid.setTableFieldName("CNTID");
        context.countryid.setDataElement(element);
        model.add(new DocumentModelKey(context.countryid));
        model.add(context.countryid);

        element = new DataElement("COUNTRY_NAME");
        element.setType(DataType.CHAR);
        element.setLength(30);
        element.setUpcase(true);
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("CNTNM");
        item.setDataElement(element);
        model.add(item);

        item = new DocumentModelItem("CURRENCY");
        item.setTableFieldName("CURID");
        item.setDataElement(context.currency.getDataElement());
        item.setReference(context.currency);
        model.add(item);
        
        context.data.addValues(model, "BR", "Brasil", "BRL");
        context.data.addValues(model, "JP", "日本", "JPY");
        context.data.addValues(model, "US", "United States of America", "USD");
        
        shd = new SearchHelpData("SH_COUNTRY");
        shd.setModel("COUNTRIES");
        shd.add("COUNTRY_ID");
        shd.add("NAME");
        shd.setExport("COUNTRY_ID");
        context.data.add(shd);

        context.data.link("COUNTRY-CONFIG", "iocaste-dataeditor "
                + "model=COUNTRIES action=edit");
        context.group.add("COUNTRY-CONFIG");
        
        authorization = new Authorization("COUNTRYCFG.CALL");
        authorization.setAction("CALL");
        authorization.setObject("LINK");
        authorization.add("LINK", "COUNTRY-CONFIG");
        context.data.add(authorization);
    }

}
