import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;

public class FileHandler {
	
	public String[] openFile(String path) throws IOException {
		
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		List<String> lines = new ArrayList<>();
		
		String curString = "";
		while ((curString = br.readLine()) != null) {
			lines.add(curString);
		}
		
		String[] lineData = lines.toArray(new String[0]);

		br.close();		
		return lineData;
	}
	
	public String[] splitLine(String line) {
		int numWords = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ') {
				numWords++;
			}
		}
		numWords++; // need to get the last word between the last space and end of line
		
		String[] words = new String[numWords];
		int pos = 0; // used to keep track of place within the array for assignments
		
		String curWord = "";
		for (int i = 0; i < line.length(); i++) {
			
			// end of word
			if (line.charAt(i) == ' ') {
				
				words[pos] = curWord;
				pos++;
				curWord = "";
			}
			else {
				curWord.concat(String.valueOf(line.charAt(i)));
			}
		}
		
		return words;
	}
}
