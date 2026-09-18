package domain;

public class NodeDoubleList<T> {


	T data;
	NodeDoubleList<T> previusNode;
	NodeDoubleList<T> nextNode;
	
	public NodeDoubleList(T data, NodeDoubleList<T> previusNode, NodeDoubleList<T> nextNode) {
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

	public NodeDoubleList<T> getPreviusNode() {
		return previusNode;
	}

	public void setPreviusNode(NodeDoubleList<T> previusNode) {
		this.previusNode = previusNode;
	}

	public NodeDoubleList<T> getNextNode() {
		return nextNode;
	}

	public void setNextNode(NodeDoubleList<T> nextNode) {
		this.nextNode = nextNode;
	}
	
	
}
