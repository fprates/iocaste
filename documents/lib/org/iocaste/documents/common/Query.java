package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Query implements Serializable {
    private static final long serialVersionUID = -5600137702519280069L;
    private String model;
    private List<WhereClause> where;
    private String[] columns;
    private Map<String, List<JoinClause>> join;
    private int maxresults;
    
    public Query() {
        where = new ArrayList<>();
        join = new LinkedHashMap<>();
    }
    
    private final void add(String field, byte condition, Object value,
            String operator) {
        where.add(new WhereClause(field, condition, value, operator));
    }
    
    public final void addColumns(String... columns) {
        this.columns = columns;
    }
    
    public final void addEqual(String field, Object value) {
        add(field, WhereClause.EQ, value, null);
    }
    
    public final void addIn(String field, Object... values) {
        add(field, WhereClause.IN, values, null);
    }
    
    public final void andEqual(String field, Object value) {
        add(field, WhereClause.EQ, value, "and");
    }
    
    public final void andIn(String field, Object... values) {
        add(field, WhereClause.IN, values, "and");
    }
    
    public final String[] getColumns() {
        return columns;
    }
    
    public final List<JoinClause> getJoinClauses(String name) {
        return join.get(name);
    }
    
    public final Set<String> getJoins() {
        return join.keySet();
    }
    
    public final int getMaxResults() {
        return maxresults;
    }
    
    public final String getModel() {
        return model;
    }
    
    public final List<WhereClause> getWhere() {
        return where;
    }
    
    public final void join(String model, String field1, String field2) {
        List<JoinClause> clauses;
        
        if (!join.containsKey(model)) {
            clauses = new ArrayList<>();
            join.put(model, clauses);
        } else {
            clauses = join.get(model);
        }
        
        clauses.add(new JoinClause(field1, field2));
    }
    
    public final void setMaxResults(int max) {
        maxresults = max;
    }
    
    public final void setModel(String model) {
        this.model = model;
    }
}
