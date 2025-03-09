package Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    protected ServerSocket server;
    protected Socket client;
    protected BufferedReader reader;

    protected List<EventHandler> handlers = new ArrayList<EventHandler>();

    public Server(int port) throws IOException {
        System.out.println("Listening on port " + port);
        this.server = new ServerSocket(port);
        System.out.println("Waiting for client connection");
        this.client = server.accept();
        System.out.println("Client connected : " + client);
        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    public void listen() throws IOException {
        String line;
        while ((line = this.reader.readLine()) != null) {
            String cmd = this.getCommandFromLine(line);
            String[] args = this.getArgumentsFromLine(line);
            if (cmd.equals("exit"))
                break;
            this.alertHandlers(cmd, args);
        }
        close();
    }

    protected void alertHandlers(String cmd, String... args) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, args);
        }
    }

    protected String getCommandFromLine(String line) {
        return line.split(" ")[0];
    }

    protected String[] getArgumentsFromLine(String line) {
        String[] parts = line.split(" ");
        List<String> args = new ArrayList<String>();
        if (parts.length > 1) {
            for (int i = 1; i < parts.length; i++) {
                args.add(parts[i]);
            }
        }

        return args.toArray(new String[0]);
    }

    public void close() throws IOException {
        System.out.println("Finalisation du serveur.");
        this.reader.close();
        this.client.close();
        this.server.close();
    }
}

