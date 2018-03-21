package reports.breeding.row;

import javafx.beans.property.SimpleStringProperty;
import model.BreedingRow;
import model.FarrowingRow;
import record.FarrowingRecord;
import record.SowRecord;
import utils.RemovePoint;

public class BreedingReportRowBoar extends BreedingReportRow {

	protected SimpleStringProperty sowNo;
	protected SimpleStringProperty sb;
	protected SimpleStringProperty mm;
	
	public BreedingReportRowBoar(BreedingRow br) {
		super(br);
		sowNo = new SimpleStringProperty((null == br.getSowNo()) ? "" : br.getSowNo().getSowNo());
		FarrowingRow fr = FarrowingRecord.findRefNo(br.getRefNo());
		
		if(fr == null) {
			if(!SowRecord.isDiseased(br.getSowNo().getSowNo()) && 
					(br.getPregnancyRemarks().equals("") || br.getPregnancyRemarks().equals("+"))) {
				sb  = new SimpleStringProperty();
				mm  = new SimpleStringProperty();
			}
			else if(br.getPregnancyRemarks().equalsIgnoreCase("+AB") 
					|| br.getPregnancyRemarks().equalsIgnoreCase("-RB") 
					|| SowRecord.isDiseased(br.getSowNo().getSowNo())){
				sb  = new SimpleStringProperty("N/A");
				mm  = new SimpleStringProperty("N/A");
			}
			else {
				sb  = new SimpleStringProperty("ERROR");
				mm  = new SimpleStringProperty("ERROR");
			}
		}
		else {
			if(br.getPregnancyRemarks().equals("+")){
				sb  = new SimpleStringProperty(fr.getSb());
				mm  = new SimpleStringProperty(fr.getMm());
			}
			else {
				sb  = new SimpleStringProperty("ERROR");
				mm  = new SimpleStringProperty("ERROR");
			}
		}
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
