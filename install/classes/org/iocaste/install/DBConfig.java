package org.iocaste.install;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.RadioGroup;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class DBConfig {
    private static final String CONFIG_FILE = "core.properties";
    private static final String MSSQL_DRIVER = 
            "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String HSQLDB_DRIVER = "org.hsqldb.jdbcDriver";
    
    private static final String[] SCRIPTS = {
        "/META-INF/db-install-10-core.sql",
        "/META-INF/db-install-15-documents.sql",
        "/META-INF/db-install-16-numbers.sql",
        "/META-INF/db-install-20-shell.sql",
        "/META-INF/db-install-25-login.sql",
        "/META-INF/db-install-30-sh.sql",
        "/META-INF/db-install-31-sh.sql",
        "/META-INF/db-install-35-package.sql"
    };
    
    private static final String[] LINES = {
        "Informe usuário e senha para criação do banco de dados do sistema.",
        "Todos os dados anteriores serão destruídos."
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
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void action(View view) throws Exception {
        Statement ps;
        Connection connection;
        String[] init = null;
        DataForm dbinfo = view.getElement("dbinfo");
        Config config = new Config();
        RadioButton rb = view.getElement("dbtype");
        
        config.dropbase = dbinfo.get("dropbase").isSelected();
        config.host = dbinfo.get("host").get();
        config.username = dbinfo.get("username").get();
        config.secret = dbinfo.get("secret").get();
        config.dbname = dbinfo.get("dbname").get();
        config.dbname = config.dbname.toUpperCase();
        
        for (RadioButton dbtype : rb.getGroup().getComponents()) {
            if (!dbtype.isSelected())
                continue;
            
            config.dbtype = Config.dbtypes.valueOf(dbtype.getName());
            init = getDBInitializator(config);
            
            break;
        }
        
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
        
        view.redirect("FINISH");
    }
    
    private final void createTables(Statement ps, Config config) throws Exception {
        String line, dbtype;
        InputStream is;
        BufferedReader reader;
        List<String> formated, script;
        
        dbtype = new StringBuilder("@").
                append(config.dbtype.toString()).
                append(":").toString();
        
        script = new ArrayList<String>();
        for (String name : SCRIPTS) {
            is = getClass().getResourceAsStream(name);
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0)
                    continue;
                if (line.startsWith("@")) {
                    if (!line.startsWith(dbtype))
                        continue;
                    line = line.substring(dbtype.length());
                }
                    
                script.add(line);
            }
            
            reader.close();
        }
        
        formated = new ArrayList<String>();
        line = "";
        for (String scriptline : script) {
            for (char c : scriptline.toCharArray()) {
                line += c;
                if (c != ';')
                    continue;
                
                formated.add(line);
                line = "";
                break;
            }
        }
        
        for (String scriptline : formated)
            ps.addBatch(scriptline);
    }
    
    private final String[] getDBInitializator(Config config) {
        String usedatabase;
        String[] init = null;
        
        switch (config.dbtype) {
        case mssql:
            config.dbdriver = MSSQL_DRIVER;
            config.iurl = "jdbc:sqlserver://".concat(config.host);
            config.url = new StringBuilder(config.iurl).
                    append(";databaseName=").
                    append(config.dbname).toString();
            
            usedatabase = new StringBuilder(MSSQL_INIT[4]).
                    append(config.dbname).
                    append(";").toString();
            
            if (!config.dropbase) {
                init = new String[1];
                init[0] = usedatabase;
                
                return init;
            }
            
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
            init[3] = usedatabase;
            
            break;
        case hsqldb:
            config.dbdriver = HSQLDB_DRIVER;
            config.iurl = new StringBuilder("jdbc:hsqldb:hsql://").
                    append(config.host).
                    append("/").
                    append(config.dbname).toString();
            config.url = config.iurl;
            
            if (!config.dropbase)
                return null;
            
            init = new String[1];
            init[0] = "drop schema public cascade";
            
            break;
        case mysql:
            config.dbdriver = MYSQL_DRIVER;
            config.iurl = "jdbc:mysql://".concat(config.host);
            config.url = new StringBuilder(config.iurl).
                    append("/").
                    append(config.dbname).toString();
            
            if (!config.dropbase) {
                init = new String[1];
                init[0] = MYSQL_INIT[2].concat(config.dbname);
                
                return init;
            }
            
            init = new String[3];
            init[0] = MYSQL_INIT[0].concat(config.dbname);
            init[1] = MYSQL_INIT[1].concat(config.dbname);
            init[2] = MYSQL_INIT[2].concat(config.dbname);
            
            break;
        }
        
        return init;
    }
    
    /**
     * 
     * @param view
     */
    public final void render(View view) {
        String name;
        Container dbtypecnt;
        DataItem item;
        DataForm dbinfo;
        Form container = new Form(view, "main");
        RadioGroup rbgroup = new RadioGroup("dbtype");
        
        for (int i = 0; i < LINES.length; i++)
            new Text(container, Integer.toString(i)).setText(LINES[i]);
        
        dbinfo = new DataForm(container, "dbinfo");
        item = new DataItem(dbinfo, Const.TEXT_FIELD, "host");
        item.setLength(100);
        item.setObligatory(true);
        view.setFocus(item);
        
        new DataItem(dbinfo, Const.TEXT_FIELD, "username").setObligatory(true);
        new DataItem(dbinfo, Const.TEXT_FIELD, "secret").setSecret(true);
        new DataItem(dbinfo, Const.TEXT_FIELD, "dbname").setObligatory(true);
        new DataItem(dbinfo, Const.CHECKBOX, "dropbase");
        
        dbtypecnt = new StandardContainer(container, "dbtypecnt");
        for (Config.dbtypes dbtype: Config.dbtypes.values()) {
            name = dbtype.toString();
            new RadioButton(dbtypecnt, name, rbgroup).setText(name);
        }
        
        new Button(container, "continue");
        new Parameter(container, "nextstage").set("DBCREATE");
        
        view.setTitle("db-config");
    }
    
    private final void saveConfig(Config config) throws Exception {
        Properties properties = new Properties();
        String path = new StringBuilder(System.getProperty("user.home")).
                append(System.getProperty("file.separator")).
                append(CONFIG_FILE).toString();
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(
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
    public enum dbtypes {
        hsqldb,
        mssql,
        mysql
    };
    
    public boolean dropbase = false;
    public String iurl = null;
    public String dbdriver = null;
    public String host = null; 
    public String url = null;
    public String username = null;
    public String secret = null;
    public String dbname = null;
    public dbtypes dbtype = null;
}
