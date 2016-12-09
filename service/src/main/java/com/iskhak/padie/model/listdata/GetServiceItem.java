package com.iskhak.padie.model.listdata;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "servicestbl")
public class GetServiceItem {
    @Id
    @GeneratedValue
    @Column(name="ID")
	private int id;
	private String name;
	private String mainQuestion;
	private String extraQuestion;
	@OneToMany( mappedBy = "serviceItem")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MainSelection> mainSelections;
	@OneToMany(mappedBy = "serviceItem")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ExtraSelection> extraSelections;
	

    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMainQuestion() {
		return mainQuestion;
	}
	public void setMainQuestion(String mainQuestion) {
		this.mainQuestion = mainQuestion;
	}
	public String getExtraQuestion() {
		return extraQuestion;
	}
	public void setExtraQuestion(String extraQuestion) {
		this.extraQuestion = extraQuestion;
	}
	
	public List<MainSelection> getMainSelections() {
		return mainSelections;
	}
	public void setMainSelections(List<MainSelection> mainSelections) {
		this.mainSelections = mainSelections;
	}
	
	public List<ExtraSelection> getExtraSelections() {
		return extraSelections;
	}
	public void setExtraSelections(List<ExtraSelection> extraSelections) {
		this.extraSelections = extraSelections;
	}
}
