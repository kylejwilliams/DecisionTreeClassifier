import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * represents individual nodes with the tree data structure
 * 
 * @author kjwkc3
 *
 * @param <T>
 *            type of data for the node
 */
public class Node<T> {
	public T data = null;
	public Node<T> parent = null;
	public List<Node<T>> children = new ArrayList<Node<T>>();
	public boolean classification;
	public boolean label;
	public int splitAttribute;

	/**
	 * Constructs a node given data
	 * 
	 * @param data
	 *            data contained within the node
	 */
	public Node(T data) {
		this.data = data;
	}

	/**
	 * Constructs a node given its data and parent node
	 * 
	 * @param data
	 *            data contained within the node
	 * @param parent
	 *            the parent node within the tree
	 */
	public Node(T data, Node<T> parent) {
		this.data = data;
		this.parent = parent;
	}

	/**
	 * gets the children of the given node
	 * 
	 * @return a list of children of the given node
	 */
	public List<Node<T>> getChildren() {
		return children;
	}

	/**
	 * adds a child to the given node
	 * 
	 * @param child
	 *            child node to give to the given parent
	 */
	public void addChild(Node<T> child) {
		child.parent = this;
		this.children.add(child);
	}

	/**
	 * @return the data contained in the given node
	 */
	public T getData() {
		return this.data;
	}

	/**
	 * prints the tree structure in preorder traversal
	 * 
	 * @param root
	 *            the root node of the tree
	 */
	public void printPreorder(Node<List<HashMap<Integer, Integer>>> root) {
		System.out.println(prefix(root, "") + root + " | data: " + root.data + " | split at: " + root.splitAttribute
				+ " | classifier: " + root.classification);
		for (Node<List<HashMap<Integer, Integer>>> n : root.getChildren())
			printPreorder(n);
	}

	/**
	 * returns a string containing dashes that represent indentation for
	 * printing the tree. Indentation represents that the node on the given line
	 * is a child of the node of the line of one less indent
	 * 
	 * @param node
	 *            the node whose prefix is being determined
	 * @param prefix
	 *            the current prefix for the node (needed for recursion
	 * @return the prefix for the given node
	 */
	public String prefix(Node<List<HashMap<Integer, Integer>>> node, String prefix) {
		if (node.parent == null)
			return prefix;
		else {
			prefix += "----";
			return prefix(node.parent, prefix);
		}
	}
}
