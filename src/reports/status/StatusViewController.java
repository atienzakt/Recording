package reports.status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Predicate;

import org.joda.time.LocalDate;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Sow;
import record.SowRecord;
import utils.DateFormat;

public class StatusViewController{

	@FXML
	private TableView<StatusRow> table;
	
	@FXML
	private TableColumn<StatusRow,Integer> noColumn;
	
	@FXML
	private TableColumn<StatusRow, String> sowNoColumn;
	
	@FXML
	private TableColumn<StatusRow, String> dateOfBirthColumn;
	
	@FXML
	private TableColumn<StatusRow, String> breedColumn;
	
	@FXML
	private TableColumn<StatusRow, String> parityColumn;
	
	@FXML
	private TableColumn<StatusRow, String> totalLitterColumn;
	
	@FXML
	private TableColumn<StatusRow, String> totalLiveColumn;
	
	@FXML
	private TableColumn<StatusRow, LocalDate> firstParityColumn;
	
	@FXML
	private TableColumn<StatusRow, String> lastWeanColumn;
	
	@FXML
	private TableColumn<StatusRow, String> statusColumn;
	
	@FXML
	private TextField noFilter;

	@FXML
	private TextField sowNoFilter;
	
	@FXML
	private TextField dateOfBirthFilter;
	
	@FXML
	private TextField breedFilter;
	
	@FXML
	private TextField parityFilter;
	
	@FXML
	private TextField totalLitterFilter;
	
	@FXML
	private TextField totalLiveFilter;
	
	@FXML
	private TextField firstParityFilter;
	
	@FXML
	private TextField lastWeanFilter;
	
	@FXML
	private TextField statusFilter;
	
	@FXML
	private Text totalActiveSows;
	
	@FXML
	private Text pregnantCount;
	
	@FXML
	private Text pregnantRate;
	
	@FXML
	private Text lactatingCount;
	
	@FXML
	private Text lactatingRate;
	
	@FXML
	private Text inseminatedCount;
	
	@FXML
	private Text inseminatedRate;
	
	@FXML
	private Text breedableCount;
	
	@FXML
	private Text breedableRate;
	
	@FXML
	private Text drySowCount;
	
	@FXML
	private Text drySowRate;
	
	@FXML
	private Text totalInactiveSows;
	
	@FXML
	private Text cullCount;
	
	@FXML
	private Text deceasedCount;
	
	@FXML
	private Text giltCount;
	
	@FXML
	private Text othersCount;
	
	private ObservableList<StatusRow> data;
	
	private FilteredList<StatusRow> filteredData;
	
	private DecimalFormat dcf = new DecimalFormat("###0.00");
	
