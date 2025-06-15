package com.falconserver.FalconServer;

import java.util.HashMap;
import java.util.Map;

public class Session {
	
	private final String sessionId;
	private final Map<String, Object> data = new HashMap<>();
	private long lastAccessTime;
	
	private Boolean newSession = false;

	public Session(String id) {
		this.sessionId = id;
		this.update();
	}
	
	public String getId() {
		return sessionId;
	}
	
	public Map<String, Object> getData() {
		return data;
	}
	
	public long getLastAccessTime() {
		return lastAccessTime;
	}
	
	public void update() {
		lastAccessTime = System.currentTimeMillis();
	}
	
	public Boolean getNewSession() {
		return newSession;
	}

	public void setNewSession(Boolean newSession) {
		this.newSession = newSession;
	}
	
	public void setAttribute(String key, Object value) {
		data.put(key, value);
    }

    public Object getAttribute(String key) {
        return data.get(key);
    }

    public void removeAttribute(String key) {
    	data.remove(key);
    }
}
