package reports.breeding.row;

import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import model.BreedingRow;
import model.FarrowingRow;
import record.FarrowingRecord;
import utils.RemovePoint;

public class BreedingReportRow {

	private SimpleStringProperty refNo;
	// private SimpleStringProperty sowNo;
	// private SimpleStringProperty breed;
	// private SimpleStringProperty dateWeaned;
	protected SimpleStringProperty dateBreed;
	// private SimpleStringProperty fpDays;
	// private SimpleStringProperty parity;
	
	protected SimpleStringProperty breedingTime;
	protected SimpleStringProperty pregnancyRemarks;
	// private SimpleStringProperty pregnancyRemarksDate;
	// private SimpleStringProperty farrowDueDate;
	// private SimpleStringProperty farrowActualDate;
	// private SimpleStringProperty prevBreedingRefNo;
	// private SimpleStringProperty prevBreedingResult;
	private SimpleStringProperty birth;
	private SimpleStringProperty liveBirth;
	private SimpleStringProperty comments;
	// private SimpleStringProperty ABW;
	protected SimpleStringProperty staff;
	protected SimpleStringProperty dateFar;
	
	

	protected final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	public BreedingReportRow(BreedingRow br) {
		refNo = new SimpleStringProperty(br.getRefNo());
		// sowNo = new SimpleStringProperty((null ==
		// br.getSowNo())?"":br.getSowNo().getSowNo());
		// breed = new SimpleStringProperty(br.getBreed());
		// dateWeaned = new SimpleStringProperty((null ==
		// br.getDateWeaned())?"":sdf.format(br.getDateWeaned()));
		dateBreed = new SimpleStringProperty((null == br.getDateBreed()) ? "" : sdf.format(br.getDateBreed()));
		// fpDays = new SimpleStringProperty(br.getFpDays());
		// parity = new SimpleStringProperty(br.getParity());
//		boarUsed = new SimpleStringProperty((null == br.getBoarUsed()) ? "" : br.getBoarUsed().stream()
//				.map( n -> n.getBoarNo())
//				.collect(Collectors.joining("/")));
		breedingTime = new SimpleStringProperty(br.getBreedingTime());
		pregnancyRemarks = new SimpleStringProperty(br.getPregnancyRemarks());
		FarrowingRow fr = FarrowingRecord.findRefNo(br.getRefNo());
		dateFar = new SimpleStringProperty(null == fr || null == fr.getFarDate()? "":sdf.format(fr.getFarDate()));
		birth  = new SimpleStringProperty(null == fr || null == fr.getTotalFar()? "":fr.getTotalFar());
		liveBirth  = new SimpleStringProperty(null == fr || null == fr.getLive()? "":fr.getLive());
		// pregnancyRemarksDate = new SimpleStringProperty((null ==
		// br.getPregnancyRemarksDate())?"":sdf.format(br.getPregnancyRemarksDate()));
		// farrowDueDate = new SimpleStringProperty((null ==
		// br.getFarrowDueDate())?"":sdf.format(br.getFarrowDueDate()));
		// farrowActualDate = new SimpleStringProperty((null ==
		// br.getFarrowActualDate())?"":sdf.format(br.getFarrowActualDate()));
		// prevBreedingRefNo = new SimpleStringProperty(br.getPrevBreedingRefNo());
		// prevBreedingResult = new SimpleStringProperty(br.getPrevBreedingResult());
		// liveBirth = new SimpleStringProperty(br.getLiveBirth());
		comments = new SimpleStringProperty(br.getComments());
		// ABW = new SimpleStringProperty(br.getABW());
		staff = new SimpleStringProperty(br.getStaff());
	}

	public String getDateBreed() {
		return dateBreed.get();
	}

	public String getBreedingTime() {
		return breedingTime.get();
	}

	public String getPregnancyRemarks() {
		return pregnancyRemarks.get();
	}

	public String getStaff() {
		return staff.get();
	}

	public String getDateFar() {
		return dateFar.get();
	}
	
	public String getComments() {
		return comments.get();
	}
	
	public String getRefNo() {
		return refNo.get();
	}
	
	public String getBirth() {
		return RemovePoint.remove(birth.get());
	}
	
	public String getLiveBirth() {
		return RemovePoint.remove(liveBirth.get());
	}
}
