package reports.breeding.columns;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import reports.breeding.row.BreedingReportRowSow;
import utils.HeaderParameterPair;

public class BreedingReportColumnSow extends BreedingReportColumn {

	

	
	public static final String boarUsed = "boarUsed";
	public static final String dateWean = "dateWean";
	

	@Override
	public void setupColumn() {
		super.setupColumn();		
		breederReportColumnData.add(5,new HeaderParameterPair("BOAR USED", boarUsed));
		breederReportColumnData.add(7,new HeaderParameterPair("DATE WEANED", dateWean));		
	}

	public List<TableColumn<BreedingReportRowSow, String>> getBreedingReportColumns() {
		List<TableColumn<BreedingReportRowSow, String>> returnValue = new ArrayList<TableColumn<BreedingReportRowSow, String>>();
		for (HeaderParameterPair pair : breederReportColumnData) {
			TableColumn<BreedingReportRowSow, String> column = new TableColumn<>(pair.header);
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
