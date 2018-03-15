package model;

import java.util.Date;
import java.util.List;

public class BreedingRow {

	private String refNo;
	private Sow sowNo;
	private String breed;
	private Date dateWeaned;
	private Date dateBreed;
	private String fpDays;
	private String parity;
	private List<Boar> boarUsed;
	private String breedingTime;
	private String pregnancyRemarks;
	private Date pregnancyRemarksDate;
	private Date farrowDueDate;
	private Date farrowActualDate;
	private String prevBreedingRefNo;
	private String prevBreedingResult;
	private String live;
	private String comments;
	private String ABW;
	private String staff;

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Sow getSowNo() {
		return sowNo;
	}

	public void setSowNo(Sow sowNo) {
		this.sowNo = sowNo;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public Date getDateWeaned() {
		return dateWeaned;
	}

	public void setDateWeaned(Date dateWeaned) {
		this.dateWeaned = dateWeaned;
	}

	public Date getDateBreed() {
		return dateBreed;
	}

	public void setDateBreed(Date dateBreed) {
		this.dateBreed = dateBreed;
	}

	public String getFpDays() {
		return fpDays;
	}

	public void setFpDays(String fpDays) {
		this.fpDays = fpDays;
	}

	public String getParity() {
		return parity;
	}

	public void setParity(String parity) {
		this.parity = parity;
	}

	public List<Boar> getBoarUsed() {
		return boarUsed;
	}

	public void setBoarUsed(List<Boar> boarUsed) {
		this.boarUsed = boarUsed;
	}

	public String getBreedingTime() {
		return breedingTime;
	}

	public void setBreedingTime(String breedingTime) {
		this.breedingTime = breedingTime;
	}

	public String getPregnancyRemarks() {
		return pregnancyRemarks;
	}

	public void setPregnancyRemarks(String pregnancyRemarks) {
		this.pregnancyRemarks = pregnancyRemarks;
	}

	public Date getPregnancyRemarksDate() {
		return pregnancyRemarksDate;
	}

	public void setPregnancyRemarksDate(Date pregnancyRemarksDate) {
		this.pregnancyRemarksDate = pregnancyRemarksDate;
	}

	public Date getFarrowDueDate() {
		return farrowDueDate;
	}

	public void setFarrowDueDate(Date farrowDueDate) {
		this.farrowDueDate = farrowDueDate;
	}

	public Date getFarrowActualDate() {
		return farrowActualDate;
	}

	public void setFarrowActualDate(Date farrowActualDate) {
		this.farrowActualDate = farrowActualDate;
	}

	public String getPrevBreedingRefNo() {
		return prevBreedingRefNo;
	}

	public void setPrevBreedingRefNo(String prevBreedingRefNo) {
		this.prevBreedingRefNo = prevBreedingRefNo;
	}

	public String getPrevBreedingResult() {
		return prevBreedingResult;
	}

	public void setPrevBreedingResult(String prevBreedingResult) {
		this.prevBreedingResult = prevBreedingResult;
	}

	public String getLive() {
		return live;
	}

	public void setLive(String live) {
		this.live = live;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getABW() {
		return ABW;
	}

	public void setABW(String aBW) {
		ABW = aBW;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getRefNo() {
		return refNo;
	}

}
