package data;

import domain.Flight;
import domain.Node.NodeDoubleList;

public class DoubleCircleListFlight {

	private NodeDoubleList<Flight> headFlight;
	private NodeDoubleList<Flight> tailFlight;
	private NodeDoubleList<Flight> currentNode;
	private int quantityNode;

	public DoubleCircleListFlight() {
		this.headFlight = null;
		this.tailFlight = null;
		this.quantityNode = 0;
	}

	// Metodo para saber si la lista esta vacia
	public boolean isEmpty() {
		return this.headFlight == null && this.tailFlight == null;
	}

	// Metodo para almacenar aviones, almacena desde el final
	public void addLastAirplane(Flight flight) {

		NodeDoubleList<Flight> newNode = new NodeDoubleList<Flight>(flight, null, null);

		if (isEmpty()) {

			headFlight = newNode;
			tailFlight = newNode;

			newNode.setNextNode(newNode);
			newNode.setPreviusNode(newNode);
		} else {

			// [2]<-[1] -> <-[2]->[1]
			newNode.setPreviusNode(tailFlight);
			newNode.setNextNode(headFlight);

			tailFlight.setNextNode(newNode);
			headFlight.setPreviusNode(newNode);

			tailFlight = newNode;
		}
		quantityNode++;

	}

	// Metodo que muestra los aviones

	public Flight showAirplaneById(int id) {

		if (!isEmpty()) {

			NodeDoubleList<Flight> aux = this.headFlight;

			do {

				if (aux.getData().getNumberFlight() == id) {
					return aux.getData();
				}
				aux = aux.getNextNode();

			} while (aux != this.headFlight);

		}

		return null;
	}

	// Metodo para navegar entre la lista hacia la derecha
	public void changeAirplanetoNext() {

		if (isEmpty()) {
			System.out.println("La lista esta vacia");
			return;
		}

		if (this.currentNode == null) {
			this.currentNode = headFlight;
		} else {
			this.currentNode = this.currentNode.getNextNode();

		}
	}

	// Metodo para navegar entre la lista hacia la izquierda

	public void changeAirplanePreviousNode() {

		if (this.currentNode == null) {
			this.currentNode = headFlight;
		} else {
			this.currentNode = this.currentNode.getPreviusNode();

		}
	}

	public NodeDoubleList<Flight> getFirtsNodeCircleDoubleList() {
		return headFlight;
	}

	public void setFirtsNodeCircleDoubleList(NodeDoubleList<Flight> firtsNodeCircleDoubleList) {
		this.headFlight = firtsNodeCircleDoubleList;
	}

	public NodeDoubleList<Flight> getLastNodeCircleDoubleList() {
		return tailFlight;
	}

	public void setLastNodeCircleDoubleList(NodeDoubleList<Flight> lastNodeCircleDoubleList) {
		this.tailFlight = lastNodeCircleDoubleList;
	}

	public NodeDoubleList<Flight> getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(NodeDoubleList<Flight> currentNode) {
		this.currentNode = currentNode;
	}

	public int getQuantityNode() {
		return quantityNode;
	}

	public void setQuantityNode(int quantityNode) {
		this.quantityNode = quantityNode;
	}

}
