import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

/**
 * This class implements the Decision Tree Classifier algorithm
 * 
 * @author kjwkc3
 *
 */
public class DecisionTreeClassifier {
	Sample sample;

	DecisionTreeClassifier(Sample sample) {
		this.sample = sample;
	}

	/**
	 * This method constructs the decision tree
	 * 
	 * @param data
	 *            training data
	 * @param targetAttribute
	 *            attribute index containing the training data's classification
	 * @param attributes
	 *            list of indices for remaining attributes that need to be
	 *            evaluated
	 * @return the root node of the generated tree
	 */
	public Node<List<HashMap<Integer, Integer>>> buildTree(List<HashMap<Integer, Integer>> data, int targetAttribute,
			ArrayList<Integer> attributes) {

		// avoid overwriting the original data
		List<HashMap<Integer, Integer>> dataCopy = new ArrayList<HashMap<Integer, Integer>>(data.size());
		for (HashMap<Integer, Integer> h : data)
			dataCopy.add((HashMap<Integer, Integer>) h.clone());
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
			root.classification = false;
			return root;
		} else if (numTrue == dataCopy.size()) {
			root.classification = true;
			return root;
		}

		if (attributes.size() == 0) {
			if (numFalse > numTrue) {
				root.classification = false;
				return root;
			} else {
				root.classification = true;
				return root;
			}
		}

		int bestClassifier = sample.bestFeatureForSplit(dataCopy, attributes);
		root.splitAttribute = bestClassifier;

		List<List<HashMap<Integer, Integer>>> children = new ArrayList<List<HashMap<Integer, Integer>>>();
		children = sample.split(dataCopy, bestClassifier);

		for (List<HashMap<Integer, Integer>> child : children) {
			Node<List<HashMap<Integer, Integer>>> node;
			node = new Node<List<HashMap<Integer, Integer>>>(child, root);

			if (node.data.isEmpty()) {
				int nt = 0; // num true
				int nf = 0; // num false
				for (HashMap<Integer, Integer> h : node.data) {
					if (h.get(targetAttribute) == 0)
						nf++;
					else if (h.get(targetAttribute) == 1)
						nt++;
				}

				if (nt > nf)
					node.classification = true;
				else
					node.classification = false;
				root.addChild(node);
			} else {
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

	/**
	 * classifies new data by traversing the given decision tree
	 * 
	 * @param root
	 *            // root node of the decision tree
	 * @param data
	 *            // new data to be classified
	 * @return // a classification for the new data
	 */
	public boolean queryTree(Node<List<HashMap<Integer, Integer>>> root, Sample data) {
		int curAttribute = root.splitAttribute;
		if (curAttribute == 0)
			return root.classification;

		int index = data.data.get(0).get(curAttribute);
		Sample s = new Sample(data.data);

		return queryTree(root.getChildren().get(index), data);
	}

}
