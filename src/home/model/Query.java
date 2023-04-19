package home.model;

public class Query {
	
	private int queryId;
	private int homeId;
	private String address;
	private String status;
	private String description;
	private String note;
	
	
	public Query(int queryId, int homeId, String address, String status, String description,String note) {
		
		this.queryId = queryId;
		this.homeId = homeId;
		this.address = address;
		this.status = status;
		this.description = description;
		this.note = note;
	}
	
	public Query(int queryId) {
		this.queryId = queryId;
	}
	
	public Query() {
		
	}
	
	
	public int getQueryId() {
		return queryId;
	}
	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}
	public int getHomeId() {
		return homeId;
	}
	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
	
	

}
