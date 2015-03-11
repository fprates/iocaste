package org.iocaste.shell.common;


public class RangeFieldPair extends TextField {
    private static final long serialVersionUID = -600304346959044282L;
    
    public RangeFieldPair(View view, String name) {
        super(view, name);
    }
    
    @Override
    public final boolean isValueRangeComponent() {
        return true;
    }
    
    public final void setMaster(RangeInputComponent rangeinput) {
        setMaster(rangeinput.getHtmlName());
    }
}
