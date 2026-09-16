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

		if (isEmpty()) {
			this.firstNodeOccupancyFlight = this.lastNodeOccupancyFlight = new NodeOccupancyFlight(numberFlight,
					occupancyRate, null);
		} else {

			this.lastNodeOccupancyFlight.setNextNodeOccupancyFlight(
					new NodeOccupancyFlight(numberFlight, occupancyRate, this.firstNodeOccupancyFlight));
			this.lastNodeOccupancyFlight = this.lastNodeOccupancyFlight.getNextNodeOccupancyFlight();
			quantityNodeOccupancyFlight++;
		}

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
