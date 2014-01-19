package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Query implements Serializable {
    private static final long serialVersionUID = -5600137702519280069L;
    private String model, statement;
    private List<WhereClause> where;
    private String[] orderby, columns;
    private Map<String, List<JoinClause>> join;
    private int maxresults;
    private boolean skiperror, descending;
    private Map<String, Object> values;
    
    public Query() {
        init();
    }
    
    public Query(String statement) {
        init();
        this.statement = statement;
        values = new HashMap<>();
    }
    
    private final void add(String field, byte condition, Object value,
            String operator) {
        where.add(new WhereClause(field, condition, value, operator));
    }
    
    public final void addColumns(String... columns) {
        this.columns = columns;
    }
    
    public final void andEqual(String field, Object value) {
        add(field, WhereClause.EQ, value, "and");
    }

    public final void andGE(String field, Object value) {
        add(field, WhereClause.GE, value, "and");
    }
    
    public final void andGT(String field, Object value) {
        add(field, WhereClause.GT, value, "and");
    }
    
    public final void andIn(String field, Object... values) {
        add(field, WhereClause.IN, values, "and");
    }

    public final void andLE(String field, Object value) {
        add(field, WhereClause.LE, value, "and");
    }
    
    public final void andLT(String field, Object value) {
        add(field, WhereClause.LT, value, "and");
    }
    
    public final void andNot(String field, Object value) {
        add(field, WhereClause.NE, value, "and");
    }
    
    public final void beginEnclose() {
        enclose(true);
    }
    
    public final void descending() {
        descending = true;
    }
    
    private final void enclose(boolean begin) {
        if (begin)
            add(null, WhereClause.BE, null, null);
        else
            add(null, WhereClause.EE, null, null);
    }
    
    public final void endEnclose() {
        enclose(false);
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
    
    public final String[] getOrderBy() {
        return orderby;
    }
    
    public final String getStatement() {
        return statement;
    }
    
    public final Map<String, Object> getValues() {
        return values;
    }
    
    public final List<WhereClause> getWhere() {
        return where;
    }
    
    private final void init() {
        where = new ArrayList<>();
        join = new LinkedHashMap<>();
        skiperror = true;
    }
    
    public final boolean isDescending() {
        return descending;
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
    
    public final boolean mustSkipError() {
        return skiperror;
    }
    
    public final void orderBy(String... fields) {
        orderby = fields;
    }
    
    public final void orEqual(String field, Object value) {
        add(field, WhereClause.EQ, value, "or");
    }

    public final void orGE(String field, Object value) {
        add(field, WhereClause.GE, value, "or");
    }
    
    public final void orGT(String field, Object value) {
        add(field, WhereClause.GT, value, "or");
    }
    
    public final void orIn(String field, Object... values) {
        add(field, WhereClause.IN, values, "or");
    }

    public final void orLE(String field, Object value) {
        add(field, WhereClause.LE, value, "or");
    }
    
    public final void orLT(String field, Object value) {
        add(field, WhereClause.LT, value, "or");
    }
    
    public final void orNot(String field, Object value) {
        add(field, WhereClause.NE, value, "or");
    }
    
    public final void setMaxResults(int max) {
        maxresults = max;
    }
    
    public final void setModel(String model) {
        this.model = model;
    }
    
    public final void setSkipError(boolean skiperror) {
        this.skiperror = skiperror;
    }
    
    public final void values(String name, Object value) {
        values.put(name, value);
    }
}
