import java.util.*;

public class GraphBase {
        private static class Vertex {
		int T1;
		int comp;
		int low;
		ArrayList<Integer> arc = new ArrayList<>();
	}

	private static class Condensation {
		int min;
		boolean isBase;
		ArrayList<Integer> arc = new ArrayList<>();
	}

	private static int time = 1;
	private static int count = 1;

	private static void Tarjan(Vertex[] graph) {
		for(int v = 0; v < graph.length; v++) {
			graph[v].T1=0;
			graph[v].comp=0;
		}
		Stack<Vertex> s = new Stack<>();
		for(int v = 0; v < graph.length; v++)
			if(graph[v].T1 == 0)
				VisitVertex_Tarjan(graph, v, s);
	}

	private static void VisitVertex_Tarjan(Vertex[] graph, int v, Stack<Vertex> s) {
		graph[v].T1 = time;
		graph[v].low = time;
		time++;
		s.push(graph[v]);
		int n = graph[v].arc.size();
		for(int u = 0; u < n; u++) {
			if(graph[graph[v].arc.get(u)].T1 == 0)
				VisitVertex_Tarjan(graph, graph[v].arc.get(u), s);
			if(graph[graph[v].arc.get(u)].comp == 0 && graph[v].low > graph[graph[v].arc.get(u)].low)
				graph[v].low = graph[graph[v].arc.get(u)].low;
		}
		if(graph[v].T1 == graph[v].low) {
			Vertex u;
			do {
				u = s.pop();
				u.comp = count;
			} while(!(u.equals(graph[v])));
			count++;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int M = in.nextInt();
		Vertex[] graph = new Vertex[N];
		for(int i = 0; i < N; i++)
			graph[i] = new Vertex();
		for(int i = 0; i < M; i++) {
			int v = in.nextInt();
			int u = in.nextInt();
			graph[v].arc.add(u);
		}
		Tarjan(graph);
		Condensation[] base = new Condensation[count];                
		for(int i = 0; i < count; i++)
			base[i]=new Condensation();
		for(int i = 0; i < count; i++)
			base[i].isBase = true;
		for(int v = 0; v < N; v++) {
			if(base[graph[v].comp].arc.size() == 0)
				base[graph[v].comp].min = v;
			base[graph[v].comp].arc.add(v);
		}
		for(int v = 0; v < N; v++) {
			for(int u = 0; u < graph[v].arc.size(); u++) {
				if(graph[v].comp != graph[graph[v].arc.get(u)].comp)
					base[graph[graph[v].arc.get(u)].comp].isBase = false;
			}
		}
		for(int i = 1; i < GraphBase.count; i++)
			if(base[i].isBase)
				System.out.print(base[i].min + " ");
	}
}
