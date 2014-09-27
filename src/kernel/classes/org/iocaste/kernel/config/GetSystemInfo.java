package org.iocaste.kernel.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Properties;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetSystemInfo extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Config config = getFunction();
        Properties dbprops = new Properties();
        Connection connection = config.database.
                getDBConnection(message.getSessionid());
        DatabaseMetaData metadata = connection.getMetaData();
        
        dbprops.put("db_product_name", metadata.getDatabaseProductName());
        dbprops.put("db_product_version", metadata.getDatabaseProductVersion());
        dbprops.put("jdbc_driver_name", metadata.getDriverName());
        dbprops.put("jdbc_driver_version", metadata.getDriverVersion());
        
        return dbprops;
    }

}
