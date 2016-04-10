import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// Data values by pos:
// 0: Alternate location? 			{ 0 = false, 1 = true }
// 1: Is there a bar? 				{ 0 = false; 1 = true }
// 2: Is it a Friday or Saturday?	{ 0 = false; 1 = true }
// 3: Am I very hungry?				{ 0 = false; 1 = true }
// 4: How many patrons?				{ 0 = none; 1 = some; 2 = full }
// 5: Price range?					{ 0 = $; 1 = $$; 2 = $$$ }
// 6: Is it raining?				{ 0 = false; 1 = true;
// 7: Do I have a reservation?		{ 0 = false; 1 = true;
// 8: Type of food?					{ 0 = French; 1 = Thai; 2 = Burger; 3 = Italian }
// 9: Estimated wait time?			{ 0 = 0-10; 1 = 10-30; 2 = 30-60; 3 = >60 }


//None = 0
//Some = 1
//Full = 2

//$   = 0
//$$  = 1
//$$$ = 2

//French  = 0
//Thai    = 1
//Burger  = 2
//Italian = 3

//0-10  = 0
//10-30 = 1
//30-60 = 2
//> 60  = 3


public class Sample {
	FileHandler fh = null;;
	String[] fileData = {};
	List<HashMap<Integer, Integer>> data = new ArrayList<>();
	int numDecisionVariables = 0;
	int totalData = 0;
	
	public Sample(FileHandler fh) {
		this.fh = fh;
		fileData = fh.linesInFile;
		numDecisionVariables = Integer.valueOf(fh.getWordAt(fileData[0], 0)); 
		totalData = Integer.valueOf(fh.getWordAt(fileData[0], 1));
		
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
		
		for (int i = 0; i < totalData - 1; i++) {
			System.out.println(i + ":\t" + data.get(i));
		}
	}
}
