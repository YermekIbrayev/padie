package com.iskhak.padie.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="selectedpkg")
public class Review {
    @Id
    @Column(name="pkgId")
    private Integer pkgId;

	@ManyToOne
	@JoinColumn(name = "cid", nullable = true, insertable=false, updatable=false)
	private UserInfo userInfo;
    @JsonIgnore
    @Column(name="providerID", insertable=false, updatable=false)
	private Integer pid;
    @Column(name="rating")
	private Float rating;
    @JsonIgnore
    @Column(name="reviewCreated")
    private Date created;
    @Column(name="description")
	private String description;
	

	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	public Integer getPkgId() {
		return pkgId;
	}
	public void setPkgId(Integer pkgId) {
		this.pkgId = pkgId;
	}
	public Float getRating() {
		return rating;
	}
	
	public UserInfo getUser() {
		return userInfo;
	}
	
	public void setUser(UserInfo userInfo) {
		this.userInfo= userInfo;
	}
	
/*	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}*/

	public void setRating(Float rating) {
		this.rating = rating;
	}
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
