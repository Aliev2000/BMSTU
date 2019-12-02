public class Element<T> {
        private int depth;
	private T value;
	private Element<T> root;

	public Element(T x) {
		depth = 0;
		value = x;
		root = this;
	}

	public T x() {
		return value;
	}

	public Element<T> find() {
		if(this != this.root)
			return this.root = this.root.find();
		return this;
	}

	public boolean equivalent(Element<T> elem) {
		return this.find() == elem.find();
	}

	public void union(Element<T> elem) {
		Element<T> el1 = this.find();
		Element<T> el2 = elem.find();
		if(el1.depth < el2.depth)
			el1.root = el2;
		else
			el2.root = el1;
	}
}
