package com.falconserver.FalconServer;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public class RouterTest {

    @Test
    public void testGreetRouteWithName() {
        Router.RouteHandler handler = Router.getInstance().getRouteHandler("/greet");

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "Falcon");

        String response = handler.handle(queryParams, null, null);

        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains("Hello Falcon!"));
    }

    @Test
    public void testGreetRouteWithoutName() {
        Router.RouteHandler handler = Router.getInstance().getRouteHandler("/greet");

        String response = handler.handle(Collections.emptyMap(), null, null);

        assertTrue(response.contains("Hello stranger"));
    }

    @Test
    public void testTimeRoute() {
        Router.RouteHandler handler = Router.getInstance().getRouteHandler("/time");

        String response = handler.handle(Collections.emptyMap(), null, null);

        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains("text/plain"));
        assertFalse(response.contains("null"));
    }

    @Test
    public void testEchoRoute() {
        Router.RouteHandler handler = Router.getInstance().getRouteHandler("/echo");

        String response = handler.handle(Collections.emptyMap(), null, "This is a test");

        assertTrue(response.contains("You posted:"));
        assertTrue(response.contains("This is a test"));
    }

    @Test
    public void testUnknownRoute() {
        Router.RouteHandler handler = Router.getInstance().getRouteHandler("/nonexistent");

        assertNull(handler);
    }
}