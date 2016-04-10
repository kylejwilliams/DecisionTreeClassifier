
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
		
		sc.close();
	}

}
