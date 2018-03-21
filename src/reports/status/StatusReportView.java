package reports.status;

import java.io.IOException;

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
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("statusReport.fxml").openStream());
		
		ObservableList<StatusRow> data = FXCollections.observableArrayList();
		for(Sow s: SowRecord.sowList) {
			data.add( new StatusRow(s, data.size()+1));
		}
		
		((StatusViewController) loader.getController()).setContents(data);
		stage.setScene(new Scene(root));
		stage.show();
	}
}
