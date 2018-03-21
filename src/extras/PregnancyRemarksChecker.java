package extras;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.Days;
import org.joda.time.Interval;

import model.BreedingRow;
import model.Sow;
import record.BreedingRecord;
import record.FarrowingRecord;
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
				for(int i =1;i<breedingList.size();i++) {
					
					Interval interval = new Interval(breedingList.get(i).getDateBreed().getTime(), breedingList.get(i-1).getDateBreed().getTime());
					if(Days.daysIn(interval).getDays()<100) {
						System.out.println("Sow No: " + s.getSowNo());
						System.out.println("Days Gap: "+Days.daysIn(interval).getDays());
						break;
					}
				
				}
			}
			
			List<BreedingRow> breedingListDate = breedingList.stream()
					.filter(br -> {
						Calendar todayMinusAWeek = Calendar.getInstance();
						todayMinusAWeek.add(Calendar.DATE, -7);
						Calendar due = Calendar.getInstance();
						due.setTime(br.getFarrowDueDate());
						return due.before(todayMinusAWeek);
					}).collect(Collectors.toList());
			
			for(BreedingRow b:breedingListDate) {
				if(null == FarrowingRecord.findRefNo(b.getRefNo())) {
					System.out.println("Due Date A week before today without Farrowing: "+ b.getRefNo() + " Sow No. "+b.getSowNo().getSowNo());
				}
			}
		}
	}
}
