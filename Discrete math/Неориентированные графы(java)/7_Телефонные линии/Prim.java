import java.util.*;

public class Prim {
        public static void main(String[] args) {
		MyGraph graph = new MyGraph();
		graph.MST_Prim();
	}
}
class MyGraph {
	private int N;
	private int weight[][];
	private ArrayList<Integer> edge[];
	private int INF = Integer.MAX_VALUE;
	public MyGraph() {
		Scanner in = new Scanner(System.in);
		N = in.nextInt();
		int M = in.nextInt();
		weight = new int[N][N];
		edge = new ArrayList[N];
		for(int i = 0; i < N; i++) {
			edge[i] = new ArrayList<>();
			for(int j = 0; j < N; j++)
				weight[i][j] = INF;
		}
		for(int i = 0; i < M; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int w = in.nextInt();
			edge[u].add(v);
			edge[v].add(u);
			weight[u][v] = w;
			weight[v][u] = w;
		}
	}
	void MST_Prim() {
		int[] distance = new int[N];
		for(int i = 0; i < N; i++)
			distance[i] = INF;
		boolean[] visited = new boolean[N];
		distance[0] = 0;
		while(true) {
			int v = -1;
			for(int i = 0; i < N; i++)
				if(!visited[i] && (v == -1 || distance[i] < distance[v]))
					v = i;
			if (v == -1 || distance[v] == INF) break;
			visited[v] = true;
			for(int i = 0; i < N; i++)
				if (!visited[i] && (weight[v][i] < (distance[i] < INF ? INF : distance[i])))
					distance[i] = distance[i] < weight[v][i] ? distance[i] : weight[v][i];
		}
		int mstPrim = 0;
		for(int i = 0; i < N; i++)
			mstPrim += distance[i];
		System.out.println(mstPrim);
	}
}
