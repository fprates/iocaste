package org.iocaste.documents;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.RangeOption;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;
import org.iocaste.protocol.IocasteException;

public class Parser {
    private static final byte SELECT = 0;
    private static final byte SELECT_ALL = 1;
    private static final byte DELETE = 2;
    private static final String[] COMPARISON_OPERATORS = {
        "=",
        "<",
        ">",
        "<>",
        ">=",
        "<=",
        "IN",
        "LIKE"
    };
    
    private static final String[] LOGIC_OPERATORS = {
        "AND",
        "OR"
    };
    
    private static final boolean isInvalidOperator(String op,
            String[] operators) {
        String ops = op.trim();
        
        for (String operator : operators)
            if (ops.equals(operator))
                return false;
        
        return true;
    }
    
    private static final byte parseColumns(String token, QueryInfo queryinfo) {
        String column;
        char[] buffer;
        int t;
        
        if (token.equals("FROM"))
            return 3;
        
        column = "";
        buffer = token.toCharArray();
        t = 0;
        while (true) {
            if (t >= buffer.length) {
                if (column.length() == 0)
                    break;
                queryinfo.columns.add(column);
                break;
            }
            
            if (buffer[t] == ',') {
                queryinfo.columns.add(column);
                column = "";
                t++;
                continue;
            }
            
            column += buffer[t++];
        }
        
        return 1;
    }
    
    private static final void parseArg1(char c, Components components) {
        if (Character.isDigit(c) || Character.isAlphabetic(c) || c == '_' ||
                c == '.') {
            components.temp += Character.toUpperCase(c);
            return;
        }
        
        components.arg1 = components.temp;
        components.temp = "";
        
        return;
    }
    
    private static final void parseArg2(char c, Components components) {
        
        if (c == ' ') {
            components.arg2 = components.temp;
            components.temp = "";
            return;
        }
        
        components.temp += c;
        return;
    }
    
    private static final void parseComparisonOperator(char c,
            Components components) {
        char ch = Character.toUpperCase(c);
        switch (ch) {
        case '=':
        case '>':
        case '<':
        case 'I':
        case 'N':
        case 'L':
        case 'E':
        case 'K':
            components.temp += ch;
            return;
        }
        
        components.op = components.temp;
        components.temp = "";
        return;
    }
    
    private static final boolean parseLogicOperator(char c,
            Components components) {
        if (Character.isAlphabetic(c)) {
            components.temp += Character.toUpperCase(c);
            return false;
        }
        
        components.logic = components.temp;
        return true;
    }
    
    /**
     * 
     * @param criteria
     * @param queryinfo
     * @param cache
     * @return
     * @throws Exception
     */
    private static final String parseJoin(String criteria, QueryInfo queryinfo,
            Cache cache) throws Exception {
        return parseWhere(criteria, queryinfo, cache, true);
    }
    