	@FXML
	public void initialize() {
		noColumn.setCellValueFactory(data -> data.getValue().no.asObject());
		sowNoColumn.setCellValueFactory(data -> data.getValue().sowNo);
		dateOfBirthColumn.setCellValueFactory(data -> data.getValue().dateOfBirth);
		breedColumn.setCellValueFactory(data -> data.getValue().breed );
		parityColumn.setCellValueFactory(data -> data.getValue().parity);
		totalLitterColumn.setCellValueFactory(data -> data.getValue().totalLitter);
		totalLiveColumn.setCellValueFactory(data -> data.getValue().totalLive);
		firstParityColumn.setCellValueFactory( new PropertyValueFactory<StatusRow,LocalDate>("firstParity"));
		firstParityColumn.setCellFactory(column -> new TableCell<StatusRow,LocalDate>(){

			@Override
			protected void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if(empty && null == item) {
					setText("");
				}
				else if(null != item) {
					setText(DateFormat.formatToString(item.toDate()));
				}
				else {
					setText("N/A");
				}
			}
			
		});
		lastWeanColumn.setCellValueFactory(data ->data.getValue().lastWean);
		statusColumn.setCellValueFactory(data ->data.getValue().status);
		
		
	}
	
	public void setContents(ObservableList<StatusRow> data) {
		this.data = data;
		filteredData = new FilteredList<StatusRow>(data);
		filteredData.predicateProperty().bind(
				Bindings.createObjectBinding(() -> row -> row.getNo().toString().toLowerCase().contains(noFilter.getText().toLowerCase())
						&& row.getSowNo().toString().toLowerCase().contains(sowNoFilter.getText().toLowerCase())
						&& row.getDateOfBirth().toString().toLowerCase().contains(dateOfBirthFilter.getText().toLowerCase())
						&& row.getBreed().toString().toLowerCase().contains(breedFilter.getText().toLowerCase())
						&& row.getParity().toString().toLowerCase().contains(parityFilter.getText().toLowerCase())
						&& row.getTotalLitter().toString().toLowerCase().contains(totalLitterFilter.getText().toLowerCase())
						&& row.getTotalLive().toString().toLowerCase().contains(totalLiveFilter.getText().toLowerCase())
						&& row.getLastWean().toString().toLowerCase().contains(lastWeanFilter.getText().toLowerCase())
						&& row.getStatus().toString().toLowerCase().contains(statusFilter.getText().toLowerCase())
						&& ( (null == row.getFirstParity() && ("N/A".toLowerCase().contains(firstParityFilter.getText().toLowerCase()) ))
						|| (null != row.getFirstParity() && DateFormat.formatToString(row.getFirstParity().toDate()).toLowerCase().contains(firstParityFilter.getText().toLowerCase()))), 
						noFilter.textProperty(),
						sowNoFilter.textProperty(),
						dateOfBirthFilter.textProperty(),
						breedFilter.textProperty(),
						parityFilter.textProperty(),
						totalLitterFilter.textProperty(),
						totalLiveFilter.textProperty(),
						lastWeanFilter.textProperty(),
						statusFilter.textProperty(),
						firstParityFilter.textProperty()));
		SortedList<StatusRow> wrapped = new SortedList(filteredData);
		wrapped.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(wrapped);
		
		
		
		
//		totalActiveSows.setText(data.size()+"");
//		
//		filteredData.predicateProperty().bind((Bindings.createObjectBinding(new Callable<>() {
//
//			@Override
//			public Predicate<? super StatusRow> call() throws Exception {
//				System.out.println("do SOmething");
//				return new Predicate<StatusRow>(){
//
//					@Override
//					public boolean test(StatusRow arg0) {
//						System.out.println("Do  More "+arg0.getSowNo());
//						return false;
//					}
//					
//				};
//			}
//		},noFilter.textProperty())));
		BigDecimal totalActive = BigDecimal.valueOf(data.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo()) && !SowRecord.getSow(row.getSowNo()).getStatus().toLowerCase().contains("inactive")).count());
		
		BigDecimal pregnant = BigDecimal.valueOf(data.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo()))
				.filter(row -> row.getStatus().toLowerCase().contains("pregnant")).count());
		
		String pregnantPercentage = totalActive.intValue()!=0? dcf.format(pregnant.divide(totalActive,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		BigDecimal lactating = BigDecimal.valueOf(data.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo()))
				.filter(row -> row.getStatus().toLowerCase().contains("lactating")).count());
		
		String lactatingPercentage = totalActive.intValue()!=0? dcf.format(lactating.divide(totalActive,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		BigDecimal inseminated = BigDecimal.valueOf(data.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo()))
				.filter(row -> row.getStatus().toLowerCase().contains("inseminated")).count());
		
		String inseminatedPercentage = totalActive.intValue()!=0? dcf.format(inseminated.divide(totalActive,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		BigDecimal breedable = BigDecimal.valueOf(data.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo()))
				.filter(row -> row.getStatus().toLowerCase().contains("breedable")).count());
		
		String breedablePercentage = totalActive.intValue()!=0? dcf.format(breedable.divide(totalActive,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		BigDecimal drySow = BigDecimal.valueOf(data.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo()))
				.filter(row -> row.getStatus().toLowerCase().contains("dry")).count());
		
		String drySowPercentage = totalActive.intValue()!=0? dcf.format(drySow.divide(totalActive,4,RoundingMode.HALF_UP).floatValue()*100)+"%":"0.00%";
		
		totalActiveSows.setText(totalActive.intValue()+"");
		pregnantCount.setText(pregnant.intValue()+"");
		pregnantRate.setText(pregnantPercentage);
		lactatingCount.setText(lactating.intValue()+"");
		lactatingRate.setText(lactatingPercentage);
		inseminatedCount.setText(inseminated.intValue()+"");
		inseminatedRate.setText(inseminatedPercentage);
		breedableCount.setText(breedable.intValue()+"");
		breedableRate.setText(breedablePercentage);
		drySowCount.setText(drySow.intValue()+"");
		drySowRate.setText(drySowPercentage);
		
		BigDecimal totalInactive = BigDecimal.valueOf(data.stream()
				.filter(row -> SowRecord.isDiseased(row.getSowNo()) || SowRecord.getSow(row.getSowNo()).getStatus().toLowerCase().contains("inactive")).count());
		BigDecimal deceased = BigDecimal.valueOf(data.stream()
				.filter(row -> SowRecord.isDiseased(row.getSowNo()))
				.filter(row -> row.getStatus().toLowerCase().contains("deceased") || row.getStatus().toLowerCase().contains("disease") ).count());
		BigDecimal cull = BigDecimal.valueOf(data.stream()
				.filter(row -> SowRecord.isDiseased(row.getSowNo()))
				.filter(row -> row.getStatus().toLowerCase().contains("cull")).count());
		BigDecimal gilt = BigDecimal.valueOf(data.stream()
				.filter(row -> row.getStatus().toLowerCase().contains("inactive")).count());
		BigDecimal others = BigDecimal.valueOf(data.stream()
				.filter(row -> SowRecord.isDiseased(row.getSowNo()))
				.filter(row -> row.getStatus().toLowerCase().contains("heat")).count());
		totalInactiveSows.setText(totalInactive.intValue()+"");
		deceasedCount.setText(deceased.intValue()+"");
		cullCount.setText(cull.intValue()+"");
		giltCount.setText(gilt.intValue()+"");
		othersCount.setText(others.intValue()+"");

	}


	public void setFilter() {
//		noFilter.textProperty().addListener(new TextFieldListener(filteredData));
//		sowNoFilter.textProperty().addListener(new TextFieldListener(filteredData));
//		dateOfBirthFilter.textProperty().addListener(new TextFieldListener(filteredData));
//		breedFilter.textProperty().addListener(new TextFieldListener(filteredData));
//		totalLitterFilter.textProperty().addListener(new TextFieldListener(filteredData));
//		totalLiveFilter.textProperty().addListener(new TextFieldListener(filteredData));
//		firstParityFilter.textProperty().addListener(new TextFieldListener(filteredData));
//		lastWeanFilter.textProperty().addListener(new TextFieldListener(filteredData));
//		statusFilter.textProperty().addListener(new TextFieldListener(filteredData));
		
		
//		noFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//				
//				return row.getNo().toString().toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
//		
//		sowNoFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//				
//				return row.getSowNo().toString().toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
//		
//		dateOfBirthFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//				
//				return row.getDateOfBirth().toString().toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
//		
//		breedFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//				
//				return row.getBreed().toString().toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
//		
//		parityFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//				
//				return row.getParity().toString().toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
//		
//		totalLitterFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//				
//				return row.getTotalLitter().toString().toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
//		
//		totalLiveFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//				
//				return row.getTotalLive().toString().toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
//		
//		firstParityFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }			
//				else if(null == row.getFirstParity() && ("N/A".toLowerCase().contains(newValue.toLowerCase()))){
//					return true;
//				}
//				else if(null == row.getFirstParity()) {
//					return false;
//				}
//				return DateFormat.formatToString(row.getFirstParity().toDate()).toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
//		
//		lastWeanFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//				
//				return row.getLastWean().toString().toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
//		
//		statusFilter.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(row -> {
//				if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//				
//				return row.getStatus().toString().toLowerCase().contains(newValue.toLowerCase())? true: false;
//			});
//		});
		
//		table.setItems(filteredData);
	}
	
	private class TextFieldListener implements ChangeListener<String>{

		private final FilteredList<StatusRow> filteredData;
		
		private TextFieldListener(FilteredList<StatusRow> filteredData) {
		    this.filteredData=filteredData;
		}
		  
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			filteredData.setPredicate(row -> row.getNo().toString().toLowerCase().contains(noFilter.getText().toLowerCase())
					&& row.getSowNo().toString().toLowerCase().contains(sowNoFilter.getText().toLowerCase())
					&& row.getDateOfBirth().toString().toLowerCase().contains(dateOfBirthFilter.getText().toLowerCase())
					&& row.getBreed().toString().toLowerCase().contains(breedFilter.getText().toLowerCase())
					&& row.getParity().toString().toLowerCase().contains(parityFilter.getText().toLowerCase())
					&& row.getTotalLitter().toString().toLowerCase().contains(totalLitterFilter.getText().toLowerCase())
					&& row.getTotalLive().toString().toLowerCase().contains(totalLiveFilter.getText().toLowerCase())
					&& row.getLastWean().toString().toLowerCase().contains(lastWeanFilter.getText().toLowerCase())
					&& row.getStatus().toString().toLowerCase().contains(statusFilter.getText().toLowerCase())
					&& ( (null == row.getFirstParity() && ("N/A".toLowerCase().contains(firstParityFilter.getText().toLowerCase()) ))
					|| (null != row.getFirstParity() && DateFormat.formatToString(row.getFirstParity().toDate()).toLowerCase().contains(firstParityFilter.getText().toLowerCase()))));
		
			filteredData.setPredicate(new Predicate<StatusRow>() {

				

				@Override
				public boolean test(StatusRow row) {
					System.out.println(row.getSowNo());
					return row.getNo().toString().toLowerCase().contains(noFilter.getText().toLowerCase())
							&& row.getSowNo().toString().toLowerCase().contains(sowNoFilter.getText().toLowerCase())
							&& row.getDateOfBirth().toString().toLowerCase().contains(dateOfBirthFilter.getText().toLowerCase())
							&& row.getBreed().toString().toLowerCase().contains(breedFilter.getText().toLowerCase())
							&& row.getParity().toString().toLowerCase().contains(parityFilter.getText().toLowerCase())
							&& row.getTotalLitter().toString().toLowerCase().contains(totalLitterFilter.getText().toLowerCase())
							&& row.getTotalLive().toString().toLowerCase().contains(totalLiveFilter.getText().toLowerCase())
							&& row.getLastWean().toString().toLowerCase().contains(lastWeanFilter.getText().toLowerCase())
							&& row.getStatus().toString().toLowerCase().contains(statusFilter.getText().toLowerCase())
							&& ( (null == row.getFirstParity() && ("N/A".toLowerCase().contains(firstParityFilter.getText().toLowerCase()) ))
							|| (null != row.getFirstParity() && DateFormat.formatToString(row.getFirstParity().toDate()).toLowerCase().contains(firstParityFilter.getText().toLowerCase())));
				}
			});
		}
		
		
	}
}


