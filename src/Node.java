import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node<T> {
	public T data = null;
	public Node<T> parent = null;
	public List<Node<T>> children = new ArrayList<Node<T>>();
	public boolean label = false;
	public int parentAttribute = 1;

	public Node(T data) {
		this.data = data;
	}

	public Node(T data, Node<T> parent) {
		this.data = data;
		this.parent = parent;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void addChild(Node<T> child) {
		child.parent = this;
		this.children.add(child);
	}

	public T getData() {
		return this.data;
	}
	
	public void printPreorder(Node<List<HashMap<Integer, Integer>>> root) {
		if (root == null) {
			System.out.println("reached a leaf");
			return;
		}
		System.out.println("split attribute: " + root.parentAttribute + " root: " + root + " parent: " + root.parent);
		for (Node<List<HashMap<Integer, Integer>>> n : root.getChildren())
			printPreorder(n);
		//System.out.println();
	}
}
