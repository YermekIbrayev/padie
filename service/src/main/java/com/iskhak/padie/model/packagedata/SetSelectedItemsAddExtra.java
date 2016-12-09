package com.iskhak.padie.model.packagedata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "selecteditemsaddextratbl")
public class SetSelectedItemsAddExtra {

	private int id;
	private int pid;
	private Date created;
	private int additionalQID;
	protected SetPackageModel packageModel;
	
    @Id
    @GeneratedValue
    @Column(name = "ID")
	@JsonIgnore
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@CreationTimestamp
	@JsonIgnore
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public int getAdditionalQID() {
		return additionalQID;
	}
	public void setAdditionalQID(int additionalQID) {
		this.additionalQID = additionalQID;
	}
	@ManyToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "pkgID")
	@JsonIgnore
	public SetPackageModel getPackageModel() {
		return packageModel;
	}
	public void setPackageModel(SetPackageModel packageModel) {
		this.packageModel = packageModel;
	}

	@Column(name = "PID")
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
}
