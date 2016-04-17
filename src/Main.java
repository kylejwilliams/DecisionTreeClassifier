
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = "";
		String filename = "";
		
		System.out.println("Enter a filename or path");
		System.out.print("> ");
		filename = sc.next();
		
		Sample sample = new Sample(new FileHandler(filename));
		sample.displayData();
		
		DecisionTreeClassifier dtc = new DecisionTreeClassifier(sample);
		ArrayList<Integer> attributes = new ArrayList<>();
		for (Integer i : sample.data.get(0).keySet())
			attributes.add(i);
		attributes.remove(0);
		
		Node<List<HashMap<Integer, Integer>>> root = dtc.buildTree(sample.data, 0, attributes);
		root.printPreorder(root);
		
		do {
			System.out.println("enter a space-separated string of data to query the decision tree (ex. '1 0 0 0 1 1 0 1')");
			System.out.println("or 'exit' to close the program."); 
			System.out.println("> ");
			input = sc.next();
			Sample data = new Sample(input, sample.numDecisionVariables);
			dtc.queryTree(root, data);
			data.displayData();
		} while (input != "exit");
		
		sc.close();
	}

}
