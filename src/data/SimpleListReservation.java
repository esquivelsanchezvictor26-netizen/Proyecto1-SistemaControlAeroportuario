package data;

import business.LogicReservation;
import domain.NodeSimpleList;

public class SimpleListReservation {

	NodeSimpleList<LogicReservation> firstReservation;
	NodeSimpleList<LogicReservation> lastReservation;
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
			this.firstReservation = this.lastReservation = new NodeSimpleList<LogicReservation>(reservation, null);
		} else {		
			this.lastReservation.setNextNode(new NodeSimpleList<LogicReservation>(reservation, null));
			this.lastReservation = this.lastReservation.getNextNode();
		}
		quantityReservation++;
	}
	
	
	//Obtener todas las reservaciones por vuelo
	public String getAllReservation() {
		
		String exit = "";
		
		NodeSimpleList<LogicReservation> aux = this.firstReservation;
		
		while(aux != null) {
			
			exit += aux.getData() + " ";
			
			aux = aux.getNextNode();
		}
		
		return exit;
	}

	public NodeSimpleList<LogicReservation> getFirstReservation() {
		return firstReservation;
	}

	public void setFirstReservation(NodeSimpleList<LogicReservation> firstReservation) {
		this.firstReservation = firstReservation;
	}

	public NodeSimpleList<LogicReservation> getLastReservation() {
		return lastReservation;
	}

	public void setLastReservation(NodeSimpleList<LogicReservation> lastReservation) {
		this.lastReservation = lastReservation;
	}

	public int getQuantityReservation() {
		return quantityReservation;
	}

	public void setQuantityReservation(int quantityReservation) {
		this.quantityReservation = quantityReservation;
	}
	
	
	
	
}
