package business.Logic;

import data.DoubleCircleListFlight;
import domain.Flight;
import domain.Node.NodeDoubleList;

public class LogicFlight {

	DoubleCircleListFlight list;
	Flight flight;

	public LogicFlight() {
		list = new DoubleCircleListFlight();
	}

	// Saber si hay vuelos duplicados
	public boolean repeatedFlights(Flight flight) {

		if (list.isEmpty() || list.getFirtsNodeCircleDoubleList() == null) {
			return false;
		}

		NodeDoubleList<Flight> aux = list.getFirtsNodeCircleDoubleList();

		do {
			if (aux.getData().getNumberFlight() == flight.getNumberFlight()) {
				return true;
			}
			aux = aux.getNextNode();
		} while (aux != list.getFirtsNodeCircleDoubleList());

		return false;
	}

	// Valida duplicado y, si no existe, agrega el vuelo a la lista circular.
	public String addFlight(Flight flight) {

		if (repeatedFlights(flight)) {
			return "ERROR: Ya existe un vuelo registrado con el número " + flight.getNumberFlight() + ".";
		}

		list.addLastAirplane(flight);
		return "Vuelo " + flight.getNumberFlight() + " registrado con éxito.";
	}

	public DoubleCircleListFlight getFlightList() {
		return list;
	}

}
