package business;

import data.DoubleListFlight;
import domain.Flight;
import domain.NodeCircleDoubleList;

public class LogicFlight<T> {

	DoubleListFlight<Flight> list;
	Flight flight;

	public LogicFlight() {
		list = new DoubleListFlight<Flight>();
	}

	// Saber si hay vuelos duplicados
	public boolean repeatedFlights(Flight flight) {

		NodeCircleDoubleList<Flight> aux = list.getFirtsNodeCircleDoubleList();

		do {

			if (aux.getData().getNumberFlight() == flight.getNumberFlight()) {

				return true;

			} else {

				aux = aux.getNextNode();

			}

		} while (aux != list.getFirtsNodeCircleDoubleList());

		return false;
	}

}