    /**
     * 
     * @param query
     * @param cache
     * @return
     * @throws Exception
     */
    public static final QueryInfo parseQuery(String query, Object[] criteria,
            Cache cache) throws Exception {
        String upcasetoken;
        StringBuilder joinclause;
        DocumentModel model = null;
        String[] parsed = query.split("\\s");
        byte pass = 0;
        QueryInfo queryinfo = new QueryInfo();
        
        for (String token : parsed) {
            upcasetoken = token.trim().toUpperCase();
            if (upcasetoken.length() == 0)
                continue;
            
            switch (pass) {
            case 0:
                if (upcasetoken.equals("SELECT")) {
                    queryinfo.command = SELECT;
                    pass = 1;
                    continue;
                }
                
                if (upcasetoken.equals("FROM")) {
                    queryinfo.command = SELECT_ALL;
                    pass = 3;
                    continue;
                }
                
                if (upcasetoken.equals("DELETE")) {
                    queryinfo.command = DELETE;
                    pass = 2;
                }
                
                if (pass > 0)
                    continue;
                
                throw new IocasteException(new StringBuilder(query).
                        append(": invalid query.").toString());
            case 1:
                pass = parseColumns(upcasetoken, queryinfo);
                continue;
            case 2:
                pass++;
                continue;
            case 3:
                queryinfo.model = Model.get(upcasetoken, cache);
                if (queryinfo.model == null)
                    throw new IocasteException(new StringBuilder(
                            "Document model ").
                            append(upcasetoken).
                            append(" not found.").toString());
                pass++;
                continue;
            case 4:
                if (upcasetoken.equals("INNER"))
                    continue;
                
                if (upcasetoken.equals("JOIN")) {
                    pass = 6;
                    continue;
                }
                
                if (upcasetoken.equals("WHERE"))
                    pass++;
                continue;
            case 5:
                queryinfo.where.append(token).append(" "); 
                continue;
            case 6:
                model = Model.get(upcasetoken, cache);
                if (model == null)
                    throw new IocasteException(new StringBuilder(
                            "Document model ").
                            append(upcasetoken).
                            append(" not found.").toString());
                queryinfo.join.put(model, new StringBuilder());
                pass++;
                continue;
            case 7:
                if (!upcasetoken.equals("ON"))
                    throw new IocasteException("\"ON\" expected.");
                
                pass = 8;
                continue;
            case 8:
                if (upcasetoken.equals("WHERE")) {
                    pass = 5;
                    continue;
                }
                
                if (upcasetoken.equals("INNER")) {
                    pass = 4;
                    continue;
                }
                
                joinclause = queryinfo.join.get(model);
                joinclause.append(token).append(" ");
                continue;
            }
        }
        
        queryinfo.criteria = criteria;
        rebuildQuery(queryinfo, cache);
        return queryinfo;
    }
    
    private static final void parseRange(Components components, int argnr,
            Object[] criteria) {
        RangeOption option;
        StringBuilder sb = new StringBuilder();
        ValueRange range = (ValueRange)criteria[argnr];
        
        for (ValueRangeItem item : range.getItens()) {
            if (sb.length() > 1)
                sb.append(" or ");
            
            option = item.getOption();
            components.op = option.getOperator();
            switch (option) {
            case CP:
                components.criteria.add(
                        item.getLow().toString().replace("*", "%"));
                break;
            default:
                components.criteria.add(item.getLow().toString());
                break;
            }
            
            sb.append(components.arg1).append(" ").
                    append(components.op).append(" ").
                    append(components.arg2);
        }
        
        if (sb.length() == 0) {
            components.ignore = true;
            components.range = null;
            return;
        }
        
        components.range = new StringBuilder("(").append(sb).append(")").
                toString();
    }
    
    /**
     * 
     * @param criteria
     * @param queryinfo
     * @param cache
     * @param isjoin
     * @return
     * @throws Exception
     */
    private static final String parseWhere(String criteria, QueryInfo queryinfo,
            Cache cache, boolean isjoin) throws Exception {
        StringBuilder sb;
        List<Object> parameters;
        int argnr = 0;
        int i = 0;
        String lastlogic = null;
        List<Components> args = new ArrayList<>();
        char[] buffer = criteria.trim().toCharArray();
        Components components = new Components();
        
        for (;;) {
            if (i == buffer.length) {
                args.add(components);
                if (components.arg2 != null)
                    break;
                
                if (isjoin) {
                    components.arg2 = rebuildField(components.temp.
                            toUpperCase(), queryinfo, cache);
                    break;
                }
                
                components.arg2 = components.temp;
                if (!components.arg2.equals("?"))
                    break;
                
                if (components.op.equals("IN"))
                    parseRange(components, argnr,queryinfo.criteria);
                else
                    components.criteria.add(queryinfo.criteria[argnr]);
                
                break;
            }
            
            // (arg1) op arg2
            if (components.arg1 == null) {
                parseArg1(buffer[i++], components);
                if (components.arg1 != null)
                    components.arg1 = rebuildField(
                            components.arg1, queryinfo, cache);
                continue;
            }

            // arg1 (op) arg2
            if (components.op == null) {
                parseComparisonOperator(buffer[i++], components);
                if (components.op != null)
                    if (isInvalidOperator(components.op, COMPARISON_OPERATORS))
                        throw new Exception(components.op.
                                concat(" is an invalid comparison operator."));
                continue;
            }
            
            // arg1 op (arg2)
            if (components.arg2 == null) {
                parseArg2(buffer[i++], components);
                if (components.arg2 != null) {
                    if (components.op.equals("IN")) {
                        parseRange(components, argnr, queryinfo.criteria);
                        argnr++;
                    } else {
                        if (isjoin) {
                            components.arg2 = rebuildField(components.arg2.
                                    toUpperCase(), queryinfo, cache);
                            continue;
                        }
                        if (components.arg2.equals("?"))
                            components.criteria.add(
                                    queryinfo.criteria[argnr++]);
                    }
                }
                continue;
            }
            
            // operador lógico
            if (!parseLogicOperator(buffer[i++], components))
                continue;
            
            if (isInvalidOperator(components.temp, LOGIC_OPERATORS))
                throw new Exception(components.op.
                        concat(" is an invalid logic operator."));
            
            args.add(components);
            components = new Components();
        }
        
        parameters = new ArrayList<>();
        sb = new StringBuilder();
        for (Components component : args) {
            if (lastlogic != null)
                sb.append(" ").append(lastlogic).append(" ");
            
            if (component.ignore) {
                lastlogic = null;
                continue;
            }
            
            lastlogic = component.logic;
            if (component.range != null)
                sb.append(component.range);
            else
                sb.append(component.arg1).append(component.op).
                        append(component.arg2);
            if (!isjoin)
                parameters.addAll(component.criteria);
        }
        
        if (!isjoin)
            queryinfo.criteria = parameters.toArray();
        return sb.toString();
    }
    
