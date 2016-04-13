
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
		
		for (int i = 0; i < sample.numDecisionVariables; i++) {
			System.out.println(sample.informationGainOnSplit(sample.data, i));
		}
		
//		DecisionTreeClassifier dtc = new DecisionTreeClassifier(sample);
//		dtc.buildTree(sample.data);
		
		sc.close();
	}

}
