package org.iocaste.shell.common;


public class RangeFieldPair extends TextField {
    private static final long serialVersionUID = -600304346959044282L;
    private static final String HIGH = ".high";
    private static final String LOW = ".low";
    private String lowname, highname;
    
    public RangeFieldPair(View view, String name) {
        super(view, name);
    }
    
    @Override
    public final String getHighHtmlName() {
        return highname;
    }
    
    @Override
    public final String getLowHtmlName() {
        return lowname;
    }
    
    @Override
    public final boolean isValueRangeComponent() {
        return true;
    }
    
    @Override
    public final void setHtmlName(String name) {
        super.setHtmlName(name);
        
        highname = name.concat(HIGH);
        lowname = name.concat(LOW);
    }
    
    public final void setMaster(RangeInputComponent rangeinput) {
        setMaster(rangeinput.getHtmlName());
    }
}
