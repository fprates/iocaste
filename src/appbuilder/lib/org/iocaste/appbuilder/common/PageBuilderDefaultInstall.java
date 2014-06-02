package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class PageBuilderDefaultInstall extends AbstractInstallObject {
    private Map<String, String> links;
    private String pkgname, profilename, taskgroupname;

    public PageBuilderDefaultInstall(String pkgname) {
        this.pkgname = pkgname;
        links = new HashMap<>();
    }
    
    @Override
    public void execute(StandardInstallContext context) {
        TaskGroup taskgroup;
        UserProfile profile;
        String programupcase;
        Authorization authorization;
        InstallData data = context.getInstallData();

        profile = new UserProfile(profilename);
        data.add(profile);
        
        taskgroup = new TaskGroup(taskgroupname);
        data.add(taskgroup);
        
        for (String link : links.keySet()) {
            authorization = new Authorization(link.concat(".CALL"));
            authorization.setAction("CALL");
            authorization.setObject("LINK");
            authorization.add("LINK", link);
            data.add(authorization);
            profile.add(authorization);
            
            data.link(link, links.get(link));
            taskgroup.add(link);
        }
        
        programupcase = pkgname.toUpperCase();
        authorization = new Authorization(programupcase.concat("EXECUTE"));
        authorization.setAction("EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.add("APPNAME", pkgname);
        data.add(authorization);
        profile.add(authorization);
    }
    
    public final void removeLink(String link) {
        links.remove(link);
    }
    
    public final void setLink(String link, String command) {
        links.put(link, command);
    }
    
    public final void setProfile(String profile) {
        profilename = profile;
    }
    
    public final void setTaskGroup(String taskgroup) {
        taskgroupname = taskgroup;
    }
}
