package reports.breeding;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Boar;
import model.BreedingRow;
import model.FarrowingRow;
import model.Sow;
import record.BoarRecord;
import record.BreedingRecord;
import record.FarrowingRecord;
import record.SowRecord;
import reports.breeding.columns.BreedingReportColumnBoar;
import reports.breeding.columns.BreedingReportColumnDate;
import reports.breeding.columns.BreedingReportColumnSow;
import reports.breeding.row.BreedingReportRowBoar;
import reports.breeding.row.BreedingReportRowDate;
import reports.breeding.row.BreedingReportRowSow;
import utils.DateFormat;

public class BreedingReportView {
	
	private DecimalFormat dcf = new DecimalFormat("####.####");

	public void createBreedingByDate(Date from, Date to) throws IOException {
		
		
		Stage stage = new Stage();
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/breeding/BreedingReportDate.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
		((Label) stage.getScene().lookup("#BreedReportStartEnd"))
				.setText("Breeding Period: From " + DateFormat.formatToString(from) + " to " + DateFormat.formatToString(to));
		TableView<BreedingReportRowDate> table = (TableView<BreedingReportRowDate>) stage.getScene()
				.lookup("#BreedReportTable");
		BreedingReportColumnDate columns = new BreedingReportColumnDate();
		columns.setupColumn();
		ObservableList<BreedingReportRowDate> data = FXCollections.observableArrayList();
		
		List<BreedingRow> filterListDate = BreedingRecord.filterByBreedDate(from, to);
		for (BreedingRow br : filterListDate) {
			data.add(new BreedingReportRowDate(br));
		}
		table.getColumns().addAll(columns.getBreedingReportColumns());
		table.setItems(data);
		
		BigDecimal positives = BigDecimal.valueOf(filterListDate.stream().filter(t -> "+".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal rebreed = BigDecimal.valueOf(filterListDate.stream().filter(t -> "-RB".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal aborts = BigDecimal.valueOf(filterListDate.stream().filter(t -> "+AB".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal pending = BigDecimal.valueOf(filterListDate.stream().filter(t -> "".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal total = BigDecimal.valueOf(filterListDate.size());

		String positivePercentage = total.intValue()!=0? dcf.format(positives.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String rebreedPercentage = total.intValue()!=0? dcf.format(rebreed.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String abortPercentage = total.intValue()!=0? dcf.format(aborts.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String pendingPercentage = total.intValue()!=0? dcf.format(pending.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		((TextArea) stage.getScene().lookup("#PerformanceText")).setText(
				"For Period:  " + DateFormat.formatToString(from) + "  -  " + DateFormat.formatToString(to)+System.lineSeparator() +
				"Total Breeding:                         " + total + System.lineSeparator() +
				"No. of Pregnant  /  Rate:          "+ positives +"  /  " +  positivePercentage+ System.lineSeparator() +
				"No. of Rebreed  /  Rate:           "+ rebreed +"  /  " +  rebreedPercentage+System.lineSeparator() +
				"No. of Abort  /  Rate:               "+ aborts +"  /  " +  abortPercentage+System.lineSeparator()+
				"No. of Pending  /  Rate:           "+ pending +"  /  " +  pendingPercentage+System.lineSeparator());
		
	}
	
	public void createBreedingBySow(String sowNumber) throws IOException {

		Stage stage = new Stage();

		Sow selectedSow = SowRecord.getSow(sowNumber);
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/breeding/BreedingReportSow.fxml"));
		stage.setScene(new Scene(root));
		stage.show(); // this is important for multiple windows

		((Label) stage.getScene().lookup("#BreedReportSow")).setText("Sow No: " + sowNumber);
		((Label) stage.getScene().lookup("#BreedReportBreed")).setText("Breed: " + selectedSow.getBreed());
		((Label) stage.getScene().lookup("#BreedReportBirthdate"))
				.setText("Birthdate: " + ((null == selectedSow.getBirthDate()) ? ""
						: SimpleDateFormat.getDateInstance().format(selectedSow.getBirthDate())));
		((Label) stage.getScene().lookup("#BreedReportOrigin")).setText("Origin: " + ((null == selectedSow.getOrigin()) ? ""
				: selectedSow.getOrigin()));
		TableView<BreedingReportRowSow> table = (TableView<BreedingReportRowSow>) stage.getScene()
				.lookup("#BreedReportTable");
		BreedingReportColumnSow columns = new BreedingReportColumnSow();
		columns.setupColumn();
		ObservableList<BreedingReportRowSow> data = FXCollections.observableArrayList();
		List<BreedingRow> filteredList = BreedingRecord.filterBySow(selectedSow);
		for (BreedingRow br : filteredList) {
			data.add(new BreedingReportRowSow(br));
		}
		table.getColumns().addAll(columns.getBreedingReportColumns());
		table.setItems(data);
		BigDecimal positives = BigDecimal.valueOf(filteredList.stream().filter(t -> "+".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal rebreed = BigDecimal.valueOf(filteredList.stream().filter(t -> "-RB".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal aborts = BigDecimal.valueOf(filteredList.stream().filter(t -> "+AB".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal total = BigDecimal.valueOf(filteredList.size());
		
		
		String positivePercentage = total.intValue()!=0? dcf.format(positives.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String rebreedPercentage = total.intValue()!=0? dcf.format(rebreed.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String abortPercentage = total.intValue()!=0? dcf.format(aborts.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		((TextArea) stage.getScene().lookup("#BreedingPerfText")).setText(
				"Total Breeding:                         " + total + System.lineSeparator() +
				"No. of Pregnant  /  Rate:          "+ positives +"  /  " +  positivePercentage+ System.lineSeparator() +
				"No. of Rebreed  /  Rate:           "+ rebreed +"  /  " +  rebreedPercentage+System.lineSeparator() +
				"No. of Abort  /  Rate:               "+ aborts +"  /  " +  abortPercentage+System.lineSeparator());
		
		List<FarrowingRow> filteredListFarrowing = filteredList.stream()
				.filter(br -> null != FarrowingRecord.findRefNo(br.getRefNo()))
				.map(br -> FarrowingRecord.findRefNo(br.getRefNo()))
				.collect(Collectors.toList());
		
		BigDecimal litterSize = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getTotalFar()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal liveBirth = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getLive()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);

		BigDecimal totalFarrowings = BigDecimal.valueOf(filteredListFarrowing.stream().count());

		String farrowPercentage = total.intValue()!=0? dcf.format(totalFarrowings.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		String aveLitterSize = totalFarrowings.intValue()!=0? (litterSize.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveLiveBirth = totalFarrowings.intValue()!=0? (liveBirth.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		((TextArea) stage.getScene().lookup("#FarrowingPerfText")).setText(
				"Total Farrow   /   Rate:        " + totalFarrowings.intValue() + "  /  " + farrowPercentage+ System.lineSeparator() +
				"Ave. Litter Size:                   "+aveLitterSize + System.lineSeparator() +
				"Ave. Live Birth:                    "+ aveLiveBirth + System.lineSeparator());

	}
	
	public void createBreedingByBoar(String boarNo,Date from, Date to) throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/breeding/BreedingReportBoar.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
		((Label) stage.getScene().lookup("#BreedReportStartEnd"))
				.setText("Breeding Period: From " + DateFormat.formatToString(from) + " to " + DateFormat.formatToString(to));
		((Label) stage.getScene().lookup("#BoarNo")).setText("Boar No: " + boarNo);
		TableView<BreedingReportRowBoar> table = (TableView<BreedingReportRowBoar>) stage.getScene()
				.lookup("#BreedReportTable");
		BreedingReportColumnBoar columns = new BreedingReportColumnBoar();
		columns.setupColumn();
		ObservableList<BreedingReportRowBoar> data = FXCollections.observableArrayList();		
		Boar selectedBoar = BoarRecord.getBoar(boarNo);
		List<BreedingRow> filteredList = BreedingRecord.filterByBreedDate(from, to).stream()
				.filter(br -> br.getBoarUsed().contains(selectedBoar))
				.collect(Collectors.toList());
		for (BreedingRow br : filteredList) {
			data.add(new BreedingReportRowBoar(br));
		}
		table.getColumns().addAll(columns.getBreedingReportColumns());
		table.setItems(data);
		
		BigDecimal positives = BigDecimal.valueOf(filteredList.stream().filter(t -> "+".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal rebreed = BigDecimal.valueOf(filteredList.stream().filter(t -> "-RB".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal aborts = BigDecimal.valueOf(filteredList.stream().filter(t -> "+AB".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal pending = BigDecimal.valueOf(filteredList.stream().filter(t -> "".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal total = BigDecimal.valueOf(filteredList.size());

		String positivePercentage = total.intValue()!=0? dcf.format(positives.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String rebreedPercentage = total.intValue()!=0? dcf.format(rebreed.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String abortPercentage = total.intValue()!=0? dcf.format(aborts.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		String pendingPercentage = total.intValue()!=0? dcf.format(pending.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		((TextArea) stage.getScene().lookup("#BreedingPerfText")).setText(
				"For Period: " + DateFormat.formatToString(from) + "  -  " + DateFormat.formatToString(to)+System.lineSeparator() +
				"Total Breeding:                         " + total + System.lineSeparator() +
				"No. of Pregnant  /  Rate:          "+ positives +"  /  " +  positivePercentage+ System.lineSeparator() +
				"No. of Rebreed  /  Rate:           "+ rebreed +"  /  " +  rebreedPercentage+System.lineSeparator() +
				"No. of Abort  /  Rate:                "+ aborts +"  /  " +  abortPercentage+System.lineSeparator()+
				"No. of Pending  /  Rate:            "+ pending +"  /  " +  pendingPercentage+System.lineSeparator());
		List<FarrowingRow> filteredListFarrowing = filteredList.stream()
				.filter(br -> null != FarrowingRecord.findRefNo(br.getRefNo()))
				.map(br -> FarrowingRecord.findRefNo(br.getRefNo()))
				.collect(Collectors.toList());
		
		BigDecimal sb = filteredListFarrowing.stream()
				.map(x -> (new BigDecimal(x.getSb())))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal mm = filteredListFarrowing.stream()
				.map(x -> (new BigDecimal(x.getMm())))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal litterSize = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getTotalFar()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal liveBirth = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getLive()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal totalFarrowings = BigDecimal.valueOf(filteredListFarrowing.stream().count());

		String aveLitterSize = totalFarrowings.intValue()!=0? (litterSize.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String aveLiveBirth = totalFarrowings.intValue()!=0? (liveBirth.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"";
		
		String alivePercentage = litterSize.intValue() != 0? dcf.format(liveBirth.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		String mmPercentage = litterSize.intValue() != 0? dcf.format(mm.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		String sbPercentage = litterSize.intValue() != 0? dcf.format(sb.divide(litterSize,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"";
		
		((TextArea) stage.getScene().lookup("#FarrowingPerfText")).setText(
				"For Period: " + DateFormat.formatToString(from) + "  -  " + DateFormat.formatToString(to)+System.lineSeparator() +
				"Total Farrow:                                " + totalFarrowings.intValue() + System.lineSeparator() +
				"Total Piglets Farrowed:                "+ litterSize.intValue() + System.lineSeparator() +
				"No. of Alive  /  Rate:                    "+liveBirth.intValue() +"  /  " +  alivePercentage+System.lineSeparator() +
				"No. of Mummified  /  Rate:          "+mm.intValue() +"  /  " +  mmPercentage+System.lineSeparator() +
				"No. of Still Birth  /  Rate:              "+sb.intValue() +"  /  " +  sbPercentage+System.lineSeparator() +
				"Ave. Litter Size:                             "+aveLitterSize + System.lineSeparator() +
				"Ave. Live Birth:                              "+ aveLiveBirth + System.lineSeparator());
	}
	
	
}
