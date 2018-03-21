package extras;

import java.util.Calendar;

import model.FarrowingRow;
import record.FarrowingRecord;
import record.SowRecord;

public class WeanChecker {

	public static void check() {
		System.out.println("Wean Check");
		for(FarrowingRow fr: FarrowingRecord.farrowingList) {
			if(SowRecord.isDiseased(fr.getSowNo().getSowNo())) {
				continue;
			}
			Calendar today = Calendar.getInstance();
			Calendar estimatedWean = Calendar.getInstance();
			estimatedWean.setTime(fr.getFarDate());
			estimatedWean.add(Calendar.DATE, 35);
			if(fr.getSowNo().getStatus().equalsIgnoreCase("lactating") && fr.getWeanDate() == null && estimatedWean.before(today)) {
				System.out.println("Long Farrowing Check: "+fr.getRefNo());
			}
		}
	}
}
