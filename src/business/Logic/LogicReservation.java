package business.Logic;

import data.BoardingQueue;
import data.DoubleListPassenger;
import domain.Flight;
import domain.Node.NodeDoubleList;
import domain.Passenger;
import data.StackHistoryTravels;

public class LogicReservation {

	private DoubleListPassenger passengerList; // no generico
	private Flight flight;
	private BoardingQueue boardingQueue;
	private StackHistoryTravels travelHistory;

	public LogicReservation() {
	}

	public LogicReservation(Flight flight) {
		this.flight = flight;
		// inicializar utilizando la capacidad maxima
		this.passengerList = new DoubleListPassenger(flight.getMaximumCapacity());
		this.boardingQueue = new BoardingQueue();
		this.travelHistory = new StackHistoryTravels();
	}

	// Metodo que reserva
	public String reserveSeat(String id, String fullName, int age) {
		if (passengerList.isFull()) {
			return "ERROR!!!! Capacidad m�xima del avi�n alcanzada. No se pueden registrar m�s de "
					+ passengerList.getQuantityNode() + " pasajeros.";
		}

		Passenger passenger = new Passenger(id, fullName, age);
		passengerList.addLast(passenger);
		passengerList.orderByNameAndAgeBubble();

		travelHistory.push("Vuelo " + flight.getNumberFlight() + " (" + flight.getRoute() + ") - Pasajero: " + fullName
				+ " (ID " + id + ")");

		return "Reserva realizada con exito: " + fullName + "en el vuelo" + this.flight.getNumberFlight();
	}

	// M�todo para agregar al pasajero a la cola de abordaje asegurando que tenga
	// reserva en este vuelo
	public String addPassengerToBoardingQueue(String passengerId) {
		// Buscar al pasajero en la lista doble de reservas del vuelo
		NodeDoubleList<Passenger> current = passengerList.getHead();
		Passenger foundPassenger = null;

		while (current != null) {
			if (current.getData().getId().equalsIgnoreCase(passengerId)) {
				foundPassenger = current.getData();
				break;
			}
			current = current.getNextNode();
		}

		// Si no tiene reserva, no puede ingresar a la cola
		if (foundPassenger == null) {
			return "ERROR: El pasajero ID " + passengerId + " no tiene reserva en este vuelo.";
		}

		// Intentar agregar a la cola (valida que no aborde o se cole dos veces)
		boolean added = boardingQueue.addInQueue(foundPassenger);
		if (added) {
			return "Pasajero " + foundPassenger.getName() + " ingresado a la cola de abordaje.";
		} else {
			return "ERROR: El pasajero ya se encuentra en la cola de abordaje.";
		}
	}

	// M�todo para abordar al siguiente en la cola (FIFO)
	public String boardNextPassenger() {
		Passenger boardedPassenger = boardingQueue.board();
		if (boardedPassenger == null) {
			return "No hay pasajeros en la cola para abordar.";
		}
		return "El pasajero " + boardedPassenger.getName() + " ha abordado con �xito el vuelo "
				+ flight.getNumberFlight();
	}

	public BoardingQueue getBoardingQueue() {
		return boardingQueue;
	}

	// Metodo que obtiene la reservacion de cada vuelo
	public Flight getFlight() {
		return flight;
	}

	// Cuantos pasajeros tiene el vuelo
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
}
