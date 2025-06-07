package com.falconserver.FalconServer;

import java.io.*;
import java.net.*;

public class Main {
	
	final static int PORT = 8080;
	
	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			
			System.out.println("Server started on port: " + Integer.toString(PORT));
			
			while(true) {

				try (
						Socket clientSocket = serverSocket.accept();
						BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				) {
					
					String request = in.readLine();
					
					String message = "Received = " + request;
					
					String response = "HTTP/1.1 200 OK\r\n" +
	                        "Content-Type: text/plain\r\n" +
	                        "Content-Length: " + message.length() + "\r\n" +
	                        "Connection: close\r\n" +
	                        "\r\n" +
	                        message;
					
					out.write(response);
					out.flush();
					
				}
				catch (IOException ex) {
					System.out.println("Error handling request/nMessage: " + ex.getMessage());
				}
				
			}
			
		}
		catch (IOException ex) {
			System.out.println("Error on server start/nMessage: " + ex.getMessage());
		}
		
	}

}
