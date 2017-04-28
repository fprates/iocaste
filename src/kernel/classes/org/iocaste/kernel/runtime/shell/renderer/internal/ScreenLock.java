package org.iocaste.kernel.runtime.shell.renderer.internal;

import org.iocaste.protocol.utils.XMLElement;

public class ScreenLock {

    public static final XMLElement render() {
        XMLElement div = new XMLElement("div");
        
        div.add("id", "screen_lock");
        div.add("class", "");
        div.addInner("");
        return div;
    }
}
