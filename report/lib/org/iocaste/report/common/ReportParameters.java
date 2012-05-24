package org.iocaste.report.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReportParameters implements Serializable {
    private static final long serialVersionUID = -8682482250056475517L;
    public static final byte PDF = 0;
    public static final byte XML = 1;
    private String outputfile;
    private String report;
    private Map<String, Object> attributes;
    private byte format;
    
    public ReportParameters() {
        format = PDF;
        attributes = new HashMap<String, Object>();
    }
    
    public final Map<String, Object> getAttributes() {
        return attributes;
    }
    
    public final byte getContentFormat() {
        return format;
    }
    
    public final String getOutputFile() {
        return outputfile;
    }
    
    public final String getReportFile() {
        return report;
    }
    
    public final void put(String key, Object value) {
        attributes.put(key, value);
    }
    
    public final void setContentFormat(byte format) {
        this.format = format;
    }
    
    public final void setOutputFile(String outputfile) {
        this.outputfile = outputfile; 
    }
    
    public final void setReportFile(String report) {
        this.report = report;
    }

}
