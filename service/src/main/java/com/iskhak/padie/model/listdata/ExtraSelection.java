package com.iskhak.padie.model.listdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "extraselectionstbl")
public class ExtraSelection {
    @Id
    @GeneratedValue
    @Column(name="id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "PID", nullable = false)
	@JsonIgnore
	private SetServiceItem serviceItem;
	private String name;

    @OneToOne(mappedBy="extraSelection")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

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
