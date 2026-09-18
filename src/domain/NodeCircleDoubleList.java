package domain;

public class NodeCircleDoubleList {


	Flight flight;
	NodeCircleDoubleList previousNode;
	NodeCircleDoubleList nextNode;
	
	public NodeCircleDoubleList(Flight flight, NodeCircleDoubleList previousNode, NodeCircleDoubleList nextNode) {
		this.flight = flight;
		this.previousNode = previousNode;
		this.nextNode = nextNode;
	}

	public Flight getData() {
		return flight;
	}

	public void setData(Flight flight) {
		this.flight = flight;
	}

	public NodeCircleDoubleList getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(NodeCircleDoubleList previousNode) {
		this.previousNode = previousNode;
	}

	public NodeCircleDoubleList getNextNode() {
		return nextNode;
	}

	public void setNextNode(NodeCircleDoubleList nextNode) {
		this.nextNode = nextNode;
	}
	
	
}
