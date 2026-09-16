package business;

import data.DoubleCircleListFlight;
import data.SimpleListReservation;
import domain.Flight;
import domain.NodeCircleDoubleList;
import domain.NodeReservation;

public class LogicControlTower {

	DoubleCircleListFlight flight;
	SimpleListReservation listRservation;

	LogicControlTower(Flight flight) {
		this.flight = new DoubleCircleListFlight();
		this.listRservation = new SimpleListReservation();
	}

	// Metodo para calcular el porcentaje de ocupacion de cada vuelo
	public double getOccupancyRate(SimpleListReservation listReservation) {

		double occupancyRate = 0.0;
		int passengers = 0;
		int capacity = 0;

		if (this.flight.isEmpty()) {
			System.out.println("No hay vuelos registrados");
		} else {

			NodeCircleDoubleList aux = this.flight.getFirtsNodeCircleDoubleList();

			do {

				Flight currentFlight = aux.getData();
				NodeReservation currentReservation = listReservation.getFirstReservation();

				while (currentReservation != null) {

					LogicReservation reservation = currentReservation.getReservation();

					if (currentFlight.getNumberFlight() == reservation.getFlight().getNumberFlight()) {

						capacity = currentFlight.getMaximumCapacity();

						passengers = reservation.getQuantityPassengersByFlight();

						occupancyRate = ((double) passengers / capacity) * 100;
						
						return occupancyRate;
					}

				}

				aux = aux.getNextNode();
			} while (aux != this.flight.getFirtsNodeCircleDoubleList());

		}

		return occupancyRate;
	}
	
	
	
	

	/**
	 * Metodo que prioriza los vuelos ordenando la lista doble circular con vuelos de mayor a menor ocupación(reservas/capacidad)
	 * Si tienen igual de ocupacion entonces se ordena el que tenga menor numero de vuelo
	 * */

	public void orderingFlighByOcuppationQuickSort(NodeCircleDoubleList listFlight) {
		
		
		if(listFlight == null || listFlight.getNextNode() == null) {
			return;
		}
		
		NodeCircleDoubleList pivot = listFlight;
		NodeCircleDoubleList allList = listFlight.getNextNode();
		
		pivot.setNextNode(null)  ;
		
		if(allList != null) {
			allList.setPreviusNode(null);
		}
		
		NodeCircleDoubleList minorFirst = null;
		NodeCircleDoubleList minorLast = null;
		
		NodeCircleDoubleList bigFirst = null;
		NodeCircleDoubleList bigLast = null;
		
		
		NodeCircleDoubleList current = allList;
		
		
		while(current != null) {}
		
	}


}



/**
 * Este metodo solo calcula una reservacion por vuelo
 * 
 * public double getOccupancyRate(LogicReservation reservation) {

    double ocupancyRate = 0.0;

    if (this.flight.isEmpty()) {
        System.out.println("No hay vuelos registrados");
    } else {

        NodeCircleDoubleList aux = this.flight.getFirtsNodeCircleDoubleList();

        do {

            Flight currentFlight = aux.getData();

            if (currentFlight.getNumberFlight() == reservation.getFlight().getNumberFlight()) {

                int capacity = currentFlight.getMaximumCapacity();
                int passengers = reservation.getQuantityPassengersByFlight();

                ocupancyRate = ((double) passengers / capacity) * 100;
            }

            aux = aux.getNextNode();

        } while (aux != this.flight.getFirtsNodeCircleDoubleList());
    }

    return ocupancyRate;
}
 * 
 * 
 * */
