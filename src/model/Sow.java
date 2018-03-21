package model;

import java.util.Date;

public class Sow {
	private String sowNo;
	private Date birthDate;
	private String origin;
	private String breed;
	private String parity;
	private String status;

	public Sow(String sowNo) {
		this.sowNo = sowNo;
	}

	public String getSowNo() {
		return sowNo;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getParity() {
		return parity;
	}

	public void setParity(String parity) {
		this.parity = parity;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (!Sow.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Sow s = (Sow) obj;
		return s.sowNo.equals(sowNo);
	}

}
