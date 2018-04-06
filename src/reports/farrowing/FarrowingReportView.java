package reports.farrowing;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import model.FarrowingRow;
import model.Sow;
import record.BreedingRecord;
import record.FarrowingRecord;
import record.SowRecord;
import reports.farrowing.columns.FarrowingReportColumnDate;
import reports.farrowing.columns.FarrowingReportColumnSow;
import reports.farrowing.row.FarrowingReportRowDate;
import reports.farrowing.row.FarrowingReportRowSow;
import utils.DateFormat;

public class FarrowingReportView {

	private DecimalFormat dcf = new DecimalFormat("###0.00");
	
	public void createFarrowingBySow(String sowNumber) throws IOException {
		Stage stage = new Stage();
		Sow selectedSow = SowRecord.getSow(sowNumber);
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/farrowing/FarrowingReportSow.fxml"));
		stage.setScene(new Scene(root));
		stage.setMaximized(true);
		stage.show();
		((Label) stage.getScene().lookup("#FarrowReportSow")).setText("SOW NO: "+selectedSow.getSowNo());
		((Label) stage.getScene().lookup("#FarrowReportBreed")).setText("Breed:    "+selectedSow.getBreed());
		((Label) stage.getScene().lookup("#FarrowReportBirthdate")).setText("Date of Birth:    " + ((null == selectedSow.getBirthDate()) ? ""
				: SimpleDateFormat.getDateInstance().format(selectedSow.getBirthDate())));																						// selectedSow.getBirthDate())?"":SimpleDateFormat.getDateInstance().format(selectedSow.getBirthDate())));
		((Label) stage.getScene().lookup("#FarrowReportOrigin")).setText("Origin:    " + ((null == selectedSow.getOrigin()) ? ""
				: selectedSow.getOrigin()));
		TableView<FarrowingReportRowSow> table = (TableView<FarrowingReportRowSow>) stage.getScene()
				.lookup("#FarrowReportTable");
		FarrowingReportColumnSow columns = new FarrowingReportColumnSow();
		columns.setupColumn();
		ObservableList<FarrowingReportRowSow> data = FXCollections.observableArrayList();
		List<FarrowingRow> filterList = FarrowingRecord.filterBySow(selectedSow);
		for (FarrowingRow fr : filterList) {
			data.add(new FarrowingReportRowSow(fr));
		}
		table.getColumns().addAll(columns.getBreedingReportColumns());
		table.setItems(data);
		
		BigDecimal litterSize = filterList.stream()
				.map(x -> new BigDecimal(x.getTotalFar()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal liveBirth = filterList.stream()
				.map(x -> new BigDecimal(x.getLive()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal sb = filterList.stream()
				.map(x -> (new BigDecimal(x.getSb())))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal mm = filterList.stream()
				.map(x -> (new BigDecimal(x.getMm())))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal totalFarrowings = BigDecimal.valueOf(filterList.stream().count());
		
		String aveLitterSize = totalFarrowings.intValue()!=0? (litterSize.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0";
		
		String aveLiveBirth = totalFarrowings.intValue()!=0? (liveBirth.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0";
		
		String aveMm = totalFarrowings.intValue()!=0? (mm.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0";
		
		String aveSb = totalFarrowings.intValue()!=0? (sb.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0";
		
		String alivePercentage = litterSize.intValue() != 0? dcf.format(liveBirth.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String mmPercentage = litterSize.intValue() != 0? dcf.format(mm.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String sbPercentage = litterSize.intValue() != 0? dcf.format(sb.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		((Text)stage.getScene().lookup("#parity1")).setText(selectedSow.getParity());
		((Text)stage.getScene().lookup("#aveLitterSize")).setText(aveLitterSize);
		((Text)stage.getScene().lookup("#aveLiveBirth")).setText(aveLiveBirth);
		((Text)stage.getScene().lookup("#aveMm")).setText(aveMm);
		((Text)stage.getScene().lookup("#aveSb")).setText(aveSb);
		
		((Text)stage.getScene().lookup("#parity2")).setText(selectedSow.getParity());
		((Text)stage.getScene().lookup("#totalLitter")).setText(litterSize.intValue()+"");
		((Text)stage.getScene().lookup("#totalLive")).setText(liveBirth.intValue()+"");
		((Text)stage.getScene().lookup("#livePercent")).setText(alivePercentage);
		((Text)stage.getScene().lookup("#totalMm")).setText(mm.intValue()+"");
		((Text)stage.getScene().lookup("#mmPercent")).setText(mmPercentage);
		((Text)stage.getScene().lookup("#totalSb")).setText(sb.intValue()+"");
		((Text)stage.getScene().lookup("#sbPercent")).setText(sbPercentage);
		
	}
	
	public void createFarrowingByDate(Date from, Date to) throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/farrowing/FarrowingReportDate.fxml"));
		stage.setScene(new Scene(root));
		stage.setMaximized(true);
		stage.show();
		((Label) stage.getScene().lookup("#FarrowReportStartEnd"))
				.setText("Farrowing Period:   " + DateFormat.formatToString(from) + "   -   " + DateFormat.formatToString(to));
		TableView<FarrowingReportRowDate> table = (TableView<FarrowingReportRowDate>) stage.getScene()
				.lookup("#FarrowReportTable");
		FarrowingReportColumnDate columns = new FarrowingReportColumnDate();
		columns.setupColumn();
		List<FarrowingRow> filterListDate = FarrowingRecord.filterByFarrowDate(from, to);
		ObservableList<FarrowingReportRowDate> data = FXCollections.observableArrayList();
		for (FarrowingRow br : filterListDate) {
			data.add(new FarrowingReportRowDate(br));
		}
		table.getColumns().addAll(columns.getFarrowingReportColumns());
		table.setItems(data);
		
		BigDecimal diffTotalAndLive = filterListDate.stream()
				.map(x -> ((new BigDecimal(x.getTotalFar()).subtract(new BigDecimal(x.getLive())))))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal sbAndMm = filterListDate.stream()
				.map(x -> ((new BigDecimal(x.getSb())).add((new BigDecimal(x.getMm())))))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal sb = filterListDate.stream()
				.map(x -> (new BigDecimal(x.getSb())))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal mm = filterListDate.stream()
				.map(x -> (new BigDecimal(x.getMm())))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal weaningMortalities = filterListDate.stream()
				.map(x -> new BigDecimal(x.getMortality()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal litterSize = filterListDate.stream()
				.map(x -> new BigDecimal(x.getTotalFar()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal liveBirth = filterListDate.stream()
				.map(x -> new BigDecimal(x.getLive()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal totalWeightLiveBirth = filterListDate.stream()
				.map(x -> (new BigDecimal(x.getLive())).multiply((new BigDecimal(x.getAbw()))))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal totalMaleFemale = filterListDate.stream()
				.map(x -> ((new BigDecimal(x.getMale())).add((new BigDecimal(x.getFemale())))))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal weaned = filterListDate.stream()
				.map(x -> new BigDecimal(x.getTotalWean()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal totalWeanWeight = filterListDate.stream()
				.map(x -> (new BigDecimal(x.getTotalWean())).multiply((new BigDecimal(x.getAww()))))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal totalWeaned = filterListDate.stream()
				.map(x -> (new BigDecimal(x.getTotalWean())))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal totelWeanAge = filterListDate.stream()
				.map(x -> (new BigDecimal(x.getTotalWean())).multiply((new BigDecimal(x.getAgeWean()))))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal totalFarrowings = BigDecimal.valueOf(filterListDate.stream().count());
		
		String aveLitterSize = totalFarrowings.intValue()!=0? (litterSize.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0";
		
		String aveLiveBirth = totalFarrowings.intValue()!=0? (liveBirth.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0";
		
		String aveMm = totalFarrowings.intValue()!=0? (mm.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0";
		
		String aveSb = totalFarrowings.intValue()!=0? (sb.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0";
		
		String alivePercentage = litterSize.intValue() != 0? dcf.format(liveBirth.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String mmPercentage = litterSize.intValue() != 0? dcf.format(mm.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String sbPercentage = litterSize.intValue() != 0? dcf.format(sb.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		


		((Text)stage.getScene().lookup("#periodFrom1")).setText(DateFormat.formatToString(from));
		((Text)stage.getScene().lookup("#periodTo1")).setText(DateFormat.formatToString(to));
		((Text)stage.getScene().lookup("#totalFarrow")).setText(totalFarrowings.intValue()+"");
		((Text)stage.getScene().lookup("#totalLitter")).setText(litterSize.intValue()+"");
		((Text)stage.getScene().lookup("#totalLive")).setText(liveBirth.intValue()+"");
		((Text)stage.getScene().lookup("#livePercent")).setText(alivePercentage);
		((Text)stage.getScene().lookup("#totalMm")).setText(mm.intValue()+"");
		((Text)stage.getScene().lookup("#mmPercent")).setText(mmPercentage);
		((Text)stage.getScene().lookup("#totalSb")).setText(sb.intValue()+"");
		((Text)stage.getScene().lookup("#sbPercent")).setText(sbPercentage);

		((Text)stage.getScene().lookup("#periodFrom2")).setText(DateFormat.formatToString(from));
		((Text)stage.getScene().lookup("#periodTo2")).setText(DateFormat.formatToString(to));
		((Text)stage.getScene().lookup("#aveLitterSize")).setText(aveLitterSize);
		((Text)stage.getScene().lookup("#aveLiveBirth")).setText(aveLiveBirth);
		((Text)stage.getScene().lookup("#aveMm")).setText(aveMm);
		((Text)stage.getScene().lookup("#aveSb")).setText(aveSb);

		
	}
}
