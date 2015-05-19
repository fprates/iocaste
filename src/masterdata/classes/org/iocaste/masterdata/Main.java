package org.iocaste.masterdata;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    public InstallData install(Message message) {
        InstallContext icontext = new InstallContext();
        Map<String, String> messages = new HashMap<>();
        
        messages.put("COUNTRY-CONFIG", "Países");
        messages.put("COUNTRY_ID", "País");
        messages.put("CURRENCY", "Moeda");
        messages.put("CURRENCY-CONFIG", "Moedas");
        messages.put("CURRENCY_ID", "Chave Moeda");
        messages.put("NAME", "Nome");
        messages.put("REGION-CONFIG", "Regiões/Estados");
        messages.put("TEXT", "Descrição");
        messages.put("UNIT_ID", "Unidade");
        messages.put("UNITS-CONFIG", "Unidades de medida");
        icontext.data.setMessages("pt_BR", messages);
        
        Currency.init(icontext);
        Country.init(icontext);
        Region.init(icontext);
        Units.init(icontext);
        
        return icontext.data;
    }
    
    @Override
    public final AbstractContext init(View view) {
        return new Context();
    }
}

class Context extends AbstractContext { }