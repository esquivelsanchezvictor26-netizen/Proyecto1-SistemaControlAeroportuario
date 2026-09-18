package business;

import data.DoubleListPassenger;
import domain.Flight;
import domain.Passenger;

public class LogicReservation {

	private DoubleListPassenger passengerList; // no generico
	private Flight flight;

	public LogicReservation() {}
	
	public LogicReservation(Flight flight) {
		this.flight = flight;
		// inicializar utilizando la capacidad maxima
		this.passengerList = new DoubleListPassenger(flight.getMaximumCapacity());

	}

	public String reserveSeat(String id, String fullName, int age) {
		if (passengerList.isFull()) {
			return "ERROR!!!! Capacidad m�xima del avi�n alcanzada. No se pueden registrar m�s de "
					+ passengerList.getQuantityNode() + " pasajeros.";
		}

		Passenger passenger = new Passenger(id, fullName, age);
		boolean success = passengerList.addOrderedByAge(passenger);

		if (success) {
			return "Reserva realizada con exito: " + fullName + "en el vuelo" + this.flight.getNumberFlight();

		} else {
			return "Error al realizar la reserva.";
		}
	}
	
	
	//Metodo que obtiene la reservacion de cada vuelo
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
}