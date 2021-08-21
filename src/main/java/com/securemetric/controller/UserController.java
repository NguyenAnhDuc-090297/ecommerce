package com.securemetric.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securemetric.entity.User;
import com.securemetric.model.MessageUser;
import com.securemetric.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/createuser")
	public ResponseEntity<MessageUser> addNewUser(@RequestBody User user){
		try {
			User newUsers = userService.saveUser(user);
			
			MessageUser mess = new MessageUser("Save user successfully", Arrays.asList(newUsers), "");
			return new ResponseEntity<MessageUser>(mess, HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<MessageUser>(new MessageUser("Failed to save", null, e.getMessage()), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PutMapping("/updateuserbyid/{id}")
	public ResponseEntity<MessageUser> updateUserById(@RequestBody User user, @PathVariable Long id){
		try {
			// Check user exist or not
			if(userService.checkUserExistedOrNot(id)) {
				User userUpdate = userService.getUserById(id).get();
				
				userUpdate.setFirstName(user.getFirstName());
				userUpdate.setLastName(user.getLastName());
				userUpdate.setEmail(user.getEmail());
				
				// Save change
				userService.updateUser(userUpdate);
				return new ResponseEntity<MessageUser>(new MessageUser("Successfully. Update user with id = " + id,
						Arrays.asList(userUpdate), ""), HttpStatus.OK);
			} else {
				return new ResponseEntity<MessageUser>(new MessageUser("Not found any customer with id = " + id,
						null, "User not found."), HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<MessageUser>(new MessageUser("Failure", null, e.getMessage()), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/finduser/{id}")
	public ResponseEntity<MessageUser> getUserById(@PathVariable Long id){
		try {
			Optional<User> user = userService.getUserById(id);
			if(user.isPresent()) {
				return new ResponseEntity<MessageUser>(new MessageUser("Retrieve a customer by id = " + id,
						Arrays.asList(user.get()), ""), HttpStatus.OK);
			} else {
				return new ResponseEntity<MessageUser>(new MessageUser("Not found any customer with id = " + id,
						null, "User not found."), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<MessageUser>(new MessageUser("Failure", null, e.getMessage()), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findallusers")
	public ResponseEntity<MessageUser> getAllUsers(){
		try {
			List<User> allUsers = userService.getAllUser();
			return new ResponseEntity<MessageUser>(new MessageUser("Retrieve all users",
					allUsers, ""), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<MessageUser>(new MessageUser("Fail!", null, e.getMessage()), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteuserbyid/{id}")
	public ResponseEntity<MessageUser> deleteUserById(@PathVariable Long id){
		try {
			// Check user exist or not
			if(userService.checkUserExistedOrNot(id)) {
				userService.deleteUser(id);
				return new ResponseEntity<MessageUser>(new MessageUser("Delete successfully user by id = " + id,
						null, ""), HttpStatus.OK);
			} else {
				return new ResponseEntity<MessageUser>(new MessageUser("Not found any customer with id = " + id,
						null, ""), HttpStatus.NOT_FOUND);
			}			
		} catch (Exception e) {
			return new ResponseEntity<MessageUser>(new MessageUser("Failure", null, e.getMessage()), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

















