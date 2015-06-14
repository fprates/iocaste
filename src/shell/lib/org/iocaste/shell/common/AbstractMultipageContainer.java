package org.iocaste.shell.common;

public abstract class AbstractMultipageContainer extends AbstractContainer
        implements MultipageContainer {
    private static final long serialVersionUID = -3003218665482940728L;
    private String current;
    
    public AbstractMultipageContainer(Container container, Const type,
            String name) {
        super(container, type, name);
    }
    
    public AbstractMultipageContainer(View view, Const type,
            String name) {
        super(view, type, name);
    }
    
    public final void add(Element element, Const childtype) {
        if ((current == null) && (element.getType() == childtype))
            current = element.getHtmlName();
        super.add(element);
    }

    @Override
    public final String getCurrentPage() {
        return current;
    }

    @Override
    public final boolean isMultipage() {
        return true;
    }
    
    public final void setCurrentPage(String current) {
        this.current = current;
    }
}
