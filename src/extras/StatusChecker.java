package extras;

import java.util.Collections;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.Days;

import model.BreedingRow;
import model.FarrowingRow;
import model.Sow;
import record.BreedingRecord;
import record.FarrowingRecord;
import record.SowRecord;

public class StatusChecker {

	public static void setAndCheckStatus() {
		System.out.println("Status Checker");
		
		for(Sow s:SowRecord.diseasedSowList) {
			s.setStatus("Deceased");
		}
		Collections.reverse(BreedingRecord.breedingList);
		for(Sow s:SowRecord.sowList.stream()
				.filter(sow -> !SowRecord.isDiseased(sow.getSowNo()))
				.collect(Collectors.toList())) {
			BreedingRow latest= null;
			
			for(BreedingRow b:BreedingRecord.breedingList) {
				if(b.getSowNo().getSowNo().equals(s.getSowNo())) {
					latest = b;
					break;
				}
			}
			
			if(null == latest) {
				System.out.println("Error in setting status for Sow: "+s.getSowNo());
			}
			
			
			DateTime now = new DateTime();
			DateTime due = new DateTime(latest.getFarrowDueDate());
			FarrowingRow farrow = FarrowingRecord.findRefNo(latest.getRefNo());
			
			if(latest.getPregnancyRemarks().equals("+")) {
								
//				//When not farrowed and due date - now is within 14 to -5 days is expecting
//				if( (Days.daysBetween(now.toLocalDate(), due.toLocalDate()).getDays() <=14 
//						&& Days.daysBetween(now.toLocalDate(), due.toLocalDate()).getDays()>= -5) 
//						&& null == farrow ){
//					s.setStatus("Expecting");
//				}
				//When not farrowed and due date is before 14 days
//				else if( (Days.daysBetween(now.toLocalDate(), due.toLocalDate()).getDays() <=114 
//						&& Days.daysBetween(now.toLocalDate(), due.toLocalDate()).getDays()>= 15) 
//						&& null == farrow) {
//					s.setStatus("Pregnant");
//				}
				//Not yet farrowed and + is pregnant
				if( null == farrow) {
					s.setStatus("Pregnant");
				}
				//Farrowed but not weaned is lactating
				else if( null != farrow && (null == latest.getDateWeaned() || null == farrow.getWeanDate())) {
					s.setStatus("Lactating");
				}
				//Farrowed but weaned is dry sow
				else if( null != farrow && (null != latest.getDateWeaned() || null != farrow.getWeanDate())) {
					s.setStatus("Dry Sow");
				}
				else {
					System.out.println("Check on: "+s.getSowNo());
				}
				
			}
			else if ( null == farrow && (latest.getPregnancyRemarks().equals("+AB") || latest.getPregnancyRemarks().equals("-RB"))) {
				s.setStatus("Breedable");
			}
			else if (null == farrow && (latest.getPregnancyRemarks().trim().equals(""))) {
				s.setStatus("Pending");
			}
			else {
				System.out.println("Check on: "+s.getSowNo());
			}
		}
		Collections.reverse(BreedingRecord.breedingList);
	}
}