    /**
     * 
     * @param field
     * @param queryinfo
     * @param cache
     * @return
     * @throws Exception
     */
    private static final String rebuildField(String field, QueryInfo queryinfo,
            Cache cache) throws Exception {
        DocumentModel model;
        String name, fieldname;
        String[] composed = field.split("\\.");
        
        if (composed.length == 1)
            return queryinfo.model.getModelItem(composed[0]).
                    getTableFieldName();
        
        model = queryinfo.model;
        name = model.getName();
        if (!name.equals(composed[0]))
            model = Model.get(composed[0], cache);
        
        fieldname = model.getModelItem(composed[1]).getTableFieldName();
        if (fieldname == null)
            throw new IocasteException(new StringBuilder(composed[1]).
                    append(" not found for model ").
                    append(name).toString());
        
        return new StringBuilder(model.getTableName()).
                append(".").append(fieldname).toString();
    }
    
    /**
     * 
     * @param queryinfo
     * @param cache
     * @throws Exception
     */
    private static final void rebuildQuery(QueryInfo queryinfo, Cache cache)
            throws Exception {
        String where;
        int t, i;
        StringBuilder sb = new StringBuilder();
        
        switch (queryinfo.command) {
        case SELECT:
            sb.append("select ");
            i = 1;
            t = queryinfo.columns.size();
            for (String column : queryinfo.columns) {
                if (column.equals("*")) {
                    sb.append("*");
                    break;
                }
                
                sb.append(rebuildField(column, queryinfo, cache));
                if ((i++) < t)
                    sb.append(", ");
            }
            
            sb.append(" from ").append(queryinfo.model.getTableName());
            break;
        case SELECT_ALL:
            sb.append("select * from ").
                    append(queryinfo.model.getTableName());
            break;
        case DELETE:
            sb.append("delete from ").
                    append(queryinfo.model.getTableName());
            break;
        }
        
        for (DocumentModel model : queryinfo.join.keySet()) {
            sb.append(" inner join ").
                    append(model.getTableName()).
                    append(" on ");
            where = queryinfo.join.get(model).toString();
            where = parseJoin(where, queryinfo, cache);
            sb.append(where);
        }
            
        if (queryinfo.where.length() > 0) {
            where = parseWhere(queryinfo.where.toString(), queryinfo, cache,
                    false);
            if (where.length() > 0)
                sb.append(" where ").append(where);
        }
        
        queryinfo.query = sb.toString();
    }

}

class Components {
    public String arg1, op, arg2, temp, range, logic;
    public List<Object> criteria;
    public boolean ignore;
    
    public Components() {
        temp = "";
        criteria = new ArrayList<Object>();
    }
}
