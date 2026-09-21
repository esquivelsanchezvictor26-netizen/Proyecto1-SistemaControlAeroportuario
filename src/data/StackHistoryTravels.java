package data;

import domain.Node.NodeSimpleList;

public class StackHistoryTravels {

	private NodeSimpleList<String> top; // CAMBIO: antes era NodeSimpleList<SimpleListReservation>, luego Trip; ahora String
	private int size;

	public StackHistoryTravels() {
		this.top = null;
		this.size = 0;
		// se quitó el campo "listReservation" que no se usaba en ningún lado
	}

	public boolean isEmpty() {
		return this.top == null;
	}

	// CAMBIO: apila el texto de UN viaje confirmado, no una lista completa de reservas
	public void push(String tripRecord) {

		NodeSimpleList<String> node = new NodeSimpleList<String>(tripRecord);
		node.setNextNode(this.top);
		this.top = node;
		this.size++;
	}

	// Muestra en el orden de entrada (recursividad, igual que ya lo tenías)
	public void showInOrderOfEntry() {
		showRecursively(this.top);
	}

	private String showRecursively(NodeSimpleList<String> node) {
	    if (node == null) {
	        return "";
	    }
	    return showRecursively(node.getNextNode()) + node.getData() + "\n";
	}

	public NodeSimpleList<String> getTop() {
		return top;
	}

	public int getSize() {
		return size;
	}
}