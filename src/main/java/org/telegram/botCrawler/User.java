package org.telegram.botCrawler;

public class User {
	
	private String username;
	private int currentId;
	private int nextId;
	
	public User(String username,int currentId, int nextId){
		this.username = username;
		this.currentId = currentId;
		this.nextId = nextId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

}

