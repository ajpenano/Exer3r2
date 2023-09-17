import java.util.Scanner;

public class UserInput {
	Scanner scanner = new Scanner(System.in);
	
	private int track;	
	void inputTrackLength() {
		try {
			System.out.print("Enter the distance between the start and the finish line: ");
			track = scanner.nextInt();
			if (track < 1) {
				System.out.println("Track length must be at least 1.");
				inputTrackLength();
			}
		} catch (Exception e) {
			System.out.println("Invalid value entered. Run the program again and enter a valid integer from 1 or greater.");
			scanner.close();
			System.exit(0);
		}
	}
	int getTrackLength() {
		return track;
	}
	
	private int numHorses;
	void inputNumberOfHorses() {
		try {
			System.out.print("Enter the number of horses: ");
			numHorses = scanner.nextInt();
			if (numHorses < 2) {
				inputNumberOfHorses();
			}			
		} catch (Exception e) {
			System.out.println("Invalid value entered. Run the program again and enter a valid integer greater than 1.");
			scanner.close();
			System.exit(0);
		}
	}
	int getNumHorses() {
		return numHorses;
	}
}
