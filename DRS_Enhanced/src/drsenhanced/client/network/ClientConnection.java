package drsenhanced.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientConnection handles communication
 * between the JavaFX client and DRS server.
 *
 * @author Krishna Kakani | 12279867
 */
public class ClientConnection {

    /*
     * Server configuration
     */
    private static final String SERVER_HOST = "localhost";

    private static final int SERVER_PORT = 5000;

    /*
     * Socket communication objects
     */
    private Socket socket;

    private PrintWriter output;

    private BufferedReader input;

    /**
     * Connects client to server.
     */
    public void connect() throws IOException {

        socket = new Socket(SERVER_HOST, SERVER_PORT);

        output = new PrintWriter(
                socket.getOutputStream(),
                true
        );

        input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                )
        );

        System.out.println("Connected to DRS Server");
    }

    /**
     * Sends request to server.
     */
    public String sendRequest(String request)
            throws IOException {

        output.println(request);

        return input.readLine();
    }

    /**
     * Closes socket connection.
     */
    public void disconnect() throws IOException {

        if (socket != null) {

            socket.close();
        }
    }
}