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

/**
 * This class represents data. Provides a number of useful functions to work
 * with the data
 * 
 * @author kjwkc3
 *
 */
public class Sample {
	FileHandler fh = null;;
	String[] fileData = {};
	List<HashMap<Integer, Integer>> data = new ArrayList<>();
	int numDecisionVariables = 0;
	int totalData = 0;

	/**
	 * creates a new sample
	 * 
	 * @param data
	 *            a string of data to be turned into a sample
	 * @param numDecisionVariables
	 *            the number of decision variables within the string of data
	 */
	public Sample(String data, int numDecisionVariables) {
		String[] d = data.split(" ");

		this.numDecisionVariables = numDecisionVariables;
		totalData = 1;

		HashMap<Integer, Integer> h = new HashMap<Integer, Integer>();

		for (int i = 0; i < d.length; i++) {
			h.put(i + 1, Integer.valueOf(d[i]));
		}

		this.data.add(h);
	}

	/**
	 * creates a sample of data by reading in from a file
	 * 
	 * @param fh
	 *            class that will handle the input file
	 */
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

	/**
	 * creates a new sample of data
	 * 
	 * @param data
	 *            data to be included in the sample
	 */
	public Sample(List<HashMap<Integer, Integer>> data) {
		this.data = data;
		numDecisionVariables = data.get(0).size();
	}

	/**
	 * creates the hashmap of data
	 */
	public void createData() {
		for (int i = 0; i < totalData; i++) { // elem 1 is first instance of
												// actual data
			HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
			String curLine = fileData[i + 1];

			for (int j = 0; j < numDecisionVariables; j++) {
				int dataValue = 0;
				dataValue = Integer.valueOf(fh.getWordAt(curLine, j));
				if (j == numDecisionVariables - 1)
					m.put(0, dataValue);
				else
					m.put(j + 1, dataValue);
			}

			data.add(m);
		}

	}

	/**
	 * displays the data to the console
	 */
	public void displayData() {
		System.out.println("Data: ");

		for (int i = 0; i < totalData; i++) {
			System.out.println(i + ":\t" + data.get(i));
		}
	}

	/**
	 * splits the given data into sub-lists by the value of the given feature
	 * 
	 * @param sampleList
	 *            data to be split
	 * @param feature
	 *            data feature on which to split the data
	 * @return a list of sub data where each list contains similar values of the
	 *         given feature
	 */
	public List<List<HashMap<Integer, Integer>>> split(List<HashMap<Integer, Integer>> sampleList, int feature) {

		// create a deep copy of the original list
		List<HashMap<Integer, Integer>> sampleListCopy = new ArrayList<HashMap<Integer, Integer>>(sampleList.size());
		for (HashMap<Integer, Integer> h : sampleList)
			sampleListCopy.add((HashMap<Integer, Integer>) h.clone());

		// pretty sure there's only supposed to be true/false, but I represented
		// the data
		// in the slides as extra lists just in case. If the provided data only
		// contains
		// true / false values, then the extra lists won't be included so
		// there's no
		// conflict
		List<HashMap<Integer, Integer>> membersWithFeatureTrue = new ArrayList<HashMap<Integer, Integer>>();
		List<HashMap<Integer, Integer>> membersWithFeatureFalse = new ArrayList<HashMap<Integer, Integer>>();
		// The following two are to represent the features with more than two
		// options, such as the restaurant example in the lecture notes
		List<HashMap<Integer, Integer>> membersWithFeatureEqualTwo = new ArrayList<HashMap<Integer, Integer>>();
		List<HashMap<Integer, Integer>> membersWithFeatureEqualThree = new ArrayList<HashMap<Integer, Integer>>();

		for (int i = 0; i < sampleListCopy.size(); i++) {
			if (sampleListCopy.get(i).get(feature) == 0)
				membersWithFeatureFalse.add(sampleListCopy.get(i));
			else if (sampleListCopy.get(i).get(feature) == 1)
				membersWithFeatureTrue.add(sampleListCopy.get(i));
			else if (sampleListCopy.get(i).get(feature) == 2)
				membersWithFeatureEqualTwo.add(sampleListCopy.get(i));
			else if (sampleListCopy.get(i).get(feature) == 3)
				membersWithFeatureEqualThree.add(sampleListCopy.get(i));
		}

		List<List<HashMap<Integer, Integer>>> splitList = new ArrayList<>();
		if (membersWithFeatureFalse.size() != 0)
			splitList.add(membersWithFeatureFalse);
		if (membersWithFeatureTrue.size() != 0)
			splitList.add(membersWithFeatureTrue);
		if (membersWithFeatureEqualTwo.size() != 0)
			splitList.add(membersWithFeatureEqualTwo);
		if (membersWithFeatureEqualThree.size() != 0)
			splitList.add(membersWithFeatureEqualThree);

		return splitList;
	}

