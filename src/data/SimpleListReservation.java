package data;

import business.LogicReservation;
import domain.NodeReservation;

public class SimpleListReservation {

	NodeReservation firstReservation;
	NodeReservation lastReservation;
	int quantityReservation;

	public SimpleListReservation() {

		this.firstReservation = null;
		this.lastReservation = null;
		this.quantityReservation = 0;
	}

	//Verificar si la lista esta vacia
	public boolean isEmpty() {
		return this.firstReservation == null && this.lastReservation == null;
	}
	
	
	//Añadir al ultimo lugar de la lista
	public void saveElementLast(LogicReservation reservation) {
		if (isEmpty()) {
			this.firstReservation = this.lastReservation = new NodeReservation(reservation, null);
		} else {		
			this.lastReservation.setNextNode(new NodeReservation(reservation, null));
			this.lastReservation = this.lastReservation.getNextNode();
		}
		quantityReservation++;
	}
	
	
	//Obtener todas las reservaciones por vuelo
	public String getAllReservation() {
		
		String exit = "";
		
		NodeReservation aux = this.firstReservation;
		
		while(aux != null) {
			
			exit += aux.getReservation() + " ";
			
			aux = aux.getNextNode();
		}
		
		return exit;
	}

	public NodeReservation getFirstReservation() {
		return firstReservation;
	}

	public void setFirstReservation(NodeReservation firstReservation) {
		this.firstReservation = firstReservation;
	}

	public NodeReservation getLastReservation() {
		return lastReservation;
	}

	public void setLastReservation(NodeReservation lastReservation) {
		this.lastReservation = lastReservation;
	}

	public int getQuantityReservation() {
		return quantityReservation;
	}

	public void setQuantityReservation(int quantityReservation) {
		this.quantityReservation = quantityReservation;
	}
	
	
	
	
}
