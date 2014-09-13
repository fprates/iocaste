package org.iocaste.kernel.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.common.Message;

public abstract class AbstractDatabaseHandler extends AbstractHandler {
    
    protected final Object[] processResultSet(ResultSet results)
            throws Exception {
        Map<String, Object> line;
        ResultSetMetaData metadata = results.getMetaData();
        int cols = metadata.getColumnCount();
        List<Map<String, Object>> lines = new ArrayList<>();
        
        while (results.next()) {
            line = new HashMap<>();
            
            for (int i = 1; i <= cols; i++)
                line.put(metadata.getColumnName(i).toUpperCase(),
                        results.getObject(i));
            
            lines.add(line);
        }
        
        results.close();
        
        return (lines.size() == 0)? null : lines.toArray();
    }

    @Override
    public abstract Object run(Message message) throws Exception;

}
