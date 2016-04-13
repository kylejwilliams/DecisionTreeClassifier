import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		for (int i = 0; i < totalData; i++) { // elem 1 is first instance of actual data
			HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
			String curLine = fileData[i + 1];
			
			
			for (int j = 0; j < numDecisionVariables; j++) {
				int dataValue = 0;
				dataValue = Integer.valueOf(fh.getWordAt(curLine, j));
				if (j == numDecisionVariables - 1) m.put(0, dataValue);
				else m.put(j + 1, dataValue);
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
	
	public boolean belongsTo(HashMap<Integer, Integer> sample, int feature) {
		return sample.containsKey(feature);
		
		
	}
	
	public List<List<HashMap<Integer, Integer>>> split(List<HashMap<Integer, Integer>> sampleList, int feature) {
		
		// create a deep copy of the original list
		List<HashMap<Integer, Integer>> sampleListCopy = new ArrayList<HashMap<Integer, Integer>>(sampleList.size());
		for (HashMap<Integer, Integer> h : sampleList)
			sampleListCopy.add((HashMap<Integer, Integer>)h.clone());
		
		List<HashMap<Integer, Integer>> membersWithFeatureTrue = new ArrayList<HashMap<Integer, Integer>>();
		List<HashMap<Integer, Integer>> membersWithFeatureFalse = new ArrayList<HashMap<Integer, Integer>>();
		//The following two are to represent the features with more than two options, such as the restaurant example in the lecture notes
		List<HashMap<Integer, Integer>> membersWithFeatureEqualTwo = new ArrayList<HashMap<Integer, Integer>>();
		List<HashMap<Integer, Integer>> membersWithFeatureEqualThree = new ArrayList<HashMap<Integer, Integer>>();
		
		for (int i = 0; i < sampleListCopy.size(); i++) {
			if (sampleListCopy.get(i).get(feature) == 0) membersWithFeatureFalse.add(sampleListCopy.get(i));
			else if (sampleListCopy.get(i).get(feature) == 1) membersWithFeatureTrue.add(sampleListCopy.get(i));
			else if (sampleListCopy.get(i).get(feature) == 2) membersWithFeatureEqualTwo.add(sampleListCopy.get(i));
			else if (sampleListCopy.get(i).get(feature) == 3) membersWithFeatureEqualThree.add(sampleListCopy.get(i));
		}
		
		List<List<HashMap<Integer, Integer>>> splitList = new ArrayList<>();
		splitList.add(membersWithFeatureFalse);
		splitList.add(membersWithFeatureTrue);
		splitList.add(membersWithFeatureEqualTwo); 
		splitList.add(membersWithFeatureEqualThree);
		
		for (List<HashMap<Integer, Integer>> l : splitList)
			for (HashMap<Integer, Integer> h : l) 
				h.remove(feature);
			
		return splitList;
	}
	
	public double informationGainOnSplit(List<HashMap<Integer, Integer>> sampleList, int feature) {
		double informationGain = 0.0;
		double entropyBeforeSplit = 0.0;
		double entropyAfterSplit = 0.0;
		List<HashMap<Integer, Integer>> trueSamples = new ArrayList<HashMap<Integer, Integer>>();
		List<HashMap<Integer, Integer>> falseSamples = new ArrayList<HashMap<Integer, Integer>>();
		
		List<HashMap<Integer, Integer>> sl = new ArrayList<>(sampleList);
		
		for (HashMap<Integer, Integer> sample : sl) {
			if (sample.get(0) == 0) falseSamples.add(sample);
			else if (sample.get(0) == 1) trueSamples.add(sample);
		}
		
		List<List<HashMap<Integer, Integer>>> sampleBeforeSplit = new ArrayList<List<HashMap<Integer, Integer>>>();
		sampleBeforeSplit.add(falseSamples);
		sampleBeforeSplit.add(trueSamples);
		entropyBeforeSplit = informationEntropy(sampleBeforeSplit);
		
		List<List<HashMap<Integer, Integer>>> sampleAfterSplit = split(sl, feature);
		entropyAfterSplit = informationEntropy(sampleAfterSplit);
	
		informationGain = entropyAfterSplit - entropyBeforeSplit;
		
		return informationGain;
	}
	
	public double informationEntropy(List<List<HashMap<Integer, Integer>>> sampleList) {
		double entropy = 0.0;
		
		int totalData = 0;
		for (List<HashMap<Integer, Integer>> list : sampleList) {
			totalData += list.size();
		}
		
		for (List<HashMap<Integer, Integer>> list : sampleList) {
			double prob = (double)(list.size())/totalData;
			if (prob == 0.0) entropy += 0.0;
			else entropy += prob * Math.log10(prob) / Math.log10(2);
		}

		return entropy * (-1);
	}
	
}
