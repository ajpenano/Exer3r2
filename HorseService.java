import java.util.Arrays;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HorseService implements Runnable {

	private int track;
	HorseService(int track) {
		this.track = track;
	}
	
	public int getTrack() {
		return track;
	}
	
	UserInput ui = new UserInput();
	void ensureAtLeast2Healthy(int numHorses) {
		long healthyCount = horses.stream().filter(horse -> horse.isHealthy()).count();
		if (healthyCount < 2) {
			System.out.println("Number of healthy horses capable of racing is less than 2.");
			ui.inputNumberOfHorses();
			numHorses = ui.getNumHorses();
			horses.clear();
			i = 1;
			makeHorses(numHorses);
			ensureAtLeast2Healthy(numHorses);
		} 
	}
	
	private int i = 1;
	private List<Horse> horses = new ArrayList<Horse>();
	private static String[] warCriesArray = {null, "", "Hurray!", "Yahoo!", "Yipee!", "Yehey!"};
	private static final List<String> warCries = new ArrayList<String>(Arrays.asList(warCriesArray));
	
	Random random = new Random();
	void makeHorses(int numHorses) {	
		Supplier<String> randomWarCrySupplier = () -> warCries.get(random.nextInt(warCries.size()));	

		if (horses.size() < numHorses) {
			horses.add(new Horse("horse"+i, random.nextBoolean(), randomWarCrySupplier.get()));	
			i++;
			makeHorses(numHorses);
		}
	}
	
	public List<Horse> getHorses() {
		return horses;
	}
	
	public void startRace(List<HorseService> healthyHorses) {
		List<HorseService> remainingHorses = new ArrayList<HorseService>(healthyHorses);
		Stream.iterate(0, i -> i + 1)
		        .peek(i -> remainingHorses.parallelStream().filter(h -> h.getHorse().getAccumDistanceTraveled() < track).forEach(HorseService::run))
		        .takeWhile(i -> remainingHorses.parallelStream().anyMatch(h -> h.getHorse().getAccumDistanceTraveled() < track))
		        .forEach(i -> {
		            List<HorseService> qualifiedHorses = remainingHorses.parallelStream().filter(h -> h.getHorse().getAccumDistanceTraveled() >= track).collect(Collectors.toList());
		            remainingHorses.removeAll(qualifiedHorses);
		});
	}
	
	void printWarCry(List<Horse> horseRankingList, int ranking) {
		Optional<String> checkNull = Optional.ofNullable(horseRankingList.get(ranking-1).getWarCry());	
		if (checkNull.isPresent() && checkNull.get().isEmpty()) {
		    System.out.print(" No war cry (empty).");
		} else if (checkNull.isPresent()){   
            System.out.print(" " + horseRankingList.get(ranking-1).getWarCry());
		} else {
            System.out.print(" No war cry (null).");  
        }
		if (ranking == 1) {
			System.out.print(" *");	
		}
		System.out.println();
	}
	
	private OrdinalNumbers on = new OrdinalNumbers();;
	private int sumOfAllDistancesTraveled;
	public void printRankingsAndAccumulatedDistanceTraveled(List<Horse> horseRankingList, int ranking) { 
		if (ranking <= horseRankingList.size()) {
			System.out.println(on.getOrdinal(ranking) + " Place\t-\t" + horseRankingList.get(ranking-1).getName() + "\t\tAggregated distance traveled: " + horseRankingList.get(ranking-1).getAccumDistanceTraveled());	
			sumOfAllDistancesTraveled+=horseRankingList.get(ranking-1).getAccumDistanceTraveled();
			ranking++;
			printRankingsAndAccumulatedDistanceTraveled(horseRankingList, ranking);
		} else {
			System.out.println("\nSum of all distances traveled by the horses: " + sumOfAllDistancesTraveled);
		}
	}
	
	private Horse horse;
	private HorseService mainInstance;
	HorseService(Horse horse, HorseService mainInstance) {
		this.horse = horse;
		this.mainInstance = mainInstance;
	}
	
    public Horse getHorse() {
        return horse;
    }
	
    private List<Horse> horseRankingList = new ArrayList<>();
    
    public List<Horse> getHorseRankingList() {
    	return horseRankingList;
    }
    
    public List<Horse> setHorseRankingList() {
    	return horseRankingList;
    }
    
	@Override
	public void run() {
			int speed = random.nextInt(10) + 1;
			synchronized (mainInstance) {
				int track = mainInstance.getTrack();
				List<Horse> horseRankingList = mainInstance.getHorseRankingList();
				this.horse.setAccumDistanceTraveled(horse.getAccumDistanceTraveled() + speed);
				int accumDistanceTraveled = horse.getAccumDistanceTraveled();
				if ((track-accumDistanceTraveled) < 0) {
					System.out.println(horse.getName() + " ran " + speed + " units. Aggregated distance travelled: " + accumDistanceTraveled + ". Distance left: 0");
				} else {
					System.out.println(horse.getName() + " ran " + speed + " units. Aggregated distance travelled: " + accumDistanceTraveled + ". Distance left: " + (track-accumDistanceTraveled));	
				}							
				if(accumDistanceTraveled >= track) {
					mainInstance.setHorseRankingList().add(horse);
					if (horseRankingList.size() == 1) {
						System.out.print("* " + horseRankingList.get(0).getName() + " won!");
						printWarCry(horseRankingList, horseRankingList.size());
					} else {
						System.out.print(horseRankingList.get(horseRankingList.size()-1).getName() + " finishes!");
						printWarCry(horseRankingList, horseRankingList.size());
					}
				}	
			}
	}

}
