package com.iskhak.padie.model.packagedata;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iskhak.padie.config.Constants;

@Entity
@Table(name = "selectedpkg")
public class PackageModel {
    @Id
    @GeneratedValue
    @Column(name = "pkgID")
	private int pkgID;
	@JsonIgnore
	@Column(name = "CID")
	private long clientId;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.DATE_TIME_FORMAT)
	private Date orderDate;
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.DATE_TIME_FORMAT)
	private Date viewed;
	private String notes;
	private String address;
	/*@JsonIgnore*/
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.DATE_TIME_FORMAT)
	private Date acceptedDate;
	/*@JsonIgnore*/
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.DATE_TIME_FORMAT)
	private Date finishedDate;
	@JsonIgnore
	@Column(name="verificationDate", nullable=true)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.DATE_TIME_FORMAT)
	private Date verificationDate;
	
	@JsonIgnore
	@Column(nullable = true)
	private Integer providerID;
	@Column(nullable = true)
	private Float price;

	
	@JsonIgnore
	@OneToMany(mappedBy="selectedPkg")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<ViewedPackage> viewedList;
	
	@OneToMany(mappedBy="packageModel", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private  List<SelectedItems> selectedItems;
	@OneToMany( mappedBy = "packageModel",  cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SelectedItemsAdd> selectedItemsAdd;
	@OneToMany(mappedBy = "packageModel",  cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)	
	private List<SelectedItemsAddExtra> selectedItemsAddExtra;
	

	public int getId() {
		return pkgID;
	}
	public void setId(int id) {
		this.pkgID = id;
	}
	

	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public void setViewed(Date viewed){
		this.viewed= viewed;
	}
	
	public Date getViewed() {
		return viewed;
	}
	
	
	public void setViewedList(List<ViewedPackage> viewed){
		this.viewedList= viewed;
	}
	
	public List<ViewedPackage> getViewedList() {
		return viewedList;
	}
	
	public Date getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	

	public Date getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(Date verificationDate) {
		this.verificationDate = verificationDate;
	}
	public Integer getProviderID() {
		return providerID;
	}
	public void setProviderID(Integer providerID) {
		this.providerID = providerID;
	}
	

	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	

	public List<SelectedItems> getSelectedItems() {
		return selectedItems;
	}
	public void setSelectedItems(List<SelectedItems> selectedItems) {
		this.selectedItems = selectedItems;
	}
	

	public List<SelectedItemsAdd> getSelectedItemsAdd() {
		return selectedItemsAdd;
	}
	

	public void setSelectedItemsAdd(List<SelectedItemsAdd> selectedItemsAdd) {
		this.selectedItemsAdd = selectedItemsAdd;
	}
	

	public List<SelectedItemsAddExtra> getSelectedItemsAddExtra() {
		return selectedItemsAddExtra;
	}
	

	public void setSelectedItemsAddExtra(List<SelectedItemsAddExtra> selectedItemsAddExtra) {
		this.selectedItemsAddExtra = selectedItemsAddExtra;
	}
	
	@Override
	public String toString(){
		return "id:"+pkgID+" Notes:"+ notes + " Address:"+ address +" price:" +price;
	}
}
