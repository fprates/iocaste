package org.iocaste.runtime.common.install;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class ApplicationInstall extends AbstractInstallObject {
    private Map<String, Link> links;
    private String pkgname, profilename, programauth;
    private Map<String, TaskGroup> tasksgroups;
    private List<String> dependson;
    
    public ApplicationInstall(String pkgname) {
        this.pkgname = pkgname;
        links = new HashMap<>();
        tasksgroups = new HashMap<>();
        dependson = new ArrayList<>();
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
    
    public final void dependsOn(String pkgname) {
        dependson.add(pkgname);
    }
    
    @Override
    public void execute(InstallContext context) throws Exception {
        String value;
        Link link;
        UserProfile profile;
        Authorization authorization;
        InstallData data = context.getInstallData();

        if (profilename == null)
            throw new IocasteException("profile name undefined.");
        
        data.setDependencies(dependson.toArray(new String[0]));
        
        profile = new UserProfile(profilename);
        data.add(profile);
        
        for (TaskGroup taskgroup : tasksgroups.values())
            data.add(taskgroup);
        
        for (String key : links.keySet()) {
            authorization = new Authorization(key.concat(".CALL"));
            authorization.setAction("CALL");
            authorization.setObject("LINK");
            authorization.add("LINK", key);
            data.add(authorization);
            profile.add(authorization);
            value = ((link = links.get(key)).execmode == null)?
                    link.value : new StringBuilder(link.value).
                        append(" !exec_mode=").append(link.execmode).toString();
            data.link(key, value); 
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
    
    public final String getPackage() {
        return pkgname;
    }
    
    public final void setAuthorization(String programauth) {
        this.programauth = programauth;
    }
    
    public final void setLink(String link, String command) {
        links.put(link, new Link(command, "runtime"));
    }
    
    public final void setProfile(String profile) {
        profilename = profile;
    }
}

class Link {
    public String value, execmode;
    
    public Link(String value, String execmode) {
        this.value = value;
        this.execmode = execmode;
    }
}