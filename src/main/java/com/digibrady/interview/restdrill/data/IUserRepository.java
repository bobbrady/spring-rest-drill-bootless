package com.digibrady.interview.restdrill.data;

import java.util.List;

import com.digibrady.interview.restdrill.model.User;

public interface IUserRepository {
	
	User getUserById(int id);
	
	List<User> getUsers();
	
	User create(User user);
	
	User update(User user);
	
	boolean delete(int id);

}
