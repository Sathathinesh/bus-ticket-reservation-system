package server.model;

import java.util.List;

public class TicketResponse {
    private String ticketNumber;
    private List<String> bookedSeats;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int totalPrice;


    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    public List<String> getBookedSeats() { return bookedSeats; }
    public void setBookedSeats(List<String> bookedSeats) { this.bookedSeats = bookedSeats; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
}
