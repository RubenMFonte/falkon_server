package com.falconserver.FalconServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Router {

    private static final Router INSTANCE = new Router();
    
    public interface RouteHandler {
    	String handle(Map<String,String> queryParams,  Session clientSession,String body); 
    }

    private final Map<String, RouteHandler> routes;

    private Router() {
        Map<String, RouteHandler> r = new HashMap<>();

        // Greet route
        r.put("/greet", (params,session,body) -> {
        	String name = params.getOrDefault("name", "stranger");
        	return Utils.getHttpResponse("200 OK", "text/plain", "Hello " + name + "!");
        });
        
        // Time route
        r.put("/time", (params,session,body) -> {
            String time = java.time.LocalDateTime.now().toString();
            return Utils.getHttpResponse("200 OK", "text/plain", time);
        });
        
        // Echo route
        r.put("/echo", (params,session,body) -> {
            return Utils.getHttpResponse("200 OK", "text/plain", "You posted:\n" + body);
        });

        routes = Collections.unmodifiableMap(r);
    }

    public static Router getInstance() {
        return INSTANCE;
    }

    public RouteHandler getRouteHandler(String path) {
        return routes.get(path);
    }
}