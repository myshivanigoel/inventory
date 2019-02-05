package in.db.auth.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Tocken {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer userId;
	private String tocken;
	private Character validFlag;
	private String typeOfTocken; 
	
	private Character status;
	private Date generatedDate;
	private Date modifiedDate;
	private Date disabledDate;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTocken() {
		return tocken;
	}
	public void setTocken(String tocken) {
		this.tocken = tocken;
	}
	public Character getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(Character validFlag) {
		this.validFlag = validFlag;
	}
	public String getTypeOfTocken() {
		return typeOfTocken;
	}
	public void setTypeOfTocken(String typeOfTocken) {
		this.typeOfTocken = typeOfTocken;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public Date getGeneratedDate() {
		return generatedDate;
	}
	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getDisabledDate() {
		return disabledDate;
	}
	public void setDisabledDate(Date disabledDate) {
		this.disabledDate = disabledDate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
