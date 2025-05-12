package client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiUserSimulation {
    public static void main(String[] args) {
        int userCount = 5; // Simulate 5 users booking at the same time
        ExecutorService executor = Executors.newFixedThreadPool(userCount);

        for (int i = 0; i < userCount; i++) {
            executor.submit(() -> {
                try {
                    BookingClient.main(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}
