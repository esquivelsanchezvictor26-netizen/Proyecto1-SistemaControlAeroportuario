package domain;



public class NodeSimpleList<T>{

	private T data;
	private NodeSimpleList<T> nextNode;
	
	public NodeSimpleList(T data, NodeSimpleList<T> nextNode) {
		super();
		this.data = data;
		this.nextNode = nextNode;
	}

	//Este contructor es para las pilas
	public NodeSimpleList(T data) {
		super();
		this.data = data;
		this.nextNode = null;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public NodeSimpleList<T> getNextNode() {
		return nextNode;
	}

	public void setNextNode(NodeSimpleList<T> nextNode) {
		this.nextNode = nextNode;
	}

	
	
	
	
}
