package com.iskhak.padie.model.packagedata;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "selecteditemstbl")
public class SetSelectedItems {
    @Id
    @GeneratedValue
    @Column(name = "ID")
	@JsonIgnore
	private int id;
	@CreationTimestamp
	@JsonIgnore
	private Date created;
	private int serviceID;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "pkgID")
	@JsonIgnore
	protected SetPackageModel packageModel;
	


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	public int getServiceID() {
		return serviceID;
	}
	public void setServiceItem(int serviceID) {
		this.serviceID = serviceID;
	}
	

	public SetPackageModel getPackageModel() {
		return packageModel;
	}
	public void setPackageModel(SetPackageModel packageModel) {
		this.packageModel = packageModel;
	}
}
