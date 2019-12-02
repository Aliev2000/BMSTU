import java.util.*;
public class BridgeNum {
        public static void main(String[] args) {
		Graph graph = new Graph();
		graph.printNumberOfBridges();
	}
}
class Vertex {
	//public int index;
	public String mark;
	public Vertex parent;
	public int comp;
	public ArrayList<Integer> edges;
	Vertex(int i){
		//this.index = i;
		this.comp = -1;
		this.mark = "white";
		this.parent = null;
		this.edges = new ArrayList<>(0);
	}
}
class Graph {
	private int N;
	private int counter = 0;
	private int counter2 = 0;
	private Vertex[] vertexes;
	LinkedList<Vertex> queue = new LinkedList<Vertex>();
	public Graph() {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int M = in.nextInt();
		this.N = N;
		vertexes = new Vertex[N];
	
		for (int i = 0; i < N; i++)
			vertexes[i] = new Vertex(i);

		for (int i = 0; i < M; i++) {
			int u = in.nextInt(); 
			int v = in.nextInt();
			if(u != v)
				vertexes[u].edges.add(v);
			vertexes[v].edges.add(u);
		}
	}
	private void DFS1() {
		for(int i = 0; i < N; i++)
			if(vertexes[i].mark == "white") {
				vertexes[i].parent = null;
				counter--;
				VisitVertex1(vertexes[i]);
			}
	}
	private void VisitVertex1(Vertex v) {
		v.mark = "gray";
		queue.add(v);
		for(int i = 0; i < v.edges.size(); i++)
			if(vertexes[v.edges.get(i)].mark == "white") {
				vertexes[v.edges.get(i)].parent = v;
				VisitVertex1(vertexes[v.edges.get(i)]);
			}
	}
	private void DFS2() {
		while(!queue.isEmpty()) {
			Vertex v = queue.pop();
			if(v.comp == -1) {
				VisitVertex2(v);
				counter2++;
			}
		}
	} 
	private void VisitVertex2(Vertex v) {
		v.comp = counter2;
		for(int i = 0; i < v.edges.size(); i++) {
			if(vertexes[v.edges.get(i)].comp == -1 && vertexes[v.edges.get(i)].parent != v)
				VisitVertex2(vertexes[v.edges.get(i)]);
		}
	}
	public void printNumberOfBridges() {
		DFS1();
		DFS2();
		System.out.println(counter + counter2);
	}
}
