package business.Logic;

import data.DoubleCircleListFlight;
import data.SimpleListOccupancy;
import data.SimpleListReservation;
import domain.Flight;
import domain.Node.NodeDoubleList;
import domain.Node.NodeSimpleList;

public class LogicControlTower {

    private DoubleCircleListFlight flight;
    private SimpleListOccupancy listOccupancy;

    public LogicControlTower(DoubleCircleListFlight flight) {
        this.flight = flight;
        this.listOccupancy = new SimpleListOccupancy();
    }

    // Método para calcular el porcentaje de ocupación de cada vuelo
    public void calculateOccupancyRates(SimpleListReservation listReservation) {
        this.listOccupancy = new SimpleListOccupancy();

        if (this.flight == null || this.flight.isEmpty()) {
            System.out.println("No hay vuelos registrados");
            return;
        }

        NodeDoubleList<Flight> aux = this.flight.getFirtsNodeCircleDoubleList();

        do {
            Flight currentFlight = aux.getData();
            NodeSimpleList<LogicReservation> currentReservation = listReservation.getFirstReservation();
            boolean found = false;

            while (currentReservation != null) {
                LogicReservation reservation = currentReservation.getData();

                if (currentFlight.getNumberFlight() == reservation.getFlight().getNumberFlight()) {
                    int capacity = currentFlight.getMaximumCapacity();
                    int passengers = reservation.getQuantityPassengersByFlight();
                    double occupancyRate = ((double) passengers / capacity) * 100;

                    listOccupancy.addLastSimpleListOccupancy(currentFlight.getNumberFlight(), occupancyRate);
                    found = true;
                    break;
                }
                currentReservation = currentReservation.getNextNode();
            }

            if (!found) {
                listOccupancy.addLastSimpleListOccupancy(currentFlight.getNumberFlight(), 0.0);
            }

            aux = aux.getNextNode();
        } while (aux != this.flight.getFirtsNodeCircleDoubleList());
    }

    public void prioritizeFlights() {
        if (this.flight == null || this.flight.isEmpty()) {
            System.out.println("No hay vuelos registrados para priorizar.");
            return;
        }

        NodeDoubleList<Flight> head = this.flight.getFirtsNodeCircleDoubleList();
        NodeDoubleList<Flight> tail = this.flight.getLastNodeCircleDoubleList();

        tail.setNextNode(null);
        head.setPreviusNode(null);

        NodeDoubleList<Flight> newHead = orderingFlighByOcuppationQuickSort(head, listOccupancy);

        NodeDoubleList<Flight> newTail = newHead;
        while (newTail.getNextNode() != null) {
            newTail = newTail.getNextNode();
        }

        newTail.setNextNode(newHead);
        newHead.setPreviusNode(newTail);

        this.flight.setFirtsNodeCircleDoubleList(newHead);
        this.flight.setLastNodeCircleDoubleList(newTail);
        this.flight.setCurrentNode(newHead);
    }

    public NodeDoubleList<Flight> orderingFlighByOcuppationQuickSort(NodeDoubleList<Flight> listFlight,
            SimpleListOccupancy listOccupancy) {

        if (listFlight == null || listFlight.getNextNode() == null) {
            return listFlight;
        }

        NodeDoubleList<Flight> pivot = listFlight;
        NodeDoubleList<Flight> allListFlight = listFlight.getNextNode();

        pivot.setNextNode(null);
        if (allListFlight != null) {
            allListFlight.setPreviusNode(null);
        }

        NodeDoubleList<Flight> prioritizeFirst = null;
        NodeDoubleList<Flight> prioritizeLast = null;

        NodeDoubleList<Flight> remainingFirst = null;
        NodeDoubleList<Flight> remainingLast = null;

        double occupationPivote = listOccupancy.getOccupancyByNumberFlight(pivot.getData().getNumberFlight());
        NodeDoubleList<Flight> current = allListFlight;

        while (current != null) {
            NodeDoubleList<Flight> nextSaveNode = current.getNextNode();

            current.setNextNode(null);
            current.setPreviusNode(null);

            double currentOccupation = listOccupancy.getOccupancyByNumberFlight(current.getData().getNumberFlight());
            boolean before;

            if (currentOccupation != occupationPivote) {
                before = currentOccupation > occupationPivote;
            } else {
                before = current.getData().getNumberFlight() < pivot.getData().getNumberFlight();
            }

            if (before) {
                if (prioritizeFirst == null) {
                    prioritizeFirst = current;
                    prioritizeLast = current;
                } else {
                    prioritizeLast.setNextNode(current);
                    current.setPreviusNode(prioritizeLast);
                    prioritizeLast = current;
                }
            } else {
                if (remainingFirst == null) {
                    remainingFirst = current;
                    remainingLast = current;
                } else {
                    remainingLast.setNextNode(current);
                    current.setPreviusNode(remainingLast);
                    remainingLast = current;
                }
            }

            current = nextSaveNode;
        }

        NodeDoubleList<Flight> prioritizeOrder = orderingFlighByOcuppationQuickSort(prioritizeFirst, listOccupancy);
        NodeDoubleList<Flight> remainingOrder = orderingFlighByOcuppationQuickSort(remainingFirst, listOccupancy);

        pivot.setNextNode(remainingOrder);
        if (remainingOrder != null) {
            remainingOrder.setPreviusNode(pivot);
        }

        if (prioritizeOrder == null) {
            pivot.setPreviusNode(null);
            return pivot;
        } else {
            NodeDoubleList<Flight> tailPrioritize = prioritizeOrder;
            while (tailPrioritize.getNextNode() != null) {
                tailPrioritize = tailPrioritize.getNextNode();
            }
            tailPrioritize.setNextNode(pivot);
            pivot.setPreviusNode(tailPrioritize);
            return prioritizeOrder;
        }
    }

 // Devuelve la matriz con los datos de vuelos ordenados por prioridad para mostrar en el JTable
    public Object[][] getPrioritizedTableData(SimpleListReservation listReservation) {
        if (flight == null || flight.isEmpty()) {
            return new Object[0][7];
        }

        int count = 0;
        NodeDoubleList<Flight> curr = flight.getFirtsNodeCircleDoubleList();
        do {
            count++;
            curr = curr.getNextNode();
        } while (curr != flight.getFirtsNodeCircleDoubleList());

        Object[][] data = new Object[count][7];
        curr = flight.getFirtsNodeCircleDoubleList();
        int index = 0;

        do {
            Flight f = curr.getData();
            int reservations = 0;

            NodeSimpleList<LogicReservation> resNode = listReservation.getFirstReservation();
            while (resNode != null) {
                if (resNode.getData().getFlight().getNumberFlight() == f.getNumberFlight()) {
                    reservations = resNode.getData().getQuantityPassengersByFlight();
                    break;
                }
                resNode = resNode.getNextNode();
            }

            double occupancy = listOccupancy.getOccupancyByNumberFlight(f.getNumberFlight());

            data[index][0] = index + 1;
            data[index][1] = "Vuelo " + f.getNumberFlight();
            data[index][2] = f.getRoute();
            data[index][3] = f.getAircraftType();
            data[index][4] = f.getMaximumCapacity();
            data[index][5] = reservations;
            data[index][6] = String.format("%.2f%%", occupancy);

            index++;
            curr = curr.getNextNode();
        } while (curr != flight.getFirtsNodeCircleDoubleList());

        return data;
    }
}