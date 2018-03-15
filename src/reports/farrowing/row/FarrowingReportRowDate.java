package reports.farrowing.row;

import javafx.beans.property.SimpleStringProperty;
import model.FarrowingRow;

public class FarrowingReportRowDate extends FarrowingReportRow {

	protected SimpleStringProperty sowNo;

	public FarrowingReportRowDate(FarrowingRow fr) {
		super(fr);
		sowNo = new SimpleStringProperty((null == fr.getBreedingRow().getDateBreed()) ? "" : fr.getSowNo().getSowNo());
	}

	public String getSowNo() {
		return sowNo.get();
	}
}
