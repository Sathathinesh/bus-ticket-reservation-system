package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class BusHttpServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

            server.createContext("/availability", new AvailabilityHandler());
            server.createContext("/reserve", new ReservationHandler());

            // Using a fixed thread pool executor (optional improvement)
            server.setExecutor(Executors.newFixedThreadPool(10));

            System.out.println("Server started at http://localhost:" + PORT);
            server.start();

        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
