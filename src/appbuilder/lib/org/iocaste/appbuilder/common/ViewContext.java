package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.navcontrol.NavControlDesign;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Validator;


public class ViewContext {
    private ViewSpec spec;
    private ViewConfig config;
    private ViewInput input;
    private ViewComponents components;
    private Map<String, ActionHandler> actionhandlers;
    private ExtendedContext extcontext;
    private NavControlDesign ncdesign;
    private boolean updateview;
    private Map<String, Validator> validators;
    private Map<String, Set<Validator>> validables;
    
    public ViewContext(String name) {
        components = new ViewComponents();
        actionhandlers = new HashMap<>();
        validators = new HashMap<>();
        validables = new HashMap<>();
    }
    
    /**
     * 
     * @param action
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T extends ActionHandler> T getActionHandler(
            String action) {
        return (T)actionhandlers.get(action);
    }
    
    /**
     * 
     * @return
     */
    public final Set<String> getActions() {
        return actionhandlers.keySet();
    }
    
    /**
     * 
     * @return
     */
    public final ViewComponents getComponents() {
        return components;
    }
    
    /**
     * 
     * @return
     */
    public final ViewConfig getConfig() {
        return config;
    } 
    
    /**
     * 
     * @return
     */
    public final NavControlDesign getDesign() {
        return ncdesign;
    }
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T extends ExtendedContext> T getExtendedContext() {
        return (T)extcontext;
    }
    
    /**
     * 
     * @return
     */
    public final ViewInput getInput() {
        return input;
    }
    
    /**
     * 
     * @return
     */
    public final ViewSpec getSpec() {
        return spec;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Set<Validator>> getValidables() {
        return validables;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Validator> getValidators() {
        return validators;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isUpdatable() {
        return updateview;
    }
    
    /**
     * 
     * @param action
     * @param handler
     */
    public final void put(String action, ActionHandler handler) {
        actionhandlers.put(action, handler);
    }
    
    /**
     * 
     * @param name
     * @param validator
     */
    public final void put(String name, AbstractExtendedValidator validator) {
        validators.put(name, validator);
    }
    
    /**
     * 
     */
    public final void reset() {
        components.reset();
    }
    
    public final void run(String action, PageBuilderContext context)
            throws Exception {
        context.action = action;
        getActionHandler(action).run(context);
    }
    /**
     * 
     * @param extcontext
     */
    public final void set(ExtendedContext extcontext) {
        this.extcontext = extcontext;
    }
    
    /**
     * 
     * @param spec
     */
    public final void set(ViewSpec spec) {
        this.spec = spec;
    }
    
    /**
     * 
     * @param config
     */
    public final void set(ViewConfig config) {
        this.config = config;
    }
    
    /**
     * 
     * @param input
     */
    public final void set(AbstractViewInput input) {
        this.input = input;
    }
    
    /**
     * 
     * @param ncdesign
     */
    public final void set(NavControlDesign ncdesign) {
        this.ncdesign = ncdesign;
    }
    
    /**
     * 
     * @param updateview
     */
    public final void setUpdate(boolean updateview) {
        this.updateview = updateview;
    }
    
    /**
     * 
     * @param input
     * @param validator
     */
    public final void validate(InputComponent input, String validator) {
        validate(input.getHtmlName(), validator);
    }
    
    /**
     * 
     * @param input
     * @param validator
     */
    public final void validate(String input, String validatorname) {
        Validator validator;
        Set<Validator> validators = validables.get(input);
        
        if (validators == null) {
            validators = new LinkedHashSet<>();
            validables.put(input, validators);
        }
        
        validator = this.validators.get(validatorname);
        if (validator == null)
            throw new RuntimeException(new StringBuilder("validator '").
                    append(validatorname).
                    append("' not registered.").toString());
        validators.add(validator);
    }
}
