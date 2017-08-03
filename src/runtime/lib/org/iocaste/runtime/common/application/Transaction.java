package org.iocaste.runtime.common.application;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.iocaste.runtime.common.RuntimeEngine;

public class Transaction {
    private String trackid;
    private RuntimeEngine runtime;
    
    public Transaction(RuntimeEngine runtime) {
    	this.runtime = runtime;
    }
    
    public final String begin(HttpServletRequest req) {
        Cookie[] cookies;
        String contextid;
        HttpSession session = req.getSession();
        
        if (!runtime.isValidContext()) {
            try {
            	contextid = runtime.newContext();
            } catch (Exception e) {
            	session.invalidate();
            	throw e;
            }
            trackid = runtime.getTrackId(contextid);
        } else {
            trackid = null;
            cookies = req.getCookies();
            if (cookies != null)
                for (Cookie cookie : cookies)
                	if (cookie.getName().equals("track_id")) {
                		trackid = cookie.getValue();
                		break;
                	}
            contextid = runtime.getContextId(trackid);
        }
        
        return contextid;
    }
    
    public final void finish(HttpServletResponse resp) {
    	Cookie trackidcookie = new Cookie("track_id", trackid);
        trackidcookie.setMaxAge(3600*8);
        resp.addCookie(trackidcookie);
    }
}
