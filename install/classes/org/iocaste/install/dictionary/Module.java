package org.iocaste.install.dictionary;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Module {
    public static final byte NUMC = 0;
    public static final byte CHAR = 1;
    public static final byte INSERT = 0;
    private static List<Table> tables = new ArrayList<>();
    private static List<Query> queries = new ArrayList<>();
    private static Map<String, Authorization> authorizations = new HashMap<>();
    private static Map<String, Profile> profiles = new HashMap<>();
    
    protected static final Table tableInstance(String name) {
        Table table = new Table(name);
        tables.add(table);
        
        return table;
    }
    
    protected static final void compile(Statement ps) throws Exception {
        String sql;
        
        for (Table table : tables) {
            sql = table.toString();
            System.out.println(sql);
            ps.addBatch(sql);
        }
        
        for (Query query : queries) {
            sql = query.toString();
            System.out.println(sql);
            ps.addBatch(sql);
        }
    }
    
    protected static final void compileAuthorizationProfile(Table auth003,
            Table auth004, String prfnm) {
        int ident;
        Authorization authorization;
        Profile profile = profiles.get(prfnm);
        
        auth003.set("prfnm", prfnm);
        auth003.set("prfid", profile.prfid);
        auth003.set("crrnt", profile.crrnt);
        insert(auth003);
        
        ident = profile.prfid * 100;
        for (String name : profile.authorizations) {
            authorization = authorizations.get(name);
            ident++;
            auth004.set("ident", ident);
            auth004.set("prfnm", prfnm);
            auth004.set("autnm", name);
            auth004.set("objct", authorization.objct);
            auth004.set("actio", authorization.actio);
            insert(auth004);
        }
    }
    
    protected static final void insert(Table table) {
        queries.add(new Query(INSERT, table));
    }
    
    protected static final void insertAuthorizationProfile(String prfnm) {
        Profile profile = new Profile();

        profile.prfid = profiles.size() + 1;
        profile.crrnt = profile.prfid * 100;
        profiles.put(prfnm, profile);
    }
    
    protected static final void insertExecuteAuthorization(Table auth001,
            Table auth002, String autnm, String appnm) {
        Authorization authorization = new Authorization();
        
        authorization.autid = authorizations.size() + 1;
        authorization.appnm = appnm;
        authorization.objct = "APPLICATION";
        authorization.actio = "EXECUTE";
        authorizations.put(autnm, authorization);
        
        auth001.set("autnm", autnm);
        auth001.set("objct", authorization.objct);
        auth001.set("actio", authorization.actio);
        auth001.set("autid", authorization.autid);
        insert(auth001);
        
        auth002.set("ident", (authorization.autid * 100) + 1);
        auth002.set("autnm", autnm);
        auth002.set("param", "APPNAME");
        auth002.set("value", appnm);
        insert(auth002);
    }
    
    protected static final void linkAuthorizationToProfile(String prfnm,
            String autnm) {
        Profile profile = profiles.get(prfnm);
        
        profile.crrnt++;
        profile.authorizations.add(autnm);
    }
}

class Query {
    public byte command;
    public String tablename;
    public Map<String, Object> values;
    public Map<String, Byte> types;
    
    public Query(byte command, Table table) {
        this.command = command;
        tablename = table.getName();
        
        values = new HashMap<>();
        values.putAll(table.getValues());
        
        types = new HashMap<>();
        for (String name : values.keySet())
            types.put(name, table.getType(name));
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder _into, _values, sb = new StringBuilder();
        
        switch (command) {
        case Module.INSERT:
            _into = new StringBuilder();
            _values = new StringBuilder();
            sb.append("insert into ").append(tablename).append("(");
            for (String field : values.keySet()) {
                if (_into.length() > 0) {
                    _into.append(",");
                    _values.append(",");
                }
                
                _into.append(field);
                switch (types.get(field)) {
                case Module.CHAR:
                    _values.append("'").append(values.get(field)).append("'");
                    break;
                case Module.NUMC:
                    _values.append(values.get(field));
                    break;
                }
            }
            
            sb.append(_into).append(") values(").append(_values).append(")");
            break;
        }
        
        return sb.toString();
    }
}

class Authorization {
    public String appnm, objct, actio;
    public int autid;
}

class Profile {
    public int prfid, crrnt;
    public List<String> authorizations = new ArrayList<>();
}