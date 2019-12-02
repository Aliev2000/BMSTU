import java.util.*;
import java.io.DataInputStream;
import java.io.InputStream;
public class MapRoute {
        private static class Coord {
		int i;
		int j;

		public Coord(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
	
	private static class Vertex {
		int index;
		int key;
		int dist;
		ArrayList<Coord> edge = new ArrayList<>();

		public Vertex(int key) {
			this.key = key;
			dist = Integer.MAX_VALUE;
		}
	}

	private static class PQueue {
		int cap;
		int count;
		Vertex[] heap;

		public PQueue(int cap) {
			cap = cap;
			count = 0;
			heap = new Vertex[cap];
		}
	}

	private static class Parser {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;

		public Parser(InputStream in) {
			din = new DataInputStream(in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead =  0;
		}

		public int nextInt() {
			int ret =  0;
			boolean neg;
			try {
				byte c = read();
				while (c <= ' ')
					c = read();
				neg = c == '-';
				if (neg)
					c = read();
				do {
					ret = ret * 10 + c - '0';
					c = read();
				} while (c > ' ');

				if (neg) return -ret;
			} catch (Exception e) {}
			return ret;
		}

		private void fillBuffer() {
			try {
				bytesRead = din.read(buffer, bufferPointer =  0, BUFFER_SIZE);
			} catch (Exception e) {}
			if (bytesRead == -1) buffer[ 0] = -1;
		}

		private byte read() {
			if (bufferPointer == bytesRead) fillBuffer();
			return buffer[bufferPointer++];
		}
	}

	private static void insert(PQueue queue, Vertex ptr) {
		int i = queue.count;
		queue.count++;
		queue.heap[i] = ptr;
		while(i > 0 && (queue.heap[(i - 1) / 2].dist > queue.heap[i].dist)) {
			Vertex temp = queue.heap[i];
			queue.heap[i] = queue.heap[(i - 1) / 2];
			queue.heap[(i - 1) / 2] = temp;
			queue.heap[i].index = i;
			i = (i - 1) / 2;
		}
		queue.heap[i].index = i;
	}

	private static Vertex ExtractMax(PQueue queue) {
		Vertex ptr = queue.heap[0];
		queue.count--;
		if(queue.count > 0) {
			queue.heap[0] = queue.heap[queue.count];
			queue.heap[0].index = 0;
			Heapify(0, queue.count, queue.heap);
		}
		return ptr;
	}

	private static void Heapify(int i, int n, Vertex[] heap) {
		while(true) {
			int l = 2 * i + 1;
			int r = 2 * i + 2;
			int j = i;
			if((l < n) && (heap[i].dist > heap[l].dist))	i = l;
			if ((r < n) && (heap[i].dist > heap[r].dist))	i = r;
			if (heap[i].dist == heap[j].dist)	break;
			Vertex temp = heap[i];
			heap[i] = heap[j];
			heap[j] = temp;
			heap[i].index = i;
			heap[j].index = j;
		}
	}

	private static void DecreaseKey(PQueue queue, Vertex ptr, int k) {
		int i = ptr.index;
		ptr.key = k;
		while(i > 0 && (queue.heap[(i - 1) / 2].dist > k)) {
			Vertex temp = queue.heap[i];
			queue.heap[i] = queue.heap[(i - 1) / 2];
			queue.heap[(i - 1) / 2] = temp;
			queue.heap[i].index = i;
			i = (i - 1) / 2;
		}
		ptr.index = i;
	}

	private static boolean Relax(Vertex u, Vertex v) {
		boolean changed;
		if(u.dist == Integer.MAX_VALUE)	changed = false;
		else	changed = (u.dist + v.key) < v.dist;
		if(changed)	v.dist = u.dist + v.key;
		return changed;
	}

	private static void Dijkstra(Vertex[][] matr, PQueue queue) {
		matr[0][0].dist = matr[0][0].key;
		while(queue.count > 0) {
			Vertex v = ExtractMax(queue);
			v.index = -1;
			for(Coord P : v.edge)
				if(matr[P.i][P.j].index != -1 && Relax(v, matr[P.i][P.j]))
					DecreaseKey(queue, matr[P.i][P.j], matr[P.i][P.j].dist);
		}
	}

	public static void main(String[] args) {
		Parser in = new Parser(System.in);
		int N = in.nextInt();
		Vertex[][] matr = new Vertex[N][N];
		PQueue queue = new PQueue(N*N);
		for(int i = 0; i < N; i++)
			for(int j = 0; j < N; j++)
				matr[i][j] = new Vertex(in.nextInt());                                
		for(int i = 0; i < N; i++)
			for(int j = 0; j < N; j++) {
				if(i + 1 < N)	matr[i][j].edge.add(new Coord(i + 1, j));
				if(j + 1 < N)	matr[i][j].edge.add(new Coord(i, j + 1));
				if(i + 1 < N)	matr[i + 1][j].edge.add(new Coord(i, j));
				if(j + 1 < N)	matr[i][j + 1].edge.add(new Coord(i, j));
				insert(queue, matr[i][j]);
			}
		Dijkstra(matr, queue);
		System.out.println(matr[N - 1][N - 1].dist);
	}
}
