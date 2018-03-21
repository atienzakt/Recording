package reports.farrowing.columns;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import reports.farrowing.row.FarrowingReportRowDate;
import utils.HeaderParameterPair;

public class FarrowingReportColumnDate extends FarrowingReportColumn {

	public final String sowNo = "sowNo";
	
	public void setupColumn() {
		super.setupColumn();
		farrowReportColumnData.add(3, new HeaderParameterPair("SOW NO.", sowNo));
	}

	public List<TableColumn<FarrowingReportRowDate, String>> getFarrowingReportColumns() {
		List<TableColumn<FarrowingReportRowDate, String>> returnValue = new ArrayList<TableColumn<FarrowingReportRowDate, String>>();
		for (HeaderParameterPair pair : farrowReportColumnData) {
			TableColumn<FarrowingReportRowDate, String> column = new TableColumn<>(pair.header);
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
