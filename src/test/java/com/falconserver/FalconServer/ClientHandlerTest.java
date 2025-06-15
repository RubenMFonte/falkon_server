package com.falconserver.FalconServer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.Socket;

class ClientHandlerTest {

    @Test
    public void testValidGetRequestToGreet() throws Exception {

        String request = "GET /greet?name=John HTTP/1.1\r\nHost: localhost\r\n\r\n";
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Socket mockSocket = new Socket() {
            public InputStream getInputStream() {
                return inputStream;
            }

            public OutputStream getOutputStream() {
                return outputStream;
            }

            public void close() {}
        };

        ClientHandler handler = new ClientHandler(mockSocket);
        handler.run();

        String response = outputStream.toString();

        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains("Hello John"));
    }

    @Test
    public void testBadRequest() throws Exception {
        String request = "INVALID REQUEST\r\n\r\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Socket mockSocket = new Socket() {
            public InputStream getInputStream() {
                return inputStream;
            }

            public OutputStream getOutputStream() {
                return outputStream;
            }

            public void close() {}
        };

        ClientHandler handler = new ClientHandler(mockSocket);
        handler.run();

        String response = outputStream.toString();

        assertTrue(response.contains("400 Bad Request"));
    }
}
