package domain;

public class NodeCircleDoubleList<T> {


	T data;
	NodeCircleDoubleList<T> previusNode;
	NodeCircleDoubleList<T> nextNode;
	
	public NodeCircleDoubleList(T data, NodeCircleDoubleList<T> previusNode, NodeCircleDoubleList<T> nextNode) {
		this.data = data;
		this.previusNode = previusNode;
		this.nextNode = nextNode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public NodeCircleDoubleList<T> getPreviusNode() {
		return previusNode;
	}

	public void setPreviusNode(NodeCircleDoubleList<T> previusNode) {
		this.previusNode = previusNode;
	}

	public NodeCircleDoubleList<T> getNextNode() {
		return nextNode;
	}

	public void setNextNode(NodeCircleDoubleList<T> nextNode) {
		this.nextNode = nextNode;
	}
	
	
}
