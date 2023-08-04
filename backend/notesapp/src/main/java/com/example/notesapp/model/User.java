package com.example.notesapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Representing the model of the user.
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int userID;
    
	String name;
	String email;
	String password;
	
	User() {}


	public User(int userID, String name, String email, String password) {
		super();
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [id=" + userID + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
