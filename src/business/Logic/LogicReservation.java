package business.Logic;

import data.BoardingQueue;
import data.DoubleListPassenger;
import domain.Flight;
import domain.Node.NodeDoubleList;
import domain.Passenger;
import data.StackHistoryTravels;

public class LogicReservation {

    private DoubleListPassenger passengerList;
    private Flight flight;
    private BoardingQueue boardingQueue;
    private StackHistoryTravels travelHistory;
    private DoubleListPassenger boardedPassengerList;

    public LogicReservation() {
    }

    public LogicReservation(Flight flight) {
        this.flight = flight;
        this.passengerList = new DoubleListPassenger(flight.getMaximumCapacity());
        this.boardingQueue = new BoardingQueue();
        this.travelHistory = new StackHistoryTravels();
        this.boardedPassengerList = new DoubleListPassenger(flight.getMaximumCapacity());
    }

    // Sobrecarga 1: Reservar pasando la pila global (Mis Viajes)
    public String reserveSeat(String id, String fullName, int age, StackHistoryTravels globalStack) {
        if (passengerList.isFull()) {
            return "ERROR: Capacidad máxima del avión alcanzada. No se pueden registrar más de "
                    + flight.getMaximumCapacity() + " pasajeros.";
        }

        Passenger passenger = new Passenger(id, fullName, age);
        passengerList.addLast(passenger);
        passengerList.orderByNameAndAgeBubble();

        String record = "Pasajero: " + fullName + " | Vuelo: " + flight.getNumberFlight()
                + " (" + flight.getRoute() + ") | ID: " + id + " | Edad: " + age;

        if (globalStack != null) {
            globalStack.push(record);
        }

        return "Reserva realizada con éxito: " + fullName + " en el vuelo " + this.flight.getNumberFlight();
    }

    // Sobrecarga 2: Reservar con 3 parámetros
    public String reserveSeat(String id, String fullName, int age) {
        return reserveSeat(id, fullName, age, null);
    }

    // Agregar a la cola de abordaje
    public String addPassengerToBoardingQueue(String passengerId) {
        NodeDoubleList<Passenger> current = passengerList.getHead();
        Passenger foundPassenger = null;

        while (current != null) {
            if (current.getData().getId().equalsIgnoreCase(passengerId)) {
                foundPassenger = current.getData();
                break;
            }
            current = current.getNextNode();
        }

        if (foundPassenger == null) {
            return "ERROR: El pasajero ID " + passengerId + " no tiene reserva en este vuelo.";
        }

        NodeDoubleList<Passenger> currentBoarded = boardedPassengerList.getHead();
        while (currentBoarded != null) {
            if (currentBoarded.getData().getId().equalsIgnoreCase(passengerId)) {
                return "ERROR: El pasajero " + foundPassenger.getName() + " ya abordó el avión previamente.";
            }
            currentBoarded = currentBoarded.getNextNode();
        }

        boolean added = boardingQueue.addInQueue(foundPassenger);
        if (added) {
            return "Pasajero " + foundPassenger.getName() + " ingresado a la cola de abordaje.";
        } else {
            return "ERROR: El pasajero ya se encuentra en la cola de abordaje.";
        }
    }

    // Abordar al siguiente pasajero (FIFO)
    public String boardNextPassenger() {
        Passenger boardedPassenger = boardingQueue.board();
        if (boardedPassenger == null) {
            return "No hay pasajeros en la cola para abordar.";
        }

        boardedPassengerList.addLast(boardedPassenger);
        return "El pasajero " + boardedPassenger.getName() + " ha abordado con éxito el vuelo " + flight.getNumberFlight();
    }

    public BoardingQueue getBoardingQueue() {
        return boardingQueue;
    }

    public void setBoardingQueue(BoardingQueue boardingQueue) {
        this.boardingQueue = boardingQueue;
    }

    public DoubleListPassenger getBoardedPassengerList() {
        return boardedPassengerList;
    }

    public void setBoardedPassengerList(DoubleListPassenger boardedPassengerList) {
        this.boardedPassengerList = boardedPassengerList;
    }

    public Flight getFlight() {
        return flight;
    }

    public int getQuantityPassengersByFlight() {
        return passengerList.getQuantityNode();
    }

    public String getPassengersAscending() {
        return passengerList.showFromStartToEnd();
    }

    public String getPassengersDescending() {
        return passengerList.showFromEndToStart();
    }

    public Object[][] getPassengersTable(boolean startToEnd) {
        if (passengerList == null) {
            return new Object[0][4];
        }
        return passengerList.getTableData(startToEnd);
    }

    public DoubleListPassenger getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(DoubleListPassenger passengerList) {
        this.passengerList = passengerList;
    }
}