package data;

import domain.Node.NodeSimpleList;

public class StackHistoryTravels {

	private NodeSimpleList<String> top; // CAMBIO: antes era NodeSimpleList<SimpleListReservation>, luego Trip; ahora String
	private int size;

	public StackHistoryTravels() {
		this.top = null;
		this.size = 0;
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

	// Desapila (Cambia la cabeza)
	public String pop() {
		if (isEmpty()) {
			return null;
		}
		String data = this.top.getData();
		this.top = this.top.getNextNode();
		this.size--;
		return data;
	}

	// Muestra la cabeza sin desapilar
	public String peek() {
		if (isEmpty()) {
			return null;
		}
		return this.top.getData();
	}

	public String showStack() {
		if (isEmpty()) {
			return "No hay registros disponibles en la pila.";
		}
		String text = "";
		NodeSimpleList<String> current = this.top;
		while (current != null) {
			text += current.getData() + "\n";
			current = current.getNextNode();
		}
		return text;
	}

	public NodeSimpleList<String> getTop() {
		return top;
	}

	public int getSize() {
		return size;
	}
}