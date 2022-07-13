package models;

public class Employee {
	private int id;
	private String username;
	private String password;
	
	public void setId(int id) { this.id = id; }
	public int getId() { return this.id; }
	
	private void setUsername(String username) {	this.username = username; }
	public String getUsername() { return this.username; }
	
	public void setPassword(String password) {	this.password = password; }
	public String getPassword() { return this.password; }
	
	public Employee(int id, String username, String password) {
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public Employee(String username, String password) {
		this(0, username, password);
	}
	
	public Employee() {
		this(0, "", "");
	}
	
	@Override
	public String toString() {
		return "Employee [getId()=" + this.getId() +  
				", getUsername()=" + this.getUsername() +  
				", getPassword()=" + this.getPassword() +  "]";
	}
}
