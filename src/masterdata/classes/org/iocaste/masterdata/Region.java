package org.iocaste.masterdata;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.protocol.user.Authorization;

public class Region {
    public static final void init(InstallContext context) {
        DocumentModel model;
        DocumentModelItem item;
        DataElement element;
        SearchHelpData shd;
        Authorization authorization;
        
        /*
         * Regiões
         */
        model = context.data.getModel("REGION",  "REGION", null);
        element = new DataElement("REGION_KEY");
        element.setType(DataType.CHAR);
        element.setLength(4);
        element.setUpcase(true);
        item = new DocumentModelItem("REGION_ID");
        item.setTableFieldName("REGID");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem("COUNTRY_ID");
        item.setTableFieldName("CNTID");
        item.setDataElement(context.countryid.getDataElement());
        item.setReference(context.countryid);
        model.add(item);
        
        element = new DataElement("REGION_CODE");
        element.setType(DataType.CHAR);
        element.setLength(2);
        element.setUpcase(true);
        item = new DocumentModelItem("CODE");
        item.setTableFieldName("REGCD");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("REGION_TEXT");
        element.setType(DataType.CHAR);
        element.setLength(30);
        element.setUpcase(false);
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("REGTX");
        item.setDataElement(element);
        model.add(item);
        
        shd = new SearchHelpData("SH_REGION");
        shd.setModel("REGION");
        shd.add("REGION_ID");
        shd.add("CODE");
        shd.add("TEXT");
        shd.setExport("REGION_ID");
        context.data.add(shd);
        
        context.data.addValues(model, "BRAC", "BR", "AC", "Acre");
        context.data.addValues(model, "BRAL", "BR", "AL", "Alagoas");
        context.data.addValues(model, "BRAM", "BR", "AM", "Amazonas");
        context.data.addValues(model, "BRAP", "BR", "AP", "Amapá");
        context.data.addValues(model, "BRBA", "BR", "BA", "Bahia");
        context.data.addValues(model, "BRCE", "BR", "CE", "Ceará");
        context.data.addValues(model, "BRDF", "BR", "DF", "Brasília");
        context.data.addValues(model, "BRES", "BR", "ES", "Espírito Santo");
        context.data.addValues(model, "BRGO", "BR", "GO", "Goiás");
        context.data.addValues(model, "BRMA", "BR", "MA", "Maranhão");
        context.data.addValues(model, "BRMG", "BR", "MG", "Minas Gerais");
        context.data.addValues(model, "BRMS", "BR", "MS", "Mato Grosso do Sul");
        context.data.addValues(model, "BRMT", "BR", "MT", "Mato Grosso");
        context.data.addValues(model, "BRPA", "BR", "PA", "Pará");
        context.data.addValues(model, "BRPB", "BR", "PB", "Paraíba");
        context.data.addValues(model, "BRPE", "BR", "PE", "Pernambuco");
        context.data.addValues(model, "BRPI", "BR", "PI", "Piauí");
        context.data.addValues(model, "BRPR", "BR", "PR", "Paraná");
        context.data.addValues(model, "BRRJ", "BR", "RJ", "Rio de Janeiro");
        context.data.addValues(model, "BRRN", "BR", "RN", "Rio Grande do "
                + "Norte");
        context.data.addValues(model, "BRRO", "BR", "RO", "Rondônia");
        context.data.addValues(model, "BRRR", "BR", "RR", "Roraima");
        context.data.addValues(model, "BRRS", "BR", "RS", "Rio Grande do Sul");
        context.data.addValues(model, "BRSC", "BR", "SC", "Santa Catarina");
        context.data.addValues(model, "BRSE", "BR", "SE", "Sergipe");
        context.data.addValues(model, "BRSP", "BR", "SP", "São Paulo");
        context.data.addValues(model, "BRTO", "BR", "TO", "Tocantins");

        context.data.link("REGION-CONFIG", "iocaste-dataeditor "
                + "model=REGION action=edit");
        context.group.add("REGION-CONFIG");
        
        authorization = new Authorization("REGIONCFG.CALL");
        authorization.setAction("CALL");
        authorization.setObject("LINK");
        authorization.add("LINK", "REGION-CONFIG");
        context.data.add(authorization);
        
        context.profile.add(authorization);
    }

}
