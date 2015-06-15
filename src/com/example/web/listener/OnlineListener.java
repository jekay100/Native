package com.example.web.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * 在线用户监听器
 */
public class OnlineListener implements HttpSessionAttributeListener {
	public static Map<Object, OnlineModel> onlines = new HashMap<>();
	
    public OnlineListener() {
    	
    }

    public void attributeRemoved(HttpSessionBindingEvent se)  { 
    	if("user".equals(se.getName())) {
    		OnlineModel model = (OnlineModel) se.getValue();
    		Object userId = model.getUserId();
    		if(!onlines.containsKey(userId)) {
    			onlines.remove(userId);
    		}
    	}
    }

    public void attributeAdded(HttpSessionBindingEvent se)  { 
    	if("user".equals(se.getName())) {
    		OnlineModel model = (OnlineModel) se.getValue();
    		Object userId = model.getUserId();
    		if(!onlines.containsKey(userId)) {
    			onlines.put(userId, model);
    		}
    	}
    }

    public void attributeReplaced(HttpSessionBindingEvent se)  { 
    	
    }
	
    public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		application.setAttribute("onlines", onlines);
	}
    
}
