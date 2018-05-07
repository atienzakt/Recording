package extras;

import model.Boar;
import model.BreedingRow;
import model.FarrowingRow;
import record.BreedingRecord;
import record.FarrowingRecord;

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
			else if(null != FarrowingRecord.findRefNo(br.getRefNo()) && !( br.getPregnancyRemarks().equals("-RB") || br.getPregnancyRemarks().equals("+AB"))) {
				System.out.println("No Pregnancy for Record for : "+br.getRefNo());
			}
		}
	}
}
