package com.digibrady.interview.restdrill.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.digibrady.interview.restdrill.model.User;

@Repository
public class UserRespository implements IUserRepository {

	private final AtomicInteger atomicId = new AtomicInteger(0);
	private final Map<Integer, User> users = new ConcurrentHashMap<>();

	public UserRespository() {
		User user = new User(atomicId.incrementAndGet(), "Fubar", "Developer", 135.00);
		users.put(user.getId(), user);
		user = new User(atomicId.incrementAndGet(), "Stevie", "Developer", 75.00);
		users.put(user.getId(), user);
	}

	@Override
	public User getUserById(int id) {
		return users.get(id);
	}

	@Override
	public List<User> getUsers() {
		List<User> values = new ArrayList<>(users.values());
		return values;
	}

	@Override
	public User create(User user) {
		User newUser = new User(atomicId.getAndIncrement(), user);
		users.put(user.getId(), user);
		return newUser;
	}

	@Override
	public User update(User user) {
		users.put(user.getId(), user);
		return user;
	}

	@Override
	public boolean delete(int id) {
		User user = users.get(id);
		if (user != null) {
			users.remove(user);
			return true;
		} else {
			return false;
		}
	}

}
