package org.iocaste.kernel.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public abstract class AbstractDatabaseHandler extends AbstractHandler {
    
    protected final Object[] processResultSet(ResultSet results)
            throws Exception {
        Map<String, Object> line;
        int cols = 0;
        List<Map<String, Object>> lines = null;
        ResultSetMetaData metadata = null;
        
        while (results.next()) {
            if (lines == null) {
                lines = new ArrayList<>();
                metadata = results.getMetaData();
                cols = metadata.getColumnCount();
            }
            
            line = new HashMap<>();
            for (int i = 1; i <= cols; i++)
                line.put(metadata.getColumnName(i).toUpperCase(),
                        results.getObject(i));
            lines.add(line);
        }
        
        results.close();
        return (lines == null)? null : lines.toArray();
    }

    @Override
    public abstract Object run(Message message) throws Exception;

}
