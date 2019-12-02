import java.util.*;

public class SparseSet<T extends Hintable> extends AbstractSet<T> {
        private ArrayList<T> dense;
	private int n;

	public SparseSet() {
		this.dense = new ArrayList<>();
		this.n = 0;
	}

	public void clear() {
		this.n = 0;
	}

	public int size() {
		return n;
	}

        public boolean contains(T obj) {
		return (n > 0 && dense.get(obj.hint()) == obj);
	}

	public boolean add(T obj) {
		if (!contains(obj)) {
			dense.add(obj);
			obj.setHint(n);
			n++;
			return true;
		}
		return false;
	}

        public boolean remove(T obj) {
                if (contains(obj)) {
			n--;
                        dense.set(obj.hint(), dense.get(n));
                        dense.get(n).setHint(obj.hint());
                        return true;
                }
                return false;
        }

	public Iterator<T> iterator() {
		return new ObjIterator();
	}

	private class ObjIterator implements Iterator<T> {
		int counter = 0;

		public boolean hasNext() {
			return (counter < n);
		}

		public T next() {
			return dense.get(counter++);
		}

		public void remove() {
			SparseSet.this.remove(dense.get(counter - 1));
		}
	}
}
