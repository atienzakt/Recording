package reports.breeding.columns;

import java.util.ArrayList;
import java.util.List;

import utils.HeaderParameterPair;

public abstract class BreedingReportColumn {

	public static final String refNo ="refNo";
	// public static final String sowNo ="sowNo";
	// public static final String breed ="breed";
	// public static final String dateWeaned="dateWeaned";
	public static final String dateBreed = "dateBreed";
	// public static final String fpDays="fpDays";
	// public static final String parity="parity";
	public static final String breedingTime = "breedingTime";
	public static final String pregnancyRemarks = "pregnancyRemarks";
	// public static final String pregnancyRemarksDate="pregnancyRemarksDate";
	// public static final String farrowDueDate="farrowDueDate";
	// public static final String farrowActualDate="farrowActualDate";
	// public static final String prevBreedingRefNo="prevBreedingRefNo";
	// public static final String prevBreedingResult="prevBreedingResult";
	public static final String birth = "birth";
	public static final String liveBirth="liveBirth";
	public static final String comments = "comments";
	// public static final String ABW="ABW";
	public static final String staff = "staff";
	public static final String dateFar = "dateFar";
	

	public List<HeaderParameterPair> breederReportColumnData;
	// = new ArrayList<HeaderParameterPair>();
	// static
	// {
	// breederRecordTableRowHeadersList.add(refNo);
	// breederRecordTableRowHeadersList.add(sowNo);
	// breederRecordTableRowHeadersList.add(breed);
	// breederRecordTableRowHeadersList.add(dateWeaned);
	// breederReportColumnData.add(new HeaderParameterPair(dateBreed,"Date Breed"));
	// breederRecordTableRowHeadersList.add(fpDays);
	// breederRecordTableRowHeadersList.add(parity);
	// breederReportColumnData.add(new HeaderParameterPair(boarUsed,"Boar Used"));
	// breederReportColumnData.add(new HeaderParameterPair(breedingTime,"Breeding
	// Time"));
	// breederReportColumnData.add(new
	// HeaderParameterPair(pregnancyRemarks,"Pregnancy Remarks"));
	// breederRecordTableRowHeadersList.add(pregnancyRemarksDate);
	// breederRecordTableRowHeadersList.add(farrowDueDate);
	// breederRecordTableRowHeadersList.add(farrowActualDate);
	// breederRecordTableRowHeadersList.add(prevBreedingRefNo);
	// breederRecordTableRowHeadersList.add(prevBreedingResult);
	// breederRecordTableRowHeadersList.add(liveBirth);
	// breederRecordTableRowHeadersList.add(comments);
	// breederReportColumnData.add(new HeaderParameterPair(staff,"Staff"));
	// }

	public void setupColumn() {
		breederReportColumnData = new ArrayList<HeaderParameterPair>();
		breederReportColumnData.add(new HeaderParameterPair("DATE BREED", dateBreed));
		breederReportColumnData.add(new HeaderParameterPair("REF NO.", refNo));
		breederReportColumnData.add(new HeaderParameterPair("TIMES INSEMINATED", breedingTime));
		breederReportColumnData.add(new HeaderParameterPair("RESULT", pregnancyRemarks));
		breederReportColumnData.add(new HeaderParameterPair("STAFF", staff));
		breederReportColumnData.add(new HeaderParameterPair("DATE FARROWED", dateFar));
		breederReportColumnData.add(new HeaderParameterPair("TOTAL BIRTH", birth));
		breederReportColumnData.add(new HeaderParameterPair("LIVE BIRTH", liveBirth));
		breederReportColumnData.add(new HeaderParameterPair("COMMENTS", comments));

	}

}
