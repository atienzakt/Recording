package record;

import java.util.ArrayList;
import java.util.List;

import model.BreedingRow;
import model.FarrowingRow;
import model.Sow;

public class SowRecord {
	public static List<Sow> sowList = new ArrayList<Sow>();
	public static List<Sow> diseasedSowList = new ArrayList<Sow>();

	public static Sow getSow(String sowNo) {
		for (Sow s : sowList) {
			if (s.getSowNo().equals(sowNo)) {
				return s;
			}
		}
		return null;
	}
	
	public static boolean isDiseased(String sowNo) {
		for(Sow s:diseasedSowList) {
			if(s.getSowNo().equals(sowNo)) {
				return true;
			}
		}
		return false;
	}
	
	public static void tagDeadSows() {
		for(BreedingRow br: BreedingRecord.breedingList) {
			if(isDiseased(br.getSowNo().getSowNo())) {
				br.setComments((br.getComments()+ System.lineSeparator()+ " Deceased").trim());
			}
		}
	}
	
	public static void setParities() {
		for(Sow s: sowList) {
			s.setParity(FarrowingRecord.filterBySow(s).size()+"");
		}
	}
}
