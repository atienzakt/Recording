package reports.farrowing.row;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.Days;

import javafx.beans.property.SimpleStringProperty;
import model.FarrowingRow;
import record.SowRecord;
import utils.RemovePoint;

public class FarrowingReportRow {

	protected SimpleStringProperty refNo;
	protected SimpleStringProperty dateFarrow;
	protected SimpleStringProperty dateBreed;
	protected SimpleStringProperty liveBirth;
	protected SimpleStringProperty sb;
	protected SimpleStringProperty mm;
	protected SimpleStringProperty abw;
	//protected SimpleStringProperty weanCount;
	protected SimpleStringProperty weanDate;
	protected SimpleStringProperty aww;
	protected SimpleStringProperty comment;
	protected SimpleStringProperty totalBirth;
	protected SimpleStringProperty peroid;
	protected SimpleStringProperty boarUsed;

	protected final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	public FarrowingReportRow(FarrowingRow fr) {
		refNo = new SimpleStringProperty(fr.getRefNo());
		dateFarrow = new SimpleStringProperty((null == fr.getFarDate()) ? "" : sdf.format(fr.getFarDate()));
		liveBirth = new SimpleStringProperty(fr.getLive());
		totalBirth = new SimpleStringProperty(fr.getTotalFar());
		sb = new SimpleStringProperty(fr.getSb());
		mm = new SimpleStringProperty(fr.getMm());
		abw = new SimpleStringProperty(fr.getAbw());
		//weanCount = new SimpleStringProperty(fr.getTotalWean());
		if(fr.getSowNo().getSowNo().equals("08550")) {
			System.out.println(fr.getSowNo().getStatus());
		}
		if(fr.getWeanDate() == null) {
			if(SowRecord.isDiseased(fr.getSowNo().getSowNo()) || !fr.getSowNo().getStatus().equalsIgnoreCase("lactating")){
				weanDate = new SimpleStringProperty("N/A");
			}
			else {
				weanDate = new SimpleStringProperty("");
			}
		}
		else {
			weanDate = new SimpleStringProperty(sdf.format(fr.getWeanDate()));
		}
		
		aww = new SimpleStringProperty(fr.getAww());
		comment = new SimpleStringProperty(fr.getComments());
		dateBreed = new SimpleStringProperty(
				(null == fr.getBreedingRow().getDateBreed()) ? "" : sdf.format(fr.getBreedingRow().getDateBreed()));
		peroid = new SimpleStringProperty(((null == fr.getBreedingRow().getDateBreed()) || (null == fr.getFarDate()))
				? ""
				: Days.daysBetween(new DateTime(fr.getBreedingRow().getDateBreed()), new DateTime(fr.getFarDate()))
						.getDays() + " days");
		boarUsed = new SimpleStringProperty(fr.getBoarUsed().stream()
				.map(br -> br.getBoarNo())
				.collect(Collectors.joining("/")));
	}

	public String getDateFarrow() {
		return dateFarrow.get();
	}

	public String getLiveBirth() {
		return RemovePoint.remove(liveBirth.get());
	}

	public String getSb() {
		return RemovePoint.remove(sb.get());
	}

	public String getMm() {
		return RemovePoint.remove(mm.get());
	}

	public String getWeanDate() {
		return weanDate.get();
	}

	public String getAww() {
		return aww.get();
	}

	public String getComment() {
		return comment.get();
	}

	public String getAbw() {
		return abw.get();
	}

	public String getTotalBirth() {
		return RemovePoint.remove(totalBirth.get());
	}

	public String getRefNo() {
		return refNo.get();
	}

	public String getDateBreed() {
		return dateBreed.get();
	}

	public String getPeroid() {
		return peroid.get();
	}

	public String getBoarUsed() {
		return boarUsed.get();
	}
	
}
