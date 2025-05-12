# 🚌 Bus Ticket Reservation System – Design Document

## 📖 Overview  
This system simulates a simplified **Bus Ticket Reservation System** via REST APIs using Java.  

The application allows clients to:
- ✅ Check seat availability and pricing between two locations.
- ✅ Book tickets for a specified number of passengers if enough seats are available.
- ✅ Simulate multiple users booking simultaneously using a multithreaded client.

**Tech Used:**  
- `com.sun.net.httpserver.HttpServer` (no third-party web frameworks)
- Google Gson for JSON processing

---

## 📌 Assumptions & Constraints
- The bus operates between **A to D**, with intermediate stops at **B** and **C**.
- One round-trip per day: **A → D and D → A**.
- Ticket prices are fixed and predefined.
- The bus contains **40 seats** named from **1A to 10D** (4 seats per row, 10 rows).
- Passengers can board from any station (**A, B, C**) to any later stop and vice versa.

---

## 📦 Package Structure  

### `server.model`
- **RequestData** – Represents incoming requests.
- **ResponseData** – Output of availability check.
- **TicketResponse** – Final reservation response with ticket details.

### `server.service`
- **ReservationService** – Core logic for pricing, availability, and ticket generation.

### `server`
- **AvailabilityHandler** – HTTP handler for availability check.
- **ReservationHandler** – HTTP handler for ticket reservation.
- **BusHttpServer** – Launches the HTTP server and configures endpoints.

### `client`
- **BookingClient** – Makes sequential API calls.
- **MultiUserSimulation** – Simulates concurrent users via threads.

## 🛠️ How to Run This Project in IntelliJ IDEA

### 📋 Project Requirements:
- **Java**
- **Maven** (if configured for dependencies like Gson and JUnit 5)

---
### ▶️ Running the Server:
1. Open the project in **IntelliJ IDEA**.
2. Navigate to `BusHttpServer.java`.
3. Run `BusHttpServer.java`.
4. The server will start at: [http://localhost:8080/](http://localhost:8080/)

---

### ▶️ Running the Client:
1. Open the project in **IntelliJ IDEA**.
2. Run either of:
   - `BookingClient.java` (for sequential booking)
   - `MultiUserSimulation.java` (for simulating concurrent users)
