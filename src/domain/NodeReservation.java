package domain;

import business.LogicReservation;

public class NodeReservation {

	private LogicReservation reservation;
	private NodeReservation nextNode;

	public NodeReservation(LogicReservation reservation, NodeReservation nexNode) {
		this.reservation = reservation;
		this.nextNode = nexNode;
	}

	public LogicReservation getReservation() {
		return reservation;
	}

	public void setReservation(LogicReservation reservation) {
		this.reservation = reservation;
	}

	public NodeReservation getNextNode() {
		return nextNode;
	}

	public void setNextNode(NodeReservation nextNode) {
		this.nextNode = nextNode;
	}

	
	
}
