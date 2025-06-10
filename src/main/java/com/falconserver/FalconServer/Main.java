package com.falconserver.FalconServer;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	final static int PORT = 8080;
	final static int THREAD_COUNT = 10;
	
	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			
			System.out.println("Server started on port: " + Integer.toString(PORT));
			
			ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
			
			while(true) {

				try {
					Socket clientSocket = serverSocket.accept();
					threadPool.execute(new ClientHandler(clientSocket));
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
