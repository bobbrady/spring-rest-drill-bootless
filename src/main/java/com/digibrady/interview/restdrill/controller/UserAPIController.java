package com.digibrady.interview.restdrill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.digibrady.interview.restdrill.data.IUserRepository;
import com.digibrady.interview.restdrill.model.User;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {

	private final IUserRepository userRepo;

	@Autowired
	public UserAPIController(IUserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		System.out.println("Fetching all Users");
		return new ResponseEntity<List<User>>(userRepo.getUsers(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		System.out.println("Fetching User with id " + id);
		User user = userRepo.getUserById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uriBuilder) {
		System.out.format("Creating User from Request Body: %s%n", user);
		User newUser = userRepo.create(user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriBuilder.path("api/user/{id}").buildAndExpand(newUser.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
		System.out.format("Creating User from Request Body: %s%n", user);
		userRepo.update(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
