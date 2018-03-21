package reports.farrowing.columns;

import java.util.ArrayList;
import java.util.List;

import utils.HeaderParameterPair;

public abstract class FarrowingReportColumn {

	public final String refNo = "refNo";
	public final String dateBreed = "dateBreed";
	public final String totalBirth = "totalBirth";
	public final String dateFarrow = "dateFarrow";
	public final String breedTimes = "breedTimes";
	public final String liveBirth = "liveBirth";
	public final String sb = "sb";
	public final String mm = "mm";
	public final String abw = "abw";
	//public final String weanCount = "weanCount";
	public final String weanDate = "weanDate";
	public final String aww = "aww";
	public final String comment = "comment";
	public final String peroid = "peroid";
	public final String boarUsed = "boarUsed";

	public List<HeaderParameterPair> farrowReportColumnData;

	public void setupColumn() {
		farrowReportColumnData = new ArrayList<>();
		
		farrowReportColumnData.add(new HeaderParameterPair("DATE FARROW", dateFarrow));
		farrowReportColumnData.add(new HeaderParameterPair("DATE BREED", dateBreed));
		farrowReportColumnData.add(new HeaderParameterPair("REF NO.", refNo));
		farrowReportColumnData.add(new HeaderParameterPair("GESTATING DAYS", peroid));
		farrowReportColumnData.add(new HeaderParameterPair("BOAR USED", boarUsed));
		farrowReportColumnData.add(new HeaderParameterPair("TOTAL BIRTH", totalBirth));
		farrowReportColumnData.add(new HeaderParameterPair("LIVE BIRTH", liveBirth));
		farrowReportColumnData.add(new HeaderParameterPair("MM", mm));
		farrowReportColumnData.add(new HeaderParameterPair("SB", sb));
		farrowReportColumnData.add(new HeaderParameterPair("AVE. BIRTH WEIGHT", abw));
		//farrowReportColumnData.add(new HeaderParameterPair("Times Inseminated", breedTimes));
		//farrowReportColumnData.add(new HeaderParameterPair("WEANED PIGLETS", weanCount));
		farrowReportColumnData.add(new HeaderParameterPair("DATE WEANED", weanDate));
		farrowReportColumnData.add(new HeaderParameterPair("COMMENTS", comment));
	}
}
