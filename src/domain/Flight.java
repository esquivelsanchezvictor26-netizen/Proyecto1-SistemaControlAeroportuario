package domain;
import data.DoubleListPassenger;

public class Flight {

	private int numberFlight;//Numero de vuelo
	private String route;//Ruta
	private String airplaneType;//Tipo de avión
	private int maximumCapacity;//Capacidad maxima
	private boolean statusAirplane;//Status del avión
	
	// Aqui agregamos lista pasajeros para poner hacer los calculos de vuelos disponibles
	private DoubleListPassenger passengerList; 

	public Flight(int numberFlight, String route, String airplaneType, int maximumCapacity, boolean statusAirplane) {
		super();
		this.numberFlight = numberFlight;
		this.route = route;
		this.airplaneType = airplaneType;
		this.maximumCapacity = maximumCapacity;
		this.statusAirplane = statusAirplane;
		this.passengerList = new DoubleListPassenger(maximumCapacity); //Inicializamos la lista para este vuelo, la lista es del tamanio de la capacidad
	}

	public DoubleListPassenger getPassengerList() {
		return passengerList;  
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
