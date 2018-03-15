package reports.farrowing.columns;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import reports.farrowing.row.FarrowingReportRowSow;
import utils.HeaderParameterPair;

public class FarrowingReportColumnSow extends FarrowingReportColumn {


	@Override
	public void setupColumn() {
		super.setupColumn();
	}

	public List<TableColumn<FarrowingReportRowSow, String>> getBreedingReportColumns() {
		List<TableColumn<FarrowingReportRowSow, String>> returnValue = new ArrayList<TableColumn<FarrowingReportRowSow, String>>();
		for (HeaderParameterPair pair : farrowReportColumnData) {
			TableColumn<FarrowingReportRowSow, String> column = new TableColumn<>(pair.header);
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
