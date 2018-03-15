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

	private DecimalFormat dcf = new DecimalFormat("####.####");
	
	public void createFarrowingBySow(String sowNumber) throws IOException {
		Stage stage = new Stage();
		Sow selectedSow = SowRecord.getSow(sowNumber);
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/farrowing/FarrowingReportSow.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
		((Label) stage.getScene().lookup("#FarrowReportSow")).setText("Sow No: "+selectedSow.getSowNo());
		((Label) stage.getScene().lookup("#FarrowReportBreed")).setText("Breed: "+selectedSow.getBreed());
		((Label) stage.getScene().lookup("#FarrowReportBirthdate")).setText("Birthdate: " + ((null == selectedSow.getBirthDate()) ? ""
				: SimpleDateFormat.getDateInstance().format(selectedSow.getBirthDate())));																						// selectedSow.getBirthDate())?"":SimpleDateFormat.getDateInstance().format(selectedSow.getBirthDate())));
		((Label) stage.getScene().lookup("#FarrowReportOrigin")).setText("Origin: " + ((null == selectedSow.getOrigin()) ? ""
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
		
		BigDecimal breedings = BigDecimal.valueOf(BreedingRecord.filterBySow(selectedSow).size());
		BigDecimal totalFarrowings = BigDecimal.valueOf(filterList.stream().count());
		
		String aveLitterSize = totalFarrowings.intValue()!=0? (litterSize.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveLiveBirth = totalFarrowings.intValue()!=0? (liveBirth.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveMm = totalFarrowings.intValue()!=0? (mm.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveSb = totalFarrowings.intValue()!=0? (sb.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String farrowPercentage = breedings.intValue() != 0? dcf.format(totalFarrowings.divide(breedings,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String alivePercentage = litterSize.intValue() != 0? dcf.format(liveBirth.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String mmPercentage = litterSize.intValue() != 0? dcf.format(mm.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String sbPercentage = litterSize.intValue() != 0? dcf.format(sb.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		((TextArea) stage.getScene().lookup("#FarrPerformanceText")).setText(
				"Parity:                                                               "+ selectedSow.getParity() + System.lineSeparator()+
				"Ave. Litter Size Per Farrow:                              "+aveLitterSize+System.lineSeparator()+
				"Ave. Live Birth Per Farrow  /  Rate:                   "+aveLiveBirth+ " /  "+alivePercentage+System.lineSeparator()+
				"Ave. Mummified Per Farrow  /  Rate:               "+aveMm+ " /  "+mmPercentage+System.lineSeparator()+
				"Ave. Still Birth Birth Per Farrow  /  Rate:          "+aveSb+ " /  "+sbPercentage+System.lineSeparator());
		
		((TextArea) stage.getScene().lookup("#BreedPerformanceText")).setText(
				"Total Breeding:                             "+breedings.intValue()+System.lineSeparator()+
				"Total Farrowing  /  Rate:               "+ totalFarrowings.intValue()+"  /  "+farrowPercentage);
		
	}
	
	public void createFarrowingByDate(Date from, Date to) throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/farrowing/FarrowingReportDate.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
		((Label) stage.getScene().lookup("#FarrowReportStartEnd"))
				.setText("Farrowing Period: From " + DateFormat.formatToString(from) + " to " + DateFormat.formatToString(to));
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
		
		String diffTotalAndLivePercentage = litterSize.intValue() != 0? (diffTotalAndLive.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		String sbAndMmPercentage = litterSize.intValue() != 0? (sbAndMm.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		String weaningMortalitiesPercentage = litterSize.intValue() != 0? (weaningMortalities.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		String aveLitterSize = totalFarrowings.intValue()!=0? (litterSize.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveLiveBirth = totalFarrowings.intValue()!=0? (liveBirth.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveMm = totalFarrowings.intValue()!=0? (mm.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveSb = totalFarrowings.intValue()!=0? (sb.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveMaleFemale = totalFarrowings.intValue()!=0? (totalMaleFemale.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveBirthWeight = liveBirth.intValue()!=0? (totalWeightLiveBirth.divide(liveBirth,2,RoundingMode.HALF_UP))+"kg":"";
		
		String aveWeaned = totalFarrowings.intValue()!=0? (weaned.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveWeanWeight = weaned.intValue()!=0? (totalWeanWeight.divide(weaned,2,RoundingMode.HALF_UP))+"kg":"";
		
		String aveWeanAge = weaned.intValue()!=0? (totelWeanAge.divide(weaned,2,RoundingMode.HALF_UP))+" days":"";
		
//		((TextArea) stage.getScene().lookup("#FarrPerformanceText1")).setText(
//				"Still Birth and Mummified: "+sbAndMm.intValue() + System.lineSeparator()+
//				"Still Birth and Mummified Percentage: "+sbAndMmPercentage + System.lineSeparator()+
//				"Total Difference of Total Farrows and Live Births: "+diffTotalAndLive.intValue()+ System.lineSeparator()+
//				"Total Difference of Total Farrows and Live Births Percentage: "+diffTotalAndLivePercentage+ System.lineSeparator()+
//				"Average Litter Size: "+aveLitterSize +System.lineSeparator() +
//				"Average Live Birth: "+aveLiveBirth + System.lineSeparator()+
//				"Average Total of Male and Female: "+aveMaleFemale + System.lineSeparator()+
//				"Average Birth Weight: "+aveBirthWeight+System.lineSeparator()
//				);
		
//		((TextArea) stage.getScene().lookup("#FarrPerformanceText2")).setText(
//		"Weaning Mortalities: "+weaningMortalities.intValue() + System.lineSeparator()+
//		"Weaning Mortality Percentage: "+weaningMortalitiesPercentage + System.lineSeparator()+
//		"Average Weaned: " +aveWeaned + System.lineSeparator()+
//		"Average Wean Weight: "+aveWeanWeight+System.lineSeparator()+
//		"Average Wean Age: "+aveWeanAge+System.lineSeparator());
		
		String alivePercentage = litterSize.intValue() != 0? dcf.format(liveBirth.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String mmPercentage = litterSize.intValue() != 0? dcf.format(mm.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String sbPercentage = litterSize.intValue() != 0? dcf.format(sb.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		((TextArea) stage.getScene().lookup("#FarrPerformanceText1")).setText(
				"For Period: " + DateFormat.formatToString(from) + "  -  " + DateFormat.formatToString(to)+System.lineSeparator() +
				"Total Farrow:                               " + totalFarrowings.intValue() + System.lineSeparator() +
				"Total Piglets Farrowed:               "+ litterSize.intValue() + System.lineSeparator() +
				"No. of Alive  /  Rate:                    "+liveBirth.intValue() +"  /  "+alivePercentage+ System.lineSeparator() +
				"No. of Mummified  /  Rate:         "+mm.intValue() +"  /  "+mmPercentage+ System.lineSeparator() +
				"No. of Still Birth  /  Rate:             "+sb.intValue() +"  /  "+sbPercentage+ System.lineSeparator() +
				"Total Weaned Piglets:                 "+totalWeaned.intValue());
		
		((TextArea) stage.getScene().lookup("#FarrPerformanceText2")).setText(
				"For Period: " + DateFormat.formatToString(from) + "  -  " + DateFormat.formatToString(to)+System.lineSeparator() +
				"Ave. Litter Size :               "+ aveLitterSize + System.lineSeparator() +
				"Ave. Live Birth:                 "+ aveLiveBirth + System.lineSeparator()+
				"Ave. Mummified :            "+ aveMm + System.lineSeparator() +
				"Ave. Still Birth:                 "+ aveSb + System.lineSeparator());
	}
}
