package DataStr;

import java.util.ArrayList;
import java.util.Stack;

import DataStr.nodes.MyEdgeNode;
import DataStr.nodes.MyVerticeNode;

public class MyGraph<Ttype> {
	
	private MyVerticeNode[] vertices;
	private final int GRAPH_DEFAULT_SIZE = 10;
	private int size = GRAPH_DEFAULT_SIZE;
	private int counter = 0;
	
	public MyGraph() {
		vertices = new MyVerticeNode[size];
	}
	
	public MyGraph(int inputSize) {
		if(inputSize > 0) {
			size = inputSize;
		}
		vertices = new MyVerticeNode[size];
	}
	
	public boolean isEmpty() {
		return (counter==0);
	}
	
	public boolean isFull() {
		return (counter == size);
	}
	
	public int howManyElements() {
		return counter;
	}
	
	private void resize()
	{
		int newSize = (counter <= 100)? size * 2 : (int)(size * 1.5);
		MyVerticeNode[] verticesNew = new MyVerticeNode[newSize];
		for(int i = 0; i < size; i++) {
			vertices[i] = verticesNew[i];
		}
		
		vertices = verticesNew;
		System.gc();
		size = newSize;
	}
	
	//TODO
	//pievienot jaunu virsotni grafa
	public void addVertice(Ttype element) throws Exception {
		if (element == null) throw new Exception("No elements");
		if(isFull()) resize();
		
		//parbaude ka virsotne neeksiste
		if(addVerticeHelp(element) != -1) {
			throw new Exception("This vertice is already in graph");
			
		}else {
			MyVerticeNode newNode = new MyVerticeNode<Ttype>(element);
			vertices[counter++] = newNode;
		}
	}
	
	//atrast virsotni grafa un atgriezt ta index
	private int addVerticeHelp(Ttype element) {
		
		for(int i = 0; i < counter; i++) {
			if(vertices[i].getElement().equals(element)) {
				return i;
			}
		}
		return -1;
	}
	
	//pievienot celu no konkretas virsotnes uz kadu citu virsotni
	public void addEdge(Ttype verticeFrom, Ttype verticeTo, float weight) throws Exception {
		if(verticeFrom == null || verticeTo == null || weight<=0 || weight >= 41000)
			throw new Exception("There is a problem with params");
		
		if(verticeFrom.equals(verticeTo))
			throw new Exception("It is not possible to create an edge to the same vertice");
		
		int indexFrom = addVerticeHelp(verticeFrom);
		int indexTo = addVerticeHelp(verticeTo);
		
		if(indexFrom == -1 || indexTo == -1)
			throw new Exception("One or both vertices are not in the graph");
		
		//TODO pārbaude, vai tads ceļs starp minetjaām virsotnēm jau neekesistē
		MyEdgeNode newEdgeNode = new MyEdgeNode(indexTo, weight);
		
		//sis būs pirmais ceļa bloks sai virsotnei
		if(vertices[indexFrom].getFirstEdgeNode() == null)
		{
			vertices[indexFrom].setFirstEdgeNode(newEdgeNode);
		}
		else
		{
			MyEdgeNode firstEdgeNode = vertices[indexFrom].getFirstEdgeNode();
			newEdgeNode.setNext(firstEdgeNode);
			firstEdgeNode.setPrevious(newEdgeNode);
			vertices[indexFrom].setFirstEdgeNode(newEdgeNode);
			
		}
	}
	
	//izprintet visas virsotnes ar piesaistitajiem celiem
	public void print() throws Exception {
		if(isEmpty()) throw new Exception("Graph is empty");
		
		for(int i = 0; i < counter; i++) {
			System.out.print(vertices[i].getElement() + " ---> ");
			MyEdgeNode tempEdge = vertices[i].getFirstEdgeNode();
			while(tempEdge != null) {
				System.out.print(vertices[tempEdge.getIndexOfNeighbour()].getElement() + " " + tempEdge.getWeight() + " km; ");
				tempEdge = tempEdge.getNext();
			}
			System.out.println();
		}
	}
	
	private ArrayList<MyVerticeNode> getNeighbours(Ttype element) throws Exception{
		if(element == null || vertices == null) throw new Exception("No Elements");
		int indexOfElement = addVerticeHelp(element);
		if(indexOfElement == -1) throw new Exception("No vertices");
		
		ArrayList<MyVerticeNode> result = new ArrayList<MyVerticeNode>();
		MyVerticeNode verticeNode = vertices[indexOfElement];
		
		MyEdgeNode tempE = verticeNode.getFirstEdgeNode();
		
		while(tempE != null) {
			int indexOf = tempE.getIndexOfNeighbour();
			MyVerticeNode verticeNodeOfNeighbour = vertices[indexOf];
			result.add(verticeNodeOfNeighbour);
			
			//isais variants
			//result.add(vertices[tempE.getIndexOfNeighbour()]);
			tempE = tempE.getNext();
		}
		
		return result;
	}
	
	private void clearVisited() throws Exception {
		if(isEmpty()) throw new Exception("Graph is empty");
		
		for(int i = 0; i < counter; i++) {
			vertices[i].setVisited(false);
		}
	}
	
	public ArrayList<Ttype> searchPathByDepth(Ttype elementFrom, Ttype elementTo) throws Exception {
		if(isEmpty()) throw new Exception("Graph is empty");
		int indexFrom = addVerticeHelp(elementFrom);
		int indexTo = addVerticeHelp(elementTo);
		
		if(indexFrom == -1 || indexTo == -1) {
			throw new Exception("One or both vertices are not in the graph");
		}
		
		clearVisited();
		ArrayList<Ttype> result = new ArrayList<Ttype>();
		
		boolean isFound = false;
		
		Stack<MyVerticeNode> stack = new Stack<MyVerticeNode>();
		MyVerticeNode verticeNodeFrom = vertices[addVerticeHelp(elementFrom)];
		stack.push(verticeNodeFrom);
		do {
			MyVerticeNode tempVNode = stack.pop();
			if(tempVNode.getElement().equals(elementTo)) {
				result.add((Ttype) tempVNode.getElement());
				isFound = true;
			}else {
				result.add((Ttype) tempVNode.getElement());
				ArrayList<MyVerticeNode> neighbours = getNeighbours((Ttype) tempVNode.getElement());
				
				for(MyVerticeNode tempNNode: neighbours) {
					if(!tempNNode.isVisited()) {
						stack.push(tempNNode);
					}
				}
			}
		}while(!stack.isEmpty() && isFound);
		
		if(!isFound) {
			throw new Exception("Path from " + elementFrom + " to " + elementTo + " does not exists");
		}
		
		return result;
	}
}
