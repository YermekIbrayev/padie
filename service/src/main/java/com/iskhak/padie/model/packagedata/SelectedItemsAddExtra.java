package com.iskhak.padie.model.packagedata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iskhak.padie.model.listdata.ExtraSelection;

@Entity
@Table(name = "selecteditemsaddextratbl")
public class SelectedItemsAddExtra {

	private int id;
	private int pid;
	private Date created;
	private ExtraSelection additionalQID;
	protected PackageModel packageModel;
	
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
	
	@OneToOne
	@JoinColumn(name="AdditionalQID")
	public ExtraSelection getAdditionalQID() {
		return additionalQID;
	}
	public void setAdditionalQID(ExtraSelection additionalQID) {
		this.additionalQID = additionalQID;
	}
	@ManyToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "pkgID")
	@JsonIgnore
	public PackageModel getPackageModel() {
		return packageModel;
	}
	public void setPackageModel(PackageModel packageModel) {
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
