package business;

import data.DoubleCircleListFlight;
import domain.Flight;
import domain.NodeDoubleList;

public class LogicFlight {

	DoubleCircleListFlight list;
	Flight flight;

	public LogicFlight() {
		list = new DoubleCircleListFlight();
	}

	// Saber si hay vuelos duplicados
	public boolean repeatedFlights(Flight flight) {

		NodeDoubleList<Flight> aux = list.getFirtsNodeCircleDoubleList();

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
