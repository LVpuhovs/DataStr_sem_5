package DataStr.nodes;

public class MyVerticeNode<Ttype> {
	
	private Ttype element;
	private MyEdgeNode firstEdgeNode = null;
	
	
	public Ttype getElement() {
		return element;
	}
	public void setElement(Ttype element) {
		if(element != null)
			this.element = element;
		else
			this.element = (Ttype) new Object();
	}
	public MyEdgeNode getFirstEdgeNode() {
		return firstEdgeNode;
	}
	public void setFirstEdgeNode(MyEdgeNode firstEdgeNode) {
		this.firstEdgeNode = firstEdgeNode;
	}
	
	public MyVerticeNode(Ttype element){
		setElement(element);
	}
	
	public String toString() {
		return "" + element;
	}
}
