import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node<T> {
	public T data = null;
	public Node<T> parent = null;
	public List<Node<T>> children = new ArrayList<Node<T>>();
	public boolean classification;
	public boolean label;
	public int splitAttribute;

	Node(T data) {
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
		System.out.println(prefix(root, "") + "node: " + root + " | data: " + root.data + " | split at: " + root.splitAttribute + " | classifier: " + root.classification);
		for (Node<List<HashMap<Integer, Integer>>> n : root.getChildren())
			printPreorder(n);	
	}
	
	public String prefix(Node<List<HashMap<Integer, Integer>>> node, String prefix) {
		if (node.parent == null)
			return prefix;
		else {
			prefix += "----";
			return prefix(node.parent, prefix);
		}
	}
}
