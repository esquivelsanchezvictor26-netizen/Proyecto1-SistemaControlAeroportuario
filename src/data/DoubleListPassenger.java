package data;

import domain.Passenger;
import domain.NodeDoubleListPassenger;

public class DoubleListPassenger {

    private NodeDoubleListPassenger head;
    private NodeDoubleListPassenger tail;
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

        NodeDoubleListPassenger newNode = new NodeDoubleListPassenger(passenger, null, null);

        // verificar que no este vacia
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            quantityNode++;
            return true;
        }

        // insertar al inicio segun edad y cabeza
        if (passenger.getAge() < head.getPassenger().getAge()) {
            newNode.setNextNode(head);
            head.setPreviousNode(newNode);
            head = newNode;
            quantityNode++;
            return true;
        }

        //  evaluar si va al medio o al final
        NodeDoubleListPassenger current = head;
        while (current.getNextNode() != null && current.getNextNode().getPassenger().getAge() <= passenger.getAge()) {
            current = current.getNextNode();
        }

        newNode.setNextNode(current.getNextNode());
        newNode.setPreviousNode(current);

        if (current.getNextNode() != null) {
            current.getNextNode().setPreviousNode(newNode);
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
        NodeDoubleListPassenger current = head;
        while (current != null) {
            sb.append(current.getPassenger().toString()).append("\n");
            current = current.getNextNode();
        }
        return sb.toString();
    }

    // recorrer de fin a inicio ya que es circular, se hace para los dos lados
    public String showFromEndToStart() {
        if (isEmpty()) return "No hay pasajeros registrados";

        StringBuilder sb = new StringBuilder();
        NodeDoubleListPassenger current = tail;
        while (current != null) {
            sb.append(current.getPassenger().toString()).append("\n");
            current = current.getPreviousNode();
        }
        return sb.toString();
    }

    public int getQuantityNode() {
        return quantityNode;
    }

    public NodeDoubleListPassenger getHead() {
        return head;
    }
}