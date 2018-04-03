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
import javafx.scene.text.Text;
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
	
	private DecimalFormat dcf = new DecimalFormat("###0.00");

	public void createBreedingByDate(Date from, Date to) throws IOException {
		
		
		Stage stage = new Stage();
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/breeding/BreedingReportDate.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
		((Label) stage.getScene().lookup("#BreedReportStartEnd"))
				.setText("Breeding Period:  " + DateFormat.formatToString(from) + "  -  " + DateFormat.formatToString(to));
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
		BigDecimal unconfirmed = BigDecimal.valueOf(filterListDate.stream().filter(t -> "".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal total = BigDecimal.valueOf(filterListDate.size()).subtract(unconfirmed);

		String positivePercentage = total.intValue()!=0? dcf.format(positives.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String rebreedPercentage = total.intValue()!=0? dcf.format(rebreed.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String abortsPercentage = total.intValue()!=0? dcf.format(aborts.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		((Text)stage.getScene().lookup("#periodFrom1")).setText(DateFormat.formatToString(from));
		((Text)stage.getScene().lookup("#periodTo1")).setText(DateFormat.formatToString(to));
		((Text)stage.getScene().lookup("#totalBreeding")).setText(total.intValue()+"");
		((Text)stage.getScene().lookup("#totalPregnant")).setText(positives.intValue()+"");
		((Text)stage.getScene().lookup("#pregnantPercent")).setText(positivePercentage);
		((Text)stage.getScene().lookup("#totalRebreed")).setText(rebreed.intValue()+"");
		((Text)stage.getScene().lookup("#rebreedPercent")).setText(rebreedPercentage);
		((Text)stage.getScene().lookup("#totalAbortion")).setText(aborts.intValue()+"");
		((Text)stage.getScene().lookup("#abortionPercent")).setText(abortsPercentage);
		((Text)stage.getScene().lookup("#unconfirmed")).setText(unconfirmed.intValue()+"");
		
		BigDecimal farrowed = BigDecimal.valueOf(filterListDate.stream().filter(br -> null!=FarrowingRecord.findRefNo(br.getRefNo())).count());
		BigDecimal expecting = BigDecimal.valueOf(filterListDate.stream()
				.filter(br -> ( !SowRecord.isDiseased(br.getSowNo().getSowNo()) 
						&& null == FarrowingRecord.findRefNo(br.getRefNo())
						&& br.getPregnancyRemarks().equals("+"))).count());
	
		List<FarrowingRow> filteredListFarrowing = filterListDate.stream()
				.filter(br -> null != FarrowingRecord.findRefNo(br.getRefNo()))
				.map(br -> FarrowingRecord.findRefNo(br.getRefNo()))
				.collect(Collectors.toList());
		
		BigDecimal litterSize = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getTotalFar()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal liveBirth = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getLive()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal mm = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getMm()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal sb = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getSb()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		String farrowingRate = total.intValue()!=0? dcf.format(farrowed.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		((Text)stage.getScene().lookup("#farrowed")).setText(farrowed.intValue()+"");
		((Text)stage.getScene().lookup("#expecting")).setText(expecting.intValue()+"");
		((Text)stage.getScene().lookup("#litterSize")).setText(litterSize.intValue()+"");
		((Text)stage.getScene().lookup("#liveBirth")).setText(liveBirth.intValue()+"");
		((Text)stage.getScene().lookup("#mm")).setText(mm.intValue()+"");
		((Text)stage.getScene().lookup("#sb")).setText(sb.intValue()+"");
		((Text)stage.getScene().lookup("#farrowingRate")).setText(farrowingRate);
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
				.setText("Date of Birth: " + ((null == selectedSow.getBirthDate()) ? ""
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
		BigDecimal unconfirmed = BigDecimal.valueOf(filteredList.stream().filter(t -> "".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal total = BigDecimal.valueOf(filteredList.size()).subtract(unconfirmed);
		
		
		String positivePercentage = total.intValue()!=0? dcf.format(positives.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String rebreedPercentage = total.intValue()!=0? dcf.format(rebreed.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String abortsPercentage = total.intValue()!=0? dcf.format(aborts.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		
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
		
		BigDecimal mm = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getMm()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal sb = filteredListFarrowing.stream()
				.map(x -> new BigDecimal(x.getSb()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);

		BigDecimal totalFarrowings = BigDecimal.valueOf(filteredListFarrowing.stream().count());

		String farrowPercentage = total.intValue()!=0? dcf.format(totalFarrowings.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		String aveLitterSize = totalFarrowings.intValue()!=0? (litterSize.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"0.00";
		
		String aveLiveBirth = totalFarrowings.intValue()!=0? (liveBirth.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"0.00";
		
		String aveMm = totalFarrowings.intValue()!=0? (mm.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"0.00";
		
		String aveSb = totalFarrowings.intValue()!=0? (sb.divide(totalFarrowings,2,RoundingMode.HALF_UP))+"":"0.00";
		
		
		((Text)stage.getScene().lookup("#totalBreeding")).setText(total.intValue()+"");
		((Text)stage.getScene().lookup("#totalPregnant")).setText(positives.intValue()+"");
		((Text)stage.getScene().lookup("#pregnantPercent")).setText(positivePercentage);
		((Text)stage.getScene().lookup("#totalRebreed")).setText(rebreed.intValue()+"");
		((Text)stage.getScene().lookup("#rebreedPercent")).setText(rebreedPercentage);
		((Text)stage.getScene().lookup("#totalAbortion")).setText(aborts.intValue()+"");
		((Text)stage.getScene().lookup("#abortionPercent")).setText(abortsPercentage);
		((Text)stage.getScene().lookup("#unconfirmed")).setText(unconfirmed.intValue()+"");
		
		((Text)stage.getScene().lookup("#parity")).setText(selectedSow.getParity());
		((Text)stage.getScene().lookup("#aveLitterSize")).setText(aveLitterSize);
		((Text)stage.getScene().lookup("#aveLiveBirth")).setText(aveLiveBirth);
		((Text)stage.getScene().lookup("#aveMm")).setText(aveMm);
		((Text)stage.getScene().lookup("#aveSb")).setText(aveSb);
		
		((Text)stage.getScene().lookup("#litterSize")).setText(litterSize.intValue()+"");
		((Text)stage.getScene().lookup("#liveBirth")).setText(liveBirth.intValue()+"");
		((Text)stage.getScene().lookup("#mm")).setText(mm.intValue()+"");
		((Text)stage.getScene().lookup("#sb")).setText(sb.intValue()+"");

	}
	
	public void createBreedingByBoar(String boarNo,Date from, Date to) throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/breeding/BreedingReportBoar.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
		((Label) stage.getScene().lookup("#BreedReportStartEnd"))
				.setText("Breeding Period:  " + DateFormat.formatToString(from) + "  -  " + DateFormat.formatToString(to));
		((Label) stage.getScene().lookup("#BoarNo")).setText("Boar No: " + boarNo);
		TableView<BreedingReportRowBoar> table = (TableView<BreedingReportRowBoar>) stage.getScene()
				.lookup("#BreedReportTable");
		BreedingReportColumnBoar columns = new BreedingReportColumnBoar();
		columns.setupColumn();
		ObservableList<BreedingReportRowBoar> data = FXCollections.observableArrayList();		
		Boar selectedBoar = BoarRecord.getBoar(boarNo);
		List<BreedingRow> filterListDate = BreedingRecord.filterByBreedDate(from, to).stream()
				.filter(br -> br.getBoarUsed().contains(selectedBoar))
				.collect(Collectors.toList());
		for (BreedingRow br : filterListDate) {
			data.add(new BreedingReportRowBoar(br));
		}
		table.getColumns().addAll(columns.getBreedingReportColumns());
		table.setItems(data);
		
		BigDecimal positives = BigDecimal.valueOf(filterListDate.stream().filter(t -> "+".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal rebreed = BigDecimal.valueOf(filterListDate.stream().filter(t -> "-RB".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal aborts = BigDecimal.valueOf(filterListDate.stream().filter(t -> "+AB".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal unconfirmed = BigDecimal.valueOf(filterListDate.stream().filter(t -> "".equals(t.getPregnancyRemarks().trim())).count());
		BigDecimal total = BigDecimal.valueOf(filterListDate.size()).subtract(unconfirmed);

		String positivePercentage = total.intValue()!=0? dcf.format(positives.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String rebreedPercentage = total.intValue()!=0? dcf.format(rebreed.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		String abortsPercentage = total.intValue()!=0? dcf.format(aborts.divide(total,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		

		BigDecimal farrowed = BigDecimal.valueOf(filterListDate.stream().filter(br -> null!=FarrowingRecord.findRefNo(br.getRefNo())).count());
		BigDecimal expecting = BigDecimal.valueOf(filterListDate.stream()
				.filter(br -> ( !SowRecord.isDiseased(br.getSowNo().getSowNo()) 
						&& null == FarrowingRecord.findRefNo(br.getRefNo())
						&& br.getPregnancyRemarks().equals("+"))).count());
		
		List<FarrowingRow> filteredListFarrowing = filterListDate.stream()
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
		
		String aveLitterSize = totalFarrowings.intValue()!=0? (litterSize.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0.00";
		
		String aveLiveBirth = totalFarrowings.intValue()!=0? (liveBirth.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0.00";
		String aveMm = totalFarrowings.intValue()!=0? (mm.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0.00";
		
		String aveSb = totalFarrowings.intValue()!=0? (sb.divide(totalFarrowings,2,RoundingMode.HALF_UP)).toString():"0.00";
		
		
		
		((Text)stage.getScene().lookup("#periodFrom1")).setText(DateFormat.formatToString(from));
		((Text)stage.getScene().lookup("#periodTo1")).setText(DateFormat.formatToString(to));
		((Text)stage.getScene().lookup("#totalBreeding")).setText(total.intValue()+"");
		((Text)stage.getScene().lookup("#totalPregnant")).setText(positives.intValue()+"");
		((Text)stage.getScene().lookup("#pregnantPercent")).setText(positivePercentage);
		((Text)stage.getScene().lookup("#totalRebreed")).setText(rebreed.intValue()+"");
		((Text)stage.getScene().lookup("#rebreedPercent")).setText(rebreedPercentage);
		((Text)stage.getScene().lookup("#totalAbortion")).setText(aborts.intValue()+"");
		((Text)stage.getScene().lookup("#abortionPercent")).setText(abortsPercentage);
		((Text)stage.getScene().lookup("#unconfirmed")).setText(unconfirmed.intValue()+"");
		
		((Text)stage.getScene().lookup("#farrowed")).setText(farrowed.intValue()+"");
		((Text)stage.getScene().lookup("#expecting")).setText(expecting.intValue()+"");
		((Text)stage.getScene().lookup("#litterSize")).setText(litterSize.intValue()+"");
		((Text)stage.getScene().lookup("#liveBirth")).setText(liveBirth.intValue()+"");
		((Text)stage.getScene().lookup("#mm")).setText(mm.intValue()+"");
		((Text)stage.getScene().lookup("#sb")).setText(sb.intValue()+"");
		
		((Text)stage.getScene().lookup("#aveLitterSize")).setText(aveLitterSize);
		((Text)stage.getScene().lookup("#aveLiveBirth")).setText(aveLiveBirth);
		((Text)stage.getScene().lookup("#aveMm")).setText(aveMm);
		((Text)stage.getScene().lookup("#aveSb")).setText(aveSb);


	}
	
	
}
