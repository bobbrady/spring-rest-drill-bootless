package com.digibrady.interview.restdrill.model;

public class User {

	private final int id;
	
	private final String name;
	
	private final String job;
	
	private final double salary;

	
	public User(int id, String name, String job, double salary){
		this.id = id;
		this.name = name;
		this.job = job;
		this.salary = salary;
	}

	public User(int id, User user){
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
