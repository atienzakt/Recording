package reports.performance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import extras.ExperimentAlgos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BreedingRow;
import model.FarrowingRow;
import record.BreedingRecord;
import record.FarrowingRecord;
import record.SowRecord;
import utils.DateFormat;

public class BreedingPerformanceView {

	public void createBreedingPerformance() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("reports/performance/BreedingPerformance.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
		VBox table = (VBox) stage.getScene().lookup("#BreedPerfList");

		// So Latest Will be shown first
		Collections.reverse(BreedingRecord.breedingList);
		for(Map.Entry<String,BigDecimal> e: ExperimentAlgos.BreedingPerformance(true).entrySet()) {
			ObservableList<String> contentList = FXCollections.observableArrayList();
			for(BreedingRow br:BreedingRecord.filterBySow(SowRecord.getSow(e.getKey()))) {
				FarrowingRow fr = FarrowingRecord.findRefNo(br.getRefNo());
				contentList.add("Ref No: "+br.getRefNo()+
						"          Breed Date: "+(br.getDateBreed()!=null?DateFormat.formatToString(br.getDateBreed()):"")+
						"          Parity: "+br.getParity()+
						"          Pregnancy Remarks: "+br.getPregnancyRemarks()+
						"          Total Farrowed: "+(null == fr || null == fr.getTotalFar()? "":fr.getTotalFar())+
						"          Live Birth: "+(null == fr || null == fr.getTotalFar()? "":fr.getTotalFar()) );
				
			}	
			ListView<String> lv = new ListView<>(contentList);
			lv.setPrefHeight(contentList.size()*25);
			TitledPane tp = new TitledPane("Sow: "+e.getKey() +
					"          Score: "+e.getValue(),lv);			
			tp.setExpanded(false);		
			table.getChildren().add(tp);			
		}
		Collections.reverse(BreedingRecord.breedingList);
	}
	

	
}
