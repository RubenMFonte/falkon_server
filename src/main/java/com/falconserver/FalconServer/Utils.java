package com.falconserver.FalconServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils {
	final static String CONTENTTYPE_PLAIN = "text/plain";
	final static String CONTENTTYPE_HTML = "text/html";
	final static String CONTENTTYPE_CSS = "text/css";
	final static String CONTENTTYPE_DEFAULT = "application/octet-stream";
	
	final static String STATUSCODE_200 = "200 OK";
	final static String STATUSCODE_400 = "400 Bad Request";
	final static String STATUSCODE_404 = "404 Not Found";
	final static String STATUSCODE_405 = "405 Method Not Allowed";
	final static String STATUSCODE_500 = "500 Internal Server Error";
	
	public static String getFileContent(String path) {
		
		if(path.equals("/")) {
			path = "/index.html";
		}
		else {
			// Check for path out of directory bounds
			try {
				String canonicalPath = new File("public", path).getCanonicalPath();
				String publicDirPath = new File("public").getCanonicalPath();
				
				if(!canonicalPath.startsWith(publicDirPath)) {
					return getHttpResponse(STATUSCODE_404, CONTENTTYPE_PLAIN, "Page not found!");
				}
			} catch (IOException e) {
				return getHttpResponse(STATUSCODE_500, CONTENTTYPE_PLAIN, "Internal Server Error.");
			}
		}

		File file = new File("public", path.equals("/") ? "/index.html" : path.substring(1));
		
		if(file.exists() && !file.isDirectory()) {
			try {
				String body = Files.readString(file.toPath());
				String contentType = getContentType(file.getName());
				
				return getHttpResponse(STATUSCODE_200, contentType, body);
			}
			catch (IOException ex) {
				System.out.println("Error reading file/nMessage: " + ex.getMessage());
				return getHttpResponse(STATUSCODE_500, CONTENTTYPE_PLAIN, "Internal Server Error.");
			}
		}
		else return getHttpResponse(STATUSCODE_404, CONTENTTYPE_PLAIN, "Page not found!");
	}
	
	public static String getHttpResponse(String status, String contentType, String content) {
		return "HTTP/1.1 " + status + "\r\n" +
		        "Content-Type: " + contentType + "\r\n" +
		        "Content-Length: " + content.length() + "\r\n" +
		        "Connection: close\r\n" +
		        "\r\n" +
		        content;
	}
	
	private static String getContentType(String filename) {
		if(filename.endsWith(".html")) return CONTENTTYPE_HTML;
		if(filename.endsWith(".css")) return CONTENTTYPE_CSS;
		return CONTENTTYPE_DEFAULT;
	}
}
