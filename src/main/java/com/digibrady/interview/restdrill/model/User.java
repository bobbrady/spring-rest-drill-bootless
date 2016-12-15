package com.digibrady.interview.restdrill.model;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(id, name, job, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name)
		    && Objects.equals(this.job, other.job) && Objects.equals(this.salary, other.salary);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", job=");
		builder.append(job);
		builder.append(", salary=");
		builder.append(salary);
		builder.append("]");
		return builder.toString();
	}
}
