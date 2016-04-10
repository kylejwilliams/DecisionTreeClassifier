import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		FileHandler fh = new FileHandler();
		Scanner sc = new Scanner(System.in);
		String data[] = {};
		String file = "";
		
		System.out.println("Enter a filename or path");
		System.out.print("> ");
		file = sc.next();
		
		try {
			data = fh.openFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// DEBUGGING
		// *********
		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i]);
		}
		// **********
		
		sc.close();
	}

}
