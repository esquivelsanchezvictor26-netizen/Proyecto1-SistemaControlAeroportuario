package data;

import domain.NodeDoubleList;
import domain.Passenger;

public class DoubleListPassenger{

    private NodeDoubleList<Passenger>  head;
    private NodeDoubleList<Passenger>  tail;
    private int quantityNode;
    private int maxCapacity;

    public DoubleListPassenger() {}
    
    public DoubleListPassenger(int maxCapacity) {
        this.head = null;
        this.tail = null;
        this.quantityNode = 0;
        this.maxCapacity = maxCapacity;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public boolean isFull() {
        return quantityNode >= maxCapacity;
    }

    // insertar manualmente por edad
    public boolean addOrderedByAge(Passenger passenger) {
        if (isFull()) {
            return false; // por si se excede su capacidad
        }

        NodeDoubleList<Passenger>  newNode = new NodeDoubleList(passenger, null, null);

        // verificar que no este vacia
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            quantityNode++;
            return true;
        }

        // insertar al inicio segun edad y cabeza
        if (passenger.getAge() < head.getData().getAge()) {
            newNode.setNextNode(head);
            head.setPreviusNode(newNode);
            head = newNode;
            quantityNode++;
            return true;
        }

        //  evaluar si va al medio o al final
        NodeDoubleList<Passenger>  current = head;
        while (current.getNextNode() != null && current.getNextNode().getData().getAge() <= passenger.getAge()) {
            current = current.getNextNode();
        }

        newNode.setNextNode(current.getNextNode());
        newNode.setPreviusNode(current);

        if (current.getNextNode() != null) {
            current.getNextNode().setPreviusNode(newNode);
        } else {
            tail = newNode; // al final
        }

        current.setNextNode(newNode);
        quantityNode++;
        return true;
    }

    // recorrer de inicio a fin
    public String showFromStartToEnd() {
        if (isEmpty()) return "No hay pasajeros registrados";
        
        StringBuilder sb = new StringBuilder();
        NodeDoubleList<Passenger>  current = head;
        while (current != null) {
            sb.append(current.getData().toString()).append("\n");
            current = current.getNextNode();
        }
        return sb.toString();
    }

    // recorrer de fin a inicio ya que es circular, se hace para los dos lados
    public String showFromEndToStart() {
        if (isEmpty()) return "No hay pasajeros registrados";

        StringBuilder sb = new StringBuilder();
        NodeDoubleList<Passenger>  current = tail;
        while (current != null) {
            sb.append(current.getData().toString()).append("\n");
            current = current.getPreviusNode();
        }
        return sb.toString();
    }

    public int getQuantityNode() {
        return quantityNode;
    }

    public NodeDoubleList<Passenger>  getHead() {
        return head;
    }
}