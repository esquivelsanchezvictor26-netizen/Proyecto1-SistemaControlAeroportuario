package domain;

public class Flight {

    private static int nextNumber = 100;

    private int numberFlight; // Número de vuelo
    private String route; // Ruta
    private String airplaneType; // Tipo de avión
    private int maximumCapacity; // Capacidad máxima
    private boolean statusAirplane; // Estatus del avión

    // Constructor automático
    public Flight(String route, String airplaneType, int maximumCapacity, boolean statusAirplane) {
        this.numberFlight = nextNumber++;
        this.route = route;
        this.airplaneType = airplaneType;
        this.maximumCapacity = maximumCapacity;
        this.statusAirplane = statusAirplane;
    }

    // Constructor explícito (por si se leen vuelos existentes desde JSON)
    public Flight(int numberFlight, String route, String airplaneType, int maximumCapacity, boolean statusAirplane) {
        this.numberFlight = numberFlight;
        this.route = route;
        this.airplaneType = airplaneType;
        this.maximumCapacity = maximumCapacity;
        this.statusAirplane = statusAirplane;
        if (numberFlight >= nextNumber) {
            nextNumber = numberFlight + 1;
        }
    }

    public static void updateNextNumber(int currentNumber) {
        if (currentNumber >= nextNumber) {
            nextNumber = currentNumber + 1;
        }
    }

    public int getNumberFlight() {
        return numberFlight;
    }

    public void setNumberFlight(int numberFlight) {
        this.numberFlight = numberFlight;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getAircraftType() {
        return airplaneType;
    }

    public void setAircraftType(String airplaneType) {
        this.airplaneType = airplaneType;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public boolean isStatusAircraft() {
        return statusAirplane;
    }

    public void setStatusAircraft(boolean statusAirplane) {
        this.statusAirplane = statusAirplane;
    }

    @Override
    public String toString() {
        return "Flight [numberFlight=" + numberFlight + ", route=" + route + ", aircraftType=" + airplaneType
                + ", maximumCapacity=" + maximumCapacity + ", statusAircraft=" + statusAirplane + "]";
    }
}
