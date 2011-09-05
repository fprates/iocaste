package org.iocaste.shell.common;

public class FileEntry extends AbstractInputComponent {
    private static final long serialVersionUID = -3285030860250606539L;
    
    public FileEntry(Container container, String name) {
        super(container, Const.FILE_ENTRY, name);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractElement#hasMultipartSupport()
     */
    @Override
    public final boolean hasMultipartSupport() {
        return true;
    }
}
