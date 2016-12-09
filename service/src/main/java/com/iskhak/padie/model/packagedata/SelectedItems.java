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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iskhak.padie.model.listdata.SetServiceItem;

@Entity
@Table(name = "selecteditemstbl")
public class SelectedItems {
    @Id
    @GeneratedValue
    @Column(name = "ID")
	@JsonIgnore
	private int id;
	@CreationTimestamp
	@JsonIgnore
	private Date created;
	@OneToOne
	@JoinColumn(name="ServiceID")
	private SetServiceItem serviceItem;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "pkgID")
	@JsonIgnore
	protected PackageModel packageModel;
	


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


	public SetServiceItem getServiceID() {
		return serviceItem;
	}
	public void setServiceItem(SetServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	

	public PackageModel getPackageModel() {
		return packageModel;
	}
	public void setPackageModel(PackageModel packageModel) {
		this.packageModel = packageModel;
	}
}
