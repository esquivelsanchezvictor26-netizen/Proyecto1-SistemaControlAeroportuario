package business;

import data.DoubleCircleListFlight;
import data.SimpleListOccupancy;
import data.SimpleListReservation;
import domain.Flight;
import domain.NodeDoubleList;
import domain.NodeReservation;

public class LogicControlTower {

	DoubleCircleListFlight flight;
	SimpleListReservation listRservation;
	SimpleListOccupancy listOccupancy;

	LogicControlTower(Flight flight) {
		this.flight = new DoubleCircleListFlight();
		this.listRservation = new SimpleListReservation();
		this.listOccupancy = new SimpleListOccupancy();
	}

	// Metodo para calcular el porcentaje de ocupacion de cada vuelo
	public double getOccupancyRate(SimpleListReservation listReservation) {

		double occupancyRate = 0.0;
		int passengers = 0;
		int capacity = 0;

		if (this.flight.isEmpty()) {
			System.out.println("No hay vuelos registrados");
		} else {

			NodeDoubleList<Flight> aux = this.flight.getFirtsNodeCircleDoubleList();

			do {

				Flight currentFlight = aux.getData();
				NodeReservation currentReservation = listReservation.getFirstReservation();
				boolean found = false;

				while (currentReservation != null) {

					LogicReservation reservation = currentReservation.getReservation();

					if (currentFlight.getNumberFlight() == reservation.getFlight().getNumberFlight()) {

						capacity = currentFlight.getMaximumCapacity();

						passengers = reservation.getQuantityPassengersByFlight();

						occupancyRate = ((double) passengers / capacity) * 100;

						listOccupancy.addLastSimpleListOccupancy(currentFlight.getNumberFlight(), occupancyRate);
						found = true;
						break;
					}

					currentReservation = currentReservation.getNextNode();

				}

				if (!found) {
					listOccupancy.addLastSimpleListOccupancy(currentFlight.getNumberFlight(), 0.0);
				}

				aux = aux.getNextNode();
			} while (aux != this.flight.getFirtsNodeCircleDoubleList());

		}

		return occupancyRate;
	}

	/**
	 * Metodo que prioriza los vuelos ordenando la lista doble circular con vuelos
	 * de mayor a menor ocupación(reservas/capacidad) Si tienen igual de ocupacion
	 * entonces se ordena el que tenga menor numero de vuelo
	 */

	public NodeDoubleList<Flight> orderingFlighByOcuppationQuickSort(NodeDoubleList<Flight> listFlight,SimpleListOccupancy listOccupancy) {

	    if (listFlight == null || listFlight.getNextNode() == null) {
	        return listFlight;
	    }

	    NodeDoubleList<Flight> pivot = listFlight;
	    NodeDoubleList<Flight> allListFlight = listFlight.getNextNode();

	    // Se desengancha el pivote
	    pivot.setNextNode(null);
	    if (allListFlight != null) {
	        allListFlight.setPreviusNode(null);
	    }

	    NodeDoubleList<Flight> prioritizeFirst = null;
	    NodeDoubleList<Flight> prioritizeLast = null;

	    NodeDoubleList<Flight> remainingFirst = null;
	    NodeDoubleList<Flight> remainingLast = null;

	    double occupationPivote = listOccupancy.getOccupancyByNumberFlight(pivot.getData().getNumberFlight());

	    NodeDoubleList<Flight> current = allListFlight;

	    while (current != null) {

	        NodeDoubleList<Flight> nextSaveNodeCircleDoubleList = current.getNextNode();
	        current.setNextNode(null);
	        current.setPreviusNode(null);

	        double currentOccupation = listOccupancy.getOccupancyByNumberFlight(current.getData().getNumberFlight());

	        // Si es mayor va primero, si tienen el mismo valor empatan por numero de vuelo
	        boolean before;

	        if (currentOccupation != occupationPivote) {
	            before = currentOccupation > occupationPivote;
	        } else {
	            before = current.getData().getNumberFlight() < pivot.getData().getNumberFlight();
	        }

	        if (before) {

	            if (prioritizeFirst == null) {
	                prioritizeFirst = current;
	                prioritizeLast = current;
	            } else {
	                prioritizeLast.setNextNode(current);
	                current.setPreviusNode(prioritizeLast);
	                prioritizeLast = current;
	            }

	        } else {
	            if (remainingFirst == null) {
	                remainingFirst = current;
	                remainingLast = current;
	            } else {
	                remainingLast.setNextNode(current);
	                current.setPreviusNode(remainingLast);
	                remainingLast = current;
	            }
	        }

	        current = nextSaveNodeCircleDoubleList;
	    }

	    NodeDoubleList<Flight> prioritizeOrder = orderingFlighByOcuppationQuickSort(prioritizeFirst, listOccupancy);
	    NodeDoubleList<Flight> prioritizeAll = orderingFlighByOcuppationQuickSort(remainingFirst, listOccupancy);

	    if (prioritizeOrder == null) {

	        pivot.setPreviusNode(null);
	        pivot.setNextNode(prioritizeAll);

	        if (prioritizeAll != null) {
	            prioritizeAll.setPreviusNode(pivot);
	        }

	        return pivot;
	    }

	    NodeDoubleList<Flight> listQuue = prioritizeOrder;

	    while (listQuue.getNextNode() != null) {
	        listQuue = listQuue.getNextNode();
	    }

	    listQuue.setNextNode(pivot);
	    pivot.setPreviusNode(listQuue);
	    pivot.setNextNode(prioritizeAll);

	    if (prioritizeAll != null) {
	        prioritizeAll.setPreviusNode(pivot);
	    }

	    return prioritizeOrder;
	}

}

