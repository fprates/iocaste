package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class PageBuilderDefaultInstall extends AbstractInstallObject {
    private Map<String, String> links;
    private String pkgname, profilename, programauth;
    private Map<String, TaskGroup> tasksgroups;
    private List<AppBuilderLink> applinks;
    
    public PageBuilderDefaultInstall(String pkgname) {
        this.pkgname = pkgname;
        links = new HashMap<>();
        tasksgroups = new HashMap<>();
        applinks = new ArrayList<>();
    }
    
    public final void addToTaskGroup(String name, String... links) {
        TaskGroup taskgroup;
        
        taskgroup = tasksgroups.get(name);
        if (taskgroup == null) {
            taskgroup = new TaskGroup(name);
            tasksgroups.put(name, taskgroup);
        }
        
        for (String link : links)
            taskgroup.add(link);
    }
    
    private final String buildapplink(AppBuilderLink applink, String action) {
        StringBuilder sb = new StringBuilder("iocaste-appbuilder @").
                append(applink.entity).append(action).
                append(" name=").append(applink.entity).
                append(" cmodel=").append(applink.cmodel);
        
        if (applink.number != null)
            sb.append(" number=").append(applink.number);
        
        return sb.toString();
    }
    
    public AppBuilderLink builderLinkInstance() {
        AppBuilderLink link = new AppBuilderLink();
        applinks.add(link);
        return link;
    }
    
    @Override
    public void execute(StandardInstallContext context) throws Exception {
        UserProfile profile;
        Authorization authorization;
        InstallData data = context.getInstallData();

        if (profilename == null)
            throw new IocasteException("profile name undefined.");
        
        profile = new UserProfile(profilename);
        data.add(profile);
        
        for (TaskGroup taskgroup : tasksgroups.values())
            data.add(taskgroup);
        
        for (AppBuilderLink applink : applinks) {
            links.put(applink.create, buildapplink(applink, "create"));
            links.put(applink.display, buildapplink(applink, "display"));
            links.put(applink.change, buildapplink(applink, "edit"));
            
            if (applink.taskgroup == null)
                continue;
            
            addToTaskGroup(applink.taskgroup, applink.create);
            addToTaskGroup(applink.taskgroup, applink.display);
            addToTaskGroup(applink.taskgroup, applink.change);
        }
        
        for (String link : links.keySet()) {
            authorization = new Authorization(link.concat(".CALL"));
            authorization.setAction("CALL");
            authorization.setObject("LINK");
            authorization.add("LINK", link);
            data.add(authorization);
            profile.add(authorization);
            data.link(link, links.get(link));
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
    
    public final void setLink(String link, String command) {
        links.put(link, command);
    }
    
    public final void setProfile(String profile) {
        profilename = profile;
    }
    
    public final void setProgramAuthorization(String programauth) {
        this.programauth = programauth;
    }
}
