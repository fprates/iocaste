package org.iocaste.install;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.iocaste.install.dictionary.Core;
import org.iocaste.install.dictionary.Documents;
import org.iocaste.install.dictionary.Login;
import org.iocaste.install.dictionary.Module;
import org.iocaste.install.dictionary.Shell;
import org.iocaste.install.dictionary.Table;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.View;

public class DBConfigRequest {
    private static final String CONFIG_FILE = "core.properties";
    private static final String IOCASTE_DIR = ".iocaste";
    private static final byte MSSQL = 0;
    private static final byte MYSQL = 1;
    private static final byte HSQLDB = 2;
    private static final String[] DRIVERS = {
        "com.microsoft.sqlserver.jdbc.SQLServerDriver",
        "com.mysql.jdbc.Driver",
        "org.hsqldb.jdbcDriver"
    };
    
    private static final String[] MSSQL_INIT = {
        "use master;",
        "if exists(select name from sys.databases where name = '",
        "drop database ",
        "create database ",
        "use "
    };
    
    private static final String[] MYSQL_INIT = {
        "drop database if exists ",
        "create database ",
        "use "
    };
    
    public static final void action(View view) throws Exception {
        Statement ps;
        Connection connection;
        String[] init = null;
        DataForm dbinfo = view.getElement("dbinfo");
        Config config = new Config();
        RadioButton rb = view.getElement("dbtype");
        
        config.option = Byte.parseByte((String)dbinfo.get("options").get());
        config.host = dbinfo.get("host").get();
        config.username = dbinfo.get("username").get();
        config.secret = dbinfo.get("secret").get();
        config.dbname = dbinfo.get("dbname").get();
        config.dbname = config.dbname.toUpperCase();
        
        for (RadioButton dbtype : rb.getGroup().getComponents()) {
            if (!dbtype.isSelected())
                continue;
            
            config.dbtype = dbtype.getName();
            init = getDBInitializator(config);
            
            break;
        }
        
        switch (config.option) {
        case DBConfig.KEEP_BASE:
        case DBConfig.NEW_BASE:
            Class.forName(config.dbdriver).newInstance();
            connection = DriverManager.getConnection(
                    config.iurl.toString(), config.username, config.secret);
            connection.setAutoCommit(false);
            
            try {
                ps = connection.createStatement();
                if (init != null)
                    for (String sql : init)
                        ps.addBatch(sql);
                
                createTables(ps, config);
                ps.executeBatch();
                saveConfig(config);
                
                connection.commit();
                ps.close();
                connection.close();
            } catch (Exception e) {
                connection.rollback();
                connection.close();
                throw e;
            }
            
            break;
        case DBConfig.CHANGE_BASE:
            saveConfig(config);
            break;
        }
        
        view.redirect("FINISH");
    }
    
    private static final void createTables(Statement ps, Config config)
            throws Exception {
        Module documents, shell, login;
        Map<String, Table> tables;
        byte dbtype = DBNames.names.get(config.dbtype);
        List<String> sqllist = new ArrayList<>();
        
        sqllist.addAll(new Core(dbtype).install());

        documents = new Documents(dbtype);
        sqllist.addAll(documents.install());
        tables = documents.getTables();
        
        shell = new Shell(dbtype);
        shell.putTables(tables);
        sqllist.addAll(shell.install());
        
        login = new Login(dbtype);
        login.putTables(tables);
        sqllist.addAll(login.install());
        
        for (String sql : sqllist)
            ps.addBatch(sql);
    }
    
    private static final String[] getDBInitializator(Config config) {
        String[] init = null;
        
        switch (DBNames.names.get(config.dbtype)) {
        case DBNames.MSSQL:
            config.dbdriver = DRIVERS[MSSQL];
            config.iurl = "jdbc:sqlserver://".concat(config.host);
            config.url = new StringBuilder(config.iurl).
                    append(";databaseName=").
                    append(config.dbname).toString();
            
            switch (config.option) {
            case DBConfig.CHANGE_BASE:
                init = new String[1];
                init[0] = MSSQL_INIT[4].concat(config.dbname);
                
                break;
            case DBConfig.NEW_BASE:
                init = new String[4];
                init[0] = MSSQL_INIT[0];
                init[1] = new StringBuilder(MSSQL_INIT[1]).
                        append(config.dbname).
                        append("') ").
                        append(MSSQL_INIT[2]).
                        append(config.dbname).
                        append(";").toString();
                init[2] = new StringBuilder(MSSQL_INIT[3]).
                        append(config.dbname).
                        append(";").toString();
                init[3] = MSSQL_INIT[4].concat(config.dbname);
                
                break;
            }
            
            break;
        case DBNames.HSQLDB:
            config.dbdriver = DRIVERS[HSQLDB];
            config.iurl = new StringBuilder("jdbc:hsqldb:hsql://").
                    append(config.host).
                    append("/").
                    append(config.dbname).toString();
            config.url = config.iurl;
            
            switch (config.option) {
            case DBConfig.NEW_BASE:
                init = new String[1];
                init[0] = "drop schema public cascade";
                
                break;
            }
            
            break;
        case DBNames.MYSQL:
            config.dbdriver = DRIVERS[MYSQL];
            config.iurl = "jdbc:mysql://".concat(config.host);
            config.url = new StringBuilder(config.iurl).
                    append("/").
                    append(config.dbname).toString();
            
            switch (config.option) {
            case DBConfig.KEEP_BASE:
                init = new String[1];
                init[0] = MYSQL_INIT[2].concat(config.dbname);
                
                break;
            case DBConfig.NEW_BASE:
                init = new String[3];
                init[0] = MYSQL_INIT[0].concat(config.dbname);
                init[1] = MYSQL_INIT[1].concat(config.dbname);
                init[2] = MYSQL_INIT[2].concat(config.dbname);
                
                break;
            }
            
            break;
        }
        
        return init;
    }
    
    private static final void saveConfig(Config config) throws Exception {
        File file;
        BufferedWriter writer;
        Properties properties = new Properties();
        String path = new StringBuilder(System.getProperty("user.home")).
                append(File.separator).
                append(IOCASTE_DIR).toString();
        
        new File(path).mkdir();
        file = new File(new StringBuilder(path).
                append(File.separator).append(CONFIG_FILE).toString());
        writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file)));
        
        properties.put("dbdriver", config.dbdriver);
        properties.put("dbname", config.dbname);
        properties.put("username", config.username);
        properties.put("secret", config.secret);
        properties.put("url", config.url);
        properties.put("host", config.host);
        properties.put("dbtype", config.dbtype.toString());
        properties.store(writer, null);
        
        writer.flush();
        writer.close();
    }
}

class Config {
    public byte option;
    public String iurl, dbdriver, host, url, username, secret, dbname, dbtype;
}

