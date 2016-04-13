
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
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
		dtc.buildTree(sample.data, 0, attributes);
		
		sc.close();
	}

}
