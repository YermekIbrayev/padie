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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "selectedpkg")
public class SetPackageModel {
    @Id
    @GeneratedValue
    @Column(name = "pkgID")
	private int pkgID;
	@JsonIgnore
	@Column(name = "CID")
	private int clientId;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date orderDate;
	private String notes;
	private String address;
	@JsonIgnore
	@CreationTimestamp
	private Date created;
	@JsonIgnore
	private Date finishedDate;
	@JsonIgnore
	private Date acceptedDate;
	@JsonIgnore
	@Column(nullable = true)
	private Integer providerID;
	@Column(nullable = true)
	private Float price;
	
	@OneToMany(mappedBy="packageModel", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	protected List<SetSelectedItems> selectedItems;
	@OneToMany( mappedBy = "packageModel",  cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	protected List<SetSelectedItemsAdd> selectedItemsAdd;
	@OneToMany(mappedBy = "packageModel",  cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)	
	protected List<SetSelectedItemsAddExtra> selectedItemsAddExtra;
	

	public int getId() {
		return pkgID;
	}
	public void setId(int id) {
		this.pkgID = id;
	}
	

	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
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
	

	public void setCreated(Date created){
		this.created= created;
	}
	

	public Date getCreated() {
		return created;
	}
	
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	
	public Date getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
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
	

	public List<SetSelectedItems> getSelectedItems() {
		return selectedItems;
	}
	public void setSelectedItems(List<SetSelectedItems> selectedItems) {
		this.selectedItems = selectedItems;
	}
	

	public List<SetSelectedItemsAdd> getSelectedItemsAdd() {
		return selectedItemsAdd;
	}
	

	public void setSelectedItemsAdd(List<SetSelectedItemsAdd> selectedItemsAdd) {
		this.selectedItemsAdd = selectedItemsAdd;
	}
	

	public List<SetSelectedItemsAddExtra> getSelectedItemsAddExtra() {
		return selectedItemsAddExtra;
	}
	

	public void setSelectedItemsAddExtra(List<SetSelectedItemsAddExtra> selectedItemsAddExtra) {
		this.selectedItemsAddExtra = selectedItemsAddExtra;
	}
	
	@Override
	public String toString(){
		return "id:"+pkgID+" Notes:"+ notes + " Address:"+ address +" price:" +price;
	}
}
