package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.model.RequestData;
import server.model.TicketResponse;
import server.service.ReservationService;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ReservationHandler implements HttpHandler {
    private static final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        try (InputStreamReader reader = new InputStreamReader(exchange.getRequestBody())) {
            RequestData request = gson.fromJson(reader, RequestData.class);
            TicketResponse ticketResponse = ReservationService.reserveTicket(request);
            String jsonResponse = gson.toJson(ticketResponse);
            sendResponse(exchange, 200, jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseText) {
        try (OutputStream os = exchange.getResponseBody()) {
            byte[] responseBytes = responseText.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, responseBytes.length);
            os.write(responseBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
