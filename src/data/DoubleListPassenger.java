package data;

import domain.Node.NodeDoubleList;
import domain.Passenger;

public class DoubleListPassenger {

	private NodeDoubleList<Passenger> firstPassenger;
	private NodeDoubleList<Passenger> lastPassenger;
	private int quantityNode;
	private int maxCapacity;

	public DoubleListPassenger() {
	}

	public DoubleListPassenger(int maxCapacity) {
		this.firstPassenger = null;
		this.lastPassenger = null;
		this.quantityNode = 0;
		this.maxCapacity = maxCapacity;
	}

	public boolean isEmpty() {
		return firstPassenger == null;
	}

	public boolean isFull() {
		return quantityNode >= maxCapacity;
	}

	public void addLast(Passenger passenger) {

		if (isEmpty()) {
			this.firstPassenger = this.lastPassenger = new NodeDoubleList<Passenger>(passenger, null, null);
		} else {
			NodeDoubleList<Passenger> node = new NodeDoubleList<Passenger>(passenger, this.lastPassenger, null);
			this.lastPassenger.setNextNode(node);
			this.lastPassenger = node;
		}
		quantityNode++;
	}

	public void orderByNameAndAgeBubble() {

        if (isEmpty( ) || this.firstPassenger.getNextNode() == null) {
            return;
        }

        boolean isChange;

        do {
            isChange = false;
            NodeDoubleList<Passenger> current = this.firstPassenger;

            while (current != null && current.getNextNode() != null) {
                
                Passenger p1 = current.getData();
                Passenger p2 = current.getNextNode().getData();
                
                boolean mustSwap = false;

                // 1. Criterio Principal: Edad (Menor edad va primero)
                if (p1.getAge() > p2.getAge()) {
                    mustSwap = true;
                } 
                // 2. Criterio Secundario (Desempate por Nombre si tienen la misma edad)
                else if (p1.getAge() == p2.getAge()) {
                    if (p1.getName().compareToIgnoreCase(p2.getName()) > 0) {
                        mustSwap = true;
                    }
                }

                if (mustSwap) {
                    // Intercambiar datos dentro de los nodos
                    current.setData(p2);
                    current.getNextNode().setData(p1);
                    isChange = true;
                }

                current = current.getNextNode();
            }

        } while (isChange);
    }

	// Recorrido de inicio a fin
	public String showFromStartToEnd() {
		if (isEmpty())
			return "No hay pasajeros registrados";

		StringBuilder sb = new StringBuilder();
		NodeDoubleList<Passenger> current = firstPassenger;
		while (current != null) {
			sb.append(current.getData().toString()).append("\n");
			current = current.getNextNode();
		}
		return sb.toString();
	}

	// Recorrido de fin a inicio
	public String showFromEndToStart() {
		if (isEmpty())
			return "No hay pasajeros registrados";

		StringBuilder sb = new StringBuilder();
		NodeDoubleList<Passenger> current = lastPassenger;
		while (current != null) {
			sb.append(current.getData().toString()).append("\n");
			current = current.getPreviusNode();
		}
		return sb.toString();
	}

	public int getQuantityNode() {
		return quantityNode;
	}

	public NodeDoubleList<Passenger> getHead() {
		return firstPassenger;
	}
}

/**
 * 
 * //Ordena por nombre si la edad es la misma public void OrderByName(Passenger
 * passeger) {
 * 
 * }
 * 
 * 
 * // insertar manualmente por edad public boolean addOrderedByAge(Passenger
 * passenger) {
 * 
 * if (isFull()) { return false; // por si se excede su capacidad }
 * 
 * //Este siempre es null NodeDoubleList<Passenger> newNode = new
 * NodeDoubleList<Passenger>(passenger, null, null);
 * 
 * // verificar que no este vacia if (isEmpty()) {
 * 
 * firstPassenger = newNode; lastPassenger = newNode; quantityNode++; return
 * true; }
 * 
 * 
 * // insertar al inicio segun edad y primero if (passenger.getAge() <
 * firstPassenger.getData().getAge()) { newNode.setNextNode(firstPassenger);
 * firstPassenger.setPreviusNode(newNode); firstPassenger = newNode;
 * quantityNode++; return true; }
 * 
 * // evaluar si va al medio o al final NodeDoubleList<Passenger> current =
 * firstPassenger; while (current.getNextNode() != null &&
 * current.getNextNode().getData().getAge() <= passenger.getAge()) { current =
 * current.getNextNode(); }
 * 
 * newNode.setNextNode(current.getNextNode()); newNode.setPreviusNode(current);
 * 
 * if(current.getData().getAge() == current.getNextNode().getData().getAge())
 * OrderByName();
 * 
 * if (current.getNextNode() != null) {
 * current.getNextNode().setPreviusNode(newNode); } else { lastPassenger =
 * newNode; // al final }
 * 
 * current.setNextNode(newNode); quantityNode++; return true; }
 * 
 *
 * 
 * 
 */

/**
 * METODO BURBUJA
 *
 * Sirve para ordenar de menor a mayor Nota:En el examen pide ordenarlo de la
 * manera opuesta
 *
 * public static int[] burbble(int array[]) {
 * 
 * for (int i = 0; i < array.length; i++) { for (int j = i + 1; j <
 * array.length; j++) {
 * 
 * /** Aqui ordena de menor a mayor Si le cambio el signo ordena de mayor a
 * menor
 * 
 * if (array[i] > array[j]) {
 * 
 * changeValue(array, i, j); } } }
 * 
 * return array;
 * 
 * }
 * 
 * 
 * 
 * private static void changeValue(int[] array, int i, int j) { int aux =
 * array[j]; array[j] = array[i]; array[i] = aux; }
 * 
 * -----LISTAS ENLADAZADAS-----
 * 
 * public void bubbleSortPunteros() { if (head == null || head.next == null)
 * return;
 * 
 * boolean huboIntercambio;
 * 
 * do { huboIntercambio = false; Nodo actual = head;
 * 
 * while (actual.next != null) { if (actual.dato > actual.next.dato) { //
 * Guardamos referencias de los nodos involucrados Nodo a = actual; Nodo b =
 * actual.next; Nodo antesDeA = a.prev; Nodo despuesDeB = b.next;
 * 
 * // 1. Conectar el nodo anterior a 'a' con 'b' if (antesDeA != null) {
 * antesDeA.next = b; } else { head = b; // Si 'a' era la cabeza, ahora la
 * cabeza es 'b' } b.prev = antesDeA;
 * 
 * // 2. Intercambiar los enlaces mutuos entre 'b' y 'a' b.next = a; a.prev = b;
 * 
 * // 3. Conectar 'a' con el nodo que iba después de 'b' a.next = despuesDeB; if
 * (despuesDeB != null) { despuesDeB.prev = a; }
 * 
 * // Después del intercambio, 'b' quedó antes que 'a'. // Mantener 'actual'
 * apuntando a 'a' para seguir avanzando correctamente. actual = a;
 * huboIntercambio = true; } actual = actual.next; } } while (huboIntercambio);
 * }
 * 
 * 
 * 
 * 
 **/
