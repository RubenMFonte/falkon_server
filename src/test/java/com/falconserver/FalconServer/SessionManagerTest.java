package com.falconserver.FalconServer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SessionManagerTest {

    @Test
    public void testSessionCreationReturnsValidSessionId() {
        SessionManager manager = SessionManager.getInstance();
        Session session = manager.createSession();

        assertNotNull(session);
        assertNotNull(session.getId());

        Session fetched = manager.getSession(session.getId());
        assertNotNull(fetched);
        assertEquals(session.getId(), fetched.getId());
    }

    @Test
    public void testSetAndGetSessionData() {
        SessionManager manager = SessionManager.getInstance();
        Session session = manager.createSession();

        session.setAttribute("user", "alice");
        assertEquals("alice", session.getAttribute("user"));

        session.setAttribute("role", "admin");
        assertEquals("admin", session.getAttribute("role"));
    }
}