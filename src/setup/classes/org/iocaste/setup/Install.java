package org.iocaste.setup;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;

public class Install {

    /**
     * 
     * @param function
     * @return
     */
    public static final InstallData init(Function function) {
        InstallData data = new InstallData();
        Config config = new Config();
        
        config.function = function;
        CModel.install(data, config);
        GlobalConfig.install(data);
        Shell.install(data);
        TextEditor.install(data);
        ApplicationBuilder.install(data);
        
        return data;
    }
}
