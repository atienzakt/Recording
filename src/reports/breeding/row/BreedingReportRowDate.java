package reports.breeding.row;

import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import model.BreedingRow;
import model.FarrowingRow;
import record.FarrowingRecord;

public class BreedingReportRowDate extends BreedingReportRow {

	protected SimpleStringProperty sowNo;
	protected SimpleStringProperty boarUsed;
	protected SimpleStringProperty dateWean;

	public BreedingReportRowDate(BreedingRow br) {
		super(br);
		sowNo = new SimpleStringProperty((null == br.getSowNo()) ? "" : br.getSowNo().getSowNo());
		boarUsed = new SimpleStringProperty((null == br.getBoarUsed()) ? "" : br.getBoarUsed().stream()
				.map( n -> n.getBoarNo())
				.collect(Collectors.joining("/")));
		FarrowingRow fr = FarrowingRecord.findRefNo(br.getRefNo());
		dateWean = new SimpleStringProperty(null == fr || null == fr.getWeanDate()? "":sdf.format(fr.getWeanDate()));
	}

	public String getSowNo() {
		return sowNo.get();
	}

	public String getBoarUsed() {
		return boarUsed.get();
	}
	
	public String getDateWean() {
		return dateWean.get();
	}
}
