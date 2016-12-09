package com.iskhak.padie.model.listdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mainselectionstbl")
public class MainSelection {
	

	private int id;
	private SetServiceItem serviceItem;
	private String name;

    @Id
    @GeneratedValue
    @Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "PID", nullable = false)
	@JsonIgnore
	public SetServiceItem getServiceItem() {
		return serviceItem;
	}
	
	public void setServiceItem(SetServiceItem serviceItem){
		this.serviceItem = serviceItem;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
