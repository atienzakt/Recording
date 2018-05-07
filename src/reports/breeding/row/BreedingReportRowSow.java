package reports.breeding.row;

import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import model.BreedingRow;
import model.FarrowingRow;
import record.FarrowingRecord;
import record.SowRecord;

public class BreedingReportRowSow extends BreedingReportRow {

	protected SimpleStringProperty boarUsed;
	protected SimpleStringProperty dateWean;

	public BreedingReportRowSow(BreedingRow br) {
		super(br);
		FarrowingRow fr = FarrowingRecord.findRefNo(br.getRefNo());
		boarUsed = new SimpleStringProperty((null == br.getBoarUsed()) ? "" : br.getBoarUsed().stream()
				.map( n -> n.getBoarNo())
				.collect(Collectors.joining("/")));
		if(fr == null) {
			if(!SowRecord.isDiseased(br.getSowNo().getSowNo()) && 
					(br.getPregnancyRemarks().equals("") || br.getPregnancyRemarks().equals("+"))) {
				dateWean  = new SimpleStringProperty();
			}
			else if(br.getPregnancyRemarks().equalsIgnoreCase("+AB") 
					|| br.getPregnancyRemarks().equalsIgnoreCase("-RB") 
					|| SowRecord.isDiseased(br.getSowNo().getSowNo())){
				dateWean = new SimpleStringProperty("N/A");
			}
			else {
				dateWean = new SimpleStringProperty("ERROR");
			}
		}
		else {
			if(br.getPregnancyRemarks().equals("+") && !SowRecord.isDiseased(br.getSowNo().getSowNo())){
				dateWean  = new SimpleStringProperty(null == fr.getWeanDate()?"":sdf.format(fr.getWeanDate()));
			}
			else if(fr.getComments().toLowerCase().contains("fource") || fr.getComments().toLowerCase().contains("dry") || SowRecord.isDiseased(br.getSowNo().getSowNo())) {
				dateWean  = new SimpleStringProperty(null == fr.getWeanDate()?"N/A":sdf.format(fr.getWeanDate()));	
			}
			else {
				dateWean  = new SimpleStringProperty("ERROR");
			}
		}
	}

	public String getBoarUsed() {
		return boarUsed.get();
	}
	
	public String getDateWean() {
		return dateWean.get();
	}
}
