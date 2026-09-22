package data;

import domain.Node.NodeOccupancyFlight;

public class SimpleListOccupancy {

    private NodeOccupancyFlight firstNodeOccupancyFlight;
    private NodeOccupancyFlight lastNodeOccupancyFlight;
    private int quantityNodeOccupancyFlight;

    public SimpleListOccupancy() {
        this.firstNodeOccupancyFlight = null;
        this.lastNodeOccupancyFlight = null;
        this.quantityNodeOccupancyFlight = 0;
    }

    // Método que verifica si la lista está vacía
    public boolean isEmpty() {
        return this.firstNodeOccupancyFlight == null && this.lastNodeOccupancyFlight == null;
    }

    // Método que añade al último de la lista
    public void addLastSimpleListOccupancy(int numberFlight, double occupancyRate) {
        NodeOccupancyFlight newNode = new NodeOccupancyFlight(numberFlight, occupancyRate, null);

        if (isEmpty()) {
            this.firstNodeOccupancyFlight = newNode;
            this.lastNodeOccupancyFlight = newNode;
        } else {
            this.lastNodeOccupancyFlight.setNextNodeOccupancyFlight(newNode);
            this.lastNodeOccupancyFlight = newNode;
        }

        quantityNodeOccupancyFlight++;
    }

    // Método que muestra la lista
    public String getAllOccupancy() {
        String exit = "";
        NodeOccupancyFlight aux = this.firstNodeOccupancyFlight;

        while (aux != null) {
            exit += aux.getOccupancyRate() + " ";
            aux = aux.getNextNodeOccupancyFlight();
        }

        return exit;
    }

    // Método corregido: busca la ocupación según el número de vuelo recorriendo NodeOccupancyFlight
    public double getOccupancyByNumberFlight(int numberFlight) {
        if (isEmpty()) {
            return 0.0;
        }

        NodeOccupancyFlight current = this.firstNodeOccupancyFlight;
        while (current != null) {
            if (current.getNumberFlight() == numberFlight) {
                return current.getOccupancyRate();
            }
            current = current.getNextNodeOccupancyFlight();
        }

        return 0.0;
    }

    public NodeOccupancyFlight getFirstNodeOccupancyFlight() {
        return firstNodeOccupancyFlight;
    }

    public int getQuantityNodeOccupancyFlight() {
        return quantityNodeOccupancyFlight;
    }
}