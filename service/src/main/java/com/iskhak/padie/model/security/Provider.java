package com.iskhak.padie.model.security;

public class Provider {
	private Long pid;
	private String name;
	private Float rating;
	
	public Provider(Long pid, String name, Float rating){
		this.pid = pid;
		this.name = name;
		this.rating = rating;
	}
	
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	
}
