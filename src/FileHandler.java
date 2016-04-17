import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * @author kjwkc3
 *
 */
public class FileHandler {
	
	String[] linesInFile = {};
	
	public FileHandler(String filename) {
		try {
			linesInFile = openFile(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[] openFile(String path) throws IOException {
		
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		List<String> lines = new ArrayList<>();
		
		String curString = "";
		while ((curString = br.readLine()) != null) {
			lines.add(curString);
		}
		
		linesInFile = lines.toArray(new String[0]);

		br.close();		
		return linesInFile;
	}
	
	public String[] splitLine(String line) {
		int numWords = getNumWords(line);		
		String[] words = new String[numWords];
		
		for (int i = 0; i < numWords; i++) {
			words[i] = getWordAt(line, i);
		}
		
		return words;
	}
	
	public String getWordAt(String line, int pos) {
		String word = "";
		int curPos = -1; // to grab the first word if needed

		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ') {
				curPos++;
				if (curPos == pos) return word;
				word = "";
			} 
			else word += line.charAt(i);
		}
		
		curPos++; // to grab the last word in the line
		if (curPos == pos) return word; 
		else return "No word found"; // generic error
	}
	
	public int getNumWords(String line) {
		int numWords = 0;
		
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ') {
				numWords++;
			}
		}
		numWords++; // need to get the last word between the last space and end of line
		
		return numWords;
	}
}
