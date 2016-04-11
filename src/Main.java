
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
		
//		for (int i = 0; i < sample.numDecisionVariables; i++) {
//			List<List<HashMap<Integer, Integer>>> l = sample.split(sample.data, i);
//			System.out.println(sample.informationEntropy(l));
//		}
		
		double informationGain = sample.IGOnSplit(sample.data, 9);
		
		sc.close();
	}

}
