package record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.FarrowingRow;
import model.Sow;

public class FarrowingRecord {

	public static List<FarrowingRow> farrowingList = new ArrayList<FarrowingRow>();
	public static List<String> farrowingListHeaders = new ArrayList<String>();

	public static List<FarrowingRow> filterBySow(Sow s) {
		List<FarrowingRow> returnValue = new ArrayList<FarrowingRow>();
		for (FarrowingRow fr : farrowingList) {
			if (fr.getSowNo().equals(s)) {
				returnValue.add(fr);
			}
		}
		return returnValue;
	}

	public static List<FarrowingRow> filterByFarrowDate(Date from, Date to) {
		List<FarrowingRow> returnValue = new ArrayList<FarrowingRow>();
		for (FarrowingRow fr : farrowingList) {
			if(null == fr.getFarDate()) {
				System.out.println("Null Entry for Farrowing "+fr.getRefNo());
			}
			else {
				if (!(fr.getFarDate().before(from) || fr.getFarDate().after(to)))
					returnValue.add(fr);
			}
		}
		return returnValue;
	}

	public static FarrowingRow findRefNo(String refNo) {
		for (FarrowingRow fr : farrowingList) {
			if (fr.getRefNo().equals(refNo)) {
				return fr;
			}
		}
		return null;
	}

	public static List<FarrowingRow> filterByBreedingDate(Date from, Date to) {
		List<FarrowingRow> returnValue = new ArrayList<FarrowingRow>();
		for (FarrowingRow fr : farrowingList) {
			if (!(fr.getBreedingRow().getDateBreed().before(from) || fr.getBreedingRow().getDateBreed().after(to))) {
				returnValue.add(fr);
			}
		}
		return returnValue;
	}
}
