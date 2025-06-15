<h1>FalconServer - Lightweight Java Web Server</h1>


<h3>Overview</h3>
<p>FalconServer is a lightweight, custom-built Java web server designed for learning and portfolio purposes.</p>
<p>It handles basic HTTP requests, supports multi-threaded client connections, simple routing, session management with cookies, and serves static files securely.</p>

<h3>Current Features</h3>
<ul>
        <li>Basic HTTP/1.1 server running on port 8080</li>
        <li>Multi-threaded request handling using ExecutorService with a fixed thread pool</li>
        <li>Simple routing system with dynamic route handlers</li>
        <li>Static file serving from a protected public directory (prevents directory traversal attacks)</li>
        <li>Support for HTTP methods: GET and POST</li>
        <li>Session management with unique session IDs and automatic session cleanup</li>
        <li>
        Basic routes implemented:
                <ul>
                        <li>/greet — responds with a personalized greeting using query parameters</li>
                        <li>/time — returns current server time</li>
                        <li>/echo — echoes back POST request bodies</li>
                </ul>
        </li>
        <li>Custom HTTP response builder including headers and cookies</li>
</ul>

<h3>Project Structure</h3>
<ul>
        <li>Main.java — server entry point, manages socket listening and creates the client threads</li>
        <li>ClientHandler.java — handles individual client requests</li>
        <li>Router.java — singleton managing route-to-handler mapping</li>
        <li>SessionManager.java — singleton handling user sessions and cleanup</li>
        <li>Utils.java — utility methods for file handling, HTTP responses, etc...</li>
        <li>public/ — directory containing static files (e.g., index.html, CSS files)</li>
</ul>

<h3>How to Run</h3>
<ol>
        <li>Build the project using Maven</li>
        <li>Run the Main class</li>
        <li>Access the server at http://localhost:8080</li>
</ol>

