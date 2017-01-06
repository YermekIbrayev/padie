package com.iskhak.padie.model.packagedata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iskhak.padie.config.Constants;

@Entity
@Table(name = "viewedpkg")
public class ViewedPackage implements Comparable<ViewedPackage>{
	private int id;
	private int selectedPkg;
 	private String deviceId;
	private Date viewed;
	private Long providerId;
	
    @Id
    @GeneratedValue
    @Column(name = "id")
	public int getId() {
		return id;
	}
   
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="pkgID")
	public int getSelectedPkg(){
		return selectedPkg;
	}
	
	public void setSelectedPkg(int selectedPkg){
		this.selectedPkg = selectedPkg;
	}

	public String getDeviceId(){
		return deviceId;
	}
	
	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}
	
    @Column(name = "created")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.DATE_TIME_FORMAT)
	public Date getViewed() {
		return viewed;
	}
	public void setViewed(Date viewed) {
		this.viewed = viewed;
	}

	@Override
	public int compareTo(ViewedPackage arg0) {
		return this.viewed.compareTo(arg0.viewed);
	}
	
	@JsonIgnore
	public Long getProviderId(){
		return providerId;
	}
	
	public void setProviderId(Long providerId){
		this.providerId = providerId;
	}

}
