package server.model;

import java.util.List;

public class ResponseData {
    private boolean available;
    private int totalPrice;
    private List<String> availableSeats;


    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public List<String> getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(List<String> availableSeats) { this.availableSeats = availableSeats; }
}
