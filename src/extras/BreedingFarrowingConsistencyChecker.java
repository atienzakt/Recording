package extras;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.Boar;
import model.BreedingRow;
import model.FarrowingRow;
import record.BreedingRecord;
import record.FarrowingRecord;
import record.SowRecord;

public class BreedingFarrowingConsistencyChecker {

	public static void check() {
		System.out.println("Consistency Checker");
		for(BreedingRow br: BreedingRecord.breedingList) {
			if(null != FarrowingRecord.findRefNo(br.getRefNo()) && !( br.getPregnancyRemarks().equals("-RB") || br.getPregnancyRemarks().equals("+AB"))) {
				FarrowingRow fr = FarrowingRecord.findRefNo(br.getRefNo());
				
				if(!br.getSowNo().getSowNo().equals(fr.getSowNo().getSowNo())) {
					System.out.println("Mismatch for Sow No: "+br.getRefNo());
				}

//				StringBuilder boarBreed = new StringBuilder();
//				StringBuilder boarFarrow = new StringBuilder();
//				for(Boar b : br.getBoarUsed()) {
//					boarBreed.append(b.getBoarNo()+"/");
//				}
//				for(Boar b:fr.getBoarUsed()) {
//					boarFarrow.append(b.getBoarNo()+"/");
//				}
//				
//				if(!boarBreed.toString().equals(boarFarrow.toString())) {
//					System.out.println("Mismatch for Boar No: "+br.getRefNo());
//				}
//				
//				if(null != br.getFarrowActualDate() && null !=fr.getFarDate() && !br.getFarrowActualDate().equals(fr.getFarDate())) {
//					System.out.println("Mismatch for Farrow Date: "+br.getRefNo());
//				}
			}
			
			if(null != FarrowingRecord.findRefNo(br.getRefNo()) 
					&& ( br.getPregnancyRemarks().equals("-RB") || br.getPregnancyRemarks().equals("+AB"))
					&& !SowRecord.isDiseased(br.getSowNo().getSowNo())) {
				System.out.println("Not Pregnant But Farrowed : "+br.getRefNo());
			}
		}
		
		for(FarrowingRow fr: FarrowingRecord.farrowingList) {
			if(null == BreedingRecord.findRefNo(fr.getRefNo())) {
				System.out.println("Farrowing without Breeding: "+fr.getRefNo());
			}
		}
		
		List<String> listRefNo = BreedingRecord.breedingList.stream().map(br -> br.getRefNo()).collect(Collectors.toList());
		Set<String> setRefNo = listRefNo.stream().collect(Collectors.toSet());
		if(listRefNo.size() != setRefNo.size()) {
			System.out.println("|| Breeding Ref No Error ||");
		}
		
		listRefNo = FarrowingRecord.farrowingList.stream().map(fr -> fr.getRefNo()).collect(Collectors.toList());
		setRefNo = listRefNo.stream().collect(Collectors.toSet());
		if(listRefNo.size() != setRefNo.size()) {
			System.out.println("|| Farrowing Ref No Error ||");
		}
	}
}
