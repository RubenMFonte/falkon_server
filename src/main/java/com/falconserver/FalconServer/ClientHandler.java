package com.falconserver.FalconServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.falconserver.FalconServer.Router.RouteHandler;

public class ClientHandler implements Runnable {

	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try (
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		) {
			
			System.out.println("Thread started: " + Thread.currentThread().getName());
			
			String request = in.readLine();
			
			if(request == null) return;

			String[] requestParts = request.split(" ");
			
			if(requestParts.length != 3) {
				out.write(Utils.getHttpResponse(Utils.STATUSCODE_400, Utils.CONTENTTYPE_PLAIN, "Request with incorrect format!"));
			}
			else {
				
				String method = requestParts[0];
				
				if (!method.equals("GET")) {
				    out.write(Utils.getHttpResponse(Utils.STATUSCODE_405, Utils.CONTENTTYPE_PLAIN, "Method is not supported."));
				    return;
				}
				
				String fullPath = requestParts[1];
				
				String[] pathAndQuery = splitPathAndQuery(fullPath);
				
				String path = pathAndQuery[0];
				String query = null;
				
				if(pathAndQuery.length > 1) query = pathAndQuery[1];
				
				Map<String, String> queryParams = parseQueryParams(query);
				
				RouteHandler handler = Router.getInstance().getRouteHandler(path);
				
				String response = "";
				
				if(handler != null) response = handler.handle(queryParams);
				else response = Utils.getFileContent(path);
					
				out.write(response);
			}
			
			out.flush();
			
		}
		catch (IOException ex) {
			System.out.println("Error handling request/nMessage: " + ex.getMessage());
		}
		
	}
	
	public String[] splitPathAndQuery(String fullPath) {
	    int queryIndex = fullPath.indexOf('?');
	    
	    if (queryIndex >= 0) {
	        String path = fullPath.substring(0, queryIndex);
	        String query = fullPath.substring(queryIndex + 1);
	        return new String[] { path, query };
	    } else {
	        return new String[] { fullPath, null };
	    }
	}

	public Map<String,String> parseQueryParams(String query) {

		Map<String, String> queryParams = new HashMap<>();
		
		if (query != null) {
		    for (String param : query.split("&")) {
		        String[] pair = param.split("=", 2);
		        if (pair.length == 2) {
		            queryParams.put(URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
		                            URLDecoder.decode(pair[1], StandardCharsets.UTF_8));
		        }
		    }
		}
		
		return queryParams;
	}
}
