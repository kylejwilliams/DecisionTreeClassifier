import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Tree<T> {
    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>();
        root.data = rootData;
        root.children = new ArrayList<Node<T>>();
    }

    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;
    }
}

public class DecisionTreeClassifier {
	Sample sample;
	
	DecisionTreeClassifier(Sample sample) {
		this.sample = sample;
	}
	
	public Tree<List<HashMap<Integer, Integer>>> buildTree(List<HashMap<Integer, Integer>> data) {
		Tree<List<HashMap<Integer, Integer>>> classifier = new Tree<List<HashMap<Integer, Integer>>>(data);
		double bestAttribute = 0.0;
		
		for (int attribute : data.get(1).keySet()) {
			sample.informationGainOnSplit(data, attribute);
		}
		
		return classifier;
	}
	
//	Check for base cases
//	For each attribute a
//	Find the normalized information gain ratio from splitting on a
//	Let a_best be the attribute with the highest normalized information gain
//	Create a decision node that splits on a_best
//	Recur on the sublists obtained by splitting on a_best, and add those nodes as children of node
}
