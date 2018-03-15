package reports.breeding.row;

import javafx.beans.property.SimpleStringProperty;
import model.BreedingRow;
import model.FarrowingRow;
import record.FarrowingRecord;
import utils.RemovePoint;

public class BreedingReportRowBoar extends BreedingReportRow {

	protected SimpleStringProperty sowNo;
	protected SimpleStringProperty sb;
	protected SimpleStringProperty mm;
	
	public BreedingReportRowBoar(BreedingRow br) {
		super(br);
		sowNo = new SimpleStringProperty((null == br.getSowNo()) ? "" : br.getSowNo().getSowNo());
		FarrowingRow fr = FarrowingRecord.findRefNo(br.getRefNo());
		sb = new SimpleStringProperty(null == fr || null == fr.getSb()? "":fr.getSb());
		mm = new SimpleStringProperty(null == fr || null == fr.getMm()? "":fr.getMm());
	}
	
	public String getSowNo() {
		return sowNo.get();
	}
	
	public String getSb() {
		return RemovePoint.remove(sb.get());
	}
	
	public String getMm() {
		return RemovePoint.remove(mm.get());
	}
}
