package org.iocaste.masterdata;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public void entries() {
        instance("pt_BR");
        put("CODE", "Código");
        put("COUNTRIES", "Países");
        put("COUNTRY-CONFIG", "Países");
        put("COUNTRY_ID", "País");
        put("CURRENCY", "Moeda");
        put("CURRENCY-CONFIG", "Moedas");
        put("CURRENCY_ID", "Chave Moeda");
        put("MEASURE_UNITS", "Unidades de medida");
        put("NAME", "Nome");
        put("REGION", "Regiões/Estados");
        put("REGION-CONFIG", "Regiões/Estados");
        put("REGION_ID", "Chave da região");
        put("TEXT", "Descrição");
        put("UNIT_ID", "Unidade");
        put("UNITS-CONFIG", "Unidades de medida");
    }

}
