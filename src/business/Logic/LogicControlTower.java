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
        // Reiniciar la lista de ocupación antes de calcular
        this.listOccupancy = new SimpleListOccupancy();

        if (this.flight == null || this.flight.isEmpty()) {
            return;
        }

        NodeDoubleList<Flight> aux = this.flight.getFirtsNodeCircleDoubleList();

        do {
            Flight currentFlight = aux.getData();
            int passengers = getPassengerCountForFlight(currentFlight.getNumberFlight(), listReservation);
            int capacity = currentFlight.getMaximumCapacity();

            double occupancyRate = 0.0;
            if (capacity > 0) {
                // Casteo a double para evitar que la división de enteros resulte en 0
                occupancyRate = ((double) passengers / (double) capacity) * 100.0;
            }

            listOccupancy.addLastSimpleListOccupancy(currentFlight.getNumberFlight(), occupancyRate);

            aux = aux.getNextNode();
        } while (aux != this.flight.getFirtsNodeCircleDoubleList());
    }

    public void prioritizeFlights() {
        if (this.flight == null || this.flight.isEmpty()) {
            return;
        }

        NodeDoubleList<Flight> head = this.flight.getFirtsNodeCircleDoubleList();
        NodeDoubleList<Flight> tail = this.flight.getLastNodeCircleDoubleList();

        // 1. Convertir la lista circular a lista doble lineal temporalmente
        tail.setNextNode(null);
        head.setPreviusNode(null);

        // 2. Aplicar QuickSort
        NodeDoubleList<Flight> newHead = orderingFlighByOcuppationQuickSort(head, listOccupancy);

        // 3. Buscar el último nodo tras el ordenamiento
        NodeDoubleList<Flight> newTail = newHead;
        while (newTail.getNextNode() != null) {
            newTail = newTail.getNextNode();
        }

        // 4. Volver a enlazar circularmente
        newTail.setNextNode(newHead);
        newHead.setPreviusNode(newTail);

        // 5. Actualizar los nodos de la lista original
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

    public Object[][] getPrioritizedTableData(SimpleListReservation listReservation) {
        if (flight == null || flight.isEmpty()) {
            return new Object[0][7];
        }

        // 1. Recalcular ocupación antes de popular la tabla
        calculateOccupancyRates(listReservation);

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

            // 2. Obtener reservas
            int reservations = getPassengerCountForFlight(f.getNumberFlight(), listReservation);

            // 3. Obtener el porcentaje directamente desde listOccupancy
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

    // Método de búsqueda auxiliar de pasajeros por vuelo
    private int getPassengerCountForFlight(int flightNumber, SimpleListReservation listReservation) {
        if (listReservation == null) return 0;

        NodeSimpleList<LogicReservation> currentRes = listReservation.getFirstReservation();
        while (currentRes != null) {
            LogicReservation reservation = currentRes.getData();
            if (reservation != null && reservation.getFlight() != null) {
                if (reservation.getFlight().getNumberFlight() == flightNumber) {
                    return reservation.getQuantityPassengersByFlight();
                }
            }
            currentRes = currentRes.getNextNode();
        }
        return 0;
    }

    public SimpleListOccupancy getListOccupancy() {
        return listOccupancy;
    }
}