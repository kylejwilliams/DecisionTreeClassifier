import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// Data values by pos:
// 0: Alternate location? 			{ 0 = false, 	1 = true 							}
// 1: Is there a bar? 				{ 0 = false; 	1 = true 							}
// 2: Is it a Friday or Saturday?	{ 0 = false; 	1 = true 							}
// 3: Am I very hungry?				{ 0 = false; 	1 = true 							}
// 4: How many patrons?				{ 0 = none; 	1 = some; 	2 = full 				}
// 5: Price range?					{ 0 = $; 		1 = $$; 	2 = $$$ 				}
// 6: Is it raining?				{ 0 = false; 	1 = true; 							}
// 7: Do I have a reservation?		{ 0 = false; 	1 = true;							}
// 8: Type of food?					{ 0 = French; 	1 = Thai; 	2 = Burger; 3 = Italian }
// 9: Estimated wait time?			{ 0 = 0-10; 	1 = 10-30; 	2 = 30-60; 	3 = >60 	}

public class Sample {
	FileHandler fh = null;;
	String[] fileData = {};
	List<HashMap<Integer, Integer>> data = new ArrayList<>();
	int numDecisionVariables = 0;
	int totalData = 0;
	
	public Sample(FileHandler fh) {
		this.fh = fh;
		fileData = fh.linesInFile;
		
		// The "+1" is because of the way the example is laid out in the 
		// project description. numDecisionVariables is just that - it does
		// not include the actual classification, hence why the "+1" is
		// needed to get that variable. There may be indexing errors if
		// the example in the project description was meant to include the
		// classification originally. In that case it should be easily
		// fixed by removing the "+1".
		numDecisionVariables = Integer.valueOf(fh.getWordAt(fileData[0], 0)) + 1;
		
		// The "-1" is similar to the above. For some reason the total data 
		// value in the project description includes the first line that gives 
		// the number of decision values and total data, so the actual data is 
		// one less than the value given.
		totalData = Integer.valueOf(fh.getWordAt(fileData[0], 1)) - 1;
		
		createData();
	}
	
	public void createData() {
		for (int i = 1; i < totalData; i++) { // elem 1 is first instance of actual data
			HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
			String curLine = fileData[i];
			
			
			for (int j = 0; j < numDecisionVariables; j++) {
				int dataValue = Integer.valueOf(fh.getWordAt(curLine, j));
				m.put(j, dataValue);
			}
			
			data.add(m);
		}
		
	}
	
	public void displayData() {
		System.out.println("Data: ");
		
		for (int i = 0; i < totalData; i++) {
			System.out.println(i + ":\t" + data.get(i));
		}
	}
}
