package domain;

public class Flight {

	private int numberFlight;//Numero de vuelo
	private String route;//Ruta
	private String airplaneType;//Tipo de avión
	private int maximumCapacity;//Capacidad maxima
	private boolean statusAirplane;//Status del avión
	
	
	public Flight(int numberFlight, String route, String airpalneType, int maximumCapacity, boolean statusAirplane) {
		super();
		this.numberFlight = numberFlight;
		this.route = route;
		this.airplaneType = airpalneType;
		this.maximumCapacity = maximumCapacity;
		this.statusAirplane = statusAirplane;
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
