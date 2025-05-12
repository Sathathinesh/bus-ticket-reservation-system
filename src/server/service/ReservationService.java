package server.service;

import server.model.RequestData;
import server.model.ResponseData;
import server.model.TicketResponse;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationService {
    private static final Map<String, Integer> priceMap = new HashMap<>();
    private static final Set<String> allSeats = new LinkedHashSet<>();
    private static final Set<String> bookedSeats = new HashSet<>();
    private static final AtomicInteger ticketCounter = new AtomicInteger(1000);

    static {
        priceMap.put("A-B", 50);
        priceMap.put("A-C", 100);
        priceMap.put("A-D", 150);
        priceMap.put("B-C", 50);
        priceMap.put("B-D", 100);
        priceMap.put("C-D", 50);

        for (int i = 1; i <= 10; i++) {
            allSeats.add(i + "A");
            allSeats.add(i + "B");
            allSeats.add(i + "C");
            allSeats.add(i + "D");
        }
    }

    public static synchronized ResponseData checkAvailability(RequestData request) {
        String routeKey = request.getOrigin() + "-" + request.getDestination();
        int price = priceMap.getOrDefault(routeKey, 0);

        List<String> available = new ArrayList<>();
        for (String seat : allSeats) {
            if (!bookedSeats.contains(seat)) {
                available.add(seat);
                if (available.size() == request.getPassengerCount()) break;
            }
        }

        ResponseData response = new ResponseData();
        response.setAvailable(available.size() == request.getPassengerCount());
        response.setTotalPrice(price * request.getPassengerCount());
        response.setAvailableSeats(available);

        return response;
    }

    public static synchronized TicketResponse reserveTicket(RequestData request) {
        ResponseData availability = checkAvailability(request);
        TicketResponse ticket = new TicketResponse();

        if (!availability.isAvailable() || request.getPaymentAmount() < availability.getTotalPrice()) {
            ticket.setTicketNumber("FAILED");
            ticket.setBookedSeats(Collections.emptyList());
            return ticket;
        }

        bookedSeats.addAll(availability.getAvailableSeats());

        ticket.setTicketNumber("TKT" + ticketCounter.getAndIncrement());
        ticket.setBookedSeats(availability.getAvailableSeats());
        ticket.setOrigin(request.getOrigin());
        ticket.setDestination(request.getDestination());
        ticket.setTotalPrice(availability.getTotalPrice());
        ticket.setDepartureTime("09:00 AM");
        ticket.setArrivalTime("12:00 PM");

        return ticket;
    }
}
