package reports.breeding.row;

import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import model.BreedingRow;
import model.FarrowingRow;
import record.FarrowingRecord;

public class BreedingReportRowSow extends BreedingReportRow {

	protected SimpleStringProperty boarUsed;
	protected SimpleStringProperty dateWean;

	public BreedingReportRowSow(BreedingRow br) {
		super(br);
		FarrowingRow fr = FarrowingRecord.findRefNo(br.getRefNo());
		boarUsed = new SimpleStringProperty((null == br.getBoarUsed()) ? "" : br.getBoarUsed().stream()
				.map( n -> n.getBoarNo())
				.collect(Collectors.joining("/")));
		dateWean = new SimpleStringProperty(null == fr || null == fr.getWeanDate()? "":sdf.format(fr.getWeanDate()));
	}

	public String getBoarUsed() {
		return boarUsed.get();
	}
	
	public String getDateWean() {
		return dateWean.get();
	}
}
