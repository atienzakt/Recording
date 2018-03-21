package reports.breeding.columns;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import reports.breeding.row.BreedingReportRowDate;
import utils.HeaderParameterPair;

public class BreedingReportColumnDate extends BreedingReportColumn {

	public static final String sowNo = "sowNo";
	public static final String boarUsed = "boarUsed";
	public static final String dateWean = "dateWean";

	@Override
	public void setupColumn() {
		super.setupColumn();		
		breederReportColumnData.add(4,new HeaderParameterPair("BOAR USED", boarUsed));
		breederReportColumnData.add(9,new HeaderParameterPair("DATE WEANED", dateWean));
		breederReportColumnData.add(2,new HeaderParameterPair("SOW NO.", sowNo));
	}

	public List<TableColumn<BreedingReportRowDate, String>> getBreedingReportColumns() {
		List<TableColumn<BreedingReportRowDate, String>> returnValue = new ArrayList<TableColumn<BreedingReportRowDate, String>>();
		for (HeaderParameterPair pair : breederReportColumnData) {
			TableColumn<BreedingReportRowDate, String> column = new TableColumn<>(pair.header);
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
