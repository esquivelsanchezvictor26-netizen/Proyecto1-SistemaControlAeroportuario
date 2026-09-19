package data;

import domain.NodeSimpleList;
import domain.Passenger;

public class BoardingQueue {

    private NodeSimpleList<Passenger> first;
    private NodeSimpleList<Passenger> last;
    private int size;

    public BoardingQueue() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int getSize() {
        return this.size;
    }

    // Comprueba si el pasajero ya está en la cola para no insertarlo dos veces
    public boolean contains(String passengerId) {
        NodeSimpleList<Passenger> current = this.first;
        while (current != null) {
            if (current.getData().getId().equalsIgnoreCase(passengerId)) {
                return true;
            }
            current = current.getNextNode();
        }
        return false;
    }

    // Agregar a la cola de abordaje
    public boolean addInQueue(Passenger passenger) {
        if (passenger == null || contains(passenger.getId())) {
            return false; // Evita ingresar pasajeros nulos o duplicados
        }

        NodeSimpleList<Passenger> node = new NodeSimpleList<Passenger>(passenger, null);

        if (isEmpty()) {
            this.first = node;
        } else {
            this.last.setNextNode(node);
        }
        this.last = node;
        this.size++;
        return true;
    }

    // Abordar siguiente pasagero (lo saca de la cola)
    public Passenger board() {
        if (isEmpty()) {
            return null;
        }

        Passenger passenger = this.first.getData();
        this.first = this.first.getNextNode();
        this.size--;

        if (this.first == null) {
            this.last = null;
        }

        return passenger;
    }

    public NodeSimpleList<Passenger> getFirst() {
        return first;
    }
}
