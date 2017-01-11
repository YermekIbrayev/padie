package com.iskhak.padie.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedNativeQueries({
	@NamedNativeQuery(
			name = "callRatedProviders", 
			query = "call rated_providers()",
			resultClass=Provider.class)
})

@Entity
@Table(name="providers")
public class Provider {
    @Id
    @Column(name = "pid")
	private Long pid;
    @Column(name = "username", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
	private String name;
    @Column(name = "rating")
	private Float rating;
    
    public Provider(){
    	super();
    }
	
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
