package com.example.notesapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.notesapp.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
 
}
