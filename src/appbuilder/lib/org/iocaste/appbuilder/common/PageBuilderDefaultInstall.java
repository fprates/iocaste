package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class PageBuilderDefaultInstall extends AbstractInstallObject {
    private Map<String, String> links;
    private String pkgname, profilename, taskgroupname, programauth;

    public PageBuilderDefaultInstall(String pkgname) {
        this.pkgname = pkgname;
        links = new HashMap<>();
    }
    
    private final String buildapplink(
            String entity, String action, String cmodel) {
        return new StringBuilder("iocaste-appbuilder @").
                append(entity).append(action).
                append(" name=").append(entity).
                append(" cmodel=").append(cmodel).toString();
    }
    
    @Override
    public void execute(StandardInstallContext context) {
        TaskGroup taskgroup;
        UserProfile profile;
        Authorization authorization;
        InstallData data = context.getInstallData();

        profile = new UserProfile(profilename);
        data.add(profile);
        
        if (taskgroupname != null) {
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
        }
        
        if (programauth == null)
            programauth = pkgname.toUpperCase().concat(".EXECUTE");
        
        authorization = new Authorization(programauth);
        authorization.setAction("EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.add("APPNAME", pkgname);
        data.add(authorization);
        profile.add(authorization);
    }
    
    public final void removeLink(String link) {
        links.remove(link);
    }
    
    public final void setAppBuilderLink(
            String createlink, String displaylink, String changelink,
            String entity, String cmodel) {
        links.put(createlink, buildapplink(entity, "create", cmodel));
        links.put(displaylink, buildapplink(entity, "display", cmodel));
        links.put(changelink, buildapplink(entity, "edit", cmodel));
    }
    
    public final void setLink(String link, String command) {
        links.put(link, command);
    }
    
    public final void setProfile(String profile) {
        profilename = profile;
    }
    
    public final void setProgramAuthorization(String programauth) {
        this.programauth = programauth;
    }
    
    public final void setTaskGroup(String taskgroup) {
        taskgroupname = taskgroup;
    }
}
