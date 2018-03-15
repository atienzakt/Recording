package model;

import java.util.Date;
import java.util.List;

public class FarrowingRow {

	private String refNo;
	private Date farDate;
	private Sow sowNo;
	private String breed;
	private List<Boar> boarUsed;
	private String totalFar;
	private String live;
	private String male;
	private String female;
	private String sb;
	private String mm;
	private String abw;
	private String mortality;
	private Date weanDate;
	private String totalWean;
	private String ageWean;
	private String aww;
	private String comments;
	private BreedingRow breedingRow;

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Date getFarDate() {
		return farDate;
	}

	public void setFarDate(Date farDate) {
		this.farDate = farDate;
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

	public List<Boar> getBoarUsed() {
		return boarUsed;
	}

	public void setBoarUsed(List<Boar> boarUsed) {
		this.boarUsed = boarUsed;
	}

	public String getTotalFar() {
		return totalFar;
	}

	public void setTotalFar(String totalFar) {
		this.totalFar = totalFar;
	}

	public String getLive() {
		return live;
	}

	public void setLive(String live) {
		this.live = live;
	}

	public String getMale() {
		return male;
	}

	public void setMale(String male) {
		this.male = male;
	}

	public String getFemale() {
		return female;
	}

	public void setFemale(String female) {
		this.female = female;
	}

	public String getSb() {
		return sb;
	}

	public void setSb(String sb) {
		this.sb = sb;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getAbw() {
		return abw;
	}

	public void setAbw(String abw) {
		this.abw = abw;
	}

	public String getMortality() {
		return mortality;
	}

	public void setMortality(String mortality) {
		this.mortality = mortality;
	}

	public Date getWeanDate() {
		return weanDate;
	}

	public void setWeanDate(Date weanDate) {
		this.weanDate = weanDate;
	}

	public String getTotalWean() {
		return totalWean;
	}

	public void setTotalWean(String totalWean) {
		this.totalWean = totalWean;
	}

	public String getAgeWean() {
		return ageWean;
	}

	public void setAgeWean(String ageWean) {
		this.ageWean = ageWean;
	}

	public String getAww() {
		return aww;
	}

	public void setAww(String aww) {
		this.aww = aww;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BreedingRow getBreedingRow() {
		return breedingRow;
	}

	public void setBreedingRow(BreedingRow breedingRow) {
		this.breedingRow = breedingRow;
	}

}
