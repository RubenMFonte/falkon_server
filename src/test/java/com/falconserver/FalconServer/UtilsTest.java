package com.falconserver.FalconServer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class UtilsTest {

    private static final String TEST_DIR = "public";
    private static final String TEST_FILE = "testfile.txt";
    private static final String TEST_CONTENT = "Hello, test!";

    @BeforeAll
    static void setup() throws IOException {
        File dir = new File(TEST_DIR);
        if (!dir.exists()) dir.mkdirs();

        File file = new File(TEST_DIR, TEST_FILE);
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(TEST_CONTENT);
        }
    }

    @AfterAll
    static void cleanup() {
        new File(TEST_DIR, TEST_FILE).delete();
        new File(TEST_DIR).delete();
    }

    @Test
    void testGetHttpResponse() {
        String response = Utils.getHttpResponse("200 OK", "text/plain", "Hello");
        assertTrue(response.contains("HTTP/1.1 200 OK"));
        assertTrue(response.contains("Content-Type: text/plain"));
        assertTrue(response.contains("Content-Length: 5"));
        assertTrue(response.endsWith("Hello"));
    }

    @Test
    void testGetHttpResponseWithSession() {
        String response = Utils.getHttpResponseWithSession("200 OK", "text/plain", "testsession", "Session Test");
        assertTrue(response.contains("Set-Cookie: testsession"));
    }

    @Test
    void testGetFileContent_validFile() {
        String path = "/" + TEST_FILE;
        String response = Utils.getFileContent(path, null);
        assertTrue(response.contains("200 OK"));
        assertTrue(response.endsWith(TEST_CONTENT));
    }

    @Test
    void testGetFileContent_fileNotFound() {
        String response = Utils.getFileContent("/nonexistent.txt", null);
        assertTrue(response.contains("404 Not Found"));
    }

    @Test
    void testGetFileContent_pathTraversal() {
        String response = Utils.getFileContent("/../secret.txt", null);
        assertTrue(response.contains("404 Not Found"));
    }

    @Test
    void testGetMinutesInMillis() {
        assertEquals(60000, Utils.getMinutesInMillis(1));
        assertEquals(1800000, Utils.getMinutesInMillis(30));
    }
}