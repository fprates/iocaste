package org.iocaste.packagetool.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;
import org.iocaste.protocol.user.UserProfile;

public class InstallData implements Serializable {
    private static final long serialVersionUID = -4509980464670421174L;
    private Map<String, DocumentModel> models;
    private Map<DocumentModel, List<Object[]>> values;
    private Map<String, String> links;
    private Set<String> numbers;
    private List<SearchHelpData> shds;
    private List<DataElement> elements;
    private Map<String, Map<String, String>> messages;
    private List<Authorization> authorizations;
    private Map<TaskGroup, Set<User>> tasksgroups;
    private String[] dependencies;
    private Set<ComplexModel> cmodels;
    private Set<User> users;
    private Map<UserProfile, Set<User>> uprofiles;
    private Set<GlobalConfigData> globalcfg;
    
    public InstallData() {
        models = new LinkedHashMap<>();
        values = new HashMap<>();
        links = new HashMap<>();
        numbers = new HashSet<>();
        shds = new ArrayList<>();
        elements = new ArrayList<>();
        messages = new HashMap<>();
        authorizations = new ArrayList<>();
        tasksgroups = new HashMap<>();
        cmodels = new TreeSet<>();
        users = new TreeSet<>();
        uprofiles = new HashMap<>();
        globalcfg = new HashSet<>();
    }
    
    /**
     * 
     * @param authorization
     */
    public final void add(Authorization authorization) {
        authorizations.add(authorization);
    }
    
    /**
     * 
     * @param cmodel
     */
    public final void add(ComplexModel cmodel) {
        cmodels.add(cmodel);
    }
    
    /**
     * 
     * @param element
     */
    public final void add(DataElement element) {
        elements.add(element);
    }
    
    /**
     * 
     * @param model
     */
    public final void add(DocumentModel model) {
        String name = model.getName();
        
        if (models.containsKey(name))
            throw new RuntimeException("can't add a duplicated model.");
        
        models.put(name, model);
    }
    
    /**
     * 
     * @param config
     */
    public final void add(GlobalConfigData config) {
        globalcfg.add(config);
    }
    
    /**
     * 
     * @param shd
     */
    public final void add(SearchHelpData shd) {
        shds.add(shd);
    }
    
    /**
     * 
     * @param user
     */
    public final void add(User user) {
        users.add(user);
    }
    
    /**
     * 
     * @param uprofile
     */
    public final void add(UserProfile uprofile) {
        uprofiles.put(uprofile, new TreeSet<User>());
    }
    
    /**
     * 
     * @param group
     * @param task
     */
    public final void add(TaskGroup taskgroup) {
        tasksgroups.put(taskgroup, new TreeSet<User>());
    }
    
    /**
     * 
     * @param name
     */
    public final void addNumberFactory(String name) {
        numbers.add(name);
    }
    
    /**
     * 
     * @param model
     * @param values
     */
    public final void addValues(DocumentModel model, Object... values) {
        List<Object[]> list;
        
        if (this.values.containsKey(model)) {
            list = this.values.get(model);
        } else {
            list = new ArrayList<Object[]>();
            this.values.put(model, list);
        }
        
        list.add(values);
        this.values.put(model, list);
    }
    
    /**
     * 
     * @param profile
     * @param user
     */
    public final void assign(UserProfile profile, User user) {
        uprofiles.get(profile).add(user);
    }
    
    /**
     * 
     * @param taskgroup
     * @param user
     */
    public final void assign(TaskGroup taskgroup, User user) {
        tasksgroups.get(taskgroup).add(user);
    }
    
    /**
     * 
     * @return
     */
    public final Authorization[] getAuthorizations() {
        return authorizations.toArray(new Authorization[0]);
    }
    
    /**
     * 
     * @return
     */
    public final ComplexModel[] getCModels() {
        return cmodels.toArray(new ComplexModel[0]);
    }
    
    /**
     * 
     * @return
     */
    public final String[] getDependencies() {
        return dependencies;
    }
    
    /**
     * 
     * @return
     */
    public final List<DataElement> getElements() {
        return elements;
    }
    
    /**
     * 
     * @return
     */
    public final Set<GlobalConfigData> getGlobalConfigs() {
        return globalcfg;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, String> getLinks() {
        return links;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Map<String, String>> getMessages() {
        return messages;
    }
    
    /**
     * 
     * @param name
     * @param tablename
     * @param classname
     * @return
     */
    public final DocumentModel getModel(String name, String tablename,
            String classname) {
        DocumentModel model = new DocumentModel(name);
        
        model.setTableName(tablename);
        model.setClassName(classname);
        add(model);
        return model;
    }
    
    /**
     * 
     * @return
     */
    public final DocumentModel[] getModels() {
        return models.values().toArray(new DocumentModel[0]);
    }
    
    /**
     * 
     * @return
     */
    public final String[] getNumberFactories() {
        return numbers.toArray(new String[0]);
    }
    
    /**
     * 
     * @return
     */
    public final SearchHelpData[] getSHData() {
        return shds.toArray(new SearchHelpData[0]);
    }
    
    /**
     * 
     * @return
     */
    public final Map<TaskGroup, Set<User>> getTasksGroups() {
        return tasksgroups;
    }
    
    /**
     * 
     * @return
     */
    public final Map<UserProfile, Set<User>> getUserProfiles() {
        return uprofiles;
    }
    
    /**
     * 
     * @return
     */
    public final Set<User> getUsers() {
        return users;
    }
    
    /**
     * 
     * @param model
     * @return
     */
    public final List<Object[]> getValues(DocumentModel model) {
        return values.get(model);
    }
    
    /**
     * 
     * @param link
     * @param command
     */
    public final void link(String link, String command) {
        links.put(link, command);
    }
    
    /**
     * 
     * @param dependencies
     */
    public final void setDependencies(String... dependencies) {
        this.dependencies = dependencies;
    }
    
    /**
     * 
     * @param language
     * @param messages
     */
    public final void setMessages(String language, Map<String, String> messages)
    {
        this.messages.put(language, messages);
    }
}
