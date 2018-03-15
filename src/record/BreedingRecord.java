package record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.BreedingRow;
import model.Sow;

public class BreedingRecord {
	public static List<BreedingRow> breedingList = new ArrayList<BreedingRow>();
	public static List<String> breedingListHeaders = new ArrayList<String>();

	public static List<BreedingRow> filterBySow(Sow s) {
		List<BreedingRow> returnValue = new ArrayList<BreedingRow>();
		for (BreedingRow br : breedingList) {
			if (br.getSowNo().equals(s)) {
				returnValue.add(br);
			}
		}
		return returnValue;
	}

	public static List<BreedingRow> filterByBreedDate(Date from, Date to) {
		
		List<BreedingRow> returnValue = new ArrayList<BreedingRow>();
		for (BreedingRow br : breedingList) {
			if(null == br.getDateBreed()) {
				System.out.println("Null Entry Date for "+br.getRefNo());
			}
			else {
				if (!(br.getDateBreed().before(from) || br.getDateBreed().after(to)))
					returnValue.add(br);
			}
		}
		return returnValue;
	}

	public static BreedingRow findRefNo(String refNo) {
		for (BreedingRow br : breedingList) {
			if (br.getRefNo().equals(refNo)) {
				return br;
			}
		}
		return null;
	}

}
