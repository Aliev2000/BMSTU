import java.util.*;

public class IntSparseSet extends AbstractSet<Integer> {
        private int n, counter;
	private int[] sparse;
	private int[] dense;
	private int low, high;

	public IntSparseSet(int low, int high) {
		this.counter = high - low;
		this.n = 0;
		this.sparse = new int[counter];
		this.dense = new int[counter];
		this.low = low;
		this.high = high;
		for (int i = 0; i < counter; i++)
			sparse[i] = -1;
	}

	@Override
	public boolean contains(Object obj) {
		if (obj instanceof Integer) {
			int element = (int) obj;
			if ((low <= element && element < high) && sparse[element-low] != -1 && sparse[element - low] < n) return true;
			return false;
		}
		return false;
	}

	@Override
	public boolean remove(Object obj) {
		if (obj instanceof Integer) {
			int element = (int)obj;
			if (contains(element) == false) return false;
			n--;
			dense[sparse[element - low]] = dense[n];
			sparse[dense[n] - low] = sparse[element - low];
			sparse[element-low] = -1;
			return true;
		} return false;
	}

	public Iterator<Integer> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<Integer> {
		private int position = 0;

		public Integer next() {
			return dense[position++];
		}

		public boolean hasNext() {
			return position < n;
		}

		public void remove() {
			IntSparseSet.this.remove(dense[position - 1]);
		}
	}

	public int size() {
		return n;
	}

	@Override
	public boolean add(Integer elem) {
		if (contains(elem) == false && (low <= elem && elem < high)) {
			dense[n] = elem;
			sparse[elem - low] = n++;
			return true;
		}
		else return false;
	}

	@Override
	public void clear() {
		n = 0;
	}
}
