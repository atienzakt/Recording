package reports.breeding.columns;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import reports.breeding.row.BreedingReportRowBoar;
import utils.HeaderParameterPair;

public class BreedingReportColumnBoar extends BreedingReportColumn  {

	public static final String sowNo = "sowNo";
	public static final String sb = "sb";
	public static final String mm = "mm";
	
	@Override
	public void setupColumn() {
		super.setupColumn();
		breederReportColumnData.add(1, new HeaderParameterPair("SOW NO.", sowNo));
		breederReportColumnData.add(9, new HeaderParameterPair("MM", mm));
		breederReportColumnData.add(10, new HeaderParameterPair("SB", sb));
		
	}
	
	public List<TableColumn<BreedingReportRowBoar, String>> getBreedingReportColumns() {
		List<TableColumn<BreedingReportRowBoar, String>> returnValue = new ArrayList<TableColumn<BreedingReportRowBoar, String>>();
		for (HeaderParameterPair pair : breederReportColumnData) {
			TableColumn<BreedingReportRowBoar, String> column = new TableColumn<>(pair.header);
			column.setCellValueFactory(new PropertyValueFactory<>(pair.parameter));
			if( pair.header.equalsIgnoreCase("comments"))
			{
				column.setStyle("-fx-alignment: CENTER-LEFT;");
			}
			returnValue.add(column);
		}
		return returnValue;
	}
}
