import java.io.IOException;
import java.util.Scanner;

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

public class Main {

	public static void main(String[] args) {
		FileHandler fh = new FileHandler();
		Scanner sc = new Scanner(System.in);
		String lines[] = {};
		String file = "";
		
		System.out.println("Enter a filename or path");
		System.out.print("> ");
		file = sc.next();
		
		try {
			lines = fh.openFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// DEBUGGING
		// *********
//		for (int i = 0; i < lines.length; i++) {
//			System.out.println(lines[i]);
//		}
		// **********
		
		sc.close();
	}

}
