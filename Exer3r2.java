import java.util.List;
import java.util.stream.Collectors;  

public class Exer3r2 {

	public static void main(String[] args) {
				
		UserInput ui;
		HorseService hs;
		
		ui = new UserInput();
		ui.inputTrackLength();
		int track = ui.getTrackLength();
		hs = new HorseService(track);
		
		ui.inputNumberOfHorses();
		int numHorses = ui.getNumHorses();
		
		hs.makeHorses(numHorses);
		hs.ensureAtLeast2Healthy(numHorses);
		
		List<HorseService> healthyHorses = hs.getHorses().stream().filter(x -> x.isHealthy()).peek(y -> y.setName(y.getName().toUpperCase()))
		        .map(h -> new HorseService(h, hs))
		        .collect(Collectors.toList());
		
		System.out.println("Healthy Horses: " + healthyHorses.size());
		
		System.out.println("\nThe race started...");
		hs.startRace(healthyHorses);
		
		System.out.println("\n|-------------------------COMPLETE RACE RESULTS-------------------------|");
		hs.printRankingsAndAccumulatedDistanceTraveled(hs.getHorseRankingList(), 1);

	}
}