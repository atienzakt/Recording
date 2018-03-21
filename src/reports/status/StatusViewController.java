package reports.status;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StatusViewController {

	@FXML
	private TableView<StatusRow> table;
	
	@FXML
	private TableColumn<StatusRow,String> noColumn;
	
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
	private TableColumn<StatusRow, String> firstParityColumn;
	
	@FXML
	private TableColumn<StatusRow, String> lastWeanColumn;
	
	@FXML
	private TableColumn<StatusRow, String> statusColumn;
	
	@FXML
	public void initialize() {
		noColumn.setCellValueFactory(data -> data.getValue().no);
		sowNoColumn.setCellValueFactory(data -> data.getValue().sowNo);
		dateOfBirthColumn.setCellValueFactory(data -> data.getValue().dateOfBirth);
		breedColumn.setCellValueFactory(data -> data.getValue().breed );
		parityColumn.setCellValueFactory(data -> data.getValue().parity);
		totalLitterColumn.setCellValueFactory(data -> data.getValue().totalLitter);
		totalLiveColumn.setCellValueFactory(data -> data.getValue().totalLive);
		firstParityColumn.setCellValueFactory(data -> data.getValue().firstParity);
		lastWeanColumn.setCellValueFactory(data ->data.getValue().lastWean);
		statusColumn.setCellValueFactory(data ->data.getValue().status);
		
	}
	
	public void setContents(ObservableList<StatusRow> data) {
		table.setItems(data);
	}
}
