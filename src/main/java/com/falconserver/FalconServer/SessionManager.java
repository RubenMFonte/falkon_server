package com.falconserver.FalconServer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final SessionManager INSTANCE = new SessionManager();
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    
    private final long sessionTimeout = Utils.getMinutesInMillis(30);
    
    private class SessionCleanup implements Runnable {
    	@Override
        public void run() {
            while (true) {
                try {

                    Thread.sleep(Utils.getMinutesInMillis(5));
                    
                    long now = System.currentTimeMillis();
                    sessions.entrySet().removeIf(entry ->
                        now - entry.getValue().getLastAccessTime() > sessionTimeout
                    );
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private SessionManager() {
    	new Thread(new SessionCleanup()).start();
    }

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public Session createSession() {
        String id = UUID.randomUUID().toString();
        Session session = new Session(id);
        sessions.put(id, session);
        return session;
    }

    public Session getSession(String id) {
        Session session = sessions.get(id);
        if (session != null) {
            if (System.currentTimeMillis() - session.getLastAccessTime() > sessionTimeout) {
                sessions.remove(id);
                return null;
            }
            session.update();
        }
        return session;
    }
}
