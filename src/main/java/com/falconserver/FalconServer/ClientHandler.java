package com.falconserver.FalconServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

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
				
				String path = requestParts[1];	
				
				out.write(Utils.getFileContent(path));
			}
			
			out.flush();
			
		}
		catch (IOException ex) {
			System.out.println("Error handling request/nMessage: " + ex.getMessage());
		}
		
	}
	
}
