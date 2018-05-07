package extras;

import java.util.Calendar;
import java.util.Collections;
import java.util.stream.Collectors;

import model.BreedingRow;
import model.FarrowingRow;
import model.Sow;
import record.BreedingRecord;
import record.FarrowingRecord;
import record.SowRecord;

public class DurationChecker {

	public static void check() {
		System.out.println("Lactating Check");
		for(FarrowingRow fr: FarrowingRecord.farrowingList) {
			if(SowRecord.isDiseased(fr.getSowNo().getSowNo())) {
				continue;
			}
			Calendar today = Calendar.getInstance();
			Calendar estimatedWean = Calendar.getInstance();
			estimatedWean.setTime(fr.getFarDate());
			estimatedWean.add(Calendar.DATE, 35);
			if(fr.getSowNo().getStatus().equalsIgnoreCase("lactating") && fr.getWeanDate() == null && estimatedWean.before(today) && !fr.getComments().toLowerCase().contains("fource")) {
				System.out.println("Long Lactating Check: "+fr.getRefNo());
			}
		}
		
		System.out.println("Pregnant Check");
		for(BreedingRow br:BreedingRecord.breedingList.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo().getSowNo()))
				.filter(row -> row.getPregnancyRemarks().trim().equals("+"))
				
				.collect(Collectors.toList())) {
			Calendar today = Calendar.getInstance();
			Calendar dueDate = Calendar.getInstance();
			dueDate.setTime(br.getDateBreed());
			dueDate.add(Calendar.DATE, 125);
			if(null==FarrowingRecord.findRefNo(br.getRefNo()) && dueDate.before(today)) {
				System.out.println("Long Pregnancy Check: "+br.getRefNo());
			}
		}
		
		System.out.println("Inseminated Check");
		for(BreedingRow br:BreedingRecord.breedingList.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo().getSowNo()))
				.filter(row -> row.getPregnancyRemarks().trim().equals(""))
				.filter(row -> row.getSowNo().getStatus().toLowerCase().contains("inseminated"))
				.collect(Collectors.toList())) {
			Calendar today = Calendar.getInstance();
			Calendar dueDate = Calendar.getInstance();
			dueDate.setTime(br.getDateBreed());
			dueDate.add(Calendar.DATE, 35);
			if(null==FarrowingRecord.findRefNo(br.getRefNo()) && dueDate.before(today)) {
				System.out.println("Long Inseminated Check: "+br.getRefNo());
			}
		}
		
		System.out.println("Breedable Check");
		for(BreedingRow br:BreedingRecord.breedingList.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo().getSowNo()))
				.filter(row -> row.getPregnancyRemarks().trim().equals("-RB") || row.getPregnancyRemarks().trim().equals("+AB"))
				.filter(row -> row.getSowNo().getStatus().toLowerCase().contains("breedable"))
				.collect(Collectors.toMap(BreedingRow::getSowNo, b -> b,(oldValue,newValue) ->newValue)).values()) {
			Calendar today = Calendar.getInstance();
			Calendar dueDate = Calendar.getInstance();
			dueDate.setTime(br.getDateBreed());
			dueDate.add(Calendar.DATE, 30);
			if(null==FarrowingRecord.findRefNo(br.getRefNo()) && dueDate.before(today)) {
				System.out.println("Long Breedable Check: "+br.getRefNo() + " || "+ br.getSowNo().getSowNo());
			}
		}
		
		System.out.println("Dry Sow Check");

		for(FarrowingRow fr: FarrowingRecord.farrowingList.stream()
				.filter(row -> !SowRecord.isDiseased(row.getSowNo().getSowNo()))
				.filter(row -> null!=row.getWeanDate())
				.filter(row -> row.getSowNo().getStatus().toLowerCase().contains("dry sow"))
				.collect(Collectors.toList())) {
			Calendar today = Calendar.getInstance();
			Calendar dueDate = Calendar.getInstance();

			dueDate.setTime(fr.getWeanDate());
			dueDate.add(Calendar.DATE, 14);
			if(dueDate.before(today)) {
				System.out.println("Long Dry Sow Check: "+fr.getRefNo() + " || "+fr.getSowNo().getSowNo());
			}
		}
	
	}
}
