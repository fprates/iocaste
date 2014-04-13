package org.iocaste.masterdata;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.UserProfile;

public class InstallContext {
    public InstallData data;
    public DocumentModelItem currency, countryid;
    public TaskGroup group;
    public UserProfile profile;
    
    public InstallContext() {
        data = new InstallData();
        
        group = new TaskGroup("MASTER_DATA");
        data.add(group);

        profile = new UserProfile("MD_MAINTAIN");
        data.add(profile);
    }
}
