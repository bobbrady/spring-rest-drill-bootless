package com.digibrady.interview.restdrill.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	private final int id;

	private final String name;

	private final String job;

	private final double salary;

	@JsonCreator
	public User(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("job") String job,
	    @JsonProperty("salary") double salary) {
		this.id = id;
		this.name = name;
		this.job = job;
		this.salary = salary;
	}

	public User(int id, User user) {
		this.id = id;
		this.name = user.getName();
		this.job = user.getJob();
		this.salary = user.getSalary();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getJob() {
		return job;
	}

	public double getSalary() {
		return salary;
	}
}
