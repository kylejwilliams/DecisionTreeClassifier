import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node<T> {
	public T data = null;
	public Node<T> parent = null;
	public List<Node<T>> children = new ArrayList<Node<T>>();
	public boolean label = false;
	public int parentAttribute = 1;

	public Node() {
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
		child.parent = parent;
		this.children.add(child);
	}

	public T getData() {
		return this.data;
	}
}

public class DecisionTreeClassifier {
	Sample sample;
	ArrayList<Integer> usedKeys = new ArrayList<Integer>();

	DecisionTreeClassifier(Sample sample) {
		this.sample = sample;
	}

	public Node<List<HashMap<Integer, Integer>>> buildTree(
			List<HashMap<Integer, Integer>> data, int targetAttribute,
			ArrayList<Integer> attributes) {
		Node<List<HashMap<Integer, Integer>>> root = new Node<List<HashMap<Integer, Integer>>>();

		int numFalse = 0;
		int numTrue = 0;
		for (HashMap<Integer, Integer> h : data) {
			if (h.get(targetAttribute) == 0)
				numFalse++;
			else if (h.get(targetAttribute) == 1)
				numTrue++;
		}
		if (numFalse == data.size()) {
			root.label = false;
			return root;
		} else if (numTrue == data.size()) {
			root.label = true;
			return root;
		}

		if (attributes.size() == 0) {
			if (numFalse > numTrue) {
				root.label = false;
				return root;
			} else {
				root.label = true;
				return root;
			}
		}
		int bestClassifier = sample.bestFeature(data, attributes);
		root.parentAttribute = bestClassifier;

		List<List<HashMap<Integer, Integer>>> children = new ArrayList<List<HashMap<Integer, Integer>>>();
		children = sample.split(data, bestClassifier);
		for (List<HashMap<Integer, Integer>> child : children) {
			Node<List<HashMap<Integer, Integer>>> node;
			node = new Node<List<HashMap<Integer, Integer>>>(child, root);

			if (node.data.isEmpty()) {
				int nt = 0;
				int nf = 0;
				for (HashMap<Integer, Integer> h : node.data) {
					if (h.get(targetAttribute) == 0)
						nf++;
					else if (h.get(targetAttribute) == 1)
						nt++;
				}
				Node<List<HashMap<Integer, Integer>>> n = new Node<List<HashMap<Integer, Integer>>>();

				if (nt > nf)
					n.label = true;
				else
					n.label = false;
				node.addChild(n);
			} else {
				Node<List<HashMap<Integer, Integer>>> n = new Node<List<HashMap<Integer, Integer>>>();

				ArrayList<Integer> newAttributes = new ArrayList<>();
				for (Integer key : node.data.get(0).keySet())
					newAttributes.add(key);

				n = buildTree(node.data, targetAttribute, newAttributes);
				node.addChild(n);
			}
		}

		return root;
	}

	// ID3 (Examples, Target_Attribute, Attributes)
	// Create a root node for the tree
	// If all examples are positive, Return the single-node tree Root, with
	// label = +.
	// If all examples are negative, Return the single-node tree Root, with
	// label = -.
	// If number of predicting attributes is empty, then Return the single node
	// tree Root,
	// with label = most common value of the target attribute in the examples.
	// Otherwise Begin
	// A ← The Attribute that best classifies examples.
	// Decision Tree attribute for Root = A.
	// For each possible value, vi, of A,
	// Add a new tree branch below Root, corresponding to the test A = vi.
	// Let Examples(vi) be the subset of examples that have the value vi for A
	// If Examples(vi) is empty
	// Then below this new branch add a leaf node with label = most common
	// target value in the examples
	// Else below this new branch add the subtree ID3 (Examples(vi),
	// Target_Attribute, Attributes – {A})
	// End
	// Return Root
}
