package test.java.service;

import server.model.RequestData;
import server.model.ResponseData;
import server.model.TicketResponse;
import server.service.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    private RequestData request;

    @BeforeEach
    public void setUp() {
        request = new RequestData();
        request.setOrigin("A");
        request.setDestination("B");
        request.setPassengerCount(2);
    }

    @Test
    public void testAvailabilitySuccess() {
        ResponseData response = ReservationService.checkAvailability(request);

        assertTrue(response.isAvailable());
        assertEquals(100, response.getTotalPrice());
        assertEquals(2, response.getAvailableSeats().size());
    }

    @Test
    public void testReserveTicketSuccess() {
        ResponseData availability = ReservationService.checkAvailability(request);
        request.setPaymentAmount(availability.getTotalPrice());

        TicketResponse ticket = ReservationService.reserveTicket(request);

        assertNotEquals("FAILED", ticket.getTicketNumber());
        assertEquals(2, ticket.getBookedSeats().size());
        assertEquals("A", ticket.getOrigin());
        assertEquals("B", ticket.getDestination());
    }

    @Test
    public void testReserveTicketFailInsufficientPayment() {
        request.setPaymentAmount(10); // too low

        TicketResponse ticket = ReservationService.reserveTicket(request);

        assertEquals("FAILED", ticket.getTicketNumber());
        assertTrue(ticket.getBookedSeats().isEmpty());
    }

    @Test
    public void testAvailabilityFailNoSeats() {
        // Simulate all seats are taken
        ReservationService.checkAvailability(request);
        ReservationService.checkAvailability(request); // First round takes some seats

        request.setPassengerCount(50); // Request more seats than available

        ResponseData response = ReservationService.checkAvailability(request);

        assertFalse(response.isAvailable()); // Should return false since not enough seats
    }

    @Test
    public void testReserveTicketFailUnavailableSeats() {
        // Simulate all seats are booked already
        ReservationService.checkAvailability(request);
        ReservationService.checkAvailability(request); // First round takes some seats

        request.setPassengerCount(50); // Request more seats than available

        TicketResponse ticket = ReservationService.reserveTicket(request);

        assertEquals("FAILED", ticket.getTicketNumber());
        assertTrue(ticket.getBookedSeats().isEmpty()); // No seats should be booked
    }

//    @Test
//    public void testReserveTicketInvalidOriginDestination() {
//        request.setOrigin("X"); // Invalid origin
//        request.setDestination("Y"); // Invalid destination
//
//        TicketResponse ticket = ReservationService.reserveTicket(request);
//
//        assertEquals("FAILED", ticket.getTicketNumber()); // Should fail because of invalid origin/destination
//        assertTrue(ticket.getBookedSeats().isEmpty());
//    }

    @Test
    public void testReserveTicketSuccessFullJourney() {
        request.setOrigin("B");
        request.setDestination("D");
        request.setPassengerCount(2);

        ResponseData availability = ReservationService.checkAvailability(request);
        request.setPaymentAmount(availability.getTotalPrice());

        TicketResponse ticket = ReservationService.reserveTicket(request);

        assertNotEquals("FAILED", ticket.getTicketNumber());
        assertEquals(2, ticket.getBookedSeats().size());
        assertEquals("B", ticket.getOrigin());
        assertEquals("D", ticket.getDestination());
    }

    @Test
    public void testReserveTicketInvalidPaymentAmount() {
        request.setPaymentAmount(200); // Higher payment amount

        TicketResponse ticket = ReservationService.reserveTicket(request);

        assertNotEquals("FAILED", ticket.getTicketNumber());
        assertFalse(ticket.getBookedSeats().isEmpty()); // It should succeed despite overpayment
    }
}
