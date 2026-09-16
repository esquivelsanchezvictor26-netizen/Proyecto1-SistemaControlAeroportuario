package domain;

public class NodeDoubleListPassenger {

    private Passenger passenger;
    private NodeDoubleListPassenger previusNode;
    private NodeDoubleListPassenger nextNode;

    public NodeDoubleListPassenger(Passenger passenger, NodeDoubleListPassenger previusNode, NodeDoubleListPassenger nextNode) {
        this.passenger = passenger;
        this.previusNode = previusNode;
        this.nextNode = nextNode;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public NodeDoubleListPassenger getPreviusNode() {
        return previusNode;
    }

    public void setPreviusNode(NodeDoubleListPassenger previusNode) {
        this.previusNode = previusNode;
    }

    public NodeDoubleListPassenger getNextNode() {
        return nextNode;
    }

    public void setNextNode(NodeDoubleListPassenger nextNode) {
        this.nextNode = nextNode;
    }
}