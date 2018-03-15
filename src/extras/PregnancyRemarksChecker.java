package extras;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Days;
import org.joda.time.Interval;

import model.BreedingRow;
import model.Sow;
import record.BreedingRecord;
import record.SowRecord;

public class PregnancyRemarksChecker {

	public static void pregnancyCheck() {
		System.out.println("Pregnancy Checker");
		for(Sow s:SowRecord.sowList) {
			List<BreedingRow> breedingList = BreedingRecord.filterBySow(s).stream()
												.sorted(Comparator.comparing(BreedingRow::getDateBreed).reversed())
												.filter(br -> br.getPregnancyRemarks().equals("+"))
												.filter(br -> !SowRecord.isDiseased(br.getSowNo().getSowNo()))
												.collect(Collectors.toList());
			
			if(breedingList.size()>1) {
//				System.out.println(s.getSowNo());
//				for(BreedingRow br:breedingList) {
//					if(br.getPregnancyRemarks().equals("+")) {
//						System.out.println(br.getDateBreed()+ " |||| "+br.getPregnancyRemarks());
//					}
//				}
				
				for(int i =1;i<breedingList.size();i++) {
					
					Interval interval = new Interval(breedingList.get(i).getDateBreed().getTime(), breedingList.get(i-1).getDateBreed().getTime());
					if(Days.daysIn(interval).getDays()<100) {
						System.out.println("Sow No: " + s.getSowNo());
						System.out.println("Days Gap: "+Days.daysIn(interval).getDays());
						break;
					}
				
				}
			}
		}
	}
}