/**
 * Este metodo solo calcula una reservacion por vuelo
 * 
 * public double getOccupancyRate(LogicReservation reservation) {
 * 
 * double ocupancyRate = 0.0;
 * 
 * if (this.flight.isEmpty()) { System.out.println("No hay vuelos registrados");
 * } else {
 * 
 * NodeCircleDoubleList aux = this.flight.getFirtsNodeCircleDoubleList();
 * 
 * do {
 * 
 * Flight currentFlight = aux.getData();
 * 
 * if (currentFlight.getNumberFlight() ==
 * reservation.getFlight().getNumberFlight()) {
 * 
 * int capacity = currentFlight.getMaximumCapacity(); int passengers =
 * reservation.getQuantityPassengersByFlight();
 * 
 * ocupancyRate = ((double) passengers / capacity) * 100; }
 * 
 * aux = aux.getNextNode();
 * 
 * } while (aux != this.flight.getFirtsNodeCircleDoubleList()); }
 * 
 * return ocupancyRate; }
 * 
 * 
 * 
 * 
 * 
 * 
 * Este codigo tiene errores
 * 	public NodeCircleDoubleList orderingFlighByOcuppationQuickSort(NodeCircleDoubleList listFlight,
			SimpleListOccupancy listOccupancy) {

		if (listFlight == null || listFlight.getNextNode() == null) {
			return listFlight;
		}

		NodeCircleDoubleList pivot = listFlight;
		NodeCircleDoubleList allListFlight = listFlight.getNextNode();

		// Se desengancha el pivote
		pivot.setNextNode(null);
		if (allListFlight != null) {
			allListFlight.setPreviusNode(null);
		}

		NodeCircleDoubleList prioritizeFirst = null;
		NodeCircleDoubleList prioritizeLast = null;

		NodeCircleDoubleList remainingFirst = null;
		NodeCircleDoubleList remainingLast = null;

		double occupationPivote = listOccupancy.getOccupancyByNumberFlight(pivot.getData().getNumberFlight());

		NodeCircleDoubleList current = allListFlight;

		while (current != null) {

			NodeCircleDoubleList nextSaveNodeCircleDoubleList = current.getNextNode();
			current.setNextNode(null);
			current.setPreviusNode(null);

			double currentOccupation = listOccupancy.getOccupancyByNumberFlight(current.getData().getNumberFlight());

			// Si es mayor va primero si tienen el mismo valor empatan
			boolean before;

			if (currentOccupation != occupationPivote) {
				before = currentOccupation > occupationPivote;
			} else {

				before = current.getData().getNumberFlight() < pivot.getData().getNumberFlight();
			}

			if (before) {

				if (prioritizeFirst == null) {
					prioritizeFirst = current;
					prioritizeLast = current;
				} else {

					prioritizeLast.setNextNode(current);
					current.setPreviusNode(prioritizeLast);
					prioritizeLast = current;
				}

			} else {
				if (remainingFirst == null) {
					remainingFirst = current;
					remainingLast = current;
				}else {
					remainingLast.setNextNode(current);
					current.setPreviusNode(remainingLast);
					remainingLast = current;
				}
			}
			
			NodeCircleDoubleList prioritizeOrder = orderingFlighByOcuppationQuickSort(prioritizeFirst, listOccupancy);
			NodeCircleDoubleList prioritizeAll = orderingFlighByOcuppationQuickSort(remainingFirst, listOccupancy);
			
			
			if(prioritizeOrder == null) {
				
				pivot.setPreviusNode(null);
				pivot.setNextNode(prioritizeAll);
				
				if(prioritizeAll != null) {
					prioritizeAll.setPreviusNode(pivot);
				}
				
				return pivot;
			}
			
			NodeCircleDoubleList listQuue = prioritizeOrder;
			
			while(listQuue.getNextNode() != null) {
				listQuue = listQuue.getNextNode();
			}
			
			listQuue.setNextNode(pivot);
			pivot.setPreviusNode(listQuue);
			pivot.setNextNode(prioritizeAll);
			
			if(prioritizeOrder != null) {
				prioritizeOrder.setPreviusNode(pivot);
			}

			
		}

		return prioritizeOrder;
	}
 */
