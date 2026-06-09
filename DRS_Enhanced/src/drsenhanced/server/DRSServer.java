package drsenhanced.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DRSServer is the main server class for the DRS-Enhanced system.
 * It listens for incoming client connections on a specific port
 * and assigns each connection to a separate thread using a thread pool.
 * 
 * This allows multiple clients to connect at the same time without
 * blocking each other - this is the multi-threaded server requirement.
 * 
 * @author Angelica - P5 Server and Testing
 */
public class DRSServer {

    // Port number the server will listen on
    private static final int PORT = 5000;
    
    // Maximum number of client threads running at the same time
    private static final int MAX_THREADS = 10;
    
    // Flag to control when the server should stop
    private boolean running;
    
    /**
     * Starts the server and waits for client connections.
     * Each new client gets its own thread via the thread pool.
     */
    public void start() {
        
        // Create a thread pool with a fixed number of threads
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
        
        running = true;
        
        System.out.println("DRS-Enhanced Server started on port " + PORT);
        System.out.println("Waiting for client connections...");
        
        // Try to open the server socket on the defined port
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            
            // Keep running until the server is stopped
            while (running) {
                
                // Wait here until a client connects
                Socket clientSocket = serverSocket.accept();
                
                System.out.println("New client connected: " 
                        + clientSocket.getInetAddress().getHostAddress());
                
                // Create a new handler for this client and run it in the thread pool
                ClientHandler handler = new ClientHandler(clientSocket);
                threadPool.execute(handler);
            }
            
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } finally {
            // Shut down the thread pool cleanly when server stops
            threadPool.shutdown();
            System.out.println("Server stopped.");
        }
    }
    
    /**
     * Stops the server by setting the running flag to false.
     */
    public void stop() {
        running = false;
        System.out.println("Server is shutting down...");
    }
    
    /**
     * Main method - entry point to start the DRS server.
     */
    public static void main(String[] args) {
        DRSServer server = new DRSServer();
        server.start();
    }
}