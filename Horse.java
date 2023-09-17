public class Horse {
	private String name;
	private boolean healthy;
	private Integer accumDistanceTraveled;
	private String warCry;
	
	Horse(String name, boolean healthy, String warCry) {
		this.name = name;
		this.healthy = healthy;
		this.accumDistanceTraveled = 0;
		this.warCry = warCry;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isHealthy() {
		return healthy;
	}
	
	public int getAccumDistanceTraveled() {
		return accumDistanceTraveled;
	}
	
	public void setAccumDistanceTraveled(int accumDistanceTraveled) {
		this.accumDistanceTraveled = accumDistanceTraveled;
	}
	
	public String getWarCry() {
		return warCry;
	}
}