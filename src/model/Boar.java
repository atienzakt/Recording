package model;

import java.util.Date;

public class Boar {

	private String boarNo;
	private Date birthDate;
	private String origin;
	private String breed;

	public Boar(String boarNo) {
		this.boarNo = boarNo;
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

	public String getBoarNo() {
		return boarNo;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (!Boar.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Boar s = (Boar) obj;
		return s.boarNo.equals(boarNo);
	}
}
