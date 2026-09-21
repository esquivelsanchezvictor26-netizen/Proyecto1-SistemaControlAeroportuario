package domain.Node;

public class NodeOccupancyFlight {

	NodeOccupancyFlight nextNodeOccupancyFlight;
	int numberFlight;
	double occupancyRate;
	
	
	public NodeOccupancyFlight(int numberFlight, double occupancyRate, NodeOccupancyFlight nectNodeOccupancyFlight){
		
		this.numberFlight = numberFlight;
		this.occupancyRate = occupancyRate;
		this.nextNodeOccupancyFlight = nextNodeOccupancyFlight;
	}


	public NodeOccupancyFlight getNextNodeOccupancyFlight() {
		return nextNodeOccupancyFlight;
	}


	public void setNextNodeOccupancyFlight(NodeOccupancyFlight nextNodeOccupancyFlight) {
		this.nextNodeOccupancyFlight = nextNodeOccupancyFlight;
	}


	public int getNumberFlight() {
		return numberFlight;
	}


	public void setNumberFlight(int numberFlight) {
		this.numberFlight = numberFlight;
	}


	public double getOccupancyRate() {
		return occupancyRate;
	}


	public void setOccupancyRate(double occupancyRate) {
		this.occupancyRate = occupancyRate;
	}
	
	
	
}
