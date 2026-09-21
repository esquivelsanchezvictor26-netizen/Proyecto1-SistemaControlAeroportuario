package data;

import domain.NodeOccupancyFlight;

public class SimpleListOccupancy {

	NodeOccupancyFlight firstNodeOccupancyFlight;
	NodeOccupancyFlight lastNodeOccupancyFlight;
	int quantityNodeOccupancyFlight;

	public SimpleListOccupancy() {

		this.firstNodeOccupancyFlight = null;
		this.lastNodeOccupancyFlight = null;
		this.quantityNodeOccupancyFlight = 0;
	}

	// Metodo que verifica si la lista esta vacia
	public boolean isEmpty() {
		return this.firstNodeOccupancyFlight == null && this.lastNodeOccupancyFlight == null;
	}

	// Metodo que añade al ultimo de la lista
	public void addLastSimpleListOccupancy(int numberFlight, double occupancyRate) {

		NodeOccupancyFlight newNode = new NodeOccupancyFlight(numberFlight, occupancyRate, null);

		if (isEmpty()) {
			this.firstNodeOccupancyFlight = newNode;
			this.lastNodeOccupancyFlight = newNode;
		} else {
			this.lastNodeOccupancyFlight.setNextNodeOccupancyFlight(newNode);
			this.lastNodeOccupancyFlight = newNode;
		}

		quantityNodeOccupancyFlight++;
	}

	// Metodo que muestra la lista
	public String getAllOccupancy() {

		String exit = "";

		NodeOccupancyFlight aux = this.firstNodeOccupancyFlight;

		while (aux != null) {

			exit += aux.getOccupancyRate() + " ";
			aux = aux.getNextNodeOccupancyFlight();
		}

		return exit;
	}

	// buscar ocupacion por numero de vuelo
	public double getOccupancyByNumberFlight(int numberFlight) {

		NodeOccupancyFlight aux = this.firstNodeOccupancyFlight;

		while (aux != null) {
			if (aux.getNumberFlight() == numberFlight) {

				return aux.getOccupancyRate();
			}

			aux = aux.getNextNodeOccupancyFlight();
		}
		return 0.0;
	}
}
