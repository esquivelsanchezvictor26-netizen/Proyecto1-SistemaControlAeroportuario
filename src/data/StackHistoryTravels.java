package data;

import domain.NodeSimpleList;

public class StackHistoryTravels {

	NodeSimpleList<SimpleListReservation> top;//Esta es la cabeza 
	int size;
	SimpleListReservation listReservation;
	
	public StackHistoryTravels() {
		this.top = null;
		this.size = 0;
		listReservation = new SimpleListReservation();
	}
	
	public boolean isEmpty() {
		return this.top == null;
	}
	
	//Añade un elemento a la lista
	public void push(SimpleListReservation reservation) {
		
		NodeSimpleList<SimpleListReservation> node = new NodeSimpleList<SimpleListReservation>(reservation);
		node.setNextNode(this.top);
		this.top = node;
		this.size++;
	}
	
	//Muestra en el orden de entrada
	public void showInOrderOfEntry() {
		showRecursively(this.top);
	}

	//Para obtener el orden de entrada se utilizo recursividad
	private String showRecursively(NodeSimpleList<SimpleListReservation> node) {
	    if (node == null) {
	        return "";
	    }
	    return showRecursively(node.getNextNode()) + node.getData() + "\n";
	}
}
