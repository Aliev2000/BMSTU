import java.util.*;

public class EqDist {
        public static void main(String[] args) {
		Graph graph = new Graph();
		graph.listMaker();
	}
}
class visited {
	static boolean[] vv;
}
class Graph {
	int N;
	int M;
	int K;
	private ArrayList<Integer> vertexes[];
	private visited vis[];
	private int dist[][];
	LinkedList<Integer> queue = new LinkedList<Integer>();
	Graph() { 
		Scanner in = new Scanner(System.in);
		this.N = in.nextInt();
		this.M = in.nextInt();
		dist = new int[N][N];
		vertexes = new ArrayList[N];
		vis = new visited[N];
		for (int i = 0; i < N; i++)
			vertexes[i] = new ArrayList<>();
		vis[0].vv = new boolean[N];
		for(int i = 0; i < M; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			vertexes[u].add(v);
			vertexes[v].add(u);
		}
		this.K = in.nextInt();
		for(int i = 0; i < K; i++) {
			int a = in.nextInt();
			BFS(a, i);
		}
	}
	void BFS(int ctrl, int ind) {
		for(int i = 0; i < N; i++) {
			vis[0].vv[i] = false;
			dist[ind][i] = -1;
		}
		dist[ind][ctrl] = 0;
		vis[ind].vv[ctrl] = true;
		queue.add(ctrl);
		while(!queue.isEmpty()) {
			int v = (int)queue.pop();
			for(int i = 0; i < vertexes[v].size(); i++)
				if(!vis[0].vv[vertexes[v].get(i)]) {
					vis[0].vv[vertexes[v].get(i)] = true;
					queue.add(vertexes[v].get(i));
					dist[ind][vertexes[v].get(i)] = dist[ind][v] + 1;
				}
		}
	}
	public void listMaker() {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < N; i++) {
			boolean flag = true;
			for(int j = 1; j < K; j++) {
				if (dist[j - 1][i] != dist[j][i] || dist[j][i] == -1) {
					flag = false;
					break;
				}
			}
			if(flag) str.append(i + " ");
		}
		if(str.length() == 0) System.out.println("-");
		else System.out.println(str);
	}
}
