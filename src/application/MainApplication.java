package application;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import extras.BreedingFarrowingConsistencyChecker;
import extras.ParityChecker;
import extras.PregnancyRemarksChecker;
import extras.StatusChecker;
import extras.WeanChecker;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import record.BoarRecord;
import record.BreedingRecord;
import record.FarrowingRecord;
import record.SowRecord;
import reports.breeding.BreedingReportView;
import reports.farrowing.FarrowingReportView;
import reports.performance.BreedingPerformanceView;
import reports.status.StatusReportView;
import utils.BreedingRecordParserCSV;
import utils.CalendarMonthPair;
import utils.DateFormat;
import utils.DatePickerFormatter;
import utils.FarrowingRecordParserCSV;
import utils.FromToDatePair;

/*TODO 
 * 1) implement separate event handler 
 * 2) New Stage/Window for every find By method refer to findBreedingRecordBySowView for example
*/

public class MainApplication extends Application implements EventHandler<ActionEvent> {

	protected StringConverter<LocalDate> sc;
	protected BreedingReportView breedingReportView = new BreedingReportView();
	protected FarrowingReportView farrowingReportView = new FarrowingReportView();
	protected BreedingPerformanceView breedingPerformanceView = new BreedingPerformanceView();
	protected StatusReportView statusReportView = new StatusReportView();
	protected Button findBreedingBySow;
	protected Button findBreedingByBoar;
	protected Button findBreedingByDate;
	protected Button findFarrowingBySow;
	protected Button findFarrowingByDate;
	protected Button performanceButton;
	protected Button monthlyBreedingReportButton;
	protected Button monthlyFarrowingReportButton;
	protected Button statusReportButton;
	

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setScene(new Scene(new Label("Loading..."),500,500));
		primaryStage.show();
		try {
			BreedingRecordParserCSV.setup();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Error In Breeding File");
			a.setHeaderText("Breeding Excel May Be Open, Missing or Corrupted");
			StringBuilder sb = new StringBuilder();
			for(StackTraceElement ste: e.getStackTrace()) {
				sb.append(ste.toString());
				sb.append(System.lineSeparator());
			}
			a.setContentText(sb.toString());
			Optional<ButtonType> exit = a.showAndWait();
			
			if(exit.isPresent() || !exit.isPresent()) {
				System.exit(0);
			}
		}
		try {
			FarrowingRecordParserCSV.setup();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Error In Farrowing File");
			a.setHeaderText("Farrowing Excel May Be Open, Missing or Corrupted");
			StringBuilder sb = new StringBuilder();
			for(StackTraceElement ste: e.getStackTrace()) {
				sb.append(ste.toString());
				sb.append(System.lineSeparator());
			}
			a.setContentText(sb.toString());
			Optional<ButtonType> exit = a.showAndWait();
			if(exit.isPresent() || !exit.isPresent()) {
				System.exit(0);
			}
		}
		SowRecord.tagDeadSows();
		SowRecord.setParities();
		PregnancyRemarksChecker.pregnancyCheck();
		ParityChecker.parityCheck();
		StatusChecker.setAndCheckStatus();
		BreedingFarrowingConsistencyChecker.check();
		WeanChecker.check();

