package business;


import data.DoubleListPassenger;
import domain.Flight;
import domain.Passenger;

public class LogicReservation {

    private DoubleListPassenger passengerList; //no generico

    public LogicReservation(Flight flight) {
        // inicializar utilizando la capacidad maxima 
        this.passengerList = new DoubleListPassenger(flight.getMaximumCapacity());
    }

    public String reserveSeat(String id, String fullName, int age) {
        if (passengerList.isFull()) {
            return "ERROR!!!! Capacidad máxima del avión alcanzada. No se pueden registrar más de " 
                    + passengerList.getQuantityNode() + " pasajeros.";
        }

        Passenger passenger = new Passenger(id, fullName, age);
        boolean success = passengerList.addOrderedByAge(passenger);

        if (success) {
            return "Reserva realizada con exito: " + fullName;
        } else {
            return "Error al realizar la reserva.";
        }
    }

    public String getPassengersAscending() {
        return passengerList.showFromStartToEnd();
    }

    public String getPassengersDescending() {
        return passengerList.showFromEndToStart();
    }
}