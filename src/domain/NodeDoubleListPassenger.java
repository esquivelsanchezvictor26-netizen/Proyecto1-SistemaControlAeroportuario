package domain;

public class NodeDoubleListPassenger {

    private Passenger passenger;
    private NodeDoubleListPassenger previousNode;
    private NodeDoubleListPassenger nextNode;

    public NodeDoubleListPassenger(Passenger passenger, NodeDoubleListPassenger previousNode, NodeDoubleListPassenger nextNode) {
        this.passenger = passenger;
        this.previousNode = previousNode;
        this.nextNode = nextNode;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public NodeDoubleListPassenger getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(NodeDoubleListPassenger previousNode) {
        this.previousNode = previousNode;
    }

    public NodeDoubleListPassenger getNextNode() {
        return nextNode;
    }

    public void setNextNode(NodeDoubleListPassenger nextNode) {
        this.nextNode = nextNode;
    }
}