		initUI(primaryStage);
		

	}

	private void initUI(Stage stage) {
		VBox root = new VBox();
		root.setPadding(new Insets(10));
		root.setAlignment(Pos.TOP_LEFT);
		root.setSpacing(25);
		findBreedingBySow = new Button("SOW BREEDING RECORD");
		findBreedingByDate = new Button("SOW BREEDING RECORD BY PERIOD");
		findBreedingByBoar = new Button("BOAR BREEDING RECORD");
		findFarrowingBySow = new Button("SOW FARROWING RECORD");
		findFarrowingByDate = new Button("SOW FARROWING RECORD BY PERIOD");
		performanceButton = new Button("BREEDING PERFORMANCE");
		monthlyBreedingReportButton = new Button("MONTHLY SOW BREEDING RECORD");
		monthlyFarrowingReportButton = new Button("MONTHLY SOW FARROWING RECORD");
		statusReportButton = new Button("DIRECTORY OF SOWS");
		root.getChildren().addAll(findBreedingBySow, findBreedingByDate, findFarrowingBySow,findBreedingByBoar, findFarrowingByDate,
				monthlyBreedingReportButton,monthlyFarrowingReportButton,statusReportButton,performanceButton);

		findFarrowingBySow.setOnAction(this);

		findBreedingBySow.setOnAction(this);
		
		findBreedingByBoar.setOnAction(this);

		findBreedingByDate.setOnAction(this);
			
		findFarrowingByDate.setOnAction(this);
		
		performanceButton.setOnAction(this);
		
		monthlyBreedingReportButton.setOnAction(this);
		
		monthlyFarrowingReportButton.setOnAction(this);
		
		statusReportButton.setOnAction(this);
		
		Scene scene = new Scene(root, 500, 500);

		stage.setScene(scene);
		stage.show();
	}

	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		
		if (event.getSource() instanceof Button) {
			Button source = (Button) event.getSource();
			if(source == findBreedingBySow || source == findFarrowingBySow) {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Records");
				dialog.setHeaderText("Select Records By Sow Number");
				dialog.setContentText("Sow Number:");
				dialog.setGraphic(null);
				Optional<String> sowNo = dialog.showAndWait();
				if (sowNo.isPresent()) {
					try {
						if(null == SowRecord.getSow(sowNo.get())){
							Alert alert = new Alert(AlertType.ERROR, "Sow Not Found", ButtonType.OK);
							alert.showAndWait();
						}
						else {
							if(source == findBreedingBySow)
								breedingReportView.createBreedingBySow(sowNo.get());
							else if(source == findFarrowingBySow)
								farrowingReportView.createFarrowingBySow(sowNo.get());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else if (source == findBreedingByBoar) {
				Dialog<List<String>> dialog = new Dialog<>();
				dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
				dialog.setTitle("Records");
				dialog.setHeaderText("Select Records By Date and Boar");
				GridPane grid = new GridPane();
				DatePicker startDate = new DatePicker();
				startDate.setConverter(DatePickerFormatter.get());
				startDate.setEditable(false);
				startDate.setPromptText("Start Date");
				DatePicker endDate = new DatePicker();
				endDate.setConverter(DatePickerFormatter.get());
				endDate.setEditable(false);
				endDate.setPromptText("End Date");
				TextField boar = new TextField();
				grid.add(new Label("Boar No:"), 0, 0);
				grid.add(boar, 1, 0);
				grid.add(new Label("Start Date:"), 0, 1);
				grid.add(startDate, 1, 1);
				grid.add(new Label("End Date:"), 0, 2);
				grid.add(endDate, 1, 2);
				dialog.getDialogPane().setContent(grid);
				dialog.setResultConverter((dialogButton) -> {
					if (dialogButton == ButtonType.OK && null != startDate.getValue() && null != endDate.getValue()) {
						List<String> values = new ArrayList<>();
						values.add(boar.getText());
						values.add(DateFormat.formatToString(Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
						values.add(DateFormat.formatToString(Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
						return values;
					}
					return null;
				});
				Optional<List<String>> output = dialog.showAndWait();
				if(output.isPresent()) {
					try {
						if(null == BoarRecord.getBoar(output.get().get(0))) {
							Alert alert = new Alert(AlertType.ERROR, "Boar Not Found", ButtonType.OK);
							alert.showAndWait();
						}
						else {
							breedingReportView.createBreedingByBoar(output.get().get(0), 
									DateFormat.formatToDate(output.get().get(1)), 
									DateFormat.formatToDate(output.get().get(2)));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						System.out.println("Parse Exception");
						e.printStackTrace();
					}
				}
			}
			else if(source == findBreedingByDate || source == findFarrowingByDate) {
				Dialog<FromToDatePair> dialog = new Dialog<FromToDatePair>();
				dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
				dialog.setTitle("Records");
				dialog.setHeaderText("Select Records By Date");
				GridPane grid = new GridPane();
				DatePicker startdate = new DatePicker();
				startdate.setConverter(DatePickerFormatter.get());
				startdate.setEditable(false);
				startdate.setPromptText("Start Date");
				DatePicker endDate = new DatePicker();
				endDate.setConverter(DatePickerFormatter.get());
				endDate.setEditable(false);
				endDate.setPromptText("End Date");
				grid.add(new Label("Start Date:"), 0, 0);
				grid.add(startdate, 1, 0);
				grid.add(new Label("End Date:"), 0, 1);
				grid.add(endDate, 1, 1);
				dialog.getDialogPane().setContent(grid);
				dialog.setResultConverter((dialogButton) -> {
					if (dialogButton == ButtonType.OK && null != startdate.getValue() && null != endDate.getValue()) {
						return new FromToDatePair(
								Date.from(Instant.from(startdate.getValue().atStartOfDay(ZoneId.systemDefault()))),
								Date.from(Instant.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()))));
					}
					return null;
				});
				Optional<FromToDatePair> toFromDate = dialog.showAndWait();
				if (toFromDate.isPresent()) {
					try {
						if(source== findBreedingByDate) {
							breedingReportView.createBreedingByDate(toFromDate.get().getFrom(), toFromDate.get().getTo());
						}
						else if(source==findFarrowingByDate)
							farrowingReportView.createFarrowingByDate(toFromDate.get().getFrom(), toFromDate.get().getTo());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else if (source == performanceButton) {
				try {
					breedingPerformanceView.createBreedingPerformance();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (source == monthlyBreedingReportButton || source == monthlyFarrowingReportButton) {
				List<CalendarMonthPair> months = new ArrayList<CalendarMonthPair>();
				Calendar start =Calendar.getInstance();
				
				if(source == monthlyBreedingReportButton)
					start.setTime(BreedingRecord.breedingList.get(0).getDateBreed());
				else if( source == monthlyFarrowingReportButton)
					start.setTime(FarrowingRecord.farrowingList.get(0).getFarDate());
					
				Calendar end = Calendar.getInstance();
				while(start.before(end)) {
					Calendar toAdd = Calendar.getInstance();
					toAdd.setTime(start.getTime());
					months.add(new CalendarMonthPair(toAdd));				
					start.add(Calendar.MONTH, 1);
				}
				Collections.reverse(months);
				ChoiceDialog<CalendarMonthPair> dialog = new ChoiceDialog<CalendarMonthPair>(months.isEmpty()?null:months.get(0),months);
				dialog.setTitle("Monthly Report");
				dialog.setHeaderText("Select Month");
				Optional<CalendarMonthPair> result = dialog.showAndWait();
				if(result.isPresent()) {
					try {						
						Calendar startDate = Calendar.getInstance();
						startDate.clear();
						startDate.set(Calendar.MONTH, result.get().calendar.get(Calendar.MONTH));
						startDate.set(Calendar.DATE, result.get().calendar.getActualMinimum(Calendar.DATE));
						startDate.set(Calendar.YEAR, result.get().calendar.get(Calendar.YEAR));
						Calendar endDate = Calendar.getInstance();
						endDate.clear();
						endDate.set(Calendar.MONTH, result.get().calendar.get(Calendar.MONTH));
						endDate.set(Calendar.DATE, result.get().calendar.getActualMaximum(Calendar.DATE));
						endDate.set(Calendar.YEAR, result.get().calendar.get(Calendar.YEAR));
						
						if(source== monthlyBreedingReportButton) 
							breedingReportView.createBreedingByDate(startDate.getTime(),endDate.getTime());						
						else if(source==monthlyFarrowingReportButton) 
							farrowingReportView.createFarrowingByDate(startDate.getTime(),endDate.getTime());						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else if (source == statusReportButton) {
				try {
					statusReportView.createStatusReport();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
