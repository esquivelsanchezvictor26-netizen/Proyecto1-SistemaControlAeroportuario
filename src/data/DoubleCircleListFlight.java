package data;

import domain.Flight;
import domain.NodeCircleDoubleList;

public class DoubleCircleListFlight {

	private NodeCircleDoubleList firtsNodeCircleDoubleList;
	private NodeCircleDoubleList lastNodeCircleDoubleList;
	private NodeCircleDoubleList currentNode;
	private int quantityNode;

	public DoubleCircleListFlight() {
		this.firtsNodeCircleDoubleList = null;
		this.lastNodeCircleDoubleList = null;
		this.quantityNode = 0;
	}

	// Metodo para saber si la lista esta vacia
	public boolean isEmpty() {
		return this.firtsNodeCircleDoubleList == null && this.lastNodeCircleDoubleList == null;
	}

	// Metodo para almacenar aviones, almacena desde el final
	public void addLastAirplane(Flight flight) {

		NodeCircleDoubleList newNode = new NodeCircleDoubleList(flight, null, null);

		if (isEmpty()) {

			firtsNodeCircleDoubleList = newNode;
			lastNodeCircleDoubleList = newNode;

			newNode.setNextNode(newNode);
			newNode.setPreviusNode(newNode);
		} else {

			// [2]<-[1] -> <-[2]->[1]
			newNode.setPreviusNode(lastNodeCircleDoubleList);
			newNode.setNextNode(firtsNodeCircleDoubleList);

			lastNodeCircleDoubleList.setNextNode(newNode);
			firtsNodeCircleDoubleList.setPreviusNode(newNode);

			lastNodeCircleDoubleList = newNode;
		}

	}

	// Metodo que muestra los aviones

	public String showAirplane() {

		String exit = "";

		if (isEmpty()) {

			return exit = "No hay aviones en el sistema";

		}

		NodeCircleDoubleList aux = this.firtsNodeCircleDoubleList;

		do {

			exit += aux.getData() + " ";

			aux = aux.getNextNode();

		} while (aux != this.firtsNodeCircleDoubleList);

		return exit;
	}

	// Metodo para navegar entre la lista hacia la derecha
	public void changeAirplanetoNext() {

		if (isEmpty()) {
			System.out.println("La lista esta vacia");
			return;
		}

		if (this.currentNode == null) {
			this.currentNode = firtsNodeCircleDoubleList;
		} else {
			this.currentNode = this.currentNode.getNextNode();

		}
	}

	// Metodo para navegar entre la lista hacia la izquierda

	public void changeAirplanePreviousNode() {
		
		if (this.currentNode == null) {
			this.currentNode = firtsNodeCircleDoubleList;
		} else {
			this.currentNode = this.currentNode.getPreviusNode();

		}
	}
	
	
	
	
	
	
	
	public NodeCircleDoubleList getFirtsNodeCircleDoubleList() {
		return firtsNodeCircleDoubleList;
	}

	public void setFirtsNodeCircleDoubleList(NodeCircleDoubleList firtsNodeCircleDoubleList) {
		this.firtsNodeCircleDoubleList = firtsNodeCircleDoubleList;
	}

	public NodeCircleDoubleList getLastNodeCircleDoubleList() {
		return lastNodeCircleDoubleList;
	}

	public void setLastNodeCircleDoubleList(NodeCircleDoubleList lastNodeCircleDoubleList) {
		this.lastNodeCircleDoubleList = lastNodeCircleDoubleList;
	}

	public NodeCircleDoubleList getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(NodeCircleDoubleList currentNode) {
		this.currentNode = currentNode;
	}

	public int getQuantityNode() {
		return quantityNode;
	}

	public void setQuantityNode(int quantityNode) {
		this.quantityNode = quantityNode;
	}
	
	
	
	
	
	

}
