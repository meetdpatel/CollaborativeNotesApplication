package com.example.notesapp.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.notesapp.model.LoginRequest;
import com.example.notesapp.model.User;
import com.example.notesapp.service.UserService;
import com.example.notesapp.repo.UserRepo;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private UserRepo userRepository;


	// Get All Users
    @GetMapping("/user")
    public List<User> getAllUsers() {
    	return this.userRepository.findAll();
        //return userService.getUsers();
    }
    
 
    @PostMapping("/user")
    public ResponseEntity<Optional<User>> addUser(@RequestBody User user) {
    	
    	if (user.getName()==null || 
		user.getEmail()==null ||
		user.getPassword()==null) {
    		return new ResponseEntity<>(Optional.ofNullable(null), HttpStatus.BAD_REQUEST);
    	}
	
		User newUser = new User(userService.incCurrentID(),user.getName(), 
				                 user.getEmail(), user.getPassword());
		userRepository.save(newUser);	
		return new ResponseEntity<>(Optional.ofNullable(newUser),HttpStatus.CREATED);
    }
    
    
    // Get User by ID
    @GetMapping("/user/{id}")
    public Optional<Optional<User>> getUserById(@PathVariable(value = "id") int Id) {
        return Optional.ofNullable(userRepository.findById(Id));
    }
    
    
    //Delete a User by ID
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable(value = "id") int Id) {
        if (userService.deleteUser(Id) == true)
        		 return "User Deleted"; 
    	return "Delete error";
    }
    
    @GetMapping("/user/findByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
      User user = userService.findByEmail(email);
      if (user == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>(user, HttpStatus.OK);
      }
    }

    
    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody User user, @PathVariable(value = "id") int Id) {
    	User oldUser = userRepository.findById(Id).get();
		if(Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
			oldUser.setEmail(user.getEmail());
		}
		if(Objects.nonNull(user.getName()) && !"".equalsIgnoreCase(user.getName())) {
			oldUser.setName(user.getName());
		}
		if(Objects.nonNull(user.getPassword()) && !"".equalsIgnoreCase(user.getPassword())) {
			oldUser.setPassword(user.getPassword());
		}
		
		return userRepository.save(oldUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginUser) {
        User user = userService.login(loginUser.getEmail(), loginUser.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            // Save the file to the server's local file system
            byte[] bytes = file.getBytes();
            Path path = Paths.get("D:\\Development\\Projects\\NotesApplication\\backend\\notesapp\\fileupload" + file.getOriginalFilename());
            Files.write(path, bytes);
            
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }




}