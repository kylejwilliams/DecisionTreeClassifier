import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DecisionTreeClassifier {
	Sample sample;
	//ArrayList<Integer> usedKeys = new ArrayList<Integer>();

	DecisionTreeClassifier(Sample sample) {
		this.sample = sample;
	}
	
	public Node<List<HashMap<Integer, Integer>>> buildTree(
			List<HashMap<Integer, Integer>> data, int targetAttribute,
			ArrayList<Integer> attributes) {
		
		List<HashMap<Integer, Integer>> dataCopy = new ArrayList<HashMap<Integer, Integer>>(data.size());
		for (HashMap<Integer, Integer> h : data)
			dataCopy.add((HashMap<Integer, Integer>)h.clone());
		Node<List<HashMap<Integer, Integer>>> root = new Node<List<HashMap<Integer, Integer>>>(dataCopy);

		int numFalse = 0;
		int numTrue = 0;
		for (HashMap<Integer, Integer> h : dataCopy) {
			if (h.get(targetAttribute) == 0)
				numFalse++;
			else if (h.get(targetAttribute) == 1)
				numTrue++;
		}
		if (numFalse == dataCopy.size()) {
			root.label = false;
			return root;
		} else if (numTrue == dataCopy.size()) {
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
		int bestClassifier = sample.bestFeatureForSplit(dataCopy, attributes);
		root.parentAttribute = bestClassifier;

		List<List<HashMap<Integer, Integer>>> children = new ArrayList<List<HashMap<Integer, Integer>>>();
		children = sample.split(dataCopy, bestClassifier);
		for (List<HashMap<Integer, Integer>> child : children) {
			Node<List<HashMap<Integer, Integer>>> node;
			node = new Node<List<HashMap<Integer, Integer>>>(child, root);
			//root.addChild(node);

			if (node.data.isEmpty()) {
				int nt = 0;
				int nf = 0;
				for (HashMap<Integer, Integer> h : node.data) {
					if (h.get(targetAttribute) == 0)
						nf++;
					else if (h.get(targetAttribute) == 1)
						nt++;
				}
				//Node<List<HashMap<Integer, Integer>>> n = new Node<List<HashMap<Integer, Integer>>>(child);

				if (nt > nf)
					node.label = true;
				else
					node.label = false;
				root.addChild(node);
			} else {
				//Node<List<HashMap<Integer, Integer>>> n = new Node<List<HashMap<Integer, Integer>>>(child);

				ArrayList<Integer> newAttributes = new ArrayList<>();
				for (Integer key : node.data.get(0).keySet()) {
					newAttributes.add(key);
					
				}
				newAttributes.remove(0);

				node = buildTree(node.data, targetAttribute, newAttributes);
				root.addChild(node);
			}
		}

		return root;
	}
	
	public void queryTree(Node<List<HashMap<Integer, Integer>>> root, Sample data) {
		
	}
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
