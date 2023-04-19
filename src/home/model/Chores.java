package home.model;



public class Chores {
	private int choreId;
	private String choreName;
	private String intensity;
	private String howOften;
	private String strtDate;
	private String endDate;
	private String assignTo;
	private String status;
	private int homeId;
	
	public Chores( String choreName, String intensity, String howOften, String strtDate, String endDate,
			String assignTo, String status, int homeId) {
		super();
		//this.choreId = choreId;
		this.choreName = choreName;
		this.intensity = intensity;
		this.howOften = howOften;
		this.strtDate = strtDate;
		this.endDate = endDate;
		this.assignTo = assignTo;
		this.status = status;
		this.homeId = homeId;
	}
	public int getChoreId() {
		return choreId;
		
		
	}
	public void setChoreId(int choreId) {
		this.choreId = choreId;
		
	}
	public String getChoreName() {
		return choreName;
	}
	public void setChoreName(String choreName) {
		this.choreName = choreName;
	}
	public String getIntensity() {
		return intensity;
	}
	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}
	public String getHowOften() {
		return howOften;
	}
	public void setHowOften(String howOften) {
		this.howOften = howOften;
	}
	public String getStrtDate() {
		return strtDate;
		
	}
	public void setStrtDate(String strtDate) {
		this.strtDate = strtDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String  endDate) {
		this.endDate = endDate;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getHomeId() {
		return homeId;
	}
	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}
	
	
	
	
	

}
