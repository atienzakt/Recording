package reports.status;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Sow;
import record.SowRecord;

public class StatusReportView {

	public void createStatusReport() throws IOException {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("statusReport.fxml"));
		Parent root = loader.load();
		
		ObservableList<StatusRow> data = FXCollections.observableArrayList();
		for(Sow s: SowRecord.sowList.stream().sorted(Comparator.comparing(Sow::getSowNo)).collect(Collectors.toList())) {
			data.add( new StatusRow(s, data.size()+1));
		}
		
		((StatusViewController) loader.getController()).setContents(data);
		stage.setScene(new Scene(root));
		stage.show();
	}
}
