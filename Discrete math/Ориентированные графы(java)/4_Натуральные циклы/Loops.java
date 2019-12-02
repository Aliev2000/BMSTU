import java.util.*;

public class Loops {
        private static class Vertex implements Comparable<Vertex> {
		ArrayList<Vertex> to, from, busket;
		Vertex label, ancestor, sdom, dom, parent;
		int n, mark;
		boolean used, operand;

		Vertex(int n) {
			this.to = new ArrayList<>();
			this.from = new ArrayList<>();
			this.busket = new ArrayList<>();
			this.n = n;
			operand = false;
		}

		@Override
		public int compareTo(Vertex graph) {
			return (this.n - graph.n);
		}
	}

	private static int N = 0;
	private static void DFS(Vertex v) {
		v.n = N;
		v.used = true;
		N++;
		for(Vertex u : v.from)
			if(!u.used) {
				u.parent = v;
				DFS(u);
			}
	}

	private static void Dominators(ArrayList<Vertex> graph) {
		for(Vertex v : graph) {
			v.sdom = v.label = v;
			v.ancestor = null;
		}
		int n = graph.size() - 1;
		for(int i = graph.size() - 1; i > 0; i--) {
			Vertex w = graph.get(i);
			for(Vertex v : w.to) {
				Vertex u = findMin(v);
				if(u.sdom.n < w.sdom.n)
					w.sdom = u.sdom;
			}
			w.ancestor = w.parent;
			w.sdom.busket.add(w);
			for(Vertex v : w.parent.busket) {
				Vertex u = findMin(v);
				v.dom = (u.sdom == v.sdom ? w.parent : u);
			}
			w.parent.busket.clear();
		}
		n++;
		for(int i = 1; i < n; i++)
			if(graph.get(i).dom != graph.get(i).sdom)
				graph.get(i).dom = graph.get(i).dom.dom;
		graph.get(0).dom = null;
	}

	private static Vertex findMin(Vertex v) {
		Vertex min;
		if(v.ancestor == null)
			min = v;
		else {
			Stack<Vertex> stack = new Stack<Vertex>();
			Vertex u = v;
			while(u.ancestor.ancestor != null) {
				stack.push(u);
				u = u.ancestor;
			}
			while(!stack.empty()) {
				v = stack.pop();
				if(v.ancestor.label.sdom.n < v.label.sdom.n)
					v.label = v.ancestor.label;
				v.ancestor = u.ancestor;
			}
			min = v.label;
		}
		return min;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.nextLine();
		ArrayList<Vertex> graph = new ArrayList<>();
		for(int i = 0; i < n; i++)
			graph.add(new Vertex(i));
		ArrayList<Integer> operations = new ArrayList<>();
		ArrayList<Integer> mrk = new ArrayList<>();
		int a, b;

		for(int i = 0; i < n; i++) {
			mrk.add(in.nextInt());
			String s = new String(in.next());
			switch (s.charAt(0)) {
				case 'A':
					if(i < n - 1) {
						graph.get(i + 1).to.add(graph.get(i));
						graph.get(i).from.add(graph.get(i + 1));
					}
					break;
				case 'B':
					operations.add(in.nextInt());
					if(i < n - 1) {
						graph.get(i + 1).to.add(graph.get(i));
						graph.get(i).from.add(graph.get(i + 1));
					}
					graph.get(i).operand = true;
					break;
				case 'J':
					operations.add(in.nextInt());
					graph.get(i).operand = true;
					break;
			}
		}
		for(int i = 0, j = 0, m = graph.size(); i < m; i++)
			if(graph.get(i).operand) {
				graph.get(i).from.add(graph.get(mrk.indexOf(operations.get(j))));
				graph.get(mrk.indexOf(operations.get(j++))).to.add(graph.get(i));
			}
		DFS(graph.get(0));
		for(int i = 0, m = graph.size(); i < m; i++) {
			if(!graph.get(i).used) {
				graph.remove(i--);
				m--;
			}
			else for(int j = 0, l = graph.get(i).to.size(); j < l; j++)
				if(!graph.get(i).to.get(j).used) {
					graph.get(i).to.remove(j--);
					l--;
				}
		}
		Collections.sort(graph);
		Dominators(graph);
		int loop = 0;
		for(Vertex v : graph)
			for(Vertex u : v.to) {
				for(; v != u && u != null; u = u.dom);
				if(v == u) {
					loop++;
					break;
				}
			}
		System.out.println(loop);
	}
}
