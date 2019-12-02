import java.util.*;
import java.util.Stack;
public class MaxComponent {
        public static void main(String[] args) {
		Graph graph = new Graph();
		graph.maxCmpnnt();
	}
}
class Graph {
	int N;
	public int counter = 0 ;
	int edgesL = 0;
	int vertexesL = 0;
	private boolean mark[];
	private int comp[];
	private ArrayList<Integer> vertexes[];
	Stack<Integer> stack = new Stack<>();
	int maxVertexes = 0;
	int maxEdges = 0;
	int numOfAttrib = 0;
	public Graph() {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int M = in.nextInt();
		this.N = N;
		vertexes = new ArrayList[N];
		mark  = new boolean[N];
		comp = new int[N];
		for (int i = 0; i < N; i++)
			vertexes[i] = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			int u = in.nextInt(); 
			int v = in.nextInt();
			if(u != v)
				vertexes[u].add(v);
			vertexes[v].add(u);
		}
	}
	int getSuccessor (int v) {
		for(int j = 0; j < vertexes[v].size(); j++)
			if(!mark[vertexes[v].get(j)])
				return vertexes[v].get(j);
		return -1;
	}
	void DFS(int v, int u){
		comp[v] = u;
		mark[v] = true;
		vertexesL++;
		stack.push(v);
		while(!stack.isEmpty()) {
			int current = (int)stack.peek();
			edgesL += vertexes[current].size();
			int vrtx = getSuccessor(current);
			if(vrtx == -1)
				stack.pop();
			else {
				mark[vrtx] = true;
				comp[vrtx] = u; 
				vertexesL++;
				stack.push(vrtx);
			}
		}
	}
	public void maxCmpnnt() {
		for(int i = 0; i < N; i++)
			if(!mark[i]) {
				vertexesL = 0;
				edgesL = 0;
				DFS(i, i);
				if((edgesL > maxEdges && vertexesL == maxVertexes) || vertexesL > maxVertexes) {
					maxVertexes = vertexesL;
					maxEdges = edgesL;
					numOfAttrib = i;
				}
			}
		StringBuilder str = new StringBuilder();
		str.append("graph {" + "\n");
		for(int v = 0; v < N; v++) {
			if(comp[v] == numOfAttrib)
				str.append(v + " [color = red]" + "\n");
			else
				str.append(v + "\n");
		}
		for(int v = 0; v < N; v++)
			if(vertexes[v].size() > 0)
				for(int u = 0; u < vertexes[v].size(); u++) {
					if(v < vertexes[v].get(u)) continue;
					if(comp[vertexes[v].get(u)] == numOfAttrib)
						str.append(vertexes[v].get(u) + " -- " + v + " [color = red]" + "\n");
					else str.append(vertexes[v].get(u) + " -- " + v + "\n");
				}
		str.append("}");
		System.out.print(str);
	}
}
