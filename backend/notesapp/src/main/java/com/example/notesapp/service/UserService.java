package com.example.notesapp.service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.notesapp.model.User;

@Service
public class UserService {
	static List<User> userList;
	int currentID;

	public UserService() {
		super();
		// TODO Auto-generated constructor stub
		userList = new ArrayList<User>();
		currentID=0;
		initList();
	
	}
	
	public void initList() {
		User user1 = new User(1, "Bob","bob@sample.com", "bob_pass");
		User user2 = new User(2, "Alice", "alice@sample.com", "alice_pass");
		User user3 = new User(3, "Carol", "carol@sample.com", "carol_pass");
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		currentID=3;
	}
	
	public List<User> getUsers() {
		return userList;
	}

	public int getCurrentID() {
		return currentID;
	}

	public void setCurrentID(int currentID) {
		this.currentID = currentID;
	}
	
	public int incCurrentID() {
		currentID += 1;
		return currentID;
	}
	
	public void addUser(User newUser) {
		userList.add(newUser);
	}
	
	public User findByID(int userID) {
		 Iterator<User> it = userList.iterator();
		 while (it.hasNext()) {
			 User user = it.next();
			 if (user.getUserID() == userID)
				 return user;
		 }
		 
		 return null;
	}
	
	public boolean deleteUser(int userID) {
		User user = findByID(userID);
		return userList.remove(user);
	}
	
	public User findByEmail(String email) {
	    for (User user : userList) {
	        if (user.getEmail().equals(email)) {
	            return user;
	        }
	    }
	    return null;
	}


	public User login(String email, String password) {
        Iterator<User> it = userList.iterator();
        while (it.hasNext()) {
            User user = it.next();
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
	
	
	
	
}
