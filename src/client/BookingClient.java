package client;

import com.google.gson.Gson;
import server.model.RequestData;
import server.model.ResponseData;
import server.model.TicketResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookingClient {
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        try {
            RequestData request = new RequestData();
            request.setOrigin("A");
            request.setDestination("B");
            request.setPassengerCount(3);

            //Check availability and price
            ResponseData response = checkAvailability(request);

            if (response != null && response.isAvailable()) {
                System.out.println("\nAvailability Found:");
                System.out.println("Available Seats: " + response.getAvailableSeats());
                System.out.println("Total Price: Rs. " + response.getTotalPrice());

                // Set payment amount as quoted
                request.setPaymentAmount(response.getTotalPrice());

                //Reserve ticket
                TicketResponse ticket = reserveTicket(request);
                if (ticket != null && !"FAILED".equals(ticket.getTicketNumber())) {
                    //Output
                    System.out.println("\nReservation Successful!");
                    System.out.println("Ticket Number: " + ticket.getTicketNumber());
                    System.out.println("Booked Seats: " + ticket.getBookedSeats());
                    System.out.println("Journey: " + ticket.getOrigin() + " to " + ticket.getDestination());
                    System.out.println("Departure Time: " + ticket.getDepartureTime());
                    System.out.println("Arrival Time: " + ticket.getArrivalTime());
                    System.out.println("Total Price: Rs. " + ticket.getTotalPrice());
                } else {
                    System.out.println("\nReservation Failed.");
                }
            } else {
                System.out.println("Seats not available for this journey.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ResponseData checkAvailability(RequestData request) throws IOException {
        URL url = new URL("http://localhost:8080/availability");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = connection.getOutputStream()) {
            os.write(gson.toJson(request).getBytes());
            os.flush();
        }

        try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
            return gson.fromJson(reader, ResponseData.class);
        } finally {
            connection.disconnect();
        }
    }

    private static TicketResponse reserveTicket(RequestData request) throws IOException {
        URL url = new URL("http://localhost:8080/reserve");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = connection.getOutputStream()) {
            os.write(gson.toJson(request).getBytes());
            os.flush();
        }

        try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
            return gson.fromJson(reader, TicketResponse.class);
        } finally {
            connection.disconnect();
        }
    }
}
