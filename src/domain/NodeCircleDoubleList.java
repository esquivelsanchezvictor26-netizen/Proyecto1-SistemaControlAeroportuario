package domain;

public class NodeCircleDoubleList {


	Flight flight;
	NodeCircleDoubleList previusNode;
	NodeCircleDoubleList nextNode;
	
	public NodeCircleDoubleList(Flight flight, NodeCircleDoubleList previusNode, NodeCircleDoubleList nextNode) {
		this.flight = flight;
		this.previusNode = previusNode;
		this.nextNode = nextNode;
	}

	public Flight getData() {
		return flight;
	}

	public void setData(Flight flight) {
		this.flight = flight;
	}

	public NodeCircleDoubleList getPreviusNode() {
		return previusNode;
	}

	public void setPreviusNode(NodeCircleDoubleList previusNode) {
		this.previusNode = previusNode;
	}

	public NodeCircleDoubleList getNextNode() {
		return nextNode;
	}

	public void setNextNode(NodeCircleDoubleList nextNode) {
		this.nextNode = nextNode;
	}
	
	
}
