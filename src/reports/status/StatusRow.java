package reports.status;

import java.math.BigDecimal;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.FarrowingRow;
import model.Sow;
import record.FarrowingRecord;
import utils.DateFormat;

public class StatusRow {

	public final StringProperty no;
	public final StringProperty sowNo;
	public final StringProperty dateOfBirth;
	public final StringProperty breed;
	public final StringProperty parity;
	public final StringProperty totalLitter;
	public final StringProperty totalLive;
	public final StringProperty firstParity;
	public final StringProperty lastWean;
	public final StringProperty status;
	
	public StatusRow(Sow sow , int number) {
		no = new SimpleStringProperty(number+"");
		sowNo = new SimpleStringProperty(sow.getSowNo());
		dateOfBirth = new SimpleStringProperty(null == sow.getBirthDate()? "NOT GIVEN":DateFormat.formatToString(sow.getBirthDate()));
		breed = new SimpleStringProperty(null == sow.getBreed()? "":sow.getBreed());
		parity = new SimpleStringProperty(null == sow.getParity()? "":sow.getParity());
		
		totalLitter = new SimpleStringProperty(FarrowingRecord.filterBySow(sow).stream()
												.map(s -> new BigDecimal(s.getTotalFar()))
												.reduce(BigDecimal.ZERO,BigDecimal::add).intValue()+"");
		
		totalLive = new SimpleStringProperty(FarrowingRecord.filterBySow(sow).stream()
												.map(s -> new BigDecimal(s.getLive()))
												.reduce(BigDecimal.ZERO,BigDecimal::add).intValue()+"");
		
		firstParity = new SimpleStringProperty(FarrowingRecord.filterBySow(sow).isEmpty()?"N/A"
				:DateFormat.formatToString(FarrowingRecord.filterBySow(sow).stream().findFirst().get().getFarDate()));
		
		Optional<FarrowingRow> lastValidWean = FarrowingRecord.filterBySow(sow).stream()
				.filter(fr -> null != fr.getWeanDate())
				.reduce((first,second) ->second);
		
		lastWean = new SimpleStringProperty(lastValidWean.isPresent()?DateFormat.formatToString(lastValidWean.get().getWeanDate()):"N/A");
		status = new SimpleStringProperty(sow.getStatus());
	}

	public String getNo() {
		return no.get();
	}

	public String getSowNo() {
		return sowNo.get();
	}

	public String getDateOfBirth() {
		return dateOfBirth.get();
	}

	public String getBreed() {
		return breed.get();
	}

	public String getParity() {
		return parity.get();
	}

	public String getTotalLitter() {
		return totalLitter.get();
	}

	public String getTotalLive() {
		return totalLive.get();
	}

	public String getFirstParity() {
		return firstParity.get();
	}

	public String getLastWean() {
		return lastWean.get();
	}

	public String getStatus() {
		return status.get();
	}
	
	
}
