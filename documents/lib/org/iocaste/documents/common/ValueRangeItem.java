package org.iocaste.documents.common;

public class ValueRangeItem {
    private RangeSign sign;
    private RangeOption option;
    private Object low;
    private Object high;
    
    public final Object getHigh() {
        return high;
    }
    
    public final Object getLow() {
        return low;
    }
    
    public final RangeOption getOption() {
        return option;
    }
    
    public final RangeSign getSign() {
        return sign;
    }
    
    public final void setHigh(Object high) {
        this.high = high;
    }
    
    public final void setLow(Object low) {
        this.low = low;
    }
    
    public final void setOption(RangeOption option) {
        this.option = option;
    }
    
    public final void setSign(RangeSign sign) {
        this.sign = sign;
    }
}
