package org.iocaste.kernel.runtime.shell.elements;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.shell.common.AbstractElement;
import org.iocaste.shell.common.Calendar;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.EventHandler;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.MultipageContainer;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.View;

public abstract class ToolDataElement implements Component, Container,
        InputComponent, ControlComponent, MultipageContainer {
	private static final long serialVersionUID = 4217306786401069911L;
	protected ToolData tooldata;
	protected ViewContext viewctx;
    private DocumentModelItem modelitem;
    private Map<String, String> elements;
    private String htmlname, master;
    private boolean translatable, nohelper, stacking, multipart;
    private boolean popup, range;
    private Calendar calendar;
    private byte[] content;
    private SearchHelp search;
    private Object ns;
    private EventHandler evhandler;
    
    public ToolDataElement(ViewContext viewctx, Const type, ToolData tooldata)
    {
		this.tooldata = tooldata;
		this.viewctx = viewctx;
		htmlname = tooldata.name;
		translatable = true;
        elements = new LinkedHashMap<>();
        if (isControlComponent() && (tooldata.actionname == null))
            tooldata.actionname = tooldata.name;
		if (tooldata.componenttype == null)
			tooldata.componenttype = type;
		if (tooldata.style == null)
			tooldata.style = type.style();
        if (tooldata.parent == null)
        	viewctx.view.add(this);
        else
        	((Container)getElement(tooldata.parent)).add(this);
    }
    
	public ToolDataElement(ViewContext viewctx, Const type, String name) {
		this(viewctx, type, viewctx.entries.get(name).data);
	}

	@Override
	public void add(Element element) {
	    append(element);
	}
	
    public final void add(Element element, Const childtype) {
        if ((tooldata.pane == null) && (element.getType() == childtype))
            tooldata.pane = element.getHtmlName();
        append(element);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#addAttribute(
     *    java.lang.String, java.lang.String)
     */
    @Override
    public final void addAttribute(String name, String value) {
    	tooldata.attributes.put(name, value);
    }

    @Override
    public boolean allowStacking() {
        return stacking;
    }

    private final void append(Element element) {
        String name = element.getHtmlName();
        tooldata.instance(name);
        elements.put(name, element.getHtmlName());
        getView().index(element);
    }
    
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public final int compareTo(Element element) {
        if (equals(element))
            return 0;
        
        return tooldata.name.compareTo(element.getName());
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object object) {
        AbstractElement element;
        
        if (object == this)
            return true;
        
        if (!(object instanceof AbstractElement))
            return false;
        
        element = (AbstractElement)object;
        if (!tooldata.name.equals(element.getHtmlName()))
            return false;
        
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#get()
     */
    @SuppressWarnings("unchecked")
    @Override
    public final <T> T get() {
        return (T)tooldata.value;
    }
    
    public final String getAction() {
        return tooldata.actionname;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getAttributes()
     */
    @Override
    public final Map<String, String> getAttributes() {
        return tooldata.attributes;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getb()
     */
    @Override
    public final byte getb() {
        if (!(tooldata.value instanceof BigDecimal))
            return (tooldata.value == null)? 0 : (byte)tooldata.value;
        
        return ((BigDecimal)tooldata.value).byteValue();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getCalendar()
     */
    @Override
    public final Calendar getCalendar() {
        return calendar;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getComponentType()
     */
    @Override
    public final Const getComponentType() {
        return tooldata.componenttype;
    }

    public final byte[] getContent() {
        return content;
    }

	@Override
	public Container getContainer() {
		return viewctx.view.getElement(tooldata.parent);
	}

    @Override
    public String getCurrentPage() {
        return tooldata.pane;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getd()
     */
    @Override
    public final double getd() {
        if (!(tooldata.value instanceof BigDecimal))
            return (tooldata.value == null)? 0 : (double)tooldata.value;
        
        return ((BigDecimal)tooldata.value).doubleValue();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getDataElement()
     */
    @Override
    public final DataElement getDataElement() {
        return tooldata.element;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getdt()
     */
    @Override
    public final Date getdt() {
        return (Date)tooldata.value;
    }

	@Override
	public <T extends Element> T getElement(String name) {
		return viewctx.view.getElement(name);
	}

    @Override
    @SuppressWarnings("unchecked")
	public <T extends Element> Set<T> getElements() {
        Set<Element> children = new LinkedHashSet<>();
        View view = getView();
        
        for (String name : elements.values())
            children.add(view.getElement(name));
        
        return (Set<T>)children;
	}

	@Override
	public String[] getElementsNames() {
		return elements.keySet().toArray(new String[0]);
	}
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getEventHandler()
     */
    @Override
    public final EventHandler getEventHandler() {
        return evhandler;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getEvents()
     */
    @Override
    public final Map<String, String> getEvents() {
        return tooldata.events;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getHtmlName()
     */
    @Override
    public final String getHtmlName() {
        return htmlname;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#geti()
     */
    @Override
    public final int geti() {
        if (!(tooldata.value instanceof BigDecimal))
            return (tooldata.value == null)? 0 : (int)tooldata.value;
        
        return ((BigDecimal)tooldata.value).intValue();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getl()
     */
    @Override
    public final long getl() {
        if (!(tooldata.value instanceof BigDecimal))
            return (tooldata.value == null)? 0 : (long)tooldata.value;
        
        return ((BigDecimal)tooldata.value).longValue();
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getLabel()
     */
    @Override
    public final String getLabel() {
        return tooldata.label;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getLength()
     */
    @Override
    public final int getLength() {
        return tooldata.length;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getLocale()
     */
    @Override
    public final Locale getLocale() {
        return viewctx.view.getLocale();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getMaster()
     */
    @Override
    public final String getMaster() {
        return master;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getModelItem()
     */
    @Override
    public final DocumentModelItem getModelItem() {
        return modelitem;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getName()
     */
    @Override
    public final String getName() {
        return tooldata.name;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getNS()
     */
    @Override
    public final Object getNS() {
        return tooldata.ns;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getNSReference()
     */
    @Override
    public final String getNSReference() {
        return tooldata.nsitem.name;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getSearchHelp()
     */
    @Override
    public final SearchHelp getSearchHelp() {
        return search;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getst()
     */
    @Override
    public final String getst() {
        return (String)tooldata.value;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getStackComponents()
     */
    @Override
    public Set<InputComponent> getStackComponents() {
        return null;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.kernel.shell.elements.Element#getStyleClass()
     */
    @Override
	public final String getStyleClass() {
		return tooldata.style;
	}
	
    /*
     * (non-Javadoc)
     * @see org.iocaste.kernel.shell.elements.Component#getText()
     */
    @Override
    public final String getText() {
    	return tooldata.text;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.kernel.shell.elements.Component#getTextArgs()
     */
    @Override
    public final Object[] getTextArgs() {
    	return tooldata.textargs;
    }
    
    protected final String getTranslation(MessageSource messages, String name) {
        String message;
        String locale = getView().getLocale().toString();
        
        messages.instance(locale);
        message = messages.get(
        		(tooldata.text != null)? tooldata.text : getName());
        if ((message == null) && (name != null))
            message = messages.get(name);
        if (message == null)
            return null;
        return (tooldata.textargs == null || tooldata.textargs.length == 0)?
                message : String.format(message, tooldata.textargs);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getType()
     */
    @Override
    public final Const getType() {
        return tooldata.componenttype;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getView()
     */
    @Override
    public final View getView() {
        return viewctx.view;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getVisibleLength()
     */
    @Override
    public final int getVisibleLength() {
        return tooldata.vlength;
    }
    
    /*
     * 
     */
    @Override
    public final boolean hasNoHelper() {
        return nohelper;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#hasPlaceHolder()
     */
    @Override
    public final boolean hasPlaceHolder() {
        return tooldata.internallabel;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return tooldata.name.hashCode();
    }

	@Override
	public boolean hasMultipartSupport() {
		return multipart;
	}
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isBooleanComponent()
     */
    @Override
    public boolean isBooleanComponent() {
        return false;
    }

    @Override
    public boolean isCancellable() {
        return tooldata.cancellable;
    }
	
	/*
	 * (non-Javadoc)
	 * @see org.iocaste.kernel.shell.elements.Element#isContainable()
	 */
	@Override
	public boolean isContainable() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.iocaste.kernel.shell.elements.Element#isControlComponent()
	 */
	@Override
	public boolean isControlComponent() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.iocaste.kernel.shell.elements.Element#isDataStorable()
	 */
	@Override
	public boolean isDataStorable() {
		return false;
	}
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return !tooldata.disabled;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.kernel.shell.elements.Container#isMultiLine()
     */
	@Override
	public boolean isMultiLine() {
		return false;
	}
    
	/*
	 * (non-Javadoc)
	 * @see org.iocaste.kernel.shell.elements.Element#isMultipage()
	 */
    @Override
    public boolean isMultipage() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isObligatory()
     */
    @Override
    public final boolean isObligatory() {
        return tooldata.required;
    }

    @Override
    public boolean isPopup() {
        return popup;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Element#isRemote()
     */
    @Override
    public boolean isRemote() {
        return false;
    }
    
    public boolean isScreenLockable() {
        return !tooldata.nolock;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isSecret()
     */
    @Override
    public final boolean isSecret() {
        return tooldata.secret;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isSelectable()
     */
    @Override
    public boolean isSelectable() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isSelected()
     */
    @Override
    public final boolean isSelected() {
        Object value = get();
        
        return (value == null)? false : (Boolean)value;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isStackable()
     */
    @Override
    public boolean isStackable() {
        return false;
    }
    
    protected final boolean isTranslatable() {
        return translatable;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isValueRangeComponent()
     */
    @Override
    public boolean isValueRangeComponent() {
        return range;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isVisible()
     */
    @Override
    public boolean isVisible() {
        return !tooldata.invisible;
    }

	@Override
	public void remove(Element element) {
		// TODO Auto-generated method stub
		
	}
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#set(java.lang.Object)
     */
    @Override
    public void set(Object value) {
        tooldata.value = value;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#set(
     *     java.lang.Object, java.lang.Object)
     */
    @Override
    public void set(Object ns, Object value) {
        this.ns = ns;
        tooldata.value = value;
    }

    @Override
    public void setAction(String action) {
        tooldata.actionname = action;
    }

    @Override
    public void setAllowStacking(boolean stacking) {
        this.stacking = stacking;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setCalendar(
     *    org.iocaste.shell.common.Calendar)
     */
    @Override
    public final void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        
        if (calendar == null)
            return;
        
        this.calendar.setInputName(getHtmlName());
    }

    @Override
    public void setCancellable(boolean cancellable) {
        tooldata.cancellable = cancellable;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setComponentType(
     *     org.iocaste.shell.common.Const)
     */
    @Override
    public void setComponentType(Const type) {
        tooldata.componenttype = type;
    }
    
    public final void setContent(byte[] content) {
        this.content = content;
    }
    
    @Override
    public final void setCurrentPage(String page) {
        tooldata.pane = page;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setDataElement(
     *     org.iocaste.documents.common.DataElement)
     */
    @Override
    public final void setDataElement(DataElement dataelement) {
        tooldata.element = dataelement;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
    	tooldata.disabled = !enabled;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setEvent(
     *    java.lang.String, java.lang.String)
     */
    @Override
    public final void setEvent(String name, String js) {
//        events.put(name, js);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#
     *     setEventHandler(org.iocaste.shell.common.EventHandler)
     */
    @Override
    public final void setEventHandler(EventHandler evhandler) {
        this.evhandler = evhandler;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setHtmlName(java.lang.String)
     */
    @Override
    public void setHtmlName(String htmlname) {
        viewctx.view.remove(this);
        this.htmlname = htmlname;
        viewctx.view.index(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setLabel(
     *     java.lang.String)
     */
    @Override
    public final void setLabel(String label) {
        tooldata.label = label;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setLength(int)
     */
    @Override
    public final void setLength(int length) {
        tooldata.length = length;
    }
    
    /**
     * 
     * @param master
     */
    protected final void setMaster(String master) {
        this.master = master;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#setModelItem(
     *     org.iocaste.documents.common.DocumentModelItem)
     */
    @Override
    public final void setModelItem(DocumentModelItem modelitem) {
        this.modelitem = modelitem;
    }

    /*
     * 
     */
    @Override
    public final void setNoHelper(boolean nohelper) {
        this.nohelper = nohelper;
    }

    @Override
    public void setNoScreenLock(boolean nolock) {
        tooldata.nolock = nolock;
    }
    
    /**
     * 
     * @param ns
     */
    protected final void setNS(Object ns) {
        this.ns = ns;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setNSReference(
     *    java.lang.String)
     */
    @Override
    public final void setNSReference(String nsreference) {
        
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setObligatory(boolean)
     */
    @Override
    public final void setObligatory(boolean obligatory) {
        tooldata.required = obligatory;
    }
    
    @Override
    public final void setPlaceHolder(boolean placeholder) {
        tooldata.internallabel = placeholder;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setSearchHelp(
     *     org.iocaste.shell.common.SearchHelp)
     */
    @Override
    public final void setSearchHelp(SearchHelp search) {
        this.search = search;
        
        if (search == null)
            return;
        
        this.search.setInputName(getHtmlName());
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setSecret(boolean)
     */
    @Override
    public final void setSecret(boolean secret) {
        tooldata.secret = secret;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setSelected(boolean)
     */
    @Override
    public final void setSelected(boolean selected) {
        set(selected);
    };
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setVisibleLength(int)
     */
    @Override
    public final void setVisibleLength(int vlength) {
        tooldata.vlength = vlength;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setStyleClass(java.lang.String)
     */
    @Override
    public final void setStyleClass(String style) {
        tooldata.style = style;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.kernel.shell.elements.Component#setText(
     *    java.lang.String, java.lang.Object[])
     */
    @Override
    public final void setText(String text, Object... args) {
    	tooldata.text = text;
    	tooldata.textargs = args;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setTranslatable(boolean)
     */
    @Override
    public final void setTranslatable(boolean translatable) {
        this.translatable = translatable;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setView(
     *     org.iocaste.shell.common.ViewData)
     */
    @Override
    public void setView(View view) {
        viewctx.view = view;
    }

	@Override
	public void setVisible(boolean visible) {
	    tooldata.invisible = !visible;
	}

	@Override
	public int size() {
		return tooldata.size();
	}
    
    /*
     * (não-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return tooldata.name;
    }

	@Override
	public void translate(MessageSource messages) {
        String message;
        
        if (!isTranslatable())
            return;
        
        message = getTranslation(messages, getName());
        if (message == null)
            return;
        
        tooldata.text = message;
	}

}