	/**
	 * calculates the information gain if the given data was split on the given
	 * feature
	 * 
	 * @param sampleList
	 *            data that needs split
	 * @param feature
	 *            possible feature to split the data
	 * @return the information gain by splitting the data at the given feature
	 */
	public double informationGainOnSplit(List<HashMap<Integer, Integer>> sampleList, int feature) {
		double informationGain = 0.0;
		double entropyBeforeSplit = 0.0;
		double entropyAfterSplit = 0.0;

		List<List<HashMap<Integer, Integer>>> listBeforeSplit = new ArrayList<List<HashMap<Integer, Integer>>>();
		listBeforeSplit.add(sampleList);
		entropyBeforeSplit = informationEntropy(listBeforeSplit);

		List<List<HashMap<Integer, Integer>>> listAfterSplit = split(sampleList, feature);
		entropyAfterSplit = informationEntropy(listAfterSplit);

		informationGain = entropyBeforeSplit - entropyAfterSplit;
		return informationGain;
	}

	/**
	 * calculates the entropy of the given data
	 * 
	 * @param sampleList
	 *            data to be evaluated
	 * @return entropy of the data
	 */
	public double informationEntropy(List<List<HashMap<Integer, Integer>>> sampleList) {

		// ASSUMPTION: classifier is within the maps as key=0, via the project
		// description
		double entropy = 0.0;

		int totalSize = 0;
		for (List<HashMap<Integer, Integer>> list : sampleList) {
			totalSize += list.size();
		}

		for (List<HashMap<Integer, Integer>> list : sampleList) {
			int numTrue = 0;
			int numFalse = 0;

			for (HashMap<Integer, Integer> h : list) {
				if (h.get(0) == 0)
					numFalse++;
				else if (h.get(0) == 1)
					numTrue++;
			}

			double normProb = (double) list.size() / totalSize;
			double trueProb = (double) numTrue / list.size();
			double falseProb = (double) numFalse / list.size();

			// need to make sure we only calculate if the log is defined for our
			// probs
			if (trueProb == 0 || Double.isNaN(trueProb)) {
				if (falseProb == 0 || Double.isNaN(falseProb))
					entropy += 0.0;
				else
					entropy += normProb * -falseProb * Math.log10(falseProb) / Math.log10(2);
			} else if (falseProb == 0 || Double.isNaN(falseProb))
				entropy += normProb * -trueProb * Math.log10(trueProb) / Math.log10(2);
			else
				entropy += normProb * (-trueProb * Math.log10(trueProb) / Math.log10(2)
						- falseProb * Math.log10(falseProb) / Math.log10(2));

			// double prob = (double)(list.size())/totalData;
			// if (prob == 0.0) entropy += 0.0;
			// else entropy += (-1)(prob * Math.log10(prob) / Math.log10(2));
		}

		return entropy;
	}

	/**
	 * goes through each feature within the data and determines which one
	 * provides the most information gain
	 * 
	 * @param data
	 *            data to be split
	 * @param features
	 *            a list of features within the data
	 * @return the index of the best feature to split the data
	 */
	public int bestFeatureForSplit(List<HashMap<Integer, Integer>> data, List<Integer> features) {
		int bestFeature = 1;
		double bestIG = 0.0;
		for (int i : features) {
			double ig = informationGainOnSplit(data, i);
			if (ig > bestIG) {
				bestIG = ig;
				bestFeature = i;
			}
		}
		return bestFeature;
	}
}
