package org.quantic.iocasteconnector;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.iocaste.documents.common.ExtendedObject;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.ServerDataEventListener;
import com.sap.conn.jco.ext.ServerDataProvider;

public class RFCDataProvider implements ServerDataProvider,
        DestinationDataProvider {
    private Map<String, ProviderConfig> configs;

    public RFCDataProvider() {
        configs = new HashMap<>();
    }
    
    public final boolean contains(String servername) {
        return configs.containsKey(servername);
    }

    @Override
    public Properties getDestinationProperties(String servername) {
        return configs.get(servername).destination;
    }
    
    @Override
    public final Properties getServerProperties(String servername) {
        return configs.get(servername).server;
    }
    
    public final void setConfig(ExtendedObject object, String locale) {
        ProviderConfig providerconfig;
        Properties properties;
        String portname, systemnr;
        
        portname = object.getst("PORT_NAME");
        providerconfig = configs.get(portname);
        if (providerconfig == null) {
            providerconfig = new ProviderConfig();
            configs.put(portname, providerconfig);
        }
        
        properties = providerconfig.server;
        properties.put(ServerDataProvider.JCO_GWHOST,
                object.getst("SAP_GWHOST"));
        properties.put(ServerDataProvider.JCO_GWSERV,
                object.getst("SAP_GWSERVER"));
        properties.put(ServerDataProvider.JCO_PROGID,
                object.getst("SAP_PROGRAM_ID"));
        properties.put(ServerDataProvider.JCO_CONNECTION_COUNT, "2");
        properties.put(ServerDataProvider.JCO_REP_DEST, portname);
        
        systemnr = String.format("%02d", object.geti("SAP_SYSTEM_NUMBER"));
        properties = providerconfig.destination;
        properties.put(DestinationDataProvider.JCO_ASHOST,
                object.getst("HOST"));
        properties.put(DestinationDataProvider.JCO_SYSNR,
                systemnr);
        properties.put(DestinationDataProvider.JCO_CLIENT,
                object.getst("SAP_CLIENT"));
        properties.put(DestinationDataProvider.JCO_USER,
                object.getst("USERNAME"));
        properties.put(DestinationDataProvider.JCO_PASSWD,
                object.getst("SECRET"));
        properties.put(DestinationDataProvider.JCO_LANG,
                locale.substring(0, 2));
    }
    
    @Override
    public final void setServerDataEventListener(
            ServerDataEventListener listener) { }

    @Override
    public final boolean supportsEvents() {
        return false;
    }

    @Override
    public final void setDestinationDataEventListener(
            DestinationDataEventListener listener) { }
    
}

class ProviderConfig {
    public Properties server, destination;
    
    public ProviderConfig() {
        server = new Properties();
        destination = new Properties();
    }
}