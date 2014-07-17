package org.iocaste.install;

public class DBConfig {
    public static final byte KEEP_BASE = 0;
    public static final byte CHANGE_BASE = 1;
    public static final byte NEW_BASE = 2;
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public static final void action(Context context)
            throws Exception {
        Config config = DBConfigRequest.action(context);
        
        if (config.option == CHANGE_BASE)
            return;
        
        Packages.install(context.function);
    }
    
    /**
     * 
     * @param view
     */
    public static final void render(Context context) {
        DBConfigResponse.render(context);
    }
}
